package com.mobilebanking.model;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.User;

public class SmsCountDTO {

    private String remarks;
    private int smsCount;
    private Bank bank;
    private String name;
    private Long bankId;
    private User user;
    private Long userId;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "SmsCountDTO{" +
                "remarks='" + remarks + '\'' +
                ", smsCount=" + smsCount +
                ", bank=" + bank +
                ", name='" + name + '\'' +
                ", bankId=" + bankId +
                ", user=" + user +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
