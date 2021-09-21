package com.wallet.ofs;

public class OfsMessage {

	private Operation operation = Operation.FUNDSTRANSFER;
	
	private Options options = new Options();
	
	private UserInfo userInfo = new UserInfo();
	
	private String transactionId;
	
	private MessageData messageData = new MessageData();

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}


	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public MessageData getMessageData() {
		return messageData;
	}

	public void setMessageData(MessageData messageData) {
		this.messageData = messageData;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	
}
