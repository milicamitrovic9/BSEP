package com.ftn.bsep.service.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.bsep.model.Admin;
import com.ftn.bsep.model.AliasCA;
import com.ftn.bsep.model.IssuerData;
import com.ftn.bsep.model.SubjectData;
import com.ftn.bsep.model.UserDTO;
import com.ftn.bsep.repository.AdminRepository;
import com.ftn.bsep.repository.AliasCARepository;
import com.ftn.bsep.service.AdminService;
import com.ftn.bsep.service.CRTGenerator;
import com.ftn.bsep.service.CertificateGenerator;
import com.ftn.bsep.service.KeyStoreReader;
import com.ftn.bsep.service.KeyStoreWriter;
import com.ftn.bsep.util.Validator;

@Service
public class AdminServiceImpl implements AdminService {

	private static final String KEY_STORE_FOLDER = "keyStore/";

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AliasCARepository aliasCARepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	//prilikom registracije admina kreiramo root sertifikat
	@Override
	public Admin create(UserDTO userDTO) throws Exception {

		Validator.validateInputs(userDTO.getName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getPassword());

		Admin adminModel = new Admin();
		String password = passwordEncoder.encode(userDTO.getPassword());
		adminModel.setPassword(password);
		adminModel.setName(userDTO.getName());
		adminModel.setLastName( userDTO.getLastName());
		adminModel.setEmail(userDTO.getEmail());
		adminModel.setRootCreated(true);

		adminModel = adminRepository.save(adminModel);

		/**
		 * Kreiranje novog para kljuceva za root
		 */
		KeyPair keyPairIssuer = generateKeyPair();

		/**
		 * Kreiranje root sertifikata
		 */
		X509Certificate novi = createRoot(keyPairIssuer);

		/**
		 * Kreiranje keyStore file za upis CA sertifikata
		 */
		KeyStoreWriter ksw = new KeyStoreWriter();
		char[] pass = new char[3];
		pass[0] = '1';
		pass[1] = '2';
		pass[2] = '3';

		ksw.loadKeyStore(null, pass);
		/**
		 * Upis sertifikata
		 */
		ksw.writeCertificate("CARoot", novi);
		/**
		 * Sacuvaj CA sertifikat u fajl
		 */
		ksw.saveKeyStore(KEY_STORE_FOLDER + "keyStoreCA.jks", pass);

		/**
		 * Kreiranje keyStore file za upis privatnog kljuca CA sertifikata
		 */
		KeyStoreWriter ksw1 = new KeyStoreWriter();
		char[] pass1 = new char[3];
		pass1[0] = '1';
		pass1[1] = '3';
		pass1[2] = '3';

		ksw1.loadKeyStore(null, pass1);
		/**
		 * Upis kljuca
		 */
		ksw1.write("CARoot", keyPairIssuer.getPrivate(), pass, novi);
		/**
		 * Cuvamo privremene kljuceve CA sertifikata u fajlu
		 */
		ksw1.saveKeyStore(KEY_STORE_FOLDER + "keyStoreCAPrivatni.jks", pass);

		/**
		 * Kreiranje end-entity key store files
		 */
		createEndEntityKSFiles();

		/**
		 * Cuvanje spiska aliasa u bazi
		 */
		aliasCARepository.save(new AliasCA("CARoot", "CARoot"));

		/**
		 * Citanje i ispis kreiranog sertifikata na kozolu
		 */
		KeyStoreReader ksr = new KeyStoreReader();
		Certificate certificate = ksr.readCertificate(KEY_STORE_FOLDER + "keyStoreCA.jks", "123", "CARoot");
		System.out.println(certificate);

		return adminModel;
	}

	private void createEndEntityKSFiles() {

		/**
		 * Kreiranje keyStore file za upis EE sertifikata
		 */
		KeyStoreWriter ksw = new KeyStoreWriter();
		char[] pass = new char[3];
		pass[0] = '1';
		pass[1] = '2';
		pass[2] = '3';

		ksw.loadKeyStore(null, pass);
		/**
		 * EE sertifikate cuvamo u ovom file-u
		 */
		ksw.saveKeyStore(KEY_STORE_FOLDER + "keyStoreEE.jks", pass);

		/**
		 * Kreiranje keyStore file za upis privatnog kljuca EE sertifikata
		 */
		KeyStoreWriter ksw1 = new KeyStoreWriter();
		char[] pass1 = new char[3];
		pass1[0] = '1';
		pass1[1] = '3';
		pass1[2] = '3';

		ksw1.loadKeyStore(null, pass1);
		/**
		 * Privremene kljuceve EE sertifikata cuvamo u ovom file-u
		 */
		ksw1.saveKeyStore(KEY_STORE_FOLDER + "keyStoreEEPrivatni.jks", pass);

	}

