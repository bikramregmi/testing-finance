package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.SettlementLog;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.model.SettlementStatus;
import com.mobilebanking.model.SettlementType;

@Repository
public interface SettlementLogRepository extends JpaRepository<SettlementLog, Long> {

	@Query("select sl from SettlementLog sl where sl.settlementStatus=?1 order by sl.id desc")
	List<SettlementLog> findBySettlementStatus(SettlementStatus status);

	@Query("select sl from SettlementLog sl where sl.transaction.id =?1 and sl.settlementType=?2")
	SettlementLog findByTransactionAndSettlementType(Long id, SettlementType merchant);

	@Query("select sl from SettlementLog sl where sl.transaction.transactionIdentifier =?1")
	List<SettlementLog> findByTransactionIdentifier(String transactionIdentifier);
	
	List<SettlementLog> findByTransaction(Transaction transaction);

}
