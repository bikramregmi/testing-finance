/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankMerchantAccounts;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Repository
public interface BankMerchantAccountsRepository extends JpaRepository<BankMerchantAccounts, Long> {

	List<BankMerchantAccounts> findByBank(Bank bank);
	
	@Query("select bm.accountNumber from BankMerchantAccounts bm where bm.bank.id=:bId and bm.merchant.id=:mId and bm.status=:status ")
	String findMerchantAccountByBankAndMerchantStatus(@Param("bId") long bankId, 
			@Param("mId") long merchantId, @Param("status") Status status);

}
