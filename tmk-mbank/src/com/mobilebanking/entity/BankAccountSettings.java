/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="bankAccountSettings")
public class BankAccountSettings extends AbstractEntity<Long> {

	private static final long serialVersionUID = -3568671252458622955L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@Column(nullable=false)
	private String operatorAccountNumber;
	
	@Column(nullable=false)
	private String channelPartnerAccountNumber;
	
	@Column(nullable=false)
	private String bankPoolAccountNumber;
	
	@Column(nullable=false)
	private String bankParkingAccountNumber;
	
	@Column
	private Status status;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
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
