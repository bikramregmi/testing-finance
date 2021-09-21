package com.wallet.serviceprovider.epay;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OPERATION")
public class Operation {

	private Long transaction;
	
	private Long state;

	public Long getTransaction() {
		return transaction;
	}

	@XmlAttribute(name = "TRANSACTION")
	public void setTransaction(Long transaction) {
		this.transaction = transaction;
	}

	public Long getState() {
		return state;
	}
	@XmlAttribute(name = "STATE")
	public void setState(Long state) {
		this.state = state;
	}
	
	
	
	
}
