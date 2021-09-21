package com.mobilebanking.model;

import java.util.List;

public class AgentDTO {

	private Long id;
	
	private String agencyName;
	
	private String agentCode;
	
	private String accountNo;
	
	private Long parentId;
	
	private String agentType;
	
	private String timeZone;
	
	private String address;
	
	private String landline;
	
	private String mobileNo;
	
	private String documentId;
	
	private String documentType;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String ownerName;
	
	private String ownerAddress;
	
	private boolean paying;
	
	private boolean receiving;
	
	private List<CountryDTO> destinationCountryList;
	
	private String[] countryNameArray;
	
	private boolean isOnline;
	
	public boolean isPaying() {
		return paying;
	}

	public void setPaying(boolean paying) {
		this.paying = paying;
	}

	public boolean isReceiving() {
		return receiving;
	}

	public void setReceiving(boolean receiving) {
		this.receiving = receiving;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	public List<CountryDTO> getDestinationCountryList() {
		return destinationCountryList;
	}

	public void setDestinationCountryList(List<CountryDTO> destinationCountryList) {
		this.destinationCountryList = destinationCountryList;
	}

	public String[] getCountryNameArray() {
		return countryNameArray;
	}

	public void setCountryNameArray(String[] countryNameArray) {
		this.countryNameArray = countryNameArray;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}
