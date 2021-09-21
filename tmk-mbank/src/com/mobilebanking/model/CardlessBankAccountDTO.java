package com.mobilebanking.model;

public class CardlessBankAccountDTO {

	private Long id;

	private String bank;
	
	private String bankName;

	private String cardlessBank;

	private Long cardlessBankId;

	private String accountNumber;

	private String bankAccountNo;

	private String cardlessBankAccountNo;

	private String debitTheirRef;

	private String atmBinNo;

	private String atmTermNo;

	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardlessBank() {
		return cardlessBank;
	}

	public void setCardlessBank(String cardlessBank) {
		this.cardlessBank = cardlessBank;
	}

	public Long getCardlessBankId() {
		return cardlessBankId;
	}

	public void setCardlessBankId(Long cardlessBankId) {
		this.cardlessBankId = cardlessBankId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
