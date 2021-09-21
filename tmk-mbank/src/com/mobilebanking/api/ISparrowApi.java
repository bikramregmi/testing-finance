package com.mobilebanking.api;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.Map;

import com.mobilebanking.model.SparrowSettingsDTO;

public interface ISparrowApi {
	
	public SparrowSettingsDTO updateSetting(SparrowSettingsDTO sparrowSettingsDTO);

	public SparrowSettingsDTO getSettings();
	
	public Map<String, String> sendSMS(String message,String mobileNo) throws UnknownHostException, InvocationTargetException;

	public Map<String, String> getCredits();

}
