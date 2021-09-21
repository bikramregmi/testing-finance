package com.mobilebanking.model.error;

public class TransactionError {
	
	public String uniqueNumber;
	
	public String trackingNumber;
	
	private String remitChannel;
		
	private String localCreated ;

	private String payoutDateTime;
	
	private String payoutChannel;
	
	private String remitAgent;
	
	private String payoutAgent;
	
	private String sender;
	
	private String beneficiary;

	private String remitCountry;
	
	private String payoutCountry;
	
	private String sendingAmount;

	private String sendingAmountUSD;

	private String sendingAmountSettlement;

	private String sendingCurrency;

	private String overAllCommission;

	private String overAllCommissionUSD;

	private String overAllCommissionSettlement;

	private String commissionRef;

	private String totalSendingAmount;

	private String totalSendingAmountUSD;

	private String totalSendingAmountSettlement;
	
	private String receivingAmount;

	private String receivingAmountUSD;

	private String receivingAmountSettlement;

	private String receivingCurrency;

	private String discountAmount;
	
	private String discountRef;

	private String exchangeRate;

	private String exchangeRateRef;

	private String exchangeRateUSD;

	private String exchangeRateUSDRef;

	private String exchangeRateSettlement;

	private String exchangeRateSettlementRef;

	private String sendingCommission;
	
	private String sendingCommissionCurrency;

	private String sendingCommissionSettlement;

	private String remittanceCommission;

	private String remittanceCommissionCurrency;
	
	private String remittanceCommissionSettlement;
	
	private String transactionStatus;

	private String remitUser;
	
	private String payoutUser;

	private String amlStatus;

	private String amlRef;

	private String amlCheckedBy;

	private String complianceRef;
	
	private String complianceCheckedBy;

	private boolean valid;
	
	private String globalExchangeRate; 
	
	private String bank;
	
	private String branch;
	
	private String bankAccountNumber;
	
	private String complianceStatus;
	
	
	public String getGlobalExchangeRate() {
		return globalExchangeRate;
	}

	public void setGlobalExchangeRate(String globalExchangeRate) {
		this.globalExchangeRate = globalExchangeRate;
	}

	public String getRemitChannel() {
		return remitChannel;
	}

	public void setRemitChannel(String remitChannel) {
		this.remitChannel = remitChannel;
	}

	public String getLocalCreated() {
		return localCreated;
	}

	public void setLocalCreated(String localCreated) {
		this.localCreated = localCreated;
	}

	public String getPayoutDateTime() {
		return payoutDateTime;
	}

	public void setPayoutDateTime(String payoutDateTime) {
		this.payoutDateTime = payoutDateTime;
	}

	public String getPayoutChannel() {
		return payoutChannel;
	}

	public void setPayoutChannel(String payoutChannel) {
		this.payoutChannel = payoutChannel;
	}

	public String getSendingAmount() {
		return sendingAmount;
	}

	public void setSendingAmount(String sendingAmount) {
		this.sendingAmount = sendingAmount;
	}

	public String getSendingAmountUSD() {
		return sendingAmountUSD;
	}

	public void setSendingAmountUSD(String sendingAmountUSD) {
		this.sendingAmountUSD = sendingAmountUSD;
	}

	public String getSendingAmountSettlement() {
		return sendingAmountSettlement;
	}

	public void setSendingAmountSettlement(String sendingAmountSettlement) {
		this.sendingAmountSettlement = sendingAmountSettlement;
	}

	public String getOverAllCommission() {
		return overAllCommission;
	}

	public void setOverAllCommission(String overAllCommission) {
		this.overAllCommission = overAllCommission;
	}

	public String getOverAllCommissionUSD() {
		return overAllCommissionUSD;
	}

	public void setOverAllCommissionUSD(String overAllCommissionUSD) {
		this.overAllCommissionUSD = overAllCommissionUSD;
	}

	public String getOverAllCommissionSettlement() {
		return overAllCommissionSettlement;
	}

	public void setOverAllCommissionSettlement(String overAllCommissionSettlement) {
		this.overAllCommissionSettlement = overAllCommissionSettlement;
	}

	public String getTotalSendingAmount() {
		return totalSendingAmount;
	}

	public void setTotalSendingAmount(String totalSendingAmount) {
		this.totalSendingAmount = totalSendingAmount;
	}

	public String getTotalSendingAmountUSD() {
		return totalSendingAmountUSD;
	}

	public void setTotalSendingAmountUSD(String totalSendingAmountUSD) {
		this.totalSendingAmountUSD = totalSendingAmountUSD;
	}

	public String getTotalSendingAmountSettlement() {
		return totalSendingAmountSettlement;
	}

	public void setTotalSendingAmountSettlement(String totalSendingAmountSettlement) {
		this.totalSendingAmountSettlement = totalSendingAmountSettlement;
	}

	public String getReceivingAmount() {
		return receivingAmount;
	}

	public void setReceivingAmount(String receivingAmount) {
		this.receivingAmount = receivingAmount;
	}

