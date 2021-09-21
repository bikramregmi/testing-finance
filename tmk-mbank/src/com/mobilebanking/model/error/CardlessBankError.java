package com.mobilebanking.model.error;

public class CardlessBankError {
	
	private String bank;

	private String host;

	private String port;

	private String userSign;

	private String userPassword;

	private String companyCode;
	
	private String bankAccountNo;

	private String cardlessBankAccountNo;

	private String debitTheirRef;

	private String atmBinNo;

	private String atmTermNo;
	
	private String city;
	
	private String state;
	
	private String address;
	
	private boolean valid;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserSign() {
		return userSign;
	}

	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getCardlessBankAccountNo() {
		return cardlessBankAccountNo;
	}

	public void setCardlessBankAccountNo(String cardlessBankAccountNo) {
		this.cardlessBankAccountNo = cardlessBankAccountNo;
	}

	public String getDebitTheirRef() {
		return debitTheirRef;
	}

	public void setDebitTheirRef(String debitTheirRef) {
		this.debitTheirRef = debitTheirRef;
	}

	public String getAtmBinNo() {
		return atmBinNo;
	}

	public void setAtmBinNo(String atmBinNo) {
		this.atmBinNo = atmBinNo;
	}

	public String getAtmTermNo() {
		return atmTermNo;
	}

	public void setAtmTermNo(String atmTermNo) {
		this.atmTermNo = atmTermNo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
