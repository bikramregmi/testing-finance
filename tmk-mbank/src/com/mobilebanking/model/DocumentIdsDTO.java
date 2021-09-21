/**
 * 
 */
package com.mobilebanking.model;

/**
 * @author bibek
 *
 */
public class DocumentIdsDTO {
	
	private long id;
	
	private String documentType;
	
	private boolean issuedDateRequired;
	
	private boolean expiryDateRequired;
	
	private boolean issuedStateRequired;
	
	private boolean issuedCityRequired;
	
	private Status status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
