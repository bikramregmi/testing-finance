package com.mobilebanking.validation;

import org.springframework.stereotype.Component;

import com.mobilebanking.model.FcmServerSettingDTO;
import com.mobilebanking.model.error.FcmServerSettingError;

@Component
public class FcmServerSettingValidation {

	
	public FcmServerSettingError validateServerSetting(FcmServerSettingDTO fcmServerSetting) {

		FcmServerSettingError error = new FcmServerSettingError();

		boolean valid = true;
		String errorMessage = null;
		errorMessage = checkKey(fcmServerSetting.getServerKey());
		if (errorMessage != null) {
			valid = false;
			error.setServerKey(errorMessage);
		}
		
		errorMessage = checkId(fcmServerSetting.getServerId());
		if (errorMessage != null) {
			valid = false;
			error.setServerId(errorMessage);
		}
		error.setValid(valid);
		return error;
	}

	private String checkId(String serverId) {
		if (serverId == null) {
			return "Invalid Key";
		} else if (serverId.trim().equals("")) {
			return "Invalid key";
		}
		return null;
	}

	private String checkKey(String serverKey) {
		if (serverKey == null) {
			return "Invalid Key";
		} else if (serverKey.trim().equals("")) {
			return "Invalid key";
		} else if (serverKey.contains(" ")) {
			return "Key must not contain space";
		} else if (serverKey.contains("/")) {
			return "Key must not contain '/'";
		}
		return null;
	}



	
}
