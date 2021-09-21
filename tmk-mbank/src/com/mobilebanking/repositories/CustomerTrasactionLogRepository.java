package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.CustomerTransactionLog;

@Repository
public interface CustomerTrasactionLogRepository extends JpaRepository<CustomerTransactionLog, Long> {

	CustomerTransactionLog findByCustomerBankAccount(CustomerBankAccount customerBankAccount);

}
