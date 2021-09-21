package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mobilebanking.api.IComplianceApi;
import com.mobilebanking.controller.ComplianceController;
import com.mobilebanking.model.ComplianceDTO;
import com.mobilebanking.model.error.ComplianceError;

public class ComplianceValidation {
	
private Logger logger=LoggerFactory.getLogger(ComplianceController.class );
	
	@Autowired
	private IComplianceApi complianceApi;
	/*public ComplianceValidation(IComplianceApi complianceApi){
		this.complianceApi=complianceApi;
	}*/
	public ComplianceError complianceValidation(ComplianceDTO complianceDTO){	
	ComplianceError complianceError =new ComplianceError();
		boolean valid=true;
		
		
		if(complianceDTO.getCountry()==null){
			logger.debug("Compliance Country null");
			complianceError.setCountry("Country Required");
			valid=false;
		}
		
		if(complianceDTO.getRequirements()==null){
			logger.debug("Requirements null");
			complianceError.setCountry("Requirements Required");
			valid=false;
		}
		
		complianceError.setValid(valid);
		return complianceError;
	}

}
