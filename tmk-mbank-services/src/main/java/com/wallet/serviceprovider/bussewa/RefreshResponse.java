package com.wallet.serviceprovider.bussewa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RefreshResponse {

	private String status;
	private String bookedSeats;
	private String unbookedSeats;
	private boolean isLocked;

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

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	@Override
	public String toString() {
		return "Refresh [status=" + status + ", bookedSeats=" + bookedSeats + ", unbookedSeats=" + unbookedSeats
				+ ", isLocked=" + isLocked + "]";
	}

}
