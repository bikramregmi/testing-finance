package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="UtilityInfo")
public class UtilityInfo {
	
	private String utilityCode;

	public UtilityInfo() {
	}

	public String getUtilityCode() {
		return utilityCode;
	}
	
	@XmlElement(name="UtilityCode")
	public void setUtilityCode(String utilityCode) {
		this.utilityCode = utilityCode;
	}
}
