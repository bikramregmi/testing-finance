package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.City;
import com.mobilebanking.model.CityDTO;

public interface ICityApi {

	void saveCity(CityDTO cityDTO);
	
	List<CityDTO> findCity();

	List<CityDTO> getAllCity();

	City getCityById(long cityId);

	void editCity(CityDTO cityDTO);
	
	List<CityDTO> getCityByState(long state);
	
	CityDTO findByCity(String name);
	
	City findByCityName(String name);
	
	List<CityDTO> findCityByStateName(String stateName);

	void saveBulkCity(CityDTO city);
	
	List<CityDTO> getCityByStateName(String stateName);

}