	public String getReceivingAmountUSD() {
		return receivingAmountUSD;
	}

	public void setReceivingAmountUSD(String receivingAmountUSD) {
		this.receivingAmountUSD = receivingAmountUSD;
	}

	public String getReceivingAmountSettlement() {
		return receivingAmountSettlement;
	}

	public void setReceivingAmountSettlement(String receivingAmountSettlement) {
		this.receivingAmountSettlement = receivingAmountSettlement;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getExchangeRateUSD() {
		return exchangeRateUSD;
	}

	public void setExchangeRateUSD(String exchangeRateUSD) {
		this.exchangeRateUSD = exchangeRateUSD;
	}

	public String getExchangeRateSettlement() {
		return exchangeRateSettlement;
	}

	public void setExchangeRateSettlement(String exchangeRateSettlement) {
		this.exchangeRateSettlement = exchangeRateSettlement;
	}

	public String getSendingCommission() {
		return sendingCommission;
	}

	public void setSendingCommission(String sendingCommission) {
		this.sendingCommission = sendingCommission;
	}

	public String getSendingCommissionSettlement() {
		return sendingCommissionSettlement;
	}

	public void setSendingCommissionSettlement(String sendingCommissionSettlement) {
		this.sendingCommissionSettlement = sendingCommissionSettlement;
	}

	public String getRemittanceCommission() {
		return remittanceCommission;
	}

	public void setRemittanceCommission(String remittanceCommission) {
		this.remittanceCommission = remittanceCommission;
	}

	public String getRemittanceCommissionSettlement() {
		return remittanceCommissionSettlement;
	}

	public void setRemittanceCommissionSettlement(
			String remittanceCommissionSettlement) {
		this.remittanceCommissionSettlement = remittanceCommissionSettlement;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getAmlStatus() {
		return amlStatus;
	}

	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}

	public String getUniqueNumber() {
		return uniqueNumber;
	}

	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
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

	public String getRemitCountry() {
		return remitCountry;
	}

	public void setRemitCountry(String remitCountry) {
		this.remitCountry = remitCountry;
	}

	public String getPayoutCountry() {
		return payoutCountry;
	}

	public void setPayoutCountry(String payoutCountry) {
		this.payoutCountry = payoutCountry;
	}

	
	public String getSendingCurrency() {
		return sendingCurrency;
	}

	public void setSendingCurrency(String sendingCurrency) {
		this.sendingCurrency = sendingCurrency;
	}

		public String getCommissionRef() {
		return commissionRef;
	}

	public void setCommissionRef(String commissionRef) {
		this.commissionRef = commissionRef;
	}


	public String getReceivingCurrency() {
		return receivingCurrency;
	}

	public void setReceivingCurrency(String receivingCurrency) {
		this.receivingCurrency = receivingCurrency;
	}

	public String getDiscountRef() {
		return discountRef;
	}

	public void setDiscountRef(String discountRef) {
		this.discountRef = discountRef;
	}

	public String getExchangeRateRef() {
		return exchangeRateRef;
	}

	public void setExchangeRateRef(String exchangeRateRef) {
		this.exchangeRateRef = exchangeRateRef;
	}

	public String getExchangeRateUSDRef() {
		return exchangeRateUSDRef;
	}

	public void setExchangeRateUSDRef(String exchangeRateUSDRef) {
		this.exchangeRateUSDRef = exchangeRateUSDRef;
	}

	public String getExchangeRateSettlementRef() {
		return exchangeRateSettlementRef;
	}

	public void setExchangeRateSettlementRef(String exchangeRateSettlementRef) {
		this.exchangeRateSettlementRef = exchangeRateSettlementRef;
	}

	public String getSendingCommissionCurrency() {
		return sendingCommissionCurrency;
	}

	public void setSendingCommissionCurrency(String sendingCommissionCurrency) {
		this.sendingCommissionCurrency = sendingCommissionCurrency;
	}


	public String getRemittanceCommissionCurrency() {
		return remittanceCommissionCurrency;
	}

	public void setRemittanceCommissionCurrency(String remittanceCommissionCurrency) {
		this.remittanceCommissionCurrency = remittanceCommissionCurrency;
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


	public String getAmlRef() {
		return amlRef;
	}

	public void setAmlRef(String amlRef) {
		this.amlRef = amlRef;
	}

	public String getAmlCheckedBy() {
		return amlCheckedBy;
	}

	public void setAmlCheckedBy(String amlCheckedBy) {
		this.amlCheckedBy = amlCheckedBy;
	}

	public String getComplianceRef() {
		return complianceRef;
	}

	public void setComplianceRef(String complianceRef) {
		this.complianceRef = complianceRef;
	}

	public String getComplianceCheckedBy() {
		return complianceCheckedBy;
	}

	public void setComplianceCheckedBy(String complianceCheckedBy) {
		this.complianceCheckedBy = complianceCheckedBy;
	}

	
	
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getComplianceStatus() {
		return complianceStatus;
	}

	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
