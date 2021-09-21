package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mobilebanking.api.ICountryApi;
import com.mobilebanking.model.CountryDTO;
import com.mobilebanking.model.error.CountryError;

public class CountryValidation {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private ICountryApi countryApi;
	
	/*public CountryValidation(ICountryApi countryApi) {
		this.countryApi = countryApi;
	}
	*/
	
	public CountryError countryValidation(CountryDTO countryDTO){
		
		CountryError countryError =new CountryError();
		boolean valid=true;
		
	/*	if(countryDTO.getName()==null){
			logger.debug("at getName");
			countryError.setName("Name required");
		    valid=false;
		}
		Country c=countryRepository.findByCountry(countryDTO.getName());
		if (c != null) {
			countryError.setName("Country Already Exists");
			valid = false;
		}
		if(countryDTO.getIsoTwo()==null){
			logger.debug("at iso two");
			countryError.setIsoTwo("Iso two required");
			valid=false;
		}
		Country i=countryRepository.findByCountry(countryDTO.getIsoTwo());
		if (i != null) {
			countryError.setIsoTwo("IsoTwo Already Exists");
			valid = false;
		}
		if(countryDTO.getIsoThree()==null){
			logger.debug("at iso three");
			countryError.setIsoThree("Iso three required");
			valid=false;
		}
		Country t=countryRepository.findByCountry(countryDTO.getIsoThree());
		if (t != null) {
			countryError.setIsoThree("IsoThree Already Exists");
			valid = false;
		}
		if(countryDTO.getDialCode()==null){
			logger.debug("error at dial code");		
			countryError.setDialCode("Dial code Required");
			valid=false;
		}
		if(countryDTO.getCurrencyCode()==null){
			logger.debug("error at currency");
			countryError.setCurrency("Currency Code Required");
			valid=false;
		}*/
		countryError.setValid(valid);
			return countryError;
	}

}
