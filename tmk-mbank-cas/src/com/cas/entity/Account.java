package com.cas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Account extends AbstractEntity<Long> {
	private static final long serialVersionUID = -9163185658863000713L;
	
	@Column(unique = true,nullable = false)
	private String accountNo;

	@Column
	private double  balance;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Country country;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
