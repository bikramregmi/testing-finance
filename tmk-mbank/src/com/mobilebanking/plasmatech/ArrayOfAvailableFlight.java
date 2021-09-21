package com.mobilebanking.plasmatech;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ArrayOfAvailableFlight {
	
	@XmlElement(name = "Availability")
	protected List<AvailableFlight> availability;

	public List<AvailableFlight> getAvailability() {
		return availability;
	}

	public void setAvailability(List<AvailableFlight> availability) {
		this.availability = availability;
	}

	
	

}
