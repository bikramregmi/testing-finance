package com.mobilebanking.model;

public class SmsAlertDTO {
	
	private String mobileNumber;

	private String message;
	
	private Long createdBy;
	
	private String bulkBatch;
	
	private Long bulkBatchId;
	
	private String deliveredDate;
	
	private String createdDate;
	
	private SmsStatus smsStatus;
	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getBulkBatch() {
		return bulkBatch;
	}

	public void setBulkBatch(String bulkBatch) {
		this.bulkBatch = bulkBatch;
	}

	public Long getBulkBatchId() {
		return bulkBatchId;
	}

	public void setBulkBatchId(Long bulkBatchId) {
		this.bulkBatchId = bulkBatchId;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public SmsStatus getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(SmsStatus smsStatus) {
		this.smsStatus = smsStatus;
	}

}
