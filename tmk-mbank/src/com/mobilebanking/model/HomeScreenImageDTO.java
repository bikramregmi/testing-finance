package com.mobilebanking.model;

import org.springframework.web.multipart.MultipartFile;

public class HomeScreenImageDTO {
	
	private Long id;
	
	private String image;
	
	private Long bankId;
	
	private Status status;
	
	private Integer placement;
	
	private MultipartFile file;

	public Long getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public Long getBankId() {
		return bankId;
	}

	public Status getStatus() {
		return status;
	}

	public Integer getPlacement() {
		return placement;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPlacement(Integer placement) {
		this.placement = placement;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
