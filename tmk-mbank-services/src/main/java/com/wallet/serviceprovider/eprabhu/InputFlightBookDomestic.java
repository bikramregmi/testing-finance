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
 * <p>Java class for InputFlightBookDomestic complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputFlightBookDomestic"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OutboundFlightId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="InboundFlightId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputFlightBookDomestic", propOrder = {
    "userName",
    "password",
    "outboundFlightId",
    "inboundFlightId"
})
public class InputFlightBookDomestic {

    @XmlElement(name = "UserName", required = true, nillable = true)
    protected String userName;
    @XmlElement(name = "Password", required = true, nillable = true)
    protected String password;
    @XmlElement(name = "OutboundFlightId", required = true, nillable = true)
    protected String outboundFlightId;
    @XmlElement(name = "InboundFlightId", required = true, nillable = true)
    protected String inboundFlightId;

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the outboundFlightId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutboundFlightId() {
        return outboundFlightId;
    }

    /**
     * Sets the value of the outboundFlightId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutboundFlightId(String value) {
        this.outboundFlightId = value;
    }

    /**
     * Gets the value of the inboundFlightId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInboundFlightId() {
        return inboundFlightId;
    }

    /**
     * Sets the value of the inboundFlightId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInboundFlightId(String value) {
        this.inboundFlightId = value;
    }

}
