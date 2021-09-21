
package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IssueTicketB2B complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IssueTicketB2B"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strFlightId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strReturnFlightId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strContactName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strContactEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strContactMobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strPassengerDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IssueTicketB2B", propOrder = {
    "strFlightId",
    "strReturnFlightId",
    "strContactName",
    "strContactEmail",
    "strContactMobile",
    "strPassengerDetail"
})
public class IssueTicketB2B {

    protected String strFlightId;
    protected String strReturnFlightId;
    protected String strContactName;
    protected String strContactEmail;
    protected String strContactMobile;
    protected String strPassengerDetail;

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

    /**
     * Gets the value of the strContactName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrContactName() {
        return strContactName;
    }

    /**
     * Sets the value of the strContactName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrContactName(String value) {
        this.strContactName = value;
    }

    /**
     * Gets the value of the strContactEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrContactEmail() {
        return strContactEmail;
    }

    /**
     * Sets the value of the strContactEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrContactEmail(String value) {
        this.strContactEmail = value;
    }

    /**
     * Gets the value of the strContactMobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrContactMobile() {
        return strContactMobile;
    }

    /**
     * Sets the value of the strContactMobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrContactMobile(String value) {
        this.strContactMobile = value;
    }

    /**
     * Gets the value of the strPassengerDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrPassengerDetail() {
        return strPassengerDetail;
    }

    /**
     * Sets the value of the strPassengerDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrPassengerDetail(String value) {
        this.strPassengerDetail = value;
    }

}
