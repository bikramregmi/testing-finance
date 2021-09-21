package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mobilebanking.api.IStateApi;
import com.mobilebanking.controller.StateController;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.error.StateError;

public class StateValidation {
	
	private Logger logger=LoggerFactory.getLogger(StateController.class );
	
	@Autowired
	private IStateApi stateApi;
	
	/*public StateValidation(IStateApi stateApi){
		this.stateApi=stateApi;
	}*/
	public StateError stateValidation(StateDTO stateDTO){	
	StateError stateError =new StateError();
		boolean valid=true;
		
		if(stateDTO.getName()==null){
			logger.debug("Name null");
			stateError.setName("Name Required");
			valid=false;
		}
		if(stateDTO.getCountry()==null){
			logger.debug("agent address null");
			stateError.setCountry("Address Required");
			valid=false;
		}
		
		/*if(stateDTO.getStatus()==null){
			logger.debug("agent address null");
			stateError.setCountry("Address Required");
			valid=false;
		}
*/		
		stateError.setValid(valid);
		return stateError;
	}
	
	public StateError stateEditValidation(StateDTO stateDTO){	
		StateError stateError =new StateError();
			boolean valid=true;
			
			if(stateDTO.getName()==null){
				logger.debug("Name null");
				stateError.setName("Name Required");
				valid=false;
			}
			if(stateDTO.getStatus()==null){
				logger.debug("Status null");
				stateError.setStatus("Status Required");
				valid=false;
			}
			stateError.setValid(valid);
			return stateError;
		}
}
