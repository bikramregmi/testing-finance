package com.cas.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cas.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long>, JpaSpecificationExecutor<Account> {

	@Query("select a from Account a where a.accountNo=?1")
	Account findByAccountNo(String accountNo);


}
