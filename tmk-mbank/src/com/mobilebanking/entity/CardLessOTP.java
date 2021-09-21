package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cardlessotp")
public class CardLessOTP extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String transactionIdentifier;
	
	private String mobileNumber;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private CardlessBank cardlessBank;
	
	private Double amount;
	
	private Double feeCharge;
	
	private String accountNumber;
	
	private Boolean isWithDrawn = false;
	
	private Boolean isExpired =  false;	
	
	@OneToOne
	private OfsResponse ofsResponse;
	
	private String bit90;
	
	private String bit11;
	
	private Boolean isReversed = false;
	
	@OneToOne(fetch = FetchType.EAGER)
	private OtpInfo otpinfo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Bank bank;

	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public CardlessBank getCardlessBank() {
		return cardlessBank;
	}

	public void setCardlessBank(CardlessBank cardlessBank) {
		this.cardlessBank = cardlessBank;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFeeCharge() {
		return feeCharge;
	}

	public void setFeeCharge(Double feeCharge) {
		this.feeCharge = feeCharge;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public OtpInfo getOtpinfo() {
		return otpinfo;
	}

	public void setOtpinfo(OtpInfo otpinfo) {
		this.otpinfo = otpinfo;
	}

	public Boolean getIsWithDrawn() {
		return isWithDrawn;
	}

	public void setIsWithDrawn(Boolean isWithDrawn) {
		this.isWithDrawn = isWithDrawn;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getBit90() {
		return bit90;
	}

	public void setBit90(String bit90) {
		this.bit90 = bit90;
	}

	public String getBit11() {
		return bit11;
	}

	public void setBit11(String bit11) {
		this.bit11 = bit11;
	}

	public Boolean getIsReversed() {
		return isReversed;
	}

	public void setIsReversed(Boolean isReversed) {
		this.isReversed = isReversed;
	}

	public OfsResponse getOfsResponse() {
		return ofsResponse;
	}

	public void setOfsResponse(OfsResponse ofsResponse) {
		this.ofsResponse = ofsResponse;
	}
	
	
}
