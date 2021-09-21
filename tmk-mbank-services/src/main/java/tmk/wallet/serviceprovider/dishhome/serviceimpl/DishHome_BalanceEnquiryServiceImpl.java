package tmk.wallet.serviceprovider.dishhome.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.serviceprovider.dishhome.AuthenticationResult;
import com.wallet.serviceprovider.dishhome.BalanceEnquiryResponseResult;
import com.wallet.serviceprovider.dishhome.CheckTransactionStatusResult;
import com.wallet.serviceprovider.dishhome.DishMediaServiceSoap;
import com.wallet.serviceprovider.dishhome.GetLanguagesResponseResult;
import com.wallet.serviceprovider.dishhome.PackageChangeResponseResult;
import com.wallet.serviceprovider.dishhome.PackageInfoResponseResult;
import com.wallet.serviceprovider.dishhome.RTNCheckResponseResult;
import com.wallet.serviceprovider.dishhome.RechargeResponseResult;
import com.wallet.serviceprovider.dishhome.TransactionHistoryResponseResult;
import com.wallet.serviceprovider.dishhome.UpdateLanguageResponseResult;
import com.wallet.serviceprovider.dishhome.VoucherResult;
import com.wallet.serviceprovider.dishhome.service.DishHome_BalanceEnquiryService;

@Service
public class DishHome_BalanceEnquiryServiceImpl implements DishHome_BalanceEnquiryService{

	@Autowired
	DishMediaServiceSoap dishMediaServiceSoap;
	
	@Override
	public HashMap<String, String> balanceEnquiry() {
		HashMap<String, String> hashResponse = new HashMap<String, String>();
		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String optionalToken = null;
		try{
		BalanceEnquiryResponseResult balanceEnquiryResult = dishMediaServiceSoap.balanceEnquiry(username,password,customerID,optionalToken);
		
		if (balanceEnquiryResult != null) {
			hashResponse.put("status", "success");
		hashResponse.put("Customer Status" , ""+ balanceEnquiryResult.getCustomerStatus());
		hashResponse.put("Package" , ""+ balanceEnquiryResult.getPackage());
		hashResponse.put("Response Code" , ""+ balanceEnquiryResult.getResponseCode());
		hashResponse.put("Response Description" , ""+ balanceEnquiryResult.getResponseDescription());
		hashResponse.put("Token" , ""+ balanceEnquiryResult.getToken());
		hashResponse.put("Balance" , ""+ balanceEnquiryResult.getBalance());
		hashResponse.put("Expiry Date" , ""+ balanceEnquiryResult.getExpiryDate());
		}
		
		dishMediaServiceSoap.printBalanceEnquiryResponseResult(balanceEnquiryResult);
		
		}catch(Exception e){
			e.printStackTrace();
			hashResponse.put("status", "failure");
		}
		
		return hashResponse;
	}

	@Override
	public void balanceEnquiryJson() {

		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String optionalToken = null;
		
		dishMediaServiceSoap.balanceEnquiryJSON(username, password, customerID, optionalToken);
	}

	@Override
	public HashMap<String, String> recharge(HashMap<String, String> myHash) {
		HashMap<String, String> hashResponse = new HashMap<String, String>();
		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		BigDecimal rechargeAmount = new BigDecimal(myHash.get("amount"));
		String optionalToken = null;
		try{
		RechargeResponseResult rechargeResponseResult = dishMediaServiceSoap.recharge(username, password, customerID, rechargeAmount, optionalToken);
		dishMediaServiceSoap.printRechargeResponseResult(rechargeResponseResult);
		
		if (rechargeResponseResult != null) {
			hashResponse.put("status", "success");
		hashResponse.put("Customer Status" , ""+ rechargeResponseResult.getCustomerStatus());
		hashResponse.put("UpdatedBalance" , ""+ rechargeResponseResult.getUpdatedBalance());
		hashResponse.put("Response Code" , ""+ rechargeResponseResult.getResponseCode());
		hashResponse.put("Response Description" , ""+ rechargeResponseResult.getResponseDescription());
		hashResponse.put("Token" , ""+ rechargeResponseResult.getToken());
		hashResponse.put("TransactionID" , ""+ rechargeResponseResult.getTransactionID());
		hashResponse.put("Expiry Date" , ""+ rechargeResponseResult.getExpiryDate());
		}
		}catch(Exception e){
			e.printStackTrace();
			hashResponse.put("status", "failure");
		}
		return hashResponse;
	}

	@Override
	public void rechargeJSON() {
		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		BigDecimal rechargeAmount = new BigDecimal("0.00");
		String optionalToken = null;
		dishMediaServiceSoap.rechargeJSON(username, password, customerID, rechargeAmount, optionalToken);
	}

