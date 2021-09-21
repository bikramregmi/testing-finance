package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IServiceCategoryApi;
import com.mobilebanking.converter.ServiceCategoryConverter;
import com.mobilebanking.entity.ServiceCategory;
import com.mobilebanking.model.ServiceCategoryDTO;
import com.mobilebanking.repositories.ServiceCategoryRepository;

@Service
public class ServiceCategoryApi implements IServiceCategoryApi {

	@Autowired
	private ServiceCategoryConverter serviceCategoryConverter;
	
	@Autowired
	private ServiceCategoryRepository serviceCategoryRepository;
	
	
	@Override
	public ServiceCategoryDTO saveServiceCategory(ServiceCategoryDTO serviceCategoryDto) {
		ServiceCategory serviceCategory = serviceCategoryConverter.convertToEntity(serviceCategoryDto);
		serviceCategory = serviceCategoryRepository.save(serviceCategory);
		//to do convert util
		return serviceCategoryConverter.convertToDto(serviceCategory);

	}

	@Override
	public List<ServiceCategoryDTO> getAllServiceCategory() {
		List<ServiceCategory> serviceCatgory = serviceCategoryRepository.findAll();
		return serviceCategoryConverter.convertToDtoList(serviceCatgory);
	}

	@Override
	public ServiceCategoryDTO findByCategoryName(String name) {
		ServiceCategory serviceCategory  = serviceCategoryRepository.findByName(name);
		if(serviceCategory!=null)
		return serviceCategoryConverter.convertToDto(serviceCategory);
		else return null;
	}

	@Override
	public ServiceCategoryDTO findServiceCategoryById(long id) {
		ServiceCategory serviceCategory  = serviceCategoryRepository.findOne(id);
		if(serviceCategory!=null)
			return serviceCategoryConverter.convertToDto(serviceCategory);
			else return null;
	}

	@Override
	public ServiceCategoryDTO editServiceCategory(ServiceCategoryDTO serviceCategoryDto) {
		ServiceCategory serviceCategory = serviceCategoryRepository.findOne(serviceCategoryDto.getId());
		serviceCategory.setName(serviceCategoryDto.getName());
		serviceCategory = serviceCategoryRepository.save(serviceCategory);
		return serviceCategoryConverter.convertToDto(serviceCategory);
	}

}
