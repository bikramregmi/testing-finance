package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBenificiary;
import com.mobilebanking.model.Status;
@Repository
public interface CustomerBenificiaryRepository extends JpaRepository<CustomerBenificiary, Long> {

	@Query("select b from CustomerBenificiary b where b.sender=?1 and b.bank=?3 and b.status=?2")
	List<CustomerBenificiary> listBenificiary(Customer sender ,Status status,Bank bank);
	
	@Query("select b from CustomerBenificiary b where b.sender=?1")
	List<CustomerBenificiary> listBenificiary(Customer senderCustomer);

	@Query("select b from CustomerBenificiary b where b.accountNumber=?1")
	CustomerBenificiary findByAccountNumber(String accountNumber);

	@Query("select b from CustomerBenificiary b where b.accountNumber=?1 and b.sender =?2")
	CustomerBenificiary findByAccountNumberAndSender(String accountNumber, Customer customer);

}
