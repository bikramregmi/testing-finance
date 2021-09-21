package com.mobilebanking.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.NonFinancialTransaction;
import com.mobilebanking.model.NonFinancialTransactionType;

@Repository
public interface NonFinancialTransactionRepository extends JpaRepository<NonFinancialTransaction, Long> {
	
	@Query("select count(m) from NonFinancialTransaction m where m.transactionType=?1")
	Long countByTransactionType(NonFinancialTransactionType nonFinancialTransactionType);

	@Query("select count(m) from NonFinancialTransaction m where m.bank=?1 and m.transactionType=?2")
	long countByBankAndTransactionType(Bank bank, NonFinancialTransactionType nonFinancialTransactionType);
	
	@Query("select count(m) from NonFinancialTransaction m where m.bankBranch=?1 and m.transactionType=?2")
	long countByBankBranchAndTransactionType(BankBranch bankbranch, NonFinancialTransactionType nonFinancialTransactionType);

	@Query("select count(m) from NonFinancialTransaction m where m.customer=?1 and m.transactionType=?2")
	long countByCustomerAndTransactionType(Customer customer, NonFinancialTransactionType nonFinancialTransactionType);

	@Query("select m from NonFinancialTransaction m where m.bank=?1 order by m.id desc")
	List<NonFinancialTransaction> findByBank(Bank bank);

	@Query("select m from NonFinancialTransaction m where m.bankBranch=?1 order by m.id desc")
	List<NonFinancialTransaction> findByBankBranch(BankBranch branch);

	@Query("select m from NonFinancialTransaction m order by m.id desc")
	List<NonFinancialTransaction> findAllTransaction();
	
	@Query("select count(m) from NonFinancialTransaction m")
	Long countAllTransaction();

	@Query("select count(m) from NonFinancialTransaction m where m.bank.id=?1")
	Long countAllTransactionByBank(long bankId);

	@Query("select count(m) from NonFinancialTransaction m where m.bankBranch.id=?1")
	Long countAllTransactionByBranch(long branchId);
	
	//added by amrit
	@Query("select count(m) from NonFinancialTransaction m where m.created=?1")
	Long countAllTransactionByDate(Date date);
	@Query("select count(m) from NonFinancialTransaction m where m.bankBranch.id=?1 and m.created=?2")
	Long countAllTransactionByBankAndDate(long associatedId, Date currentDate);
	@Query("select count(m) from NonFinancialTransaction m where m.bankBranch.id=?1 and m.created=?2")
	Long countAllTransactionByBranchAndDate(long associatedId, Date currentDate);

	@Query("select count(m) from NonFinancialTransaction m where m.transactionType=?1 and m.created>=?2")
	Long countByTransactionTypeAndDate(NonFinancialTransactionType nonFinancialTransactionType, Date date);

	@Query("select count(m) from NonFinancialTransaction m where m.bank=?1 and m.transactionType=?2 and m.created>=?3")
	Long countByBankAndTransactionTypeAndDate(Bank bank, NonFinancialTransactionType nonFinancialTransactionType,
			Date date);
	@Query("select count(m) from NonFinancialTransaction m where m.bankBranch=?1 and m.transactionType=?2 and m.created>=?3")
	Long countByBankBranchAndTransactionTypeAndDate(BankBranch bankBranch,
			NonFinancialTransactionType nonFinancialTransactionType, Date date);

	@Query("select count(m) from NonFinancialTransaction m where m.customer=?1 and m.transactionType=?2 and m.created>=?3")
	Long countByCustomerAndTransactionTypeAndDate(Customer customer,
			NonFinancialTransactionType nonFinancialTransactionType, Date date);



}
