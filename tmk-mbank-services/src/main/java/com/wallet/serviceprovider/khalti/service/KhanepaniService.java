package com.wallet.serviceprovider.khalti.service;

import java.util.HashMap;
import java.util.List;

import com.wallet.serviceprovider.khalti.KhanepaniCounter;
import com.wallet.serviceprovider.khalti.KhanepaniCustomerDetailResponse;
import com.wallet.serviceprovider.khalti.KhanepaniServiceChargeResponse;

public interface KhanepaniService {
	
	List<KhanepaniCounter> getCounters(HashMap<String, String> requestHash);
	
	KhanepaniCustomerDetailResponse getCustomerDetail(HashMap<String, String> requestHash);

	HashMap<String, String> payKhanepaniBill(HashMap<String, String> myHash);

	KhanepaniServiceChargeResponse getServiceCharge(HashMap<String, String> requestHash);

}
