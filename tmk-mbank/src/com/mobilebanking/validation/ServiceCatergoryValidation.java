package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IServiceCategoryApi;
import com.mobilebanking.model.ServiceCategoryDTO;
import com.mobilebanking.model.error.ServiceCategoryError;

@Component
public class ServiceCatergoryValidation {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IServiceCategoryApi serviceCategoryService;
	
	private ServiceCategoryError serviceCategoryError;

public ServiceCategoryError serviceCategoryValidation(ServiceCategoryDTO serviceCategoryDto){
		
		serviceCategoryError =  new ServiceCategoryError();
		
		boolean valid = true;
		
		valid = valid && checkCategoryName(serviceCategoryDto);
		serviceCategoryError.setValid(valid);
		return serviceCategoryError;
		
	}

	private boolean checkCategoryName(ServiceCategoryDTO serviceCategoryDto) {

		if(serviceCategoryDto.getName()==null){
			logger.debug("Category Name is null");
			serviceCategoryError.setName("Category Name Required");
			return false;
		}else{
			if(serviceCategoryDto.getName().trim().equals("")){
				logger.debug("Category Name is null");
				serviceCategoryError.setName("Category Name Required");
				return false;
			}else{
				ServiceCategoryDTO serviceCategory = serviceCategoryService.findByCategoryName(serviceCategoryDto.getName());
				if(serviceCategory!=null){
					logger.debug("Category  already exists");
					serviceCategoryError.setName("Category Already Exists");
					return  false;
				}
			}
		}
		
		return true;
	}


}
