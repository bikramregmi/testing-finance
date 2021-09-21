package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IGeneralSettingsApi;
import com.mobilebanking.api.ISmsPackageApi;
import com.mobilebanking.converter.SmsPackageConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.SmsPackages;
import com.mobilebanking.model.SmsPackageDTO;
import com.mobilebanking.repositories.SmsPackageRepository;
import com.mobilebanking.util.StringConstants;

@Service
public class SmsPackageApi implements ISmsPackageApi {

	@Autowired
	private SmsPackageConverter smsPackageConverter;
	
	@Autowired
	private SmsPackageRepository smsPackageRepository;
	
	@Autowired 
	private IGeneralSettingsApi generalSettingApi;
	
	@Override
	public List<SmsPackageDTO> getAllSmsPackage() {
		
		List<SmsPackages> smsPackageList = smsPackageRepository.findAll();
		
		return smsPackageConverter.convertToDtoList(smsPackageList);
		
	}

	@Override
	public SmsPackages saveSmsPackage(Bank bank) {
		
		SmsPackages smsPackages = new SmsPackages();
		
		smsPackages.setBank(bank);
		smsPackages.setGeneralSetting(generalSettingApi.get(StringConstants.SPARROW_SMS_CREDIT));
		smsPackages = smsPackageRepository.save(smsPackages);
		return smsPackages;
		
	}

	
	
	
}
