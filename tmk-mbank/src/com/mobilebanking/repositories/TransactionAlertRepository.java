package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.TransactionAlert;

@Repository
public interface TransactionAlertRepository extends JpaRepository<TransactionAlert, Long> {

	/*@Query("select ta from TransactionAlert ta where ta.traceId>?1 order by ta.traceId")
	List<TransactionAlert> findbyTraceId(Integer trace);*/

}
