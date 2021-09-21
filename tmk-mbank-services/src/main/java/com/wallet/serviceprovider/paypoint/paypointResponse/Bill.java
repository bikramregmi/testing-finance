package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Bill")
public class Bill {
    private String billNumber;
    private String dueDate;
    private long amount;
    private String reserveInfo;
    private BillParam billParam;
    private long refStan;

    public String getBillNumber() {
        return this.billNumber;
    }

    @XmlElement(name="BillNumber")
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    @XmlElement(name="DueDate")
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public long getAmount() {
        return this.amount;
    }

    @XmlElement(name="Amount")
    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getReserveInfo() {
        return this.reserveInfo;
    }

    @XmlElement(name="ReserveInfo")
    public void setReserveInfo(String reserveInfo) {
        this.reserveInfo = reserveInfo;
    }

    public BillParam getBillParam() {
        return this.billParam;
    }

    @XmlElement(name="BillParam")
    public void setBillParam(BillParam billParam) {
        this.billParam = billParam;
    }

    public long getRefStan() {
        return this.refStan;
    }

    @XmlElement(name="RefStan")
    public void setRefStan(long refStan) {
        this.refStan = refStan;
    }
}
