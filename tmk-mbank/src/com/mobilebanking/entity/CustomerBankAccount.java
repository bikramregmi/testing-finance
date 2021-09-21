package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.AccountMode;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="customerBankAccount")
public class CustomerBankAccount extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private BankBranch branch;
	
	@Column(nullable=false)
	private String accountNumber;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Customer customer;
	
	@Column
	private AccountMode accountMode;
	
	@Column(nullable=true)
	private Status status; 
	
	@Column(nullable=true)
	private boolean isPrimary = false;
	
	public BankBranch getBranch() {
		return branch;
	}

	public void setBranch(BankBranch branch) {
		this.branch = branch;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
}
