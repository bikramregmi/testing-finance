package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.LedgerStatus;
import com.mobilebanking.model.LedgerType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name = "ledger")
public class Ledger extends AbstractEntity<Long> {

	private static final long serialVersionUID = -9163185658863000713L;

	@Column
	private long externalRefId;
	
	@Column(nullable = false)
	private String transactionId;
   
	@ManyToOne(fetch = FetchType.EAGER)
	private Account fromAccount;
    
	@ManyToOne(fetch = FetchType.EAGER)
	private Account toAccount;

	@Column(nullable = false)
	private double amount;

	@Column(nullable = false)
	private double fromBalance;

	@Column(nullable = false)
	private double toBalance;

	@Column(nullable = false)
	private LedgerStatus status;

	@Column(nullable = false)
	private LedgerType type;

	@Column
	private String remarks;

	@Column
	private long parentId;
	
	@Column
	private boolean isCredit = false;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User uploadedBy;

	public long getExternalRefId() {
		return externalRefId;
	}

	public void setExternalRefId(long externalRefId) {
		this.externalRefId = externalRefId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFromBalance() {
		return fromBalance;
	}

	public void setFromBalance(double fromBalance) {
		this.fromBalance = fromBalance;
	}

	public double getToBalance() {
		return toBalance;
	}

	public void setToBalance(double toBalance) {
		this.toBalance = toBalance;
	}

	public LedgerStatus getStatus() {
		return status;
	}

	public void setStatus(LedgerStatus status) {
		this.status = status;
	}

	public LedgerType getType() {
		return type;
	}

	public void setType(LedgerType type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public boolean isCredit() {
		return isCredit;
	}

	public void setCredit(boolean isCredit) {
		this.isCredit = isCredit;
	}
	
	public User getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(User uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
}
