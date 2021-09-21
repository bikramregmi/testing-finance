package com.wallet.serviceprovider.epay;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="ACCOUNT")
public class Account {

	private Double balance;
	private Double overdraft;
	
	public Double getAmount() {
		return balance;
	}
	@XmlAttribute(name ="BALANCE")
	public void setAmount(Double balance) {
		this.balance = balance;
	}
	public Double getOverdraft() {
		return overdraft;
	}
	@XmlAttribute(name="OVERDRAFT")
	public void setOverdraft(Double overdraft) {
		this.overdraft = overdraft;
	}
	
	
	
}
