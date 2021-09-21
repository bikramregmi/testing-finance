package com.mobilebanking.model;



public class ConfirmationCodeDTO {
	
	
	long id;
	
	private String generator;
	
	private String generatedTo;
	
	private String valid;
	
	private ConfirmationCodeType casType;
	
	private double cicoAmount;
	
	private String code;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	

	public ConfirmationCodeType getCasType() {
		return casType;
	}

	public void setCasType(ConfirmationCodeType casType) {
		this.casType = casType;
	}

	public double getCicoAmount() {
		return cicoAmount;
	}

	public void setCicoAmount(double cicoAmount) {
		this.cicoAmount = cicoAmount;
	}

	
	
	
	

}
