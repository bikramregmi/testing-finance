package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.CustomerBenificiary;
import com.mobilebanking.model.BeneficiaryDTO;

@Component
public class BeneficiaryConverter implements IConverter<CustomerBenificiary, BeneficiaryDTO>, IListConverter<CustomerBenificiary, BeneficiaryDTO> {

	@Override
	public List<BeneficiaryDTO> convertToDtoList(List<CustomerBenificiary> entityList) {
		
		List<BeneficiaryDTO> benificiaryList = new ArrayList<>();
		for(CustomerBenificiary beneficiary : entityList ){
			BeneficiaryDTO benificiaryDto = convertToDto(beneficiary);
			benificiaryList.add(benificiaryDto);
		}
		return benificiaryList;
	}

	@Override
	public CustomerBenificiary convertToEntity(BeneficiaryDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeneficiaryDTO convertToDto(CustomerBenificiary entity) {

		BeneficiaryDTO beneficiaryDto = new BeneficiaryDTO();
		beneficiaryDto.setId(entity.getId());
		beneficiaryDto.setAccountNumber(entity.getAccountNumber());
		beneficiaryDto.setName(entity.getReciever());
		beneficiaryDto.setBankId(entity.getBank().getId());
		beneficiaryDto.setBankName(entity.getBank().getName());
		beneficiaryDto.setBankBranchName(entity.getBankBranch().getName());
		beneficiaryDto.setBankBranchId(entity.getBankBranch().getId());
		
		return beneficiaryDto;
	}

}
