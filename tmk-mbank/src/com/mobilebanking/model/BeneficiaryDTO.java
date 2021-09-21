package com.mobilebanking.model;

public class BeneficiaryDTO {

	private long id;
	
	private String name;
	
	private String accountNumber;
	
	private String bankName;
	
	private String bankBranchName;
	
	private Long bankId;
	
	private Long bankBranchId;
	
	private boolean beneficiaryFlag;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isBeneficiaryFlag() {
		return beneficiaryFlag;
	}

	public void setBeneficiaryFlag(boolean beneficiaryFlag) {
		this.beneficiaryFlag = beneficiaryFlag;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}


	public Long getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(Long bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	
}
