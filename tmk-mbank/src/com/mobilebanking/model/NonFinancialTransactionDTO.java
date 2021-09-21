package com.mobilebanking.model;

public class NonFinancialTransactionDTO {

	private String customerName;
	
	private String mobileNumber;
	
	private String bankCode;
	
	private String branchCode;
	
	private String transactionIdentifier;
	
	private String date;
	
	private String accountNumber;
	
	private IsoStatus status;
	
	private NonFinancialTransactionType transactionType;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public IsoStatus getStatus() {
		return status;
	}

	public void setStatus(IsoStatus status) {
		this.status = status;
	}

	public NonFinancialTransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(NonFinancialTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
}
