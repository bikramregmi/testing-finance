package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="i")
public class NeaResponseI {

	
	@XmlElement(name = "f")
	private NeaResponseF[] neaResponseF;

	@XmlAttribute(name = "col")
    private String col;

	@XmlAttribute(name = "row")
    private String row;

	public NeaResponseF[] getNeaResponseF() {
		return neaResponseF;
	}

	public void setNeaResponseF(NeaResponseF[] neaResponseF) {
		this.neaResponseF = neaResponseF;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}
	
}
