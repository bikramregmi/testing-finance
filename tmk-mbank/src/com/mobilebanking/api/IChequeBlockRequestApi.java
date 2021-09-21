package com.mobilebanking.api;

import java.util.Date;
import java.util.List;

import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.ChequeBlockRequestDTO;

public interface IChequeBlockRequestApi {

	ChequeBlockRequestDTO save(String accountNumber , String ChequeNumber, long customerId);
	
	List<ChequeBlockRequestDTO> getChequeListByBankBranch(Long id);

	List<ChequeBlockRequestDTO> getcheckBlockRequestByAccountNumber(String accountNumber);

	void editchequeBlockRequest(long chequeRequestId);

	List<ChequeBlockRequestDTO> getChequeListByBankBranch(List<BankBranchDTO> bankBranchList);

	Long countChequeBlockRequest(Date date);

}
