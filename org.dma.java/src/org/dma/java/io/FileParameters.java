/*******************************************************************************
 * 2008-2020 Public Domain
 * Contributors
 * Marco Lopes (marcolopespt@gmail.com)
 *******************************************************************************/
package org.dma.java.io;

import java.io.File;

public class FileParameters {

	public final String prefix;
	public final String suffix;
	public final File folder;
	public final String filename;
	public final String wildcards;

	/** folder = "." (always reflects current system folder)*/
	public FileParameters(String prefix, String suffix) {
		this(prefix, suffix, ".");
	}

	/** suffix CAN be null, or may or may not start with DOT (.) */
	public FileParameters(String prefix, String suffix, String foldername) {
		this(prefix, suffix, new File(foldername));
	}

	/** suffix CAN be null, or may or may not start with DOT (.) */
	public FileParameters(String prefix, String suffix, File folder) {
		this.prefix=prefix;
		this.suffix=suffix==null || suffix.startsWith(".") ? suffix : "."+suffix;
		this.folder=folder;
		this.filename=prefix + (suffix==null ? "" : this.suffix);
		this.wildcards="*" + (suffix==null ? "" : this.suffix);
	}

	public FileParameters cloneCopy(String foldername) {
		return cloneCopy(new File(foldername));
	}

	public FileParameters cloneCopy(File folder) {
		return new FileParameters(prefix, suffix, folder);
	}


	/** @see File#createTempFile(String, String, File) */
	public File createTempFile() {
		try{
			File file=File.createTempFile(prefix+"-", suffix, folder);
			file.deleteOnExit();
			return file;

		}catch(Exception e){
			System.err.println(e);
		}return null;
	}


	/** @see #createTempFile() */
	public File createTempFile(byte[] bytes) {

		File file=createTempFile();
		new ByteFileHandler(file).write(bytes);

		return file;

	}


	/** @see File#exists() */
	public boolean exists() {
		return toFile().exists();
	}


	public File toFile() {
		return new File(toString());
	}


	/** path + filename */
	@Override
	public String toString() {
		return folder.getAbsolutePath() + File.separator + filename;
	}


}