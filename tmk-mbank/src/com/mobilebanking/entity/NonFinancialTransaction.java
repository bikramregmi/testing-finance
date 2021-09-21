package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.IsoStatus;
import com.mobilebanking.model.NonFinancialTransactionType;

@Entity
@Table(name = "nonfinancialtransaction")
public class NonFinancialTransaction extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch =FetchType.EAGER )
	private Bank bank;
	
	@ManyToOne(fetch =FetchType.EAGER )
	private BankBranch bankBranch;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;

	private String accountNumber;
	
	private IsoStatus status;
	
	private NonFinancialTransactionType transactionType;
	
	private String transactionId;
	
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
