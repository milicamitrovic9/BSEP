package com.ftn.bsep.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.cert.X509Certificate;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {

	public PDFGenerator() {
		
	}

	public void createCertificatePDF(X509Certificate cert) throws DocumentException, FileNotFoundException {

		String certName = cert.getSubjectDN().getName();
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("certificates/" + certName + ".pdf"));

		document.open();
		document.add(
				new Paragraph(" ================================== Sertifikat =================================="));
		document.add(new Paragraph(
				"--------------------------------------------------------------------------------------------------------------"));
		document.add(new Paragraph(cert.toString()));
		document.add(new Paragraph(
				"--------------------------------------------------------------------------------------------------------------"));
		document.close();

	}
}
