package com.mobilebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.ICommissionApi;
import com.mobilebanking.model.BankCommissionDTO;
import com.mobilebanking.model.BankCommissionDtoList;
import com.mobilebanking.model.error.BankCommissionError;
import com.mobilebanking.validation.CommissionValidation;


@Controller
public class BankCommissionController {
	
	@Autowired
	private ICommissionApi commissionApi;

	@Autowired
	private CommissionValidation commissionValidation;
	
	@RequestMapping(method=RequestMethod.POST,value = "/bankCommissionAmount")
	public String saveForm(@ModelAttribute("commissionAmountForm") BankCommissionDtoList commissionAmountList , ModelMap model) {
	    
		BankCommissionError error = commissionValidation.bankCommissionValidatation(commissionAmountList);
		
		if(error.getValid()){
			for(BankCommissionDTO commissionAmount : commissionAmountList.getCommissionAmounts()) {
				Long id = commissionAmountList.getBankId();
				commissionAmount.setBankId(id);
				commissionApi.saveBankCommission(commissionAmount,commissionAmountList.getBankId());
		    }
		    return "redirect:listBank";
		}else{
			model.put("error", error);
	    return "redirect:listBank";
		}
	}
	
	
}
