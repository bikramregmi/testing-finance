package com.wallet.serviceprovider.unitedsolutions.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.serviceprovider.unitedsolutions.response.InBound;
import com.wallet.serviceprovider.unitedsolutions.response.OutBound;

@XmlRootElement(name="Flightavailability")
@XmlAccessorType(XmlAccessType.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightAvaibilityResponse {

	@JsonProperty("inbound")
	private InBound inBound;
	
	private OutBound outBound;
	
	public InBound getInBound() {
		return inBound;
	}
	@XmlElement(name="InBound")
	
	public void setInBound(InBound inBound) {
		this.inBound = inBound;
	}

	public OutBound getOutBound() {
		return outBound;
	}

	@XmlElement(name="Outbound")
	public void setOutBound(OutBound outBound) {
		this.outBound = outBound;
	}
	
	
	
}
