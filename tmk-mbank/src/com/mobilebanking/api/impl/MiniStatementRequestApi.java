package com.mobilebanking.api.impl;

import com.mobilebanking.api.IMiniStatementRequestApi;
import com.mobilebanking.converter.MiniStatementRequestConverter;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.MiniStatementRequest;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.mobile.MiniStatementRequestDTO;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.MiniStatementRepository;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.ConvertUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MiniStatementRequestApi implements IMiniStatementRequestApi {

    @Autowired
    private MiniStatementRepository miniStatementRepository;

    @Autowired
    private CustomerBankAccountRepository customerBankAccountRepository;

    @Autowired
    private MiniStatementRequestConverter miniStatementRequestConverter;

    @Override
    public MiniStatementRequest saveRequest(MiniStatementRequestDTO miniStatementRequestDTO) throws JSONException, ClientException {
        CustomerBankAccount customerBankAccount= customerBankAccountRepository.findCustomerAccountByAccountNumber(miniStatementRequestDTO.getAccountNumber(),miniStatementRequestDTO.getCustomerId());
        MiniStatementRequest miniStatementRequest=new MiniStatementRequest();
        miniStatementRequest.setCustomerBankAccount(customerBankAccount);
        miniStatementRequest.setStartDate(miniStatementRequestDTO.getStartDate());
        miniStatementRequest.setEndDate(miniStatementRequestDTO.getEndDate());
        miniStatementRequest.setEmail(miniStatementRequestDTO.getEmail());
        miniStatementRepository.save(miniStatementRequest);

        return miniStatementRequest;
    }
    @Override
    public List<MiniStatementRequest> findAll() {
        return miniStatementRepository.findAll();

    }

    @Override
    public List<MiniStatementRequestDTO> findMiniStatementRequestByBank(Long bankId) {
        List<MiniStatementRequest> requestList = miniStatementRepository.findMiniStatementRequestByBank(bankId);
        if(!requestList.isEmpty()){
            return miniStatementRequestConverter.convertToDtoList(requestList);
        }
        return null;
    }

    @Override
    public List<MiniStatementRequestDTO> findMiniStatementRequestByBranch(Long branchId) {
        List<MiniStatementRequest> requestList = miniStatementRepository.findMiniStatementRequestByBranch(branchId);
        if(!requestList.isEmpty()){
            return miniStatementRequestConverter.convertToDtoList(requestList);
        }

        return null;
    }
}
