package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customertransactionlog")
public class CustomerTransactionLog extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	@OneToOne(fetch=FetchType.EAGER)
	private CustomerBankAccount customerBankAccount;
	
	private Double perTransactionLimit;
	
	private Long dailyTransactionLimit;
	
	private Double weeklyTransactionLimit;
	
	private Double monthlyTransactionLimit;
	
	private Double dailyTransactionAmountLimit;
	
	private Date weeklyTransactionDate;
	
	private Date monthlyTransactionDate;

	public CustomerBankAccount getCustomerBankAccount() {
		return customerBankAccount;
	}

	public void setCustomerBankAccount(CustomerBankAccount customerBankAccount) {
		this.customerBankAccount = customerBankAccount;
	}

	public Long getDailyTransactionLimit() {
		return dailyTransactionLimit;
	}

	public void setDailyTransactionLimit(Long dailyTransactionLimit) {
		this.dailyTransactionLimit = dailyTransactionLimit;
	}

	public Double getPerTransactionLimit() {
		return perTransactionLimit;
	}

	public void setPerTransactionLimit(Double perTransactionLimit) {
		this.perTransactionLimit = perTransactionLimit;
	}

	public Double getWeeklyTransactionLimit() {
		return weeklyTransactionLimit;
	}

	public void setWeeklyTransactionLimit(Double weeklyTransactionLimit) {
		this.weeklyTransactionLimit = weeklyTransactionLimit;
	}

	public Double getMonthlyTransactionLimit() {
		return monthlyTransactionLimit;
	}

	public void setMonthlyTransactionLimit(Double monthlyTransactionLimit) {
		this.monthlyTransactionLimit = monthlyTransactionLimit;
	}

	public Date getWeeklyTransactionDate() {
		return weeklyTransactionDate;
	}

	public void setWeeklyTransactionDate(Date weeklyTransactionDate) {
		this.weeklyTransactionDate = weeklyTransactionDate;
	}

	public Date getMonthlyTransactionDate() {
		return monthlyTransactionDate;
	}

	public void setMonthlyTransactionDate(Date monthlyTransactionDate) {
		this.monthlyTransactionDate = monthlyTransactionDate;
	}

	public Double getDailyTransactionAmountLimit() {
		return dailyTransactionAmountLimit;
	}

	public void setDailyTransactionAmountLimit(Double dailyTransactionAmountLimit) {
		this.dailyTransactionAmountLimit = dailyTransactionAmountLimit;
	}

}
