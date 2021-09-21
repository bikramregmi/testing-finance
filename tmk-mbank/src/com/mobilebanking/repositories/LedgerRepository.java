package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.Ledger;

public interface LedgerRepository extends CrudRepository<Ledger, Long>, JpaSpecificationExecutor<Ledger>{

	@Query("select l from Ledger l where l.transactionId=?1")
	Ledger findByLedgerId(long transactionId);
	
	@Query("select l from Ledger l where l.fromAccount.id=?1 OR l.toAccount.id=?1 order by l.created DESC")
	List<Ledger> findByAccountId(Long id);
}
