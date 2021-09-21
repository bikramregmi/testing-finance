package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.BulkSmsAlertBatch;
import com.mobilebanking.entity.SmsAlert;
import com.mobilebanking.model.SmsStatus;

@Repository
public interface SmsAlertRepository extends JpaRepository<SmsAlert, Long>{

	List<SmsAlert> findBySmsStatus(SmsStatus smsStatus);

	@Query("select count(s) from SmsAlert s where s.bulkSmsAlertBatch=?1")
	Long countByBulkSmsAlertBatch(BulkSmsAlertBatch bulkSmsAlertBatch);

	@Query("select s from SmsAlert s where s.bulkSmsAlertBatch.id=?1")
	List<SmsAlert> findByBatchId(long batchId);

}
