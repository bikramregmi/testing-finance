package com.mobilebanking.api.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ISettlementLogApi;
import com.mobilebanking.entity.SettlementLog;
import com.mobilebanking.model.SettlementLogDTO;
import com.mobilebanking.model.SettlementStatus;
import com.mobilebanking.repositories.SettlementLogRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
public class SettlementLogApi implements ISettlementLogApi {

	@Autowired
	private SettlementLogRepository settlementLogRepository;
	
	@Autowired
	private ICustomerApi customerApi;

	@Override
	public List<SettlementLogDTO> listPendingLog() {

		 List<SettlementLog> settlementLogList =  settlementLogRepository.findBySettlementStatus(SettlementStatus.FAILURE);
		return ConvertUtil.convertSettlementLogList(settlementLogList);
	}

	@Override
	public Boolean autoUpdateSettlementByTransaction(String transactionIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean autoUpdateSettlementBySettlementLog(Long settlementLogId) {
		SettlementLog settlementLog = settlementLogRepository.findOne(settlementLogId);
		HashMap<String, String> settlementResponse = customerApi.fundTransfer(settlementLog.getFromAccountNumber(), settlementLog.getToAccountNumber(), settlementLog.getAmount(), settlementLog.getBank());
		if(settlementResponse.get("status").equals("success")){
			settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
			settlementLog.setResponseCode(settlementResponse.get("code"));
			settlementLogRepository.save(settlementLog);
			return true;
		}else{
			settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
			settlementLog.setResponseCode(settlementResponse.get("code"));
			settlementLogRepository.save(settlementLog);
			return false;
		}
	}

	@Override
	public Boolean manualUpdateSettlementBySettlementLog(long settlementLogId) {
		SettlementLog settlementLog = settlementLogRepository.findOne(settlementLogId);
			settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
			settlementLogRepository.save(settlementLog);
			return true;
	}

	@Override
	public List<SettlementLogDTO> findByTransactionIdentifier(String transactionIdentifier) {
		List<SettlementLog> settlementLogList = settlementLogRepository.findByTransactionIdentifier(transactionIdentifier);
		if(settlementLogList!=null){
			return ConvertUtil.convertSettlementLogList(settlementLogList);
		}
		return null;
	}

	
	
}
