package com.wallet.serviceprovider.paypointResponse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name= "")
public class PPResponse {

	private String result;
	
	private String key;
	
	private String resultMessage;
	
	private  UtilityInfo utilityInfo;
	
	private BillInfo billInfo; 
	
	public String getResult() {
		return result;
	}

	
	public void setResult(String result) {
		this.result = result;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}


	public UtilityInfo getUtilityInfo() {
		return utilityInfo;
	}


	public void setUtilityInfo(UtilityInfo utilityInfo) {
		this.utilityInfo = utilityInfo;
	}
	
	
}
