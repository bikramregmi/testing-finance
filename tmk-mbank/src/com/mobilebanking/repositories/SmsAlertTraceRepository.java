package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.SmsAlertTrace;

@Repository
public interface SmsAlertTraceRepository extends JpaRepository<SmsAlertTrace, Long> {
	@Query("select traceId from SmsAlertTrace")
	String findByTraced();

	SmsAlertTrace findByBank(Bank bank);

}
