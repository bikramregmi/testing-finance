package com.mobilebanking.model;

public class CustomerProfileDTO {

	private Long id;
	
	private String name;
	
	private String customerUid;
	
	private Long txnnLimit;
	
	private Double perTxnLimit;
	
	private Long dailyTxnLimit;
	
	private Double dailyTransactionAmount;
	
	private Double weeklyTxnLimit;
	
	private Double monthlyTxnLimit;
	
	private Long bankId;
	
	private ProfileStatus status;
	
	private Double registrationAmount;
	
	private Double renewAmount;
	
	private Double pinResetCharge;
	
	private boolean isDefault;
	
	private String profileUniqueId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerUid() {
		return customerUid;
	}

	public void setCustomerUid(String customerUid) {
		this.customerUid = customerUid;
	}

	public Long getTxnnLimit() {
		return txnnLimit;
	}

	public void setTxnnLimit(Long txnnLimit) {
		this.txnnLimit = txnnLimit;
	}


	public Double getWeeklyTxnLimit() {
		return weeklyTxnLimit;
	}

	public void setWeeklyTxnLimit(Double weeklyTxnLimit) {
		this.weeklyTxnLimit = weeklyTxnLimit;
	}

	public Double getMonthlyTxnLimit() {
		return monthlyTxnLimit;
	}

	public void setMonthlyTxnLimit(Double monthlyTxnLimit) {
		this.monthlyTxnLimit = monthlyTxnLimit;
	}

	public Double getPerTxnLimit() {
		return perTxnLimit;
	}

	public void setPerTxnLimit(Double perTxnLimit) {
		this.perTxnLimit = perTxnLimit;
	}

	public Long getDailyTxnLimit() {
		return dailyTxnLimit;
	}

	public void setDailyTxnLimit(Long dailyTxnLimit) {
		this.dailyTxnLimit = dailyTxnLimit;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Double getDailyTransactionAmount() {
		return dailyTransactionAmount;
	}

	public void setDailyTransactionAmount(Double dailyTransactionAmount) {
		this.dailyTransactionAmount = dailyTransactionAmount;
	}

	public ProfileStatus getStatus() {
		return status;
	}

	public void setStatus(ProfileStatus status) {
		this.status = status;
	}

	public Double getRegistrationAmount() {
		return registrationAmount;
	}

	public void setRegistrationAmount(Double registrationAmount) {
		this.registrationAmount = registrationAmount;
	}

	public Double getRenewAmount() {
		return renewAmount;
	}

	public void setRenewAmount(Double renewAmount) {
		this.renewAmount = renewAmount;
	}

	public Double getPinResetCharge() {
		return pinResetCharge;
	}

	public void setPinResetCharge(Double pinResetCharge) {
		this.pinResetCharge = pinResetCharge;
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getProfileUniqueId() {
		return profileUniqueId;
	}

	public void setProfileUniqueId(String profileUniqueId) {
		this.profileUniqueId = profileUniqueId;
	}
	
}