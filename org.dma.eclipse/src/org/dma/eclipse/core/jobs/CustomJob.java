/*******************************************************************************
 * 2008-2020 Public Domain
 * Contributors
 * Marco Lopes (marcolopespt@gmail.com)
 *******************************************************************************/
package org.dma.eclipse.core.jobs;

import java.util.ArrayList;
import java.util.List;

import org.dma.eclipse.core.jobs.tasks.JobTask;
import org.dma.eclipse.core.jobs.tasks.JobUITask;
import org.dma.java.util.Chronograph;
import org.dma.java.util.Debug;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Display;
/**
 * On the Job: The Eclipse Jobs API
 * http://www.eclipse.org/articles/Article-Concurrency/jobs-api.html
 */
public class CustomJob extends Job {

	// MUTEX: MUTual EXclusion semaphore
	/** Global rule to avoid simultaneous executions */
	public static final ISchedulingRule MUTEX_RULE = new MutexRule();

	/** Global family to identify {@link CustomJob} */
	public static final Object FAMILY = MUTEX_RULE;

	/** Tries to cancel all jobs from this {@link CustomJob#FAMILY}
	 * and executes {@link Thread#sleep(long)} repeatedly
	 * to allow non-cancelable RUNNING jobs to terminate */
	public static void cancelAll(long sleep) {
		Job.getJobManager().cancel(FAMILY);
		while(!getJobManager().isIdle()) try{
			Thread.sleep(sleep);
		}catch(Exception e){}
	}

	private final List<JobTask> tasks=new ArrayList();
	@Deprecated
	private IAction doneAction;

	private boolean canceled=false;

	public CustomJob() {
		this("");
	}

	/** @see CustomJob#CustomJob(String, int) */
	public CustomJob(String name) {
		this(name, Job.LONG);
	}

	/**
	 * Creates a new job
	 * <p>
	 * The new job is created with a MUTUAL EXCLUSION rule that prevents
	 * two or more jobs with the same rule from executing at the same time
	 * <p>
	 * <b>setRule(MUTEX_RULE)</b> can be used to create a new rule for a new
	 * batch of jobs that need to be mutal exclusive
	 * <p>
	 * <b>setRule(null)</b> can be used to indicate that the job has no rule and
	 * can be executed immediately
	 *
	 * @param name - The job name. <b>if NULL job will be silent!</b>
	 * @param priority - One of INTERACTIVE, SHORT, LONG, BUILD, or DECORATE.
	 *
	 */
	public CustomJob(String name, int priority) {
		super(name==null ? "" : name);
		setPriority(priority);
		setUser(false); // avoid progress dialogue
		setSystem(name==null); // do not reveal in any UI
		setRule(MUTEX_RULE);
	}


	@Override
	public boolean belongsTo(Object family) {
		return family.equals(FAMILY);
	}



	/*
	 * Tasks
	 */
	public CustomJob addTask(JobTask action) {
		tasks.add(action);
		return this;
	}

	public CustomJob removeTask(JobTask action) {
		tasks.remove(action);
		return this;
	}

	public boolean hasTasks() {
		return !tasks.isEmpty();
	}

	/** @see ExclusiveJobList */
	@Deprecated
	public CustomJob setDoneAction(IAction doneAction) {
		this.doneAction=doneAction;
		return this;
	}



	/*
	 * Execution
	 */
	/** Execute jobs with rule (null=immediately) */
	public void schedule(ISchedulingRule rule) {
		Debug.err("SCHEDULING JOB", this);
		setRule(rule);
		schedule();
	}

	public boolean isBusy() {
		Debug.err("JOB "+this+" STATE is "+getStateName());
		int state=getState();
		return state==Job.RUNNING || state==Job.WAITING || state==Job.SLEEPING;
	}

	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void canceling() {
		Debug.err("CANCELING JOB", this);
		canceled=true;
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {

		ILock lock = getJobManager().newLock();

		Chronograph time = new Chronograph();

		try{
			time.start();
			lock.acquire();
			monitor.beginTask("", IProgressMonitor.UNKNOWN);
			Debug.out("STARTED JOB", this);

			canceled=false;
			for(int i=0; i<tasks.size() && !canceled; i++){

				final JobTask jtask=tasks.get(i);
				Debug.out("RUNNING TASK", jtask);
				monitor.subTask(jtask.getName());

				if(jtask instanceof JobUITask){
					//UI task
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							jtask.getAction().run();
						}
					});
				}else{//NORMAL task
					jtask.getAction().run();
				}

			}return Status.OK_STATUS;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (doneAction!=null){
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						doneAction.run();
					}
				});
			}
			time.stop();
			lock.release();
			monitor.done();
			Debug.out("FINISHED JOB ("+time.toString()+")", this);

		}return Status.CANCEL_STATUS;

	}


	public String getStateName() {

		switch(getState()){
		case Job.RUNNING: return "RUNNING";
		case Job.WAITING: return "WAITING";
		case Job.SLEEPING: return "SLEEPING";
		}return "NONE";

	}


}
