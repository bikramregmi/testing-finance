package com.mobilebanking.model;

import java.util.List;

public class NotificationGroupDTO {
	private String name;

	private List<Long> customerList;

	private Integer customerCount;

	private Long bankId;
	
	private String bankName;
	
	private String bankCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Long> customerList) {
		this.customerList = customerList;
	}

	public Integer getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(Integer customerCount) {
		this.customerCount = customerCount;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
