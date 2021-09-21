package com.mobilebanking.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown=true)
public class FlightReservationResponse {

	private String ttlTime;
	@JsonProperty("pnrNo")
	private String pnrNo;
	@JsonProperty("flightId")
	private String flightId;
	@JsonProperty("ttlDate")
	private String ttlDate;
	@JsonProperty("reservationStatus")
	private String reservationStatus;
	@JsonProperty("status")
	private String status;
	@JsonProperty("airlineId")
	private String airlineId;
	
	public String getTtlTime() {
		return ttlTime;
	}
	public void setTtlTime(String ttlTime) {
		this.ttlTime = ttlTime;
	}
	public String getPnrNo() {
		return pnrNo;
	}
	public void setPnrNo(String pnrNo) {
		this.pnrNo = pnrNo;
	}
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public String getTtlDate() {
		return ttlDate;
	}
	public void setTtlDate(String ttlDate) {
		this.ttlDate = ttlDate;
	}
	public String getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAirlineId() {
		return airlineId;
	}
	public void setAirlineId(String airlineId) {
		this.airlineId = airlineId;
	}
	
}
