package com.wallet.serviceprovider.ntc;

public class TransactionInfoResponse {
	
	String requestTag;
	GlobalTransaction transaction;
	
	public String getRequestTag() {
		return requestTag;
	}
	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}
	public GlobalTransaction getTransaction() {
		return transaction;
	}
	public void setTransaction(GlobalTransaction transaction) {
		this.transaction = transaction;
	}
	
	
	
	

}
