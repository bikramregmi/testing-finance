/**
 * 
 */
package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.TransactionLog;

/**
 * @author bibek
 *
 */
@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

}
