package com.mobilebanking.model;

import java.util.List;


public class CommissionDTO {
	
	private Long id;
	
	private List<Double> commissionFlat;
	
	private List<Double> commissionPercentage;
	
	private List<Double> fromAmount;
	
	private List<Double> toAmount;
	
	private Status status;
	
	private List<Double> feeFlat;
	
	private List<Double> feePercentage;
	
	private String bank;
	
	private String merchant;
	
	private String service;
	
	private boolean commissionForCustomer;
	
	private boolean sameForAll;
	
	private boolean feeCharge;
	
	private TransactionType transactionType;
	
	private CommissionType commissionType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Double> getCommissionFlat() {
		return commissionFlat;
	}

	public void setCommissionFlat(List<Double> commissionFlat) {
		this.commissionFlat = commissionFlat;
	}

	public List<Double> getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(List<Double> commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	public List<Double> getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(List<Double> fromAmount) {
		this.fromAmount = fromAmount;
	}

	public List<Double> getToAmount() {
		return toAmount;
	}

	public void setToAmount(List<Double> toAmount) {
		this.toAmount = toAmount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Double> getFeeFlat() {
		return feeFlat;
	}

	public void setFeeFlat(List<Double> feeFlat) {
		this.feeFlat = feeFlat;
	}

	public List<Double> getFeePercentage() {
		return feePercentage;
	}

	public void setFeePercentage(List<Double> feePercentage) {
		this.feePercentage = feePercentage;
	}

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

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public boolean isCommissionForCustomer() {
		return commissionForCustomer;
	}

	public void setCommissionForCustomer(boolean commissionForCustomer) {
		this.commissionForCustomer = commissionForCustomer;
	}

	public boolean isSameForAll() {
		return sameForAll;
	}

	public void setSameForAll(boolean sameForAll) {
		this.sameForAll = sameForAll;
	}

	public boolean isFeeCharge() {
		return feeCharge;
	}

	public void setFeeCharge(boolean feeCharge) {
		this.feeCharge = feeCharge;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public CommissionType getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(CommissionType commissionType) {
		this.commissionType = commissionType;
	}

}
