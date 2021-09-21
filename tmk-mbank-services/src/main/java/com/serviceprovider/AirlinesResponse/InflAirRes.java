package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InflAirRes {
	
	@XmlElement
	private SegmentFlightRef[] SegmentFlightRef;

	public SegmentFlightRef[] getSegmentFlightRef() {
		return SegmentFlightRef;
	}

	public void setSegmentFlightRef(SegmentFlightRef[] segmentFlightRef) {
		SegmentFlightRef = segmentFlightRef;
	}

	

}
