package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.FcmServerSetting;
import com.mobilebanking.model.FcmServerSettingDTO;

@Component
public class FcmServerSettingConverter implements IConverter<FcmServerSetting, FcmServerSettingDTO>, IListConverter<FcmServerSetting, FcmServerSettingDTO> {

	@Override
	public List<FcmServerSettingDTO> convertToDtoList(List<FcmServerSetting> entityList) {
		List<FcmServerSettingDTO> fcmSettingDtoList = new ArrayList<>();
		
		for(FcmServerSetting serverSetting : entityList){
			fcmSettingDtoList.add(convertToDto(serverSetting));
		}
		return fcmSettingDtoList;
	}

	@Override
	public FcmServerSetting convertToEntity(FcmServerSettingDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FcmServerSettingDTO convertToDto(FcmServerSetting entity) {
		FcmServerSettingDTO fcmServerSettingDto = new FcmServerSettingDTO();
		fcmServerSettingDto.setId(entity.getId());
		fcmServerSettingDto.setServerId(entity.getFcmServerID());
		fcmServerSettingDto.setServerKey(entity.getFcmServerKey());
		if(entity.getBankList()!=null){
		List<String> bankNameList = new ArrayList<>();
		
		for(Bank bank : entity.getBankList()){
			bankNameList.add(bank.getName());
		}
		fcmServerSettingDto.setBankName(bankNameList);
		}
//		if(entity.getBank()!=null)
//		fcmServerSettingDto.setBankName(entity.getBank().getName());
		return fcmServerSettingDto;
	}

}
