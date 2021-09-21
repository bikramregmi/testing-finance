package com.mobilebanking.plasmatech;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueTicketRes {

	@JsonProperty("passenger")
	protected List<Passenger> passenger;

	@JsonProperty("passenger")
	public List<Passenger> getPassenger() {
		return passenger;
	}

	@JsonProperty("passenger")
	public void setPassenger(List<Passenger> passenger) {
		this.passenger = passenger;
	}

}
