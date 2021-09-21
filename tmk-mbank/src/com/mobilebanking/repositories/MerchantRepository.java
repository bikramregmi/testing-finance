package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Merchant;
import com.mobilebanking.model.Status;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long>{
	
	@Query("select m from Merchant m where m.id = (?1)")
	Merchant getMerchantById(long merchantId);

	@Query("select m from Merchant m where m.name = (?1)")
	Merchant findMerchantByName(String merchantName);
	
	@Query("select m from Merchant m where m.status=:status")
	List<Merchant> findMerchantByStatus(@Param("status") Status status);

}
