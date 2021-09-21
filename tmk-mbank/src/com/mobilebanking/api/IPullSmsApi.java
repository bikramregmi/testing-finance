package com.mobilebanking.api;

import com.mobilebanking.model.PullSmsDTO;

public interface IPullSmsApi {

	void savePullSms(PullSmsDTO pullsms);
	
}