	private X509Certificate createRoot(KeyPair keyPair)
			throws NoSuchProviderException, CertificateException, NoSuchAlgorithmException, InvalidKeyException,
			SignatureException, IOException {

		/**
		 * Kreiranje podataka o subjektu i issueru
		 */
		SubjectData subjectData = generateSubjectData(keyPair);
		IssuerData issuerData = generateIssuerData(keyPair.getPrivate());

		/**
		 * Generise se sertifikat za subjekta, potpisan od strane issuer-a
		 */
		CertificateGenerator cg = new CertificateGenerator();
		X509Certificate cert = cg.generateCertificate(subjectData, issuerData, "CA");

		System.out.println("\n===== Podaci o izdavacu sertifikata =====");
		System.out.println(cert.getIssuerX500Principal().getName());
		System.out.println("\n===== Podaci o vlasniku sertifikata =====");
		System.out.println(cert.getSubjectX500Principal().getName());
		System.out.println("\n===== Sertifikat =====");
		System.out.println("-------------------------------------------------------");
		System.out.println(cert);
		System.out.println("-------------------------------------------------------");

		/**
		 * Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom
		 * javnog kljuca izdavaoca
		 */
		cert.verify(keyPair.getPublic());
		System.out.println("\nValidacija uspesna :)");

		CRTGenerator crtGen = new CRTGenerator();
		crtGen.createCertificateCRT(cert);

		System.out.println("Non critical: " + cert.getNonCriticalExtensionOIDs());

		return cert;

	}

	private IssuerData generateIssuerData(PrivateKey issuerKey) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, "SOFTWARE ROOT");
		builder.addRDN(BCStyle.SURNAME, "Tim");
		builder.addRDN(BCStyle.GIVENNAME, "3XM");
		builder.addRDN(BCStyle.O, "UNS-FTN");
		builder.addRDN(BCStyle.OU, "Katedra za informatiku");
		builder.addRDN(BCStyle.C, "RS");
		builder.addRDN(BCStyle.E, "tim3XM@uns.ac.rs");
		/**
		 *  UID (USER ID) je ID korisnika
		 */
		builder.addRDN(BCStyle.UID, UUID.randomUUID().toString());

		/** 
		 * Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
		 * privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
		 * podatke o vlasniku sertifikata koji izdaje nov sertifikat
		 */
		return new IssuerData(issuerKey, builder.build());
	}

	private SubjectData generateSubjectData(KeyPair keyPairSubject) {
		try {
			/**
			 * Datumi od kad do kad vazi sertifikat
			 */
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = iso8601Formater.parse("2017-12-31");
			Date endDate = iso8601Formater.parse("2023-12-31");

			/**
			 * Serijski broj sertifikata
			 */
			String sn = "1";
			/**
			 * Klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o
			 * vlasniku
			 */
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
			builder.addRDN(BCStyle.CN, "SOFTWARE ROOT");
			builder.addRDN(BCStyle.SURNAME, "Tim");
			builder.addRDN(BCStyle.GIVENNAME, "3XM");
			builder.addRDN(BCStyle.O, "UNS-FTN");
			builder.addRDN(BCStyle.OU, "Katedra za informatiku");
			builder.addRDN(BCStyle.C, "RS");
			builder.addRDN(BCStyle.E, "tim3XM@uns.ac.rs");
			/**
			 *  UID (USER ID) je ID korisnika
			 */
			builder.addRDN(BCStyle.UID, UUID.randomUUID().toString());

			/**
			 *  Kreiraju se podaci za sertifikat, sto ukljucuje:
			 *  javni kljuc koji se vezuje za sertifikat
			 *  podatke o vlasniku
			 *  serijski broj sertifikata
			 *  od kada do kada vazi sertifikat
			 */
			return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
		} catch (ParseException e) {
			logger.error(e.toString());
		}
		return null;
	}

	private KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			logger.error(e.toString());
		}
		return null;
	}
	
	@Override
	public Collection<Admin> findAll() {
		return adminRepository.findAll();
	}

	@Override
	public Admin findById(Long id) {
		return adminRepository.findById(id).orElseGet(null);
	}

	@Override
	public Admin findByEmail(String email) {
		return adminRepository.findByEmail(email);
	}
	
	@Override
	public Admin update(Admin admin) throws Exception {
		Admin adminForUpdate = findById(admin.getId());
		adminForUpdate.copyValues(admin);
		adminForUpdate = adminRepository.save(adminForUpdate);
		return adminForUpdate;
	}

	@Override
	public void delete(Long id) {
		adminRepository.deleteById(id);
	}

	@Override
	public void deleteByEmail(String email) {
		Admin adminForDelete = findByEmail(email);
		adminRepository.deleteById(adminForDelete.getId());
	}


}
