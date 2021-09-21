package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.api.ISmsPackageApi;
import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.controller.BankController;
import com.mobilebanking.converter.BankAccountSettingsConverter;
import com.mobilebanking.converter.BankMerchantAccountsConverter;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankAccountSettings;
import com.mobilebanking.entity.BankMerchantAccounts;
import com.mobilebanking.entity.ChannelPartner;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.Merchant;
import com.mobilebanking.entity.ProfileAccountSetting;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserLog;
import com.mobilebanking.model.AccountType;
import com.mobilebanking.model.BankAccountSettingsDto;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.BankMerchantAccountsDto;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.ProfileAccountSettingDto;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.BankAccountSettingsRepository;
import com.mobilebanking.repositories.BankMerchantAccountsRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.ChannelPartnerRepository;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.CreditLimitRepository;
import com.mobilebanking.repositories.MerchantRepository;
import com.mobilebanking.repositories.ProfileAccountSettingRepository;
import com.mobilebanking.repositories.SmsLogRepository;
import com.mobilebanking.repositories.UserLogRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.repositories.UserTemplateRepository;
import com.mobilebanking.util.AccountUtil;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.DateTypes;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.PageInfo;
import com.mobilebanking.util.PasswordGenerator;
import com.mobilebanking.util.StringConstants;

@Transactional
@Service
public class BankApi implements IBankApi {

	private Logger logger = LoggerFactory.getLogger(BankController.class);

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ChannelPartnerRepository channelPartnerRepository;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordGenerator passwordGenerator;
	@Autowired
	private SmsLogRepository smsLogRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ISparrowApi sparrowApi;

	@Autowired
	private UserLogRepository userLogRepository;

	@Autowired
	private IEmailApi emailApi;

	@Autowired
	private ISmsPackageApi smsPackageApi;

	@Autowired
	private BankAccountSettingsRepository bankAccountSettingsRepository;

	@Autowired
	private BankAccountSettingsConverter bankAccountSettingsConverter;

	@Autowired
	private BankMerchantAccountsRepository bankMerchantAccountsRepository;

	@Autowired
	private BankMerchantAccountsConverter bankMerchantAccountsConverter;

	@Autowired
	private UserTemplateRepository userTemplateRepository;

	@Autowired
	private CreditLimitRepository creditLimitRepository;

	@Autowired
	private AccountUtil accountUtil;

	@Autowired
	private ProfileAccountSettingRepository profileAccountSetting;

	@Autowired
	private IIsoApi isoApi;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveBank(BankDTO bankDto) {
		City city = cityRepository.findCityByIdAndState(bankDto.getState(), Long.parseLong(bankDto.getCity()));
		ChannelPartner channelPartner = channelPartnerRepository.findOne(bankDto.getChannelPartner());
		List<Merchant> merchants = new ArrayList<Merchant>();
		String[] merchantList = bankDto.getMerchants();
		Bank bank = new Bank();
		bank.setName(bankDto.getName());
		bank.setSwiftCode(bankDto.getSwiftCode());
		bank.setCity(city);
		bank.setChannelPartner(channelPartner);
		bank.setAddress(bankDto.getAddress());
		bank.setStatus(Status.Active);
		bank.setLicenseCount(bankDto.getLicenseCount());
		bank.setLicenseExpiryDate(DateUtil.convertStringToDate(bankDto.getLicenseExpiryDate()));
		bank.setCreatedBy(userRepository.findOne(bankDto.getCreatedById()));
		bank.setEmail(bankDto.getEmail());
		bank.setMobileNumber(bankDto.getMobileNumber());
		bank.setSmsCount(bankDto.getSmsCount());
		bank.setIsoUrl(bankDto.getIsoUrl());
		bank.setPortNumber(bankDto.getPortNumber());
		bank.setBankTranferAccount(bankDto.getBankTranferAccount());
		for (int i = 0; i < merchantList.length; i++) {

			Merchant merchant = merchantRepository.findOne(Long.parseLong(merchantList[i]));
			merchants.add(merchant);
		}
		bank.setAcquiringInstitutionIdentificationCode(bankDto.getAcquiringInstitutionIdentificationCode());
		bank.setCardAcceptorTerminalIdentification(bankDto.getCardAcceptorTerminalIdentification());
		bank.setMerchantType(bankDto.getMerchantType());
		bank.setMerchant(merchants);
		// bank.setIsDedicated(bankDto.getIsDedicated());
		try {
			bank = bankRepository.save(bank);
			smsPackageApi.saveSmsPackage(bank);
			User user = addDefaultUser(bank);
			user.setUserTemplate(userTemplateRepository.findByUserTemplate(bankDto.getUserTemplate()));
			userRepository.save(user);
			createBankAccount(bank);
			// createOperatorAndChannelPartnerAccount(bank, bankDto);
			emailApi.sendEmail(UserType.Bank, bank.getSwiftCode(), bank.getEmail(), user.getSecretKey(),
					bank.getName());
			bankRepository.save(bank);
			if (bank.getMobileNumber() != null) {
				addSmsLog(bank, SmsType.REGISTRATION, user);
			}
			logger.info("Bank Added.");
		} catch (Exception e) {
			logger.debug("Could Not Add Bank to Database");
			e.printStackTrace();
		}

	}

