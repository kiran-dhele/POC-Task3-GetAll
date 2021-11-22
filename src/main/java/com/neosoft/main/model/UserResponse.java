package com.neosoft.main.model;

public class UserResponse {

	private final String jwtToken;

	
	public UserResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}


	public String getJwtToken() {
		return jwtToken;
	}
	
	
}
