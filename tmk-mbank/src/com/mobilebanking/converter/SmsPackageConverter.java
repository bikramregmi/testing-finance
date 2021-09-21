package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.SmsPackages;
import com.mobilebanking.model.SmsPackageDTO;

@Component
public class SmsPackageConverter implements IConverter<SmsPackages, SmsPackageDTO>, IListConverter<SmsPackages, SmsPackageDTO> {

	@Override
	public List<SmsPackageDTO> convertToDtoList(List<SmsPackages> entityList) {

		List<SmsPackageDTO> smsList = new ArrayList<>();
		for(SmsPackages sms :entityList){
			smsList.add(convertToDto(sms));
		}
		return smsList;
	}

	@Override
	public SmsPackages convertToEntity(SmsPackageDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsPackageDTO convertToDto(SmsPackages entity) {
		SmsPackageDTO smsPackage = new SmsPackageDTO();
//		smsPackage.setBankBranchId(entity.getBankBranch().getId());
		smsPackage.setBankId(entity.getBank().getId());
		smsPackage.setGeneralSettingId(entity.getGeneralSetting().getId());
		smsPackage.setId(entity.getId());
		return smsPackage;
	}

}
