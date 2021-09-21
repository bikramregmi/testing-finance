package com.wallet.serviceprovider.ntc;

public class ERetailRecharge {
	
	protected AuthorizationData requesterAuthentication;
	protected AgentIdentifier debitedAgent;
	protected String rechargedMsisdn;
	protected boolean debitedAgentNotification;
	protected long rechargeAmount;
	protected long productId;
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
	public String getRechargedMsisdn() {
		return rechargedMsisdn;
	}
	public void setRechargedMsisdn(String rechargedMsisdn) {
		this.rechargedMsisdn = rechargedMsisdn;
	}
	public boolean isDebitedAgentNotification() {
		return debitedAgentNotification;
	}
	public void setDebitedAgentNotification(boolean debitedAgentNotification) {
		this.debitedAgentNotification = debitedAgentNotification;
	}
	public long getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(long rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getRequestTag() {
		return requestTag;
	}
	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}
	
	

}
