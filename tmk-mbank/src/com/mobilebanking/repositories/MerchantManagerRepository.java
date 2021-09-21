package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.model.Status;

@Repository
public interface MerchantManagerRepository extends JpaRepository<MerchantManager, Long>{

	@Query("select s from MerchantManager s where s.merchantsAndServices.merchantService.uniqueIdentifier=?1 and s.status=?2 and s.selected=?3")
	MerchantManager getselected(String uniqueService, Status status, boolean selected);

	@Query("select s from MerchantManager s where s.merchantsAndServices.merchantService.id=?1")
	 List<MerchantManager> findMerchantManagerByService(Long serviceId);
	
	@Query("select s from MerchantManager s where s.merchantsAndServices.merchantService.id=?1 and s.merchantsAndServices.merchant.id=?2")
	MerchantManager findByServiceAndMerchant(Long serviceId,Long merchantId);
	
	@Query("select s from MerchantManager s where s.merchantsAndServices.merchant.id=?1 and s.status=?2")
	 List<MerchantManager> findMerchantManagerByServiceProvider(Long merchantId, Status status);

	@Query("select s from MerchantManager s where  s.merchantsAndServices.merchant.id=?1")
	List<MerchantManager> findMerchantManagerByMerchant(long merchantId);

}
