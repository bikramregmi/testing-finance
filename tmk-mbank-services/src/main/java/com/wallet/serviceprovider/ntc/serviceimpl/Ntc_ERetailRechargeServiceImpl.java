package com.wallet.serviceprovider.ntc.serviceimpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.serviceprovider.ntc.AgentIdentifier;
import com.wallet.serviceprovider.ntc.AliasCategory;
import com.wallet.serviceprovider.ntc.AuthorizationData;
import com.wallet.serviceprovider.ntc.DetailTransaction;
import com.wallet.serviceprovider.ntc.ERetailRechargeResult;
import com.wallet.serviceprovider.ntc.EndPoint;
import com.wallet.serviceprovider.ntc.GlobalTransaction;
import com.wallet.serviceprovider.ntc.WebServiceException_Exception;
import com.wallet.serviceprovider.ntc.service.Ntc_ERetailRechargeService;

@Service
public class Ntc_ERetailRechargeServiceImpl  implements Ntc_ERetailRechargeService{
	
	@Autowired
	EndPoint endPoint;
	
	@Override
	public HashMap<String, String> mobileTopUp(HashMap<String, String> myHash)  {

		HashMap<String, String> hashResponse = new HashMap<String, String>();

			AuthorizationData requesterAuthentication = new AuthorizationData();
			requesterAuthentication.setAliasCategory(AliasCategory.USER);
			requesterAuthentication.setAliasName("eretail_talktime");
			requesterAuthentication.setAliasPassword("T@lkt1me123");
			// requesterAuthentication.setMvnoId(null);

			String requestTag = "";

			// for ERetail Recharge
			AgentIdentifier debitedAgent = new AgentIdentifier();
			debitedAgent.setActorId(1408L);
			debitedAgent.setAliasCategory(AliasCategory.MSISDN);
			// debitedAgent.setAliasName("Suresh");
			// debitedAgent.setMvnoId(2L);

			String checker = myHash.get("serviceTo").substring(0, 3);
			String rechargedMsisdn = "977" + myHash.get("serviceTo");
			boolean debitedAgentNotification = true;
			long rechargeAmount = (long) Double.parseDouble(myHash.get("amount")) * 100000L;
			long productId = 1L;
			if (checker.equals("985") || checker.equals("975")) {
				productId = 10L;
			}
			
			try{
			ERetailRechargeResult eRetailRechargeResult = endPoint.eRetailRecharge(requesterAuthentication,
					debitedAgent, rechargedMsisdn, debitedAgentNotification, rechargeAmount, productId, requestTag);
			
			GlobalTransaction globalTransaction = eRetailRechargeResult.getGlobalTransaction();

			if (globalTransaction != null) {
				hashResponse.put("status", "success");
				List<DetailTransaction> detailTransactions = globalTransaction.getDetailTransactionList()
						.getDetailTransaction();
				for (DetailTransaction detailTransaction : detailTransactions) {
					hashResponse.put("Agent Id" , ""+detailTransaction.getAgentId());
					hashResponse.put("Amount" , ""+detailTransaction.getAmount());
					hashResponse.put("Balance Credit Left", ""+ detailTransaction.getBalanceCreditLeft());
					hashResponse.put("Balance Name" , ""+ detailTransaction.getBalanceName());
					hashResponse.put("Balance Type" , ""+ detailTransaction.getBalanceType());
					hashResponse.put("Currency Description" , ""+ detailTransaction.getCurrencyDescription());
					hashResponse.put("Operation" , ""+ detailTransaction.getOperation());
					hashResponse.put("Quantity" , ""+ detailTransaction.getQuantity());
					hashResponse.put("Unitary Price" , ""+ detailTransaction.getUnitaryPrice());
					hashResponse.put("Vat" , ""+ detailTransaction.getVat());
				}

			} 

			hashResponse.put("Transaction Date Time" , ""+ globalTransaction.getTransactionDateTime());
			hashResponse.put("Transaction ID" , ""+ globalTransaction.getTransactionId());
			hashResponse.put("Transaction Oper State" , ""+ globalTransaction.getTransactionOperState());

			hashResponse.put("Request Tag" , ""+ eRetailRechargeResult.getRequestTag());

			endPoint.printERetailRechargeResponse(eRetailRechargeResult);
			
			}catch(Exception e){
				e.printStackTrace();
				hashResponse.put("status", "failure");
			}
			
		

		return hashResponse;
	}

}
