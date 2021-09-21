package com.mobilebanking.model;

import java.util.List;

public class BankCommissionDtoList {

	private List<BankCommissionDTO> commissionAmounts;
	
	private Long bankId;
	
	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public List<BankCommissionDTO> getCommissionAmounts() {
		return commissionAmounts;
	}

	public void setCommissionAmounts(List<BankCommissionDTO> commissionAmounts) {
		this.commissionAmounts = commissionAmounts;
	}
	
}
