package com.mobilebanking.model;

import java.util.HashMap;
import java.util.Map;

public class TransactionDTO {

	private long id;
	public long originatingUser;
	public String destination;
	public long transactionOwner;
	public String transactionOwnerName;
	public String status;
	public String externalTransactionRefNo;
	public String additionalInformation;
	public double amount;
	public TransactionType transactionType;
	public String sourceType;
	public String sourceAccount;
	public String sourceOwnerName;
	private Map<String, String> requestDetail = new HashMap<String, String>();
	private Map<String, String> responseDetail = new HashMap<String, String>();
	public String destinationAccount;
	public String destinationType;
	public String transactionIdentifier;
	public TransactionStatus transactionStatus;
	public double commissionAmount;
	public String currency;
	public double calculatedRewardPoints;
	public String thirdPartyReference;
	public String discount;
	public double discountAmount;
	public double fee;
	public Long bankId;
	public String accountNumber;
	private String createdDate;
	private String service;
	private String sourceBankAccount;
	private String originatorName;
	private String originatorMobileNo;
	private double reversed;
	private String responseMessage;
	private String remarks;
	private String bankCode;
	private String branchCode;
	private String totalCommission;
	private String bankCommission;
	private String operatorCommissionAmount;
	private String channelPartnerCommissionAmount;
	private String merchantRefstan;
	private String previousTranId;
	private SettlementStatus settlementStatus;
	private String isoCode;
	private double agencyCommission;
	// for Report
	private String operatorSettlement;
	private String bankSettlement;
	private String channelPartnerSettlement;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOriginatingUser() {
		return originatingUser;
	}

	public void setOriginatingUser(long originatingUser) {
		this.originatingUser = originatingUser;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public long getTransactionOwner() {
		return transactionOwner;
	}

	public void setTransactionOwner(long transactionOwner) {
		this.transactionOwner = transactionOwner;
	}

	public String getTransactionOwnerName() {
		return transactionOwnerName;
	}

	public void setTransactionOwnerName(String transactionOwnerName) {
		this.transactionOwnerName = transactionOwnerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExternalTransactionRefNo() {
		return externalTransactionRefNo;
	}

	public void setExternalTransactionRefNo(String externalTransactionRefNo) {
		this.externalTransactionRefNo = externalTransactionRefNo;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public String getSourceOwnerName() {
		return sourceOwnerName;
	}

	public void setSourceOwnerName(String sourceOwnerName) {
		this.sourceOwnerName = sourceOwnerName;
	}

	public Map<String, String> getRequestDetail() {
		return requestDetail;
	}

	public void setRequestDetail(Map<String, String> requestDetail) {
		this.requestDetail = requestDetail;
	}

	public Map<String, String> getResponseDetail() {
		return responseDetail;
	}

	public void setResponseDetail(Map<String, String> responseDetail) {
		this.responseDetail = responseDetail;
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public String getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public double getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getCalculatedRewardPoints() {
		return calculatedRewardPoints;
	}

	public void setCalculatedRewardPoints(double calculatedRewardPoints) {
		this.calculatedRewardPoints = calculatedRewardPoints;
	}

	public String getThirdPartyReference() {
		return thirdPartyReference;
	}

	public void setThirdPartyReference(String thirdPartyReference) {
		this.thirdPartyReference = thirdPartyReference;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getOriginatorName() {
		return originatorName;
	}

	public void setOriginatorName(String originatorName) {
		this.originatorName = originatorName;
	}

	public String getOriginatorMobileNo() {
		return originatorMobileNo;
	}

	public void setOriginatorMobileNo(String originatorMobileNo) {
		this.originatorMobileNo = originatorMobileNo;
	}

	public double getReversed() {
		return reversed;
	}

	public void setReversed(double reversed) {
		this.reversed = reversed;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getSourceBankAccount() {
		return sourceBankAccount;
	}

	public void setSourceBankAccount(String sourceBankAccount) {
		this.sourceBankAccount = sourceBankAccount;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBankCommission() {
		return bankCommission;
	}

	public void setBankCommission(String bankCommission) {
		this.bankCommission = bankCommission;
	}

	public String getOperatorCommissionAmount() {
		return operatorCommissionAmount;
	}

	public void setOperatorCommissionAmount(String operatorCommissionAmount) {
		this.operatorCommissionAmount = operatorCommissionAmount;
	}

	public String getChannelPartnerCommissionAmount() {
		return channelPartnerCommissionAmount;
	}

	public void setChannelPartnerCommissionAmount(String channelPartnerCommissionAmount) {
		this.channelPartnerCommissionAmount = channelPartnerCommissionAmount;
	}

	public String getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(String totalCommission) {
		this.totalCommission = totalCommission;
	}

	public String getMerchantRefstan() {
		return merchantRefstan;
	}

	public void setMerchantRefstan(String merchantRefstan) {
		this.merchantRefstan = merchantRefstan;
	}

	public String getPreviousTranId() {
		return previousTranId;
	}

	public void setPreviousTranId(String previousTranId) {
		this.previousTranId = previousTranId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOperatorSettlement() {
		return operatorSettlement;
	}

	public void setOperatorSettlement(String operatorSettlement) {
		this.operatorSettlement = operatorSettlement;
	}

	public String getBankSettlement() {
		return bankSettlement;
	}

	public void setBankSettlement(String bankSettlement) {
		this.bankSettlement = bankSettlement;
	}

	public String getChannelPartnerSettlement() {
		return channelPartnerSettlement;
	}

	public void setChannelPartnerSettlement(String channelPartnerSettlement) {
		this.channelPartnerSettlement = channelPartnerSettlement;
	}

	public SettlementStatus getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(SettlementStatus settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public double getAgencyCommission() {
		return agencyCommission;
	}

	public void setAgencyCommission(double agencyCommission) {
		this.agencyCommission = agencyCommission;
	}

}
