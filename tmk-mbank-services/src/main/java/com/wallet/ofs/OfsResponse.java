package com.wallet.ofs;

public class OfsResponse {

	private String transactionId;
	
	private String messageId;
	
	private Integer responseCode;
	
	private String ReturnedMessageData;
	
	private String messageRequest;

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

	public String getMessageRequest() {
		return messageRequest;
	}

	public void setMessageRequest(String messageRequest) {
		this.messageRequest = messageRequest;
	}
	
}
