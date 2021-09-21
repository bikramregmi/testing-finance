package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="DebtInfo")
public class DebtInfo {
	
	 @XmlAttribute(name="debt")
	 private String debt;
	 
	 @XmlAttribute(name="rest_count")
	 private String rest_count;

	public String getDebt() {
		return debt;
	}

	public void setDebt(String debt) {
		this.debt = debt;
	}

	public String getRest_count() {
		return rest_count;
	}

	public void setRest_count(String rest_count) {
		this.rest_count = rest_count;
	}
	 
}
