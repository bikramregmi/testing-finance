package com.wallet.serviceprovider.ntc;

public class ERetailTransfer {
	protected AuthorizationData requesterAuthentication;
	protected AgentIdentifier debitedAgent;
	protected AgentIdentifier creditedAgent;
	protected boolean debitedAgentNotification;
	protected boolean creditedAgentNotification;
	protected TransferredStockList transferredStockList;
	protected String requestTag;
	
	public AuthorizationData getRequesterAuthentication() {
		return requesterAuthentication;
	}
	public void setRequesterAuthentication(AuthorizationData requesterAuthentication) {
		this.requesterAuthentication = requesterAuthentication;
	}
	public AgentIdentifier getDebitedAgent() {
		return debitedAgent;
	}
	public void setDebitedAgent(AgentIdentifier debitedAgent) {
		this.debitedAgent = debitedAgent;
	}
	public AgentIdentifier getCreditedAgent() {
		return creditedAgent;
	}
	public void setCreditedAgent(AgentIdentifier creditedAgent) {
		this.creditedAgent = creditedAgent;
	}
	public boolean isDebitedAgentNotification() {
		return debitedAgentNotification;
	}
	public void setDebitedAgentNotification(boolean debitedAgentNotification) {
		this.debitedAgentNotification = debitedAgentNotification;
	}
	public boolean isCreditedAgentNotification() {
		return creditedAgentNotification;
	}
	public void setCreditedAgentNotification(boolean creditedAgentNotification) {
		this.creditedAgentNotification = creditedAgentNotification;
	}
	public TransferredStockList getTransferredStockList() {
		return transferredStockList;
	}
	public void setTransferredStockList(TransferredStockList transferredStockList) {
		this.transferredStockList = transferredStockList;
	}
	public String getRequestTag() {
		return requestTag;
	}
	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}
	
	

}
