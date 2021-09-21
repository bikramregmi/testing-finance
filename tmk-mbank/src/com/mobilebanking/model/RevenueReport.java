package com.mobilebanking.model;

import java.util.List;

public class RevenueReport {
	
	private String fromDate;
	
	private String toDate;
	
	private String bankName;
	
	private String merchant;
	
	private int totalTransactions;
	
	private double totalTransactionAmount;
	
	private double totalCommissionEarned;
	
	private List<Revenue> revenueList;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(int totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public double getTotalTransactionAmount() {
		return totalTransactionAmount;
	}

	public void setTotalTransactionAmount(double totalTransactionAmount) {
		this.totalTransactionAmount = totalTransactionAmount;
	}

	public List<Revenue> getRevenueList() {
		return revenueList;
	}

	public void setRevenueList(List<Revenue> revenueList) {
		this.revenueList = revenueList;
	}

	public double getTotalCommissionEarned() {
		return totalCommissionEarned;
	}

	public void setTotalCommissionEarned(double totalCommissionEarned) {
		this.totalCommissionEarned = totalCommissionEarned;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

}
