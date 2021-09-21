package com.mobilebanking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobilebanking.model.mobile.ResponseStatus;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponseDTO {
	
	String Status;
	
	ResponseStatus responseStatus;
	
	String message;
	
	String refresh;
	
	Object detail;
	
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	
}
