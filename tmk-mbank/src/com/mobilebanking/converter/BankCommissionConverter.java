package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.BankCommission;
import com.mobilebanking.model.BankCommissionDTO;

@Component
public class BankCommissionConverter implements IListConverter<BankCommission, BankCommissionDTO>, IConverter<BankCommission, BankCommissionDTO>{

	@Override
	public BankCommission convertToEntity(BankCommissionDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankCommissionDTO convertToDto(BankCommission entity) {
		BankCommissionDTO dto = new BankCommissionDTO();
		dto.setId(entity.getId());
		dto.setCommissionFlat(entity.getCommissionFlat());
		dto.setCommissionPercentage(entity.getCommissionPercentage());
		dto.setFeeFlat(entity.getFeeFlat());
		dto.setFeePercentage(entity.getFeePercentage());
		dto.setChannelPartnerCommissionFlat(entity.getChannelPartnerCommissionFlat());
		dto.setChannelPartnerCommissionPercentage(entity.getChannelPartnerCommissionPercentage());
		dto.setOperatorCommissionFlat(entity.getOperatorCommissionFlat());
		dto.setOperatorCommissionPercentage(entity.getOperatorCommissionPercentage());
		dto.setCommissionAmountId(entity.getCommissionAmount().getId());
		dto.setCashBack(entity.getCashBack());
		return dto;
	}

	@Override
	public List<BankCommissionDTO> convertToDtoList(List<BankCommission> entityList) {
		List<BankCommissionDTO> dtoList = new ArrayList<>();
		for(BankCommission entity : entityList){
			dtoList.add(convertToDto(entity));
		}
		return dtoList;
	}

}
