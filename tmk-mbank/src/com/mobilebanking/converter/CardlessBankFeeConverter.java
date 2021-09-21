package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.CardlessBankFee;
import com.mobilebanking.model.CardlessBankFeeDTO;

@Component
public class CardlessBankFeeConverter implements IConverter<CardlessBankFee, CardlessBankFeeDTO>,
		IListConverter<CardlessBankFee, CardlessBankFeeDTO> {

	@Override
	public List<CardlessBankFeeDTO> convertToDtoList(List<CardlessBankFee> entityList) {
		List<CardlessBankFeeDTO> dtoList = new ArrayList<CardlessBankFeeDTO>();
		for (CardlessBankFee entity : entityList) {
			dtoList.add(convertToDto(entity));
		}
		return dtoList;
	}

	@Override
	public CardlessBankFee convertToEntity(CardlessBankFeeDTO dto) {
		CardlessBankFee entity = new CardlessBankFee();
		entity.setFromAmount(dto.getFromAmount());
		entity.setToAmount(dto.getToAmount());
		entity.setFee(dto.getFee());
		return entity;
	}

	@Override
	public CardlessBankFeeDTO convertToDto(CardlessBankFee entity) {
		CardlessBankFeeDTO dto = new CardlessBankFeeDTO();
		dto.setId(entity.getId());
		dto.setCardlessBank(entity.getCardlessBank().getBank());
		dto.setCardlessBankId(entity.getCardlessBank().getId());
		dto.setSameForAll(entity.getSameForAll());
		if (!entity.getSameForAll()) {
			dto.setFromAmount(entity.getFromAmount());
			dto.setToAmount(entity.getToAmount());
		}
		dto.setFee(entity.getFee());
		return dto;
	}

}
