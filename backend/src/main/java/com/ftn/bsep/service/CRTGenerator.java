package com.ftn.bsep.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftn.bsep.controller.LoginController;

public class CRTGenerator {

    private static Logger logger = LoggerFactory.getLogger(CRTGenerator.class);

	public CRTGenerator() {
		
	}

	public void createCertificateCRT(X509Certificate cert) throws CertificateEncodingException, IOException {

		String certName = cert.getSubjectDN().getName(); 
        byte[] buf = cert.getEncoded();

        FileOutputStream os = new FileOutputStream("certificates/" + certName + ".crt");
        os.write(buf);
        os.close();
        logger.info("Sertifikat downloaded");
	}
}
