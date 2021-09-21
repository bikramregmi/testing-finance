package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="notification")
public class Notification extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String title;
	
	private String message;
	
	private String sendTo;
	
	private String response;
	
	private boolean topic;
	
	private String bankCode;
	
	public boolean isTopic() {
		return topic;
	}

	public void setTopic(boolean topic) {
		this.topic = topic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
}
