package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FareDetails {
	@XmlElement
	 private String DistCommission;
	@XmlElement
	    private String Commission;
	@XmlElement
	    private Adult Adult;
	@XmlElement
	    private String TxnFee;
	@XmlElement
	    private String ServiceTax;

	    public String getDistCommission ()
	    {
	        return DistCommission;
	    }

	    public void setDistCommission (String DistCommission)
	    {
	        this.DistCommission = DistCommission;
	    }

	    public String getCommission ()
	    {
	        return Commission;
	    }

	    public void setCommission (String Commission)
	    {
	        this.Commission = Commission;
	    }

	    public Adult getAdult ()
	    {
	        return Adult;
	    }

	    public void setAdult (Adult Adult)
	    {
	        this.Adult = Adult;
	    }

	    public String getTxnFee ()
	    {
	        return TxnFee;
	    }

	    public void setTxnFee (String TxnFee)
	    {
	        this.TxnFee = TxnFee;
	    }

	    public String getServiceTax ()
	    {
	        return ServiceTax;
	    }

	    public void setServiceTax (String ServiceTax)
	    {
	        this.ServiceTax = ServiceTax;
	    }

}
