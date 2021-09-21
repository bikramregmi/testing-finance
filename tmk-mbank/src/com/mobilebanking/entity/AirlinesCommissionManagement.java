package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "airlinesCommission")
public class AirlinesCommissionManagement extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	@OneToOne
	private Customer customer;
	
	private Double commissionAmount;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(Double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	
}
