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
 * <p>Java class for ReturnBusTicketBook complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnBusTicketBook"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TicketSrlNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="TimeOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BoardingPoints" type="{http://schemas.datacontract.org/2004/07/Utility.API}BPoints"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnBusTicketBook", propOrder = {
    "code",
    "message",
    "ticketSrlNo",
    "timeOut",
    "boardingPoints"
})
public class ReturnBusTicketBook {

    @XmlElement(name = "Code", required = true, nillable = true)
    protected String code;
    @XmlElement(name = "Message", required = true, nillable = true)
    protected String message;
    @XmlElement(name = "TicketSrlNo", required = true, nillable = true)
    protected String ticketSrlNo;
    @XmlElement(name = "TimeOut", required = true, nillable = true)
    protected String timeOut;
    @XmlElement(name = "BoardingPoints", required = true, nillable = true)
    protected BPoints boardingPoints;

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
     * Gets the value of the ticketSrlNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketSrlNo() {
        return ticketSrlNo;
    }

    /**
     * Sets the value of the ticketSrlNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketSrlNo(String value) {
        this.ticketSrlNo = value;
    }

    /**
     * Gets the value of the timeOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeOut() {
        return timeOut;
    }

    /**
     * Sets the value of the timeOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeOut(String value) {
        this.timeOut = value;
    }

    /**
     * Gets the value of the boardingPoints property.
     * 
     * @return
     *     possible object is
     *     {@link BPoints }
     *     
     */
    public BPoints getBoardingPoints() {
        return boardingPoints;
    }

    /**
     * Sets the value of the boardingPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link BPoints }
     *     
     */
    public void setBoardingPoints(BPoints value) {
        this.boardingPoints = value;
    }

}
