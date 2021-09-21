package com.mobilebanking.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.mobilebanking.entity.WebService;
import com.mobilebanking.model.WebServiceDTO;
import com.mobilebanking.model.error.WebServiceError;
import com.mobilebanking.repositories.WebServiceRepository;

public class WebServiceValidation {
	
	@Autowired
	private WebServiceRepository webServiceRepository;
	
	/*public WebServiceValidation(WebServiceRepository webServiceRepository){
		this.webServiceRepository=webServiceRepository;
	}
	*/
	public WebServiceError validateUser(WebServiceDTO dto){
		WebServiceError error = new WebServiceError();
		boolean valid = true;
		if (dto.getApi_user() == null || dto.getApi_user().trim().equals("")) {
			error.setApi_user("Required");
			valid = false;
		}
		else{
			WebService user=webServiceRepository.findByName(dto.getApi_user());
			if(user!=null){
				error.setApi_user("Duplicate");
				valid = false;
			}
		}

		if (dto.getApi_password() == null || dto.getApi_password().trim().equals("")) {
			error.setApi_password("Required");
			valid = false;
		}

		error.setValid(valid);
		return error;
	}

}
