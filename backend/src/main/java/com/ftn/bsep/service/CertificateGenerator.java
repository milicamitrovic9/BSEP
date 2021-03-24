package com.ftn.bsep.service;

import java.math.BigInteger;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftn.bsep.model.IssuerData;
import com.ftn.bsep.model.SubjectData;

public class CertificateGenerator {

	Logger logger = LoggerFactory.getLogger(CertificateGenerator.class);

	public CertificateGenerator() {

	}

	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, String extension) {

		Security.addProvider(new BouncyCastleProvider());

		try {

			/**
			 * Klasa za generisanje ne moze da primi direktno private key zato imamo builder sa enkripcijom 
			 * Sadrzi private key Issuer-a sertifikta i koristi se za potpisivanje sertifikata 
			 * Parametar je algoritam koji se koristi za potpisavanje sertifikata
			 */
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider(BouncyCastleProvider.PROVIDER_NAME);

			/**
			 * Kreiramo objekat koji sadrzi private key koji sluzi za potpisavanje
			 * sertifikata
			 */
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			/**
			 * Postavljaju se podaci za generisanje sertifiakta
			 */
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()), subjectData.getStartDate(), subjectData.getEndDate(),
					subjectData.getX500name(), subjectData.getPublicKey());

			/**
			 * Dodavanje ekstenzije Ako je intermediate
			 */
			if (extension.contains("CA")) {
				certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
				certGen.addExtension(Extension.keyUsage, true,
						new KeyUsage(KeyUsage.nonRepudiation | KeyUsage.keyCertSign | KeyUsage.cRLSign));
			} else {
				certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
				/**
				 * Potpisivanje dokumenta
				 */
				if (extension.contains("signing")) {
					certGen.addExtension(Extension.keyUsage, true,
							new KeyUsage(KeyUsage.nonRepudiation | KeyUsage.digitalSignature));
				} else if (extension.contains("encipherment")) {
					/**
					 * Data and key encription
					 */
					certGen.addExtension(Extension.keyUsage, true, 
							new KeyUsage(KeyUsage.keyEncipherment | KeyUsage.dataEncipherment | KeyUsage.keyAgreement | KeyUsage.nonRepudiation));
				}
			}

			/**
			 * Generisemo sertifikat
			 */
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			/**
			 * Builder generise sertifikat kao objekat klase X509CertificateHolder Nakon
			 * toga je potrebno konvertovati u konverter, za sta se koristi
			 * JcaX509CertificateConverter
			 */
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			/**
			 * Konvertujemo u X509Certificate sertifikat
			 */
			return certConverter.getCertificate(certHolder);

		} catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException
				| CertIOException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
