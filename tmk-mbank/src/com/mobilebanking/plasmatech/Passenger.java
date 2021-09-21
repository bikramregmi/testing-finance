package com.mobilebanking.plasmatech;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown =  true)
public class Passenger {
	
	@JsonProperty("airline")
	private String airline;
	@JsonProperty("flightId")
	private Object flightId;
	@JsonProperty("title")
	private String title;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("paxNo")
	private String paxNo;
	@JsonProperty("paxType")
	private String paxType;
	@JsonProperty("nationality")
	private String nationality;
	@JsonProperty("paxId")
	private String paxId;
	@JsonProperty("issueFrom")
	private String issueFrom;
	@JsonProperty("agencyName")
	private String agencyName;
	@JsonProperty("issueDate")
	private String issueDate;
	@JsonProperty("issueBy")
	private String issueBy;
	@JsonProperty("flightNo")
	private String flightNo;
	@JsonProperty("flightDate")
	private String flightDate;
	@JsonProperty("departure")
	private String departure;
	@JsonProperty("flightTime")
	private String flightTime;
	@JsonProperty("ticketNo")
	private String ticketNo;
	@JsonProperty("barCodeValue")
	private String barCodeValue;
	@JsonProperty("barCodeImage")
	private Object barCodeImage;
	@JsonProperty("arrival")
	private String arrival;
	@JsonProperty("arrivalTime")
	private String arrivalTime;
	@JsonProperty("sector")
	private String sector;
	@JsonProperty("classCode")
	private String classCode;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("fare")
	private String fare;
	@JsonProperty("surcharge")
	private String surcharge;
	@JsonProperty("taxCurrency")
	private String taxCurrency;
	@JsonProperty("tax")
	private String tax;
	@JsonProperty("commissionPercentage")
	private Object commissionPercentage;
	@JsonProperty("reportingTime")
	private String reportingTime;
	@JsonProperty("freeBaggage")
	private String freeBaggage;
	@JsonProperty("pnrNo")
	private String pnrNo;
	@JsonProperty("refundable")
	private String refundable;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("airline")
	public String getAirline() {
	return airline;
	}

	@JsonProperty("airline")
	public void setAirline(String airline) {
	this.airline = airline;
	}

	@JsonProperty("flightId")
	public Object getFlightId() {
	return flightId;
	}

	@JsonProperty("flightId")
	public void setFlightId(Object flightId) {
	this.flightId = flightId;
	}

	@JsonProperty("title")
	public String getTitle() {
	return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
	this.title = title;
	}

	@JsonProperty("gender")
	public String getGender() {
	return gender;
	}

	@JsonProperty("gender")
	public void setGender(String gender) {
	this.gender = gender;
	}

