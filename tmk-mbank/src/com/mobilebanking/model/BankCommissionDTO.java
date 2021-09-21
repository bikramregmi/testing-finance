package com.mobilebanking.model;

public class BankCommissionDTO {

	private Long id;
	private Double commissionFlat;
	
	private Double commissionPercentage;
	
	private Double feeFlat;
	
	private Double feePercentage;
	
	private Double channelPartnerCommissionFlat;
	
	private Double channelPartnerCommissionPercentage;

	private Double operatorCommissionFlat;
	
	private Double operatorCommissionPercentage;
	
	private Double cashBack;
	
	private Long commissionAmountId;

	public Long bankId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Long getCommissionAmountId() {
		return commissionAmountId;
	}

	public void setCommissionAmountId(Long commissionAmountId) {
		this.commissionAmountId = commissionAmountId;
	}

	public Double getCommissionFlat() {
		return commissionFlat;
	}

	public void setCommissionFlat(Double commissionFlat) {
		this.commissionFlat = commissionFlat;
	}

	public Double getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(Double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	public Double getFeeFlat() {
		return feeFlat;
	}

	public void setFeeFlat(Double feeFlat) {
		this.feeFlat = feeFlat;
	}

	public Double getFeePercentage() {
		return feePercentage;
	}

	public void setFeePercentage(Double feePercentage) {
		this.feePercentage = feePercentage;
	}

	public Double getChannelPartnerCommissionFlat() {
		return channelPartnerCommissionFlat;
	}

	public void setChannelPartnerCommissionFlat(Double channelPartnerCommissionFlat) {
		this.channelPartnerCommissionFlat = channelPartnerCommissionFlat;
	}

	public Double getChannelPartnerCommissionPercentage() {
		return channelPartnerCommissionPercentage;
	}

	public void setChannelPartnerCommissionPercentage(Double channelPartnerCommissionPercentage) {
		this.channelPartnerCommissionPercentage = channelPartnerCommissionPercentage;
	}

	public Double getOperatorCommissionFlat() {
		return operatorCommissionFlat;
	}

	public void setOperatorCommissionFlat(Double operatorCommissionFlat) {
		this.operatorCommissionFlat = operatorCommissionFlat;
	}

	public Double getOperatorCommissionPercentage() {
		return operatorCommissionPercentage;
	}

	public void setOperatorCommissionPercentage(Double operatorCommissionPercentage) {
		this.operatorCommissionPercentage = operatorCommissionPercentage;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Double getCashBack() {
		return cashBack;
	}

	public void setCashBack(Double cashBack) {
		this.cashBack = cashBack;
	}
	
}
