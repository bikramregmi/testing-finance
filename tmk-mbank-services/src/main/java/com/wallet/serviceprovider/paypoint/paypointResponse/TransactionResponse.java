package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PPResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionResponse {
	
	@XmlAttribute(name="Result")
	private String result;
	@XmlAttribute(name="Key")
    private String key;
	
	@XmlElement(name="ResultMessage")
    private ResultMessage ResultMessage;

    public ResultMessage getResultMessage ()
    {
        return ResultMessage;
    }

    public void setResultMessage (ResultMessage ResultMessage)
    {
        this.ResultMessage = ResultMessage;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    
}
