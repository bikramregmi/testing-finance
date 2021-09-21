//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.15 at 03:01:18 PM NPT 
//


package com.wallet.serviceprovider.eprabhu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnFlightSearchDomestic complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnFlightSearchDomestic"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SectorFrom" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SectorTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Outbound" type="{http://schemas.datacontract.org/2004/07/Utility.API}ArrayOfFlightDomestic"/&gt;
 *         &lt;element name="Inbound" type="{http://schemas.datacontract.org/2004/07/Utility.API}ArrayOfFlightDomestic"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnFlightSearchDomestic", propOrder = {
    "code",
    "message",
    "sectorFrom",
    "sectorTo",
    "outbound",
    "inbound"
})
public class ReturnFlightSearchDomestic {

    @XmlElement(name = "Code", required = true, nillable = true)
    protected String code;
    @XmlElement(name = "Message", required = true, nillable = true)
    protected String message;
    @XmlElement(name = "SectorFrom", required = true, nillable = true)
    protected String sectorFrom;
    @XmlElement(name = "SectorTo", required = true, nillable = true)
    protected String sectorTo;
    @XmlElement(name = "Outbound", required = true, nillable = true)
    protected ArrayOfFlightDomestic outbound;
    @XmlElement(name = "Inbound", required = true, nillable = true)
    protected ArrayOfFlightDomestic inbound;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the sectorFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectorFrom() {
        return sectorFrom;
    }

    /**
     * Sets the value of the sectorFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectorFrom(String value) {
        this.sectorFrom = value;
    }

    /**
     * Gets the value of the sectorTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectorTo() {
        return sectorTo;
    }

    /**
     * Sets the value of the sectorTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectorTo(String value) {
        this.sectorTo = value;
    }

    /**
     * Gets the value of the outbound property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFlightDomestic }
     *     
     */
    public ArrayOfFlightDomestic getOutbound() {
        return outbound;
    }

    /**
     * Sets the value of the outbound property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFlightDomestic }
     *     
     */
    public void setOutbound(ArrayOfFlightDomestic value) {
        this.outbound = value;
    }

    /**
     * Gets the value of the inbound property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFlightDomestic }
     *     
     */
    public ArrayOfFlightDomestic getInbound() {
        return inbound;
    }

    /**
     * Sets the value of the inbound property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFlightDomestic }
     *     
     */
    public void setInbound(ArrayOfFlightDomestic value) {
        this.inbound = value;
    }

}
