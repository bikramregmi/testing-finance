package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mobilebanking.api.ICityApi;
import com.mobilebanking.controller.CityController;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.error.CityError;

public class CityValidation {
	private Logger logger=LoggerFactory.getLogger(CityController.class );
	
	@Autowired
	private ICityApi cityApi;
	/*public CityValidation(ICityApi cityApi){
		this.cityApi=cityApi;
	}*/
public CityError cityValidation(CityDTO cityDTO){
		
	CityError cityError =new CityError();
		boolean valid=true;
		
		if(cityDTO.getName()==null){
			logger.debug("Name null");
			cityError.setName("Invalid Name");
			valid=false;
		}else if(cityDTO.getName().trim().equals("")){
			logger.debug("Name null");
			cityError.setName("Invalid Name");
			valid=false;
		}
		if(cityDTO.getState()==null){
			logger.debug("state null");
			cityError.setState("State Required");
			valid=false;
		}
		
		cityError.setValid(valid);
		return cityError;
	}

public CityError cityEditValidation(CityDTO cityDTO){
	
	CityError cityError =new CityError();
		boolean valid=true;
		
		if(cityDTO.getName()==null){
			logger.debug("Name null");
			cityError.setName("Invalid Name");
			valid=false;
		}else if(cityDTO.getName().trim().equals("")){
			logger.debug("Name null");
			cityError.setName("Invalid Name");
			valid=false;
		}
		if(cityDTO.getStatus()==null){
			logger.debug("status null");
			cityError.setStatus("Status Required");
			valid=false;
		}
		
		cityError.setValid(valid);
		return cityError;
	}

}
