package com.mobilebanking.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.ChequeBlockRequest;

@Repository
public interface ChequeBlockRequestRepository extends JpaRepository<ChequeBlockRequest, Long> {

	@Query("select cs from ChequeBlockRequest cs where cs.customerAccount.branch.id=?1")
	List<ChequeBlockRequest> getAllChequeBlockRequestByBankBranch(Long id);

	@Query("select cs from ChequeBlockRequest cs where cs.customerAccount.accountNumber.id=?1")
	List<ChequeBlockRequest> getcheckRequestByAccountNumber(String accountNumber);

	@Query("select count(c) from ChequeBlockRequest c where c.created>=?1")
	Long countRequestFromDate(Date date);


}
