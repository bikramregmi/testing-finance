package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="otpinfo")
public class OtpInfo extends AbstractEntity<Long>{

	private static final long serialVersionUID = -360643126141848487L;

	private String secretKey;
	
	private String timeStep;
	
	private Date date;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(String timeStep) {
		this.timeStep = timeStep;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
