/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Commission;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long>{
	
	@Query("select c from Commission c where c.status=:status")
	List<Commission> findAllCommission(@Param("status") Status status);
	
	@Query("select c from Commission c where c.status=:status")
	List<Commission> findCommissionByStatus(@Param("status") Status status);
	
	@Query("select c from Commission c where c.id=:id")
	Commission findCommissionById(@Param("id") long commissionId);
	
	@Query("select c from Commission c where c.bank.id=:bankId")
	List<Commission> findCommissionByBank(@Param("bankId") long bankId);
	
	@Query("select c from Commission c where c.bank.id=:bankId and c.status=:status")
	List<Commission> findCommissionByBankAndStatus(@Param("bankId") long bankId, @Param("status") Status status);

	@Query("select c from Commission c where c.merchant.id=:merchant and c.service.id=:service and c.status=:status")
	Commission findByBankAndService(@Param("merchant") long merchantId, @Param("service") long serviceId, @Param("status") Status status);
//	@Query("select c from Commission c where c.merchant=?1 and c.service=?2 and c.bank.id=?3 and c.status=?4")
//	Commission findByBankAndService(Merchant merchant, MerchantService merchantService,
//			long	 bankId, Status active);

	@Query("select c from Commission c where c.merchant.id=?1")
	List<Commission> findbyMerchant(long merchantId);
}
