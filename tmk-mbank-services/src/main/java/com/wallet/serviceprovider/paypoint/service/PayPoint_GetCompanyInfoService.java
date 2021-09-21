package com.wallet.serviceprovider.paypoint.service;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.wallet.serviceprovider.paypoint.paypointResponse.NeaBillAmountResponse;
import com.wallet.serviceprovider.paypoint.paypointResponse.WorldLinkPackage;

public interface PayPoint_GetCompanyInfoService {

	void companyInfo();
	
	WorldLinkPackage checkPakageForWorldLink(HashMap<String,String> myHash)throws JAXBException;
	
	HashMap <String,String> checkPayment(HashMap<String,String> myHash) throws JAXBException;
	
	HashMap <String,String> executePayment(HashMap<String,String> myHash , HashMap<String,String> checkPaymentResponse) throws JAXBException;

	void executePaymentEx();
	
	void addCancelRequest();
	
	void addCancelRequestEx();
	
	void getTransactionReport();
	
	void getTransactionReportSummary();
	
	HashMap <String,String> getTransaction(HashMap<String,String> myHash) throws JAXBException;
	
	void getTransactionEx();
	
	void getTransactionByExternal();
	
	void dailyReconciliation();
	
	void retransmit();

	HashMap<String, String> executePayment(HashMap<String, String> myHash) throws JAXBException;

	NeaBillAmountResponse checkNeaPaymentAmount(HashMap<String, String> hashRequest) throws JAXBException;
	
}
