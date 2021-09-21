/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Transaction;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.TransactionStatus;
import com.mobilebanking.model.TransactionType;

/**
 * @author bibek
 *
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("select t from Transaction t where t.transactionIdentifier=?1")
	Transaction getTransactionByTransactionIdentifier(String identifier);

	@Query("select t from Transaction t where t.created>=?1 and t.created<=?2")
	List<Transaction> findTransactionBetweenDates(Date fromDate, Date toDate);

	@Query("select t from Transaction t where t.bank.id=?1 order by t.id desc")
	List<Transaction> getTransactionByBank(Long bankId);

	@Query("select count(t) from Transaction t where t.merchantManager.merchantsAndServices.merchantService.uniqueIdentifier=?1 and t.status=?2")
	long countTransactionByServiceAndStatus(String uniqueIdentifier,TransactionStatus status);

	@Query("select count(t) from Transaction t where t.merchantManager.merchantsAndServices.merchantService.uniqueIdentifier=?1 and t.bank.id=?2 and t.status=?3")
	long countTransactionByServiceAndBankAndStatus(String uniqueIdentifier, long bankId, TransactionStatus status);

	@Query("select t from Transaction t order by t.id desc")
	List<Transaction> getAllTransactions();

	List<Transaction> findByStatus(TransactionStatus status);

	@Query("select t from Transaction t where t.bankBranch.id=?1 order by t.id desc")
	List<Transaction> getTransactionByBankBranch(long associatedId);

	@Query("select count(t) from Transaction t where t.merchantManager.merchantsAndServices.merchantService.uniqueIdentifier=?1 and t.bankBranch.id=?2 and t.status=?3")
	Long countTransactionByServiceAndBranchAndStatus(String uniqueIdentifier, long associatedId, TransactionStatus status);
	
	@Query("select count(t) from Transaction t")
	Long countAllTransactions();

	@Query("select count(t) from Transaction t where t.bank.id=?1")
	Long countAllTransactionsByBank(long bankId);

	@Query("select count(t) from Transaction t where t.bankBranch.id=?1")
	Long countAllTransactionsByBranch(long branchId);

	@Query("select t from Transaction t where t.refstan=?1")
	Transaction findByRefStan(String refStan);
	
	//added by amrit
    @Query("select count(t) from Transaction t where t.created>=?1")
	Long countAllTransactionsByDate(Date date);
    @Query("select count(t) from Transaction t where t.bank.id=?1 and t.created>=?2")
	Long countAllTransactionsThisMonthByBankAndDate(long bankId,Date date);

	@Query("select count(t) from Transaction t where t.bankBranch.id=?1 and t.created>=?2")
	Long countAllTransactionsByBranchAndDate(long branchId,Date date);

	@Query("select count(t) from Transaction t where t.created>=?4 and t.merchantManager.merchantsAndServices.merchantService.uniqueIdentifier=?1 and t.bank.id=?2 and t.status=?3")
	Long countTransactionByServiceAndBankAndStatusAndDate(String uniqueIdentifier, long associatedId,
			TransactionStatus status, Date date);


	@Query("select count(t) from Transaction t where t.created>=?3 and t.merchantManager.merchantsAndServices.merchantService.uniqueIdentifier=?1 and t.status=?2")
	Long countTransactionByServiceAndStatusAndDate(String uniqueIdentifier, TransactionStatus status, Date date);

	@Query("select count(t) from Transaction t where t.created>=?4 and t.merchantManager.merchantsAndServices.merchantService.uniqueIdentifier=?1 and t.bankBranch.id=?2 and t.status=?3")
	Long countTransactionByServiceAndBranchAndStatusAndDate(String uniqueIdentifier, long associatedId,
			TransactionStatus status, Date date);

	@Query("select t from Transaction t where t.merchantManager.merchantsAndServices.merchantService.serviceCatagory.id=?1 and t.created>=?2")
	List<Transaction> getByServiceCategoryAndDate(Long serviceCategoryId, Date date);

	@Query("select t from Transaction t where t.merchantManager.merchantsAndServices.merchantService.serviceCatagory.id=?1")
	List<Transaction> getByServiceCategory(Long serviceCategoryId);

	@Query("select count(t) from Transaction t where t.transactionType=?1")
	Long countByTransactionType(TransactionType transactionType);
	
	@Query("select count(t) from Transaction t where t.transactionType=?1 and t.created>=?2")
	Long countByTransactionTypeAndDate(TransactionType transactionType, Date date);
    @Query("select count(t) from Transaction t where t.bank.id=?1 and t.created<=?2")
	Long countAllTransactionsUpToLastMonthByBankAndDate(long parseLong, Date date);
    @Query("select count(t) from Transaction t where t.bank.id=?1 and t.created<=?2 and t.created>?3")
	Long countAllTransactionsThisNepaliMonthByBankAndDate(long parseLong, Date monthYear, Date previousMonthDate);

    @Query("select t from Transaction t where t.originatingUser.id=?1 order by t.id desc")
	List<Transaction> findTransationByOriginatingUser(Long userId);
    
 /*   @Query("select sum(t.amount) from Transaction t where t.bank.id=?1")
	Double sumTotalTransactionAmountByBank(long parseLong);
    
    @Query("select sum(t.amount) from Transaction t where t.bank.id=?1 and t.created<=?2")
	Double sumTotalTransactionAmountUptoLatMonthByBankAndDate(long parseLong, Date monthYear);

    @Query("select sum(t.amount) from Transaction t where t.bank.id=?1 and t.created>?2")
	Double sumTotalTransactionAmountThisMonthByBankAndDate(long parseLong, Date monthYear);
*/
}
