package com.serviceprovider.AirlinesResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "row")
@XmlAccessorType(XmlAccessType.NONE)
public class Row {
	@XmlAttribute(name = "Description")
	private String Description;
	@XmlAttribute(name = "AirPortCode")
    private String AirPortCode;
	@XmlAttribute(name = "IsTop")
    private String IsTop;
	@XmlAttribute(name = "Country")
    private String Country;
	@XmlAttribute(name = "City")
    private String City;

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getAirPortCode ()
    {
        return AirPortCode;
    }

    public void setAirPortCode (String AirPortCode)
    {
        this.AirPortCode = AirPortCode;
    }

    public String getIsTop ()
    {
        return IsTop;
    }

    public void setIsTop (String IsTop)
    {
        this.IsTop = IsTop;
    }

    public String getCountry ()
    {
        return Country;
    }

    public void setCountry (String Country)
    {
        this.Country = Country;
    }

    public String getCity ()
    {
        return City;
    }

    public void setCity (String City)
    {
        this.City = City;
    }

}
