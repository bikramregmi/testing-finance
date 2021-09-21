package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.MobileBankingCancelRequest;
import com.mobilebanking.model.MobileBankingCancelRequestDTO;

@Component
public class MobileBankingCancelRequestConverter
		implements IConverter<MobileBankingCancelRequest, MobileBankingCancelRequestDTO>,
		IListConverter<MobileBankingCancelRequest, MobileBankingCancelRequestDTO> {

	@Override
	public List<MobileBankingCancelRequestDTO> convertToDtoList(List<MobileBankingCancelRequest> entityList) {
		List<MobileBankingCancelRequestDTO> dtoList = new ArrayList<>();
		for (MobileBankingCancelRequest entity : entityList)
			dtoList.add(convertToDto(entity));
		return dtoList;
	}

	@Override
	public MobileBankingCancelRequest convertToEntity(MobileBankingCancelRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MobileBankingCancelRequestDTO convertToDto(MobileBankingCancelRequest entity) {
		MobileBankingCancelRequestDTO dto = new MobileBankingCancelRequestDTO();
		dto.setId(entity.getId());
		if (entity.getCustomer().getMiddleName() != null)
			dto.setCustomerName(entity.getCustomer().getFirstName() + " " + entity.getCustomer().getMiddleName() + " "
					+ entity.getCustomer().getLastName());
		else
			dto.setCustomerName(entity.getCustomer().getFirstName() + " " + entity.getCustomer().getLastName());
		dto.setMobileNumber(entity.getCustomer().getMobileNumber());
		dto.setCustomerUniqueId(entity.getCustomer().getUniqueId());
		dto.setBank(entity.getCustomer().getBank().getName());
		dto.setBankbranch(entity.getCustomer().getBankBranch().getName());
		dto.setRequestedDate(entity.getCreated().toString().substring(0, 16));
		return dto;
	}

}
