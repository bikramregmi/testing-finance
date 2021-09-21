package com.wallet.serviceprovider.dishhome.service;

import java.util.HashMap;

public interface DishHome_BalanceEnquiryService {

	void ping();
	void pingJSON();
	HashMap<String, String> balanceEnquiry();
	void balanceEnquiryJson();
	HashMap<String, String> recharge(HashMap<String, String> myHash);
	void rechargeJSON();
	void getLanguages();
	void getLanguagesJSON();
	void updateLanguage();
	void updateLanguageJSON();
	HashMap<String, String>  rechargeBySTBIDorCASIDorCHIPID(HashMap<String, String> myHash);
	void rechargeBySTBIDorCASIDorCHIPIDJSON();
	void getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPID();
	void getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON();
	void getStatusbyTransactionID();
	void getStatusbyTransactionIDJSON();
	void getCustomerDetailBySTBIDorCASIDorCHIPID();
	void getCustomerDetailBySTBIDorCASIDorCHIPIDJSON();
	void lookupVoucher();
	void lookupVoucherJSON();
	void packageChange();
	void packageChangeJSON();
	void rtnCheck();
	void rtnCheckJSON();
	void customerIDCheck();
	void customerIDCheckJSON();
	void packageInfo();
	void packageInfoJSON();
	
	
		
}
