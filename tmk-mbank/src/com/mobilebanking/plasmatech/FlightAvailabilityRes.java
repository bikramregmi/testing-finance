
package com.mobilebanking.plasmatech;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Flightavailability")
@JsonIgnoreProperties(ignoreUnknown =true)
public class FlightAvailabilityRes {

	@XmlElement(name = "Outbound")
    protected ArrayOfAvailableFlight outbound;
	
    @XmlElement(name = "Inbound")
    protected ArrayOfAvailableFlight inbound;
    
    
	public ArrayOfAvailableFlight getOutbound() {
		return outbound;
	}
	public void setOutbound(ArrayOfAvailableFlight outbound) {
		this.outbound = outbound;
	}
	public ArrayOfAvailableFlight getInbound() {
		return inbound;
	}
	public void setInbound(ArrayOfAvailableFlight inbound) {
		this.inbound = inbound;
	}
    
	
    
    


}
