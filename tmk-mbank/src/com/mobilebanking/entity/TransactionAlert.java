package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mobilebanking.model.NotificationStatus;
import com.mobilebanking.model.SmsStatus;

@Entity
//@Immutable
@Table(name = "transDaily")
public class TransactionAlert extends AbstractEntity<Long> {

//	private String accountNo;
//	private Long customerId;
//	private String tranDate;
//	private String remarks;
//	private Double amount;
//	private String tranType;

	
	//new view from hamro test 
	
	private static final long serialVersionUID = 1L;

	private String message ;
	
	private String mobileNumber;
	
//	private String accountNumber;
	
//	private Long traceId;  
	
	private String swiftCode;
	
	private SmsStatus status;
	
	private NotificationStatus notificationStatus;
	
	private String channelPartner;
	
	private Date date;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	/*public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accounNumber) {
		this.accountNumber = accounNumber;
	}*/
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public SmsStatus getStatus() {
		return status;
	}
	public void setStatus(SmsStatus status) {
		this.status = status;
	}
	public String getChannelPartner() {
		return channelPartner;
	}
	public void setChannelPartner(String channelPartner) {
		this.channelPartner = channelPartner;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}
	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	
}
