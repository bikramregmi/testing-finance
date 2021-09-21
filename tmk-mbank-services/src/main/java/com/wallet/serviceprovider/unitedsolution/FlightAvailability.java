
package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlightAvailability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightAvailability"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strAgencyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strSectorFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strSectorTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strFlightDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strReturnDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strTripType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strNationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="intAdult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="intChild" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightAvailability", propOrder = {
    "strUserId",
    "strPassword",
    "strAgencyId",
    "strSectorFrom",
    "strSectorTo",
    "strFlightDate",
    "strReturnDate",
    "strTripType",
    "strNationality",
    "intAdult",
    "intChild"
})
public class FlightAvailability {

    protected String strUserId;
    protected String strPassword;
    protected String strAgencyId;
    protected String strSectorFrom;
    protected String strSectorTo;
    protected String strFlightDate;
    protected String strReturnDate;
    protected String strTripType;
    protected String strNationality;
    protected String intAdult;
    protected String intChild;

    /**
     * Gets the value of the strUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrUserId() {
        return strUserId;
    }

    /**
     * Sets the value of the strUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrUserId(String value) {
        this.strUserId = value;
    }

    /**
     * Gets the value of the strPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrPassword() {
        return strPassword;
    }

    /**
     * Sets the value of the strPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrPassword(String value) {
        this.strPassword = value;
    }

    /**
     * Gets the value of the strAgencyId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrAgencyId() {
        return strAgencyId;
    }

    /**
     * Sets the value of the strAgencyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrAgencyId(String value) {
        this.strAgencyId = value;
    }

    /**
     * Gets the value of the strSectorFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrSectorFrom() {
        return strSectorFrom;
    }

    /**
     * Sets the value of the strSectorFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrSectorFrom(String value) {
        this.strSectorFrom = value;
    }

    /**
     * Gets the value of the strSectorTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrSectorTo() {
        return strSectorTo;
    }

    /**
     * Sets the value of the strSectorTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrSectorTo(String value) {
        this.strSectorTo = value;
    }

    /**
     * Gets the value of the strFlightDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFlightDate() {
        return strFlightDate;
    }

    /**
     * Sets the value of the strFlightDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFlightDate(String value) {
        this.strFlightDate = value;
    }

    /**
     * Gets the value of the strReturnDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrReturnDate() {
        return strReturnDate;
    }

    /**
     * Sets the value of the strReturnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrReturnDate(String value) {
        this.strReturnDate = value;
    }

    /**
     * Gets the value of the strTripType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrTripType() {
        return strTripType;
    }

    /**
     * Sets the value of the strTripType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrTripType(String value) {
        this.strTripType = value;
    }

    /**
     * Gets the value of the strNationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrNationality() {
        return strNationality;
    }

    /**
     * Sets the value of the strNationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrNationality(String value) {
        this.strNationality = value;
    }

    /**
     * Gets the value of the intAdult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntAdult() {
        return intAdult;
    }

    /**
     * Sets the value of the intAdult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntAdult(String value) {
        this.intAdult = value;
    }

    /**
     * Gets the value of the intChild property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntChild() {
        return intChild;
    }

    /**
     * Sets the value of the intChild property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntChild(String value) {
        this.intChild = value;
    }

}
