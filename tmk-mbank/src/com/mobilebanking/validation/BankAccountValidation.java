package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobilebanking.model.BankAccountDTO;
import com.mobilebanking.model.error.BankAccountError;

public class BankAccountValidation {
	
private Logger logger=LoggerFactory.getLogger(BankAccountValidation.class );

/*	public BankAccountValidation(IBankAccountApi bankAccountApi){
		this.bankAccountApi=bankAccountApi;
	}
*/	public BankAccountError bankAccountValidation(BankAccountDTO bankAccountDTO){	
	BankAccountError bankAccountError =new BankAccountError();
		boolean valid=true;
		
		bankAccountError.setValid(valid);
		return bankAccountError;
	}


}
