package com.mobilebanking.model;

import java.util.List;

public class CardlessBankFeeDTOList {
	
	private Long cardlessBankId;
	
	private boolean sameforall;
	
	private Double fee;
	
	private List<CardlessBankFeeDTO> feeList;

	public Long getCardlessBankId() {
		return cardlessBankId;
	}

	public void setCardlessBankId(Long cardlessBankId) {
		this.cardlessBankId = cardlessBankId;
	}

	public List<CardlessBankFeeDTO> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<CardlessBankFeeDTO> feeList) {
		this.feeList = feeList;
	}

	public boolean isSameforall() {
		return sameforall;
	}

	public void setSameforall(boolean sameforall) {
		this.sameforall = sameforall;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

}
