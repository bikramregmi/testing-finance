//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.02 at 10:23:47 AM NPT 
//


package com.wallet.serviceprovider.dishhome;

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
 *         &lt;element name="PackageChangeResult" type="{http://tempuri.org/}PackageChangeResponseResult" minOccurs="0"/&gt;
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
    "packageChangeResult"
})
@XmlRootElement(name = "PackageChangeResponse")
public class PackageChangeResponse {

    @XmlElement(name = "PackageChangeResult")
    protected PackageChangeResponseResult packageChangeResult;

    /**
     * Gets the value of the packageChangeResult property.
     * 
     * @return
     *     possible object is
     *     {@link PackageChangeResponseResult }
     *     
     */
    public PackageChangeResponseResult getPackageChangeResult() {
        return packageChangeResult;
    }

    /**
     * Sets the value of the packageChangeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageChangeResponseResult }
     *     
     */
    public void setPackageChangeResult(PackageChangeResponseResult value) {
        this.packageChangeResult = value;
    }

}
