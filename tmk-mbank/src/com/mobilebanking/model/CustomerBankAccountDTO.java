/**
 * 
 */
package com.mobilebanking.model;

/**
 * @author bibek
 *
 */
public class CustomerBankAccountDTO {
	
	private long id;
	
	private String branch;
	
	private String accountNumber;
	
	private String customer;
	
	private String bank;
	
	private AccountMode accountMode;
	
	private Status status;
	
	private long branchId;
	
	private String branchCode;
	
	private Long bankId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public AccountMode getAccountMode() {
		return accountMode;
	}

	public void setAccountMode(AccountMode accountMode) {
		this.accountMode = accountMode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getBranchId() {
		return branchId;
	}

	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

}
