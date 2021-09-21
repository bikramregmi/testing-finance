package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.MerchantManagerDTO;
import com.mobilebanking.model.Status;

public interface IMerchantManagerApi {
	
	public MerchantManagerDTO getselected(String uniqueService, Status status, boolean selected);
	
	public List<MerchantManagerDTO> findMerchantManagerByService(Long serviceId);
	
	public MerchantManagerDTO findByServiceAndMerchant(Long serviceId,Long merchantId);
	
	public List<MerchantManagerDTO> findMerchantManagerByServiceProvider(Long merchantId, Status status);

	public List<MerchantManagerDTO> getAllMerchantManagerByService(long serviceId);

	public MerchantManagerDTO merchantPriority(long serviceId, long merchantId);

	public void deleteServiceFromMerchant(long serviceId, long serviceProviderId);
	
	public void changeStatus(Long serviceProviderId, Long serviceId);

}
