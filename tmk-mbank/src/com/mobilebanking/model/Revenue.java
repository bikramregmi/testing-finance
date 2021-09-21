package com.mobilebanking.model;

public class Revenue {

	private String transactionType;

	private long noOfTransaction;

	private double valueOfTransaction;

	private double commissionEarned;

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public long getNoOfTransaction() {
		return noOfTransaction;
	}

	public void setNoOfTransaction(long noOfTransaction) {
		this.noOfTransaction = noOfTransaction;
	}

	public double getValueOfTransaction() {
		return valueOfTransaction;
	}

	public void setValueOfTransaction(double valueOfTransaction) {
		this.valueOfTransaction = valueOfTransaction;
	}

	public double getCommissionEarned() {
		return commissionEarned;
	}

	public void setCommissionEarned(double commissionEarned) {
		this.commissionEarned = commissionEarned;
	}

}
