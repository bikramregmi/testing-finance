package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

@Entity
@Table(name = "homescreenimage")
public class HomeScreenImage extends AbstractEntity<Long>{

	private static final long serialVersionUID = -4073053698548268320L;
	
	@OneToOne
	private Document document;
	
	@ManyToOne
	private Bank bank;
	
	@Column
	private Status status;
	
	@Column
	private int placement;

	public Document getDocument() {
		return document;
	}

	public Bank getBank() {
		return bank;
	}

	public Status getStatus() {
		return status;
	}

	public int getPlacement() {
		return placement;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPlacement(int placement) {
		this.placement = placement;
	}

}
