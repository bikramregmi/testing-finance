package com.mobilebanking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.model.ShortCodeDTO;

@Component	
public class ShortCodeValidation {
	
	@Autowired
	private ICustomerApi customerApi;

	public boolean validateMobile(ShortCodeDTO shortCodeDto,String clientId){
		//TODO send client id from here
		boolean customer = customerApi.getCustomerByMobileNo(shortCodeDto.getFrom(),clientId);
		
		return customer;
		
	}
	
}
