/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilebanking.entity.EmailLog;
import com.mobilebanking.model.EmailType;

/**
 * @author bibek
 *
 */
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

	List<EmailLog> findByEmailTypeAndBankId(EmailType lowBalanceAlert, Long bankid);

}
