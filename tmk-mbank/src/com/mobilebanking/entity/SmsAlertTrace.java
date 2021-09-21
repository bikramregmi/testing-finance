package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="smsalerttrace")
public class SmsAlertTrace extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	private Long traceId;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Bank bank;

	public Long getTraceId() {
		return traceId;
	}

	public void setTraceId(Long traceId) {
		this.traceId = traceId;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
}
