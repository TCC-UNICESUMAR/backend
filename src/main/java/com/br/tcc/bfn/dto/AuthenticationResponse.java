package com.br.tcc.bfn.dto;

public class AuthenticationResponse {

	private String acessToken;

	private String refreshToken;

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String acessToken, String refreshToken) {
		super();
		this.acessToken = acessToken;
		this.refreshToken = refreshToken;
	}

	public String getAcessToken() {
		return acessToken;
	}

	public void setAcessToken(String acessToken) {
		this.acessToken = acessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
