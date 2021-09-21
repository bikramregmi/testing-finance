package com.mobilebanking.model;

public class ChequeRequestDTO {

	private long id;
	
	private String accountNumber;
	
	private Integer chequeLeaves;
	
	private String mPin;
	
	private ChequeRequestStatus status;
	
	private String customerName ;
	
	private String mobileNumber;
	
	private String date;
	
	public String getmPin() {
		return mPin;
	}

	public void setmPin(String mPin) {
		this.mPin = mPin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getChequeLeaves() {
		return chequeLeaves;
	}

	public void setChequeLeaves(Integer chequeLeaves) {
		this.chequeLeaves = chequeLeaves;
	}

	public ChequeRequestStatus getStatus() {
		return status;
	}

	public void setStatus(ChequeRequestStatus status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
