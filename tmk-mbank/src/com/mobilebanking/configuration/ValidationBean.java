package com.mobilebanking.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mobilebanking.validation.AmlUploadValidation;
import com.mobilebanking.validation.BankAccountValidation;
import com.mobilebanking.validation.BankValidation;
import com.mobilebanking.validation.CityValidation;
import com.mobilebanking.validation.ComplianceValidation;
import com.mobilebanking.validation.CountryValidation;
import com.mobilebanking.validation.CustomerValidation;
import com.mobilebanking.validation.MenuTemplateValidation;
import com.mobilebanking.validation.StateValidation;
import com.mobilebanking.validation.TransactionValidation;
import com.mobilebanking.validation.UserValidation;
import com.mobilebanking.validation.WebServiceValidation;

@Configuration
public class ValidationBean {
	
	@Bean(name="amluploadValidation")
	public AmlUploadValidation amlUploadValidation(){
		return new AmlUploadValidation();
	}
	
	@Bean(name="bankAccountValidation")
	public BankAccountValidation bankAccountValidation(){
		return new BankAccountValidation();
	}
	
	@Bean(name="bankValidation")
	public BankValidation bankValidation(){
		return new BankValidation();
	}
	
	@Bean(name="cityValidation")
	public CityValidation cityValidation(){
		return new CityValidation();
	}
		
	@Bean(name="complianceValidation")
	public ComplianceValidation complianceValidation(){
		return new ComplianceValidation();
	}			

	@Bean(name="countryValidation")
	public CountryValidation countryValidation(){
		return new CountryValidation();
	}
	
	@Bean(name="customerValidation")
	public CustomerValidation customerValidation(){
		return new CustomerValidation();
	}
	
	@Bean(name="menuTemplateValidation")
	public MenuTemplateValidation menuTemplateValidation(){
		return new MenuTemplateValidation();
	}
	
	@Bean(name="stateValidation")
	public StateValidation stateValidation(){
		return new StateValidation();
	}
	
	@Bean(name="transactionValidation")
	public TransactionValidation transactionValidation(){
		return new TransactionValidation();
	}
		@Bean(name="userValidation")
	public UserValidation userValidation(){
		return new UserValidation();
	}
	
	@Bean(name="webServiceValidation")
	public WebServiceValidation webServiceValidation(){
		return new WebServiceValidation();
	}
	
}
