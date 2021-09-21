package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

@Entity
@Table(name = "customerBenificary")
public class CustomerBenificiary extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch= FetchType.EAGER)
	private Customer sender;
	
	@ManyToOne
	private Bank bank;
	
	@ManyToOne 
	private BankBranch bankBranch;
	
	@Column(nullable=false)
	private String accountNumber;
	
	@Column(nullable=false)
	private String reciever;
	
	public Status status ;
	
	public Customer getSender() {
		return sender;
	}

	public void setSender(Customer sender) {
		this.sender = sender;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	
}
