package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.Country;
import com.mobilebanking.model.CountryDTO;

public interface ICountryApi {

	void saveCountry(CountryDTO countryDTO);

	List<CountryDTO> getAllCountry();

	Country getCountryById(long countryId);
	
	List<CountryDTO> findCountry();

	List<CountryDTO> findAllCountryExcept(List<Country> countryList);
	
	
	CountryDTO findCountryById(long countryId); 
	
	List<CountryDTO> findCountryListFromNameList(List<String> countryNameList);
	
	List<String> findCountryListExceptNameList(List<String> countryNameList);
	 
	void editCountry(CountryDTO countryDto);
	
	void deleteCountry(long countryId);
	
	List<CountryDTO> findAllCountryList();
	
}
