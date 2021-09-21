package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilebanking.api.ICityApi;
import com.mobilebanking.controller.CityController;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.State;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.StateRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
@Transactional
public class CityApi implements ICityApi {

	private Logger logger=LoggerFactory.getLogger(CityController.class );
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;
	/*public CityApi(CityRepository cityRepository,StateRepository stateRepository){
		this.cityRepository=cityRepository;
		this.stateRepository=stateRepository;
		}
	*/
	@Override
	public void saveCity(CityDTO cityDTO){
		
		State state=stateRepository.findByState(cityDTO.getState());
		City city=new City();
		city.setName(cityDTO.getName());
		city.setState(state);
		city.setStatus(Status.Active);
		cityRepository.save(city);
		logger.debug("City Saved");
		
	}
	
	@Override
	public List<CityDTO> findCity() {
		List<City> cityList = new ArrayList<City>();
		cityList = cityRepository.findCity();
		return ConvertUtil.convertCityDtoToCity(cityList);
	}
	
	@Override
	public List<CityDTO> getAllCity() {
		List<City> cityList=ConvertUtil.convertIterableToList(cityRepository.findAll());
		return ConvertUtil.convertCityDtoToCity(cityList);
	}
	
	@Override
	public City getCityById(long cityId) {
		return cityRepository.findOne(cityId);
	}
	
	@Override
	public List<CityDTO> getCityByState(long state) {
		List<City> cityList=ConvertUtil.convertIterableToList(cityRepository.findCityByState(state));
		return ConvertUtil.convertCityDtoToCity(cityList);
	}

	@Override
	public void editCity(CityDTO cityDTO) {
		City city=getCityById(cityDTO.getId());
		city.setName(cityDTO.getName());
		city.setStatus(cityDTO.getStatus());
		cityRepository.save(city);
	}

	@Override
	public CityDTO findByCity(String name) {
		City city = cityRepository.findByCity(name);
		
		return ConvertUtil.convertCity(city);
	}

	@Override
	public City findByCityName(String name) {
		City city = cityRepository.findByCity(name);
		return city;
	}

	@Override
	public List<CityDTO> findCityByStateName(String stateName) {
		List<City> citiesList = ConvertUtil.convertIterableToList(cityRepository.findCityByStateName(stateName));
		return ConvertUtil.convertCityDtoToCity(citiesList);
	}

	@Override
	public void saveBulkCity(CityDTO cityDTO) {
		City city=new City();
		if(cityDTO.getState()!=null){
		State state=stateRepository.findByState(cityDTO.getState());
		if(state==null){
			city.setState(state);
			}}
		if(cityDTO.getName()!=null){
		city.setName(cityDTO.getName());
		city.setStatus(Status.Active);
		cityRepository.save(city);
		}
		
		logger.debug("City Saved");
		
	}

	@Override
	public List<CityDTO> getCityByStateName(String stateName) {
		List<City> cityList = cityRepository.findCityByStateName(stateName);
		if (! cityList.isEmpty()) {
			return ConvertUtil.convertCityDtoToCity(cityList);
		}
		return null;
	}

}
