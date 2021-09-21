/*package com.wallet.api.impl;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IUnitedSolutionsApi;
import com.wallet.entity.DishHome;
import com.wallet.entity.Services;
import com.wallet.repositories.ServicesRepository;
import com.wallet.serviceprovider.ntc.WebServiceException_Exception;
import com.wallet.util.ConvertUtil;
import com.wallet.util.ServiceDetector;

@Service
public class UnitedSolutionsApi implements IUnitedSolutionsApi {

	@Autowired 
	private ServicesRepository servicesRepository;
	
	@Autowired
	private ServiceDetector serviceDetector;

	@Override
	public boolean checkBalance(String serviceId) throws UnknownHostException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		Services service = servicesRepository.getServiceByIdentifier(serviceId);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		DishHome dishHome = new DishHome();
		dishHome.setRequestDetail(hashRequest);
		//dishHome = dishHomeRepository.save(dishHome);
		Map<String, String> hashResponse = new HashMap<String, String>();
		try {
			hashResponse = serviceDetector.detectService(service.getUniqueIdentifier(), hashRequest);
		} catch (WebServiceException_Exception e) {
			e.printStackTrace();
		}
		
		if(hashResponse==null){
			return false;
		}else
			if(hashResponse.get("test").equals("test")){
				dishHome.setResponseDetail(hashResponse);
				return true;
			}
		
			else{
				return false;
			}
	}

	@Override
	public boolean flightAvailability() {
		serviceDetector.unitedSolutions();
		return false;
	}

}
*/