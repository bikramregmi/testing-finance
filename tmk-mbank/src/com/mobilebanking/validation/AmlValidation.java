package com.mobilebanking.validation;
/*package com.wallet.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IAmlApi;
import com.wallet.controller.AmlController;
import com.wallet.model.AmlDTO;
import com.wallet.model.error.AmlError;


public class AmlValidation {
	
	private Logger logger=LoggerFactory.getLogger(AmlController.class );
	
	@Autowired
	private IAmlApi amlApi;
	
	public AmlError amlValidation(AmlDTO amlDTO){	
	AmlError amlError =new AmlError();
		boolean valid=true;
		
		if(amlDTO.getName()==null){
			logger.debug("Name null");
			amlError.setName("Name Required");
			valid=false;
		}
		if(amlDTO.getUploadedBy()==null){
			logger.debug("Uploaded Name null");
			amlError.setUploadedBy("Uploaded name Required");
			valid=false;
		}
		if(amlDTO.getSource()==null){
			logger.debug("Source null");
			amlError.setSource("Source Required");
			valid=false;
		}
		if(amlDTO.getAlias()==null){
			logger.debug("Alias null");
			amlError.setAlias("Alias Required");
			valid=false;
		}
		if(amlDTO.getAddress()==null){
			logger.debug("Address null");
			amlError.setAddress("Address Required");
			valid=false;
		}
		if(amlDTO.getDocuments()==null){
			logger.debug("Documents null");
			amlError.setDocuments("Documents Required");
			valid=false;
		}
		
		
		
		amlError.setValid(valid);
		return amlError;
	}

}
*/