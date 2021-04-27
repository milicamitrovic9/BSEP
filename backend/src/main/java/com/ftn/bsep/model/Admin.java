package com.ftn.bsep.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin implements Serializable {

	private static final long serialVersionUID = 5183330603537586370L;

	public Admin() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name", nullable = false)
	private String name;

	@Column(name = "LastName", nullable = false)
	private String lastName;

	@Column(name = "Email", nullable = false)
	private String email;

	@Column(name = "Password", nullable = false)
	private byte[] password;

	@Column(name = "RootCreated", nullable = false)
	private boolean rootCreated = false;

	public Admin(String name, String lastName, String email, byte[] password) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.rootCreated = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public boolean isRootCreated() {
		return rootCreated;
	}

	public void setRootCreated(boolean rootCreated) {
		this.rootCreated = rootCreated;
	}

	public void copyValues(Admin admin) {
		this.name = admin.getName();
		this.lastName = admin.getLastName();
		this.email = admin.getEmail();
		this.password = admin.getPassword();
		this.rootCreated = admin.isRootCreated();

	}
}