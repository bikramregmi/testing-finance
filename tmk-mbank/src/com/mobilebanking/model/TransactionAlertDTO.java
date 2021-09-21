package com.mobilebanking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown =true)
public class TransactionAlertDTO {

	private String message ;
	
	private String mobileNumber;
	
//	private String accountNumber;
	
//	private Long traceId;  
	
	private String swiftCode;

	private String channelPartner;
	
	private String password;
	
	private String dateTime;
	
	private SmsStatus smsStatus;
	
	private NotificationStatus notificationStatus;
	
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

	public void setAccountNumber(String accoutNumber) {
		this.accountNumber = accoutNumber;
	}
*/
/*	public Long getTraceId() {
		return traceId;
	}

	public void setTraceId(Long traceId) {
		this.traceId = traceId;
	}*/

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getChannelPartner() {
		return channelPartner;
	}

	public void setChannelPartner(String channelPartner) {
		this.channelPartner = channelPartner;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public SmsStatus getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(SmsStatus smsStatus) {
		this.smsStatus = smsStatus;
	}

	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

}
