package com.mobilebanking.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="merchant")
public class Merchant extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	//maintain balance for each merchant per Bank
	
	@Column(nullable = true)
	private String accountNumber;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String registrationNumber;
	
	@Column(nullable = false)
	private String vatNumber;
	
	@Column(nullable = false)
	private String mobileNumber;
	
	@Column
	private String landLine;
	
	@Column(nullable = false)
	private String ownerName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private City city;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private Status status;
	
	@Column(nullable = false)
	private String merchantApiUrl;
	
	@Column(nullable = false)
	private String apiUsername ;
	
	@Column(nullable = false)
	private String  apiPassWord;
	//added on may 16 2018
	@Column(name="extra_field_one")
	private String extraFieldOne;
	
	@Column(name="extra_field_two")
	private String extraFieldTwo;
	@Column(name="extra_field_three")
	private String extraFieldThree;
	//end added
	@ElementCollection
	private Set<MerchantManager> merchantManagers = new HashSet<MerchantManager>();

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLandLine() {
		return landLine;
	}

	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<MerchantManager> getMerchantManagers() {
		return merchantManagers;
	}

	public void setMerchantManagers(Set<MerchantManager> merchantManagers) {
		this.merchantManagers = merchantManagers;
	}

	public String getMerchantApiUrl() {
		return merchantApiUrl;
	}

	public void setMerchantApiUrl(String merchantApiUrl) {
		this.merchantApiUrl = merchantApiUrl;
	}

	public String getApiUsername() {
		return apiUsername;
	}

	public void setApiUsername(String apiUsername) {
		this.apiUsername = apiUsername;
	}

	public String getApiPassWord() {
		return apiPassWord;
	}

	public void setApiPassWord(String apiPassWord) {
		this.apiPassWord = apiPassWord;
	}
	
	public String getExtraFieldOne() {
		return extraFieldOne;
	}

	public void setExtraFieldOne(String extraFieldOne) {
		this.extraFieldOne = extraFieldOne;
	}

	public String getExtraFieldTwo() {
		return extraFieldTwo;
	}

	public void setExtraFieldTwo(String extraFieldTwo) {
		this.extraFieldTwo = extraFieldTwo;
	}

	public String getExtraFieldThree() {
		return extraFieldThree;
	}

	public void setExtraFieldThree(String extraFieldThree) {
		this.extraFieldThree = extraFieldThree;
	}


}
