package com.mobilebanking.model;

import java.util.List;

public class PaymentCatagoryDTO {
	
	long id;
	
	String name;
	
	List<ServicesDTO> serviceDTO;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServicesDTO> getServiceDTO() {
		return serviceDTO;
	}

	public void setServiceDTO(List<ServicesDTO> serviceDTO) {
		this.serviceDTO = serviceDTO;
	}

	

	
	
	
	
}
