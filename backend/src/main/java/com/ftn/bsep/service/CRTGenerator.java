package com.ftn.bsep.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public class CRTGenerator {

	public CRTGenerator() {
		
	}

	public void createCertificateCRT(X509Certificate cert) throws CertificateEncodingException, IOException {

		String certName = cert.getSubjectDN().getName();
        byte[] buf = cert.getEncoded();

        FileOutputStream os = new FileOutputStream("dataCERT/" + certName + ".crt");
        os.write(buf);
        os.close();
	}
}