	@Override
	public void getLanguages() {
		
		String username = "enet";
		String password = "enet";
		String optionalToken = null;
		
		GetLanguagesResponseResult getLanguageResponse = dishMediaServiceSoap.getLanguages(username, password, optionalToken);
		dishMediaServiceSoap.printLanguageResponseResult(getLanguageResponse);
	}

	@Override
	public void getLanguagesJSON() {
		String username = "enet";
		String password = "enet";
		String optionalToken = null;
		
		dishMediaServiceSoap.getLanguagesJSON(username, password, optionalToken);
		
	}

	@Override
	public void updateLanguage() {
		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String languageKey = null;
		String optionalToken = null;
		
		UpdateLanguageResponseResult updateLanguageResponse = dishMediaServiceSoap.updateLanguage(username, password, customerID, languageKey, optionalToken);
		dishMediaServiceSoap.printUpdateLanguageResponseResult(updateLanguageResponse);
	}

	@Override
	public void updateLanguageJSON() {
		
		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String languageKey = null;
		String optionalToken = null;
		
		dishMediaServiceSoap.updateLanguageJSON(username, password, customerID, languageKey, optionalToken);
		
	}

	@Override
	public HashMap<String, String> rechargeBySTBIDorCASIDorCHIPID(HashMap<String, String> myHash) {
		HashMap<String, String> hashResponse = new HashMap<String, String>();
		String username = "enet";
		String password = "enet";
		String stbiDorCASIDorCHIPID = myHash.get("casId");
		BigDecimal rechargeAmount = new BigDecimal(myHash.get("amount"));
		String optionalToken = null;
		try{
		RechargeResponseResult rechargeResponseResult = dishMediaServiceSoap.rechargeBySTBIDorCASIDorCHIPID(username, password, stbiDorCASIDorCHIPID, rechargeAmount, optionalToken);
		
		dishMediaServiceSoap.printRechargeBySTBIDorCASIDorCHIPIDResponseResult(rechargeResponseResult);
	

		if (rechargeResponseResult != null) {
			hashResponse.put("status", "success");
		hashResponse.put("Customer Status" , ""+ rechargeResponseResult.getCustomerStatus());
		hashResponse.put("UpdatedBalance" , ""+ rechargeResponseResult.getUpdatedBalance());
		hashResponse.put("Response Code" , ""+ rechargeResponseResult.getResponseCode());
		hashResponse.put("Response Description" , ""+ rechargeResponseResult.getResponseDescription());
		hashResponse.put("Token" , ""+ rechargeResponseResult.getToken());
		hashResponse.put("TransactionID" , ""+ rechargeResponseResult.getTransactionID());
		hashResponse.put("Expiry Date" , ""+ rechargeResponseResult.getExpiryDate());
		}
		}catch(Exception e){
			e.printStackTrace();
			hashResponse.put("status", "failure");
		}
		return hashResponse;
	}

	@Override
	public void rechargeBySTBIDorCASIDorCHIPIDJSON() {

		String username = "enet";
		String password = "enet";
		String stbiDorCASIDorCHIPID = null;
		BigDecimal rechargeAmount = new BigDecimal("0.00");
		String optionalToken = null;
		
		dishMediaServiceSoap.rechargeBySTBIDorCASIDorCHIPIDJSON(username, password, stbiDorCASIDorCHIPID, rechargeAmount, optionalToken);
		
		
	}

	@Override
	public void getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPID() {
		
		String username = "enet";
		String password = "enet";
		String stbiDorCASIDorCHIPID = null;
		String optionalToken = null;
		
		TransactionHistoryResponseResult transactionHistoryResponse = dishMediaServiceSoap.getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPID(username, password, stbiDorCASIDorCHIPID, optionalToken);
		dishMediaServiceSoap.printTransactionHistoryResponseResult(transactionHistoryResponse);
	}

	@Override
	public void getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON() {

		String username = "enet";
		String password = "enet";
		String stbiDorCASIDorCHIPID = null;
		String optionalToken = null;
		dishMediaServiceSoap.getLastPinlessTopupTransactionForSTBIDorCASIDorCHIPIDJSON(username, password, stbiDorCASIDorCHIPID, optionalToken);
		
	}

	@Override
	public void getStatusbyTransactionID() {
		
		String username = "enet";
		String password = "enet";
		int transactionID = 1;
		String optionalToken = null;
		
		CheckTransactionStatusResult checkTransactionStatusResultResponse = dishMediaServiceSoap.getStatusbyTransactionID(username, password, transactionID, optionalToken);
		dishMediaServiceSoap.printCheckTransactionStatusResponseResult(checkTransactionStatusResultResponse);
	}

