package com.mobilebanking.api;

import java.util.Date;
import java.util.List;

import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.ChequeRequestDTO;

public interface IChequeRequestApi {

	boolean save(String accountNo , Integer chequeLeaves, long customerId);

	List<ChequeRequestDTO> getAllChequeRequestByBankBranch(Long id);

	void editchequeRequest(long chequeRequestId);

	List<ChequeRequestDTO> getcheckRequestByAccountNumber(String accountNumber);

	List<ChequeRequestDTO> getAllChequeRequestByBankBranch(List<BankBranchDTO> bankBranchList);

	Long countChequeBookRequest(Date date);
	
}
