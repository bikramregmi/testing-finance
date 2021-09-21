package com.wallet.serviceprovider.bussewa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TicketBookingResponse {

	private String status;
	private String message;
	private String ticketSrlNo;
	private String timeOut;
	private List <String> boardingPoints;
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
	public String getTicketSrlNo() {
		return ticketSrlNo;
	}
	public void setTicketSrlNo(String ticketSrlNo) {
		this.ticketSrlNo = ticketSrlNo;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public List<String> getBoardingPoints() {
		return boardingPoints;
	}
	public void setBoardingPoints(List<String> boardingPoints) {
		this.boardingPoints = boardingPoints;
	}
	@Override
	public String toString() {
		return "TicketBooking [status=" + status + ", message=" + message + ", ticketSrlNo=" + ticketSrlNo
				+ ", timeOut=" + timeOut + ", boardingPoints=" + boardingPoints + "]";
	}
	
	
	
	
	
}
