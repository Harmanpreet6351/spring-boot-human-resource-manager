package com.hrmgr.responses;

public class JwtResponse {

	String token;

	public String getToken() {
		return token;
	}

	public JwtResponse(String token) {
		this.token = token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
