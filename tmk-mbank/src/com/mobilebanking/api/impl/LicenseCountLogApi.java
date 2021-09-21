package com.mobilebanking.api.impl;

import com.mobilebanking.api.ILicenseCountLogApi;
import com.mobilebanking.converter.LicenseCountLogConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.LicenseCountLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.LicenseCountLogDTO;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.LicenseCountLogRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.ClientException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LicenseCountLogApi implements ILicenseCountLogApi {

    @Autowired
    private LicenseCountLogRepository licenseCountLogRepository;
    @Autowired
    private LicenseCountLogConverter licenseCountLogConverter;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<LicenseCountLogDTO> findLicenseCountLog() {
        List<LicenseCountLog> requestList = licenseCountLogRepository.findLicenseCountLog();
        if(!requestList.isEmpty()){
            return licenseCountLogConverter.convertToDtoList(requestList);
        }

        return null;
    }

    @Override
    public LicenseCountLog saveRequest(LicenseCountLogDTO licenseCountLogDTO) throws JSONException, ClientException {
        Bank bank = bankRepository.findOne(licenseCountLogDTO.getBankId());
        User user=userRepository.findUserById(licenseCountLogDTO.getUserId());
        LicenseCountLog licenseCountLog=new LicenseCountLog();
        licenseCountLog.setBank(bank);
        licenseCountLog.setUser(user);
        licenseCountLog.setLicenseCount(licenseCountLogDTO.getLicenseCount());
        licenseCountLog.setRemarks(licenseCountLogDTO.getRemarks());
        licenseCountLogRepository.save(licenseCountLog);
        return null;
    }

    public boolean addLicenseCount(Long bankId, Integer licenseCount) {
        System.out.println("lcoount"+licenseCount);
        Bank bank = bankRepository.findOne(bankId);
        bank.setLicenseCount(bank.getLicenseCount() + licenseCount);
        bankRepository.save(bank);
        return true;
    }
}
