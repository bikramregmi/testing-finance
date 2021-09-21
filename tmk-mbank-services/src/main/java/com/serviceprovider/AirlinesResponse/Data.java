package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name ="data" )
public class Data {
	
	@XmlElement
	private InflAirRes InflAirRes;

	public InflAirRes getInflAirRes() {
		return InflAirRes;
	}

	public void setInflAirRes(InflAirRes InflAirRes) {
		this.InflAirRes = InflAirRes;
	}

}
