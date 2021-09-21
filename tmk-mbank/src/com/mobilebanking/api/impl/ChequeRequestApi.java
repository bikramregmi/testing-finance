package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IChequeRequestApi;
import com.mobilebanking.converter.ChequeRequestConverter;
import com.mobilebanking.entity.ChequeRequest;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.ChequeRequestDTO;
import com.mobilebanking.model.ChequeRequestStatus;
import com.mobilebanking.repositories.ChequeRequestRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;

@Service
public class ChequeRequestApi implements IChequeRequestApi {

	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;
	@Autowired
	private ChequeRequestRepository chequeRequestRepository;
	
	@Autowired
	private ChequeRequestConverter chequeRequestConverter;
	
	@Override
	public boolean save(String accountNo, Integer chequeLeaves,long customerId) {

		ChequeRequest chequeRequest = new ChequeRequest();
		chequeRequest.setChequeLeaves(chequeLeaves);
		chequeRequest.setChequeRequestStatus(ChequeRequestStatus.PENDING);
		CustomerBankAccount customerBankAccount =  customerBankAccountRepository.findCustomerAccountByAccountNumber(accountNo, customerId);
		chequeRequest.setCustomerAccount(customerBankAccount);
		chequeRequestRepository.save(chequeRequest);
		return true;
	}

	@Override
	public List<ChequeRequestDTO> getAllChequeRequestByBankBranch(Long id) {
//		User user = userRepository.findOne(id);
		List<ChequeRequest> chequeRequestList = chequeRequestRepository.getAllChequeRequestByBankBranch(id);
		if(!chequeRequestList.isEmpty()){
			return chequeRequestConverter.convertToDtoList(chequeRequestList);
		}
		
		return null;
	}
	
	@Override
	public List<ChequeRequestDTO> getAllChequeRequestByBankBranch(List<BankBranchDTO> bankBranchList) {
		List<ChequeRequestDTO> listOfRequest = new ArrayList<>();
		for(BankBranchDTO bankBranch : bankBranchList){
//		List<ChequeRequestDTO> chequeRequest = new ArrayList<>();
		List<ChequeRequest> chequeRequestList = chequeRequestRepository.getAllChequeRequestByBankBranch(bankBranch.getId());
//		
		for(ChequeRequest list :chequeRequestList){
			listOfRequest.add(chequeRequestConverter.convertToDto(list));
			
		}
		}
		if(!listOfRequest.isEmpty()){
//			chequeRequest = chequeRequestConverter.convertToDtoList(chequeRequestList);
			return listOfRequest;
		}else
		return null;
	}

	@Override
	public void editchequeRequest(long chequeRequestId) {
		ChequeRequest chequeRequest = chequeRequestRepository.findOne(chequeRequestId);
		if(chequeRequest.getChequeRequestStatus().equals(ChequeRequestStatus.PENDING)){
			chequeRequest.setChequeRequestStatus(ChequeRequestStatus.PROCESSING);
		}else if(chequeRequest.getChequeRequestStatus().equals(ChequeRequestStatus.PROCESSING)){
			chequeRequest.setChequeRequestStatus(ChequeRequestStatus.COMPLETED);
		}
		chequeRequestRepository.save(chequeRequest);
		
	}

	@Override
	public List<ChequeRequestDTO> getcheckRequestByAccountNumber(String accountNumber) {
		List<ChequeRequest> chequeRequestList = chequeRequestRepository.getcheckRequestByAccountNumber(accountNumber);
		if(!chequeRequestList.isEmpty()){
			return chequeRequestConverter.convertToDtoList(chequeRequestList);
		}
		
		return null;
	}

	@Override
	public Long countChequeBookRequest(Date date) {
		if(date == null){
			return chequeRequestRepository.count();
		}else{
			return chequeRequestRepository.countRequestFromDate(date);
		}
	}

	

}
