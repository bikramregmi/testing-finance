package com.mobilebanking.validation;

import org.springframework.stereotype.Component;

import com.mobilebanking.model.BankCommissionDTO;
import com.mobilebanking.model.BankCommissionDtoList;
import com.mobilebanking.model.error.BankCommissionError;

@Component
public class CommissionValidation {

	boolean valid;
	public BankCommissionError bankCommissionValidatation(BankCommissionDtoList commissionAmountList){
		
		BankCommissionError error = new BankCommissionError();
		valid = true;
		for(BankCommissionDTO commissionAmount : commissionAmountList.getCommissionAmounts()) {
			
			
			if(commissionAmount.getCommissionFlat()==null){
				error.setErrorMessage("Please enter Commission Flat");
				valid = false;
			}
			if(commissionAmount.getCommissionPercentage()==null){
				error.setErrorMessage("Please enter Commission Percentage");
				valid = false;
			}
			
			if(commissionAmount.getFeePercentage()==null){
				error.setErrorMessage("Please enter Fee Percentage");
				valid = false;
			}
			
			if(commissionAmount.getFeeFlat()==null){
				error.setErrorMessage("Please enter Fee Flat");
				valid = false;
			}
			
			error.setValid(valid);
			
		}
		
		return error;
		
	}
	
}
