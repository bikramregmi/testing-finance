
package com.wallet.serviceprovider.unitedsolution;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RescheduleSave complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RescheduleSave"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="strRescheduleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="strOperation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RescheduleSave", propOrder = {
    "strRescheduleId",
    "strOperation"
})
public class RescheduleSave {

    protected String strRescheduleId;
    protected String strOperation;

    /**
     * Gets the value of the strRescheduleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrRescheduleId() {
        return strRescheduleId;
    }

    /**
     * Sets the value of the strRescheduleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrRescheduleId(String value) {
        this.strRescheduleId = value;
    }

    /**
     * Gets the value of the strOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrOperation() {
        return strOperation;
    }

    /**
     * Sets the value of the strOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrOperation(String value) {
        this.strOperation = value;
    }

}
