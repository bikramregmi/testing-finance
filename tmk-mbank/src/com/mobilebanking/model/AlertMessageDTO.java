package com.mobilebanking.model;

import java.util.List;

public class AlertMessageDTO {

	private String message ;
	
	private List<Long> customerlist;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Long> getCustomerlist() {
		return customerlist;
	}

	public void setCustomerlist(List<Long> customerlist) {
		this.customerlist = customerlist;
	}

}
