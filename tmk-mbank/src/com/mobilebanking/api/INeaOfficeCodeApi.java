package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.NeaOfficeCodeDTO;
import com.mobilebanking.model.Status;

public interface INeaOfficeCodeApi {
	
	public List<NeaOfficeCodeDTO> getAllOfficeCodes();

	public NeaOfficeCodeDTO saveNeaOfficeCode(NeaOfficeCodeDTO dto);

	public NeaOfficeCodeDTO editNeaOfficeCode(NeaOfficeCodeDTO dto);

	public NeaOfficeCodeDTO getNeaOfficeCode(long id);

	public NeaOfficeCodeDTO getByOfficeCode(String officeCode);

	public NeaOfficeCodeDTO changeOfficeCodeStatus(Long id);

	public List<NeaOfficeCodeDTO> getAllOfficeCodesByStatus(Status status);

	public void deleteOffice(Long id);

}
