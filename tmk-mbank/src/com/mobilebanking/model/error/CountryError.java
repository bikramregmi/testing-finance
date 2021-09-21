package com.mobilebanking.model.error;


public class CountryError {
	
	
	private String name;
	
	private String isoTwo;
	
	private String isoThree;
	
	private String dialCode;
	
	private String currency;
	
	private String status;
	
	private String currencyCode;
	
	private boolean valid;
	
	private String bulkError;
	
	public String getBulkError() {
		return bulkError;
	}

	public void setBulkError(String bulkError) {
		this.bulkError = bulkError;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoTwo() {
		return isoTwo;
	}

	public void setIsoTwo(String isoTwo) {
		this.isoTwo = isoTwo;
	}

	public String getIsoThree() {
		return isoThree;
	}

	public void setIsoThree(String isoThree) {
		this.isoThree = isoThree;
	}

	public String getDialCode() {
		return dialCode;
	}

	public void setDialCode(String dialCode) {
		this.dialCode = dialCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	

}
