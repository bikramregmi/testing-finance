package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.AccountType;

public interface AccountRepository extends CrudRepository<Account, Long>, JpaSpecificationExecutor<Account>{

	@Query("select a from Account a where a.accountNumber=:number")
	Account findAccountByAccountNumber(@Param("number") String accountNumber);
	
    @Query("select a from Account a where a.user=?1")
	Account findByUser(User user);
    
    @Query("select a from Account a where a.merchant.id=?1")
	Account findAccountByMerchantId(Long id);
    
    @Query("select a from Account a where a.accountHead=:aHead and a.accountType=:uType")
    Account findAccountByAccountHeadAndUserType(@Param("aHead") String accountHead, @Param("uType") AccountType accountType);
    
    @Query("select a from Account a where a.bank.id=?1 and a.accountType=?2")
    Account findAccountOfBank(long bankId,AccountType accountType);
    
    @Query("select a.balance from Account a where a.bank.id=?1 AND a.accountType=?2")
    Double findBalanceOfBank(long bankId,AccountType accountType);
    
    @Query("select a.balance from Account a where a.accountNumber=:number")
    Double findBalanceByAccountNumber(@Param("number") String accountNumber);

    @Query("select a from Account a where a.bank.id=?1 and a.cardlessBank.id=?2")
	Account findAccountByBankAndCardlessBank(Long bankId, Long cardlessBankId);
    
    
}
