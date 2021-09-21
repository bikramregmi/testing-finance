package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICountryApi;
import com.mobilebanking.entity.Country;
import com.mobilebanking.model.CountryDTO;
import com.mobilebanking.repositories.CountryRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
public class CountryApi implements ICountryApi{
	
	@Autowired
	private CountryRepository countryRepository;
	/*
	public CountryApi(CountryRepository countryRepository,CurrencyRepository currencyRepository){
		this.countryRepository=countryRepository;
		this.currencyRepository= currencyRepository;
	}*/

	@Override
	public void saveCountry(CountryDTO countryDTO) {
//		Country country=ConvertUtil.convertCountryDTO(countryDTO);
//		country=countryRepository.save(country);
	}

	public void editCountry(CountryDTO countryDto){
//		Country country = countryRepository.findOne(countryDto.getId());
//		country = ConvertUtil.convertCountryDTO(countryDto, country);
//		countryRepository.save(country);
	}
	
	@Override
	public List<CountryDTO> getAllCountry() {
		List<Country> countryList=ConvertUtil.convertIterableToList(countryRepository.findAll());
		return ConvertUtil.convertCountrytoCountryDtoList(countryList);
	}

	@Override
	@Deprecated
	public Country getCountryById(long countryId) {
		return countryRepository.findOne(countryId);
	}
	
	@Override
	public CountryDTO findCountryById(long countryId) {
		return ConvertUtil.convertToCountryDto(countryRepository.findOne(countryId));
	}
	
	@Override
	public List<CountryDTO> findCountry() {
		List<Country> countryList = new ArrayList<Country>();
		countryList = countryRepository.findCountry();
		return ConvertUtil.convertCountrytoCountryDtoList(countryList);
	}
	
	@Override
	public List<CountryDTO> findAllCountryList() {
		List<Country> countryList = new ArrayList<Country>();
		countryList = countryRepository.findCountry();
		return ConvertUtil.convertCountrytoCountryDtoList(countryList);
	}
	
	@Override
	public List<CountryDTO> findAllCountryExcept(List<Country> countryList) {
		List<Long> countryId = new ArrayList<>();
		if(countryList.size()<0){
		for(Country country:countryList){
			countryId.add(country.getId());
			
		}
		// TODO Auto-generated method stub 
		return ConvertUtil.convertCountrytoCountryDtoList(countryRepository.findAllCountryExcept(countryId));
		}else{
			return findCountry();
		}
		 
	}
	
	
	

	@Override
	public List<CountryDTO> findCountryListFromNameList(List<String> countryNameList) {
		List<Country> countryList = countryRepository.findCountryListFromNameList(countryNameList);
		return ConvertUtil.convertCountrytoCountryDtoList(countryList);
	}
	
	@Override
	public List<String> findCountryListExceptNameList(List<String> countryNameList){
		return countryRepository.findCountryListExceptNameList(countryNameList);
	}

	@Override
	public void deleteCountry(long countryId) {
		countryRepository.delete(countryId);
		
	}
}
