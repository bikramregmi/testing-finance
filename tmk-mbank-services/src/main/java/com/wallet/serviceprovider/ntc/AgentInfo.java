package com.wallet.serviceprovider.ntc;

public class AgentInfo {
	protected AuthorizationData requesterAuthentication;
	protected AgentIdentifier targetAgent;
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
	public String getRequestTag() {
		return requestTag;
	}
	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}
	
	
	

}
