package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICityApi;
import com.mobilebanking.model.FinancialInstitutionDTO;
import com.mobilebanking.model.error.FinancialInstitutionError;

@Service
public class FinancialInstitutionValidation {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ICityApi cityApi;
	
	public FinancialInstitutionError validateFinance(FinancialInstitutionDTO instituteDTO){
		
		boolean valid = true;
		FinancialInstitutionError error = new FinancialInstitutionError();
		
		if(instituteDTO.getName()!=null){
			if(instituteDTO.getName().trim().equals("")){
				logger.debug("Institute Name is required");
				error.setName("Institute Name is required");
				valid = false;
			}
			else if(instituteDTO.getName().length()<5){
				logger.debug("Institute name should be greater than or equal to 5");
				error.setName("Institute name is less than 5");
				valid = false;
			}
		}else{
			logger.debug("Institute Name is required");
			error.setName("Institute name is required");
			valid = false;
		}
		
		
		if(instituteDTO.getAddress()!=null){
			if(instituteDTO.getAddress().trim().equals("")){
				logger.debug("Institute address is required");
				error.setAddress("Institute Address is required");
				valid = false;
			}
		}else{
			logger.debug("Institute address is required");
			error.setAccount("Institute Address is required");
			valid = false;
		}
		
		if(instituteDTO.getCity()!=null){
			if(instituteDTO.getCity().trim().equals("")){
				logger.debug("City name is required");
				error.setCity("City name is required");
				valid = false;
			}else if(cityApi.findByCity(instituteDTO.getCity())==null){
				logger.debug("Invalid city Name");
				error.setCity("Invalid city name");
				valid = false;
			}
		}else{
			logger.debug("City name is required");
			error.setCity("City name is required");
		}
		
		if(instituteDTO.getInstituteType()!=null){
			if(instituteDTO.getInstituteType().trim().equals("")){
				logger.debug("Institute type is required");
				error.setInstituteType("Institute type is required");
				valid = false;
			}
		}else{
			logger.debug("Institute type is required");
			error.setInstituteType("Institute type is required");
			valid = false;
		}
		
		if(instituteDTO.getSwiftCode()!=null){
			if(instituteDTO.getSwiftCode().trim().equals("")){
				logger.debug("Swift code is required");
				error.setSwiftCode("Swift code is required");
				valid = false;
			}
		}else{
			logger.debug("Swift code is required");
			error.setSwiftCode("Swift code is required");
			valid = false;
		}
		
		error.setValid(valid);
		
		return error;
		
	}

}
