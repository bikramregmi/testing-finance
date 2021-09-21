package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.INeaOfficeCodeApi;
import com.mobilebanking.converter.NeaOfficeCodeConverter;
import com.mobilebanking.entity.NeaOfficeCode;
import com.mobilebanking.model.NeaOfficeCodeDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.NeaOfficeCodeRepository;

@Service
public class NeaOfficeCodeApi implements INeaOfficeCodeApi{
	
	@Autowired
	private NeaOfficeCodeRepository neaOfficeCodeRepository;
	
	@Autowired
	private NeaOfficeCodeConverter neaOfficeCodeConverter;

	@Override
	public List<NeaOfficeCodeDTO> getAllOfficeCodes() {
		List<NeaOfficeCode> officeCodeList = neaOfficeCodeRepository.findAll();
		return neaOfficeCodeConverter.convertToDtoList(officeCodeList);
	}

	@Override
	public NeaOfficeCodeDTO saveNeaOfficeCode(NeaOfficeCodeDTO dto) {
		NeaOfficeCode entity = neaOfficeCodeConverter.convertToEntity(dto);
		entity = neaOfficeCodeRepository.save(entity);
		return neaOfficeCodeConverter.convertToDto(entity);
	}

	@Override
	public NeaOfficeCodeDTO editNeaOfficeCode(NeaOfficeCodeDTO dto) {
		NeaOfficeCode entity = neaOfficeCodeRepository.findOne(dto.getId());
		entity.setOffice(dto.getOffice());
		entity.setOfficeCode(dto.getOfficeCode());
		entity = neaOfficeCodeRepository.save(entity);
		return neaOfficeCodeConverter.convertToDto(entity);
	}

	@Override
	public NeaOfficeCodeDTO getNeaOfficeCode(long id) {
		NeaOfficeCode entity = neaOfficeCodeRepository.findOne(id);
		return neaOfficeCodeConverter.convertToDto(entity);
	}

	@Override
	public NeaOfficeCodeDTO getByOfficeCode(String officeCode) {
		NeaOfficeCode entity = neaOfficeCodeRepository.findByOfficeCode(officeCode);
		return neaOfficeCodeConverter.convertToDto(entity);
	}

	@Override
	public NeaOfficeCodeDTO changeOfficeCodeStatus(Long id) {
		NeaOfficeCode entity = neaOfficeCodeRepository.findOne(id);
		if(entity != null){
			if(entity.getStatus().equals(Status.Active)){
				entity.setStatus(Status.Inactive);
			}else if(entity.getStatus().equals(Status.Inactive)){
				entity.setStatus(Status.Active);
			}
			entity = neaOfficeCodeRepository.save(entity);
			return neaOfficeCodeConverter.convertToDto(entity);
		}
		return null;
	}

	@Override
	public List<NeaOfficeCodeDTO> getAllOfficeCodesByStatus(Status status) {
		List<NeaOfficeCode> officeCodeList = neaOfficeCodeRepository.getAllOfficeCodesByStatus(status);
		return neaOfficeCodeConverter.convertToDtoList(officeCodeList);
	}

	@Override
	public void deleteOffice(Long id) {
		neaOfficeCodeRepository.delete(id);
	}

}
