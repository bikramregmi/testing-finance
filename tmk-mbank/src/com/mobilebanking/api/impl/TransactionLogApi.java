/**
 * 
 */
package com.mobilebanking.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ITransactionLogApi;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.entity.TransactionLog;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.TransactionLogRepository;

/**
 * @author bibek
 *
 */
@Service
public class TransactionLogApi implements ITransactionLogApi {
	
	@Autowired
	private TransactionLogRepository transactionLogRepository;
	
	@Override
	public void createTransactionLog(Transaction transaction, String remarks, UserType transactionByUser,
			String transactionBy) {
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setRemarks(remarks);
		transactionLog.setTransaction(transaction);
		transactionLog.setStatus(transaction.getStatus());
		transactionLog.setTransactionBy(transactionBy);
		transactionLog.setTransactionByUser(transactionByUser);
		
		transactionLogRepository.save(transactionLog);
	}

}
