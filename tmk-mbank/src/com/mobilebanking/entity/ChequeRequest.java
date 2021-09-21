package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.ChequeRequestStatus;

@Entity
@Table(name = "chequeRequest")
public class ChequeRequest extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	private CustomerBankAccount customerAccount;
	
	private Integer chequeLeaves;
	
	private ChequeRequestStatus chequeRequestStatus;

	public CustomerBankAccount getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerBankAccount customerAccount) {
		this.customerAccount = customerAccount;
	}

	public Integer getChequeLeaves() {
		return chequeLeaves;
	}

	public void setChequeLeaves(Integer chequeLeaves) {
		this.chequeLeaves = chequeLeaves;
	}

	public ChequeRequestStatus getChequeRequestStatus() {
		return chequeRequestStatus;
	}

	public void setChequeRequestStatus(ChequeRequestStatus chequeRequestStatus) {
		this.chequeRequestStatus = chequeRequestStatus;
	}
	
}
