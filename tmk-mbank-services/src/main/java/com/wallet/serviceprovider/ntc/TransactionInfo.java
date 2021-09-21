package com.wallet.serviceprovider.ntc;

import javax.xml.ws.Holder;

public class TransactionInfo {
	protected AuthorizationData requesterAuthentication;
	protected String transactionId;
	protected Holder<String> requestTag;
	protected Holder<GlobalTransaction> transaction;
	
	public AuthorizationData getRequesterAuthentication() {
		return requesterAuthentication;
	}
	public void setRequesterAuthentication(AuthorizationData requesterAuthentication) {
		this.requesterAuthentication = requesterAuthentication;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Holder<String> getRequestTag() {
		return requestTag;
	}
	public void setRequestTag(Holder<String> requestTag) {
		this.requestTag = requestTag;
	}
	public Holder<GlobalTransaction> getTransaction() {
		return transaction;
	}
	public void setTransaction(Holder<GlobalTransaction> transaction) {
		this.transaction = transaction;
	}
	
	
}
