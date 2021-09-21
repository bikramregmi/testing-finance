package com.mobilebanking.airlines.model;

public class FlightAvaibilityRequest {
	
	private String service_identifier;

private String nationality;
	
	private String sector_from;
	
	private String sector_to;
	
	private Integer adult_number;
	
	private Integer child_number;
	
	private String flight_date;
	
	private String return_date;
	
	private String trip_type;

	public String getService_identifier() {
		return service_identifier;
	}

	public void setService_identifier(String service_identifier) {
		this.service_identifier = service_identifier;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getSector_from() {
		return sector_from;
	}

	public void setSector_from(String sector_from) {
		this.sector_from = sector_from;
	}

	public String getSector_to() {
		return sector_to;
	}

	public void setSector_to(String sector_to) {
		this.sector_to = sector_to;
	}

	public Integer getAdult_number() {
		return adult_number;
	}

	public void setAdult_number(Integer adult_number) {
		this.adult_number = adult_number;
	}

	public Integer getChild_number() {
		return child_number;
	}

	public void setChild_number(Integer child_number) {
		this.child_number = child_number;
	}

	public String getFlight_date() {
		return flight_date;
	}

	public void setFlight_date(String flight_date) {
		this.flight_date = flight_date;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	public String getTrip_type() {
		return trip_type;
	}

	public void setTrip_type(String trip_type) {
		this.trip_type = trip_type;
	}
	
}
