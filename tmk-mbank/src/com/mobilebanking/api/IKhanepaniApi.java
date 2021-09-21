package com.mobilebanking.api;

import java.util.List;

import com.wallet.serviceprovider.khalti.KhanepaniCounter;
import com.wallet.serviceprovider.khalti.KhanepaniCustomerDetailResponse;
import com.wallet.serviceprovider.khalti.KhanepaniServiceChargeResponse;

public interface IKhanepaniApi {
	
	List<KhanepaniCounter> getKhanePaniCounters();

	KhanepaniCustomerDetailResponse getKhanepaniCustomerDetail(String customer_code, String counter, String month_id);

	KhanepaniServiceChargeResponse getKhanePaniServiceCharge(String amount, String counter);
}
