
package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CancelReservation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancelReservation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strAgencyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strPnrNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strTicketNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strAirlineId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelReservation", propOrder = {
    "strUserId",
    "strPassword",
    "strAgencyId",
    "strPnrNo",
    "strTicketNo",
    "strAirlineId"
})
public class CancelReservation {

    protected String strUserId;
    protected String strPassword;
    protected String strAgencyId;
    protected String strPnrNo;
    protected String strTicketNo;
    protected String strAirlineId;

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
     * Gets the value of the strPnrNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrPnrNo() {
        return strPnrNo;
    }

    /**
     * Sets the value of the strPnrNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrPnrNo(String value) {
        this.strPnrNo = value;
    }

    /**
     * Gets the value of the strTicketNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrTicketNo() {
        return strTicketNo;
    }

    /**
     * Sets the value of the strTicketNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrTicketNo(String value) {
        this.strTicketNo = value;
    }

    /**
     * Gets the value of the strAirlineId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrAirlineId() {
        return strAirlineId;
    }

    /**
     * Sets the value of the strAirlineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrAirlineId(String value) {
        this.strAirlineId = value;
    }

}
