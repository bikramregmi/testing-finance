package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="PPResponse")
public class PPResponse {
    private String result;
    private String key;
    private String resultMessage;
    private UtilityInfo utilityInfo;
    private BillInfo billInfo;

    private ResultData resultData;
    
    public String getResult() {
        return this.result;
    }

    @XmlAttribute(name="Result")
    public void setResult(String result) {
        this.result = result;
    }

    public String getKey() {
        return this.key;
    }
    
    @XmlAttribute(name="Key")
    public void setKey(String key) {
        this.key = key;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    @XmlElement(name="ResultMessage")
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public UtilityInfo getUtilityInfo() {
        return this.utilityInfo;
    }

    @XmlElement(name="UtilityInfo")
    public void setUtilityInfo(UtilityInfo utilityInfo) {
        this.utilityInfo = utilityInfo;
    }

    public BillInfo getBillInfo() {
        return this.billInfo;
    }

    @XmlElement(name="BillInfo")
    public void setBillInfo(BillInfo billInfo) {
        this.billInfo = billInfo;
    }

	public ResultData getResultData() {
		return resultData;
	}
	  @XmlElement(name="ResultData")
	public void setResultData(ResultData resultData) {
		this.resultData = resultData;
	}
    
    
}
