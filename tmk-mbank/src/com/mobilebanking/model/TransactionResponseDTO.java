package com.mobilebanking.model;

import com.mobilebanking.model.mobile.ResponseStatus;

public class TransactionResponseDTO {

	private String status ;
	
	private ResponseStatus responseStatus;
	
	private String message;
	
	private String date ;
	
	private String transactionIdentifier;
	
	private String serviceTo;

	private String code;
	
	private String pinNo;
	
	private String serialNo;
	
	private String airlinesPdfUrl;
	
	//fields added by amrit 26-04-2018
	private String bankName;
	private String branchName;
	private String icon;
	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	
	
	//end fields  added 26-04-2018
	
	public void setCode(ResponseStatus status) {
		this.code = status.getKey();
	}
	
	
	public String getCode() {
		return code;
	}


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

	public String getServiceTo() {
		return serviceTo;
	}

	public void setServiceTo(String serviceTo) {
		this.serviceTo = serviceTo;
	}


	public String getPinNo() {
		return pinNo;
	}


	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}


	public String getSerialNo() {
		return serialNo;
	}


	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public String getAirlinesPdfUrl() {
		return airlinesPdfUrl;
	}


	public void setAirlinesPdfUrl(String airlinesPdfUrl) {
		this.airlinesPdfUrl = airlinesPdfUrl;
	}
	
}
