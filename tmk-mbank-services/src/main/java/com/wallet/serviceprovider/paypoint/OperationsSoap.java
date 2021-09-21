package com.wallet.serviceprovider.paypoint;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class OperationsSoap extends WebServiceGatewaySupport implements OperationsSoapInterface{
	
	private static final Logger log = LoggerFactory.getLogger(OperationsSoap.class);
	
	public void bypassSslCertificate() throws NoSuchAlgorithmException, KeyManagementException{
	
	// Create a trust manager that does not validate certificate chains
    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
    };

    // Install the all-trusting trust manager
    SSLContext sc = SSLContext.getInstance("SSL");
	sc.init(null, trustAllCerts, new java.security.SecureRandom());
	
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    // Install the all-trusting host verifier
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	@Override
	public String checkPayment(int companyCode, int serviceCode, String account, String special1, String special2,
			String transactionDate, long transactionId, String userId, String userPassword, int salePointType, String url) {

		CheckPayment checkPayment =new CheckPayment();
		checkPayment.setAccount(account);
		checkPayment.setCompanyCode(companyCode);
		checkPayment.setSalePointType(salePointType);
		checkPayment.setServiceCode(serviceCode);
		checkPayment.setSpecial1(special1);
		checkPayment.setSpecial2(special2);
		checkPayment.setTransactionDate(transactionDate);
		checkPayment.setTransactionId(transactionId);
		checkPayment.setUserId(userId);
		checkPayment.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		CheckPaymentResponse checkPaymentResponse =(CheckPaymentResponse) getWebServiceTemplate()
//				.marshalSendAndReceive("https://test.paypoint.md:4444/PayPointWS/PayPointMSOperations.asmx?WSDL", checkPayment, 
//						new SoapActionCallback("http://tempuri.org/CheckPayment"));
		
		CheckPaymentResponse checkPaymentResponse =(CheckPaymentResponse) getWebServiceTemplate()
				.marshalSendAndReceive(url, checkPayment, 
						new SoapActionCallback("http://tempuri.org/CheckPayment"));
		
		return checkPaymentResponse.getCheckPaymentResult();
	}
	
	public void printCheckPayment(String response){
		log.info("CheckPayment--> " + response);
	}

	@Override
	public String executePayment(int companyCode, int serviceCode, String account, String special1, String special2,
			String transactionDate, long transactionId, long refStan, long amount, String billNumber, String userId,
			String userPassword, int salePointType, String url) {
		
		ExecutePayment executePayment = new ExecutePayment();
		executePayment.setAccount(account);
		executePayment.setAmount(amount);
		executePayment.setBillNumber(billNumber);
		executePayment.setCompanyCode(companyCode);
		executePayment.setRefStan(refStan);
		executePayment.setSalePointType(salePointType);
		executePayment.setServiceCode(serviceCode);
		executePayment.setSpecial1(special1);
		executePayment.setSpecial2(special2);
		executePayment.setTransactionDate(transactionDate);
		executePayment.setTransactionId(transactionId);
		executePayment.setUserId(userId);
		executePayment.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExecutePaymentResponse executePaymentResponse =(ExecutePaymentResponse)  getWebServiceTemplate()
				.marshalSendAndReceive(url, executePayment, 
						new SoapActionCallback( "http://tempuri.org/ExecutePayment"));
		
		return executePaymentResponse.getExecutePaymentResult();
	}
	
	public void printExecutePayment(String response){
		log.info("ExecutePayment--> " + response);
	}

	@Override
	public String executePaymentEx(int companyCode, int serviceCode, String account, String special1, String special2,
			String transactionDate, long transactionId, long refStan, long amount, String billNumber, String userId,
			String userPassword, int salePointType, int paySourceType, int currencyCode) {

		ExecutePaymentEx executePaymentEx = new ExecutePaymentEx();
		executePaymentEx.setAccount(account);
		executePaymentEx.setAmount(amount);
		executePaymentEx.setBillNumber(billNumber);
		executePaymentEx.setCompanyCode(companyCode);
		executePaymentEx.setCurrencyCode(currencyCode);
		executePaymentEx.setPaySourceType(paySourceType);
		executePaymentEx.setRefStan(refStan);
		executePaymentEx.setSalePointType(salePointType);
		executePaymentEx.setServiceCode(serviceCode);
		executePaymentEx.setSpecial1(special1);
		executePaymentEx.setSpecial2(special2);
		executePaymentEx.setTransactionDate(transactionDate);
		executePaymentEx.setTransactionId(transactionId);
		executePaymentEx.setUserId(userId);
		executePaymentEx.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExecutePaymentExResponse executePaymentExResponse = (ExecutePaymentExResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", executePaymentEx, 
						new SoapActionCallback( "http://tempuri.org/ExecutePaymentEx"));
		
		return executePaymentExResponse.getExecutePaymentExResult();
	}
	
	public void printExecutePaymentEx(String response){
		log.info("ExecutePaymentEx--> " + response);
	}

	@Override
	public String addCancelRequest(int companyCode, long refStan, String transactionDate, long transactionId,
			String userId, String userPassword, int salePointType) {

		AddCancelRequest addCancelRequest= new AddCancelRequest();
		addCancelRequest.setCompanyCode(companyCode);
		addCancelRequest.setRefStan(refStan);
		addCancelRequest.setSalePointType(salePointType);
		addCancelRequest.setTransactionDate(transactionDate);
		addCancelRequest.setTransactionId(transactionId);
		addCancelRequest.setUserId(userId);
		addCancelRequest.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AddCancelRequestResponse addCancelRequestResponse = (AddCancelRequestResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", addCancelRequest, 
						new SoapActionCallback("http://tempuri.org/AddCancelRequest"));
		
		return addCancelRequestResponse.getAddCancelRequestResult();
	}
	
	public void printAddCancelRequest(String response){
		log.info("AddCancelRequest--> " + response);
	}

	@Override
	public String addCancelRequestEx(int companyCode, long refStan, String transactionDate, long transactionId,
			String userId, String userPassword, int salePointType, String note) {
		
		AddCancelRequestEx cancelRequestEx = new AddCancelRequestEx();
		cancelRequestEx.setCompanyCode(companyCode);
		cancelRequestEx.setNote(note);
		cancelRequestEx.setRefStan(refStan);
		cancelRequestEx.setSalePointType(salePointType);
		cancelRequestEx.setTransactionDate(transactionDate);
		cancelRequestEx.setTransactionId(transactionId);
		cancelRequestEx.setUserId(userId);
		cancelRequestEx.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AddCancelRequestExResponse cancelRequestExResponse =(AddCancelRequestExResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", cancelRequestEx, 
						new SoapActionCallback("http://tempuri.org/AddCancelRequestEx"));
		
		return cancelRequestExResponse.getAddCancelRequestExResult();
	}
	
	public void printAddCancelRequestEx(String response){
		log.info("AddCancelRequestEx--> " + response);
	}

	@Override
	public String getCompanyInfo(int companyCode, int serviceCode, String userId, String userPassword,
			int salePointType) {
		
		GetCompanyInfo companyInfo = new GetCompanyInfo();
		companyInfo.setCompanyCode(companyCode);
		companyInfo.setServiceCode(serviceCode);
		companyInfo.setUserId(userId);
		companyInfo.setUserPassword(userPassword);
		companyInfo.setSalePointType(salePointType);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GetCompanyInfoResponse companyInfoResponse = (GetCompanyInfoResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", companyInfo,
						new SoapActionCallback("http://tempuri.org/GetCompanyInfo"));
		
		return companyInfoResponse.getGetCompanyInfoResult();
		
	}
	
	public void printCompanyInfoResponse(String response){
		log.info("GetCompanyInfoResult --> " + response);
	}

	@Override
	public String getTransactionReport(String userLogin, String userPassword, String dealerName, String userName,
			String startDate, String endDate, long refStan, int salePointType, int status) {

		GetTransactionReport getTransactionReport = new GetTransactionReport();
		getTransactionReport.setDealerName(dealerName);
		getTransactionReport.setEndDate(endDate);
		getTransactionReport.setRefStan(refStan);
		getTransactionReport.setSalePointType(salePointType);
		getTransactionReport.setStartDate(startDate);
		getTransactionReport.setStatus(status);
		getTransactionReport.setUserLogin(userLogin);
		getTransactionReport.setUserName(userName);
		getTransactionReport.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GetTransactionReportResponse getTransactionReportResponse =(GetTransactionReportResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", getTransactionReport,
						new SoapActionCallback("http://tempuri.org/GetTransactionReport"));
		
		return getTransactionReportResponse.getGetTransactionReportResult();
	}
	
	public void printTransactionReport(String response){
		log.info("GetTransactionReportResult --> " + response);
	}


	@Override
	public String getTransactionReportSummary(String userLogin, String userPassword, String dealerName, String userName,
			String startDate, String endDate, long refStan, int salePointType, int status) {
		
		GetTransactionReportSummary getTransactionReportSummary =new GetTransactionReportSummary();
		
		getTransactionReportSummary.setDealerName(dealerName);
		getTransactionReportSummary.setEndDate(endDate);
		getTransactionReportSummary.setRefStan(refStan);
		getTransactionReportSummary.setSalePointType(salePointType);
		getTransactionReportSummary.setStartDate(startDate);
		getTransactionReportSummary.setStatus(status);
		getTransactionReportSummary.setUserLogin(userLogin);
		getTransactionReportSummary.setUserName(userName);
		getTransactionReportSummary.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GetTransactionReportSummaryResponse getTransactionReportSummaryResponse =(GetTransactionReportSummaryResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", getTransactionReportSummary,
						new SoapActionCallback("http://tempuri.org/GetTransactionReportSummary"));
		
		return getTransactionReportSummaryResponse.getGetTransactionReportSummaryResult();
	}
	
	public void printTransactionReportSummary(String response){
		log.info("GetTransactionReportSummaryResult --> " + response);
	}

	@Override
	public String getTransaction(String userLogin, String userPassword, long stan, long refStan, String key,
			String billNumber, String url) {
		
		GetTransaction getTransaction =new GetTransaction();
		getTransaction.setBillNumber(billNumber);
		getTransaction.setKey(key);
		getTransaction.setRefStan(refStan);
		getTransaction.setStan(stan);
		getTransaction.setUserLogin(userLogin);
		getTransaction.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GetTransactionResponse getTransactionResponse = (GetTransactionResponse) getWebServiceTemplate()
				.marshalSendAndReceive(url, getTransaction,
						new SoapActionCallback("http://tempuri.org/GetTransaction"));
		
		return getTransactionResponse.getGetTransactionResult();
	}
	
	public void printTransaction(String response){
		log.info("GetTransactionResult --> " + response);
	}

	@Override
	public String getTransactionEx(String userLogin, String userPassword, long stan, long refStan, String key,
			String billNumber) {
		
		GetTransactionEx getTransactionEx =new GetTransactionEx();
		getTransactionEx.setBillNumber(billNumber);
		getTransactionEx.setKey(key);
		getTransactionEx.setRefStan(refStan);
		getTransactionEx.setStan(stan);
		getTransactionEx.setUserLogin(userLogin);
		getTransactionEx.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GetTransactionExResponse getTransactionExResponse =(GetTransactionExResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", getTransactionEx,
						new SoapActionCallback("http://tempuri.org/GetTransactionEx"));
		
		return getTransactionExResponse.getGetTransactionExResult();
	}
	
	public void printTransactionEx(String response){
		log.info("GetTransactionExResult --> " + response);
	}

	@Override
	public String getTransactionByExternal(String userLogin, String userPassword, long stanEx, String date) {
		
		GetTransactionByExternal getTransactionByExternal = new GetTransactionByExternal();
		getTransactionByExternal.setDate(date);
		getTransactionByExternal.setStanEx(stanEx);
		getTransactionByExternal.setUserLogin(userLogin);
		getTransactionByExternal.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GetTransactionByExternalResponse getTransactionByExternalResponse = (GetTransactionByExternalResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", getTransactionByExternal,
						new SoapActionCallback("http://tempuri.org/GetTransactionByExternal"));
		
		return getTransactionByExternalResponse.getGetTransactionByExternalResult();
		
	}
	
	public void printTransactionByExternal(String response){
		log.info("GetTransactionByExternalResult --> " + response);
	}

	@Override
	public String dailyReconciliation(String userLogin, String userPassword, int dealerId, int companyId, String day,
			long totalSum, int totalCount) {
		
		DailyReconciliation dailyReconciliation = new DailyReconciliation();
		dailyReconciliation.setCompanyId(companyId);
		dailyReconciliation.setDay(day);
		dailyReconciliation.setDealerId(dealerId);
		dailyReconciliation.setTotalCount(totalCount);
		dailyReconciliation.setTotalSum(totalSum);
		dailyReconciliation.setUserLogin(userLogin);
		dailyReconciliation.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DailyReconciliationResponse dailyReconciliationResponse = (DailyReconciliationResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", dailyReconciliation,
						new SoapActionCallback("http://tempuri.org/DailyReconciliation"));
		
		return dailyReconciliationResponse.getDailyReconciliationResult();
	}
	
	public void printDailyReconciliation(String response){
		log.info("DailyReconciliation --> " + response);
	}

	@Override
	public String retransmit(int companyCode, String account, String transactionDate, long transactionId, long refStan,
			String userId, String userPassword, int salePointType, String note) {
		
		Retransmit retransmit = new Retransmit();
		retransmit.setAccount(account);
		retransmit.setCompanyCode(companyCode);
		retransmit.setNote(note);
		retransmit.setRefStan(refStan);
		retransmit.setSalePointType(salePointType);
		retransmit.setTransactionDate(transactionDate);
		retransmit.setTransactionId(transactionId);
		retransmit.setUserId(userId);
		retransmit.setUserPassword(userPassword);
		
		try {
			bypassSslCertificate();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RetransmitResponse retransmitResponse = (RetransmitResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://paypoint.com.np/PayPointWS/PayPointMSOperations.asmx?WSDL", retransmit,
						new SoapActionCallback("http://tempuri.org/Retransmit"));
		
		return retransmitResponse.getRetransmitResult();
	}
	
	public void printRetransmit(String response){
		log.info("Retransmit--> " + response);
	}

}
