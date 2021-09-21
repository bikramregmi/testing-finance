package com.wallet.serviceprovider.dishhome;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class DishMediaServiceSoap extends WebServiceGatewaySupport implements DishMediaServiceSoapInterface {
	
	private static final Logger log = LoggerFactory.getLogger(DishMediaServiceSoap.class);

	@Override
	public AuthenticationResult ping(String username) {
		
		Ping ping = new Ping();
		ping.setUsername(username);
		PingResponse pingResponse = (PingResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						ping, new SoapActionCallback("http://tempuri.org/Ping"));
		
		
		return pingResponse.getPingResult();
	}
	
	public void printAuthenticationResponseResult(AuthenticationResult response){
		log.info("Message--> " + response.getMessage());
		log.info("Authenticated--> " + response.isAuthenticated());
		log.info("StatusCode--> " + response.getStatusCode());
		log.info("Id--> " + response.getId());
		log.info("Token--> " + response.getToken());
	}

	
	@Override
	public void pingJSON(String username) {
		
		PingJSON pingJSON = new PingJSON();
		pingJSON.setUsername(username);
		
		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				pingJSON, new SoapActionCallback("http://tempuri.org/PingJSON"));

	}

	@Override
	public RTNCheckResponseResult rtnCheck(String username, String password, String ani, String optionalToken) {

		RTNCheck checkRTN = new RTNCheck();
		checkRTN.setUsername(username);
		checkRTN.setPassword(password);
		checkRTN.setANI(ani);
		checkRTN.setOptionalToken(optionalToken);
		
		RTNCheckResponse checkRTNResponse = (RTNCheckResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						checkRTN, new SoapActionCallback("http://tempuri.org/RTNCheck"));
		
		
		return checkRTNResponse.getRTNCheckResult();
	}

	public void printRTNCheckResponseResult(RTNCheckResponseResult response){
		log.info("RTN1--> " + response.getRTN1());
		log.info("RTN2--> " + response.getRTN2());
		log.info("RTN3--> " + response.getRTN3());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("CustomerName--> " + response.getCustomerName());
		log.info("Language--> " + response.getLanguage());
		log.info("Package--> " + response.getPackage());
		log.info("Balance--> " + response.getBalance());
		log.info("ExpiryDate--> " + response.getExpiryDate());
		log.info("Region--> " + response.getRegion());
		log.info("Zone--> " + response.getZone());
		log.info("District--> " + response.getDistrict());
		log.info("MunicipalityorVDC--> " + response.getMunicipalityorVDC());
		log.info("CustomerType--> " + response.getCustomerType());
		log.info("CustomerStatus--> " + response.getCustomerStatus());
		log.info("CustomerId--> " + response.getCustomerId());
		log.info("IsRTN--> " + response.isIsRTN());
		log.info("IsDealer--> " + response.isIsDealer());
		
		
	}
	
	@Override
	public void rtnCheckJSON(String username, String password, String ani, String optionalToken) {
		
		RTNCheckJSON checkRTNJSON = new RTNCheckJSON();
		checkRTNJSON.setUsername(username);
		checkRTNJSON.setPassword(password);
		checkRTNJSON.setANI(ani);
		checkRTNJSON.setOptionalToken(optionalToken);
		
		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				checkRTNJSON, new SoapActionCallback("http://tempuri.org/RTNCheckJSON"));
	
	}

	@Override
	public RTNCheckResponseResult customerIDCheck(String username, String password, int customerID,
			String optionalToken) {
		
		CustomerIDCheck customerIDCheck = new CustomerIDCheck();
		customerIDCheck.setUsername(username);
		customerIDCheck.setPassword(password);
		customerIDCheck.setCustomerID(customerID);
		customerIDCheck.setOptionalToken(optionalToken);
		

		CustomerIDCheckResponse customerIDCheckResponse = (CustomerIDCheckResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						customerIDCheck, new SoapActionCallback("http://tempuri.org/CustomerIDCheck"));
		
		
		return customerIDCheckResponse.getCustomerIDCheckResult();
	}
	

	@Override
	public void customerIDCheckJSON(String username, String password, int customerID, String optionalToken) {

		CustomerIDCheckJSON customerIDCheckJSON = new CustomerIDCheckJSON();
		customerIDCheckJSON.setUsername(username);
		customerIDCheckJSON.setPassword(password);
		customerIDCheckJSON.setCustomerID(customerID);
		customerIDCheckJSON.setOptionalToken(optionalToken);
		

		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				customerIDCheckJSON, new SoapActionCallback("http://tempuri.org/CustomerIDCheckJSON"));
		
	}

	@Override
	public PackageInfoResponseResult packageInfo(String username, String password, int customerID,
			String optionalToken) {
		
		PackageInfo packageInfo = new PackageInfo();
		packageInfo.setUsername(username);
		packageInfo.setPassword(password);
		packageInfo.setCustomerID(customerID);
		packageInfo.setOptionalToken(optionalToken);
		
		PackageInfoResponse packageInfoResponse = (PackageInfoResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						packageInfo, new SoapActionCallback("http://tempuri.org/PackageInfo"));
		
		
		return packageInfoResponse.getPackageInfoResult();
	}
	
	
	public void printPackageInfoResponseResult(PackageInfoResponseResult response){
		log.info("CurrentPackageName--> " + response.getCurrentPackageName());
		log.info("SubscriptionCost--> " + response.getSubscriptionCost());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("UpgradePackageOptions--> " + response.getUpgradePackageOptions());
		log.info("UpgradeSubcriptionCosts--> " + response.getUpgradeSubcriptionCosts());
		
	}

	@Override
	public void packageInfoJSON(String username, String password, int customerID, String optionalToken) {
		
		PackageInfoJSON packageInfoJSON = new PackageInfoJSON();
		packageInfoJSON.setUsername(username);
		packageInfoJSON.setPassword(password);
		packageInfoJSON.setCustomerID(customerID);
		packageInfoJSON.setOptionalToken(optionalToken);
		

		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				packageInfoJSON, new SoapActionCallback("http://tempuri.org/PackageInfoJSON"));
		
	}

	@Override
	public PackageChangeResponseResult packageChange(String username, String password, int customerID,
			String packageName, String optionalToken) {
		
		PackageChange packageChange = new PackageChange();
		packageChange.setUsername(username);
		packageChange.setPassword(password);
		packageChange.setCustomerID(customerID);
		packageChange.setOptionalToken(optionalToken);
		
		PackageChangeResponse packageChangeResponse = (PackageChangeResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						packageChange, new SoapActionCallback("http://tempuri.org/PackageChange"));
		
		
		
		return packageChangeResponse.getPackageChangeResult();
	}

	public void printPackageChangeResponseResult(PackageChangeResponseResult response){
		log.info("Customer Status--> " + response.getCustomerStatus());
		log.info("UpdatedBalance--> " + response.getUpdatedBalance());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("CustomerStatus--> " + response.getCustomerStatus());
		log.info("Expiry Date--> " + response.getExpiryDate());
		
	}
	
	@Override
	public void packageChangeJSON(String username, String password, int customerID, String packageName,
			String optionalToken) {
		
		PackageChangeJSON packageChangeJSON = new PackageChangeJSON();
		packageChangeJSON.setUsername(username);
		packageChangeJSON.setPassword(password);
		packageChangeJSON.setCustomerID(customerID);
		packageChangeJSON.setOptionalToken(optionalToken);
		
		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				packageChangeJSON, new SoapActionCallback("http://tempuri.org/PackageChangeJSON"));

	}

	@Override
	public BalanceEnquiryResponseResult balanceEnquiry(String username, String password, int customerID,
			String optionalToken) {
		
		BalanceEnquiry balanceEnquiry = new BalanceEnquiry();
		balanceEnquiry.setUsername(username);
		balanceEnquiry.setPassword(password);
		balanceEnquiry.setCustomerID(customerID);
		balanceEnquiry.setOptionalToken(optionalToken);
		
		BalanceEnquiryResponse balanceEnquiryResponse = (BalanceEnquiryResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						balanceEnquiry, new SoapActionCallback("http://tempuri.org/BalanceEnquiry"));
		
		return balanceEnquiryResponse.getBalanceEnquiryResult();
	}
	
	public void printBalanceEnquiryResponseResult(BalanceEnquiryResponseResult response){
		log.info("Customer Status--> " + response.getCustomerStatus());
		log.info("Package--> " + response.getPackage());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("Balance--> " + response.getBalance());
		log.info("Expiry Date--> " + response.getExpiryDate());
		
	}

	@Override
	public void balanceEnquiryJSON(String username, String password, int customerID, String optionalToken) {
		
		BalanceEnquiryJSON balanceEnquiryJSON = new BalanceEnquiryJSON();
		balanceEnquiryJSON.setUsername(username);
		balanceEnquiryJSON.setPassword(password);
		balanceEnquiryJSON.setCustomerID(customerID);
		balanceEnquiryJSON.setOptionalToken(optionalToken);
		
		getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						balanceEnquiryJSON, new SoapActionCallback("http://tempuri.org/BalanceEnquiryJSON"));
		
		
	}

	@Override
	public RechargeResponseResult recharge(String username, String password, int customerID, BigDecimal rechargeAmount,
			String optionalToken) {
		Recharge recharge = new Recharge();
		recharge.setUsername(username);
		recharge.setPassword(password);
		recharge.setCustomerID(customerID);
		recharge.setOptionalToken(optionalToken);
		recharge.setRechargeAmount(rechargeAmount);
		
		RechargeResponse rechargeResponse = (RechargeResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						recharge, new SoapActionCallback("http://tempuri.org/Recharge"));
		
		return rechargeResponse.getRechargeResult();
	}

	public void printRechargeResponseResult(RechargeResponseResult response){
		log.info("Customer Status--> " + response.getCustomerStatus());
		log.info("TransactionID--> " + response.getTransactionID());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("UpdatedBalance--> " + response.getUpdatedBalance());
		log.info("Expiry Date--> " + response.getExpiryDate());
	}
	
	@Override
	public void rechargeJSON(String username, String password, int customerID, BigDecimal rechargeAmount,
			String optionalToken) {
		RechargeJSON rechargeJSON = new RechargeJSON();
		rechargeJSON.setUsername(username);
		rechargeJSON.setPassword(password);
		rechargeJSON.setCustomerID(customerID);
		rechargeJSON.setOptionalToken(optionalToken);
		rechargeJSON.setRechargeAmount(rechargeAmount);
		
		RechargeJSONResponse rechargeJSONResponse = (RechargeJSONResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						rechargeJSON, new SoapActionCallback("http://tempuri.org/RechargeJSON"));
	}

	@Override
	public GetLanguagesResponseResult getLanguages(String username, String password, String optionalToken) {
		GetLanguages getLanguages = new GetLanguages();
		getLanguages.setUsername(username);
		getLanguages.setPassword(password);
		getLanguages.setOptionalToken(optionalToken);
		
		GetLanguagesResponse getLanguagesResponse = (GetLanguagesResponse)  getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						getLanguages, new SoapActionCallback("http://tempuri.org/GetLanguages"));
		
		return getLanguagesResponse.getGetLanguagesResult();
	}
	
	public void printLanguageResponseResult(GetLanguagesResponseResult response){
		log.info("LanguageNames--> " + response.getLanguageNames());
		log.info("LanguageKeys--> " + response.getLanguageKeys());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("Count--> " + response.getCount());
	}

	@Override
	public void getLanguagesJSON(String username, String password, String optionalToken) {
		
		GetLanguagesJSON getLanguagesJSON = new GetLanguagesJSON();
		getLanguagesJSON.setUsername(username);
		getLanguagesJSON.setPassword(password);
		getLanguagesJSON.setOptionalToken(optionalToken);
		
		 getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						getLanguagesJSON, new SoapActionCallback("http://tempuri.org/GetLanguagesJSON"));
		
	}

	@Override
	public UpdateLanguageResponseResult updateLanguage(String username, String password, int customerID,
			String languageKey, String optionalToken) {
		UpdateLanguage updateLanguage = new UpdateLanguage();
		updateLanguage.setUsername(username);
		updateLanguage.setPassword(password);
		updateLanguage.setCustomerID(customerID);
		updateLanguage.setOptionalToken(optionalToken);
		updateLanguage.setLanguageKey(languageKey);
		

		UpdateLanguageResponse getLanguagesResponse = (UpdateLanguageResponse)  getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						updateLanguage, new SoapActionCallback("http://tempuri.org/UpdateLanguage"));
		
		return getLanguagesResponse.getUpdateLanguageResult();
	}

	public void printUpdateLanguageResponseResult(UpdateLanguageResponseResult response){
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
	}
	
	@Override
	public void updateLanguageJSON(String username, String password, int customerID, String languageKey,
			String optionalToken) {
		UpdateLanguageJSON updateLanguageJSON = new UpdateLanguageJSON();
		updateLanguageJSON.setUsername(username);
		updateLanguageJSON.setPassword(password);
		updateLanguageJSON.setCustomerID(customerID);
		updateLanguageJSON.setOptionalToken(optionalToken);
		updateLanguageJSON.setLanguageKey(languageKey);
		
		 getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						updateLanguageJSON, new SoapActionCallback("http://tempuri.org/UpdateLanguageJSON"));
		
	}

	@Override
	public RechargeResponseResult rechargeBySTBIDorCASIDorCHIPID(String username, String password,
			String stbiDorCASIDorCHIPID, BigDecimal rechargeAmount, String optionalToken) {
		RechargeBySTBIDorCASIDorCHIPID rechargeBySTBIDorCASIDorCHIPID = new RechargeBySTBIDorCASIDorCHIPID();
		rechargeBySTBIDorCASIDorCHIPID.setUsername(username);
		rechargeBySTBIDorCASIDorCHIPID.setPassword(password);
		rechargeBySTBIDorCASIDorCHIPID.setSTBIDorCASIDorCHIPID(stbiDorCASIDorCHIPID);
		rechargeBySTBIDorCASIDorCHIPID.setOptionalToken(optionalToken);
		rechargeBySTBIDorCASIDorCHIPID.setRechargeAmount(rechargeAmount);
		
		RechargeBySTBIDorCASIDorCHIPIDResponse rechargeBySTBIDorCASIDorCHIPIDResponse = (RechargeBySTBIDorCASIDorCHIPIDResponse)  getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						rechargeBySTBIDorCASIDorCHIPID, new SoapActionCallback("http://tempuri.org/RechargeBySTBIDorCASIDorCHIPID"));
		
		return rechargeBySTBIDorCASIDorCHIPIDResponse.getRechargeBySTBIDorCASIDorCHIPIDResult();
	}
	
	public void printRechargeBySTBIDorCASIDorCHIPIDResponseResult(RechargeResponseResult response){
		log.info("Customer Status--> " + response.getCustomerStatus());
		log.info("TransactionID--> " + response.getTransactionID());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("UpdatedBalance--> " + response.getUpdatedBalance());
		log.info("Expiry Date--> " + response.getExpiryDate());
	}

	@Override
	public void rechargeBySTBIDorCASIDorCHIPIDJSON(String username, String password, String stbiDorCASIDorCHIPID,
			BigDecimal rechargeAmount, String optionalToken) {
		
		RechargeBySTBIDorCASIDorCHIPIDJSON rechargeBySTBIDorCASIDorCHIPIDJSON = new RechargeBySTBIDorCASIDorCHIPIDJSON();
		rechargeBySTBIDorCASIDorCHIPIDJSON.setUsername(username);
		rechargeBySTBIDorCASIDorCHIPIDJSON.setPassword(password);
		rechargeBySTBIDorCASIDorCHIPIDJSON.setSTBIDorCASIDorCHIPID(stbiDorCASIDorCHIPID);
		rechargeBySTBIDorCASIDorCHIPIDJSON.setOptionalToken(optionalToken);
		rechargeBySTBIDorCASIDorCHIPIDJSON.setRechargeAmount(rechargeAmount);
		
		 getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						rechargeBySTBIDorCASIDorCHIPIDJSON, new SoapActionCallback("org.tempuri.RechargeBySTBIDorCASIDorCHIPIDJSON"));
		
		
	}
	
	
	@Override
	public TransactionHistoryResponseResult getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPID(String username,
			String password, String stbiDorCASIDorCHIPID, String optionalToken) {
		GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPID lastPinlessTopupTransactionForSTBIDorCASIDorCHIPID =  new GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPID();
		
		lastPinlessTopupTransactionForSTBIDorCASIDorCHIPID.setUsername(username);
		lastPinlessTopupTransactionForSTBIDorCASIDorCHIPID.setPassword(password);
		lastPinlessTopupTransactionForSTBIDorCASIDorCHIPID.setSTBIDorCASIDorCHIPID(stbiDorCASIDorCHIPID);
		lastPinlessTopupTransactionForSTBIDorCASIDorCHIPID.setOptionalToken(optionalToken);
		
		GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResponse lastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResponse = (GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResponse)  getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						lastPinlessTopupTransactionForSTBIDorCASIDorCHIPID, new SoapActionCallback("http://tempuri.org/GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPID"));
		
		return lastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResponse.getGetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDResult();
	}
	
	public void printTransactionHistoryResponseResult(TransactionHistoryResponseResult response){
		log.info("TransactionDate--> " + response.getTransactionDate());
		log.info("TransactionID--> " + response.getTransactionID());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("TransactionAmount--> " + response.getTransactionAmount());
		log.info("Description--> " + response.getDescription());
	}


	@Override
	public void getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON(String username, String password,
			String stbiDorCASIDorCHIPID, String optionalToken) {
		GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON lastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON =  new GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON();
		
		lastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON.setUsername(username);
		lastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON.setPassword(password);
		lastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON.setSTBIDorCASIDorCHIPID(stbiDorCASIDorCHIPID);
		lastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON.setOptionalToken(optionalToken);
		
		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				lastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON, new SoapActionCallback("http://tempuri.org/GetLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON"));

	}

	@Override
	public CheckTransactionStatusResult getStatusbyTransactionID(String username, String password, int transactionID,
			String optionalToken) {
		
		GetStatusbyTransactionID getStatusbyTransactionID = new GetStatusbyTransactionID();
		getStatusbyTransactionID.setUsername(username);
		getStatusbyTransactionID.setPassword(password);
		getStatusbyTransactionID.setTransactionID(transactionID);
		getStatusbyTransactionID.setOptionalToken(optionalToken);

		GetStatusbyTransactionIDResponse getStatusbyTransactionIDResponse = (GetStatusbyTransactionIDResponse)  getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						getStatusbyTransactionID, new SoapActionCallback("http://tempuri.org/GetStatusbyTransactionID"));
		
		return getStatusbyTransactionIDResponse.getGetStatusbyTransactionIDResult();
	}
	
	public void printCheckTransactionStatusResponseResult(CheckTransactionStatusResult response){
		log.info("TransactionDate--> " + response.getTransactionDate());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("pending--> " + response.pending);
	}


	@Override
	public void getStatusbyTransactionIDJSON(String username, String password, int transactionID,
			String optionalToken) {

		GetStatusbyTransactionIDJSON getStatusbyTransactionIDJSON = new GetStatusbyTransactionIDJSON();
		getStatusbyTransactionIDJSON.setUsername(username);
		getStatusbyTransactionIDJSON.setPassword(password);
		getStatusbyTransactionIDJSON.setTransactionID(transactionID);
		getStatusbyTransactionIDJSON.setOptionalToken(optionalToken);
		
		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				getStatusbyTransactionIDJSON, new SoapActionCallback("http://tempuri.org/GetStatusbyTransactionIDJSON"));
	}

	@Override
	public RTNCheckResponseResult getCustomerDetailBySTBIDorCASIDorCHIPID(String username, String password,
			String stbiDorCASIDorCHIPID, String optionalToken) {

		GetCustomerDetailBySTBIDorCASIDorCHIPID getCustomerDetailBySTBIDorCASIDorCHIPID = new GetCustomerDetailBySTBIDorCASIDorCHIPID();
		getCustomerDetailBySTBIDorCASIDorCHIPID.setUsername(username);
		getCustomerDetailBySTBIDorCASIDorCHIPID.setPassword(password);
		getCustomerDetailBySTBIDorCASIDorCHIPID.setSTBIDorCASIDorCHIPID(stbiDorCASIDorCHIPID);
		getCustomerDetailBySTBIDorCASIDorCHIPID.setOptionalToken(optionalToken);
		

		GetCustomerDetailBySTBIDorCASIDorCHIPIDResponse getCustomerDetailBySTBIDorCASIDorCHIPIDResponse = (GetCustomerDetailBySTBIDorCASIDorCHIPIDResponse)  getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						getCustomerDetailBySTBIDorCASIDorCHIPID, new SoapActionCallback("http://tempuri.org/GetCustomerDetailBySTBIDorCASIDorCHIPID"));
		
		return getCustomerDetailBySTBIDorCASIDorCHIPIDResponse.getGetCustomerDetailBySTBIDorCASIDorCHIPIDResult();
	}

	public void printRTNCheckResponseResponseResult(RTNCheckResponseResult response){
		
		log.info("RTN1--> " + response.getRTN1());
		log.info("RTN2--> " + response.getRTN2());
		log.info("RTN3--> " + response.getRTN3());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("CustomerName--> " + response.getCustomerName());
		log.info("Language--> " + response.getLanguage());
		log.info("Package--> " + response.getPackage());
		log.info("Balance--> " + response.getBalance());
		log.info("ExpiryDate--> " + response.getExpiryDate());
		log.info("Region--> " + response.getRegion());
		log.info("Zone--> " + response.getZone());
		log.info("District--> " + response.getDistrict());
		log.info("MunicipalityorVDC--> " + response.getMunicipalityorVDC());
		log.info("CustomerType--> " + response.getCustomerType());
		log.info("CustomerStatus--> " + response.getCustomerStatus());
		log.info("CustomerId--> " + response.getCustomerId());
		log.info("IsRTN--> " + response.isRTN);
		log.info("IsDealer--> " + response.isDealer);
		
	}

	
	
	@Override
	public void getCustomerDetailBySTBIDorCASIDorCHIPIDJSON(String username, String password,
			String stbiDorCASIDorCHIPID, String optionalToken) {
		

		GetCustomerDetailBySTBIDorCASIDorCHIPIDJSON getCustomerDetailBySTBIDorCASIDorCHIPIDJSON = new GetCustomerDetailBySTBIDorCASIDorCHIPIDJSON();
		getCustomerDetailBySTBIDorCASIDorCHIPIDJSON.setUsername(username);
		getCustomerDetailBySTBIDorCASIDorCHIPIDJSON.setPassword(password);
		getCustomerDetailBySTBIDorCASIDorCHIPIDJSON.setSTBIDorCASIDorCHIPID(stbiDorCASIDorCHIPID);
		getCustomerDetailBySTBIDorCASIDorCHIPIDJSON.setOptionalToken(optionalToken);
		
		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				getCustomerDetailBySTBIDorCASIDorCHIPIDJSON, new SoapActionCallback("http://tempuri.org/GetCustomerDetailBySTBIDorCASIDorCHIPIDJSON"));
	}

	@Override
	public VoucherResult lookupVoucher(String username, String password, String voucherNumber, String optionalToken) {
		
		LookupVoucher lookupVoucher = new LookupVoucher();
		lookupVoucher.setUsername(username);
		lookupVoucher.setPassword(password);
		lookupVoucher.setVoucherNumber(voucherNumber);
		lookupVoucher.setOptionalToken(optionalToken);
		
		LookupVoucherResponse lookupVoucherResponse = (LookupVoucherResponse)  getWebServiceTemplate()
				.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
						lookupVoucher, new SoapActionCallback("http://tempuri.org/LookupVoucher"));
		
		
		return lookupVoucherResponse.getLookupVoucherResult();
	}

	public void printVoucherResponseResult(VoucherResult response){
		log.info("SerialNumber--> " + response.getSerialNumber());
		log.info("Status--> " + response.getStatus());
		log.info("Response Code--> " + response.getResponseCode());
		log.info("RedeemedCustomerID--> " + response.getRedeemedCustomerID());
		log.info("RedeemedDeviceSerialNumber--> " + response.getRedeemedDeviceSerialNumber());
		log.info("Denomination--> " + response.getDenomination());
		log.info("Response Description--> " + response.getResponseDescription());
		log.info("Token--> " + response.getToken());
		log.info("RedeemedDate--> " + response.getRedeemedDate());
		
	}
	
	@Override
	public void lookupVoucherJSON(String username, String password, String voucherNumber, String optionalToken) {

		LookupVoucherJSON lookupVoucherJSON = new LookupVoucherJSON();
		lookupVoucherJSON.setUsername(username);
		lookupVoucherJSON.setPassword(password);
		lookupVoucherJSON.setVoucherNumber(voucherNumber);
		lookupVoucherJSON.setOptionalToken(optionalToken);
		
		getWebServiceTemplate()
		.marshalSendAndReceive("http://110.44.124.37:1013/dishmediaservice.asmx?wsdl",
				lookupVoucherJSON, new SoapActionCallback("http://tempuri.org/LookupVoucherJSON"));
	
	}

}
