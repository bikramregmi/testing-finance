package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.Status;

public interface IMerchantApi {
	
	MerchantDTO findMerchantById(long merchantId);
	
	List<MerchantDTO> getAllMerchant();
	
	MerchantDTO saveMerchant(MerchantDTO merchantDTO) throws Exception;
	
	void deleteMerchant(long merchantid);
	
	MerchantDTO editMerchant(MerchantDTO merchant);

	List<MerchantDTO> getMerchantByStatus(Status status);

	void addMerchantServiceToMerchant(long merchantServiceId, long serviceProviderId);
	
}
