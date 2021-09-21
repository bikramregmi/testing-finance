/**
 * 
 */
package com.mobilebanking.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IGeneralSettingsApi;
import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.entity.GeneralSettings;
import com.mobilebanking.repositories.GeneralSettingsRepository;
import com.mobilebanking.util.StringConstants;

/**
 * @author bibek
 *
 */
@Service
public class GeneralSettingsApi implements IGeneralSettingsApi {
	
	@Autowired
	private GeneralSettingsRepository settingsRepository; 
	
	@Autowired
	private ISparrowApi sparrowApi; 
	
	@Override
	public void save(String key, String value){
		GeneralSettings settings = settingsRepository.findSettingsByKey(key);
		if (settings == null) {
			settings = new GeneralSettings();
			settings.setSettingsKey(key);
			settings.setSettingsValue(value);
		} else {
			settings.setSettingsValue(value);	
		}
		
		settingsRepository.save(settings);
	} 

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.IOptionsApi#get(java.lang.String)
	 */
	@Override
	public GeneralSettings get(String key) {
		GeneralSettings options = settingsRepository.findSettingsByKey(key);
		return options;

	}

	@Override
//	@Scheduled(fixedRate=350000)
	public void smsCredit() {
		GeneralSettings settings = settingsRepository.findSettingsByKey(StringConstants.SPARROW_SMS_CREDIT);
		GeneralSettings settingsCredit = settingsRepository.findSettingsByKey(StringConstants.SPARROW_SMS_CREDIT_CONSUMED);
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap = sparrowApi.getCredits();
		if (settings == null) {
			settings = new GeneralSettings();
			settings.setSettingsKey(StringConstants.SPARROW_SMS_CREDIT);
			settings.setSettingsValue(responseMap.get("credits"));
			settingsRepository.save(settings);
			
		} else {
			settings.setSettingsValue(responseMap.get("credits"));
			settingsRepository.save(settings);
		}
		if (settingsCredit == null) {
			settingsCredit = new GeneralSettings();
			settingsCredit.setSettingsKey(StringConstants.SPARROW_SMS_CREDIT_CONSUMED);
			settingsCredit.setSettingsValue(responseMap.get("creditsConsumed"));
			settingsRepository.save(settingsCredit);
		} else {
			settingsCredit.setSettingsValue(responseMap.get("creditsConsumed"));
			settingsRepository.save(settingsCredit);
		}
		
		
	}

	@Override
	public void addCredit(String value) throws Exception {
		GeneralSettings settings = settingsRepository.findSettingsByKey(StringConstants.SPARROW_SMS_CREDIT);
		settings.setSettingsValue(String.valueOf((Double.parseDouble(settings.getSettingsValue())+Double.parseDouble(value))));
		settingsRepository.save(settings);
		
	}

}
