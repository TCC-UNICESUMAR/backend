package com.br.tcc.bfn.dtos;

public class RegisterRequest {

	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String cnpjOrCpf;
	private String profileImageId;


	public RegisterRequest(String firstname, String lastname, String email, String password, String cnpjOrCpf) {
	}

	public RegisterRequest() {
		super();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCnpjOrCpf() {
		return cnpjOrCpf;
	}

	public void setCnpjOrCpf(String cnpjOrCpf) {
		this.cnpjOrCpf = cnpjOrCpf;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}
}
