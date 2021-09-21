package com.mobilebanking.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(value = { "isRoundTrip" })
public class FlightRequestDTO {

	@JsonProperty("nationality")
	private String nationality;
	@JsonProperty("sectorFrom")
	private String source;
	@JsonProperty("sectorTo")
	private String destination;
	@JsonProperty("adultNumber")
	private Integer adult;
	@JsonProperty("childNumber")
	private Integer child;
	@JsonProperty("flightDate")
	private String departureDate;
	@JsonProperty("returnDate")
	private String arrivalDate;
	
	private Boolean isRoundTrip;
	
	@JsonProperty("tripType")
	private String tripType;
	@JsonProperty("serviceIdentifier")
	private String serviceIdentifier ="ARS";

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getAdult() {
		return adult;
	}

	public void setAdult(Integer adult) {
		this.adult = adult;
	}

	public Integer getChild() {
		return child;
	}

	public void setChild(Integer child) {
		this.child = child;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public Boolean getIsRoundTrip() {
		return isRoundTrip;
	}

	public void setIsRoundTrip(Boolean isRoundTrip) {
		this.isRoundTrip = isRoundTrip;
	}

	public String getServiceIdentifier() {
		return serviceIdentifier;
	}

	public void setServiceIdentifier(String serviceIdentifier) {
		this.serviceIdentifier = serviceIdentifier;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
}
