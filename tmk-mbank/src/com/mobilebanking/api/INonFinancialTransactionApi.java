package com.mobilebanking.api;

import java.util.Date;
import java.util.List;

import com.mobilebanking.model.IsoStatus;
import com.mobilebanking.model.NonFinancialTransactionDTO;
import com.mobilebanking.model.NonFinancialTransactionType;
import com.mobilebanking.model.PagablePage;


public interface INonFinancialTransactionApi {

	List<NonFinancialTransactionDTO> getTransactionByBank(Long bankId);
	
	List<NonFinancialTransactionDTO>getByBranch(Long branchId);

	List<NonFinancialTransactionDTO> getAllTransaction();

	List<NonFinancialTransactionDTO> filterTransaction(String fromDate, String toDate, String identifier, String status, String mobileNo, String bank);

	PagablePage getAllTransactions(Integer currentpage, String fromDate, String toDate, String identifier,
			IsoStatus status, String mobileNo, String bank);
	
	Long countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType nonFinancialTransactionType);
	
	String exportNonFinancialTransaction(String path);
	
	Long countTransaction();
//added by amrit for dashbaord
	Long countTransactionsByDate(Date date);
	

	Long countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType nonFinancialTransactionType,
			Date date);
	//end of the added
}
