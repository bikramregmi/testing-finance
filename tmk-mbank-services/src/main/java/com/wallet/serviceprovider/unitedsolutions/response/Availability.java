package com.wallet.serviceprovider.unitedsolutions.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name="Availability")
@XmlAccessorType(XmlAccessType.NONE)
public class Availability {

	@XmlTransient
//	@XmlElement(name = "Airline")
	private String airline;
	
	@XmlTransient
//	@XmlElement(name="AirlineLogo")
	private String airlineLogo;
	
	@XmlTransient
//	@XmlElement(name="FlightDate")
	private String flightDate;
	
	@XmlTransient
//	@XmlElement(name="FlightNo")
	private String flightNo;
	
	@XmlTransient
//	@XmlElement(name="Departure")
	private String departure;
	@XmlTransient
//	@XmlElement(name="DepartureTime")
	private String departureTime;
	
	@XmlTransient
//	@XmlElement(name="Arrival")
	private String arrival;
	
	@XmlTransient
//	@XmlElement(name="ArrivalTime")
	private String arrivalTime;
	
	@XmlTransient
//	@XmlElement(name="AircraftType")
	private String aircraftType;

	@XmlTransient
//	@XmlElement(name="Adult")
	private String adult;
	
	@XmlTransient
//	@XmlElement(name="Child")
	private String child;
	
	@XmlTransient
//	@XmlElement(name="Infant")
	private String infant;
	
	@XmlTransient
//	@XmlElement(name="FlightId")
	private String flightId;
	
	@XmlTransient
//	@XmlElement(name="FlightClassCode")
	private String flightClassCode;
	
	@XmlTransient
//	@XmlElement(name="Currency")
	private String currency;
	
	@XmlTransient
//	@XmlElement(name="AdultFare")
	private String adultFare;
	
	@XmlTransient
//	@XmlElement(name="ChildFare")
	private String childFare;
	
	@XmlTransient
//	@XmlElement(name="InfantFare")
	private String infantFare;
	
	@XmlTransient
//	@XmlElement(name="ResFare")
	private String resFare;
	
	@XmlTransient
//	@XmlElement(name="FuelSurcharge")
	private String fuelSurcharge;
	
	@XmlTransient
//	@XmlElement(name="Tax")
	private String tax;
	
	@XmlTransient
//	@XmlElement(name="Refundable")
	private String refundable;
	
	@XmlTransient
//	@XmlElement(name="FreeBaggage")
	private String freeBaggage;
	
	@XmlTransient
//	@XmlElement(name="AgencyCommission")
	private String agencyCommission;
	
	@XmlTransient
//	@XmlElement(name="ChildCommission")
	private String childCommission;
	
	@XmlTransient
//	@XmlElement(name="CallingStationId")
	private String callingStationId;
	@XmlTransient
//	@XmlElement(name="CallingStation")
	private String callingStation;

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getAirlineLogo() {
		return airlineLogo;
	}

	public void setAirlineLogo(String airlineLogo) {
		this.airlineLogo = airlineLogo;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getInfant() {
		return infant;
	}

	public void setInfant(String infant) {
		this.infant = infant;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getFlightClassCode() {
		return flightClassCode;
	}

	public void setFlightClassCode(String flightClassCode) {
		this.flightClassCode = flightClassCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAdultFare() {
		return adultFare;
	}

	public void setAdultFare(String adultFare) {
		this.adultFare = adultFare;
	}

	public String getChildFare() {
		return childFare;
	}

	public void setChildFare(String childFare) {
		this.childFare = childFare;
	}

	public String getInfantFare() {
		return infantFare;
	}

	public void setInfantFare(String infantFare) {
		this.infantFare = infantFare;
	}

	public String getResFare() {
		return resFare;
	}

	public void setResFare(String resFare) {
		this.resFare = resFare;
	}

	public String getFuelSurcharge() {
		return fuelSurcharge;
	}

	public void setFuelSurcharge(String fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getRefundable() {
		return refundable;
	}

	public void setRefundable(String refundable) {
		this.refundable = refundable;
	}

	public String getFreeBaggage() {
		return freeBaggage;
	}

	public void setFreeBaggage(String freeBaggage) {
		this.freeBaggage = freeBaggage;
	}

	public String getAgencyCommission() {
		return agencyCommission;
	}

	public void setAgencyCommission(String agencyCommission) {
		this.agencyCommission = agencyCommission;
	}

	public String getChildCommission() {
		return childCommission;
	}

	public void setChildCommission(String childCommission) {
		this.childCommission = childCommission;
	}

	public String getCallingStationId() {
		return callingStationId;
	}

	public void setCallingStationId(String callingStationId) {
		this.callingStationId = callingStationId;
	}

	public String getCallingStation() {
		return callingStation;
	}

	public void setCallingStation(String callingStation) {
		this.callingStation = callingStation;
	}
	
	
}
