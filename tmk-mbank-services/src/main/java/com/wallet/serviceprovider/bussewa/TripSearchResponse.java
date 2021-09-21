package com.wallet.serviceprovider.bussewa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TripSearchResponse {

	private String status;
	private List<TripResponse> trips;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TripResponse> getTrips() {
		return trips;
	}

	public void setTrips(List<TripResponse> trips) {
		this.trips = trips;
	}

	@Override
	public String toString() {
		return "TripSearch [status=" + status + ", trips=" + trips + "]";
	}

}
