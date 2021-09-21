/**
 * 
 */
package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankOAuthClient;

/**
 * @author bibek
 *
 */
@Repository
public interface BankOAuthClientRepository extends JpaRepository<BankOAuthClient, Long> {
	
	@Query("select bo.oAuthClientId from BankOAuthClient bo where bo.bank.id=:id")
	String findOAuthClientForBank(@Param("id") long bankId);

	BankOAuthClient findByOAuthClientId(String clientId);

	BankOAuthClient findByBank(Bank bank);
}
