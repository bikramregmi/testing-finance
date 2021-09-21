package com.mobilebanking.model;

import java.util.List;

public class BulkSmsAlertDTO {
	
	private Long id;
	
	private Long createdBy;
	
	private String createdDate;

	private String scheduledDate;
	
	private String batchId;
	
	private Long smsCount;
	
	private List<SmsAlertDTO> smsAlertList;

	public Long getCreatedBy() {
		return createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public String getBatchId() {
		return batchId;
	}

	public Long getSmsCount() {
		return smsCount;
	}

	public List<SmsAlertDTO> getSmsAlertList() {
		return smsAlertList;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public void setSmsCount(Long smsCount) {
		this.smsCount = smsCount;
	}

	public void setSmsAlertList(List<SmsAlertDTO> smsAlertList) {
		this.smsAlertList = smsAlertList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
