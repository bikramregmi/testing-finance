package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)

public class Fare {
	@XmlElement
	private String msgcode;
	@XmlElement
    private String msgQual;
	@XmlElement
    private String Description;

	public String getMsgcode() {
		return msgcode;
	}

	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}

	public String getMsgQual() {
		return msgQual;
	}

	public void setMsgQual(String msgQual) {
		this.msgQual = msgQual;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
    
}
