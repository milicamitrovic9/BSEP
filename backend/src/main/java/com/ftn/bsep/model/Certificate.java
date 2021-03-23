package com.ftn.bsep.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class Certificate extends X509Certificate implements Serializable {

	//TODO Dodati atribute za nas sertifikat

	private  int version=3;
	private String signatureAlgorithm="md5WithRSAEncryption";
	private int serialNumber;
	private int subjectId;
	private String issuer;
	private boolean validity;
	private  Date notBefore;
	private Date notAfter;
	private  String subject;
	private PrivateKey subjectPrivateKeyInfo;
	private PublicKey  subjectPublicKeyInfo;
	private ArrayList<Certificate> issuedCertificates=new ArrayList<>();
	private Certificate CA;
	private ArrayList<Certificate>path=new ArrayList<>();
	private  boolean isRoot=false;
	private Extension extension;
	private String extensionString;


	public Certificate() {
		this.version = 3;
		this.signatureAlgorithm="md5WithRSAEncryption";
		KeyPair kp = generateKeys();
		this.subjectPrivateKeyInfo=kp.getPrivate();
		this.subjectPublicKeyInfo=kp.getPublic();
		this.validity=true;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public boolean isValidity() {
		return validity;
	}

	public void setValidity(boolean validity) {
		this.validity = validity;
	}

	public void setNotBefore(Date notBefore) {
		this.notBefore = notBefore;
	}

	public void setNotAfter(Date notAfter) {
		this.notAfter = notAfter;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public PrivateKey getSubjectPrivateKeyInfo() {
		return subjectPrivateKeyInfo;
	}

	public void setSubjectPrivateKeyInfo(PrivateKey subjectPrivateKeyInfo) {
		this.subjectPrivateKeyInfo = subjectPrivateKeyInfo;
	}

	public PublicKey getSubjectPublicKeyInfo() {
		return subjectPublicKeyInfo;
	}

	public void setSubjectPublicKeyInfo(PublicKey subjectPublicKeyInfo) {
		this.subjectPublicKeyInfo = subjectPublicKeyInfo;
	}

	public ArrayList<Certificate> getIssuedCertificates() {
		return issuedCertificates;
	}

	public void setIssuedCertificates(ArrayList<Certificate> issuedCertificates) {
		this.issuedCertificates = issuedCertificates;
	}

	public Certificate getCA() {
		return CA;
	}

	public void setCA(Certificate CA) {
		this.CA = CA;
	}

	public ArrayList<Certificate> getPath() {
		return path;
	}

	public void setPath(ArrayList<Certificate> path) {
		this.path = path;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean root) {
		isRoot = root;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

	public String getExtensionString() {
		return extensionString;
	}

	public void setExtensionString(String extensionString) {
		this.extensionString = extensionString;
	}

	private KeyPair generateKeys() {
		try {
			//Generator para kljuceva
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

			//Za kreiranje kljuceva neophodno je definisati generator pseudoslucajnih brojeva
			//Generator mora biti bezbedan - da nije jednostavno predvideti koje brojeve ce RNG generisati
			//Koristimo generator zasnovan na SHA1 algoritmu, gde je SUN provajder

			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

			//inicijalizacija generatora, 2048-bitni kljuc
			keyGen.initialize(2048, random);

			//generise par kljuceva koji se sastoji od javnog i privatnog kljuca
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Set<String> getCriticalExtensionOIDs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getExtensionValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getNonCriticalExtensionOIDs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasUnsupportedCriticalExtension() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBasicConstraints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Principal getIssuerDN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean[] getIssuerUniqueID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean[] getKeyUsage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getNotAfter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getNotBefore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getSerialNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSigAlgName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSigAlgOID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getSigAlgParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getSignature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getSubjectDN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean[] getSubjectUniqueID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getTBSCertificate() throws CertificateEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getEncoded() throws CertificateEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicKey getPublicKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verify(PublicKey arg0) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException,
			NoSuchProviderException, SignatureException {
		// TODO Auto-generated method stub

	}

	@Override
	public void verify(PublicKey arg0, String arg1) throws CertificateException, NoSuchAlgorithmException,
			InvalidKeyException, NoSuchProviderException, SignatureException {
		// TODO Auto-generated method stub

	}

}
