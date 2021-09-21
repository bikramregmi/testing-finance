package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Adult {
	 private String travellerref;
	 @XmlElement
	    private Fare Fare;
	 @XmlElement
	    private String paxfareno;
	 @XmlElement
	    private String company;
	 @XmlElement
	    private String fareamt;
	 @XmlElement
	    private String taxamt;
	 @XmlElement
	    private String transStageQual;
	 @XmlElement
	    private String ptc;
	 @XmlElement
	    private FareDetail FareDetail;
	 @XmlElement
	    private String YQ;

	    public String getTravellerref ()
	    {
	        return travellerref;
	    }

	    public void setTravellerref (String travellerref)
	    {
	        this.travellerref = travellerref;
	    }

	    public Fare getFare ()
	    {
	        return Fare;
	    }

	    public void setFare (Fare Fare)
	    {
	        this.Fare = Fare;
	    }

	    public String getPaxfareno ()
	    {
	        return paxfareno;
	    }

	    public void setPaxfareno (String paxfareno)
	    {
	        this.paxfareno = paxfareno;
	    }

	    public String getCompany ()
	    {
	        return company;
	    }

	    public void setCompany (String company)
	    {
	        this.company = company;
	    }

	    public String getFareamt ()
	    {
	        return fareamt;
	    }

	    public void setFareamt (String fareamt)
	    {
	        this.fareamt = fareamt;
	    }

	    public String getTaxamt ()
	    {
	        return taxamt;
	    }

	    public void setTaxamt (String taxamt)
	    {
	        this.taxamt = taxamt;
	    }

	    public String getTransStageQual ()
	    {
	        return transStageQual;
	    }

	    public void setTransStageQual (String transStageQual)
	    {
	        this.transStageQual = transStageQual;
	    }

	    public String getPtc ()
	    {
	        return ptc;
	    }

	    public void setPtc (String ptc)
	    {
	        this.ptc = ptc;
	    }

	    public FareDetail getFareDetail ()
	    {
	        return FareDetail;
	    }

	    public void setFareDetail (FareDetail FareDetail)
	    {
	        this.FareDetail = FareDetail;
	    }

	    public String getYQ ()
	    {
	        return YQ;
	    }

	    public void setYQ (String YQ)
	    {
	        this.YQ = YQ;
	    }
}
