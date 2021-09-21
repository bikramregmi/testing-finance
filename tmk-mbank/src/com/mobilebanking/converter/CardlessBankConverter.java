package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.CardlessBank;
import com.mobilebanking.model.CardlessBankDTO;

@Component
public class CardlessBankConverter implements IConverter<CardlessBank, CardlessBankDTO>, IListConverter<CardlessBank, CardlessBankDTO>{

	@Override
	public List<CardlessBankDTO> convertToDtoList(List<CardlessBank> entityList) {
		List<CardlessBankDTO> dtoList = new ArrayList<CardlessBankDTO>();
		for(CardlessBank entity : entityList){
			dtoList.add(convertToDto(entity));
		}
		return dtoList;
	}

	@Override
	public CardlessBank convertToEntity(CardlessBankDTO dto) {
		CardlessBank entity = new CardlessBank();
		entity.setBank(dto.getBank());
		entity.setHost(dto.getHost());
		entity.setPort(dto.getPort());
		entity.setUserSign(dto.getUserSign());
		entity.setUserPassword(dto.getUserPassword());
		entity.setCompanyCode(dto.getCompanyCode());
		entity.setAddress(dto.getAddress());
		return entity;
	}

	@Override
	public CardlessBankDTO convertToDto(CardlessBank entity) {
		CardlessBankDTO dto = new CardlessBankDTO();
		dto.setId(entity.getId());
		dto.setBank(entity.getBank());
		dto.setHost(entity.getHost());
		dto.setPort(entity.getPort());
		dto.setUserSign(entity.getUserSign());
		dto.setUserPassword(entity.getUserPassword());
		dto.setCompanyCode(entity.getCompanyCode());
		if(entity.getUser()!=null)
		dto.setUser(entity.getUser().getId());
		dto.setAddress(entity.getAddress());
		dto.setCity(entity.getCity().getName());
		return dto;
	}

}
