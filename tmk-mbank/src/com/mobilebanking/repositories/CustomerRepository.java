package com.mobilebanking.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.UserType;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/*@Query("select c from Customer c where c.firstName=?1")
	Customer findByCustomer(String firstName);	*/
	
	/*@Query("select c from Customer c join fetch c.createdBy where c.id=?1")
	Customer findById(Long id);	*/
	
	@Query("select c from Customer c join fetch c.createdBy where c.uniqueId=:uuid")
	Customer findCustomerByUniqueId(@Param("uuid") String uniqueId);
	
	@Query("select c from Customer c where c.user.id=:uId")
	Customer getCustomerByUser(@Param("uId") long userId);
	
	@Query("select c from Customer c join fetch c.createdBy where c.user.userName=:userName")
	Customer getCustomerByUserName(@Param("userName") String userName);
	
//	@Query("select c from Customer c join fetch c.createdBy where c.mobileNumber=?1 and c.bank=?2")
	Customer findByMobileNumberAndBank( String mobileNumber, Bank bank);
	
//	@Query("select c from Customer c join fetch c.createdBy where c.mobileNumber=?1 and c.bankBranch=?2")
	Customer findByMobileNumberAndBankBranch(String mobileNo,BankBranch bankBranch);
	
	@Query("select c from Customer c order by c.firstName")
	List<Customer> findCustomer();
	
	/*@Query("select c from Customer c where c.bank.id=:bankId ORDER by c.firstName asc")
	List<Customer> getCustomerListofParticularBank(@Param("bankId") long bankId);*/
	
	@Query("select c from Customer c where c.bank.id=:bId ORDER by c.firstName asc")
	List<Customer> listCustomerByBank(@Param("bId") long bankId);
	
	@Query("select c from Customer c where c.bankBranch.id=:branchId ORDER by c.firstName asc")
	List<Customer> listCustomerByBranch(@Param("branchId") long branchId);
	
