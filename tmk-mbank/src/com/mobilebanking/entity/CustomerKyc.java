/**
 * 
 */
package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Column;
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
@Table(name="customerKyc")
public class CustomerKyc extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Customer customer;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private DocumentIds documentId;
	
	@Column
	private String documentNumber;
	
	@Column
	private Date issuedDate;
	
	@Column(nullable=true)
	private Date expiryDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private State issuedState;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private City issuedCity;
	
	@Column
	private String imageUrl;
	
	@Column
	private Status status;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public DocumentIds getDocumentId() {
		return documentId;
	}

	public void setDocumentId(DocumentIds documentId) {
		this.documentId = documentId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public State getIssuedState() {
		return issuedState;
	}

	public void setIssuedState(State issuedState) {
		this.issuedState = issuedState;
	}

	public City getIssuedCity() {
		return issuedCity;
	}

	public void setIssuedCity(City issuedCity) {
		this.issuedCity = issuedCity;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
