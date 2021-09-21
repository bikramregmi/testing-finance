package com.wallet.serviceprovider.paypoint.paypointResponse;

import java.util.HashMap;
import java.util.List;

public class NeaBillAmountResponse {

	private HashMap<String, String> hashResponse;
	
	private List<Payment> payment;

	public HashMap<String, String> getHashResponse() {
		return hashResponse;
	}

	public void setHashResponse(HashMap<String, String> hashResponse) {
		this.hashResponse = hashResponse;
	}

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}
	
}
