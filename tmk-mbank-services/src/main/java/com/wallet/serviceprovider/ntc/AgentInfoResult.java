//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.20 at 11:09:35 AM NPT 
//


package com.wallet.serviceprovider.ntc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agentInfoResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agentInfoResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="requestTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="targetAgentInfo" type="{http://soapposapi.eservglobal.com/}targetAgentInfo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agentInfoResult", propOrder = {
    "requestTag",
    "targetAgentInfo"
})
public class AgentInfoResult {

    protected String requestTag;
    protected TargetAgentInfo targetAgentInfo;

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

    /**
     * Gets the value of the targetAgentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TargetAgentInfo }
     *     
     */
    public TargetAgentInfo getTargetAgentInfo() {
        return targetAgentInfo;
    }

    /**
     * Sets the value of the targetAgentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetAgentInfo }
     *     
     */
    public void setTargetAgentInfo(TargetAgentInfo value) {
        this.targetAgentInfo = value;
    }

}
