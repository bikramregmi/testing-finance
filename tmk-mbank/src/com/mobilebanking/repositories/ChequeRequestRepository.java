package com.mobilebanking.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.ChequeRequest;

@Repository
public interface ChequeRequestRepository extends JpaRepository<ChequeRequest, Long> {

	@Query("select cr from ChequeRequest cr where cr.customerAccount.branch.id=?1")
	List<ChequeRequest> getAllChequeRequestByBankBranch(Long id);
	
	@Query("select cr from ChequeRequest cr where cr.customerAccount.accountNumber=?1")
	List<ChequeRequest> getcheckRequestByAccountNumber(String accountNumber);

	@Query("select count(c) from ChequeRequest c where c.created>=?1")
	Long countRequestFromDate(Date date);

}