//	@Query("select c from Customer c where c.id not in (select u.associatedId from User u where u.userType=?1) and c.fullName  like CONCAT(?2, '%')")
//	List<Customer> getUnregisteredcustoemr(UserType userType, String objectName);
	
	/*@Query("select c from Customer c where c.created>=(:toDate-55*(24*60*60*1000))")
	List<Customer> getRecentRegisteredCustomerList(@Param("toDate") int toDate);*/
	
	/*@Query("select c from Customer c where c.created > ?1 and c.created < ?2")
	List<Customer> getTest(Date fromDate, Date toDate);*/
	
	/*@Query("select c from Customer c join fetch c.createdBy where c.creratedTimeMilliSeconds <= (:toDate - :diff)")
	List<Customer> getRecentRegisteredCustomer(@Param("toDate") long toDateMilliSeconds, @Param("diff") long difference);*/
	
	@Query("select c from Customer c where c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=:uType ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	List<Customer> getCustomerWithoutTransaction(@Param("uType") UserType userType);

	@Query("select c from Customer c where c.bank.id=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	List<Customer> getCustomerWithoutTransactionOfBank(UserType userType, long bankId);
	
	@Query("select c from Customer c where c.bankBranch.id=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	List<Customer> getCustomerWithoutTransactionOfBranch(UserType customer, long associatedId);
	
	@Query("select count(c) from Customer c")
	Long countAllCustomer();
	
	@Query("select count(c) from Customer c where c.bank.id=?1")
	Long countAllCustomerByBank(long bankId);

	@Query("select count(c) from Customer c where c.bankBranch.id=?1")
	Long countAllCustomerByBranch(long branchId);
	
	@Query("select count(c) from Customer c where c.created>=?1")
	Long countCustomerFromADate(Date date);

	@Query("select count(c) from Customer c where c.bank.id=?1 and c.created>=?2")
	Long countCustomerThisMonthFromADateByBankId(long bankId, Date fromDate);
	

	@Query("select count(c) from Customer c where c.bankBranch.id=?1 and c.created>=?2")
	Long countCustomerFromADateByBranchId(long branchId, Date date);

	@Query("select count(c) from Customer c where c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	Long countCustomerWithoutTransaction(UserType userType);
	
	@Query("select count(c) from Customer c where c.bank.id=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	Long countCustomerWithoutTransactionOfBank(UserType userType, long bankId);
	
	@Query("select count(c) from Customer c where c.bankBranch.id=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	Long countCustomerWithoutTransactionOfBranch(UserType customer, long branchId);

	@Query("select c from Customer c where c.mobileNumber like CONCAT(?1,'%') and c.bank.swiftCode=?2 ")
	List<Customer> getUserByMobileNumberConCat(String data, String bankCode);

	@Query("select c from Customer c where c.bank.swiftCode=?2 AND c.id not in (select cl.id from NotificationGroup n join n.customerList cl where n.name=?1 and n.bankCode=?2)")
	List<Customer> findCustomerNotSubscribedToNotificationGroup(String notificationGroupName,String swiftCode);
	
	@Query("select count(c) from Customer c where c.created>=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	Long countCustomerWithoutTransactionByDate(UserType customer, Date date);

	@Query("select count(c) from Customer c where c.created>=?3 and c.bank.id=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	Long countCustomerWithoutTransactionOfBankAndDate(UserType customer, long associatedId, Date date);
	
	@Query("select count(c) from Customer c where c.created>=?3 and c.bankBranch.id=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	Long countCustomerWithoutTransactionOfBranchAndDate(UserType customer, long associatedId, Date date);

	@Query("select c from Customer c where c.created>=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=:uType ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	List<Customer> getCustomerWithoutTransactionByDate(UserType customer, Date date);

	@Query("select c from Customer c where c.created>=?3 and c.bank.id=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	List<Customer> getCustomerWithoutTransactionOfBankByDate(UserType customer, long associatedId, Date date);

	@Query("select c from Customer c where c.created>=?3 and c.bankBranch.id=?2 and c.id not in (select t.originatingUser.associatedId from Transaction t where t.originatingUser.userType=?1 ) and c not in (select n.customer from NonFinancialTransaction n) order by c.id desc")
	List<Customer> getCustomerWithoutTransactionOfBranchByDate(UserType customer, long associatedId, Date date);

	@Query("select count(c) from Customer c where c.status=?1")
	Long countCustomerByStatus(CustomerStatus status);

	@Query("select count(c) from Customer c where c.bank.id=?1 and c.status=?2")
	Long countCustomerByBankAndStatus(long associatedId, CustomerStatus status);

	@Query("select count(c) from Customer c where c.bankBranch.id=?1 and c.status=?2")
	Long countCustomerByBranchAndStatus(long associatedId, CustomerStatus status);
	
    @Query("select c from Customer c where c.mobileNumber=?1 ")
	List<Customer> findByMobile(String mobileNo);

    @Query("select count(c) from Customer c where c.bank.id=?1 and c.created<=?2")
	Object countCustomerUpToLastMonth(long bankId, Date previousDate);
    
	@Query("select count(c) from Customer c where c.bank.id=?1 and c.created<=?2 and c.created>?3")
	Long countCustomerThisMonthOnlyFromADateByBankId(long bankId, Date toDate, Date fromDate);

	@Query("select c from Customer c where c.lastRenewed>=?1 order by c.lastRenewed desc")
	List<Customer> getCustomerAfterLastRenewDate(Date date);
	
	@Query("select c from Customer c where c.lastRenewed>=?1 and c.bank.id=?2 order by c.lastRenewed desc")
	List<Customer> getCustomerAfterLastRenewDateAndBank(Date time, Long associatedId);

	@Query("select c from Customer c where c.lastRenewed>=?1 and c.bankBranch.id=?2 order by c.lastRenewed desc")
	List<Customer> getCustomerAfterLastRenewDateAndBranch(Date time, Long associatedId);

}
	