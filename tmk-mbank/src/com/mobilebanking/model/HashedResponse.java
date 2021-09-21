package com.mobilebanking.model;

public class HashedResponse {

	private String serviceCode;
	
	private String utilityCode;
	
	private String commissionValue;
	
	private String billNumber;
	
	private String refStan;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getUtilityCode() {
		return utilityCode;
	}

	public void setUtilityCode(String utilityCode) {
		this.utilityCode = utilityCode;
	}

	public String getCommissionValue() {
		return commissionValue;
	}

	public void setCommissionValue(String commissionValue) {
		this.commissionValue = commissionValue;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getRefStan() {
		return refStan;
	}

	public void setRefStan(String refStan) {
		this.refStan = refStan;
	}
	
	
}
