package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.mobilebanking.model.OfsResponseStatus;

@Entity
@Table(name = "ofsResponse")
public class OfsResponse extends AbstractEntity<Long>{
	
private static final long serialVersionUID = -6601995639006872929L;

private String transactionId;
	
	private String messageId;
	
	private Integer responseCode;
	
	private OfsResponseStatus status;
	
	@Lob
	@Column(length=3000)
	private String ReturnedMessageData;
	

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getReturnedMessageData() {
		return ReturnedMessageData;
	}

	public void setReturnedMessageData(String returnedMessageData) {
		ReturnedMessageData = returnedMessageData;
	}

	public OfsResponseStatus getStatus() {
		return status;
	}

	public void setStatus(OfsResponseStatus status) {
		this.status = status;
	}

}
