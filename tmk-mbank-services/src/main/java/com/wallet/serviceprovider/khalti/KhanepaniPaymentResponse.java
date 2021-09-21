package com.wallet.serviceprovider.khalti;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KhanepaniPaymentResponse {
	
	private String status;
	
	private Map<String, String> detail;
	
	private String message;
	
	private String id;
	
	private Object extra_data;
	
	private Object error_data;
	
	private String state;
	
	private String due_amount;
	
	private String error_code;
	
	private Object details;
	
	private String error;

	public String getStatus() {
		return status;
	}

	public Map<String, String> getDetail() {
		return detail;
	}

	public String getMessage() {
		return message;
	}

	public String getId() {
		return id;
	}

	public Object getExtra_data() {
		return extra_data;
	}

	public Object getError_data() {
		return error_data;
	}

	public String getState() {
		return state;
	}

	public String getDue_amount() {
		return due_amount;
	}

	public String getError_code() {
		return error_code;
	}

	public Object getDetails() {
		return details;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDetail(Map<String, String> detail) {
		this.detail = detail;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setExtra_data(Object extra_data) {
		this.extra_data = extra_data;
	}

	public void setError_data(Object error_data) {
		this.error_data = error_data;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setDue_amount(String due_amount) {
		this.due_amount = due_amount;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
