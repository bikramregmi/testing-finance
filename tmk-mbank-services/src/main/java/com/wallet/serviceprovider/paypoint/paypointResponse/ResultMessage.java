package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ResultMessage")
public class ResultMessage {
	
	@XmlElement(name="Transaction")
	private Transaction Transaction;

	public Transaction getTransaction() {
		return Transaction;
	}

	public void setTransaction(Transaction transaction) {
		Transaction = transaction;
	}
	

}

