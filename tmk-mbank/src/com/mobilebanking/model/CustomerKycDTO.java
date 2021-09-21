/**
 * 
 */
package com.mobilebanking.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author bibek
 *
 */
public class CustomerKycDTO {
	
	private long id;
	
	private String documentId;
	
	private String documentNumber;
	
	private String issuedDate;
	
	private String expiryDate;
	
	private String issuedState;
	
	private String issuedCity;
	
	private String imageUrl;
	
	private String customer;
	
	private byte[] file;
	
	private MultipartFile multiPartFile;
	
	private Status status;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getIssuedState() {
		return issuedState;
	}

	public void setIssuedState(String issuedState) {
		this.issuedState = issuedState;
	}

	public String getIssuedCity() {
		return issuedCity;
	}

	public void setIssuedCity(String issuedCity) {
		this.issuedCity = issuedCity;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public MultipartFile getMultiPartFile() {
		return multiPartFile;
	}

	public void setMultiPartFile(MultipartFile multiPartFile) {
		this.multiPartFile = multiPartFile;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
