//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.15 at 03:01:18 PM NPT 
//


package com.wallet.serviceprovider.eprabhu;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="DishHomeBookingResult" type="{http://schemas.datacontract.org/2004/07/Utility.API}ReturnTransaction" minOccurs="0"/&gt;
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
    "dishHomeBookingResult"
})
@XmlRootElement(name = "DishHomeBookingResponse", namespace = "http://tempuri.org/")
public class DishHomeBookingResponse {

    @XmlElementRef(name = "DishHomeBookingResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ReturnTransaction> dishHomeBookingResult;

    /**
     * Gets the value of the dishHomeBookingResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ReturnTransaction }{@code >}
     *     
     */
    public JAXBElement<ReturnTransaction> getDishHomeBookingResult() {
        return dishHomeBookingResult;
    }

    /**
     * Sets the value of the dishHomeBookingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ReturnTransaction }{@code >}
     *     
     */
    public void setDishHomeBookingResult(JAXBElement<ReturnTransaction> value) {
        this.dishHomeBookingResult = value;
    }

}
