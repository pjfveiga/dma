/*******************************************************************************
 * 2008-2016 Public Domain
 * Contributors
 * Marco Lopes (marcolopespt@gmail.com)
 * Sergio Gomes (s.miguelgomes@hotmail.com)
 *******************************************************************************/
package org.dma.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.Collection;
import java.util.GregorianCalendar;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;

import org.dma.java.security.JKSCertificate;

public class PdfFileHandler extends FileHandler {

	public PdfFileHandler(String pathname) {
		super(pathname);
	}

	public PdfFileHandler(File file) {
		super(file);
	}


	/**
	 * <a href=http://itextpdf.sourceforge.net/howtosign.html>
	 * How to sign a PDF using iText</a>
	 */
	public void sign(PrivateKey privateKey, Certificate[] certChain,
			String reason, String location, String contact) throws DocumentException, IOException {

		File output=new File(file+".tmp");

		FileInputStream in=new FileInputStream(file);
		FileOutputStream out=new FileOutputStream(output);

		try{
			PdfStamper stamper=PdfStamper.createSignature(
					new PdfReader(in), out, '\0'); // keep pdf version

			try{
				PdfSignatureAppearance signature=stamper.getSignatureAppearance();
				signature.setCrypto(privateKey, certChain, null,
						PdfSignatureAppearance.WINCER_SIGNED);

				signature.setSignDate(new GregorianCalendar());
				signature.setReason(reason);
				signature.setLocation(location);
				signature.setContact(contact);
				//comment next line to have an invisible signature
				//signature.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);

			}finally{
				stamper.close();
			}

		}finally{
			out.close(); //PdfReader does not close stream
			in.close();
		}

		file.delete();
		output.renameTo(file);

	}


	public void sign(JKSCertificate cert, String reason, String location, String contact)
			throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, DocumentException, IOException {

		sign(cert.getPrivateKey(), cert.getCertificateChain(), reason, location, contact);

	}


	@Deprecated
	public void sign(KeyStore keyStore, String password, String reason, String location, String contact)
			throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, DocumentException, IOException {

		String alias=keyStore.aliases().nextElement();
		PrivateKey privateKey=(PrivateKey)keyStore.getKey(alias, password.toCharArray());

		sign(privateKey, keyStore.getCertificateChain(alias), reason, location, contact);

	}


	public void addScript(String script) throws DocumentException, IOException {

		File output=new File(file+".tmp");
		FileInputStream in=new FileInputStream(file);
		FileOutputStream out=new FileOutputStream(output);

		try{
			PdfStamper stamper=new PdfStamper(new PdfReader(in), out);

			try{
				stamper.addJavaScript(script);

			}finally{
				stamper.close();
			}

		}finally{
			out.close(); //PdfReader does not close stream
			in.close();
		}

		file.delete();
		output.renameTo(file);

	}


	/** Parameterized file will be used as OUTPUT */
	public void merge(Collection<File> files) throws DocumentException, IOException {

		if (files.size()==0) return;

		FileOutputStream out=new FileOutputStream(file);

		try{
			PdfCopyFields copy=new PdfCopyFields(out);

			try{
				for(File file: files){
					PdfReader reader=new PdfReader(file.getAbsolutePath());
					try{
						copy.addDocument(reader);

					}finally{
						reader.close();
					}
				}

			}finally{
				copy.close();
			}

		}finally{
			out.close();
		}

	}


}
