package com.mobilebanking.model.error;


public class WebServiceError {
	
	private boolean valid;

	private String api_user;
	
	private String api_password;

	private String access_key;

	private String secret_key;
	
	

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getApi_user() {
		return api_user;
	}

	public void setApi_user(String api_user) {
		this.api_user = api_user;
	}

	public String getApi_password() {
		return api_password;
	}

	public void setApi_password(String api_password) {
		this.api_password = api_password;
	}

	public String getAccess_key() {
		return access_key;
	}

	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	
	
}
