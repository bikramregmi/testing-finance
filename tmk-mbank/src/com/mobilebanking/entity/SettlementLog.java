package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.SettlementStatus;
import com.mobilebanking.model.SettlementType;

@Entity
@Table(name = "settlementlogs")
public class SettlementLog extends AbstractEntity<Long> {

	private static final long serialVersionUID = 4843394470437593160L;

	@ManyToOne
	private Transaction transaction;
	
	@ManyToOne
	private Bank bank;
	
	private SettlementStatus settlementStatus;
	
	private SettlementType settlementType;
	
	private String fromAccountNumber;
	
	private String toAccountNumber;
	
	private Double amount;
	
	private Double transferAmount;
	
	private String responseCode;

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public SettlementStatus getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(SettlementStatus settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public SettlementType getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(SettlementType settlementType) {
		this.settlementType = settlementType;
	}

	public String getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
	
}
