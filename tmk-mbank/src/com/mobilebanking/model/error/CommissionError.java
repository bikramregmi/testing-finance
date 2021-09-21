package com.mobilebanking.model.error;


public class CommissionError {

	private String bank;
	
	private String merchant;

	private String fromAmount;
	
	private String toAmount;
	
	private String commissionFlat;
	
	private String commissionPercent;
	
	private String commissionType;

	private double commissionCustomerFlat;

	private double commissoinCustomerPercentage;
	
	private String commissionBankFlat;
	
	private String commssionBankPercentage;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(String fromAmount) {
		this.fromAmount = fromAmount;
	}

	public String getToAmount() {
		return toAmount;
	}

	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}

	public String getCommissionFlat() {
		return commissionFlat;
	}

	public void setCommissionFlat(String commissionFlat) {
		this.commissionFlat = commissionFlat;
	}

	public String getCommissionPercent() {
		return commissionPercent;
	}

	public void setCommissionPercent(String commissionPercent) {
		this.commissionPercent = commissionPercent;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public double getCommissionCustomerFlat() {
		return commissionCustomerFlat;
	}

	public void setCommissionCustomerFlat(double commissionCustomerFlat) {
		this.commissionCustomerFlat = commissionCustomerFlat;
	}

	public double getCommissoinCustomerPercentage() {
		return commissoinCustomerPercentage;
	}

	public void setCommissoinCustomerPercentage(double commissoinCustomerPercentage) {
		this.commissoinCustomerPercentage = commissoinCustomerPercentage;
	}

	public String getCommissionBankFlat() {
		return commissionBankFlat;
	}

	public void setCommissionBankFlat(String commissionBankFlat) {
		this.commissionBankFlat = commissionBankFlat;
	}

	public String getCommssionBankPercentage() {
		return commssionBankPercentage;
	}

	public void setCommssionBankPercentage(String commssionBankPercentage) {
		this.commssionBankPercentage = commssionBankPercentage;
	}
}
