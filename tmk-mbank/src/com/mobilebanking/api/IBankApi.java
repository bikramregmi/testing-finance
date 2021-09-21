package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.Bank;
import com.mobilebanking.model.BankAccountSettingsDto;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.BankMerchantAccountsDto;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.ProfileAccountSettingDto;
import com.mobilebanking.model.Status;

public interface IBankApi {
	
	void saveBank(BankDTO dto);
	
	List<BankDTO> findBank();

	List<BankDTO> getAllBank();

	Bank getBankById(long bankId);

	void editBank(BankDTO bankDTO);
	
	List<BankDTO> getBankByCountry(String isoThree);
	
	BankDTO getBankDtoById(long bankId) ;
	
	BankDTO getBankByName(String name);
	
	List<BankDTO> getBankByNameLike(String name);
	
	List<BankDTO> geteBankByStatus(Status status);

	void saveBulkBank(BankDTO bank);
	
	void sendSmsForBankUser() throws Exception;
	
	List<MerchantDTO> getMerchantByBank(String bankCode);
	
	BankDTO getBankByCode(String bankCode);
	
	BankAccountSettingsDto getActiveBankAccountsSettingsByBank(String bankCode);
	
	BankAccountSettingsDto getBankAccountsSettingsByBank(String bankCode);

	BankAccountSettingsDto saveBankAccountSetting(BankAccountSettingsDto bankAccountsettingDto);

	BankAccountSettingsDto editBankAccountSetting(BankAccountSettingsDto bankAccounts);

	BankMerchantAccountsDto saveBankMerchantAccount(BankMerchantAccountsDto merchantAccount);

	List<BankMerchantAccountsDto> getbankMerchantAccountsByBank(String bankCode);

	BankMerchantAccountsDto getBankMerchantAccountsById(Long id);

	BankMerchantAccountsDto editBankMerchantAccount(BankMerchantAccountsDto merchantAccounts);

	List<MerchantDTO> getMerchantWithoutMerchantAccountByBank(String bankCode);

	boolean updateSmsCount(Long bankId, Integer smsCount);

	List<BankDTO> getAllBankWithBalance();

	boolean addLicenseCount(Long bankId, Integer licenseCount);

    boolean addRemarks(Long bankId,String remarks);

	String getBankEmail(Long bankid);

	Boolean getIsMessageSent(Long bankid);

	ProfileAccountSettingDto getProfileAccountsSettingsByBank(long l);

	void saveBankProfileAccountSetting(ProfileAccountSettingDto profileAccounts,long bankId);

	void editProfileAccountSetting(ProfileAccountSettingDto bankAccounts, long associatedId);

	List<BankDTO> getBankWithoutOauthClient();

	Long countByChannelPartner(long channelPartnerId);

	List<BankDTO> getAllBankOfChannelPartner(Long channelPartnerId);
	
	PagablePage geteBanks(Integer page, String name, String swiftCode, String fromDate, String toDate);

	boolean checkBankCbsStatus(Long bankId);

	List<BankDTO> getBankByCbsStatus(Status status);

	//void sendUserNameForBankUser() throws Exception;
	

}
