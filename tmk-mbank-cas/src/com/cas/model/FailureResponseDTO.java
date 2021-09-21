package com.cas.model;


public class FailureResponseDTO {

	private ResponseStatus status;
	private String code;
	private String message;

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
		code = status.getValue();
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

}
