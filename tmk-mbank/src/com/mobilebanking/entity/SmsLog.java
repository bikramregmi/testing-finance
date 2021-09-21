/**
 * 
 */
package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="smsLog")
public class SmsLog extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	@Column
	private SmsType smsType;
	
	@Column
	private UserType smsForUser;
	
	@Column
	private String smsFor;
	
	@Column
	private String forMobileNo;
	
	@Column
	private long smsFrom;
	
	@Column
	private UserType smsFromUser;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private BankBranch bankBranch;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@Column
	private String message;
	
	@Column
	private SmsStatus status;
	
	private boolean isSmsIn;
	
	@Column(nullable=true)
	private Date delivered;
	
	@Column(nullable=true)
	private String responseCode;
	
	@Column(nullable=true)
	private String responseMessage;
	
	public SmsType getSmsType() {
		return smsType;
	}

	public void setSmsType(SmsType smsType) {
		this.smsType = smsType;
	}

	public UserType getSmsForUser() {
		return smsForUser;
	}

	public void setSmsForUser(UserType smsForUser) {
		this.smsForUser = smsForUser;
	}

	public String getSmsFor() {
		return smsFor;
	}

	public void setSmsFor(String smsFor) {
		this.smsFor = smsFor;
	}

	public long getSmsFrom() {
		return smsFrom;
	}

	public void setSmsFrom(long smsFrom) {
		this.smsFrom = smsFrom;
	}

	public UserType getSmsFromUser() {
		return smsFromUser;
	}

	public void setSmsFromUser(UserType smsFromUser) {
		this.smsFromUser = smsFromUser;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SmsStatus getStatus() {
		return status;
	}

	public void setStatus(SmsStatus status) {
		this.status = status;
	}

	public Date getDelivered() {
		return delivered;
	}

	public void setDelivered(Date delivered) {
		this.delivered = delivered;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public boolean isSmsIn() {
		return isSmsIn;
	}

	public void setSmsIn(boolean isSmsIn) {
		this.isSmsIn = isSmsIn;
	}

	public String getForMobileNo() {
		return forMobileNo;
	}

	public void setForMobileNo(String forMobileNo) {
		this.forMobileNo = forMobileNo;
	}
}
