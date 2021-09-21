package com.cas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cas.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

	@Query("select t from Transaction t where t.transactionId=?1")
	Transaction findByTransactionId(long transactionId);

	@Query("select t from Transaction t where t.fromAccount.id=?1 OR t.toAccount.id=?1 order by t.created DESC")
	List<Transaction> findByAccountId(Long id);


}
