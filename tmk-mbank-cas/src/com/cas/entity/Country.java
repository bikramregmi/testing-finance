package com.cas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Country extends AbstractEntity<Long> {
	private static final long serialVersionUID = -9163185658863000713L;

	@Column(unique = true,nullable = false)
	private String countryName;

	@Column(nullable = false, length = 3)
	private String countryCode;

	@Column(nullable = false)
	private String currency;
	
	@Column(nullable = false)
	private String phoneCode;

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}


	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}


}
