
package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetPnrDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetPnrDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strAgencyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strPnrNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPnrDetail", propOrder = {
    "strUserId",
    "strPassword",
    "strAgencyId",
    "strPnrNo",
    "strLastName"
})
public class GetPnrDetail {

    protected String strUserId;
    protected String strPassword;
    protected String strAgencyId;
    protected String strPnrNo;
    protected String strLastName;

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
     * Gets the value of the strLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrLastName() {
        return strLastName;
    }

    /**
     * Sets the value of the strLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrLastName(String value) {
        this.strLastName = value;
    }

}
