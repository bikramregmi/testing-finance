package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.MobileBankingCancelRequestDTO;

public interface IMobileBankingCancelRequestApi {

	public void save(Long customerId);

	public List<MobileBankingCancelRequestDTO> findRequest(Long userId);
	
	public MobileBankingCancelRequestDTO findByCustomer(long customerId);

}
