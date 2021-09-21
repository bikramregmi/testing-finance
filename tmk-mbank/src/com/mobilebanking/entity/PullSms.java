package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pullsms")
public class PullSms extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String smskeyword;
	
	private String smstext ;
	
	private String smsfrom ;
	
	private String smsto;

	public String getSmskeyword() {
		return smskeyword;
	}

	public void setSmskeyword(String smskeyword) {
		this.smskeyword = smskeyword;
	}

	public String getSmstext() {
		return smstext;
	}

	public void setSmstext(String smstext) {
		this.smstext = smstext;
	}

	public String getSmsfrom() {
		return smsfrom;
	}

	public void setSmsfrom(String smsfrom) {
		this.smsfrom = smsfrom;
	}

	public String getSmsto() {
		return smsto;
	}

	public void setSmsto(String smsto) {
		this.smsto = smsto;
	}

}
