package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="body")
public class Body {

	@XmlAttribute(name = "cnt")
	 private String cnt;

	@XmlElement(name = "i")
	 private NeaResponseI neaResponseI;

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public NeaResponseI getNeaResponseI() {
		return neaResponseI;
	}

	public void setNeaResponseI(NeaResponseI neaResponseI) {
		this.neaResponseI = neaResponseI;
	}

	

}
