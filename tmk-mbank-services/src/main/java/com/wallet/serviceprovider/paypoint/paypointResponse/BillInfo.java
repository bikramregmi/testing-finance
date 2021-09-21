package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="BillInfo")

public class BillInfo {
    private Bill bill;

    public Bill getBill() {
        return this.bill;
    }

    @XmlElement(name="Bill")
    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
