package com.mobilebanking.model;

import java.util.List;

import com.wallet.iso8583.model.MiniStatement;

public class MiniStatementRespose {

	private List<MiniStatement> ministatementList;
	
	private Double availableBalance ;
	
	private String balanceDate;

	public List<MiniStatement> getMinistatementList() {
		return ministatementList;
	}

	public void setMinistatementList(List<MiniStatement> ministatementList) {
		this.ministatementList = ministatementList;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}
	
}
