package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mobilebankingcancelrequest")
public class MobileBankingCancelRequest extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
