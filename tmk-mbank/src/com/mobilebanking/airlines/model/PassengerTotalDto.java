package com.mobilebanking.airlines.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mobilebanking.plasmatech.PassengerDto;

@JsonIgnoreProperties(value = { "pnrNo" },ignoreUnknown = true)
public class PassengerTotalDto {

	private String serviceIdentifier = "ARS";

	private String contactName;
	@JsonProperty("contactEmail")
	private String email;
	@JsonProperty("contactNumber")
	private String number;

	private String amount;

	@JsonProperty("flightId")
	private String strFlightId;
	@JsonProperty("returnFlightId")
	private String strReturnFlightId;

	private String channel;
	
	@JsonProperty("pnrNo")
	private String pnrNo;

	private String reservationStatus;
	@JsonProperty("feeTax")
	private String feeAndTax;

	private String agencyCommission;
	@JsonProperty("totalPassenger")
	private String totalPessanger;
	@JsonProperty("issueTicketRequest")
	private List<PassengerDto> passengerDto;

	private String mobilePin;
	
	private String airlineId;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String accountNumber;

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

	public String getStrReturnFlightId() {
		return strReturnFlightId;
	}

	public void setStrReturnFlightId(String strReturnFlightId) {
		this.strReturnFlightId = strReturnFlightId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPnrNo() {
		return pnrNo;
	}

	public void setPnrNo(String pnrNo) {
		this.pnrNo = pnrNo;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public String getFeeAndTax() {
		return feeAndTax;
	}

	public void setFeeAndTax(String feeAndTax) {
		this.feeAndTax = feeAndTax;
	}

	public String getAgencyCommission() {
		return agencyCommission;
	}

	public void setAgencyCommission(String agencyCommission) {
		this.agencyCommission = agencyCommission;
	}

	public String getTotalPessanger() {
		return totalPessanger;
	}

	public void setTotalPessanger(String totalPessanger) {
		this.totalPessanger = totalPessanger;
	}

	public List<PassengerDto> getPassengerDto() {
		return passengerDto;
	}

	public void setPassengerDto(List<PassengerDto> passengerDto) {
		this.passengerDto = passengerDto;
	}

	public String getMobilePin() {
		return mobilePin;
	}

	public void setMobilePin(String mobilePin) {
		this.mobilePin = mobilePin;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(String airlineId) {
		this.airlineId = airlineId;
	}

}
