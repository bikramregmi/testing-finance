package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CardlessBankAccount;

@Repository
public interface CardlessBankAccountsRepository extends JpaRepository<CardlessBankAccount, Long>{

	@Query("select c from CardlessBankAccount c where c.bank.swiftCode=?1")
	List<CardlessBankAccount> findByBankCode(String bankCode);
	
	@Query("select c from CardlessBankAccount c where c.cardlessBank.id=?1")
	List<CardlessBankAccount> findByCardlessBank(Long cardlessBankId);
	
	@Query("select c from CardlessBankAccount c where c.cardlessBank.id=?1 and c.bank.id=?2")
	CardlessBankAccount findByCardlessBankAndBank(Long cardlessBankId,Long bankId);
	

}
