package com.mobilebanking.model;

import java.util.HashMap;
import java.util.List;

//@JsonIgnoreProperties(value = { "id","idList", },ignoreUnknown = true)

public class NotificationDTO {
	
	private Long id;

	private String date;

	private String title;

	private String body;

	private String idList;

	private String sendTo;

	private boolean topic;
	
	private boolean allCustomer;

	private String response;
	
	private Long bankId;
	
	private String bankName;
	
	private String bankCode;
	
	private boolean toMobile = true;
	
	private List<String> serverKeyList;
	
	HashMap<String, String> serverKeyIdPair;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public boolean isTopic() {
		return topic;
	}

	public void setTopic(boolean topic) {
		this.topic = topic;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public boolean isToMobile() {
		return toMobile;
	}

	public void setToMobile(boolean toMobile) {
		this.toMobile = toMobile;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public boolean isAllCustomer() {
		return allCustomer;
	}

	public void setAllCustomer(boolean allCustomer) {
		this.allCustomer = allCustomer;
	}

	public List<String> getServerKeyList() {
		return serverKeyList;
	}

	public void setServerKeyList(List<String> serverKeyList) {
		this.serverKeyList = serverKeyList;
	}

	public HashMap<String, String> getServerKeyIdPair() {
		return serverKeyIdPair;
	}

	public void setServerKeyIdPair(HashMap<String, String> serverKeyIdPair) {
		this.serverKeyIdPair = serverKeyIdPair;
	}

}
