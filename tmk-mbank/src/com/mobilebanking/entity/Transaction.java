/**
 * 
 */
package com.mobilebanking.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.mobilebanking.model.SettlementStatus;
import com.mobilebanking.model.TransactionStatus;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.model.UserType;

/**
 * @author bibek
 * 
 */
@Entity
@Table(name="transaction")
public class Transaction extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = -1120958444669161103L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User originatingUser;
	
	private String destination;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User owner;
	
	@Column
	private String referenceNumber;
	
	@Column(nullable=false)
	private double amount;
	
	@Column
	private double transactionAmount;
	
	@Column
	private Double cashBack;
	
	@Column(nullable=true)
	private double fee;
	
	@ManyToOne
	private Commission commissionRef;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private CommissionAmount commissionAmountRef;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private BankCommission bankCommission;
	
	@Column(nullable=true)
	private double totalCommission;
		
	@Column
	private long rewardPoints;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "servicerequest")
	private Map<String, String> requestDetail = new HashMap<String, String>();

	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "serviceresponse")
	private Map<String, String> responseDetail = new HashMap<String, String>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	private MerchantService service;
	
	@Column
	private UserType transactionBy;
	
	@Column
	private TransactionStatus status;
	
	@Column
	private TransactionType transactionType;
	
	@Column
	private String remarks;
	
	@Column
	private String transactionIdentifier;
	
	private SettlementStatus settlementStatus;

	@ManyToOne(fetch=FetchType.EAGER)
	private BankBranch bankBranch;
	
	@Column(nullable=true)
	private String sourceAccount;
	
	@Column(nullable=true)
	private String destinationAccount;
	
	@ManyToOne
	private MerchantManager merchantManager;
	
	@Column(nullable=true)
	private Double bankCommissionAmount;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@Column(nullable=true)
	private Double operatorCommissionAmount;
	
	@Column(nullable=true)
	private Double channelPartnerCommissionAmount;

	private String sourceBankAccount;
	
	private String previousTransactionId;
	
	private String refstan;
	
	private Double agencyCommission;
	
	public BankCommission getBankCommission() {
		return bankCommission;
	}

	public void setBankCommission(BankCommission bankCommission) {
		this.bankCommission = bankCommission;
	}

	public User getOriginatingUser() {
		return originatingUser;
	}

	public void setOriginatingUser(User originatingUser) {
		this.originatingUser = originatingUser;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Double getCashBack() {
		return cashBack;
	}

	public void setCashBack(Double cashBack) {
		this.cashBack = cashBack;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public Commission getCommissionRef() {
		return commissionRef;
	}

	public void setCommissionRef(Commission commissionRef) {
		this.commissionRef = commissionRef;
	}

	public CommissionAmount getCommissionAmountRef() {
		return commissionAmountRef;
	}

	public void setCommissionAmountRef(CommissionAmount commissionAmountRef) {
		this.commissionAmountRef = commissionAmountRef;
	}

	public double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public long getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(long rewardPoints) {
		this.rewardPoints = rewardPoints;
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

	public MerchantService getService() {
		return service;
	}

	public void setService(MerchantService service) {
		this.service = service;
	}

	public UserType getTransactionBy() {
		return transactionBy;
	}

	public void setTransactionBy(UserType transactionBy) {
		this.transactionBy = transactionBy;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

	public String getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public MerchantManager getMerchantManager() {
		return merchantManager;
	}

	public void setMerchantManager(MerchantManager merchantManager) {
		this.merchantManager = merchantManager;
	}

	public Double getBankCommissionAmount() {
		return bankCommissionAmount;
	}

	public void setBankCommissionAmount(Double bankCommissionAmount) {
		this.bankCommissionAmount = bankCommissionAmount;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	public Double getOperatorCommissionAmount() {
		return operatorCommissionAmount;
	}

	public void setOperatorCommissionAmount(Double operatorCommissionAmount) {
		this.operatorCommissionAmount = operatorCommissionAmount;
	}

	public Double getChannelPartnerCommissionAmount() {
		return channelPartnerCommissionAmount;
	}

	public void setChannelPartnerCommissionAmount(Double channelPartnerCommissionAmount) {
		this.channelPartnerCommissionAmount = channelPartnerCommissionAmount;
	}

	public String getSourceBankAccount() {
		return sourceBankAccount;
	}

	public void setSourceBankAccount(String sourceBankAccount) {
		this.sourceBankAccount = sourceBankAccount;
	}

	public SettlementStatus getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(SettlementStatus settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public String getPreviousTransactionId() {
		return previousTransactionId;
	}

	public void setPreviousTransactionId(String previousTransactionId) {
		this.previousTransactionId = previousTransactionId;
	}

	public String getRefstan() {
		return refstan;
	}

	public void setRefstan(String refstan) {
		this.refstan = refstan;
	}

	public Double getAgencyCommission() {
		return agencyCommission;
	}

	public void setAgencyCommission(Double agencyCommission) {
		this.agencyCommission = agencyCommission;
	}
	
}