	public void createOperatorAndChannelPartnerAccount(Bank bank, BankDTO bankDto) {
		if (bankDto.getOperatorAccountNumber() != null || !bankDto.getOperatorAccountNumber().trim().isEmpty()) {
			Account operatorAccount = accountRepository.findAccountByAccountNumber(bankDto.getOperatorAccountNumber());
			if (operatorAccount == null) {
				operatorAccount = new Account();
				operatorAccount.setBalance(0.0);
				operatorAccount.setAccountHead(StringConstants.OPERATOR_ACCOUNT_HEAD);
				operatorAccount.setAccountNumber(bankDto.getOperatorAccountNumber());
				operatorAccount.setAccountType(AccountType.OPERATOR);
				operatorAccount.setBank(bank);
				operatorAccount.setUser(userRepository.findByUsername(bank.getSwiftCode()));
				accountRepository.save(operatorAccount);
			}
			bank.setOperatorAccountNumber(bankDto.getOperatorAccountNumber());
		}
		if (bankDto.getChannelPartnerAccountNumber() != null
				|| !bankDto.getChannelPartnerAccountNumber().trim().isEmpty()) {
			Account channelPartnerAccount = accountRepository
					.findAccountByAccountNumber(bankDto.getChannelPartnerAccountNumber());
			if (channelPartnerAccount == null) {
				channelPartnerAccount = new Account();
				channelPartnerAccount.setBalance(0.0);
				channelPartnerAccount.setAccountHead(bank.getChannelPartner().getName());
				channelPartnerAccount.setAccountNumber(bankDto.getChannelPartnerAccountNumber());
				channelPartnerAccount.setAccountType(AccountType.CHANNELPARTNER);
				channelPartnerAccount.setBank(bank);
				channelPartnerAccount.setUser(userRepository.findByUsername(bank.getSwiftCode()));
				accountRepository.save(channelPartnerAccount);
			}
			bank.setChannelPartnerAccountNumber(bankDto.getChannelPartnerAccountNumber());
		}
		bankRepository.save(bank);
	}

	public void createBankAccount(Bank bank) {
		Account account = new Account();
		account.setBank(bank);
		account.setBalance(0.0);
		account.setAccountHead(bank.getName());
		account.setAccountType(AccountType.BANK);
		account.setAccountNumber(accountUtil.generateAccountNumber());
		account = accountRepository.save(account);
		bank.setAccountNumber(account.getAccountNumber());
		bankRepository.save(bank);
	}

	@Override
	public void saveBulkBank(BankDTO bankDto) {

		City city = cityRepository.findCitByCityNamesAndState(bankDto.getState(), bankDto.getCity());
		Bank bank = new Bank();
		bank.setName(bankDto.getName());
		bank.setSwiftCode(bankDto.getSwiftCode());
		bank.setCity(city);
		bank.setAddress(bankDto.getAddress());
		bank.setStatus(Status.Active);
		bank.setLicenseCount(bankDto.getLicenseCount());
		bank.setLicenseExpiryDate(DateUtil.convertStringToDate(bankDto.getLicenseExpiryDate()));
		bank.setCreatedBy(userRepository.findOne(bankDto.getCreatedById()));
		bank.setEmail(bankDto.getEmail());
		bank.setMobileNumber(bankDto.getMobileNumber());

		try {
			bank = bankRepository.save(bank);
			smsPackageApi.saveSmsPackage(bank);
			User user = addDefaultUser(bank);
			emailApi.sendEmail(UserType.Bank, bank.getSwiftCode(), bank.getEmail(), user.getSecretKey(),
					bank.getName());
			createBankAccount(bank);
			if (bank.getMobileNumber() != null) {
				addSmsLog(bank, SmsType.REGISTRATION, user);
			}

		} catch (Exception e) {
			logger.debug("Could Not Add Bank to Database");
			e.printStackTrace();
		}
	}