	@JsonProperty("firstName")
	public String getFirstName() {
	return firstName;
	}

	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
	return lastName;
	}

	@JsonProperty("lastName")
	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	@JsonProperty("paxNo")
	public String getPaxNo() {
	return paxNo;
	}

	@JsonProperty("paxNo")
	public void setPaxNo(String paxNo) {
	this.paxNo = paxNo;
	}

	@JsonProperty("paxType")
	public String getPaxType() {
	return paxType;
	}

	@JsonProperty("paxType")
	public void setPaxType(String paxType) {
	this.paxType = paxType;
	}

	@JsonProperty("nationality")
	public String getNationality() {
	return nationality;
	}

	@JsonProperty("nationality")
	public void setNationality(String nationality) {
	this.nationality = nationality;
	}

	@JsonProperty("paxId")
	public String getPaxId() {
	return paxId;
	}

	@JsonProperty("paxId")
	public void setPaxId(String paxId) {
	this.paxId = paxId;
	}

	@JsonProperty("issueFrom")
	public String getIssueFrom() {
	return issueFrom;
	}

	@JsonProperty("issueFrom")
	public void setIssueFrom(String issueFrom) {
	this.issueFrom = issueFrom;
	}

	@JsonProperty("agencyName")
	public String getAgencyName() {
	return agencyName;
	}

	@JsonProperty("agencyName")
	public void setAgencyName(String agencyName) {
	this.agencyName = agencyName;
	}

	@JsonProperty("issueDate")
	public String getIssueDate() {
	return issueDate;
	}

	@JsonProperty("issueDate")
	public void setIssueDate(String issueDate) {
	this.issueDate = issueDate;
	}

	@JsonProperty("issueBy")
	public String getIssueBy() {
	return issueBy;
	}

	@JsonProperty("issueBy")
	public void setIssueBy(String issueBy) {
	this.issueBy = issueBy;
	}

	@JsonProperty("flightNo")
	public String getFlightNo() {
	return flightNo;
	}

	@JsonProperty("flightNo")
	public void setFlightNo(String flightNo) {
	this.flightNo = flightNo;
	}

	@JsonProperty("flightDate")
	public String getFlightDate() {
	return flightDate;
	}

	@JsonProperty("flightDate")
	public void setFlightDate(String flightDate) {
	this.flightDate = flightDate;
	}

	@JsonProperty("departure")
	public String getDeparture() {
	return departure;
	}

	@JsonProperty("departure")
	public void setDeparture(String departure) {
	this.departure = departure;
	}

	@JsonProperty("flightTime")
	public String getFlightTime() {
	return flightTime;
	}

	@JsonProperty("flightTime")
	public void setFlightTime(String flightTime) {
	this.flightTime = flightTime;
	}

	@JsonProperty("ticketNo")
	public String getTicketNo() {
	return ticketNo;
	}

	@JsonProperty("ticketNo")
	public void setTicketNo(String ticketNo) {
	this.ticketNo = ticketNo;
	}

	@JsonProperty("barCodeValue")
	public String getBarCodeValue() {
	return barCodeValue;
	}

	@JsonProperty("barCodeValue")
	public void setBarCodeValue(String barCodeValue) {
	this.barCodeValue = barCodeValue;
	}

	@JsonProperty("barCodeImage")
	public Object getBarCodeImage() {
	return barCodeImage;
	}

	@JsonProperty("barCodeImage")
	public void setBarCodeImage(Object barCodeImage) {
	this.barCodeImage = barCodeImage;
	}

	@JsonProperty("arrival")
	public String getArrival() {
	return arrival;
	}

	@JsonProperty("arrival")
	public void setArrival(String arrival) {
	this.arrival = arrival;
	}

	@JsonProperty("arrivalTime")
	public String getArrivalTime() {
	return arrivalTime;
	}

	@JsonProperty("arrivalTime")
	public void setArrivalTime(String arrivalTime) {
	this.arrivalTime = arrivalTime;
	}

	@JsonProperty("sector")
	public String getSector() {
	return sector;
	}

	@JsonProperty("sector")
	public void setSector(String sector) {
	this.sector = sector;
	}

	@JsonProperty("classCode")
	public String getClassCode() {
	return classCode;
	}

	@JsonProperty("classCode")
	public void setClassCode(String classCode) {
	this.classCode = classCode;
	}

	@JsonProperty("currency")
	public String getCurrency() {
	return currency;
	}

	@JsonProperty("currency")
	public void setCurrency(String currency) {
	this.currency = currency;
	}

	@JsonProperty("fare")
	public String getFare() {
	return fare;
	}

	@JsonProperty("fare")
	public void setFare(String fare) {
	this.fare = fare;
	}

	@JsonProperty("surcharge")
	public String getSurcharge() {
	return surcharge;
	}

	@JsonProperty("surcharge")
	public void setSurcharge(String surcharge) {
	this.surcharge = surcharge;
	}

	@JsonProperty("taxCurrency")
	public String getTaxCurrency() {
	return taxCurrency;
	}

	@JsonProperty("taxCurrency")
	public void setTaxCurrency(String taxCurrency) {
	this.taxCurrency = taxCurrency;
	}

	@JsonProperty("tax")
	public String getTax() {
	return tax;
	}

	@JsonProperty("tax")
	public void setTax(String tax) {
	this.tax = tax;
	}

	@JsonProperty("commissionPercentage")
	public Object getCommissionPercentage() {
	return commissionPercentage;
	}

	@JsonProperty("commissionPercentage")
	public void setCommissionPercentage(Object commissionPercentage) {
	this.commissionPercentage = commissionPercentage;
	}

	@JsonProperty("reportingTime")
	public String getReportingTime() {
	return reportingTime;
	}

	@JsonProperty("reportingTime")
	public void setReportingTime(String reportingTime) {
	this.reportingTime = reportingTime;
	}

	@JsonProperty("freeBaggage")
	public String getFreeBaggage() {
	return freeBaggage;
	}

	@JsonProperty("freeBaggage")
	public void setFreeBaggage(String freeBaggage) {
	this.freeBaggage = freeBaggage;
	}

	@JsonProperty("pnrNo")
	public String getPnrNo() {
	return pnrNo;
	}

	@JsonProperty("pnrNo")
	public void setPnrNo(String pnrNo) {
	this.pnrNo = pnrNo;
	}

	@JsonProperty("refundable")
	public String getRefundable() {
	return refundable;
	}

	@JsonProperty("refundable")
	public void setRefundable(String refundable) {
	this.refundable = refundable;
	}
	}
