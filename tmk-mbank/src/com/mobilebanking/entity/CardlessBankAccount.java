package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

@Entity
@Table(name = "cardlessbankaccount")
public class CardlessBankAccount extends AbstractEntity<Long> {

	private static final long serialVersionUID = -7903599200862310528L;

	@ManyToOne(fetch = FetchType.EAGER)
	private Bank bank;

	@ManyToOne(fetch = FetchType.EAGER)
	private CardlessBank cardlessBank;

	private String accountNumber;

	private Status status;

	private String bankAccountNo;

	private String cardlessBankAccountNo;

	private String debitTheirRef;

	private String atmBinNo;

	private String atmTermNo;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public CardlessBank getCardlessBank() {
		return cardlessBank;
	}

	public void setCardlessBank(CardlessBank cardlessBank) {
		this.cardlessBank = cardlessBank;
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

}
