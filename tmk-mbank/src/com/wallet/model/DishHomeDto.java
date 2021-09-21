package com.wallet.model;

import java.util.HashMap;
import java.util.Map;

public class DishHomeDto {

	private String balance;
	
	private Map<String, String> responseDetail = new HashMap<String, String>();
	
	private Map<String, String> requestDetail = new HashMap<String, String>();

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public Map<String, String> getResponseDetail() {
		return responseDetail;
	}

	public void setResponseDetail(Map<String, String> responseDetail) {
		this.responseDetail = responseDetail;
	}

	public Map<String, String> getRequestDetail() {
		return requestDetail;
	}

	public void setRequestDetail(Map<String, String> requestDetail) {
		this.requestDetail = requestDetail;
	}

	
}
