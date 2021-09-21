package com.mobilebanking.api;

import com.mobilebanking.entity.MiniStatementRequest;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.mobile.MiniStatementRequestDTO;
import com.mobilebanking.util.ClientException;
import org.json.JSONException;

import java.util.List;

public interface IMiniStatementRequestApi {

    MiniStatementRequest saveRequest(MiniStatementRequestDTO miniStatementRequestDTO) throws JSONException, ClientException;
    List<MiniStatementRequest> findAll();

    List<MiniStatementRequestDTO> findMiniStatementRequestByBank(Long bankId);

    List<MiniStatementRequestDTO> findMiniStatementRequestByBranch(Long branchId);
}
