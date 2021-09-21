package com.mobilebanking.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArsRequest {
	private String serviceIdentifier ="ARS";
	private String flightDate;
	private String returnDate;
	private String sectorFrom;
	private String sectorTo;
	private String tripType;
	private String adultNumber;
	private String childNumber;
	private Nationality nationality;
	public String getServiceIdentifier() {
		return serviceIdentifier;
	}
	public void setServiceIdentifier(String serviceIdentifier) {
		this.serviceIdentifier = serviceIdentifier;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getSectorFrom() {
		return sectorFrom;
	}
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}
	public String getSectorTo() {
		return sectorTo;
	}
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public String getAdultNumber() {
		return adultNumber;
	}
	public void setAdultNumber(String adultNumber) {
		this.adultNumber = adultNumber;
	}
	public String getChildNumber() {
		return childNumber;
	}
	public void setChildNumber(String childNumber) {
		this.childNumber = childNumber;
	}
	public Nationality getNationality() {
		return nationality;
	}
	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	
}
