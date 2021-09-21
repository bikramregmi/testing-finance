
package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Reschedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reschedule"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strPnrNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strAirlineId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strCurrentDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strNewDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reschedule", propOrder = {
    "strPnrNo",
    "strAirlineId",
    "strCurrentDate",
    "strNewDate"
})
public class Reschedule {

    protected String strPnrNo;
    protected String strAirlineId;
    protected String strCurrentDate;
    protected String strNewDate;

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

    /**
     * Gets the value of the strCurrentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCurrentDate() {
        return strCurrentDate;
    }

    /**
     * Sets the value of the strCurrentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCurrentDate(String value) {
        this.strCurrentDate = value;
    }

    /**
     * Gets the value of the strNewDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrNewDate() {
        return strNewDate;
    }

    /**
     * Sets the value of the strNewDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrNewDate(String value) {
        this.strNewDate = value;
    }

}
