package com.mobilebanking.model;

public class ReportRequestDTO {
	
	private String id;
	
	private String fromDate;
	
	private String toDate;
	
	private CustomerType customerType;
	
	private AgentType agentType;
	
	private TransactionStatus txnStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	public TransactionStatus getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(TransactionStatus txnStatus) {
		this.txnStatus = txnStatus;
	}
	
	

}
