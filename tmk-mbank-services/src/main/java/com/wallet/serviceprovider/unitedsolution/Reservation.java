
package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Reservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reservation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strFlightId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strReturnFlightId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reservation", propOrder = {
    "strFlightId",
    "strReturnFlightId"
})
public class Reservation {

    protected String strFlightId;
    protected String strReturnFlightId;

    /**
     * Gets the value of the strFlightId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFlightId() {
        return strFlightId;
    }

    /**
     * Sets the value of the strFlightId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFlightId(String value) {
        this.strFlightId = value;
    }

    /**
     * Gets the value of the strReturnFlightId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrReturnFlightId() {
        return strReturnFlightId;
    }

    /**
     * Sets the value of the strReturnFlightId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrReturnFlightId(String value) {
        this.strReturnFlightId = value;
    }

}
