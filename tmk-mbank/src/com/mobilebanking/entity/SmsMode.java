/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */

@Entity
@Table(name="smsMode")
public class SmsMode extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	@Column
	private SmsType smsType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private BankBranch bankBranch;
	
	@Column(columnDefinition="TEXT")
	private String message;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User createdBy;
	
	@Column
	private Status status;

	public SmsType getSmsType() {
		return smsType;
	}

	public void setSmsType(SmsType smsType) {
		this.smsType = smsType;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
