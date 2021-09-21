package com.mobilebanking.model.error;

public class StateError {
	
	private String name;
	
	private String country;
	
	private String status;
	
	private boolean valid;

	private String bulkUploadError;
	
	public String getBulkUploadError() {
		return bulkUploadError;
	}

	public void setBulkUploadError(String bulkUploadError) {
		this.bulkUploadError = bulkUploadError;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

}
