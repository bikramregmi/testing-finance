package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.ChequeBlockRequest;
import com.mobilebanking.model.ChequeBlockRequestDTO;
import com.mobilebanking.util.DateUtil;

@Component
public class ChequeBlockRequestConverter implements IListConverter<ChequeBlockRequest, ChequeBlockRequestDTO> , IConverter<ChequeBlockRequest, ChequeBlockRequestDTO>{

	@Override
	public List<ChequeBlockRequestDTO> convertToDtoList(List<ChequeBlockRequest> entityList) {
		
		List<ChequeBlockRequestDTO> chequeBlockRequestList = new ArrayList<>();
		
		for(ChequeBlockRequest entity : entityList ){
			ChequeBlockRequestDTO checkBlockRequest = convertToDto(entity);
			chequeBlockRequestList.add(checkBlockRequest);
		}
		
		return chequeBlockRequestList;
	}

	@Override
	public ChequeBlockRequest convertToEntity(ChequeBlockRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChequeBlockRequestDTO convertToDto(ChequeBlockRequest entity) {
		ChequeBlockRequestDTO chequeBlockRequestDTO = new ChequeBlockRequestDTO();
		chequeBlockRequestDTO.setAccountNumber(entity.getCustomerAccount().getAccountNumber());
		chequeBlockRequestDTO.setChequeNumber(entity.getChequeNumber());
		chequeBlockRequestDTO.setMobileNumber(entity.getCustomerAccount().getCustomer().getMobileNumber());
		chequeBlockRequestDTO.setCustomerName(entity.getCustomerAccount().getCustomer().getFirstName()+" "+entity.getCustomerAccount().getCustomer().getMiddleName()+" "+entity.getCustomerAccount().getCustomer().getLastName());
		chequeBlockRequestDTO.setStatus(entity.getStatus());
		chequeBlockRequestDTO.setId(entity.getId());
		chequeBlockRequestDTO.setDate(DateUtil.convertDateToString(entity.getCreated()));
		return chequeBlockRequestDTO;
		
	}

	
	
}
