package com.wallet.serviceprovider.ntc;

public class AgentBalance {
	protected AuthorizationData requesterAuthentication;
	protected AgentIdentifier targetAgent;
	protected String productId; 
	protected String requestTag;
	
	public AuthorizationData getRequesterAuthentication() {
		return requesterAuthentication;
	}
	public void setRequesterAuthentication(AuthorizationData requesterAuthentication) {
		this.requesterAuthentication = requesterAuthentication;
	}
	public AgentIdentifier getTargetAgent() {
		return targetAgent;
	}
	public void setTargetAgent(AgentIdentifier targetAgent) {
		this.targetAgent = targetAgent;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRequestTag() {
		return requestTag;
	}
	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}
	
	

}
