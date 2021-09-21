package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bulksmsalertbatch")
public class BulkSmsAlertBatch extends AbstractEntity<Long>{
	
	private static final long serialVersionUID = 4376500748692084154L;
	
	private String batchId;
	
	@ManyToOne
	private User createdBy;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
}
