package com.mobilebanking.entity;

import javax.persistence.*;

@Entity
@Table(name="smscountlog")
public class SmsCountLog extends AbstractEntity<Long> {

    @Column
    private String remarks;
    @Column
    private int smsCount;
    @ManyToOne(fetch=FetchType.EAGER)
    private Bank bank;
    @ManyToOne(fetch=FetchType.EAGER)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "SmsCountLog{" +
                "remarks='" + remarks + '\'' +
                ", smsCount=" + smsCount +
                ", bank=" + bank +
                ", user=" + user +
                '}';
    }
}
