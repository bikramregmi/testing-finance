package com.mobilebanking.model;

public class ProfileAccountSettingDto {

	private long id;
	
	private String registrationAccount;
	
	private String pinResetAccount;
	
	private String renewAccount;
	
	private Status status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getPinResetAccount() {
		return pinResetAccount;
	}

	public void setPinResetAccount(String pinResetAccount) {
		this.pinResetAccount = pinResetAccount;
	}

	public String getRenewAccount() {
		return renewAccount;
	}

	public void setRenewAccount(String renewAccount) {
		this.renewAccount = renewAccount;
	}
	
}
