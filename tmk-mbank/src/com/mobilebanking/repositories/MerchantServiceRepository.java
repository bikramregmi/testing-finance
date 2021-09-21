/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
public interface MerchantServiceRepository extends JpaRepository<MerchantService, Long> {
	
	@Query("select ms from MerchantService ms where ms.service=:service")
	MerchantService findMerchantServiceByName(@Param("service") String service);
	
	@Query("select ms from MerchantService ms where ms.uniqueIdentifier=:uuid")
	MerchantService findMerchantServiceByIdentifier(@Param("uuid") String uniqueIdentifier);
		
	@Query("select ms from MerchantService ms where ms.status=:status")
	List<MerchantService> findMerchantServiceByStatus(@Param("status") Status status);
	
	@Query("select ms from MerchantService ms order by ms.service")
	List<MerchantService> findAllMerchantServices();

	@Query("select ms from MerchantService ms where ms.status=?2 and ms not in(select m.merchantsAndServices.merchantService from MerchantManager m where m.merchantsAndServices.merchant.id=?1)")
	List<MerchantService> findNotIncludedMerchantServiceByMerchantIdAndStatus(long merchantId, Status status);

	@Query("select s from MerchantService s where s not in (select e.merchantsAndServices.merchantService from MerchantManager e where e.merchantsAndServices.merchant.id=?1)")
	List<MerchantService> getAllMerchantServicesExceptIncluded(long merchantServiceId);

	

//
//	@Query("select ms from MerchantService ms where ms.status=?2 and ms not in("
//			+ "select m.merchantsAndServices.merchantService MerchantManager m "
//			+ "where m.merchantsAndServices.merchant.id=?1)")
//	List<MerchantService> findNotIncludedMerchantServiceByMerchantIdAndStatus(long merchantId, Status status);
	
}
