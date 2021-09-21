package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.ServiceCategory;
import com.mobilebanking.model.ServiceCategoryDTO;

@Component
public class ServiceCategoryConverter implements IConverter<ServiceCategory, ServiceCategoryDTO>,IListConverter<ServiceCategory, ServiceCategoryDTO> {

	@Override
	public List<ServiceCategoryDTO> convertToDtoList(List<ServiceCategory> entityList) {
		List<ServiceCategoryDTO> serviceCategoryDtoList = new ArrayList<ServiceCategoryDTO>();
		for (ServiceCategory serviceCategory : entityList) {
			serviceCategoryDtoList.add(convertToDto(serviceCategory));
		}
		return serviceCategoryDtoList;	
		}

	@Override
	public ServiceCategory convertToEntity(ServiceCategoryDTO dto) {
		
		ServiceCategory serviceCategory = new ServiceCategory();
		serviceCategory.setName(dto.getName());
		return serviceCategory;

	}

	@Override
	public ServiceCategoryDTO convertToDto(ServiceCategory entity) {
		// TODO Auto-generated method stub
		ServiceCategoryDTO serviceCategoryDto = new ServiceCategoryDTO();
		serviceCategoryDto.setName(entity.getName());
		serviceCategoryDto.setId(entity.getId());
//		serviceCategoryDto.setServiceDto(entity.getMerchantServices());
		
		return serviceCategoryDto;
	}

}
