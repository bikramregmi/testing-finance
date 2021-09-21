package com.mobilebanking.plasmatech;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.NONE)
public class AvailableFlight {
	
	@XmlElement(name = "Airline")
    protected String airline;
	@XmlElement(name = "AirlineLogo")
    protected String airlineLogo;
	@XmlElement(name = "FlightDate")
    protected String flightDate;
	@XmlElement(name = "FlightNo")
    protected String flightNo;
	@XmlElement(name = "Departure")
    protected String departure;
	@XmlElement(name = "DepartureTime")
    protected String departureTime;
	@XmlElement(name = "Arrival")
    protected String arrival;
	@XmlElement(name = "ArrivalTime")
    protected String arrivalTime;
	@XmlElement(name = "AircraftType")
    protected String aircraftType;
	@XmlElement(name = "Adult")
    protected String adult;
	@XmlElement(name = "Child")
    protected String child;
	@XmlElement(name = "Infant")
    protected String infant;
	@XmlElement(name = "FlightId")
    protected String flightId;
	@XmlElement(name = "FlightClassCode")
    protected String flightClassCode;
	@XmlElement(name = "Currency")
    protected String currency;
	@XmlElement(name = "AdultFare")
    protected String adultFare;
	@XmlElement(name = "ChildFare")
    protected String childFare;
	@XmlElement(name = "InfantFare")
    protected String infantFare;
	@XmlElement(name = "ResFare")
    protected String resFare;
	@XmlElement(name = "FuelSurcharge")
    protected String fuelSurcharge;
	@XmlElement(name = "Tax")
    protected String tax;
	@XmlElement(name = "Refundable")
    protected String refundable;
	@XmlElement(name = "FreeBaggage")
    protected String freeBaggage;
	@XmlElement(name = "AgencyCommission")
    protected String agencyCommission;
	@XmlElement(name = "ChildCommission")
    protected String childCommission;
	@XmlElement(name = "CallingStationId")
    protected String callingStationId;
	@XmlElement(name = "CallingStation")
    protected String callingStation;
	
	protected Double cashBack;
	
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
	public Double getCashBack() {
		return cashBack;
	}
	public void setCashBack(Double cashBack) {
		this.cashBack = cashBack;
	}
	
	


}
