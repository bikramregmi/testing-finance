package com.mobilebanking.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.plasmatech.IssueTicketRes;
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueTicketResponse {
	
	private String Status;
	
	private ResponseStatus responseStatus;
	
	private String message;
	
	private String refresh;
	
	private Object detail;
	
	private IssueTicketRes ticketResponse;
	

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	public IssueTicketRes getTicketResponse() {
		return ticketResponse;
	}

	public void setTicketResponse(IssueTicketRes ticketResponse) {
		this.ticketResponse = ticketResponse;
	}
	
}
