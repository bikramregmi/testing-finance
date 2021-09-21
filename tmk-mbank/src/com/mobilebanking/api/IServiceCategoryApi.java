package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.ServiceCategoryDTO;

public interface IServiceCategoryApi {

	ServiceCategoryDTO saveServiceCategory(ServiceCategoryDTO serviceCategory);
	List<ServiceCategoryDTO> getAllServiceCategory();
	ServiceCategoryDTO findByCategoryName(String name);
	ServiceCategoryDTO findServiceCategoryById(long id);
	ServiceCategoryDTO editServiceCategory(ServiceCategoryDTO serviceCategory);

}
