package com.wallet.serviceprovider.khalti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KhanepaniCustomerDetailResponse {
	
	private String status;
	
	private String customer_code;
	
	private String customer_name;
	
	private String address;
	
	private String mobile_number;
	
	private String current_month_dues;
	
	private String current_month_discount;
	
	private String current_month_fine;
	
	private String total_credit_sales_amount;
	
	private String total_advance_amount;
	
	private String previous_dues;
	
	private String total_dues;
	
	private String service_charge;

	public String getStatus() {
		return status;
	}

	public String getCustomer_code() {
		return customer_code;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public String getAddress() {
		return address;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public String getCurrent_month_dues() {
		return current_month_dues;
	}

	public String getCurrent_month_discount() {
		return current_month_discount;
	}

	public String getCurrent_month_fine() {
		return current_month_fine;
	}

	public String getTotal_credit_sales_amount() {
		return total_credit_sales_amount;
	}

	public String getTotal_advance_amount() {
		return total_advance_amount;
	}

	public String getPrevious_dues() {
		return previous_dues;
	}

	public String getTotal_dues() {
		return total_dues;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public void setCurrent_month_dues(String current_month_dues) {
		this.current_month_dues = current_month_dues;
	}

	public void setCurrent_month_discount(String current_month_discount) {
		this.current_month_discount = current_month_discount;
	}

	public void setCurrent_month_fine(String current_month_fine) {
		this.current_month_fine = current_month_fine;
	}

	public void setTotal_credit_sales_amount(String total_credit_sales_amount) {
		this.total_credit_sales_amount = total_credit_sales_amount;
	}

	public void setTotal_advance_amount(String total_advance_amount) {
		this.total_advance_amount = total_advance_amount;
	}

	public void setPrevious_dues(String previous_dues) {
		this.previous_dues = previous_dues;
	}

	public void setTotal_dues(String total_dues) {
		this.total_dues = total_dues;
	}

	public String getService_charge() {
		return service_charge;
	}

	public void setService_charge(String service_charge) {
		this.service_charge = service_charge;
	}

}
