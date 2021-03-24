package com.ftn.bsep.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ftn.bsep.model.Certificate;
import com.ftn.bsep.model.CertificateDAO;
import com.itextpdf.text.DocumentException;


	public interface CertificateService {

		Collection<Certificate> findAll();
		boolean createCA(CertificateDAO certificate) throws Exception;
		Certificate createRoot(CertificateDAO certificate) throws Exception;
		Certificate revoke(Certificate certificate) throws Exception;
		void addToKeyStore(Certificate certificate,String keyStoreFile)throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException;
		List<String> naziviSertifikata();
		List<String> naziviEESertifikata();
		List<String> naziviPovucenihSertifikata();
		ArrayList<CertificateDAO> vratiSveCA()throws KeyStoreException, NoSuchProviderException;
		ArrayList<CertificateDAO>vratiSveEE()throws KeyStoreException, NoSuchProviderException;

		void skiniCa(String uid) throws FileNotFoundException, DocumentException;

		void skiniEE(String uid) throws FileNotFoundException, DocumentException;

		void povuciCertificateCA(String uid);

		void povuciCertificateEE(String uid);

		ArrayList<CertificateDAO> vratiSvePovucene() throws NoSuchProviderException, KeyStoreException;

		boolean validacijaCA(String uid);
		boolean validacijaEE(String uid);
		public boolean validacijaSvi(String izabraniAliasSvi);

	}
