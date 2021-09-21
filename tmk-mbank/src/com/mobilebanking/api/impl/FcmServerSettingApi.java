package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IFcmServerSettingApi;
import com.mobilebanking.converter.FcmServerSettingConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.FcmServerSetting;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.FcmServerSettingDTO;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.FcmServerSettingRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
public class FcmServerSettingApi implements IFcmServerSettingApi {

	@Autowired
	private FcmServerSettingConverter fcmServerSettingConverter;

	@Autowired
	private FcmServerSettingRepository fcmServerSettingRepository;

	@Autowired
	private BankRepository bankRepository;

	@Override
	public void saveServerSetting(FcmServerSettingDTO fcmServerSettingDTO) {
		FcmServerSetting fcmServerSetting = new FcmServerSetting();
		fcmServerSetting.setFcmServerKey(fcmServerSettingDTO.getServerKey());
		fcmServerSetting.setFcmServerID(fcmServerSettingDTO.getServerId());
		fcmServerSettingRepository.save(fcmServerSetting);
	}

	@Override
	public List<FcmServerSettingDTO> listFcmServerSetting() {
		List<FcmServerSetting> fcmServerList = fcmServerSettingRepository.findAll();
		return fcmServerSettingConverter.convertToDtoList(fcmServerList);
	}

	@Override
	public FcmServerSettingDTO getByServerKey(String serverKey) {
		return fcmServerSettingConverter.convertToDto(fcmServerSettingRepository.findByFcmServerKey(serverKey));
	}

	@Override
	public List<BankDTO> getNotAddedBank(String serverKey) {
		List<Bank> bankList = bankRepository.findBankNotInFcmSetting(serverKey);
		return ConvertUtil.ConvertBankDTOtoBank(bankList);
	}

	@Override
	public void addBankToFcmServer(String serverKey, String[] bankSwiftCodeList) {
		FcmServerSetting fcmServerSetting = fcmServerSettingRepository.findByFcmServerKey(serverKey);
		for (String swiftCode : bankSwiftCodeList) {
			Bank bank = bankRepository.findBankByCode(swiftCode);
			if(fcmServerSetting.getBankList()==null){
				List<Bank> bankList= new  ArrayList<>();
				bankList.add(bank);
				fcmServerSetting.setBankList(bankList);
			}else
			fcmServerSetting.getBankList().add(bank);
		}
		fcmServerSettingRepository.save(fcmServerSetting);
	}

	@Override
	public void removeBankFromFcmServer(String swiftCode, String serverKey) {
		Bank bank = bankRepository.findBankByCode(swiftCode);
		FcmServerSetting fcmServerSetting = fcmServerSettingRepository.findByFcmServerKey(serverKey);
		fcmServerSetting.getBankList().remove(bank);
		fcmServerSettingRepository.save(fcmServerSetting);
	}

	@Override
	public Object getAddedBank(String serverKey) {
		List<Bank> bankList = bankRepository.findBankInFcmSetting(serverKey);
		return ConvertUtil.ConvertBankDTOtoBank(bankList);
	}

}
