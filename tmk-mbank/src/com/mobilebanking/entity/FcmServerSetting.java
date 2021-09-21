package com.mobilebanking.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "fcmServerSetting")
public class FcmServerSetting extends AbstractEntity<Long> {

	private static final long serialVersionUID = -3565589383044693716L;

	@Column(unique=true)
	private String fcmServerKey;
	@Column(unique=true)
	private String fcmServerID;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Bank> bankList;

	public String getFcmServerKey() {
		return fcmServerKey;
	}

	public void setFcmServerKey(String fcmServerKey) {
		this.fcmServerKey = fcmServerKey;
	}

	public String getFcmServerID() {
		return fcmServerID;
	}

	public void setFcmServerID(String fcmServerID) {
		this.fcmServerID = fcmServerID;
	}

	public List<Bank> getBankList() {
		return bankList;
	}

	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}

}
