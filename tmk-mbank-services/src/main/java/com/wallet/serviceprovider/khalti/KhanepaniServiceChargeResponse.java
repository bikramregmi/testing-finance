package com.wallet.serviceprovider.khalti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KhanepaniServiceChargeResponse {
	
	private String amount;
	
	private String service_charge;
	
	private String status;

	public String getAmount() {
		return amount;
	}

	public String getService_charge() {
		return service_charge;
	}

	public String getStatus() {
		return status;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setService_charge(String service_charge) {
		this.service_charge = service_charge;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
