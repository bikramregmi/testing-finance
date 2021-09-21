package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mobilebanking.model.ServicesDTO;
import com.mobilebanking.model.error.ServiceError;

@Service
public class ServiceValidation {
	
	private Logger logger = LoggerFactory.getLogger(ServiceValidation.class);
	
	public ServiceError validateService(ServicesDTO serviceDto){
		
		ServiceError error = new ServiceError();
		boolean valid = true;
		
		if(serviceDto.getName()!=null){
			if(serviceDto.getName().trim().equals("")){
				logger.debug("Empty Service Name");
				error.setName("Name should not be empty");
				valid = false;
			}else if(serviceDto.getName().length()<5){
				logger.debug("Too short Service name");
				error.setName("Service Name should not be less than 5");
			}
		}else{
			logger.debug("Service Name is required");
			error.setName("Service Name is required");
			valid = false;
		}
		
		/*if(serviceDto.getMerchant()==null){
			logger.debug("Mechant is null");
			error.setMerchant("Merchant should not be empty");
			valid = false;
		}
		*/
		error.setValid(valid);
		return error;
	}

}
