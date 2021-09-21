package com.wallet.serviceprovider.bussewa;

import java.util.List;

public class MultiPassengerDetailRequest {

	private List<MultiPassengerDetail> multiDetail;

	private String contactNumber;
	
	private String boardingPoint;
	
	private String securityCode;
	
	private String ticketSerailNumber;
	
	public List<MultiPassengerDetail> getMultiDetail() {
		return multiDetail;
	}

	public void setMultiDetail(List<MultiPassengerDetail> multiDetail) {
		this.multiDetail = multiDetail;
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

	public String getTicketSerailNumber() {
		return ticketSerailNumber;
	}

	public void setTicketSerailNumber(String ticketSerailNumber) {
		this.ticketSerailNumber = ticketSerailNumber;
	}


}
