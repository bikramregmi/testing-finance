package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.SmsPackages;
import com.mobilebanking.model.SmsPackageDTO;

public interface ISmsPackageApi {

	List<SmsPackageDTO> getAllSmsPackage();
	SmsPackages saveSmsPackage(Bank bank);
}
