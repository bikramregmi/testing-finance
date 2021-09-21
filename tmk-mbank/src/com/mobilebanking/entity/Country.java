package com.mobilebanking.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="country")
public class Country extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String isoTwo;
	
	@Column(unique = true, nullable = false)
	private String isoThree;
	
	@Column(nullable = false)
	private String dialCode;
	
	
	@Column(nullable = false)
	private Status status;
	
	@OneToMany(fetch=FetchType.LAZY)
	private List<Bank> bankList;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="country")
	private List<Compliance> complianceList;
	
	@Column
	private String currencyCode;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoTwo() {
		return isoTwo;
	}

	public void setIsoTwo(String isoTwo) {
		this.isoTwo = isoTwo;
	}

	public String getIsoThree() {
		return isoThree;
	}

	public void setIsoThree(String isoThree) {
		this.isoThree = isoThree;
	}

	public String getDialCode() {
		return dialCode;
	}

	public void setDialCode(String dialCode) {
		this.dialCode = dialCode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}


	public List<Bank> getBankList() {
		return bankList;
	}

	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}

	public List<Compliance> getComplianceList() {
		return complianceList;
	}

	public void setComplianceList(List<Compliance> complianceList) {
		this.complianceList = complianceList;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
