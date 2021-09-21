//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.02 at 10:23:47 AM NPT 
//


package com.wallet.serviceprovider.dishhome;

import java.math.BigDecimal;
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
 *         &lt;element name="Username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="STBIDorCASIDorCHIPID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RechargeAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="OptionalToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "username",
    "password",
    "stbiDorCASIDorCHIPID",
    "rechargeAmount",
    "optionalToken"
})
@XmlRootElement(name = "RechargeBySTBIDorCASIDorCHIPIDJSON")
public class RechargeBySTBIDorCASIDorCHIPIDJSON {

    @XmlElement(name = "Username")
    protected String username;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "STBIDorCASIDorCHIPID")
    protected String stbiDorCASIDorCHIPID;
    @XmlElement(name = "RechargeAmount", required = true)
    protected BigDecimal rechargeAmount;
    @XmlElement(name = "OptionalToken")
    protected String optionalToken;

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the stbiDorCASIDorCHIPID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTBIDorCASIDorCHIPID() {
        return stbiDorCASIDorCHIPID;
    }

    /**
     * Sets the value of the stbiDorCASIDorCHIPID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTBIDorCASIDorCHIPID(String value) {
        this.stbiDorCASIDorCHIPID = value;
    }

    /**
     * Gets the value of the rechargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    /**
     * Sets the value of the rechargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRechargeAmount(BigDecimal value) {
        this.rechargeAmount = value;
    }

    /**
     * Gets the value of the optionalToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionalToken() {
        return optionalToken;
    }

    /**
     * Sets the value of the optionalToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionalToken(String value) {
        this.optionalToken = value;
    }

}
