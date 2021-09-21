package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.SmsStatus;

@Entity
@Table(name="smsalert")
public class SmsAlert extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	private String forMobileNumber;
	
	private String message;
	
	@ManyToOne
	private BulkSmsAlertBatch bulkSmsAlertBatch;
	
	private Date scheduledDate;
	
	private Date deliveredDate;
	
	private SmsStatus smsStatus;
	
	@ManyToOne
	private User createdBy;
	
	private String responseCode;
	
	private String responseMessage;

	public String getForMobileNumber() {
		return forMobileNumber;
	}

	public String getMessage() {
		return message;
	}

	public BulkSmsAlertBatch getBulkSmsAlertBatch() {
		return bulkSmsAlertBatch;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public Date getDeliveredDate() {
		return deliveredDate;
	}

	public SmsStatus getSmsStatus() {
		return smsStatus;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setForMobileNumber(String forMobileNumber) {
		this.forMobileNumber = forMobileNumber;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setBulkSmsAlertBatch(BulkSmsAlertBatch bulkSmsAlertBatch) {
		this.bulkSmsAlertBatch = bulkSmsAlertBatch;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public void setSmsStatus(SmsStatus smsStatus) {
		this.smsStatus = smsStatus;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
