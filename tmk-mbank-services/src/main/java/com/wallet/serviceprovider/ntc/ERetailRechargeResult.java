//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.23 at 02:23:58 PM NPT 
//


package com.wallet.serviceprovider.ntc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eRetailRechargeResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eRetailRechargeResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="globalTransaction" type="{http://soapposapi.eservglobal.com/}globalTransaction" minOccurs="0"/&gt;
 *         &lt;element name="requestTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eRetailRechargeResult", propOrder = {
    "globalTransaction",
    "requestTag"
})
public class ERetailRechargeResult {

    protected GlobalTransaction globalTransaction;
    protected String requestTag;

    /**
     * Gets the value of the globalTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalTransaction }
     *     
     */
    public GlobalTransaction getGlobalTransaction() {
        return globalTransaction;
    }

    /**
     * Sets the value of the globalTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalTransaction }
     *     
     */
    public void setGlobalTransaction(GlobalTransaction value) {
        this.globalTransaction = value;
    }

    /**
     * Gets the value of the requestTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestTag() {
        return requestTag;
    }

    /**
     * Sets the value of the requestTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestTag(String value) {
        this.requestTag = value;
    }

}
