package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.MobileBankingCancelRequest;

@Repository
public interface MobileBankingCancelRequestRepository extends JpaRepository<MobileBankingCancelRequest, Long>{

	@Query("select m from MobileBankingCancelRequest m order by id desc")
	List<MobileBankingCancelRequest> findAllCancelRequest();

	@Query("select m from MobileBankingCancelRequest m where m.customer.bank.id=?1 order by id desc")
	List<MobileBankingCancelRequest> findAllCancelRequestByBank(long bankId);

	@Query("select m from MobileBankingCancelRequest m where m.customer.bankBranch.id=?1 order by id desc")
	List<MobileBankingCancelRequest> findAllCancelRequestByBranch(Long branchId);

	MobileBankingCancelRequest findByCustomer(Customer customer);

}
