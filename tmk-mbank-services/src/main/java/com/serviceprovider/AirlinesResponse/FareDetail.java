package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class FareDetail {
	@XmlElement
	 private String segref;
	@XmlElement
	    private String cabin;
	@XmlElement
	    private String passengertype;
	@XmlElement
	    private String faretype;
	@XmlElement
	    private String AvlStatus;
	@XmlElement
	    private String FareBasis;
	@XmlElement
	    private String rbd;

	    public String getSegref ()
	    {
	        return segref;
	    }

	    public void setSegref (String segref)
	    {
	        this.segref = segref;
	    }

	    public String getCabin ()
	    {
	        return cabin;
	    }

	    public void setCabin (String cabin)
	    {
	        this.cabin = cabin;
	    }

	    public String getPassengertype ()
	    {
	        return passengertype;
	    }

	    public void setPassengertype (String passengertype)
	    {
	        this.passengertype = passengertype;
	    }

	    public String getFaretype ()
	    {
	        return faretype;
	    }

	    
	    public void setFaretype (String faretype)
	    {
	        this.faretype = faretype;
	    }

	    public String getAvlStatus ()
	    {
	        return AvlStatus;
	    }

	    public void setAvlStatus (String AvlStatus)
	    {
	        this.AvlStatus = AvlStatus;
	    }

	    public String getFareBasis ()
	    {
	        return FareBasis;
	    }

	    public void setFareBasis (String FareBasis)
	    {
	        this.FareBasis = FareBasis;
	    }

	    public String getRbd ()
	    {
	        return rbd;
	    }

	    public void setRbd (String rbd)
	    {
	        this.rbd = rbd;
	    }
}
