/**
 * 
 */
package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.BankMerchantAccounts;
import com.mobilebanking.model.BankMerchantAccountsDto;

/**
 * @author bibek
 *
 */
@Component
public class BankMerchantAccountsConverter implements IConverter<BankMerchantAccounts, BankMerchantAccountsDto>,
		IListConverter<BankMerchantAccounts, BankMerchantAccountsDto> {

	@Override
	public List<BankMerchantAccountsDto> convertToDtoList(List<BankMerchantAccounts> entityList) {
		List<BankMerchantAccountsDto> dtoList = new ArrayList<>();
		for(BankMerchantAccounts account : entityList){
			dtoList.add(convertToDto(account));
		}
		return dtoList;
	}

	@Override
	public BankMerchantAccounts convertToEntity(BankMerchantAccountsDto dto) {
		return null;
	}

	@Override
	public BankMerchantAccountsDto convertToDto(BankMerchantAccounts entity) {
		BankMerchantAccountsDto dto = new BankMerchantAccountsDto();
		dto.setBank(entity.getBank().getName());
		dto.setId(entity.getId());
		dto.setMerchant(entity.getMerchant().getName());
		dto.setAccountNumber(entity.getAccountNumber());
		dto.setStatus(entity.getStatus());
		dto.setMerchantId(entity.getMerchant().getId());
		return dto;
	}

}
