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
 *         &lt;element name="GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult" type="{http://tempuri.org/}TransactionHistoryResponseResult" minOccurs="0"/&gt;
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
    "getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult"
})
@XmlRootElement(name = "GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResponse")
public class GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResponse {

    @XmlElement(name = "GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult")
    protected TransactionHistoryResponseResult getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult;

    /**
     * Gets the value of the getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionHistoryResponseResult }
     *     
     */
    public TransactionHistoryResponseResult getGetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult() {
        return getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult;
    }

    /**
     * Sets the value of the getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionHistoryResponseResult }
     *     
     */
    public void setGetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult(TransactionHistoryResponseResult value) {
        this.getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult = value;
    }

}
