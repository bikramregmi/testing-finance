package com.wallet.serviceprovider.bussewa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class PaymentConfirmationResponse {

	private String status;
	private String message;
	private String busNo;
	private List <String> contactInfo;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBusNo() {
		return busNo;
	}
	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}
	public List<String> getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(List<String> contactInfo) {
		this.contactInfo = contactInfo;
	}
	@Override
	public String toString() {
		return "PaymentConfirmation [status=" + status + ", message=" + message + ", busNo=" + busNo + ", contactInfo="
				+ contactInfo + "]";
	}
	
	
	
	
}


