/**
 * 
 */
package com.mobilebanking.api;

import java.util.HashMap;

import javax.xml.bind.JAXBException;

import com.wallet.serviceprovider.paypoint.paypointResponse.NeaBillAmountResponse;
import com.wallet.serviceprovider.paypoint.paypointResponse.WorldLinkPackage;

/**
 * @author bibek
 *
 */
public interface IMerchantPaymentRefactorApi {
	
	HashMap<String, String> merchantPayment(String serviceId, String transactionIdentifier, double amount, long userAssociateId,  String accountNumber, HashMap<String, String> hashInput) throws Exception;
	
	WorldLinkPackage worldLinkCheck(String serviceId, String transactionId, double amount, Long id) throws JAXBException;
	
	HashMap<String, String> merchantPayment(String serviceId, String transactionId, double amount, Long id, HashMap<String, String> hash) throws Exception;

	NeaBillAmountResponse getNeaPaymentAmount(String serviceId, String transactionId, String scno, String officeCode) throws JAXBException;

	HashMap<String, Object> merchantPaymentAirlies(String serviceId, String strFlightId, double parseDouble,
			Long id, String accountNumber, HashMap<String, Object> hashRequest);

//	HashMap<String,String> merchantPayment(String serviceId, String transactionIdentifier, double amount, long userAssociatedId, String accountNumber) throws Exception;
	//HashMap<String, String> merchantPayment(String serviceI, String transactionI, double amount, long aid) throws Exception;
}
