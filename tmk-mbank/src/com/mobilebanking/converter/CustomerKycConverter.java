package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.CustomerKyc;
import com.mobilebanking.model.CustomerKycDTO;
import com.mobilebanking.util.DateUtil;

@Component
public class CustomerKycConverter
		implements IConverter<CustomerKyc, CustomerKycDTO>, IListConverter<CustomerKyc, CustomerKycDTO> {

	@Override
	public List<CustomerKycDTO> convertToDtoList(List<CustomerKyc> entityList) {
		List<CustomerKycDTO> customerKycDtoList = new ArrayList<CustomerKycDTO>();
		for (CustomerKyc ck : entityList) {
			customerKycDtoList.add(convertToDto(ck));
		}
		return customerKycDtoList;
	}

	@Override
	public CustomerKyc convertToEntity(CustomerKycDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerKycDTO convertToDto(CustomerKyc entity) {
		CustomerKycDTO customerKycDto = new CustomerKycDTO();
		customerKycDto.setId(entity.getId());
		customerKycDto.setCustomer(
				entity.getCustomer().getFirstName() + " " + entity.getCustomer().getLastName());;
		customerKycDto.setDocumentId(entity.getDocumentId().getDocumentType());
		customerKycDto.setDocumentNumber(entity.getDocumentNumber());
		if (entity.getExpiryDate() != null) {
			customerKycDto.setExpiryDate(DateUtil.convertDateToString(entity.getExpiryDate()));
		} else {
			customerKycDto.setExpiryDate(null);
		}
		customerKycDto.setIssuedDate(DateUtil.convertDateToString(entity.getIssuedDate()));
		customerKycDto.setIssuedState(entity.getIssuedState().getName());
		customerKycDto.setIssuedCity(entity.getIssuedCity().getName());
		customerKycDto.setStatus(entity.getStatus());
		
		return customerKycDto;
	}

}
