package com.mobilebanking.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.mobilebanking.model.ProfileStatus;

@Entity
@Table(name = "customerprofile")
public class CustomerProfile extends AbstractEntity<Long> {

	private static final long serialVersionUID = -8154589559046692285L;

	@OneToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	@JoinTable(name = "customerprofile_customer", joinColumns = @JoinColumn(name = "customerprofile_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private List<Customer> customer;

	private String name;

	private Integer perDayTransactionLimit;

	private Double perTransactionLimit;

	private Long dailyTransactionLimit;

	private Double weeklyTransactionLimit;

	private Double monthlyTransactionLimit;

	private Double dailyTransactionAmount;

	private ProfileStatus status;

	@ManyToOne
	private Bank bank;

	private Double registrationCharge;

	private Double renewCharge;

	private Double pinResetCharge;
	
	private String profileUniqueId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public Double getPerTransactionLimit() {
		return perTransactionLimit;
	}

	public void setPerTransactionLimit(Double perTransactionLimit) {
		this.perTransactionLimit = perTransactionLimit;
	}

	public Long getDailyTransactionLimit() {
		return dailyTransactionLimit;
	}

	public void setDailyTransactionLimit(Long dailyTransactionLimit) {
		this.dailyTransactionLimit = dailyTransactionLimit;
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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Integer getPerDayTransactionLimit() {
		return perDayTransactionLimit;
	}

	public void setPerDayTransactionLimit(Integer perDayTransactionLimit) {
		this.perDayTransactionLimit = perDayTransactionLimit;
	}

	public Double getDailyTransactionAmount() {
		return dailyTransactionAmount;
	}

	public void setDailyTransactionAmount(Double dailyTransactionAmount) {
		this.dailyTransactionAmount = dailyTransactionAmount;
	}

	public ProfileStatus getStatus() {
		return status;
	}

	public void setStatus(ProfileStatus status) {
		this.status = status;
	}

	public Double getRegistrationCharge() {
		return registrationCharge;
	}

	public void setRegistrationCharge(Double registrationCharge) {
		this.registrationCharge = registrationCharge;
	}

	public Double getRenewCharge() {
		return renewCharge;
	}

	public void setRenewCharge(Double renewCharge) {
		this.renewCharge = renewCharge;
	}

	public Double getPinResetCharge() {
		return pinResetCharge;
	}

	public void setPinResetCharge(Double pinResetCharge) {
		this.pinResetCharge = pinResetCharge;
	}

	public String getProfileUniqueId() {
		return profileUniqueId;
	}

	public void setProfileUniqueId(String profileUniqueId) {
		this.profileUniqueId = profileUniqueId;
	}

}
