package com.mobilebanking.api.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IKhanepaniApi;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.MerchantManagerRepository;
import com.mobilebanking.util.StringConstants;
import com.wallet.serviceprovider.khalti.KhanepaniCounter;
import com.wallet.serviceprovider.khalti.KhanepaniCustomerDetailResponse;
import com.wallet.serviceprovider.khalti.KhanepaniServiceChargeResponse;
import com.wallet.serviceprovider.khalti.service.KhanepaniService;

@Service
public class KhanepaniApi implements IKhanepaniApi{
	
	@Autowired
	private KhanepaniService khanepaniService;
	
	@Autowired
	private MerchantManagerRepository merchantManagerRepository;
	
	@Override
	public List<KhanepaniCounter> getKhanePaniCounters() {
		MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.KHANE_PANI, Status.Active, true);
		if(merchantManager != null){
			String url = merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl();
			String token = merchantManager.getMerchantsAndServices().getMerchant().getExtraFieldOne();
			HashMap<String, String> requestHash = new HashMap<>();
			requestHash.put("url", url);
			requestHash.put("token", token);
			return khanepaniService.getCounters(requestHash);
		}
		return null;
	}
	
	@Override
	public KhanepaniCustomerDetailResponse getKhanepaniCustomerDetail(String customer_code, String counter, String month_id) {
		HashMap<String, String> requestHash = new HashMap<String, String>();
		MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.KHANE_PANI, Status.Active, true);
		if (merchantManager == null) {
			return null;
		} else {
			requestHash.put("token", merchantManager.getMerchantsAndServices().getMerchant().getExtraFieldOne());
			requestHash.put("url", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());
		}
		requestHash.put("customer_code", customer_code);
		requestHash.put("counter", counter);
		requestHash.put("month_id", month_id);
		KhanepaniCustomerDetailResponse khanepaniCustomerDetailResponse =khanepaniService.getCustomerDetail(requestHash);
		return khanepaniCustomerDetailResponse;
	}
	
	@Override
	public KhanepaniServiceChargeResponse getKhanePaniServiceCharge(String amount, String counter) {
		MerchantManager merchantManager = merchantManagerRepository.getselected(StringConstants.KHANE_PANI, Status.Active, true);
		if(merchantManager != null){
			String url = merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl();
			String token = merchantManager.getMerchantsAndServices().getMerchant().getExtraFieldOne();
			HashMap<String, String> requestHash = new HashMap<>();
			requestHash.put("url", url);
			requestHash.put("token", token);
			requestHash.put("amount", amount);
			requestHash.put("counter", counter);
			return khanepaniService.getServiceCharge(requestHash);
		}
		return null;
	}

}
