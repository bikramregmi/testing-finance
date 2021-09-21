package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.PoolAccount;

public interface PoolAccountRepository extends CrudRepository<PoolAccount, Long>, JpaSpecificationExecutor<PoolAccount>{

}
