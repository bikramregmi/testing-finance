package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.ChequeBlockRequestStatus;

@Entity
@Table(name = "chequeBlokRequest")
public class ChequeBlockRequest extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	private String chequeNumber;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private CustomerBankAccount customerAccount;
	
	private ChequeBlockRequestStatus status;

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public CustomerBankAccount getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerBankAccount customerAccount) {
		this.customerAccount = customerAccount;
	}

	public ChequeBlockRequestStatus getStatus() {
		return status;
	}

	public void setStatus(ChequeBlockRequestStatus status) {
		this.status = status;
	}
	
}
