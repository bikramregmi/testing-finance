package com.mobilebanking.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.converter.SparrowSettingsConverter;
import com.mobilebanking.entity.SparrowSettings;
import com.mobilebanking.model.SparrowSettingsDTO;
import com.mobilebanking.repositories.SparrowSettingsRepository;
import com.wallet.sms.service.SendSparrowSmsApi;

@Service
public class SparrowApi implements ISparrowApi {

	@Autowired
	private SparrowSettingsRepository sparrowSettingsRepository;

	@Autowired
	private SparrowSettingsConverter sparrowSettingsConverter;

	@Autowired
	private SendSparrowSmsApi sparrowSms;

	@Override
	public SparrowSettingsDTO updateSetting(SparrowSettingsDTO sparrowSettingsDTO) {
		SparrowSettings sparrowSetting = sparrowSettingsRepository.findSettings();
		if (sparrowSetting == null) {
			sparrowSetting = new SparrowSettings();
		}
		sparrowSetting.setUrl(sparrowSettingsDTO.getUrl());
		sparrowSetting.setIdentity(sparrowSettingsDTO.getFrom());
		sparrowSetting.setToken(sparrowSettingsDTO.getToken());
		sparrowSetting.setShortCode(sparrowSettingsDTO.getShortCode());
		sparrowSetting.setLastModified(new Date());
		sparrowSetting = sparrowSettingsRepository.save(sparrowSetting);
		return sparrowSettingsConverter.convertToDto(sparrowSetting);
	}

	@Override
	public SparrowSettingsDTO getSettings() {
		SparrowSettings sparrowSetting = sparrowSettingsRepository.findSettings();
		if (sparrowSetting != null)
			return sparrowSettingsConverter.convertToDto(sparrowSetting);
		return null;
	}

	@Override
	public Map<String, String> sendSMS(String message, String mobileNo)
			throws UnknownHostException, InvocationTargetException {
		SparrowSettings sparrowSetting = sparrowSettingsRepository.findSettings();
		HashMap<String, String> sparrowDetail = new HashMap<>();
		sparrowDetail.put("url", sparrowSetting.getUrl());
		sparrowDetail.put("from", sparrowSetting.getIdentity());
		sparrowDetail.put("token", sparrowSetting.getToken());

		return sparrowSms.sendSms(message, mobileNo, sparrowDetail);
	}

	@Override
	public Map<String, String> getCredits() {
		SparrowSettings sparrowSetting = sparrowSettingsRepository.findSettings();
		HashMap<String, String> sparrowDetail = new HashMap<>();
		sparrowDetail.put("url", sparrowSetting.getUrl());
		sparrowDetail.put("token", sparrowSetting.getToken());
		return sparrowSms.getCredits(sparrowDetail);
	}

}
