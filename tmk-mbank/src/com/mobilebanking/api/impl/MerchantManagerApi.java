package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IMerchantManagerApi;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.model.MerchantManagerDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.MerchantManagerRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
public class MerchantManagerApi implements IMerchantManagerApi{
	
	@Autowired
	private MerchantManagerRepository merchantManagerRepository;

	@Override
	public MerchantManagerDTO getselected(String uniqueService, Status status, boolean selected) {
		
		MerchantManager merchantManager = merchantManagerRepository.getselected(uniqueService, status, selected);
		return ConvertUtil.convertMerchantManager(merchantManager);
	}

	@Override
	public List<MerchantManagerDTO> findMerchantManagerByService(Long serviceId) {
		
		List<MerchantManager> merchantManagerList = merchantManagerRepository.findMerchantManagerByService(serviceId);
		return ConvertUtil.convertMerchantManagerList(merchantManagerList);
	}

	@Override
	public MerchantManagerDTO findByServiceAndMerchant(Long serviceId, Long merchantId) {

		MerchantManager merchantManager = merchantManagerRepository.findByServiceAndMerchant(serviceId, merchantId);
		return ConvertUtil.convertMerchantManager(merchantManager);
	}

	@Override
	public List<MerchantManagerDTO> findMerchantManagerByServiceProvider(Long merchantId, Status status) {

		List<MerchantManager> merchantManagerList = merchantManagerRepository.findMerchantManagerByServiceProvider(merchantId, status);
		return ConvertUtil.convertMerchantManagerList(merchantManagerList);
	}

	@Override
	public List<MerchantManagerDTO> getAllMerchantManagerByService(long serviceId) {
        List<MerchantManager> merchantManagers = merchantManagerRepository.findMerchantManagerByService(serviceId);
		return ConvertUtil.convertMerchantManagerList(merchantManagers);

	}

	@Override
	public MerchantManagerDTO merchantPriority(long serviceId, long merchantId) {
		List<MerchantManager> merchantManagers = merchantManagerRepository.findMerchantManagerByService(serviceId);

		for (MerchantManager m : merchantManagers) {
			m.setSelected(false);
		}

		merchantManagerRepository.save(merchantManagers);

		MerchantManager entity = merchantManagerRepository.findByServiceAndMerchant(serviceId, merchantId);

		entity.setSelected(true);

		entity = merchantManagerRepository.save(entity);
		
		return ConvertUtil.convertMerchantManager(entity);

	}

	@Override
	public void deleteServiceFromMerchant(long serviceId, long serviceProviderId) {
		// TODO Auto-generated method stub
		MerchantManager entity = merchantManagerRepository.findByServiceAndMerchant(serviceId, serviceProviderId);
		merchantManagerRepository.delete(entity);

	}

	@Override
	public void changeStatus(Long serviceProviderId, Long serviceId) {
		
		MerchantManager entity = merchantManagerRepository.findByServiceAndMerchant(serviceId, serviceProviderId);
		
		if(entity.getStatus().toString().equals("Active")){
			entity.setStatus(Status.Inactive);
		}else{
			entity.setStatus(Status.Active);
		}
		merchantManagerRepository.save(entity);

		
	}

}
