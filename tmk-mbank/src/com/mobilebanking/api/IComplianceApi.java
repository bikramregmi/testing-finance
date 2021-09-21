package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.Compliance;
import com.mobilebanking.model.ComplianceDTO;
import com.mobilebanking.model.ComplianceType;

public interface IComplianceApi {
	
	void saveCompliance(ComplianceDTO dto);
	
	List<ComplianceDTO> getAllCompliance();

	Compliance getComplianceById(long complianceId);

	void editCompliance(ComplianceDTO complianceDTO);
	
	List<ComplianceDTO> findCompliance();
	
	ComplianceDTO findByCountryAndType(String country,ComplianceType type);

}
