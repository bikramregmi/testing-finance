/**
 * 
 */
package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CreditLimit;

/**
 * @author bibek
 *
 */
@Repository
public interface CreditLimitRepository extends JpaRepository<CreditLimit, Long> {
	
	@Query("select cl from CreditLimit cl where cl.bank.id=:bId")
	CreditLimit findCreditLimitByBank(@Param("bId") long bankId);
	
	@Query("select cl.amount from CreditLimit cl where cl.bank.id=:bId")
	Double findCreditAmountByBank(@Param("bId") long bankId);
	
	
}
