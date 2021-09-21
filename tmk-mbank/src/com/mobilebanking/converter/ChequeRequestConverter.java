package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.ChequeRequest;
import com.mobilebanking.model.ChequeRequestDTO;
import com.mobilebanking.util.DateUtil;

@Component
public class ChequeRequestConverter implements IConverter<ChequeRequest, ChequeRequestDTO>, IListConverter<ChequeRequest, ChequeRequestDTO> {

	@Override
	public List<ChequeRequestDTO> convertToDtoList(List<ChequeRequest> entityList) {
		List<ChequeRequestDTO> chequeRequestList = new ArrayList<>();
		for(ChequeRequest chequeRequest :entityList ){
			ChequeRequestDTO chequeRequestDTO = convertToDto(chequeRequest);
			chequeRequestList.add(chequeRequestDTO);
		}
		return chequeRequestList;
	}

	@Override
	public ChequeRequest convertToEntity(ChequeRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChequeRequestDTO convertToDto(ChequeRequest entity) {
		ChequeRequestDTO chequeRequestDTO = new  ChequeRequestDTO();
		chequeRequestDTO.setAccountNumber(entity.getCustomerAccount().getAccountNumber());
		chequeRequestDTO.setChequeLeaves(entity.getChequeLeaves());
		chequeRequestDTO.setCustomerName(entity.getCustomerAccount().getCustomer().getFirstName()+" "+entity.getCustomerAccount().getCustomer().getMiddleName()+" "+entity.getCustomerAccount().getCustomer().getLastName());
		chequeRequestDTO.setMobileNumber(entity.getCustomerAccount().getCustomer().getMobileNumber());
		chequeRequestDTO.setStatus(entity.getChequeRequestStatus());
		chequeRequestDTO.setDate(DateUtil.convertDateToString(entity.getCreated()));
		chequeRequestDTO.setId(entity.getId());
		return chequeRequestDTO;
	}

}
