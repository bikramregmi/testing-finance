/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="documentIds")
public class DocumentIds extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Column
	private String documentType;
	
	@Column
	private boolean issuedDateRequired = true;
	
	@Column
	private boolean expiryDateRequired = false;
	
	@Column
	private boolean issuedStateRequired = true;
	
	@Column
	private boolean issuedCityRequired = true;
	
	@Column
	private Status status;

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public boolean isIssuedDateRequired() {
		return issuedDateRequired;
	}

	public void setIssuedDateRequired(boolean issuedDateRequired) {
		this.issuedDateRequired = issuedDateRequired;
	}

	public boolean isExpiryDateRequired() {
		return expiryDateRequired;
	}

	public void setExpiryDateRequired(boolean expiryDateRequired) {
		this.expiryDateRequired = expiryDateRequired;
	}

	public boolean isIssuedStateRequired() {
		return issuedStateRequired;
	}

	public void setIssuedStateRequired(boolean issuedStateRequired) {
		this.issuedStateRequired = issuedStateRequired;
	}

	public boolean isIssuedCityRequired() {
		return issuedCityRequired;
	}

	public void setIssuedCityRequired(boolean issuedCityRequired) {
		this.issuedCityRequired = issuedCityRequired;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
