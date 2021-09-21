package com.wallet.serviceprovider.ncell.serviceimpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.wallet.serviceprovider.ncell.service.NcellTopUpService;

@Service
public class NcellTopUpServiceImpl implements NcellTopUpService {

	@Override
	public void SubscriberTopUp() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("requesttype", "TOPUP");
		HttpEntity<?> entity = new HttpEntity<>(headers);
	}

	@Override
	public void checkBalacnce() {
		// TODO Auto-generated method stub
		
	}

}
