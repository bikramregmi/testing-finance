package com.mobilebanking.plasmatech;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "PassengerDetail")
@XmlAccessorType (XmlAccessType.FIELD)
public class PassengerCollection {

	@XmlElement(name = "Passenger")
	private List<PassengerDto> passengerList;

	public List<PassengerDto> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<PassengerDto> passengerList) {
		this.passengerList = passengerList;
	}
	
	
}
