package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerProfile;
import com.mobilebanking.model.ProfileStatus;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

	@Query("select cp from CustomerProfile cp JOIN FETCH cp.customer c where c=?1")
	CustomerProfile findByCustomer(Customer customer);

	@Query("select cp from CustomerProfile cp LEFT JOIN FETCH cp.customer c where upper(cp.name)=upper(?1)")
	CustomerProfile findByName(String name);

	@Query("select cp from CustomerProfile cp LEFT JOIN FETCH cp.customer c where cp.id=?1")
	CustomerProfile findById(long customerProfileId);

	List<CustomerProfile> findByBank(Bank bank);

	@Query("select cp from CustomerProfile cp where cp.bank is NULL")
	List<CustomerProfile> findPreviousProfile();

	@Query("select cp from CustomerProfile cp where cp.bank.id=?1 and (cp.status !=?2 or cp.status is NULL)")
	List<CustomerProfile> findByBankAndStatus(Long bankId, ProfileStatus active);

	@Query("select cp.bank from CustomerProfile cp where cp.id=?1")
	Bank findBankByProfile(long profileId);

	CustomerProfile findByProfileUniqueId(String uniqueId);

	// @Query("select cp from CustomerProfile cp where
	// cp.isRegistrationCharge=?1")
	// List<CustomerProfile> findByRegistrationCharge(boolean
	// isRegistrationCharge);

	// @Query("select cp from CustomerProfile cp where
	// cp.registrationCharge<=0")
	// List<CustomerProfile> findByRenewalCharge();

}
