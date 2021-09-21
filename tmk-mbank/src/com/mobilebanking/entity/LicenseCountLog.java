package com.mobilebanking.entity;

import javax.persistence.*;

@Entity
@Table(name="licensecountlog")
public class LicenseCountLog extends AbstractEntity<Long> {

    @Column
    private String remarks;
    @Column
    private int licenseCount;
    @ManyToOne(fetch=FetchType.EAGER)
    private Bank bank;
    @ManyToOne
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

    public int getLicenseCount() {
        return licenseCount;
    }

    public void setLicenseCount(int licenseCount) {
        this.licenseCount = licenseCount;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "LicenseCountLog{" +
                "remarks='" + remarks + '\'' +
                ", licenseCount=" + licenseCount +
                ", bank=" + bank +
                '}';
    }
}
