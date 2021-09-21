/*package com.wallet.api.impl;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IEpayApi;
import com.wallet.entity.Agent;
import com.wallet.entity.Customer;
import com.wallet.entity.Merchant;
import com.wallet.entity.Services;
import com.wallet.entity.User;
import com.wallet.model.UserType;
import com.wallet.repositories.ServicesRepository;
import com.wallet.serviceprovider.ntc.WebServiceException_Exception;
import com.wallet.util.ServiceDetector;

@Service
public class EpayApi implements IEpayApi {

	@Autowired
	private ServicesRepository servicesRepository;
	
	@Autowired
	private ServiceDetector serviceDetector;
	@Override
	public boolean getStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getBalance(String serviceI) {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		Services service = servicesRepository.getServiceByIdentifier(serviceI);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		Map<String, String> hashResponse = new HashMap<String, String>();
		try {
			hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(), hashRequest);
		} catch (WebServiceException_Exception | UnknownHostException e) {
			e.printStackTrace();
		}
		if(hashResponse.get("epay").equals("test")){
			//dishHome.setResponseDetail(hashResponse);
			return true;
		}
		return false;
	}

	@Override
	public boolean onlinePayment(String serviceI, double amount, long aid) {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		boolean valid = true;
		Services service = servicesRepository.getServiceByIdentifier(serviceI);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		hashRequest.put("amount", "" +amount);
		Map<String, String> hashResponse = new HashMap<String, String>();
		try {
			hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(), hashRequest);
		} catch (WebServiceException_Exception | UnknownHostException e) {
			e.printStackTrace();
		}
		if(hashResponse.get("epay").equals("test")){
			//dishHome.setResponseDetail(hashResponse);
			valid = true;
		}else
		{
			valid = false;
		}
			return valid;
			
	}

	@Override
	public boolean addPayment() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPayment(String serviceI, double amount, long aid) {
		// TODO Auto-generated method stub
		return false;
	}

}
*/