package com.mobilebanking.validation;
/*package com.wallet.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

importom.wallet.api.ISuperAgentApi;
import com.wallet.model.SuperAgentDTO;
import com.wallet.model.error.SuperAgentError;

@Service
public class SuperAgentValidation {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private  ISuperAgentApi superAgentApi;
	
	public SuperAgentValidation(ISuperAgentApi superAgentApi){
		this.superAgentApi = superAgentApi;
	}

	public SuperAgentError superAgentValidation(SuperAgentDTO superAgentDTO) {
		
		SuperAgentError error=new SuperAgentError();
		boolean valid=true;

		if(superAgentDTO.getAgencyName()==null){
			logger.debug("Agency Name is null");
			error.setAgencyName("Agency Name is required.");
			valid=false;
		}
		if(superAgentDTO.getAgentCode()==null){
			logger.debug("Agent Code is null");
			error.setAgentCode("Agent Code is required.");
			valid=false;
		}
		if(superAgentDTO.getLandline()==null){
			logger.debug("Agent's Land Line is null");
			error.setLandline("Land Line is required");
			valid=false;
		}
		  if (superAgentDTO.getLandline() != null) {
			  try {
				    Long.parseLong(superAgentDTO.getLandline());
				   } catch (Exception e) {
					   logger.debug("Invalid Landline Number==>");
				    error.setLandline("Invalid Landline Number");
				    valid = false;
				   }
			  } 
		
		if (superAgentDTO.getMobileNo() == null) {
			  logger.debug("Mobile Number required==>");
		   error.setMobileNo("Mobile Number required");
		   valid = false;
		  } else {
		   if (superAgentDTO.getMobileNo().length() < 10
		     || superAgentDTO.getMobileNo().length() > 10) {
			   logger.debug("Mobile number must be of 10-digit==>");
		    error.setMobileNo("Mobile number must be of 10-digit");
		    valid = false;
		   }
		   try {
		    Long.parseLong(superAgentDTO.getMobileNo());
		   } catch (Exception e) {
			   logger.debug("Invalid Mobile Number==>"+e);
		    error.setMobileNo("Invalid Mobile Number");
		    valid = false;
		   }
		  }
		error.setValid(valid);
		return error;

	}

}
*/