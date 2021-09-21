package com.wallet.serviceprovider.bussewa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TicketReconfirmation {

	private String ticketSerialNumber;

	public String getTicketSerialNumber() {
		return ticketSerialNumber;
	}

	public void setTicketSerialNumber(String ticketSerialNumber) {
		this.ticketSerialNumber = ticketSerialNumber;
	}

	@Override
	public String toString() {
		return "TicketReconfirmation [ticketSerialNumber=" + ticketSerialNumber + "]";
	}

}
