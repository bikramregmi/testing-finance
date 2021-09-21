/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.TransactionStatus;
import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="transactionLog")
public class TransactionLog extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1508231949987220514L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Transaction transaction;
	
	@Column(nullable=false)
	private String remarks;
	
	@Column(nullable=false)
	private UserType transactionByUser;
	
	@Column(nullable=false)
	private String transactionBy;
	
	@Column
	private TransactionStatus status;

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public UserType getTransactionByUser() {
		return transactionByUser;
	}

	public void setTransactionByUser(UserType transactionByUser) {
		this.transactionByUser = transactionByUser;
	}

	public String getTransactionBy() {
		return transactionBy;
	}

	public void setTransactionBy(String transactionBy) {
		this.transactionBy = transactionBy;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}	
}
