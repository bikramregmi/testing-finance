package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IChequeBlockRequestApi;
import com.mobilebanking.converter.ChequeBlockRequestConverter;
import com.mobilebanking.entity.ChequeBlockRequest;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.ChequeBlockRequestDTO;
import com.mobilebanking.model.ChequeBlockRequestStatus;
import com.mobilebanking.repositories.ChequeBlockRequestRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;

@Service
public class ChequeBlockRequestApi implements IChequeBlockRequestApi {

	@Autowired
	private ChequeBlockRequestRepository chequeBlockRequestRepository;
	
	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;
	
	@Autowired
	private ChequeBlockRequestConverter  chequeBlockRequestConverter;	
	
	@Override
	public ChequeBlockRequestDTO save(String accountNumber, String chequeNumber,long customerId) {
		
		ChequeBlockRequest chequeRequest = new ChequeBlockRequest();
		
		CustomerBankAccount customerBankAccount =  customerBankAccountRepository.findCustomerAccountByAccountNumber(accountNumber, customerId);
		chequeRequest.setChequeNumber(chequeNumber);
		chequeRequest.setCustomerAccount(customerBankAccount);
		chequeRequest.setStatus(ChequeBlockRequestStatus.PENDING);
		chequeRequest = chequeBlockRequestRepository.save(chequeRequest);
		
		return chequeBlockRequestConverter.convertToDto(chequeRequest);
	}

	@Override
	public List<ChequeBlockRequestDTO> getChequeListByBankBranch(Long id) {
		List<ChequeBlockRequest> chequeRequestList = chequeBlockRequestRepository.getAllChequeBlockRequestByBankBranch(id);
		if(!chequeRequestList.isEmpty()){
			return chequeBlockRequestConverter.convertToDtoList(chequeRequestList);
		}
		
		return null;
		
	}
	@Override
	public List<ChequeBlockRequestDTO> getcheckBlockRequestByAccountNumber(String accountNumber) {
		List<ChequeBlockRequest> chequeRequestList = chequeBlockRequestRepository.getcheckRequestByAccountNumber(accountNumber);
		if(!chequeRequestList.isEmpty()){
			return chequeBlockRequestConverter.convertToDtoList(chequeRequestList);
		}
		
		return null;
	}

	@Override
	public void editchequeBlockRequest(long chequeRequestId) {
		ChequeBlockRequest chequeRequest = chequeBlockRequestRepository.findOne(chequeRequestId);
		if(chequeRequest.getStatus().equals(ChequeBlockRequestStatus.PENDING)){
			chequeRequest.setStatus(ChequeBlockRequestStatus.BLOCKED);
		}
		chequeBlockRequestRepository.save(chequeRequest);
		
	}

	@Override
	public List<ChequeBlockRequestDTO> getChequeListByBankBranch(List<BankBranchDTO> bankBranchList) {
		List<ChequeBlockRequestDTO> listOfRequest = new ArrayList<>();
		for(BankBranchDTO bankBranch : bankBranchList){
//		List<ChequeRequestDTO> chequeRequest = new ArrayList<>();
		List<ChequeBlockRequest> chequeBlockRequestList = chequeBlockRequestRepository.getAllChequeBlockRequestByBankBranch(bankBranch.getId());
//		
		for(ChequeBlockRequest list :chequeBlockRequestList){
			listOfRequest.add(chequeBlockRequestConverter.convertToDto(list));
			
		}
		}
		if(!listOfRequest.isEmpty()){
//			chequeRequest = chequeRequestConverter.convertToDtoList(chequeRequestList);
			return listOfRequest;
		}else
		return null;
	}

	@Override
	public Long countChequeBlockRequest(Date date) {
		if(date == null){
			return chequeBlockRequestRepository.count();
		}else{
			return chequeBlockRequestRepository.countRequestFromDate(date);
		}
	}
	
}
