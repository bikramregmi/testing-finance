package com.mobilebanking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.INotificationGroupApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.NotificationGroupDTO;
import com.mobilebanking.model.error.NotificationGroupError;

@Component
public class NotificationGroupValidation {

	@Autowired
	private INotificationGroupApi notificationGroupApi;

	@Autowired
	private IBankApi bankApi;

	public NotificationGroupError validateNotificationGroup(NotificationGroupDTO notificationGroup) {

		NotificationGroupError error = new NotificationGroupError();

		boolean valid = true;
		String errorMessage = null;
		BankDTO bank = bankApi.getBankDtoById(notificationGroup.getBankId());
		errorMessage = checkName(notificationGroup.getName(), bank.getSwiftCode());
		if (errorMessage != null) {
			valid = false;
			error.setName(errorMessage);
		}

		error.setValid(valid);
		return error;
	}

	private String checkName(String name, String bankCode) {
		if (name == null) {
			return "Invalid Name";
		} else if (name.trim().equals("")) {
			return "Invalid Name";
		} else if (name.contains(" ")) {
			return "Notification Group must not contain space";
		} else if (name.contains("/")) {
			return "Notification Group must not contain '/'";
		} else {
			NotificationGroupDTO notificationGroupDTO = notificationGroupApi.getByName(name, bankCode);
			if (notificationGroupDTO != null) {
				return "Notification Group with the name already exists";
			}
		}
		return null;
	}

}
