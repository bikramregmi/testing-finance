package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.NeaOfficeCode;
import com.mobilebanking.model.Status;

@Repository
public interface NeaOfficeCodeRepository extends JpaRepository<NeaOfficeCode, Long>{

	@Query("select o from NeaOfficeCode o where o.status=?1 order by o.office")
	List<NeaOfficeCode> getAllOfficeCodesByStatus(Status status);
	
	public NeaOfficeCode findByOfficeCode(String officeCodes);

	
}