	@Override
	public void getStatusbyTransactionIDJSON() {
		
		String username = "enet";
		String password = "enet";
		int transactionID = 1;
		String optionalToken = null;
		
		dishMediaServiceSoap.getStatusbyTransactionIDJSON(username, password, transactionID, optionalToken);
		
		
	}

	@Override
	public void getCustomerDetailBySTBIDorCASIDorCHIPID() {
		String username = "enet";
		String password = "enet";
		String stbiDorCASIDorCHIPID = null;
		String optionalToken = null;
		
		RTNCheckResponseResult RTNCheckResponseResultResponse = dishMediaServiceSoap.getCustomerDetailBySTBIDorCASIDorCHIPID(username, password, stbiDorCASIDorCHIPID, optionalToken);
		dishMediaServiceSoap.printRTNCheckResponseResponseResult(RTNCheckResponseResultResponse);
	}

	@Override
	public void getCustomerDetailBySTBIDorCASIDorCHIPIDJSON() {

		String username = "enet";
		String password = "enet";
		String stbiDorCASIDorCHIPID = null;
		String optionalToken = null;
		
		dishMediaServiceSoap.getCustomerDetailBySTBIDorCASIDorCHIPIDJSON(username, password, stbiDorCASIDorCHIPID, optionalToken);
		
	}

	@Override
	public void lookupVoucher() {
		
		String username = "enet";
		String password = "enet";
		String voucherNumber = null;
		String optionalToken = null;
		
		VoucherResult voucherResultResponse = dishMediaServiceSoap.lookupVoucher(username, password, voucherNumber, optionalToken);
		dishMediaServiceSoap.printVoucherResponseResult(voucherResultResponse);
		
	}

	@Override
	public void lookupVoucherJSON() {
		String username = "enet";
		String password = "enet";
		String voucherNumber = null;
		String optionalToken = null;
		
		dishMediaServiceSoap.lookupVoucherJSON(username, password, voucherNumber, optionalToken);
	}

	@Override
	public void packageChange() {

		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String packageName = null;
		String optionalToken = null;
		
		PackageChangeResponseResult packageChangeResponseResult = dishMediaServiceSoap.packageChange(username, password, customerID, packageName, optionalToken);
		dishMediaServiceSoap.printPackageChangeResponseResult(packageChangeResponseResult);
	}

	@Override
	public void packageChangeJSON() {
		
		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String packageName = null;
		String optionalToken = null;
		
		dishMediaServiceSoap.packageChangeJSON(username, password, customerID, packageName, optionalToken);
	}

	@Override
	public void ping() {

		String username = "enet";
		
		AuthenticationResult authenticationResult = dishMediaServiceSoap.ping(username);
		dishMediaServiceSoap.printAuthenticationResponseResult(authenticationResult);
		
	}

	@Override
	public void pingJSON() {
		
		String username = "enet";
		
		dishMediaServiceSoap.pingJSON(username);
		
	}

	@Override
	public void rtnCheck() {
		
		String username = "enet";
		String password = "enet";
		String ani = null;
		String optionalToken = null;
		
		RTNCheckResponseResult checkRTNResponseResult = dishMediaServiceSoap.rtnCheck(username, password, ani, optionalToken);
		dishMediaServiceSoap.printRTNCheckResponseResponseResult(checkRTNResponseResult);
	}

	@Override
	public void rtnCheckJSON() {

		String username = "enet";
		String password = "enet";
		String ani = null;
		String optionalToken = null;
		
		dishMediaServiceSoap.rtnCheckJSON(username, password, ani, optionalToken);
		
	}

	@Override
	public void customerIDCheck() {

		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String optionalToken = null;
		
		RTNCheckResponseResult checkRTNResponseResult = dishMediaServiceSoap.customerIDCheck(username, password, customerID, optionalToken);
		dishMediaServiceSoap.printRTNCheckResponseResponseResult(checkRTNResponseResult);
	}

	@Override
	public void customerIDCheckJSON() {

		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String optionalToken = null;
		
		dishMediaServiceSoap.customerIDCheckJSON(username, password, customerID, optionalToken);
		
	}

	@Override
	public void packageInfo() {
		
		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String optionalToken = null;
		
		PackageInfoResponseResult packageInfoResponseResult = dishMediaServiceSoap.packageInfo(username, password, customerID, optionalToken);
		dishMediaServiceSoap.printPackageInfoResponseResult(packageInfoResponseResult);
	}

	@Override
	public void packageInfoJSON() {
		
		String username = "enet";
		String password = "enet";
		int customerID = 2078142;
		String optionalToken = null;
		
		dishMediaServiceSoap.packageInfoJSON(username, password, customerID, optionalToken);
		
	} 
	
	

}
