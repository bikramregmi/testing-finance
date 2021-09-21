package com.wallet.serviceprovider.bussewa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class BackToPreviousPageResponse {

	private String status;
	private String bookedSeats;
	private String unbookedSeats;
	private String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(String bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public String getUnbookedSeats() {
		return unbookedSeats;
	}
	public void setUnbookedSeats(String unbookedSeats) {
		this.unbookedSeats = unbookedSeats;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "BackToPreviousPage [status=" + status + ", bookedSeats=" + bookedSeats + ", unbookedSeats="
				+ unbookedSeats + ", message=" + message + "]";
	}
	
}


