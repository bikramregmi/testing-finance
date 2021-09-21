package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.Compliance;
import com.mobilebanking.model.ComplianceType;

public interface ComplianceRepository extends CrudRepository<Compliance, Long>,JpaSpecificationExecutor<Compliance> {

	@Query("select s from Compliance s order by s.id")
	List<Compliance> findCompliance();
	
	@Query("select s from Compliance s where s.id=?1")
	Compliance findByCompliance(String id);
	
	@Query("select s from Compliance s where s.id=?1")
	Compliance findByCountry(String id);
	
	@Query("select e from Compliance e where e.country=?1")
	Compliance findByComplianceCountry(String country);
	
	@Query("select e from Compliance e where e.country.id=?1 and e.complianceType=?2")
	Compliance findByCountryAndType(Long id,ComplianceType type);
	
}
