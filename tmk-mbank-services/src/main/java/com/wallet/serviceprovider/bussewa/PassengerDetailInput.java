package com.wallet.serviceprovider.bussewa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class PassengerDetailInput {

	private String name;
	private String contactNumber;
	private String boardingPoint;
	private String securityCode;
	private String ticketSerialNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getBoardingPoint() {
		return boardingPoint;
	}

	public void setBoardingPoint(String boardingPoint) {
		this.boardingPoint = boardingPoint;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getTicketSerialNumber() {
		return ticketSerialNumber;
	}

	public void setTicketSerialNumber(String ticketSerialNumber) {
		this.ticketSerialNumber = ticketSerialNumber;
	}

	@Override
	public String toString() {
		return "PassengerDetailInput [name=" + name + ", contactNumber=" + contactNumber + ", boardingPoint="
				+ boardingPoint + ", securityCode=" + securityCode + ", ticketSerialNumber=" + ticketSerialNumber + "]";
	}

}

