package com.mobilebanking.model.mobile;

public class AirlinesResponseDTO {

	private String status ;
	
	private ResponseStatus responseStatus;
	
	private String message;
	
	private String date ;
	
	private String transactionIdentifier;
	
	private String airlinesPdfUrl;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

	public String getAirlinesPdfUrl() {
		return airlinesPdfUrl;
	}

	public void setAirlinesPdfUrl(String airlinesPdfUrl) {
		this.airlinesPdfUrl = airlinesPdfUrl;
	}

}
