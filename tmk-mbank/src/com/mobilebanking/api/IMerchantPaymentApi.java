package com.mobilebanking.api;

import java.util.HashMap;

import javax.xml.bind.JAXBException;

import com.wallet.serviceprovider.paypoint.paypointResponse.WorldLinkPackage;

public interface IMerchantPaymentApi {

	HashMap<String, String> merchantPayment(String serviceI, String transactionI, double amount, long aid) throws Exception;

	boolean paytime();

	void eprabhu();

	void epay();

	void bussewa();
	
	void payPoint();
	
	void unitedsolution();
	//DishHomeDto dishHome(String serviceI);

	WorldLinkPackage worldLinkCheck(String serviceId, String transactionId, double parseDouble, Long id) throws JAXBException;

	HashMap<String, String> merchantPayment(String serviceId, String transactionId, double parseDouble, Long id,
			HashMap<String, String> hash) throws Exception;

	HashMap<String, String> getNeaPaymentAmount(String serviceId, String transactionId, String scno, String officeCode) throws JAXBException;
	
	//boolean dishHomeRecharge(String serviceI,double amount,long aid) throws Exception;

}
