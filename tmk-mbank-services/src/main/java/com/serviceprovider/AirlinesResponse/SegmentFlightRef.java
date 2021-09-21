package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SegmentFlightRef {
	@XmlElement
	private Outbound Outbound;
	@XmlElement
    private String refQualifier;
	@XmlElement
    private String segrefno;
	@XmlElement
    private FareDetails FareDetails;

    public Outbound getOutbound ()
    {
        return Outbound;
    }

    public void setOutbound (Outbound Outbound)
    {
        this.Outbound = Outbound;
    }

    public String getRefQualifier ()
    {
        return refQualifier;
    }

    public void setRefQualifier (String refQualifier)
    {
        this.refQualifier = refQualifier;
    }

    public String getSegrefno ()
    {
        return segrefno;
    }

    public void setSegrefno (String segrefno)
    {
        this.segrefno = segrefno;
    }

	public FareDetails getFareDetails() {
		return FareDetails;
	}

	public void setFareDetails(FareDetails fareDetails) {
		FareDetails = fareDetails;
	}

}
