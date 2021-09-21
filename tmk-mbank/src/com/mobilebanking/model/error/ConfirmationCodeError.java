package com.mobilebanking.model.error;



public class ConfirmationCodeError {
	
	private String generator;
	
	private String generatedTo;
	
	private boolean valid;
	
	private String casType;
	
	private String cicoAmount;
	
	private String code;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getGeneratedTo() {
		return generatedTo;
	}

	public void setGeneratedTo(String generatedTo) {
		this.generatedTo = generatedTo;
	}



	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getCasType() {
		return casType;
	}

	public void setCasType(String casType) {
		this.casType = casType;
	}

	public String getCicoAmount() {
		return cicoAmount;
	}

	public void setCicoAmount(String cicoAmount) {
		this.cicoAmount = cicoAmount;
	}

	
	

}
