package com.mobilebanking.model;

public class MobileBankingCancelRequestDTO {
	
	private long id;
	
	private String customerName;
	
	private String mobileNumber;
	
	private String customerUniqueId;
	
	private String requestedDate;
	
	private String bankbranch;
	
	private String bank;

	public long getId() {
		return id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getCustomerUniqueId() {
		return customerUniqueId;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public String getBankbranch() {
		return bankbranch;
	}

	public String getBank() {
		return bank;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setCustomerUniqueId(String customerUniqueId) {
		this.customerUniqueId = customerUniqueId;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public void setBankbranch(String bankbranch) {
		this.bankbranch = bankbranch;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

}
