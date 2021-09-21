/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.model.AccountMode;

/**
 * @author bibek
 *
 */
@Repository
public interface CustomerBankAccountRepository extends JpaRepository<CustomerBankAccount, Long> {
	
	@Query("select cb from CustomerBankAccount cb where cb.branch.bank.id=:bankId and cb.branch.id=:branchId")
	List<CustomerBankAccount> findCustomerBankAccountOfParticularBank(@Param("bankId") long bankId, @Param("branchId") long branchId);
	
	@Query("select cb from CustomerBankAccount cb where cb.customer.uniqueId=:uuid order by cb.branch.bank.name asc")
	List<CustomerBankAccount> findCustomerBankAccountOfCustomer(@Param("uuid") String uniqueId);
	
	@Query("select cb from CustomerBankAccount cb where cb.customer.id=?1 and cb.accountMode=?2")
	List<CustomerBankAccount> findCustomerBankAccountOfCustomerAndAccountMode(Long id, AccountMode accounrMode);
	
	@Query("select cb from CustomerBankAccount cb where cb.accountNumber=:aNumber and cb.customer.id=:cId")
	CustomerBankAccount findCustomerAccountByAccountNumber(@Param("aNumber") String accountNumber, @Param("cId") long customerId);

	@Query("select cb from CustomerBankAccount cb where cb.customer.mobileNumber=?1 and cb.branch.bank.id=?2")
	List<CustomerBankAccount> findCustomerAccountByMobileNumberAndbank(String mobileNumber,long bankId);

	@Query("select cb from CustomerBankAccount cb where cb.branch.id=:branchId and cb.customer=:cus")
	List<CustomerBankAccount> findCustomerAllAccountsByBranchAndCustomer(@Param("branchId") long branchId, @Param("cus") Customer customer);

	@Query("select cb from CustomerBankAccount cb where cb.branch.bank.id=:bankId")
	List<CustomerBankAccount> findCustomerAllAccountsByBank(@Param("bankId") long bankId);
	
	@Query("select cb from CustomerBankAccount cb where cb.branch.bank.id=:bankId  and cb.customer=:cus")
	List<CustomerBankAccount> findCustomerAllAccountsByBankAndCustomer(@Param("bankId") long bankId, @Param("cus") Customer customer);

	@Query("select cb from CustomerBankAccount cb where cb.accountNumber=?1 and cb.branch.bank.id=?2")
	CustomerBankAccount findByAccountNumberAndBankId(String accountNumber,long bankId);
	
	@Query("select cb from CustomerBankAccount cb where cb.branch.id=?1")
	List<CustomerBankAccount> findCustomerAllAccountsByBranch(Long id);

	@Query("select cb from CustomerBankAccount cb where cb.branch.bank.id=?1 and cb.branch.id=?2")
	List<CustomerBankAccount> findCustomerAllAccountsByBankAndBranch(long bankId, long branchId);

	@Query("select cb from CustomerBankAccount cb where cb.customer=?1 and isPrimary=?2")
	CustomerBankAccount findPrimaryAccount(Customer customer,Boolean primary);

	@Query("select count(cb) from CustomerBankAccount cb where cb.branch.bank.id=?1 and cb.accountMode=?2")
	Long countByBankAndAccountMode(Long id, AccountMode accountMode);
	
	@Query("select count(cb) from CustomerBankAccount cb where cb.branch.id=?1 and cb.accountMode=?2")
	Long countByBranchAndAccountMode(Long branchId, AccountMode accountMode);

	@Query("select count(cb) from CustomerBankAccount cb where cb.accountMode=?1")
	Long countByAccountMode(AccountMode accountMode);

	List<CustomerBankAccount> findByAccountNumber(String accountNo);
}
