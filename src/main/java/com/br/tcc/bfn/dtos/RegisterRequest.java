package com.br.tcc.bfn.dtos;

public class RegisterRequest {

	private String name;
	private String email;
	private String password;
	private String phone;
	private String cnpjOrCpf;


	public RegisterRequest(String firstname, String lastname, String email, String password, String cnpjOrCpf) {
	}

	public RegisterRequest() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
