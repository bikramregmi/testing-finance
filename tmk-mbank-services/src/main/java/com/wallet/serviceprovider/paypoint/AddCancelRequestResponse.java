//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.14 at 01:17:41 PM NPT 
//


package com.wallet.serviceprovider.paypoint;

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
 *         &lt;element name="AddCancelRequestResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "addCancelRequestResult"
})
@XmlRootElement(name = "AddCancelRequestResponse")
public class AddCancelRequestResponse {

    @XmlElement(name = "AddCancelRequestResult")
    protected String addCancelRequestResult;

    /**
     * Gets the value of the addCancelRequestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddCancelRequestResult() {
        return addCancelRequestResult;
    }

    /**
     * Sets the value of the addCancelRequestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddCancelRequestResult(String value) {
        this.addCancelRequestResult = value;
    }

}
