package com.ftn.bsep.model;

import java.util.Date;

public class CertificateDAO {

	private String cn;
	private String o;
	private String ou;
	private String c;
	private String mail;
	private String surname;
	private String givenname;
	private String uid;

	private String issuer;
	private Date notBefore;
	private Date notAfter;
	private String extension;

	
	public CertificateDAO(String cn, String o, String ou, String c, String mail, String surname, String givenname,
			String uid, String issuer, Date notBefore, Date notAfter, String extension) {
		super();
		this.cn = cn;
		this.o = o;
		this.ou = ou;
		this.c = c;
		this.mail = mail;
		this.surname = surname;
		this.givenname = givenname;
		this.uid = uid;
		this.issuer = issuer;
		this.notBefore = notBefore;
		this.notAfter = notAfter;
		this.extension = extension;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Date getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(Date notBefore) {
		this.notBefore = notBefore;
	}

	public Date getNotAfter() {
		return notAfter;
	}

	public void setNotAfter(Date notAfter) {
		this.notAfter = notAfter;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "Certificate [cn=" + cn + ", o=" + o + ", ou=" + ou + ", c=" + c + ", mail=" + mail + ", surname="
				+ surname + ", givenname=" + givenname + ", uid=" + uid + ", issuer=" + issuer + ", notBefore="
				+ notBefore + ", notAfter=" + notAfter + ", extension=" + extension + "]";
	}

}
