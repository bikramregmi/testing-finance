package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.SettlementLogDTO;

public interface ISettlementLogApi {

	List<SettlementLogDTO> listPendingLog();
	
	Boolean autoUpdateSettlementByTransaction(String transactionIdentifier);
	
	Boolean autoUpdateSettlementBySettlementLog(Long settlementLogId);

	Boolean manualUpdateSettlementBySettlementLog(long id);

	List<SettlementLogDTO> findByTransactionIdentifier(String transactionIdentifier);
	
	
}
