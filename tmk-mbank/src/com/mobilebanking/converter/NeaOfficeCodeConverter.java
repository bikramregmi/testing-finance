package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.NeaOfficeCode;
import com.mobilebanking.model.NeaOfficeCodeDTO;
import com.mobilebanking.model.Status;

@Component
public class NeaOfficeCodeConverter implements IConverter<NeaOfficeCode, NeaOfficeCodeDTO>, IListConverter<NeaOfficeCode, NeaOfficeCodeDTO>{

	@Override
	public List<NeaOfficeCodeDTO> convertToDtoList(List<NeaOfficeCode> entityList) {
		List<NeaOfficeCodeDTO> NeaOfficeCodeDTOList = new ArrayList<NeaOfficeCodeDTO>();
		for (NeaOfficeCode neaOfficeCode : entityList) {
			NeaOfficeCodeDTOList.add(convertToDto(neaOfficeCode));
		}
		return NeaOfficeCodeDTOList;
	}

	@Override
	public NeaOfficeCode convertToEntity(NeaOfficeCodeDTO dto) {
		NeaOfficeCode entity = new NeaOfficeCode();
		entity.setOffice(dto.getOffice());
		entity.setOfficeCode(dto.getOfficeCode());
		entity.setStatus(Status.Active);
		return entity;
	}

	@Override
	public NeaOfficeCodeDTO convertToDto(NeaOfficeCode entity) {
		NeaOfficeCodeDTO dto = new NeaOfficeCodeDTO();
		if(entity == null ){
			return null;
		}
		dto.setId(entity.getId());
		dto.setOffice(entity.getOffice());
		dto.setOfficeCode(entity.getOfficeCode());
		dto.setStatus(entity.getStatus());
		return dto;
	}


}
