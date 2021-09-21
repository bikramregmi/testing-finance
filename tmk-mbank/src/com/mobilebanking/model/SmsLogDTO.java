/**
 * 
 */
package com.mobilebanking.model;

import java.util.Date;

/**
 * @author bibek
 *
 */
public class SmsLogDTO {
	
	private long id;
	
	private SmsType smsType;
	
	private String smsForUser;
	
	private String smsFor;
	
	private String smsFromUser;
	
	private String bankBranch;
	
	private String bank;
	
	private SmsStatus status;
	private String statusString;
	private String message;
	
	private Date delivered;
	
	private Date created;
	
	private String deliveredDate;
	
	private Boolean isIncommingSms;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SmsType getSmsType() {
		return smsType;
	}

	public void setSmsType(SmsType smsType) {
		this.smsType = smsType;
	}

	public String getSmsFor() {
		return smsFor;
	}

	public void setSmsFor(String smsFor) {
		this.smsFor = smsFor;
	}

	public String getSmsFromUser() {
		return smsFromUser;
	}

	public void setSmsFromUser(String smsFromUser) {
		this.smsFromUser = smsFromUser;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public SmsStatus getStatus() {
		return status;
	}

	public void setStatus(SmsStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDelivered() {
		return delivered;
	}

	public void setDelivered(Date delivered) {
		this.delivered = delivered;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public Boolean getIsIncommingSms() {
		return isIncommingSms;
	}

	public void setIsIncommingSms(Boolean isIncommingSms) {
		this.isIncommingSms = isIncommingSms;
	}

	public String getSmsForUser() {
		return smsForUser;
	}

	public void setSmsForUser(String smsForUser) {
		this.smsForUser = smsForUser;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}
	
}
