package com.mobilebanking.model.mobile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDetail {

	@JsonProperty("tickets")
	private String tickets;
	
	@JsonProperty("downloadTicketUrl")
	private String downloadTicketUrl;
	
	@JsonProperty("responseDate")
	private String responseDate;
	
	@JsonProperty("status")
	private String status;

	public String getTickets() {
		return tickets;
	}

	public void setTickets(String tickets) {
		this.tickets = tickets;
	}

	public String getDownloadTicketUrl() {
		return downloadTicketUrl;
	}

	public void setDownloadTicketUrl(String downloadTicketUrl) {
		this.downloadTicketUrl = downloadTicketUrl;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
