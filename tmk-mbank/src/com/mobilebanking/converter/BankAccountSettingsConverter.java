/**
 * 
 */
package com.mobilebanking.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.BankAccountSettings;
import com.mobilebanking.model.BankAccountSettingsDto;

/**
 * @author bibek
 *
 */
@Component
public class BankAccountSettingsConverter implements IConverter<BankAccountSettings, BankAccountSettingsDto>,
		IListConverter<BankAccountSettings, BankAccountSettingsDto> {

	@Override
	public List<BankAccountSettingsDto> convertToDtoList(List<BankAccountSettings> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankAccountSettings convertToEntity(BankAccountSettingsDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankAccountSettingsDto convertToDto(BankAccountSettings entity) {
		BankAccountSettingsDto dto = new BankAccountSettingsDto();
		dto.setBank(entity.getBank().getName());
		dto.setBankPoolAccountNumber(entity.getBankPoolAccountNumber());
		dto.setChannelPartnerAccountNumber(entity.getChannelPartnerAccountNumber());
		dto.setOperatorAccountNumber(entity.getOperatorAccountNumber());
		dto.setBankParkingAccountNumber(entity.getBankParkingAccountNumber());
		dto.setId(entity.getId());
		dto.setStatus(entity.getStatus());
		
		return dto;
	}

}
