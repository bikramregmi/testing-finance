package com.wallet.serviceprovider.epay;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="KKM_PG_GATE")
public class RequestQueryResponse {

	
	
	private String operatorDate;
	
	private BalanceQueryResponseError error;
	
	private Account account;
	
	private Operation operation;
	
	public BalanceQueryResponseError getError() {
		return error;
	}
	@XmlElement(name="ERROR")
	public void setError(BalanceQueryResponseError error) {
		this.error = error;
	}
	public Account getAccount() {
		return account;
	}
	@XmlElement(name ="ACCOUNT")
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getOPERATION_DATE() {
		return operatorDate;
	}
	@XmlElement(name ="OPERATION_DATE")
	public void setOPERATION_DATE(String operatorDate) {
		this.operatorDate = operatorDate;
	}
	
	public Operation getOperation() {
		return operation;
	}
	@XmlElement(name ="OPERATION")
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	
	
	
/*	
	 @Override
	    public String toString() {
	        return "RequestQueryResponse{" +
	                "error=" + error +
	                ", account='" + account + '\'' +
	                '}';
	    }*/
	
}
