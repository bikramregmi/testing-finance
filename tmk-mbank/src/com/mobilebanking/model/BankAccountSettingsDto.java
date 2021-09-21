/**
 * 
 */
package com.mobilebanking.model;

/**
 * @author bibek
 *
 */
public class BankAccountSettingsDto {
	
	private long id;
		
	private String bank;
	
	private String operatorAccountNumber;
	
	private String channelPartnerAccountNumber;
	
	private String bankPoolAccountNumber;
	
	private String bankParkingAccountNumber;
	
	private Status status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getOperatorAccountNumber() {
		return operatorAccountNumber;
	}

	public void setOperatorAccountNumber(String operatorAccountNumber) {
		this.operatorAccountNumber = operatorAccountNumber;
	}

	public String getChannelPartnerAccountNumber() {
		return channelPartnerAccountNumber;
	}

	public void setChannelPartnerAccountNumber(String channelPartnerAccountNumber) {
		this.channelPartnerAccountNumber = channelPartnerAccountNumber;
	}

	public String getBankPoolAccountNumber() {
		return bankPoolAccountNumber;
	}

	public void setBankPoolAccountNumber(String bankPoolAccountNumber) {
		this.bankPoolAccountNumber = bankPoolAccountNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getBankParkingAccountNumber() {
		return bankParkingAccountNumber;
	}

	public void setBankParkingAccountNumber(String bankParkingAccountNumber) {
		this.bankParkingAccountNumber = bankParkingAccountNumber;
	}

}
