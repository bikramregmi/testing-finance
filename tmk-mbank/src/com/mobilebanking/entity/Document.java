package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class Document extends AbstractEntity<Long> {

	private static final long serialVersionUID = -8018575743776303716L;

	private String identifier;

	private String extention;


	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

}
