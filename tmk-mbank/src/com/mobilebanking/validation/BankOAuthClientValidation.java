/**
 * 
 */
package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.model.error.BankOAuthClientError;
import com.mobilebanking.repositories.BankOAuthClientRepository;

/**
 * @author user
 *
 */
@Component
public class BankOAuthClientValidation {
	
	private Logger logger=LoggerFactory.getLogger(BankOAuthClientValidation.class ); 
	
	@Autowired
	private BankOAuthClientRepository bankOAuthClientRepository;
	
	public BankOAuthClientError bankOAuthValidation(String redirectUrl, String bank) {
		BankOAuthClientError bankOAuthClientError = new BankOAuthClientError();
		boolean valid = true;
		if (redirectUrl.trim() == "") {
			bankOAuthClientError.setRedirectUrl("Redirect URL or Client Name cannot be Empty");
			valid = false;
		}
		
		if (bank.trim() != "" && ! bank.equals("0")) {
			String clientId = bankOAuthClientRepository.findOAuthClientForBank(Long.parseLong(bank));
			if (clientId != null) {
				valid = false;
				bankOAuthClientError.setBank("Duplicat Entry for the Bank");
			}
	
		}
		
		bankOAuthClientError.setValid(valid);
		return bankOAuthClientError;
	}

}
