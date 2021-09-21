package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.ComplianceType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="compliance")
public class Compliance extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Country country;
	
	@Column(nullable=false)
	private int days;
	
	@Column(nullable=false)
	private double amount;
	
	@Column(nullable=false)
	private String requirements;

	private ComplianceType complianceType;
	
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public ComplianceType getComplianceType() {
		return complianceType;
	}

	public void setComplianceType(ComplianceType complianceType) {
		this.complianceType = complianceType;
	}
	
	
	
	
}
