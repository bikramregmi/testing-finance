package com.mobilebanking.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="miniStatement")
public class MiniStatementRequest  extends AbstractEntity<Long>{

    @Column(nullable = false)
    private String email;

    @ManyToOne(fetch=FetchType.EAGER)
    private CustomerBankAccount customerBankAccount;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerBankAccount getCustomerBankAccount() {
        return customerBankAccount;
    }

    public void setCustomerBankAccount(CustomerBankAccount customerBankAccount) {
        this.customerBankAccount = customerBankAccount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
