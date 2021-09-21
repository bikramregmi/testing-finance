package com.mobilebanking.model;

public class CardlessBankFeeDTO {
	
	private Long id;
	
	private Long cardlessBankId;
	
	private String cardlessBank;
	
	private Double fromAmount;

	private Double toAmount;

	private Double fee;
	
	private Boolean sameForAll;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCardlessBankId() {
		return cardlessBankId;
	}

	public void setCardlessBankId(Long cardlessBankId) {
		this.cardlessBankId = cardlessBankId;
	}

	public String getCardlessBank() {
		return cardlessBank;
	}

	public void setCardlessBank(String cardlessBank) {
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

	public Boolean getSameForAll() {
		return sameForAll;
	}

	public void setSameForAll(Boolean sameForAll) {
		this.sameForAll = sameForAll;
	}

}
