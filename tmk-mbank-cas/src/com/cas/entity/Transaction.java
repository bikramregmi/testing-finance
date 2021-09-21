package com.cas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.cas.model.TransactionStatus;
import com.cas.model.TransactionType;

@Entity
public class Transaction extends AbstractEntity<Long> {
	private static final long serialVersionUID = -9163185658863000713L;
	
	@Column(unique = true,nullable = false)
	private long transactionId;
	
	@Column(unique = true,nullable = false)
	private long uniqueId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Account fromAccount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Account toAccount;

	@Column(nullable = false)
	private double  sentAmount;
	
	@Column(nullable = false)
	private double  receivedAmount;
	
	@Column(nullable = false)
	private double exchangeRate;
	
	@Column(nullable = false)
	private TransactionStatus status;
	
	@Column(nullable = false)
	private TransactionType type;
	
	@Column
	private String remarks;

	public long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public double getSentAmount() {
		return sentAmount;
	}

	public void setSentAmount(double sentAmount) {
		this.sentAmount = sentAmount;
	}

	public double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
