package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IComplianceApi;
import com.mobilebanking.controller.ComplianceController;
import com.mobilebanking.entity.Compliance;
import com.mobilebanking.entity.Country;
import com.mobilebanking.model.ComplianceDTO;
import com.mobilebanking.model.ComplianceType;
import com.mobilebanking.repositories.ComplianceRepository;
import com.mobilebanking.repositories.CountryRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
public class ComplianceApi implements IComplianceApi {
	
	
	private Logger logger=LoggerFactory.getLogger(ComplianceController.class );
	
	@Autowired
	private  ComplianceRepository complianceRepository;
	@Autowired
	private  CountryRepository countryRepository;
	/*public ComplianceApi(ComplianceRepository complianceRepository,CountryRepository countryRepository){
		this.complianceRepository=complianceRepository;
		this.countryRepository=countryRepository;
		}
	*/
	@Override
	public void saveCompliance(ComplianceDTO complianceDTO){
		
		Country country=countryRepository.findByCountry(complianceDTO.getCountry());
		Compliance compliance=new Compliance();
		compliance.setCountry(country);
		compliance.setDays(complianceDTO.getDays());
		compliance.setAmount(complianceDTO.getAmount());
		compliance.setRequirements(complianceDTO.getRequirements());
		if (complianceDTO.getComplianceType().equals("Sending")) {
			compliance.setComplianceType(ComplianceType.Sending);
		} else {
			compliance.setComplianceType(ComplianceType.Receiving);
		}
		complianceRepository.save(compliance);
		logger.debug("Compliance Saved");
		
	}
	
	@Override
	public List<ComplianceDTO> getAllCompliance() {
		List<Compliance> complianceList=ConvertUtil.convertIterableToList(complianceRepository.findAll());
		return ConvertUtil.convertComplianceDtoToCompliance(complianceList);
	}
	@Override
	public Compliance getComplianceById(long complianceId) {
		// TODO Auto-generated method stub
		return complianceRepository.findOne(complianceId);
	}

	@Override
	public void editCompliance(ComplianceDTO complianceDTO) {
		Compliance compliance=getComplianceById(complianceDTO.getId());
		Country country=countryRepository.findByCountry(complianceDTO.getCountry());
		compliance.setCountry(country);
		compliance.setDays(complianceDTO.getDays());
		compliance.setAmount(complianceDTO.getAmount());
		compliance.setRequirements(complianceDTO.getRequirements());
		complianceRepository.save(compliance);
		// TODO Auto-generated method stub
	}
	@Override
	public List<ComplianceDTO> findCompliance() {
		List<Compliance> complianceList = new ArrayList<Compliance>();
		complianceList = complianceRepository.findCompliance();
		return ConvertUtil.convertComplianceDtoToCompliance(complianceList);
	}
	
	public ComplianceDTO findByCountryAndType(String countryId,ComplianceType type){
		Compliance compliance = complianceRepository.findByCountryAndType(Long.parseLong(countryId), type);
		return ConvertUtil.convertCompliance(compliance);
	}
	
	

}
