package com.wallet.serviceprovider.bussewa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TripResponse {

	private String id;
	private boolean isLocked;
	private String operator;
	private String busNo;
	private String busType;
	private String vehicleType;
	private String date;
	private String commissionPercentage;
	private String inputField;
	private String ticketPrice;
	private  boolean multiDetail;
	private String departureTime;
	private String bookedSeats;
	private String unbookedSeats;
	private String totalSeats;
	private String removeableSeats;
	private String invisibleSeats;
	private boolean isBusTypeLayout;
	private String ammenities;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getBusNo() {
		return busNo;
	}
	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCommissionPercentage() {
		return commissionPercentage;
	}
	public void setCommissionPercentage(String commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}
	public String getInputField() {
		return inputField;
	}
	public void setInputField(String inputField) {
		this.inputField = inputField;
	}
	public String getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(String bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public String getUnbookedSeats() {
		return unbookedSeats;
	}
	public void setUnbookedSeats(String unbookedSeats) {
		this.unbookedSeats = unbookedSeats;
	}
	public String getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(String totalSeats) {
		this.totalSeats = totalSeats;
	}
	public String getRemoveableSeats() {
		return removeableSeats;
	}
	public void setRemoveableSeats(String removeableSeats) {
		this.removeableSeats = removeableSeats;
	}
	public String getAmmenities() {
		return ammenities;
	}
	public void setAmmenities(String ammenities) {
		this.ammenities = ammenities;
	}
	
	public boolean isMultiDetail() {
		return multiDetail;
	}
	public void setMultiDetail(boolean multiDetail) {
		this.multiDetail = multiDetail;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public boolean isBusTypeLayout() {
		return isBusTypeLayout;
	}
	public void setBusTypeLayout(boolean isBusTypeLayout) {
		this.isBusTypeLayout = isBusTypeLayout;
	}
	
	public String getInvisibleSeats() {
		return invisibleSeats;
	}
	public void setInvisibleSeats(String invisibleSeats) {
		this.invisibleSeats = invisibleSeats;
	}
	@Override
	public String toString() {
		return "Trip [id=" + id + ", isLocked=" + isLocked + ", operator=" + operator + ", busNo=" + busNo
				+ ", busType=" + busType + ", vehicleType=" + vehicleType + ", date=" + date + ", commissionPercentage="
				+ commissionPercentage + ", inputField=" + inputField + ", ticketPrice=" + ticketPrice
				+ ", multiDetail=" + multiDetail + ", departureTime=" + departureTime + ", bookedSeats=" + bookedSeats
				+ ", unbookedSeats=" + unbookedSeats + ", totalSeats=" + totalSeats + ", removeableSeats="
				+ removeableSeats + ", invisibleSeats=" + invisibleSeats + ", isBusTypeLayout=" + isBusTypeLayout
				+ ", ammenities=" + ammenities + "]";
	}
	
	
	
	
	
}
