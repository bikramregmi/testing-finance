package com.mobilebanking.airlines.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueTicketRequestDetail {

	@JsonProperty("serviceCategoryName")
	private String serviceCategoryName;
	@JsonProperty("contactMobile")
	private String contactMobile;
	@JsonProperty("amount")
	private String amount;
	@JsonProperty("feeAndTax")
	private String feeAndTax;
	@JsonProperty("contactEmail")
	private String contactEmail;
	@JsonProperty("contactName")
	private String contactName;
	@JsonProperty("merchantManagerId")
	private String merchantManagerId;
	@JsonProperty("channel")
	private String channel;
	@JsonProperty("serviceName")
	private String serviceName;
	@JsonProperty("merchantName")
	private String merchantName;
	@JsonProperty("agencyCommission")
	private String agencyCommission;
	@JsonProperty("requestDate")
	private String requestDate;
	@JsonProperty("serviceId")
	private String serviceId;
	@JsonProperty("serviceTo")
	private String serviceTo;
	@JsonProperty("reservationStatus")
	private String reservationStatus;
	
	@JsonProperty("serviceCategoryName")
	public String getServiceCategoryName() {
	return serviceCategoryName;
	}

	@JsonProperty("serviceCategoryName")
	public void setServiceCategoryName(String serviceCategoryName) {
	this.serviceCategoryName = serviceCategoryName;
	}

	@JsonProperty("contactMobile")
	public String getContactMobile() {
	return contactMobile;
	}

	@JsonProperty("contactMobile")
	public void setContactMobile(String contactMobile) {
	this.contactMobile = contactMobile;
	}

	@JsonProperty("amount")
	public String getAmount() {
	return amount;
	}

	@JsonProperty("amount")
	public void setAmount(String amount) {
	this.amount = amount;
	}

	@JsonProperty("feeAndTax")
	public String getFeeAndTax() {
	return feeAndTax;
	}

	@JsonProperty("feeAndTax")
	public void setFeeAndTax(String feeAndTax) {
	this.feeAndTax = feeAndTax;
	}

	@JsonProperty("contactEmail")
	public String getContactEmail() {
	return contactEmail;
	}

	@JsonProperty("contactEmail")
	public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
	}

	@JsonProperty("contactName")
	public String getContactName() {
	return contactName;
	}

	@JsonProperty("contactName")
	public void setContactName(String contactName) {
	this.contactName = contactName;
	}

	@JsonProperty("merchantManagerId")
	public String getMerchantManagerId() {
	return merchantManagerId;
	}

	@JsonProperty("merchantManagerId")
	public void setMerchantManagerId(String merchantManagerId) {
	this.merchantManagerId = merchantManagerId;
	}

	@JsonProperty("channel")
	public String getChannel() {
	return channel;
	}

	@JsonProperty("channel")
	public void setChannel(String channel) {
	this.channel = channel;
	}

	@JsonProperty("serviceName")
	public String getServiceName() {
	return serviceName;
	}

	@JsonProperty("serviceName")
	public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
	}

	@JsonProperty("merchantName")
	public String getMerchantName() {
	return merchantName;
	}

	@JsonProperty("merchantName")
	public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
	}

	@JsonProperty("agencyCommission")
	public String getAgencyCommission() {
	return agencyCommission;
	}

	@JsonProperty("agencyCommission")
	public void setAgencyCommission(String agencyCommission) {
	this.agencyCommission = agencyCommission;
	}

	@JsonProperty("requestDate")
	public String getRequestDate() {
	return requestDate;
	}

	@JsonProperty("requestDate")
	public void setRequestDate(String requestDate) {
	this.requestDate = requestDate;
	}

	@JsonProperty("serviceId")
	public String getServiceId() {
	return serviceId;
	}

	@JsonProperty("serviceId")
	public void setServiceId(String serviceId) {
	this.serviceId = serviceId;
	}

	@JsonProperty("serviceTo")
	public String getServiceTo() {
	return serviceTo;
	}

	@JsonProperty("serviceTo")
	public void setServiceTo(String serviceTo) {
	this.serviceTo = serviceTo;
	}

	@JsonProperty("reservationStatus")
	public String getReservationStatus() {
	return reservationStatus;
	}

	@JsonProperty("reservationStatus")
	public void setReservationStatus(String reservationStatus) {
	this.reservationStatus = reservationStatus;
	}

	
}
