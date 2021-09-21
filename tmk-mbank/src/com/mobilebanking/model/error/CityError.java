package com.mobilebanking.model.error;


public class CityError {
	
	private String name;
	
	private String state;
	
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
