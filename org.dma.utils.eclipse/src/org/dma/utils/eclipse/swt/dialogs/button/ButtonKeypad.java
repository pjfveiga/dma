/*******************************************************************************
 * 2008-2012 Public Domain
 * Contributors
 * Marco Lopes (marcolopes@netc.pt)
 *******************************************************************************/
package org.dma.utils.eclipse.swt.dialogs.button;

import org.dma.utils.eclipse.swt.SWTUtils;
import org.dma.utils.eclipse.swt.custom.CustomShell;
import org.dma.utils.eclipse.swt.image.SWTImageUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public abstract class ButtonKeypad extends CustomShell {

	public abstract void done(Double value);

	private final static String RETURN = "OK";
	private final static String[] keys = new String[]{
		"7", "8", "9",
		"4", "5", "6",
		"1", "2", "3",
		"0", ".", RETURN };

	private Text text;
	private String value = "0";
	private final int pixels;

	public ButtonKeypad(int pixels){
		super(Display.getCurrent().getActiveShell(), SWT.SHEET);

		this.pixels=pixels;

		createCompositeValue();
		createCompositeButtons();

		setGridLayout();
		pack();
		setCenteredLocation();
	}

	public ButtonKeypad(){
		this(Display.getCurrent().getClientArea().height / 10);
	}


	/*
	 * Composites
	 */
	private void createCompositeValue() {

		Composite composite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		text = new Text(composite, SWT.BORDER | SWT.RIGHT);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.setForeground(SWTImageUtils.getColor(SWT.COLOR_RED));
		text.setEditable(false);
		text.setText(value);
		text.setFont(SWTUtils.createFont(text, 20));

	}


	private void createCompositeButtons() {

		Composite composite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		for(int i=0; i<keys.length; i++){
			Button button=new Button(composite, SWT.PUSH);
			button.setLayoutData(new GridData(pixels,pixels));
			button.setFont(SWTUtils.createFont(button, 20));
			button.setText(keys[i]);
			button.setData(keys[i]);

			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					String c=(String)e.widget.getData();
					if (c.equals(RETURN)){
						close();
						done(getValue());
					}else{
						if (value.equals("0")){
							value=c;
						}else if (!c.equals(".") || !value.contains(".")){
							value+=c;
						}
						text.setText(value);
					}
				}
			});
		}

	}



	/*
	 * Getters and setters
	 */
	public Double getValue() {
		return Double.valueOf(value);
	}


}
