package com.mobilebanking.model;

public class LedgerDTO {
	
	private long id;
	
	private String transactionId;
	 
	private String date;
	
	private String fromAccount;
	
	private String toAccount;
	
	private double  amount;
	
	private double  fromBalance;
	
	private double  toBalance;
		
	private LedgerStatus transactionStatus;
	
	private LedgerType transactionType;
	
	private String remarks;
	
	private long externalRefId;
	
	private long parentId;
	
	private String bank;

	private String uploadedBy;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getFromBalance() {
		return fromBalance;
	}
	public void setFromBalance(double fromBalance) {
		this.fromBalance = fromBalance;
	}
	public double getToBalance() {
		return toBalance;
	}
	public void setToBalance(double toBalance) {
		this.toBalance = toBalance;
	}
	public LedgerStatus getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(LedgerStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public LedgerType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(LedgerType transactionType) {
		this.transactionType = transactionType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public long getExternalRefId() {
		return externalRefId;
	}
	public void setExternalRefId(long externalRefId) {
		this.externalRefId = externalRefId;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
}
