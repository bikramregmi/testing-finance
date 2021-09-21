package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "ofsrequest")
public class OfsRequest extends AbstractEntity<Long>{

	
	private static final long serialVersionUID = -7986826773177367074L;

	@Lob
	@Column(length=3000)
	private String message;
	
	private Long cardlessBankId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCardlessBankId() {
		return cardlessBankId;
	}

	public void setCardlessBankId(Long cardlessBankId) {
		this.cardlessBankId = cardlessBankId;
	}
	
}
