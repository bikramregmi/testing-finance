package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.BankCommission;
import com.mobilebanking.model.Status;

@Repository
public interface BankCommissionRepository extends JpaRepository<BankCommission, Long> {

	@Query("select bc from BankCommission bc where bc.commissionAmount.id=?1 and bc.status=?2 and bc.bank.id=?3")
	BankCommission findByCommissionAmountAndStatus(Long id, Status status,Long bankid);

}
