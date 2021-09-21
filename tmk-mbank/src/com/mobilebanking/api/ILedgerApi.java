package com.mobilebanking.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Ledger;
import com.mobilebanking.model.LedgerDTO;
import com.mobilebanking.model.LedgerStatus;
import com.mobilebanking.model.LedgerType;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.util.ClientException;

public interface ILedgerApi {
	
	Ledger searchByLedgerId(long LedgerId);
	LedgerDTO createLedger(LedgerDTO ledger) throws ClientException;
	LedgerDTO reverseTxnInLedger(long ledgerId, String remarks) throws ClientException;
	LedgerDTO cancelTxnInLedger(long ledgerId) throws ClientException;
	List<LedgerDTO> getStatement(long accountId) throws Exception;
	LedgerDTO getLedgerDetail(long txnId);
	Ledger saveLedger(long txnId,double amount,Account fromAccount,Account toAccount,LedgerStatus status,LedgerType type) throws ClientException;
	Ledger saveCommissionSettlementInLedger(long txnId,double amount,Account fromAccount,Account toAccount,double fromCommissionInStlmntCur,double toCommissionInStlmntCur,LedgerStatus status,LedgerType type) throws ClientException;
	LedgerDTO editLedger(LedgerDTO dto) throws ClientException;
	HashMap<String, Object> getLoadFundReport(String fromDate, String toDate);
	//added by amrit
	Map<String,Object> getCreditLimitReport(String fromDate, String toDate);
    //List<Ledger> getCreditLimitReport(String fromDate, String toDate);
	//end added
	PagablePage getLedgerHistory(Integer integer, String fromDate, String toDate, String bankCode, String ledgerType);
	List<LedgerDTO> getBankLedger(String bankCode, String fromDate, String toDate, String debitCredit);
	PagablePage getLedgerHistory1(Integer pageNumber, String fromDate, String toDate, String bankCode,
			String ledgerType);

}
