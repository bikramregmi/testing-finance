package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Outbound {
	@XmlElement
	private Flightinfo Flightinfo;

	public Flightinfo getFlightinfo() {
		return Flightinfo;
	}

	public void setFlightinfo(Flightinfo flightinfo) {
		Flightinfo = flightinfo;
	}
	
	
	
}
