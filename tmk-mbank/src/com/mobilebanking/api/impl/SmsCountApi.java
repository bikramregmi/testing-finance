package com.mobilebanking.api.impl;

import com.mobilebanking.api.ISmsCountApi;
import com.mobilebanking.converter.SmsCountConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.LicenseCountLog;
import com.mobilebanking.entity.SmsCountLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.SmsCountDTO;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.SmsCountRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.ClientException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SmsCountApi implements ISmsCountApi {
    @Autowired
    private SmsCountRepository smsCountRepository;
    @Autowired
    private SmsCountConverter smsCountConverter;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<SmsCountDTO> findSmsCountLog() {
        List<SmsCountLog> requestList = smsCountRepository.findSmsCountLog();
        if(!requestList.isEmpty()){
            return smsCountConverter.convertToDtoList(requestList);
        }
        return null;
    }

    @Override
    public SmsCountLog saveRequest(SmsCountDTO smsCountDTO) throws JSONException, ClientException {
        Bank bank = bankRepository.findOne(smsCountDTO.getBankId());
        User user=userRepository.findUserById(smsCountDTO.getUserId());
        SmsCountLog smsCountLog=new SmsCountLog();
        smsCountLog.setBank(bank);
        smsCountLog.setSmsCount(smsCountDTO.getSmsCount());
        smsCountLog.setRemarks(smsCountDTO.getRemarks());
        smsCountLog.setUser(user);
        smsCountRepository.save(smsCountLog);
        return null;
    }
    public boolean addSmsCount(Long bankId, Integer licenseCount) {
        Bank bank = bankRepository.findOne(bankId);
        bank.setLicenseCount(bank.getLicenseCount() + licenseCount);
        bankRepository.save(bank);
        return true;
    }
}
