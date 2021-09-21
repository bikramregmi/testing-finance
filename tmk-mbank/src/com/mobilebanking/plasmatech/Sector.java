package com.mobilebanking.plasmatech;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Sector")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sector {
	@XmlElement(name = "SectorName" )
	private String SectorName;
	@XmlElement(name = "SectorCode")
	private String SectorCode;

	public String getSectorName() {
		return SectorName;
	}

	public void setSectorName(String SectorName) {
		this.SectorName = SectorName;
	}

	public String getSectorCode() {
		return SectorCode;
	}

	public void setSectorCode(String SectorCode) {
		this.SectorCode = SectorCode;
	}

}

