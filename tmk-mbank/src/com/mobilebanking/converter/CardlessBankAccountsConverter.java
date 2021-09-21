package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.CardlessBankAccount;
import com.mobilebanking.model.CardlessBankAccountDTO;

@Component
public class CardlessBankAccountsConverter implements IConverter<CardlessBankAccount, CardlessBankAccountDTO>, IListConverter<CardlessBankAccount, CardlessBankAccountDTO>{

	@Override
	public List<CardlessBankAccountDTO> convertToDtoList(List<CardlessBankAccount> entityList) {
		List<CardlessBankAccountDTO> dtoList = new ArrayList<CardlessBankAccountDTO>();
		for(CardlessBankAccount entity: entityList){
			dtoList.add(convertToDto(entity));
		}
		return dtoList;
	}

	@Override
	public CardlessBankAccount convertToEntity(CardlessBankAccountDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardlessBankAccountDTO convertToDto(CardlessBankAccount entity) {
		CardlessBankAccountDTO dto = new CardlessBankAccountDTO();
		dto.setId(entity.getId());
		dto.setBank(entity.getBank().getSwiftCode());
		dto.setBankName(entity.getBank().getName());
		dto.setCardlessBank(entity.getCardlessBank().getBank());
		dto.setCardlessBankId(entity.getCardlessBank().getId());
		dto.setAccountNumber(entity.getAccountNumber());
		dto.setStatus(entity.getStatus());
		dto.setBankAccountNo(entity.getBankAccountNo());
		dto.setCardlessBankAccountNo(entity.getCardlessBankAccountNo());
		dto.setDebitTheirRef(entity.getDebitTheirRef());
		dto.setAtmBinNo(entity.getAtmBinNo());
		dto.setAtmTermNo(entity.getAtmTermNo());
		return dto;
	}

}
