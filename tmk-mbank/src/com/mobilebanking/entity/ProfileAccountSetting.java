package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

@Entity
@Table(name = "profileAccountSetting")
public class ProfileAccountSetting extends AbstractEntity<Long>{

	private static final long serialVersionUID = -2166439104541820274L;
	
	private String registrationAccount;
	
	private String pinRestAccount;
	
	private String renewAccount;
	
	private Status status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;

	public String getRegistrationAccount() {
		return registrationAccount;
	}

	public void setRegistrationAccount(String registrationAccount) {
		this.registrationAccount = registrationAccount;
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

	public String getPinRestAccount() {
		return pinRestAccount;
	}

	public void setPinRestAccount(String pinRestAccount) {
		this.pinRestAccount = pinRestAccount;
	}

	public String getRenewAccount() {
		return renewAccount;
	}

	public void setRenewAccount(String renewAccount) {
		this.renewAccount = renewAccount;
	}
	
}