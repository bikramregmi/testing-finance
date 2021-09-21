package com.mobilebanking.airlines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class FlightReservationDTO {

	private String serviceIdentifier = "ARS";
	@JsonProperty("flightId")
	private String strFlightId;
	@JsonProperty("amount")
	private String totalAmount;
	@JsonProperty("returnFlightId")
	private String strReturnFlightId;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String cashBack ;

	public String getServiceIdentifier() {
		return serviceIdentifier;
	}
	public void setServiceIdentifier(String serviceIdentifier) {
		this.serviceIdentifier = serviceIdentifier;
	}
	public String getStrFlightId() {
		return strFlightId;
	}
	public void setStrFlightId(String strFlightId) {
		this.strFlightId = strFlightId;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStrReturnFlightId() {
		return strReturnFlightId;
	}
	public void setStrReturnFlightId(String strReturnFlightId) {
		this.strReturnFlightId = strReturnFlightId;
	}
	public String getCashBack() {
		return cashBack;
	}
	public void setCashBack(String cashBack) {
		this.cashBack = cashBack;
	}

}
