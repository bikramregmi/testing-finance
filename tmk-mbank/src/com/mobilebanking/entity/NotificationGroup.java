package com.mobilebanking.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "notificationGroup")
public class NotificationGroup extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	private String name;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Customer> customerList;
	
	private String bankCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
