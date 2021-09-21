package com.mobilebanking.model;

import java.util.List;

public class ServiceCategoryDTO {

	long id;
	
	String name;
	
	List<MerchantServiceDTO> serviceDto;

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

	public List<MerchantServiceDTO> getServiceDto() {
		return serviceDto;
	}

	public void setServiceDto(List<MerchantServiceDTO> serviceDto) {
		this.serviceDto = serviceDto;
	}
	
	

}
