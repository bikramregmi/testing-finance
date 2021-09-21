package com.wallet.serviceprovider.bussewa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TicketCancellation {

	private String contactNumber;
	private String ticketSerialNumber;
	private String cancelReason;
	private String tickets;

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getTicketSerialNumber() {
		return ticketSerialNumber;
	}

	public void setTicketSerialNumber(String ticketSerialNumber) {
		this.ticketSerialNumber = ticketSerialNumber;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getTickets() {
		return tickets;
	}

	public void setTickets(String tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "TicketCancellation [contactNumber=" + contactNumber + ", ticketSerialNumber=" + ticketSerialNumber
				+ ", cancelReason=" + cancelReason + ", tickets=" + tickets + "]";
	}

}
