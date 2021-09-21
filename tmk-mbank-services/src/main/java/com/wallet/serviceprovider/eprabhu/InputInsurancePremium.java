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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InputInsurancePremium complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputInsurancePremium"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OperatorCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PolicyNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PolicyName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="PartnerTxnId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputInsurancePremium", propOrder = {
    "userName",
    "password",
    "operatorCode",
    "policyNumber",
    "policyName",
    "amount",
    "partnerTxnId"
})
public class InputInsurancePremium {

    @XmlElement(name = "UserName", required = true, nillable = true)
    protected String userName;
    @XmlElement(name = "Password", required = true, nillable = true)
    protected String password;
    @XmlElement(name = "OperatorCode")
    protected int operatorCode;
    @XmlElement(name = "PolicyNumber", required = true, nillable = true)
    protected String policyNumber;
    @XmlElement(name = "PolicyName", required = true, nillable = true)
    protected String policyName;
    @XmlElement(name = "Amount")
    protected float amount;
    @XmlElement(name = "PartnerTxnId", required = true, nillable = true)
    protected String partnerTxnId;

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
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
     * Gets the value of the operatorCode property.
     * 
     */
    public int getOperatorCode() {
        return operatorCode;
    }

    /**
     * Sets the value of the operatorCode property.
     * 
     */
    public void setOperatorCode(int value) {
        this.operatorCode = value;
    }

    /**
     * Gets the value of the policyNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
     * Sets the value of the policyNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyNumber(String value) {
        this.policyNumber = value;
    }

    /**
     * Gets the value of the policyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyName() {
        return policyName;
    }

    /**
     * Sets the value of the policyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyName(String value) {
        this.policyName = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public float getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(float value) {
        this.amount = value;
    }

    /**
     * Gets the value of the partnerTxnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerTxnId() {
        return partnerTxnId;
    }

    /**
     * Sets the value of the partnerTxnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerTxnId(String value) {
        this.partnerTxnId = value;
    }

}
