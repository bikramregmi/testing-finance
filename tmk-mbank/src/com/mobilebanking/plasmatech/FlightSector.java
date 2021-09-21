package com.mobilebanking.plasmatech;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FlightSector")
public class FlightSector {
	@XmlElement(name="Sector")
	private Sector[] Sector;

	public Sector[] getSector() {
		return Sector;
	}

	public void setSector(Sector[] Sector) {
		this.Sector = Sector;
	}
}