	private void addSmsLog(Bank bank, SmsType smsType, User user) {

		SmsLog smsLog = new SmsLog();
		smsLog.setBank(bank);
		smsLog.setSmsForUser(UserType.Bank);
		smsLog.setSmsFor(String.valueOf(bank.getId()));
		smsLog.setSmsFromUser(UserType.Admin);
		smsLog.setSmsFrom(AuthenticationUtil.getCurrentUser().getId());
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLog.setSmsType(smsType);
		smsLog.setForMobileNo(bank.getMobileNumber());
		if (smsType.equals(SmsType.REGISTRATION)) {
			String message = StringConstants.REGISTRATION_SMS;
			message = message.replace("{username}", user.getUserName());
			message = message.replace("{password}", user.getSecretKey());
			smsLog.setMessage(message);
		}

		smsLogRepository.save(smsLog);

	}

	private User addDefaultUser(Bank bank) {

		User user = new User();
		Map<String, String> passwordMap = passwordGenerator.generatePassword();
		user.setSecretKey(passwordMap.get("password"));
		user.setPassword(passwordMap.get("passwordEncoded"));
		user.setUserName(bank.getSwiftCode());
		user.setAddress(bank.getAddress());
		user.setAssociatedId(bank.getId());
		user.setUserType(UserType.Bank);
		user.setCity(bank.getCity());
		user.setStatus(Status.Active);
		user.setAuthority(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED);
		user.setFirstLogin(true);
		user = userRepository.save(user);

		UserLog userLog = new UserLog();
		userLog.setUser(user);
		userLog.setRemarks("User Created ");
		userLogRepository.save(userLog);
		return user;
		// send username and pass word via email
	}

	@Override
	public List<BankDTO> findBank() {
		List<Bank> bankList = new ArrayList<Bank>();
		bankList = bankRepository.findBank();
		return ConvertUtil.ConvertBankDTOtoBank(bankList);
	}

	@Override
	public List<BankDTO> getAllBank() {
		List<Bank> bankList = ConvertUtil.convertIterableToList(bankRepository.findAll());
		if (!bankList.isEmpty()) {
			return ConvertUtil.ConvertBankDTOtoBank(bankList);
		}
		return null;
	}

	@Override
	public Bank getBankById(long bankId) {
		// TODO Auto-generated method stub
		return bankRepository.findOne(bankId);
	}

	@Override
	public BankDTO getBankDtoById(long bankId) {
		Bank bank = bankRepository.findOne(bankId);
		if (bank != null) {
			return ConvertUtil.convertBank(bankRepository.findOne(bankId));
		}
		return null;

	}

	@Override
	public void editBank(BankDTO bankDto) {
		City city = cityRepository.findCityByIdAndState(bankDto.getState(), Long.parseLong(bankDto.getCity()));
		Bank bank = getBankById(bankDto.getId());
		bank.setAddress(bankDto.getAddress());
		bank.setCity(city);
		bank.setLicenseExpiryDate(DateUtil.convertStringToDate(bankDto.getLicenseExpiryDate()));
		List<Merchant> merchantList = new ArrayList<Merchant>();
		String[] merchantArray = bankDto.getMerchants();
		for (int i = 0; i < merchantArray.length; i++) {
			Merchant merchant = merchantRepository.findOne(Long.parseLong(merchantArray[i]));
			merchantList.add(merchant);
		}
		bank.setMerchant(merchantList);
		bank.setAcquiringInstitutionIdentificationCode(bankDto.getAcquiringInstitutionIdentificationCode());
		bank.setCardAcceptorTerminalIdentification(bankDto.getCardAcceptorTerminalIdentification());
		bank.setMerchantType(bankDto.getMerchantType());
		bank.setIsoUrl(bankDto.getIsoUrl());
		bank.setPortNumber(bankDto.getPortNumber());
		bank.setBankTranferAccount(bankDto.getBankTranferAccount());
		bank.setName(bankDto.getName());
		bank.setEmail(bankDto.getEmail());
		bankRepository.save(bank);
	}

