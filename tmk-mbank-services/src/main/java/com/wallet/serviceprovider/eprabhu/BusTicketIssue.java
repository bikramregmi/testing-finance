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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BusTicketIssueRequest" type="{http://schemas.datacontract.org/2004/07/Utility.API}InputBusTicketIssue"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "busTicketIssueRequest"
})
@XmlRootElement(name = "BusTicketIssue", namespace = "http://tempuri.org/")
public class BusTicketIssue {

    @XmlElement(name = "BusTicketIssueRequest", namespace = "http://tempuri.org/", required = true, nillable = true)
    protected InputBusTicketIssue busTicketIssueRequest;

    /**
     * Gets the value of the busTicketIssueRequest property.
     * 
     * @return
     *     possible object is
     *     {@link InputBusTicketIssue }
     *     
     */
    public InputBusTicketIssue getBusTicketIssueRequest() {
        return busTicketIssueRequest;
    }

    /**
     * Sets the value of the busTicketIssueRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputBusTicketIssue }
     *     
     */
    public void setBusTicketIssueRequest(InputBusTicketIssue value) {
        this.busTicketIssueRequest = value;
    }

}
