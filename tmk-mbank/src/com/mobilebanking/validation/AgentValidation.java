package com.mobilebanking.validation;
/*package com.wallet.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.wallet.api.IAgentApi;
import com.wallet.model.AgentDTO;
import com.wallet.model.error.AgentError;

public class AgentValidation {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private  IAgentApi agentApi;
	
	public AgentValidation(){}
	
	public AgentValidation(IAgentApi agentApi) {
		this.agentApi = agentApi;
	}
	
	
	public AgentError agentValidation(AgentDTO agentDTO) {
		
		AgentError agentError=new AgentError();
		boolean valid=true;
		
		if(agentDTO.getAddress()==null){
			logger.debug("agent address null");
			agentError.setAddress("Address Required");
			valid=false;
		}
		if(agentDTO.getAgencyName()==null){
			logger.debug("Agency Name null");
			agentError.setAgencyName("Agency Name is Required");
			valid=false;
		}
		if(agentDTO.getAgentCode()==null){
			logger.debug("Agent Code null");
			agentError.setAgentCode("Agent Code is Required");
			valid=false;
		}
		
		if(agentDTO.getLandline()==null){
			logger.debug("agent Land Line null");
			agentError.setLandline("Land Line Required");
			valid=false;
		}
		  if (agentDTO.getLandline() != null) {
			  try {
				    Long.parseLong(agentDTO.getLandline());
				   } catch (Exception e) {
					   logger.debug("Invalid Landline Number==>");
				    agentError.setLandline("Invalid Landline Number");
				    valid = false;
				   }
			  } 
		
		 if (agentDTO.getMobileNo() == null) {
			  logger.debug("Mobile Number required==>");
		   agentError.setMobileNo("Mobile Number required");
		   valid = false;
		  } else {
		   if (agentDTO.getMobileNo().length() < 10
		     || agentDTO.getMobileNo().length() > 10) {
			   logger.debug("Mobile number must be of 10-digit==>");
		    agentError.setMobileNo("Mobile number must be of 10-digit");
		    valid = false;
		   }
		   try {
		    Long.parseLong(agentDTO.getMobileNo());
		   } catch (Exception e) {
			   logger.debug("Invalid Mobile Number==>"+e);
		    agentError.setMobileNo("Invalid Mobile Number");
		    valid = false;
		   }
		  }
		
		agentError.setValid(valid);
		return agentError;
	}

}
*/