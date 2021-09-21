package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.SparrowSettings;
import com.mobilebanking.model.SparrowSettingsDTO;

@Component
public class SparrowSettingsConverter implements IConverter<SparrowSettings, SparrowSettingsDTO>,
		IListConverter<SparrowSettings, SparrowSettingsDTO> {

	@Override
	public List<SparrowSettingsDTO> convertToDtoList(List<SparrowSettings> entityList) {
		List<SparrowSettingsDTO> dtoList = new ArrayList<>();
		for (SparrowSettings entity : entityList) {
			dtoList.add(convertToDto(entity));
		}
		return dtoList;
	}

	@Override
	public SparrowSettings convertToEntity(SparrowSettingsDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SparrowSettingsDTO convertToDto(SparrowSettings entity) {
		SparrowSettingsDTO dto = new SparrowSettingsDTO();
		dto.setUrl(entity.getUrl());
		dto.setFrom(entity.getIdentity());
		dto.setToken(entity.getToken());
		dto.setShortCode(entity.getShortCode());
		return dto;
	}

}
