package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.BulkSmsAlertBatch;

@Repository
public interface BulkSmsAlertBatchRepository extends JpaRepository<BulkSmsAlertBatch, Long>{

	@Query("select b from BulkSmsAlertBatch b where b.createdBy.userType=10 and b.createdBy.associatedId=?1 order by id desc")
	List<BulkSmsAlertBatch> findBulkBatchByBank(long bankId);
	
	@Query("select b from BulkSmsAlertBatch b where b.createdBy.userType=11 and b.createdBy.associatedId=?1 order by id desc")
	List<BulkSmsAlertBatch> findBulkBatchByBankBranch(long bankId);

	BulkSmsAlertBatch findByBatchId(String batch);

	@Query("select b from BulkSmsAlertBatch b order by id desc")
	List<BulkSmsAlertBatch> findAllBulkBatch();

}
