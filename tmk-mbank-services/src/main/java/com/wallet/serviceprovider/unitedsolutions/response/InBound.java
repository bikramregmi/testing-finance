package com.wallet.serviceprovider.unitedsolutions.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="InBound")
@XmlAccessorType(XmlAccessType.NONE)
public class InBound {

	private List<Availability> availability;

	public List<Availability> getAvailability() {
		return availability;
	}

	@XmlElement(name = "Availability")
	public void setAvailability(List<Availability> availability) {
		this.availability = availability;
	}
	
}
