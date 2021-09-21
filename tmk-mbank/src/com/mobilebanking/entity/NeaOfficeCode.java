package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

@Entity
@Table(name = "officecode")
public class NeaOfficeCode extends AbstractEntity<Long> {

	private static final long serialVersionUID = -722912148247821115L;

	private String officeCode;

	private String office;

	private Status status;

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}