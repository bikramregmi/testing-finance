package com.mobilebanking.model;

import java.util.Date;

public class ReportDTO {
	
	private String uniqueNumber;
	
	private CustomerType customerType;
	
	private AgentType agentType;
	
	private Date localCreated ;

	private Date payoutDateTime;
	
	private String remitAgent;
	
	private String payoutAgent;
	
	private String sender;
	
	private String beneficiary;
	
	private double sendingAmount;
	
	private double sendingAmountSettlement;
	
	private double totalSendingAmount;
	
    private double totalSendingAmountSettlement;
	
	private double receivingAmount;
	
	private double receivingAmountSettlement;
	
	private String remitUser;
	
	private String payoutUser;
	
	private String remitCountry;

	public String getUniqueNumber() {
		return uniqueNumber;
	}

	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}

	public Date getLocalCreated() {
		return localCreated;
	}

	public void setLocalCreated(Date localCreated) {
		this.localCreated = localCreated;
	}

	public Date getPayoutDateTime() {
		return payoutDateTime;
	}

	public void setPayoutDateTime(Date payoutDateTime) {
		this.payoutDateTime = payoutDateTime;
	}

	public String getRemitAgent() {
		return remitAgent;
	}

	public void setRemitAgent(String remitAgent) {
		this.remitAgent = remitAgent;
	}

	public String getPayoutAgent() {
		return payoutAgent;
	}

	public void setPayoutAgent(String payoutAgent) {
		this.payoutAgent = payoutAgent;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public double getSendingAmount() {
		return sendingAmount;
	}

	public void setSendingAmount(double sendingAmount) {
		this.sendingAmount = sendingAmount;
	}

	public double getSendingAmountSettlement() {
		return sendingAmountSettlement;
	}

	public void setSendingAmountSettlement(double sendingAmountSettlement) {
		this.sendingAmountSettlement = sendingAmountSettlement;
	}

	public double getTotalSendingAmount() {
		return totalSendingAmount;
	}

	public void setTotalSendingAmount(double totalSendingAmount) {
		this.totalSendingAmount = totalSendingAmount;
	}

	public double getTotalSendingAmountSettlement() {
		return totalSendingAmountSettlement;
	}

	public void setTotalSendingAmountSettlement(double totalSendingAmountSettlement) {
		this.totalSendingAmountSettlement = totalSendingAmountSettlement;
	}

	public double getReceivingAmount() {
		return receivingAmount;
	}

	public void setReceivingAmount(double receivingAmount) {
		this.receivingAmount = receivingAmount;
	}

	public double getReceivingAmountSettlement() {
		return receivingAmountSettlement;
	}

	public void setReceivingAmountSettlement(double receivingAmountSettlement) {
		this.receivingAmountSettlement = receivingAmountSettlement;
	}

	public String getRemitUser() {
		return remitUser;
	}

	public void setRemitUser(String remitUser) {
		this.remitUser = remitUser;
	}

	public String getPayoutUser() {
		return payoutUser;
	}

	public void setPayoutUser(String payoutUser) {
		this.payoutUser = payoutUser;
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

	public String getRemitCountry() {
		return remitCountry;
	}

	public void setRemitCountry(String remitCountry) {
		this.remitCountry = remitCountry;
	}
	
	


}
