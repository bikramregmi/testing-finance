package com.mobilebanking.model.mobile;

public class ResponseDTO{

	private String status;
	private String code;
	private String message;
	private Object details;

	public String getStatus() {
		return status;
	}
	
	public void setCode(ResponseStatus status) {
		this.code = status.getKey();
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

}
