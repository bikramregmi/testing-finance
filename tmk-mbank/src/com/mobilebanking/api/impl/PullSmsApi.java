package com.mobilebanking.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IPullSmsApi;
import com.mobilebanking.entity.PullSms;
import com.mobilebanking.model.PullSmsDTO;
import com.mobilebanking.repositories.PullMessageRepository;
@Service
public class PullSmsApi implements IPullSmsApi {

	@Autowired
	private PullMessageRepository pullmsmRepository;
	@Override
	public void savePullSms(PullSmsDTO pullsms) {
		
		PullSms pullMessage = new PullSms();
		
		pullMessage.setSmsfrom(pullsms.getSmsfrom());
		pullMessage.setSmskeyword(pullsms.getSmskeyword());
		pullMessage.setSmstext(pullsms.getSmstext());
		pullMessage.setSmsto(pullsms.getSmsto());
		pullmsmRepository.save(pullMessage);
	}

}
