package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

@Entity
@Table(name="cardlessbankfee")
public class CardlessBankFee extends AbstractEntity<Long>{

	private static final long serialVersionUID = -8049262238278064178L;
	
	@ManyToOne
	private CardlessBank cardlessBank;
	
	private Boolean sameForAll;
	
	private Double fromAmount;
	
	private Double toAmount;
	
	private Double fee;
	
	private Status status;

	public CardlessBank getCardlessBank() {
		return cardlessBank;
	}

	public void setCardlessBank(CardlessBank cardlessBank) {
		this.cardlessBank = cardlessBank;
	}

	public Double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(Double fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Double getToAmount() {
		return toAmount;
	}

	public void setToAmount(Double toAmount) {
		this.toAmount = toAmount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Boolean getSameForAll() {
		return sameForAll;
	}

	public void setSameForAll(Boolean sameForAll) {
		this.sameForAll = sameForAll;
	}

}
