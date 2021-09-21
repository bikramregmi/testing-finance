package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.AirlinesCommissionManagement;
import com.mobilebanking.entity.Customer;

@Repository
public interface AirlinesCommissionManagementRepository extends JpaRepository<AirlinesCommissionManagement, Long> {

	AirlinesCommissionManagement findByCustomer(Customer customer);

}