	@Override
	public List<BankDTO> getBankByCountry(String isoThree) {
		return null;
		// List<Bank>
		// bankList=ConvertUtil.convertIterableToList(bankRepository.findByBankCountry(isoThree));
		// return ConvertUtil.ConvertBankDTOtoBank(bankList);
	}

	@Override
	public BankDTO getBankByName(String name) {
		Bank bank = bankRepository.findByBank(name);
		if (bank != null) {
			return ConvertUtil.convertBank(bank);
		}
		return null;
	}

	@Override
	public List<BankDTO> getBankByNameLike(String name) {
		List<Bank> bankList = ConvertUtil.convertIterableToList(bankRepository.getBankByNameLike(name));
		if (!bankList.isEmpty()) {
			return ConvertUtil.ConvertBankDTOtoBank(bankList);
		}
		return null;
	}

	@Override
	public List<BankDTO> geteBankByStatus(Status status) {
		List<Bank> bankList = ConvertUtil.convertIterableToList(bankRepository.getBankByStatus(status));
		if (!bankList.isEmpty()) {
			return ConvertUtil.ConvertBankDTOtoBank(bankList);
		}
		return null;
	}

	@Override
	@Scheduled(fixedRate = 12000)
	public void sendSmsForBankUser() throws Exception {
		List<SmsLog> smsLogs = smsLogRepository.findSmsLogBySmsTypeAndStatusAndUserType(SmsType.REGISTRATION,
				SmsStatus.INITIATED, UserType.Bank);
		// System.out.println(smsLogs + " THIS IS SMS LOGS");
		Map<String, String> responseMap = new HashMap<String, String>();
		if (!smsLogs.isEmpty()) {
			for (int i = 0; i < smsLogs.size(); i++) {
				// System.out.println("BANK SMS SEND");
				responseMap = sparrowApi.sendSMS(smsLogs.get(i).getMessage(),
						smsLogs.get(i).getBank().getMobileNumber());
				smsLogs.get(i).setStatus(SmsStatus.QUEUED);
				SmsLog sLog = smsLogRepository.save(smsLogs.get(i));
				if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
					sLog.setStatus(SmsStatus.DELIVERED);
					sLog.setDelivered(new Date());
					smsLogRepository.save(sLog);
				} else {
					sLog.setResponseCode(responseMap.get("code"));
					sLog.setResponseMessage(responseMap.get("response"));
					smsLogRepository.save(sLog);
				}
			}
		}

	}

	@Override
	public List<MerchantDTO> getMerchantByBank(String bankCode) {

		Bank bank = bankRepository.findBankByCode(bankCode);
		if (bank != null) {
			List<Merchant> merchantList = bank.getMerchant();
			if (!merchantList.isEmpty()) {
				return ConvertUtil.convertMerchantToDTO(merchantList);
			}
		}
		return null;
	}

	@Override
	public BankDTO getBankByCode(String bankCode) {
		Bank bank = bankRepository.findBankByCode(bankCode);
		if (bank != null) {
			return ConvertUtil.convertBank(bank);
		}
		return null;
	}

	@Override
	public BankAccountSettingsDto getActiveBankAccountsSettingsByBank(String bankCode) {
		BankAccountSettings bankAccounts = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(bankCode, Status.Active);
		if (bankAccounts != null) {
			return bankAccountSettingsConverter.convertToDto(bankAccounts);
		}
		return null;
	}

	@Override
	public BankAccountSettingsDto saveBankAccountSetting(BankAccountSettingsDto bankAccountsettingDto) {
		BankAccountSettings bankAccountSetting = new BankAccountSettings();
		bankAccountSetting.setBank(bankRepository.findBankByCode(bankAccountsettingDto.getBank()));
		bankAccountSetting.setOperatorAccountNumber(bankAccountsettingDto.getOperatorAccountNumber());
		bankAccountSetting.setChannelPartnerAccountNumber(bankAccountsettingDto.getChannelPartnerAccountNumber());
		bankAccountSetting.setBankPoolAccountNumber(bankAccountsettingDto.getBankPoolAccountNumber());
		bankAccountSetting.setBankParkingAccountNumber(bankAccountsettingDto.getBankParkingAccountNumber());
		bankAccountSetting.setStatus(Status.Active);
		bankAccountSetting = bankAccountSettingsRepository.save(bankAccountSetting);
		// now create the same replica in our acocunt for ledger posting
		return bankAccountSettingsConverter.convertToDto(bankAccountSetting);
	}

	@Override
	public BankAccountSettingsDto editBankAccountSetting(BankAccountSettingsDto bankAccounts) {
		BankAccountSettings bankAccountSetting = bankAccountSettingsRepository.findOne(bankAccounts.getId());
		bankAccountSetting.setStatus(bankAccounts.getStatus());
		bankAccountSetting.setOperatorAccountNumber(bankAccounts.getOperatorAccountNumber());
		bankAccountSetting.setChannelPartnerAccountNumber(bankAccounts.getChannelPartnerAccountNumber());
		bankAccountSetting.setBankParkingAccountNumber(bankAccounts.getBankParkingAccountNumber());
		bankAccountSetting.setBankPoolAccountNumber(bankAccounts.getBankPoolAccountNumber());

		bankAccountSettingsRepository.save(bankAccountSetting);
		return bankAccountSettingsConverter.convertToDto(bankAccountSetting);
	}

	@Override
	public BankAccountSettingsDto getBankAccountsSettingsByBank(String bankCode) {
		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCode(bankCode);
		if (bankAccountSettings != null) {
			return bankAccountSettingsConverter.convertToDto(bankAccountSettings);
		}
		return null;
	}

	@Override
	public BankMerchantAccountsDto saveBankMerchantAccount(BankMerchantAccountsDto merchantAccountDto) {
		BankMerchantAccounts merchantAccount = new BankMerchantAccounts();
		merchantAccount.setMerchant(merchantRepository.findOne(merchantAccountDto.getMerchantId()));
		merchantAccount.setBank(bankRepository.findBankByCode(merchantAccountDto.getBank()));
		merchantAccount.setAccountNumber(merchantAccountDto.getAccountNumber());
		merchantAccount.setStatus(Status.Active);
		merchantAccount = bankMerchantAccountsRepository.save(merchantAccount);
		return bankMerchantAccountsConverter.convertToDto(merchantAccount);
	}

	@Override
	public List<BankMerchantAccountsDto> getbankMerchantAccountsByBank(String bankCode) {
		Bank bank = bankRepository.findBankByCode(bankCode);
		List<BankMerchantAccounts> bankMerchantAccountsList = bankMerchantAccountsRepository.findByBank(bank);
		if (!bankMerchantAccountsList.isEmpty()) {
			return bankMerchantAccountsConverter.convertToDtoList(bankMerchantAccountsList);
		}
		return null;
	}

	@Override
	public BankMerchantAccountsDto getBankMerchantAccountsById(Long id) {
		BankMerchantAccounts merchantAccount = bankMerchantAccountsRepository.findOne(id);
		if (merchantAccount != null) {
			return bankMerchantAccountsConverter.convertToDto(merchantAccount);
		}
		return null;
	}

	@Override
	public BankMerchantAccountsDto editBankMerchantAccount(BankMerchantAccountsDto merchantAccountDto) {
		BankMerchantAccounts merchantAccount = bankMerchantAccountsRepository.findOne(merchantAccountDto.getId());
		merchantAccount.setAccountNumber(merchantAccountDto.getAccountNumber());
		merchantAccount.setStatus(merchantAccountDto.getStatus());
		bankMerchantAccountsRepository.save(merchantAccount);
		merchantAccountDto = bankMerchantAccountsConverter.convertToDto(merchantAccount);
		merchantAccountDto.setBank(merchantAccount.getBank().getSwiftCode());
		return merchantAccountDto;
	}

	@Override
	public List<MerchantDTO> getMerchantWithoutMerchantAccountByBank(String bankCode) {
		Bank bank = bankRepository.findBankByCode(bankCode);
		if (bank != null) {
			List<BankMerchantAccounts> merchantAccountList = bankMerchantAccountsRepository.findByBank(bank);
			List<Merchant> merchantList = bank.getMerchant();
			if (!merchantList.isEmpty()) {
				if (!merchantAccountList.isEmpty()) {
					List<Merchant> newMerchantList = new ArrayList<>();
					for (Merchant merchant : merchantList) {
						boolean valid = true;
						for (BankMerchantAccounts merchantAccount : merchantAccountList) {
							if (merchant == merchantAccount.getMerchant()) {
								valid = false;
							}
							if (valid) {
								newMerchantList.add(merchant);
							}
						}
					}
					if (!newMerchantList.isEmpty()) {
						return ConvertUtil.convertMerchantToDTO(newMerchantList);
					}
					return null;
				}
				return ConvertUtil.convertMerchantToDTO(merchantList);
			}
		}
		return null;
	}

	@Override
	public boolean updateSmsCount(Long bankId, Integer smsCount) {
		Bank bank = bankRepository.findOne(bankId);
		bank.setSmsCount(bank.getSmsCount() + smsCount);
		bankRepository.save(bank);
		return true;
	}

	@Override
	public List<BankDTO> getAllBankWithBalance() {
		List<Bank> bankList = ConvertUtil.convertIterableToList(bankRepository.findAll());
		if (!bankList.isEmpty()) {
			List<BankDTO> bankDtoList = ConvertUtil.ConvertBankDTOtoBank(bankList);
			for (BankDTO bank : bankDtoList) {
				Double creditLimitOfBank = creditLimitRepository.findCreditAmountByBank(bank.getId());
				Double accountBalanceOfBank = accountRepository.findBalanceOfBank(bank.getId(), AccountType.BANK);
				if (creditLimitOfBank != null) {
					bank.setCreditLimit(creditLimitOfBank);
				} else {
					bank.setCreditLimit(0.0);
				}
				if (accountBalanceOfBank != null) {
					bank.setRemainingBalance(accountBalanceOfBank);
				} else {
					bank.setRemainingBalance(0.0);
				}
			}
			return bankDtoList;
		}
		return null;
	}

	@Override
	public boolean addLicenseCount(Long bankId, Integer licenseCount) {
		System.out.println("lcoount"+licenseCount);
		Bank bank = bankRepository.findOne(bankId);
		bank.setLicenseCount(bank.getLicenseCount() + licenseCount);
		bankRepository.save(bank);
		return true;
	}

	@Override
	public boolean addRemarks(Long bankId, String remarks) {
		Bank bank = bankRepository.findOne(bankId);
		bank.setRemarks(remarks);
		bankRepository.save(bank);
		return true;
	}

	@Override
	public String getBankEmail(Long bankid) {
		return bankRepository.findOne(bankid).getEmail();
	}

	@Override
	public Boolean getIsMessageSent(Long bankid) {
		return bankRepository.findOne(bankid).getIsAlertSent();
	}

	@Override
	public ProfileAccountSettingDto getProfileAccountsSettingsByBank(long bankId) {
		Bank bank = bankRepository.findOne(bankId);
		ProfileAccountSetting profileAccount = profileAccountSetting.findByBankAndStatus(bank, Status.Active);
		ProfileAccountSettingDto profileAccountDto = null;
		if (profileAccount != null) {
			profileAccountDto = new ProfileAccountSettingDto();
			profileAccountDto.setId(profileAccount.getId());
			profileAccountDto.setRegistrationAccount(profileAccount.getRegistrationAccount());
			if (profileAccount.getPinRestAccount() != null)
				profileAccountDto.setPinResetAccount(profileAccount.getPinRestAccount());
			profileAccountDto.setRenewAccount(profileAccount.getRenewAccount());
			profileAccountDto.setStatus(profileAccount.getStatus());
		}
		return profileAccountDto;
	}

	@Override
	public void saveBankProfileAccountSetting(ProfileAccountSettingDto profileAccountsDto, long bankId) {

		ProfileAccountSetting profileAccount = new ProfileAccountSetting();
		profileAccount.setRegistrationAccount(profileAccountsDto.getRegistrationAccount());
		profileAccount.setStatus(Status.Active);
		profileAccount.setBank(bankRepository.findOne(bankId));
		profileAccount.setPinRestAccount(profileAccountsDto.getPinResetAccount());
		profileAccount.setRenewAccount(profileAccountsDto.getRenewAccount());
		profileAccountSetting.save(profileAccount);
	}

	@Override
	public void editProfileAccountSetting(ProfileAccountSettingDto bankAccountsDto, long bankId) {
		ProfileAccountSetting profileAccount = profileAccountSetting.findOne(bankAccountsDto.getId());
		profileAccount.setRegistrationAccount(bankAccountsDto.getRegistrationAccount());
		profileAccount.setPinRestAccount(bankAccountsDto.getPinResetAccount());
		profileAccount.setRenewAccount(bankAccountsDto.getRenewAccount());
		profileAccountSetting.save(profileAccount);
	}

	@Override
	public List<BankDTO> getBankWithoutOauthClient() {
		List<Bank> bankList = bankRepository.findBankWithoutOauthClient();
		if (bankList != null) {
			return ConvertUtil.ConvertBankDTOtoBank(bankList);
		}
		return null;
	}

	@Override
	public Long countByChannelPartner(long channelPartnerId) {
		return bankRepository.countByChannelPartner(channelPartnerId);
	}

	@Override
	public List<BankDTO> getAllBankOfChannelPartner(Long channelPartnerId) {
		List<Bank> bankList = bankRepository.findByChannelPartner(channelPartnerId);
		if (bankList != null && !bankList.isEmpty()) {
			return ConvertUtil.ConvertBankDTOtoBank(bankList);
		}
		return null;
	}

	@Override
	public PagablePage geteBanks(Integer page, String name, String swiftCode, String fromDate, String toDate) {
		PagablePage pageble = new PagablePage();
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		int starting = ((page * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Bank.class);
		Criteria countCriteria = session.createCriteria(Bank.class);

		if (fromDate != null && !fromDate.trim().equals("")) {
			criteria.add(Restrictions.gt("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
			countCriteria.add(Restrictions.gt("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
		}
		if (toDate != null && !toDate.trim().equals("")) {
			criteria.add(Restrictions.lt("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
			countCriteria.add(Restrictions.lt("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
		}
		if (name != null && !name.trim().equals("")) {
			criteria.add(Restrictions.like("name", name + "%"));
			countCriteria.add(Restrictions.like("name", name + "%"));
		}
		if (swiftCode != null && !swiftCode.trim().equals("")) {
			criteria.add(Restrictions.like("swiftCode", swiftCode + "%"));
			countCriteria.add(Restrictions.like("swiftCode", swiftCode + "%"));
		}

		countCriteria.setProjection(Projections.rowCount());
		int totalpage = (int) ((Integer.valueOf(countCriteria.uniqueResult().toString())) / PageInfo.pageList);
		System.out.println("the total page is : " + totalpage);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(page, totalpage, PageInfo.numberOfPage);

		criteria.setFirstResult(starting);
		criteria.setMaxResults((int) PageInfo.pageList);
		List<Bank> bankList = criteria.list();
		List<BankDTO> bankDtoList = null;
		if (bankList != null && !bankList.isEmpty()) {
			bankDtoList = ConvertUtil.ConvertBankDTOtoBank(bankList);
			for (BankDTO bank : bankDtoList) {
				Double creditLimitOfBank = creditLimitRepository.findCreditAmountByBank(bank.getId());
				Double accountBalanceOfBank = accountRepository.findBalanceOfBank(bank.getId(), AccountType.BANK);
				if (creditLimitOfBank != null) {
					bank.setCreditLimit(creditLimitOfBank);
				} else {
					bank.setCreditLimit(0.0);
				}
				if (accountBalanceOfBank != null) {
					bank.setRemainingBalance(accountBalanceOfBank);
				} else {
					bank.setRemainingBalance(0.0);
				}
			}
		}
		pageble.setObject(bankDtoList);
		pageble.setPageList(pagesnumbers);
		pageble.setLastpage(totalpage);
		pageble.setCurrentPage(page);
		return pageble;
	}

	@Override
	public boolean checkBankCbsStatus(Long bankId) {
		if (isoApi.echoBank(bankId)) {
			Bank bank = bankRepository.findOne(bankId);
			bank.setCbsStatus(Status.Active);
			bankRepository.save(bank);
			return true;
		} else {
			Bank bank = bankRepository.findOne(bankId);
			bank.setCbsStatus(Status.Inactive);
			bankRepository.save(bank);
			return false;
		}
	}

	@Override
	public List<BankDTO> getBankByCbsStatus(Status status) {
		List<Bank> bankList =bankRepository.findByCbsStatus(status);
		if(bankList != null)
			return ConvertUtil.ConvertBankDTOtoBank(bankList);
		return null;
	}

}