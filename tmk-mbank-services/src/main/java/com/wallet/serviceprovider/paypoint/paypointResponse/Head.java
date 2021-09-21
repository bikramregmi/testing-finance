package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="head")
public class Head {
	
	
	@XmlElement(name = "account")
    private Account account;

	@XmlElement(name = "sum")
    private Sum sum;

	@XmlElement(name = "commission")
    private Commission commission;

	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;	
	}

	public Sum getSum() {
		return sum;
	}

	public void setSum(Sum sum) {
		this.sum = sum;
	}

	public Commission getCommission() {
		return commission;
	}

	public void setCommission(Commission commission) {
		this.commission = commission;
	}
	
	
	
	
}
