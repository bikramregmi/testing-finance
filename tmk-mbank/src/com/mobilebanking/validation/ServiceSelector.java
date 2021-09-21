package com.mobilebanking.validation;

import java.util.ArrayList;
import java.util.List;

import com.mobilebanking.model.ServicesList;

public class ServiceSelector {

	public static List<ServicesList> topUps() {

		ServicesList[] userService = ServicesList.values();

		List<ServicesList> allTypes = new ArrayList<ServicesList>();
		for (int i = 0; i < userService.length; i++) {
			if (userService[i] == ServicesList.NTC_ADSL_LIMITED || userService[i] == ServicesList.NTC_ADSL_UNLIMITED
					|| userService[i] == ServicesList.NTC_CDMA_POSTPAID
					|| userService[i] == ServicesList.NTC_CDMA_PREPAID || userService[i] == ServicesList.NTC_POSTPAID
					|| userService[i] == ServicesList.NTC_PREPAID) {
				allTypes.add(userService[i]);
			}

		}

		return allTypes;
	}

}
