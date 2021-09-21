package com.mobilebanking.model;

public class TransactionResponse {
	
	private Double amount;
	
	private String service;
	
	private String serviceTo;
	
	private String accountNumber;
	
	private String transactionIdentifier;
	
	private String date;
	
	private TransactionStatus status;

	public Double getAmount() {
		return amount;
	}

	public String getService() {
		return service;
	}

	public String getServiceTo() {
		return serviceTo;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setServiceTo(String serviceTo) {
		this.serviceTo = serviceTo;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

}
