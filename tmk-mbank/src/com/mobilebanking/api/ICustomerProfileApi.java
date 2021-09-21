package com.mobilebanking.api;

import java.util.HashMap;
import java.util.List;

import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.CustomerProfile;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerProfileDTO;

public interface ICustomerProfileApi {
	boolean saveProfile (CustomerProfileDTO customerProfile);
	boolean editProfile (CustomerProfileDTO customerProfile);
	CustomerProfileDTO getCustomerProfile(String uuid);
	HashMap<String, String> checkTransactionLimit(CustomerBankAccountDTO customerBankAccount,Double amount);
	void updateCustomerTransactionLog(CustomerBankAccount customerBankAccount, Double amount);
	void resetDailyTransactionLog(CustomerBankAccount customerBankAccount);
	void resetWeeklyTransactionLog(CustomerBankAccount customerBankAccount);
	void resetMonthlyTransactionLog(CustomerBankAccount customerBankAccount);
	CustomerProfileDTO getProfileById(long profileId);
	CustomerProfile addCustomerToProfile(long customerProfileId, Customer customer);
	CustomerProfile updateCustomerToProfile(long customerProfileId, Customer customer);
	void checkAndUpdateRenewPeriod();
	List<CustomerProfileDTO> listCustomerProfile(long bankId);
	boolean changeStatus(long customerProfileId);
	List<CustomerProfileDTO> listActiveCustomerProfile(Long id);
	BankDTO getBankByProfile(long profileId);

}
