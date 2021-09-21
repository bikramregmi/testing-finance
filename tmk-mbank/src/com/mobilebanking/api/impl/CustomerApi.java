package com.mobilebanking.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerLogApi;
import com.mobilebanking.api.ICustomerProfileApi;
import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.converter.BeneficiaryConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.BankOAuthClient;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.CustomerBenificiary;
import com.mobilebanking.entity.CustomerProfile;
import com.mobilebanking.entity.FcmServerSetting;
import com.mobilebanking.entity.Iso8583Log;
import com.mobilebanking.entity.NonFinancialTransaction;
import com.mobilebanking.entity.OAuthAccessToken;
import com.mobilebanking.entity.ProfileAccountSetting;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.SmsMode;
import com.mobilebanking.entity.User;
import com.mobilebanking.fcm.PushNotification;
import com.mobilebanking.model.AccountMode;
import com.mobilebanking.model.BankServiceCharge;
import com.mobilebanking.model.BeneficiaryDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.IsoRequestType;
import com.mobilebanking.model.IsoResponseCode;
import com.mobilebanking.model.IsoStatus;
import com.mobilebanking.model.MiniStatementRespose;
import com.mobilebanking.model.NonFinancialTransactionType;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerBenificiaryRepository;
import com.mobilebanking.repositories.CustomerProfileRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.FcmServerSettingRepository;
import com.mobilebanking.repositories.Iso8583LogRepository;
import com.mobilebanking.repositories.NonFinancialTransactionRepository;
import com.mobilebanking.repositories.OAuthAccessTokenRepository;
import com.mobilebanking.repositories.ProfileAccountSettingRepository;
import com.mobilebanking.repositories.SmsLogRepository;
import com.mobilebanking.repositories.SmsModeRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.DateTypes;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.FileUtils;
import com.mobilebanking.util.PageInfo;
import com.mobilebanking.util.PasswordGenerator;
import com.mobilebanking.util.STANGenerator;
import com.mobilebanking.util.StringConstants;
import com.wallet.iso8583.core.NavaJeevanIsoCore;
import com.wallet.iso8583.model.MiniStatement;
import com.wallet.iso8583.network.NetworkTransportNavaJeevan;
import com.wallet.iso8583.parser.ParseFields;
import com.wallet.sms.SparrowSmsResponseCode;

@Service
public class CustomerApi implements ICustomerApi {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordGenerator passwordGenerator;

	@Autowired
	private BankBranchRepository bankBranchRepository;

	@Autowired
	private CustomerBankAccountRepository customerbankAccountRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private SmsModeRepository smsModeRepository;

	@Autowired
	private SmsLogRepository smsLogRepository;

	@Autowired
	private ISparrowApi sparrowApi;

	@Autowired
	private IEmailApi emailApi;

	@Autowired
	private CustomerBenificiaryRepository customerBenificiaryRepository;

	@Autowired
	private BeneficiaryConverter benificiaryConverter;

	@Autowired
	private NavaJeevanIsoCore navaJeevanIsoCore;

	@Autowired
	private NetworkTransportNavaJeevan networkTransporstNavaJeevan;

	@Autowired
	private ParseFields fieldParser;

	@Autowired
	private Iso8583LogRepository isoLogRepository;

	@Autowired
	private NonFinancialTransactionRepository ministatementLogRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ConvertUtil convertUtil;

	@Autowired
	private BankOAuthClientRepository bankAuthRepository;

	@Autowired
	private ICustomerProfileApi customerProfileApi;

	@Autowired
	private ProfileAccountSettingRepository profileAccontSettingRepository;

	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	@Autowired
	private PushNotification pushNotification;

	@Autowired
	private FcmServerSettingRepository fcmServerSettingRepository;

	@Autowired
	private ICustomerLogApi customerLogApi;

	@Autowired
	private OAuthAccessTokenRepository oAuthAccessTokenRepository;

	@Override
	public boolean saveCustomer(CustomerDTO customerDto) {
		BankBranch bankBranch = bankBranchRepository.findOne(Long.parseLong(customerDto.getBankBranch()));
		Customer getcustomer = customerRepository.findByMobileNumberAndBank(customerDto.getMobileNumber(),
				bankBranch.getBank());
		User createdBy = userRepository.findOne(Long.parseLong(customerDto.getCreatedBy()));
		if (getcustomer != null) {
			CustomerBankAccount customerAccount = customerbankAccountRepository.findByAccountNumberAndBankId(
					bankBranch.getBranchCode() + customerDto.getAccountNumber(), bankBranch.getBank().getId());
			if (customerAccount != null) {
				return false;
			}
			CustomerBankAccount customerBankAccount = new CustomerBankAccount();
			customerBankAccount.setAccountNumber(customerDto.getAccountNumber());
			customerBankAccount.setBranch(bankBranch);
			customerBankAccount.setCustomer(getcustomer);
			customerBankAccount.setAccountMode(customerDto.getAccountMode());
			customerBankAccount.setStatus(Status.Active);
			customerBankAccount.setPrimary(false);
			customerBankAccount = customerbankAccountRepository.save(customerBankAccount);

			customerLogApi.save(getcustomer.getUniqueId(),
					"Added Another Account with acc number" + customerBankAccount.getAccountNumber(),
					createdBy.getUserName());
			createCustomerLogLinkedAccounts(getcustomer.getUniqueId(), customerBankAccount.getBranch());
		} else {
			createNewCustomer(customerDto);
		}

		return true;
	}

	public void createCustomerLogLinkedAccounts(String uniqueId, BankBranch bankBranch) {
		String message = smsModeRepository.findSmsModeMessageByBankAndSmsType(bankBranch.getBank().getId(),
				SmsType.MULTIPLE, Status.Active);
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);

		SmsLog smsLog = new SmsLog();
		smsLog.setBank(bankBranch.getBank());
		smsLog.setBankBranch(bankBranch);
		if (message != null) {
			message = message.replace("{username}", customer.getUser().getSmsUserName());
		} else {
			message = StringConstants.LINKED_ACCOUNT_REGISTRATION;
			message = message.replace("{username}", customer.getUser().getSmsUserName());
			message = message.replace("{bank}", bankBranch.getBank().getName().split(" ")[0]);
		}
		smsLog.setMessage(message);
		smsLog.setSmsForUser(UserType.Customer);
		smsLog.setSmsFor(customer.getUniqueId());
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLog.setSmsFrom(bankBranch.getId());
		smsLog.setSmsFromUser(UserType.BankBranch);
		smsLog.setSmsType(SmsType.MULTIPLE);
		smsLog.setForMobileNo(customer.getMobileNumber());
		smsLogRepository.save(smsLog);
	}

	@Override
	public boolean saveBulkCustomer(CustomerDTO customerDto, UserType userType) {
		Bank bank = bankRepository.findOne(Long.parseLong(customerDto.getBank()));
		Customer getcustomer = customerRepository.findByMobileNumberAndBank(customerDto.getMobileNumber(), bank);
		if (getcustomer != null) {
			CustomerBankAccount customerAccount = customerbankAccountRepository
					.findByAccountNumberAndBankId(customerDto.getAccountNumber(), bank.getId());
			if (customerAccount != null) {
				return false;
			}
			User createdBy = userRepository.findOne(Long.parseLong(customerDto.getCreatedBy()));
			BankBranch bankBranch = bankBranchRepository.findByBankAndBranchCode(bank.getId(),
					customerDto.getBankBranchCode());
			CustomerBankAccount customerBankAccount = new CustomerBankAccount();
			customerBankAccount.setAccountNumber(customerDto.getAccountNumber());
			customerBankAccount.setBranch(bankBranch);
			customerBankAccount.setCustomer(getcustomer);
			customerBankAccount.setAccountMode(customerDto.getAccountMode());
			customerBankAccount.setStatus(Status.Active);
			customerBankAccount.setPrimary(false);
			customerBankAccount = customerbankAccountRepository.save(customerBankAccount);

			customerLogApi.save(getcustomer.getUniqueId(),
					"Added Another Account with acc number" + customerBankAccount.getAccountNumber(),
					createdBy.getUserName());
			createCustomerLogLinkedAccounts(getcustomer.getUniqueId(), customerBankAccount.getBranch());
		} else {
			createNewBulkCustomer(customerDto, userType);
		}
		return true;
	}

	private void createNewBulkCustomer(CustomerDTO customerDto, UserType userType) {
		String[] spilittedaddress = customerDto.getAddressOne().split("-");
		City city = cityRepository.findByName(spilittedaddress[0]);
		Bank bank = new Bank();
		if (userType == UserType.Bank) {
			bank = bankRepository.findOne(Long.parseLong(customerDto.getBank()));
		} else if (userType == UserType.BankBranch) {
			BankBranch branch = bankBranchRepository.findOne(Long.parseLong(customerDto.getBankBranch()));
			bank = branch.getBank();
		}
		BankBranch bankBranch = bankBranchRepository.findByBankAndBranchCode(bank.getId(),
				customerDto.getBankBranchCode());
		Customer customer = new Customer();
		List<SmsMode> smsModeList = new ArrayList<SmsMode>();
		User createdBy = userRepository.findOne(Long.parseLong(customerDto.getCreatedBy()));
		if (customerDto.getMiddleName() != null && !(customerDto.getMiddleName().trim().equals(""))) {
			customer.setFullName(
					customerDto.getFirstName() + " " + customerDto.getMiddleName() + " " + customerDto.getLastName());
		} else {
			customer.setFullName(customerDto.getFirstName() + " " + customerDto.getLastName());
		}
		customer.setFirstName(customerDto.getFirstName());
		customer.setMiddleName(customerDto.getMiddleName());
		customer.setLastName(customerDto.getLastName());
		customer.setAddress(customerDto.getAddressOne());
		customer.setMobileNumber(customerDto.getMobileNumber());
		customer.setMobileBanking(customerDto.isMobileBanking());
		customer.setCity(city);
		customer.setUniqueId(passwordGenerator.generateUUID());
		customer.setMobileBanking(customerDto.isMobileBanking());
		customer.setGprsSubscribed(customerDto.isGprsSubscribed());
		customer.setAlertType(customerDto.isAlertType());
		customer.setCreratedTimeMilliSeconds(System.currentTimeMillis());
		customer.setAppService(customerDto.isAppService());
		customer.setSmsService(customerDto.isSmsService());
		customer.setStatus(CustomerStatus.Hold);
		customer.setFirstApprove(false);
		customer.setCreatedBy(createdBy);
		customer.setBank(bank);
		customer.setBankBranch(bankBranch);
		if (customerDto.isSmsService() == true) {
			customer.setSmsSubscribed(true);
		} else {
			customer.setSmsSubscribed(false);
		}
		customer.setIsRegstered(true);
		if (customerDto.getSmsType() != null) {
			for (int i = 0; i < customerDto.getSmsType().size(); i++) {
				SmsMode smsMode = smsModeRepository.findSmsModeByBankAndSmsType(bankBranch.getBank().getName(),
						Status.Active, customerDto.getSmsType().get(i));
				smsModeList.add(smsMode);
			}
			customer.setSmsMode(smsModeList);
		}
		customer = customerRepository.save(customer);
		if (customer != null)
			try {
				User user = createUser(customer, bank);
				customer.setUser(user);
				customer = customerRepository.save(customer);
				/*
				 * if (customer.getEmail() != null) {
				 * emailApi.sendEmail(UserType.Customer, user.getUserName(),
				 * customer.getEmail(), user.getSecretKey(), bank.getName()); }
				 * createSmsLog(customer, SmsType.REGISTRATION, createdBy);
				 */
				// Bank bank = bankBranch.getBank();
				bank.setCustomerCount(bank.getCustomerCount() + 1);
				bank.setLicenseCount(bank.getLicenseCount() - 1);
				bankRepository.save(bank);

				CustomerBankAccount customerBankAccount = new CustomerBankAccount();
				customerBankAccount.setAccountNumber(customerDto.getAccountNumber());
				customerBankAccount.setBranch(bankBranch);
				customerBankAccount.setCustomer(customer);
				customerBankAccount.setAccountMode(customerDto.getAccountMode());
				customerBankAccount.setStatus(Status.Active);

				customerbankAccountRepository.save(customerBankAccount);

				customerLogApi.save(customer.getUniqueId(), "Customer Registration Successfully",
						createdBy.getUserName());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private void createNewCustomer(CustomerDTO customerDto) {
		Customer customer = new Customer();
		List<SmsMode> smsModeList = new ArrayList<SmsMode>();
		City city = null;
		try {
			city = cityRepository.findCityByIdAndState(customerDto.getState(), Long.parseLong(customerDto.getCity()));

		} catch (NumberFormatException e) {
			e.printStackTrace();
			city = cityRepository.findByName(customerDto.getCity());
		}

		User createdBy = userRepository.findOne(Long.parseLong(customerDto.getCreatedBy()));
		BankBranch bankBranch = bankBranchRepository.findOne(Long.parseLong(customerDto.getBankBranch()));
		customer.setCity(city);
		customer.setFirstName(customerDto.getFirstName());
		customer.setMiddleName(customerDto.getMiddleName());
		customer.setLastName(customerDto.getLastName());
		customer.setAddress(customerDto.getAddressOne());
		customer.setAddressTwo(customerDto.getAddressTwo());
		customer.setAddressThree(customerDto.getAddressThree());
		customer.setCreatedBy(createdBy);
		customer.setBank(bankBranch.getBank());
		customer.setBankBranch(bankBranch);
		if (customerDto.getEmail() != null) {
			customer.setEmail(customerDto.getEmail());
		}
		customer.setFirstApprove(true);
		customer.setMobileNumber(customerDto.getMobileNumber());
		customer.setPhoneNumber(customerDto.getLandline());
		customer.setStatus(customerDto.getStatus());
		if (!(customer.getStatus().equals(CustomerStatus.Approved)))
			customer.setFirstApprove(false);

		customer.setUniqueId(passwordGenerator.generateUUID());
		customer.setMobileBanking(customerDto.isMobileBanking());
		customer.setGprsSubscribed(customerDto.isGprsSubscribed());
		customer.setAlertType(customerDto.isAlertType());
		customer.setCreratedTimeMilliSeconds(System.currentTimeMillis());
		customer.setAppService(customerDto.isAppService());
		customer.setSmsService(customerDto.isSmsService());
		if (customerDto.isSmsService() == true) {
			customer.setSmsSubscribed(true);
		} else {
			customer.setSmsSubscribed(false);
		}
		customer.setIsRegstered(true);
		if (customerDto.getSmsType() != null) {
			for (int i = 0; i < customerDto.getSmsType().size(); i++) {
				SmsMode smsMode = smsModeRepository.findSmsModeByBankAndSmsType(bankBranch.getBank().getName(),
						Status.Active, customerDto.getSmsType().get(i));
				smsModeList.add(smsMode);
			}
			customer.setSmsMode(smsModeList);
		}
		customer = customerRepository.save(customer);

		Bank bank = bankBranch.getBank();
		bank.setCustomerCount(bank.getCustomerCount() + 1);
		bank.setLicenseCount(bank.getLicenseCount() - 1);
		bank = bankRepository.save(bank);

		if (customer != null)
			try {
				User user = createUser(customer, bank);
				customer.setUser(user);
				customer = customerRepository.save(customer);
				CustomerBankAccount customerBankAccount = new CustomerBankAccount();
				customerBankAccount.setAccountNumber(customerDto.getAccountNumber());
				customerBankAccount.setBranch(bankBranch);
				customerBankAccount.setCustomer(customer);
				customerBankAccount.setAccountMode(customerDto.getAccountMode());
				customerBankAccount.setStatus(Status.Active);
				customerBankAccount.setPrimary(true);
				customerbankAccountRepository.save(customerBankAccount);
				customerProfileApi.addCustomerToProfile(customerDto.getCustomerProfileId(), customer);
				CustomerProfile customerProfile = customerProfileRepository.findOne(customerDto.getCustomerProfileId());
				if (customerProfile.getRegistrationCharge() != null && customerProfile.getRegistrationCharge() > 0) {
					HashMap<String, String> registrationCharge;
					try {
						if (customer.getStatus().equals(CustomerStatus.Approved)) {
							registrationCharge = deductServiceCharge(customerBankAccount,
									customerDto.getCustomerProfileId(), BankServiceCharge.Registration);
							// HashMap<String,String> registrationCharge =
							// fundTransfer(customerBankAccount.getAccountNumber(),
							// destinationAccountNumber, amount, bank,
							// remarksOne, remarksTwo);
							System.out.println("IsO LOG " + registrationCharge.get("isoCode"));
							if (registrationCharge.get("status").equals("success")) {
								customer.setApprovalMessage("Registration charge succesfully paid.");
								customer.setFirstApprove(true);
								customer = customerRepository.save(customer);
							} else {
								customer.setApprovalMessage(registrationCharge.get("Result Message"));
								customer.setStatus(CustomerStatus.Hold);
								customer.setFirstApprove(false);
								customer = customerRepository.save(customer);
								user.setStatus(Status.Inactive);
								user = userRepository.save(user);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (customer.getStatus().equals(CustomerStatus.Approved)) {
					createSmsLog(customer, SmsType.REGISTRATION, createdBy);
				}

				if (customer.getEmail() != null && customer.getStatus().equals(CustomerStatus.Approved)) {
					emailApi.sendEmail(UserType.Customer, user.getUserName(), customer.getEmail(), user.getSecretKey(),
							bank.getName());
				}
				customerLogApi.save(customer.getUniqueId(), "Customer Registration Successfully",
						createdBy.getUserName());

			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public HashMap<String, String> deductServiceCharge(CustomerBankAccount customerBankAccount, long profileId,
			BankServiceCharge bankServiceChargeType) {
		HashMap<String, String> hashResponse = new HashMap<>();
		String remarks = null;
		String destinationAccount = null;
		double amount = 0;
		CustomerProfile customerProfile = customerProfileRepository.findOne(profileId);
		ProfileAccountSetting profileAccountSetting = profileAccontSettingRepository
				.findByBankAndStatus(customerBankAccount.getBranch().getBank(), Status.Active);
		if (profileAccountSetting == null) {
			hashResponse.put("status", "failure");
			hashResponse.put("Result Message", "No Account setting found");
			return hashResponse;
		}
		if (bankServiceChargeType.equals(BankServiceCharge.Registration)) {
			if (profileAccountSetting.getRegistrationAccount() == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message", "No Registration Account found");
				return hashResponse;
			}
			remarks = "Registration fee charge";
			destinationAccount = profileAccountSetting.getRegistrationAccount();
			amount = customerProfile.getRegistrationCharge();
		} else if (bankServiceChargeType.equals(BankServiceCharge.PIN_RESET)) {
			if (profileAccountSetting.getPinRestAccount() == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message", "No Pin Reset Account found");
				return hashResponse;
			}
			remarks = "Pin Resest fee charge";
			destinationAccount = profileAccountSetting.getPinRestAccount();
			amount = customerProfile.getPinResetCharge();
		} else if (bankServiceChargeType.equals(BankServiceCharge.Renew)) {
			if (profileAccountSetting.getRenewAccount() == null) {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message", "No Renew Account found");
				return hashResponse;
			}
			remarks = "Renewal fee charge";
			destinationAccount = profileAccountSetting.getRenewAccount();
			amount = customerProfile.getRenewCharge();
		}
		HashMap<String, String> isoResponse;
		if (StringConstants.IS_PRODUCTION) {
			isoResponse = fundTransfer(customerBankAccount.getAccountNumber(), destinationAccount, amount,
					customerBankAccount.getBranch().getBank(), customerBankAccount.getCustomer().getMobileNumber(),
					remarks, "Fee Charge");
		} else {
			isoResponse = new HashMap<>();
			isoResponse.put("status", "success");
		}
		if (isoResponse.get("status").equals("failure")) {
			hashResponse.put("status", "failure");
			// hashResponse.put("Result Message", "Could not process the
			// transaction,Please try again later");
			try {
				if (IsoResponseCode.getEnumByKey(isoResponse.get("code")) != null) {
					hashResponse.put("isoCode", isoResponse.get("code"));
					hashResponse.put("Result Message", IsoResponseCode.getValueByKey(isoResponse.get("code")));
				} else {
					hashResponse.put("isoCode", isoResponse.get("code"));
					hashResponse.put("Result Message", "Unable to transfer fund.");
				}
			} catch (Exception e) {
				// e.printStackTrace();
				if (isoResponse.get("code") != null) {
					hashResponse.put("isoCode", isoResponse.get("code"));
				}
				hashResponse.put("Result Message", "Error while transferring fund.");
			}
			// hashResponse.put("Result Message", "Could not process the
			// transaction,Please try again later");
		} else {
			return isoResponse;
		}
		return hashResponse;
	}

	@Override
	public BeneficiaryDTO saveBenificiary(BeneficiaryDTO beneficiaryDto, CustomerDTO sender) {
		BeneficiaryDTO customerbeneficiaryDto = new BeneficiaryDTO();
		BankBranch bankBranch = bankBranchRepository.findOne(beneficiaryDto.getBankBranchId());
		Customer customer;
		customer = customerRepository.findOne(sender.getId());
		CustomerBenificiary beneficiary = customerBenificiaryRepository
				.findByAccountNumberAndSender(bankBranch.getBranchCode() + beneficiaryDto.getAccountNumber(), customer);
		if (beneficiary != null) {
			customerbeneficiaryDto.setBeneficiaryFlag(true);
			return customerbeneficiaryDto;
		}
		CustomerBenificiary customerBenificiary = new CustomerBenificiary();
		if (beneficiaryDto.getBankId() != null) {
			Bank bank = bankRepository.findOne(beneficiaryDto.getBankId());
			customerBenificiary.setBank(bank);
		} else {
			Bank bank = bankBranch.getBank();
			customerBenificiary.setBank(bank);
		}
		customerBenificiary.setBankBranch(bankBranch);
		customerBenificiary.setSender(customer);
		customerBenificiary.setAccountNumber(bankBranch.getBranchCode() + beneficiaryDto.getAccountNumber());
		customerBenificiary.setReciever(beneficiaryDto.getName());
		customerBenificiary.setStatus(Status.Active);
		customerBenificiary = customerBenificiaryRepository.save(customerBenificiary);
		return benificiaryConverter.convertToDto(customerBenificiary);
	}

	@Override
	public BeneficiaryDTO editBenificiary(BeneficiaryDTO beneficiaryDto, CustomerDTO sender) {
		BankBranch bankBranch = bankBranchRepository.findOne(beneficiaryDto.getBankBranchId());
		CustomerBenificiary customerBenificiary = customerBenificiaryRepository.findOne(beneficiaryDto.getId());
		if (beneficiaryDto.getBankId() != null) {
			Bank bank = bankRepository.findOne(beneficiaryDto.getBankId());
			customerBenificiary.setBank(bank);
		} else {
			Bank bank = bankBranch.getBank();
			customerBenificiary.setBank(bank);
		}
		customerBenificiary.setBankBranch(bankBranch);
		customerBenificiary.setAccountNumber(bankBranch.getBranchCode() + beneficiaryDto.getAccountNumber());
		customerBenificiary.setReciever(beneficiaryDto.getName());
		customerBenificiary.setStatus(Status.Active);
		customerBenificiary = customerBenificiaryRepository.save(customerBenificiary);
		return benificiaryConverter.convertToDto(customerBenificiary);
	}

	@Override
	public boolean deleteBenificiary(Long id) {
		CustomerBenificiary customerBenificiary = customerBenificiaryRepository.findOne(id);
		customerBenificiaryRepository.delete(customerBenificiary);
		return true;
	}

	public User createUser(Customer customer, Bank bank) {
		BankOAuthClient bankAouthClient = bankAuthRepository.findByBank(bank);
		String clientID = bankAouthClient.getoAuthClientId();
		Map<String, String> passwordMap = passwordGenerator.generateMPinForCustomer();
		String pin = PasswordGenerator.generatePin();
		User user = new User();
		user.setAssociatedId(customer.getId());
		user.setAddress(customer.getAddress());
		user.setAuthority(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED);
		user.setCity(customer.getCity());
		user.setEmail(customer.getEmail());
		user.setUserName(clientID + customer.getMobileNumber());
		user.setFirstLogin(true);
		user.setPin(pin);
		user.setSecretKey(passwordMap.get("password"));
		user.setPassword(passwordMap.get("passwordEncoded"));
		user.setToken(passwordGenerator.generateToken());
		user.setVerificationCode(passwordGenerator.generateVerificationCode());
		if (customer.getStatus().equals(CustomerStatus.Approved))
			user.setStatus(Status.Active);
		else
			user.setStatus(Status.Inactive);
		user.setUserType(UserType.Customer);
		user.setBank(bank);
		user.setSmsUserName(customer.getMobileNumber());
		user = userRepository.save(user);
		return user;
	}

	public void createSmsLog(Customer customer, SmsType smsType, User createdBy) throws Exception {
		SmsLog smsLog = new SmsLog();
		// customer =
		// customerRepository.findCustomerByUniqueId(customer.getUniqueId());
		User user = customer.getUser();
		long bankId = 0;
		long bankBranchId = 0;
		boolean createdByBank = false;
		String authority = createdBy.getAuthority();
		if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
				|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
			bankId = createdBy.getAssociatedId();
			createdByBank = true;
		} else if (authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
				|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)) {
			BankBranch bankBranch = bankBranchRepository.findOne(createdBy.getAssociatedId());
			bankId = bankBranch.getBank().getId();
			bankBranchId = bankBranch.getId();
		}
		smsLog.setBank(bankRepository.findOne(bankId));
		String message = smsModeRepository.findSmsModeMessageByBankAndSmsType(bankId, smsType, Status.Active);
		smsLog.setForMobileNo(customer.getMobileNumber());
		if (message != null) {
			// message = message.replace("password", "mpin");
			message = message.replace("{username}", user.getSmsUserName());
			message = message.replace("{mpin}", user.getSecretKey());
			message = message.replace("{bank}", smsLog.getBank().getName().split(" ")[0]);
			// message = message.replace("{pin}", user.getPin());
			smsLog.setBank(bankRepository.findOne(bankId));
			if (bankBranchId > 0) {
				smsLog.setBankBranch(bankBranchRepository.findOne(bankBranchId));
			}
			smsLog.setSmsForUser(UserType.Customer);
			smsLog.setSmsFor(customer.getUniqueId());
			smsLog.setMessage(message);
			smsLog.setStatus(SmsStatus.INITIATED);
			smsLog.setSmsFromUser(createdByBank ? UserType.Bank : UserType.BankBranch);
			smsLog.setSmsFrom(createdByBank ? bankId : bankBranchId);
			smsLog.setSmsType(smsType);
			smsLogRepository.save(smsLog);
		} else {
			message = StringConstants.REGISTRATION_CUSTOMER;
			message = message.replace("{username}", user.getSmsUserName());
			message = message.replace("{mpin}", user.getSecretKey());
			// message = message.replace("{pin}", user.getPin());

			message = message.replace("{bank}", smsLog.getBank().getName().split(" ")[0]);
			smsLog.setSmsForUser(UserType.Customer);
			smsLog.setSmsFor(customer.getUniqueId());
			smsLog.setMessage(message);
			smsLog.setBank(bankRepository.findOne(bankId));
			if (bankBranchId > 0) {
				smsLog.setBankBranch(bankBranchRepository.findOne(bankBranchId));
			}
			smsLog.setStatus(SmsStatus.INITIATED);
			smsLog.setSmsFromUser(createdByBank ? UserType.Bank : UserType.BankBranch);
			smsLog.setSmsFrom(createdByBank ? bankId : bankBranchId);
			smsLog.setSmsType(smsType);
			smsLogRepository.save(smsLog);
		}
	}

	@Override
	public CustomerDTO getCustomerById(long customerId) throws ClientException {
		Customer customer = customerRepository.findOne(customerId);
		if (customer == null) {
			throw new ClientException("Invalid Account Number");
		} else {
			return convertUtil.convertCustomer(customer);
		}
	}

	@Override
	public List<CustomerDTO> findCustomerOfParticularBankBranch(long bankBranchId) {
		List<Customer> customerList = customerRepository.listCustomerByBranch(bankBranchId);
		if (customerList != null) {
			return convertUtil.convertCustomerToDto(customerList);
		}
		return null;
	}

	@Override
	public List<CustomerDTO> findCustomerOfParticularBank(long bankId) {
		List<Customer> customerList = customerRepository.listCustomerByBank(bankId);
		if (customerList != null) {
			return convertUtil.convertCustomerToDto(customerList);
		}
		return null;
	}

	@Override
	public CustomerDTO updateCustomer(long customerId, CustomerStatus status) throws IOException, JSONException {
		Customer customer = customerRepository.findOne(customerId);
		customer.setStatus(status);
		customer = customerRepository.save(customer);

		return null;
	}

	@Override
	public CustomerDTO findCustomerByUniqueId(String uniqueId) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		if (customer != null) {
			return convertUtil.convertCustomer(customer);
		}
		return null;
	}

	@Override
	public CustomerDTO editCustomer(CustomerDTO customerDto) throws IOException, JSONException {
		Customer customer = customerRepository.findCustomerByUniqueId(customerDto.getUniqueId());
		if (customer == null) {
			return null;
		}
		City city = cityRepository.findCityByIdAndState(customerDto.getState(), Long.parseLong(customerDto.getCity()));
		customer.setAddress(customerDto.getAddressOne());
		customer.setAddressTwo(customerDto.getAddressTwo());
		customer.setSmsService(customerDto.isSmsService());
		customer.setAppService(customerDto.isAppService());
		customer.setFirstName(customerDto.getFirstName());
		customer.setMiddleName(customerDto.getMiddleName());
		customer.setLastName(customerDto.getLastName());
		customer.setComment(customerDto.getComment());
		customer.setCommentedby(AuthenticationUtil.getCurrentUser().getUserName());
		customer.setCity(city);
		customer.setLastModified(new Date());
		customer = customerRepository.save(customer);
		customerProfileApi.updateCustomerToProfile(customerDto.getCustomerProfileId(), customer);

		customerLogApi.save(customer.getUniqueId(), customerDto.getComment(),
				AuthenticationUtil.getCurrentUser().getUserName());
		return convertUtil.convertCustomer(customer);

	}

	@Override
	@Scheduled(fixedDelay = 10000)
	public void sendSmsToRegisteredCustomer() throws Exception {
		List<SmsLog> smsLogs = smsLogRepository.findSmsLogBySmsTypeAndStatusAndUserType(SmsType.REGISTRATION,
				SmsStatus.INITIATED, UserType.Customer);
		for (SmsLog smsLog : smsLogs) {
			smsLog.setStatus(SmsStatus.QUEUED);
			smsLogRepository.save(smsLog);
		}
		if (!smsLogs.isEmpty()) {
			for (SmsLog smsLog : smsLogs) {
				smsLog = smsLogRepository.findOne(smsLog.getId());
				if (smsLog.getBank().getSmsCount() <= 0) {
					smsLog.setStatus(SmsStatus.BANK_OUT_OF_SMS);
					smsLogRepository.save(smsLog);
				} else {
					try {
						String uniqueId = smsLog.getSmsFor();
						Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
						String message = smsLog.getMessage();
						Map<String, String> responseMap = sparrowApi.sendSMS(message, customer.getMobileNumber());
						if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
							smsLog.setStatus(SmsStatus.DELIVERED);
							message = smsModeRepository.findSmsModeMessageByBankAndSmsType(smsLog.getBank().getId(),
									smsLog.getSmsType(), Status.Active);
							if (message != null) {
								message = message.replace("{username}", customer.getUser().getSmsUserName());
								message = message.replace("{mpin}", "#####");
							} else {
								message = StringConstants.REGISTRATION_CUSTOMER;
								message = message.replace("{username}", customer.getUser().getSmsUserName());
								message = message.replace("{mpin}", "#####");
							}
							smsLog.setMessage(message);
							smsLogRepository.save(smsLog);
							Bank bank = bankRepository.findOne(smsLog.getBank().getId());
							bank.setSmsCount(bank.getSmsCount() - 1);
							bankRepository.save(bank);
						} else {
							smsLog.setStatus(SmsStatus.INITIATED);
							smsLogRepository.save(smsLog);
						}
					} catch (Exception e) {
						e.printStackTrace();
						smsLog.setStatus(SmsStatus.INITIATED);
						smsLogRepository.save(smsLog);
					}
				}

			}
		}
	}

	@Override
	@Scheduled(fixedRate = 12000)
	public void sendSmsToMultipleAccountsForCustomer() throws Exception {
		List<SmsLog> smsLogs = smsLogRepository.findSmsLogBySmsTypeAndStatusAndUserType(SmsType.MULTIPLE,
				SmsStatus.INITIATED, UserType.Customer);
		Map<String, String> responseMap = new HashMap<String, String>();
		if (!smsLogs.isEmpty()) {
			SmsLog sLog = new SmsLog();
			for (int i = 0; i < smsLogs.size(); i++) {
				if (smsLogs.get(i).getBank().getSmsCount() <= 0) {
					sLog.setStatus(SmsStatus.BANK_OUT_OF_SMS);
					smsLogRepository.save(smsLogs.get(i));
				} else {
					try {
						// before sending sms check if the bank exceeds the
						// smspackage or not
						String uniqueId = smsLogs.get(i).getSmsFor();
						Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
						String message = smsLogs.get(i).getMessage();
						responseMap = sparrowApi.sendSMS(message, customer.getMobileNumber());
						smsLogs.get(i).setStatus(SmsStatus.QUEUED);
						sLog = smsLogRepository.save(smsLogs.get(i));
						if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
							sLog.setStatus(SmsStatus.DELIVERED);
							smsLogRepository.save(sLog);
							Bank bank = bankRepository.findOne(smsLogs.get(i).getBank().getId());
							bank.setSmsCount(bank.getSmsCount() - 1);
							bankRepository.save(bank);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/*
	 * @Override public void sendPinAndPasswordToRegisteredCustomer() throws
	 * Exception { // add later if necessary }
	 */

	@Override
	public Double getCustomerBankBalance(String uniqueId, String accountNumber) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		if (customer != null) {
			// get bank of the customer so that we can call dynamically to
			// bankISO
			customerbankAccountRepository.findCustomerAccountByAccountNumber(accountNumber, customer.getId());
			// from here we make call to bank using ISO8583 with account and
			// customer information
			CustomerBankAccount customerBankAccount = customerbankAccountRepository
					.findCustomerAccountByAccountNumber(accountNumber, customer.getId());
			Bank bank = customerBankAccount.getBranch().getBank();
			// if (accountNumber.length() < 23) {
			// accountNumber = customerBankAccount.getBranch().getBranchId() +
			// accountNumber;
			// System.out.println("here + IN THE ACCOUNT PART");
			// }
			HashMap<String, String> isoData = new HashMap<String, String>();
			String date = DateUtil.convertDateToString(new Date());
			String[] dateArray = date.split("/");
			if (bank.getDesc1() != null) {
				isoData.put("2", bank.getDesc1());
			} else {
				isoData.put("2", "123467890123456");
			}
			isoData.put("3", "301000");
			isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
			isoData.put("11", STANGenerator.StanForIso(""));
			isoData.put("12", DateUtil.convertDateToStringForIso(new Date()).substring(4, 10));
			isoData.put("13", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
			isoData.put("14", "0000");
			isoData.put("15", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
			isoData.put("18", bank.getMerchantType());
			isoData.put("25", "00");
			isoData.put("22", "000");
			isoData.put("32", bank.getAcquiringInstitutionIdentificationCode()); // would
																					// be
																					// in
																					// Bank
																					// Settings
			// isoData.put("37", DateUtil.convertDateToStringForIso(new Date())
			// + "96");
			isoData.put("41", bank.getCardAcceptorTerminalIdentification());
			// isoData.put("32", "15548116");
			isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) + "98");
			// isoData.put("41", "15548116");
			isoData.put("49", "524");
			isoData.put("102", accountNumber);
			String packedData = "";
			String response = "";
			try {
				packedData = navaJeevanIsoCore.packMessage("0200", isoData);

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				response = networkTransporstNavaJeevan.transportIsoMessage(bank.getIsoUrl(),
						Integer.parseInt(bank.getPortNumber()), packedData);
				if (response.equalsIgnoreCase("failure")) {
					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setIsoResponse(response);
					isoLog.setStatus(IsoStatus.NORESPONSE);
					isoLogRepository.save(isoLog);
				}

				HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(response);
				if (unpackedData.get("39").equals("00")) {
					// now parse the field 47 if account holder name is to be
					// displayed

					NonFinancialTransaction balancelog = new NonFinancialTransaction();
					balancelog.setBank(customerBankAccount.getBranch().getBank());
					balancelog.setBankBranch(customerBankAccount.getBranch());
					balancelog.setCustomer(customer);
					balancelog.setAccountNumber(accountNumber);
					balancelog.setStatus(IsoStatus.SUCCESS);
					balancelog.setTransactionId("" + System.currentTimeMillis());
					balancelog.setTransactionType(NonFinancialTransactionType.BALANCEENQUIRY);
					ministatementLogRepository.save(balancelog);

					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setIsoResponse(response);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setStatus(IsoStatus.SUCCESS);
					isoLog.setResponseCode("00");
					isoLogRepository.save(isoLog);
					Double balance = fieldParser.parseAmountField54(unpackedData.get("54"));
					return balance;
				} else {
					NonFinancialTransaction balancelog = new NonFinancialTransaction();
					balancelog.setBank(customerBankAccount.getBranch().getBank());
					balancelog.setBankBranch(customerBankAccount.getBranch());
					balancelog.setCustomer(customer);
					balancelog.setAccountNumber(accountNumber);
					balancelog.setStatus(IsoStatus.FAILURE);
					balancelog.setTransactionType(NonFinancialTransactionType.BALANCEENQUIRY);
					balancelog.setTransactionId("" + System.currentTimeMillis());
					ministatementLogRepository.save(balancelog);

					Iso8583Log isoLog = new Iso8583Log();
					isoLog.setIsoRequest(packedData);
					isoLog.setIsoResponse(response);
					isoLog.setRequestType(IsoRequestType.BALANCEINQUIRY);
					isoLog.setStatus(IsoStatus.FAILURE);
					isoLog.setResponseCode(unpackedData.get("39"));
					isoLogRepository.save(isoLog);
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public MiniStatementRespose getMiniStatementOfUser(String uniqueId, String accountNumber) throws Exception {
		MiniStatementRespose ministatementResponse = new MiniStatementRespose();
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		CustomerBankAccount customerBankAccount = customerbankAccountRepository
				.findByAccountNumberAndBankId(accountNumber, customer.getBank().getId());
		// CustomerBankAccount customerBankAccount =
		// customerbankAccountRepository.findCustomerAccountByAccountNumber(accountNumber,
		// customer.getId());
		// if (accountNumber.length() < 23) {
		// accountNumber = customerBankAccount.getBranch().getBranchCode() +
		// accountNumber;
		// }

		Bank bank = customerBankAccount.getBranch().getBank();
		HashMap<String, String> isoData = new HashMap<String, String>();

		if (bank.getDesc1() != null) {
			isoData.put("2", bank.getDesc1());
		} else {
			isoData.put("2", "123467890123456");
		}
		isoData.put("3", "350000");
		isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
		isoData.put("11", STANGenerator.StanForIso("211"));
		isoData.put("12", DateUtil.convertDateToStringForIso(new Date()).substring(4, 10));
		isoData.put("13", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("14", "0000");
		isoData.put("15", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("18", bank.getMerchantType());
		isoData.put("25", "00");
		isoData.put("22", "000");
		// isoData.put("32", "15548116");
		isoData.put("32", bank.getAcquiringInstitutionIdentificationCode()); // would
																				// be
																				// in
																				// Bank
																				// Settings
		// isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) +
		// "96");
		isoData.put("41", bank.getCardAcceptorTerminalIdentification());

		isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) + "25");
		// isoData.put("41", "15548116");
		isoData.put("49", "524");
		isoData.put("102", accountNumber);

		String packedData = "";
		String response = "";
		try {
			packedData = navaJeevanIsoCore.packMessage("0200", isoData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = networkTransporstNavaJeevan.transportIsoMessage(bank.getIsoUrl(),
					Integer.parseInt(bank.getPortNumber()), packedData);
			// response =
			// "04620210F23A400106838400000000000600000015123467890123456350000000000000000103101580721116822582910301031124408155481178275270015548117011AJIT
			// KHADKA240001D00000000300020171029001D00000003000020171029001D00000000300020171029001D00000001500020171029001D00000003000020171029066D00000038460320170814501C00000143000020170730050D00000109978820170716066D00000041183020170714501C000000010000201707025240401001524C0000012538641002524C0000012538642400100100100002663000002100";
			if (response.equalsIgnoreCase("failure")) {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setRequestType(IsoRequestType.MINISTATEMENT);
				isoLog.setIsoResponse(response);
				isoLog.setStatus(IsoStatus.NORESPONSE);
				isoLogRepository.save(isoLog);
			}
			HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(response);
			if (unpackedData.get("39").equals("00")) {
				// now parse the field 47 if account holder name is to be
				// displayed
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.MINISTATEMENT);
				isoLog.setStatus(IsoStatus.SUCCESS);
				isoLog.setResponseCode("00");
				isoLogRepository.save(isoLog);
				if (unpackedData.get("39").equals("00")) {
					// now parse the fields
					List<MiniStatement> miniStatementList = fieldParser
							.getMiniStatementsField48(unpackedData.get("48"));
					// if(unpackedData.get("54").equals(""))
					Double balance = fieldParser.parseAmountField54(unpackedData.get("54"));
					NonFinancialTransaction miniStatementLog = new NonFinancialTransaction();
					miniStatementLog.setBank(customerBankAccount.getBranch().getBank());
					miniStatementLog.setBankBranch(customerBankAccount.getBranch());
					miniStatementLog.setCustomer(customer);
					miniStatementLog.setAccountNumber(accountNumber);
					miniStatementLog.setStatus(IsoStatus.SUCCESS);
					miniStatementLog.setTransactionId("" + System.currentTimeMillis());
					miniStatementLog.setTransactionType(NonFinancialTransactionType.MINISTATEMENT);
					ministatementLogRepository.save(miniStatementLog);

					Collections.sort(miniStatementList, new Comparator<MiniStatement>() {
						@Override
						public int compare(MiniStatement o1, MiniStatement o2) {
							return o1.getTransactionDate().compareTo(o2.getTransactionDate());
						}
					});

					ministatementResponse.setAvailableBalance(balance);
					ministatementResponse.setMinistatementList(miniStatementList);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					ministatementResponse.setBalanceDate(dateFormat.format(new Date()));
					return ministatementResponse;
				}
			} else {

				NonFinancialTransaction miniStatementLog = new NonFinancialTransaction();
				miniStatementLog.setBank(customerBankAccount.getBranch().getBank());
				miniStatementLog.setBankBranch(customerBankAccount.getBranch());
				miniStatementLog.setCustomer(customer);
				miniStatementLog.setAccountNumber(accountNumber);
				miniStatementLog.setStatus(IsoStatus.FAILURE);
				miniStatementLog.setTransactionId("" + System.currentTimeMillis());
				miniStatementLog.setTransactionType(NonFinancialTransactionType.MINISTATEMENT);
				ministatementLogRepository.save(miniStatementLog);

				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.MINISTATEMENT);
				isoLog.setStatus(IsoStatus.FAILURE);
				isoLog.setResponseCode(unpackedData.get("39"));
				isoLogRepository.save(isoLog);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * @Override public CustomerDTO getCustomerByUserId(long userId) { Customer
	 * customer = customerRepository.getCustomerByUser(userId); if (customer !=
	 * null) { return convertUtil.convertCustomer(customer); } return null; }
	 */

	/*
	 * @Override public String getMiniStatement(String uniqueId, String
	 * accountNumber) { Customer customer =
	 * customerRepository.findCustomerByUniqueId(uniqueId); if (customer !=
	 * null) { CustomerBankAccount account = customerbankAccountRepository
	 * .findCustomerAccountByAccountNumber(accountNumber, customer.getId()); if
	 * (account != null) { // from here we make call to bank using ISO8583 with
	 * account // and parse the data in a particular format so that it can be //
	 * send to applications
	 * 
	 * return
	 * "2017-05-06 Test Cr 1200|2017-05-05 Testing Db 5000|2017-05-01 Testing Db 3000"
	 * ; }
	 * 
	 * } return null; }
	 */

	public boolean getCustomerByMobileNo(String from, String clientId) {
		BankOAuthClient bankAouthClient = bankAuthRepository.findByOAuthClientId(clientId);
		Bank bank = bankAouthClient.getBank();
		Customer customer = customerRepository.findByMobileNumberAndBank(from, bank);
		if (customer != null) {
			return true;
		} else
			return false;
	}

	@Override
	public CustomerDTO getCustomerBalanceByMobileNumber(String from, String clientId) {
		BankOAuthClient bankAouthClient = bankAuthRepository.findByOAuthClientId(clientId);
		Bank bank = bankAouthClient.getBank();
		Customer customer = customerRepository.findByMobileNumberAndBank(from, bank);
		if (customer != null) {
			return convertUtil.convertCustomer(customer);
		} else
			return null;
	}

	@Override
	public boolean resendSmsForVerification(String userName) throws Exception {
		Customer customer = customerRepository.getCustomerByUserName(userName);
		Map<String, String> smsResponse = new HashMap<String, String>();
		boolean sendSms = true;
		if (customer == null) {
			return false;
		}

		String message = StringConstants.VERIFICATION_MESSAGE;
		String verificationCode = passwordGenerator.generateVerificationCode();

		message = message.replace("{verificationCode}", verificationCode);
		try {
			User user = userRepository.findOne(customer.getUser().getId());
			user.setVerificationCode(verificationCode);
			user.setWebServiceLoginCount(user.getWebServiceLoginCount() + 1);
			user = userRepository.save(user);
		} catch (Exception e) {
			return false;
		}
		smsResponse = sparrowApi.sendSMS(message, customer.getMobileNumber());
		if (smsResponse.get("code").equals("200")) {
			sendSms = createSmsLogForResendVerification(customer, SmsType.VERIFICATION,
					StringConstants.VERIFICATION_MESSAGE.replace("{verificationCode}", "#####"), SmsStatus.DELIVERED,
					smsResponse.get("code"));

			return true;
		} else {
			sendSms = createSmsLogForResendVerification(customer, SmsType.VERIFICATION, message, SmsStatus.FAILED,
					smsResponse.get("code"));
		}
		return false;
	}

	@Override
	public String resendToken(String userName) {
		User user = userRepository.findByUsername(userName);
		if (user.getWebServiceLoginCount() == 0) {
			return "unverified";
		} else {
			String token = userRepository.getUserToken(userName);
			return token;
		}

	}

	public boolean createSmsLogForResendVerification(Customer customer, SmsType smsType, String message,
			SmsStatus smsStatus, String responseCode) {
		SmsLog smsLog = new SmsLog();
		User createdBy = customer.getCreatedBy();
		smsLog.setForMobileNo(customer.getMobileNumber());
		Bank bank;
		if (createdBy.getUserType().equals(UserType.BankBranch)) {
			BankBranch bankBranch = bankBranchRepository.findOne(createdBy.getAssociatedId());
			bank = bankBranch.getBank();
			smsLog.setSmsFrom(bankBranch.getId());
			smsLog.setSmsFromUser(UserType.BankBranch);

		} else {
			bank = bankRepository.findOne(createdBy.getAssociatedId());
			smsLog.setBank(bank);
			smsLog.setSmsFromUser(UserType.Bank);
			smsLog.setSmsFrom(bank.getId());
		}
		if (smsStatus.equals(SmsStatus.DELIVERED)) {
			bank.setSmsCount(bank.getSmsCount() - 1);
			bankRepository.save(bank);
		}
		smsLog.setMessage(message);
		smsLog.setSmsForUser(UserType.Customer);
		smsLog.setSmsFor(customer.getUniqueId());
		smsLog.setStatus(smsStatus);
		smsLog.setResponseMessage(SparrowSmsResponseCode.getValue(responseCode));
		smsLog.setResponseCode(responseCode);
		if (responseCode.equals("200")) {
			smsLog.setDelivered(new Date());
		}
		try {
			smsLogRepository.save(smsLog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BeneficiaryDTO> listBenificiary(Long id, String clientId) {
		Customer sender = customerRepository.findOne(id);
		BankOAuthClient bankAouthClient = bankAuthRepository.findByOAuthClientId(clientId);
		Bank bank = bankAouthClient.getBank();
		List<CustomerBenificiary> beneficiary = customerBenificiaryRepository.listBenificiary(sender, Status.Active,
				bank);
		if (!beneficiary.isEmpty()) {
			return benificiaryConverter.convertToDtoList(beneficiary);
		}
		return null;
	}

	@Override
	public boolean verifyToken(String userName, String token) {
		User user = userRepository.findByUsername(userName);
		if (user != null) {
			if (user.getToken().equals(token)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean resetPin(String uniqueId) throws UnknownHostException, InvocationTargetException {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		CustomerProfile customerProfile = customerProfileRepository.findByCustomer(customer);
		if (customerProfile == null) {
			customerLogApi.save(customer.getUniqueId(), "Pin Reset error, No Profile Found For the Customer",
					AuthenticationUtil.getCurrentUser().getUserName());
			return false;
		}
		CustomerBankAccount customerBankAccount = customerbankAccountRepository.findPrimaryAccount(customer, true);
		if (customerProfile.getPinResetCharge() != null && customerProfile.getPinResetCharge() > 0) {
			HashMap<String, String> registrationrResponse;
			if (StringConstants.IS_PRODUCTION) {
				registrationrResponse = deductServiceCharge(customerBankAccount, customerProfile.getId(),
						BankServiceCharge.PIN_RESET);
			} else {
				registrationrResponse = new HashMap<>();
				registrationrResponse.put("status", "success");
			}

			if ((registrationrResponse.get("status").equals("failure"))) {
				customerLogApi.save(customer.getUniqueId(), registrationrResponse.get("Result Message"),
						AuthenticationUtil.getCurrentUser().getUserName());
				return false;
			}
		}
		User currentUser = AuthenticationUtil.getCurrentUser();
		Bank bank = new Bank();
		BankBranch bankBranch = null;
		if (currentUser.getUserType() == UserType.Bank) {
			bank = bankRepository.findOne(currentUser.getAssociatedId());
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			bankBranch = bankBranchRepository.findOne(currentUser.getAssociatedId());
			bank = bankBranchRepository.findOne(currentUser.getAssociatedId()).getBank();
		}
		Map<String, String> pinMap = passwordGenerator.generateMPinForCustomer();
		User user = customer.getUser();
		user.setPassword(pinMap.get("passwordEncoded"));
		user.setSecretKey(pinMap.get("password"));
		userRepository.save(user);

		// send sms to customer
		SmsLog smsLog = createSmsLogForPinReset(customer, pinMap.get("password"));
		Map<String, String> response = sparrowApi.sendSMS(smsLog.getMessage(), customer.getMobileNumber());
		smsLog.setStatus(SmsStatus.QUEUED);
		String message = StringConstants.PIN_RESET_MESSAGE;
		message = message.replace("{mPin}", "#####");
		smsLog.setMessage(message);
		smsLog = smsLogRepository.save(smsLog);
		if (response.get("code").equalsIgnoreCase("200")) {
			smsLog.setStatus(SmsStatus.DELIVERED);
			smsLog.setDelivered(new Date());
			smsLog.setBank(bank);
			if (bankBranch != null) {
				smsLog.setBankBranch(bankBranch);
			}
			smsLog.setSmsType(SmsType.PIN_RESET);
			smsLog.setSmsForUser(UserType.Customer);
			if (currentUser.getUserType().equals(UserType.Bank)) {
				smsLog.setSmsFromUser(UserType.Bank);
			} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
				smsLog.setSmsFromUser(UserType.BankBranch);
			}
			smsLog.setSmsFrom(bank.getId());
			smsLogRepository.save(smsLog);
			bank.setSmsCount(bank.getSmsCount() - 1);
			bankRepository.save(bank);
		}
		customerLogApi.save(customer.getUniqueId(), "mPin Changed Successfully",
				AuthenticationUtil.getCurrentUser().getUserName());
		return true;
	}

	public SmsLog createSmsLogForPinReset(Customer customer, String mPin) {
		SmsLog smsLog = new SmsLog();
		String message = StringConstants.PIN_RESET_MESSAGE;
		message = message.replace("{mPin}", mPin);

		smsLog.setMessage(message);
		smsLog.setSmsFor(customer.getUniqueId());
		smsLog.setSmsForUser(UserType.Customer);
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLog.setForMobileNo(customer.getMobileNumber());

		smsLog = smsLogRepository.save(smsLog);
		return smsLog;
	}

	@Override
	public void changeAccountNumber(String uniqueId, String currentAccountNumber, String newAccountNumber,
			AccountMode accountMode, String comment) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		CustomerBankAccount customerBankAccount = customerbankAccountRepository
				.findCustomerAccountByAccountNumber(currentAccountNumber, customer.getId());
		customerBankAccount.setAccountNumber(newAccountNumber);
		customerBankAccount.setAccountMode(accountMode);
		customerbankAccountRepository.save(customerBankAccount);

		customer.setLastModified(new Date());
		customer.setComment(comment);
		customer.setCommentedby(AuthenticationUtil.getCurrentUser().getUserName());
		customer = customerRepository.save(customer);

		customerLogApi.save(customer.getUniqueId(), comment, AuthenticationUtil.getCurrentUser().getUserName());
	}

	@Override
	public void addCustomerBankAccount(String uniqueId, String accountNumber, long bankBranchId,
			AccountMode accountMode, String comment) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		BankBranch bankBranch = bankBranchRepository.findOne(bankBranchId);
		CustomerBankAccount customerBankAccount = new CustomerBankAccount();
		customerBankAccount.setBranch(bankBranch);
		customerBankAccount.setCustomer(customer);
		customerBankAccount.setAccountNumber(accountNumber);
		if ((customerbankAccountRepository.findPrimaryAccount(customer, true) == null)
				&& (accountMode == AccountMode.SAVING)) {
			customerBankAccount.setPrimary(true);
		} else {
			customerBankAccount.setPrimary(false);
		}
		customerBankAccount.setAccountMode(accountMode);
		customerbankAccountRepository.save(customerBankAccount);

		customer.setLastModified(new Date());
		customer.setComment(comment);
		customer.setCommentedby(AuthenticationUtil.getCurrentUser().getUserName());

		customerRepository.save(customer);
		customerLogApi.save(customer.getUniqueId(), comment, AuthenticationUtil.getCurrentUser().getUserName());
		createCustomerLogLinkedAccounts(customer.getUniqueId(), customerBankAccount.getBranch());
	}

	@Override
	public void deleteCustomerBankAccount(Long customerBankAccountId) {
		CustomerBankAccount account = customerbankAccountRepository.findOne(customerBankAccountId);
		if (account.isPrimary()) {
			throw new UnsupportedOperationException("Cannot Delete Primary Account.");
		}
		Customer customer = account.getCustomer();
		customerbankAccountRepository.delete(account);
		if (customer != null) {
			customer.setLastModified(new Date());
			customerRepository.save(customer);
		}

	}

	@Override
	public List<CustomerDTO> getCustomerWithoutTransaction(Long associatedId) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		List<Customer> customerList = new ArrayList<>();
		if (currentUser.getUserType() == UserType.Admin) {
			customerList = customerRepository.getCustomerWithoutTransaction(UserType.Customer);
		} else if (currentUser.getUserType() == UserType.Bank) {
			customerList = customerRepository.getCustomerWithoutTransactionOfBank(UserType.Customer, associatedId);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			customerList = customerRepository.getCustomerWithoutTransactionOfBranch(UserType.Customer, associatedId);
		}
		if (customerList != null && !customerList.isEmpty()) {
			return convertUtil.convertCustomerToDto(customerList);
		}
		return null;
	}

	@Override
	public Long countCustomerWithInLastWeek(long associatedId) {
		Date date = new Date();
		date.setDate(date.getDate() - 7);
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Admin) {
			return customerRepository.countCustomerFromADate(date);
		} else if (currentUser.getUserType() == UserType.Bank) {
			return customerRepository.countCustomerThisMonthFromADateByBankId(associatedId, date);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return customerRepository.countCustomerFromADateByBranchId(associatedId, date);
		}
		return 0L;
	}

	@Override
	public Long countCustomerWithInLastMonth(long associatedId) {
		Date date = new Date();
		date.setMonth(date.getMonth() - 1);
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Admin) {
			return customerRepository.countCustomerFromADate(date);
		} else if (currentUser.getUserType() == UserType.Bank) {
			return customerRepository.countCustomerThisMonthFromADateByBankId(associatedId, date);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return customerRepository.countCustomerFromADateByBranchId(associatedId, date);
		}
		return 0L;
	}

	@Override
	public HashMap<String, String> accountTransfer(String sourceAccountNumber, String destinationAccountNumber,
			Double amount, Bank bank) {
		HashMap<String, String> isoData = new HashMap<String, String>();
		HashMap<String, String> fundTransferResponse = new HashMap<String, String>();
		String amountString = String.valueOf(amount);
		String[] amountStringArray = amountString.split("\\.");
		if (amountStringArray[1].length() == 1) {
			amountString = amountStringArray[0] + amountStringArray[1] + "0";
		} else if (amountStringArray[1].length() == 2) {
			amountString = amountStringArray[0] + amountStringArray[1];
		} else {
			amountString = amountStringArray[0] + amountStringArray[1].substring(0, 2);
		}
		for (int i = amountString.length(); i < 12; i++) {
			amountString = "0" + amountString;
		}
		if (bank.getDesc1() != null) {
			isoData.put("2", bank.getDesc1());
		} else {
			isoData.put("2", "123467890123456");
		}
		isoData.put("3", "400000"); // processing codes would be in general
									// settings
		isoData.put("4", amountString);
		isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
		isoData.put("11", STANGenerator.StanForIso("41"));
		isoData.put("12", DateUtil.convertDateToStringForIso(new Date()).substring(4, 10));
		isoData.put("13", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("14", "0000");
		isoData.put("15", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("18", bank.getMerchantType()); // would be in Bank Settings
		isoData.put("25", "00");
		isoData.put("22", "000");
		isoData.put("32", bank.getAcquiringInstitutionIdentificationCode()); // would
																				// be
																				// in
																				// Bank
																				// Settings
		isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) + "96");
		isoData.put("41", bank.getCardAcceptorTerminalIdentification()); // would
																			// be
																			// in
																			// Bank
																			// Settings
																			// isoData.put("41",
																			// "15548116");
		isoData.put("49", "524"); // 524 for NRS
		isoData.put("102", sourceAccountNumber);
		isoData.put("103", destinationAccountNumber);

		String bit90 = "0200" + isoData.get("11") + isoData.get("7") + "00000" + isoData.get("32");
		for (; bit90.length() < 42;) {
			bit90 += "0";
		}
		// 123,124 20 char// service name and amount, / mobile number
		String packedData = "";
		try {
			packedData = navaJeevanIsoCore.packMessage("0200", isoData);
		} catch (Exception e1) {
			e1.printStackTrace();
			fundTransferResponse.put("status", "failure");
			return fundTransferResponse;
		}
		fundTransferResponse.put("bit90", bit90);
		fundTransferResponse.put("stan", isoData.get("11"));
		String response = "";
		try {
			response = networkTransporstNavaJeevan.transportIsoMessage(bank.getIsoUrl(),
					Integer.parseInt(bank.getPortNumber()), packedData);
			if (response.equalsIgnoreCase("failure")) {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setIsoResponse(response);
				isoLog.setStatus(IsoStatus.NORESPONSE);
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "failure");
				return fundTransferResponse;
			}
			HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(response);
			if (unpackedData.get("39").equals("00")) {
				// now parse the field 47 if account holder name is to be
				// displayed
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setStatus(IsoStatus.SUCCESS);
				isoLog.setResponseCode("00");
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "success");

				return fundTransferResponse;
			} else {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setStatus(IsoStatus.FAILURE);
				isoLog.setResponseCode(unpackedData.get("39"));
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "failure");
				return fundTransferResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
			fundTransferResponse.put("status", "failure");
			return fundTransferResponse;
		}

		// return "failure";
	}

	@Override
	public HashMap<String, String> fundTransfer(String sourceAccountNumber, String destinationAccountNumber,
			Double amount, Bank bank) {
		HashMap<String, String> isoData = new HashMap<String, String>();
		HashMap<String, String> fundTransferResponse = new HashMap<String, String>();
		String amountString = String.valueOf(amount);
		String[] amountStringArray = amountString.split("\\.");
		if (amountStringArray[1].length() == 1) {
			amountString = amountStringArray[0] + amountStringArray[1] + "0";
		} else if (amountStringArray[1].length() == 2) {
			amountString = amountStringArray[0] + amountStringArray[1];
		} else {
			amountString = amountStringArray[0] + amountStringArray[1].substring(0, 2);
		}
		for (int i = amountString.length(); i < 12; i++) {
			amountString = "0" + amountString;
		}
		if (bank.getDesc1() != null) {
			isoData.put("2", bank.getDesc1());
		} else {
			isoData.put("2", "123467890123456");
		}
		isoData.put("3", "400000"); // processing codes would be in general
									// settings
		isoData.put("4", amountString);
		isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
		isoData.put("11", STANGenerator.StanForIso("41"));
		isoData.put("12", DateUtil.convertDateToStringForIso(new Date()).substring(4, 10));
		isoData.put("13", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("14", "0000");
		isoData.put("15", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("18", bank.getMerchantType()); // would be in Bank Settings
		isoData.put("25", "00");
		isoData.put("22", "000");
		isoData.put("32", bank.getAcquiringInstitutionIdentificationCode()); // would
																				// be
																				// in
																				// Bank
																				// Settings
		isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) + "96");
		isoData.put("41", bank.getCardAcceptorTerminalIdentification()); // would
																			// be
																			// in
																			// Bank
																			// Settings
																			// isoData.put("41",
																			// "15548116");
		isoData.put("49", "524"); // 524 for NRS
		isoData.put("102", sourceAccountNumber);
		isoData.put("103", destinationAccountNumber);

		/*
		 * String bit90 =
		 * "0200"+isoData.get("11")+isoData.get("7")+"00000"+isoData.get("32");
		 * for(;bit90.length()<42;){ bit90 +="0"; }
		 */
		// 123,124 20 char// service name and amount, / mobile number
		String packedData = "";
		try {
			packedData = navaJeevanIsoCore.packMessage("0200", isoData);
		} catch (Exception e1) {
			e1.printStackTrace();
			fundTransferResponse.put("status", "failure");
			fundTransferResponse.put("code", "message_pack_error");
			return fundTransferResponse;
		}
		// fundTransferResponse.put("bit90",bit90);
		String response = "";
		try {
			response = networkTransporstNavaJeevan.transportIsoMessage(bank.getIsoUrl(),
					Integer.parseInt(bank.getPortNumber()), packedData);
			if (response.equalsIgnoreCase("failure")) {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setIsoResponse(response);
				isoLog.setStatus(IsoStatus.NORESPONSE);
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "failure");
				fundTransferResponse.put("code", "no_response");
				return fundTransferResponse;
			}
			HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(response);
			fundTransferResponse.put("code", unpackedData.get("39"));
			if (unpackedData.get("39").equals("00")) {
				// now parse the field 47 if account holder name is to be
				// displayed
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setStatus(IsoStatus.SUCCESS);
				isoLog.setResponseCode("00");
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "success");

				return fundTransferResponse;
			} else {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setStatus(IsoStatus.FAILURE);
				isoLog.setResponseCode(unpackedData.get("39"));
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "failure");

				return fundTransferResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
			fundTransferResponse.put("status", "failure");
			fundTransferResponse.put("code", "message_exception_error");
			return fundTransferResponse;
		}

	}

	@Override
	public HashMap<String, String> fundTransfer(String sourceAccountNumber, String destinationAccountNumber,
			double amount, Bank bank, String remarksOne, String remarksTwo, String desc1) {
		HashMap<String, String> fundTransferResponse = new HashMap<String, String>();
		HashMap<String, String> isoData = new HashMap<String, String>();
		String amountString = String.valueOf(amount);
		String[] amountStringArray = amountString.split("\\.");
		if (amountStringArray[1].length() == 1) {
			amountString = amountStringArray[0] + amountStringArray[1] + "0";
		} else if (amountStringArray[1].length() == 2) {
			amountString = amountStringArray[0] + amountStringArray[1];
		} else {
			amountString = amountStringArray[0] + amountStringArray[1].substring(0, 2);
		}
		for (int i = amountString.length(); i < 12; i++) {
			amountString = "0" + amountString;
		}
		if (desc1 == null) {
			isoData.put("2", "123467890123456");
		} else {
			isoData.put("2", desc1);
		}
		isoData.put("3", "400000"); // processing codes would be in general
									// settings
		isoData.put("4", amountString);
		isoData.put("7", DateUtil.convertDateToStringForIso(new Date()));
		isoData.put("11", STANGenerator.StanForIso("41"));
		isoData.put("12", DateUtil.convertDateToStringForIso(new Date()).substring(4, 10));
		isoData.put("13", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("14", "0000");
		isoData.put("15", DateUtil.convertDateToStringForIso(new Date()).substring(0, 4));
		isoData.put("18", bank.getMerchantType()); // would be in Bank Settings
		isoData.put("25", "00");
		isoData.put("22", "000");
		isoData.put("32", bank.getAcquiringInstitutionIdentificationCode()); // would
																				// be
																				// in
																				// Bank
																				// Settings
		isoData.put("41", bank.getCardAcceptorTerminalIdentification()); // would
																			// be
																			// in
																			// Bank
																			// Settings
																			// isoData.put("41",
																			// "15548116");
		isoData.put("37", DateUtil.convertDateToStringForIso(new Date()) + "96");
		isoData.put("49", "524"); // 524 for NRS
		isoData.put("102", sourceAccountNumber);
		isoData.put("103", destinationAccountNumber);
		isoData.put("123", remarksOne);
		isoData.put("124", remarksTwo);
		// 123,124 20 char// service name and amount, / mobile number
		String packedData = "";
		try {
			packedData = navaJeevanIsoCore.packMessage("0200", isoData);
		} catch (Exception e1) {
			e1.printStackTrace();
			fundTransferResponse.put("status", "failure");
			fundTransferResponse.put("code", "message_pack_error");
			return fundTransferResponse;
		}
		String response = "";
		try {
			response = networkTransporstNavaJeevan.transportIsoMessage(bank.getIsoUrl(),
					Integer.parseInt(bank.getPortNumber()), packedData);
			if (response.equalsIgnoreCase("failure")) {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setIsoResponse(response);
				isoLog.setStatus(IsoStatus.NORESPONSE);
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "failure");
				fundTransferResponse.put("code", "no_response");
				return fundTransferResponse;
			}
			HashMap<String, String> unpackedData = navaJeevanIsoCore.unpackMessage(response);
			fundTransferResponse.put("code", unpackedData.get("39"));
			if (unpackedData.get("39").equals("00")) {
				// now parse the field 47 if account holder name is to be
				// displayed
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setStatus(IsoStatus.SUCCESS);
				isoLog.setResponseCode("00");
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "success");

				return fundTransferResponse;
			} else {
				Iso8583Log isoLog = new Iso8583Log();
				isoLog.setIsoRequest(packedData);
				isoLog.setIsoResponse(response);
				isoLog.setRequestType(IsoRequestType.FUNDTRANSFER);
				isoLog.setStatus(IsoStatus.FAILURE);
				isoLog.setResponseCode(unpackedData.get("39"));
				isoLogRepository.save(isoLog);
				fundTransferResponse.put("status", "failure");

				return fundTransferResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
			fundTransferResponse.put("status", "failure");
			fundTransferResponse.put("code", "message_exception_error");
			return fundTransferResponse;
		}

		// return "failure";
	}

	@Override
	public PagablePage getCustomer(Integer currentpage, String name, String mobileNo, String city, String status,
			String accountNo, String branchCode, String bankCode, Boolean firstApproval, String fromDate, String toDate,
			boolean expired) {
		PagablePage pageble = new PagablePage();

		if (currentpage == null || currentpage == 0) {
			currentpage = 1;
		}

		int starting = ((currentpage * (int) PageInfo.pageList) - (int) PageInfo.pageList);

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Customer.class);
		User currentUser = AuthenticationUtil.getCurrentUser();
		Junction junction = Restrictions.disjunction();
		if (currentUser.getUserType() == UserType.Bank) {
			if (branchCode != null && !branchCode.trim().equals("")) {
				BankBranch bankBranch = bankBranchRepository.findByBankAndBranchCode(currentUser.getAssociatedId(),
						branchCode);
				criteria.add(Restrictions.eq("bankBranch", bankBranch));
			} else {
				criteria.add(Restrictions.eq("bank", bankRepository.findOne(currentUser.getAssociatedId())));
			}
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			criteria.add(Restrictions.eq("bankBranch", bankBranchRepository.findOne(currentUser.getAssociatedId())));
		}
		if (firstApproval != null && !firstApproval) {
			if (status != null && !(status.trim().equals(""))) {
				criteria.add(Restrictions.ne("status", CustomerStatus.valueOf(status)));
				criteria.add(Restrictions.eq("firstApprove", firstApproval));
			}
		} else {
			if (status != null && !(status.trim().equals(""))) {
				criteria.add(Restrictions.eq("status", CustomerStatus.valueOf(status)));
			}
		}
		if (mobileNo != null && !(mobileNo.trim().equals(""))) {
			criteria.add(Restrictions.like("mobileNumber", mobileNo + "%"));
		}

		if (city != null && !(city.trim().equals(""))) {
			City cityEntity = cityRepository.findOne(Long.parseLong(city));
			if (cityEntity != null) {
				criteria.add(Restrictions.eq("city", cityEntity));
			}
		}

		if (bankCode != null && !(bankCode.trim().equals(""))) {
			criteria.add(Restrictions.eq("bank", bankRepository.findBankByCode(bankCode)));
		}

		if (name != null && !(name.trim().equals(""))) {
			junction = Restrictions.disjunction();
			junction.add(Restrictions.like("firstName", name + "%"));
			junction.add(Restrictions.like("middleName", name + "%"));
			junction.add(Restrictions.like("lastName", name + "%"));
			junction.add(Restrictions.like("fullName", name + "%"));
			criteria.add(junction);
		}

		if (accountNo != null && !(accountNo.trim().equals(""))) {
			if (accountNo.length() == 20) {
				accountNo = accountNo.substring(0, 3) + accountNo;
			}
			List<CustomerBankAccount> bankAccountList = customerbankAccountRepository.findByAccountNumber(accountNo);
			if (bankAccountList != null && bankAccountList.size() != 0) {
				junction = Restrictions.disjunction();
				for (CustomerBankAccount bankAccount : bankAccountList)
					junction.add(Restrictions.eq("id", bankAccount.getCustomer().getId()));
				criteria.add(junction);
			} else {
				criteria.add(Restrictions.eq("id", 0L));
			}
		}

		// this part of to date and fromdate is added on 29-04-2018 by amrit
		if (fromDate != null && !(fromDate.trim().equalsIgnoreCase(""))
				&& !(fromDate.trim().equalsIgnoreCase("undefined"))) {

			// System.out.println("from date :
			// ******"+DateUtil.getDate(fromDate));
			// System.out.println("from date after util is
			// :"+DateUtil.getDate(fromDate,DateTypes.FROM_DATE));
			criteria.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
		}

		if (toDate != null && !(toDate.trim().equalsIgnoreCase("")) && !(toDate.trim().equalsIgnoreCase("undefined"))) {
			// System.out.println("to date after util is
			// :"+DateUtil.getDate(toDate,DateTypes.TO_DATE));
			criteria.add(Restrictions.le("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
			// System.out.println("to date : ******"+DateUtil.getDate(toDate,
			// "YYYY-MM-DD"));

		}

		if (expired) {
			criteria.add(Restrictions.eq("status", CustomerStatus.Expired));
		}

		criteria.addOrder(Order.desc("id"));

		int totalpage = (int) Math.ceil(criteria.list().size() / PageInfo.pageList);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
		criteria.setFirstResult(starting);
		criteria.setMaxResults((int) PageInfo.pageList);
		List<Customer> customer = criteria.list();

		pageble.setCurrentPage(currentpage);
		pageble.setObject(convertUtil.convertCustomerToDto(customer));
		pageble.setPageList(pagesnumbers);
		pageble.setLastpage(totalpage);

		return pageble;
	}

	private List<CustomerDTO> processCsvFileOfCustomer(String inputFilePath) {
		System.out.println("Input file path: " + inputFilePath);
		List<CustomerDTO> inputList = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath))) {
			// skip(1) used to skip the heading in the file. If no heading then
			// remove skip

			String[] header = br.readLine().split(",");

			if (!header[0].equalsIgnoreCase("Name") || !header[1].equalsIgnoreCase("Address")
					|| !header[2].equalsIgnoreCase("Mobile Number") || !header[3].equalsIgnoreCase("Bank Code")
					|| !header[4].equalsIgnoreCase("Branch Code") || !header[5].equalsIgnoreCase("Account Number")
					|| !header[6].equalsIgnoreCase("Account Type") || !header[7].equalsIgnoreCase("App Service")
					|| !header[8].equalsIgnoreCase("SMS Subscribed") || !header[9].equalsIgnoreCase("Profile")) {

				throw new IllegalArgumentException("Invalid number of columns.");

			}

			inputList = br.lines().map(mapToCustomerFromCustomerCsv).collect(Collectors.toList());
			System.out.println("Length " + inputList.size());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		return inputList;
	}

	private Function<String, CustomerDTO> mapToCustomerFromCustomerCsv = (line) -> {
		System.out.println("Line : " + line);
		String[] column = line.split(",");// a CSV has comma separated lines

		if (column.length != 10) {
			throw new IllegalArgumentException("Invalid number of columns.");
		}

		CustomerDTO customerDTO = new CustomerDTO();
		if ((column[0] == null && column[0].equals("")) || (column[1] == null && column[1].equals(""))
				|| (column[2] == null && column[2].equals("")) || (column[3] == null && column[3].equals(""))
				|| (column[4] == null && column[4].equals("")) || (column[5] == null && column[5].equals(""))
				|| (column[6] == null && column[6].equals("")) || (column[7] == null && column[7].equals(""))
				|| (column[8] == null && column[8].equals("")) || (column[9] == null && column[9].equals(""))) {

			throw new IllegalArgumentException("Invalid columns In the given file.");

		}
		String name = column[0];

		String[] splittedString = name.split(" ");

		customerDTO.setFirstName(splittedString[0]);
		if (splittedString.length == 3) {
			customerDTO.setMiddleName(splittedString[1]);
			customerDTO.setLastName(splittedString[2]);
		} else {
			customerDTO.setLastName(splittedString[1]);
		}
		String[] splittedAddress = column[1].split("-");
		customerDTO.setAddressOne(splittedAddress[1]);

		customerDTO.setCity(splittedAddress[0]);

		customerDTO.setMobileNumber(column[2]);

		String bankCode = column[3];
		// customerDTO.setBankCode(column[3]);

		Bank bank = bankRepository.findBankByCode(bankCode);
		if (bank == null) {
			throw new IllegalArgumentException("Invalid Bank Code, No bank with the given code exists.");

		} else {
			customerDTO.setBank(bank.getId().toString());
		}
		BankBranch branch = bankBranchRepository.findByBankAndBranchCode(bank.getId(), column[4]);
		if (branch != null)
			customerDTO.setBankBranch(branch.getId().toString());
		else
			throw new IllegalArgumentException("Invalid Branch Code, No branch with the given code exists.");

		customerDTO.setAccountNumber(column[5]);

		if (column[6].equalsIgnoreCase("savings")) {
			customerDTO.setAccountMode(AccountMode.SAVING);
		} else if (column[6].equalsIgnoreCase("loan")) {
			customerDTO.setAccountMode(AccountMode.LOAN);
		} else if (column[6].equalsIgnoreCase("current")) {
			customerDTO.setAccountMode(AccountMode.CURRENT);
		}

		if (column[7].equals("1")) {
			customerDTO.setAppService(true);
		} else if (column[7].equals("0")) {
			customerDTO.setAppService(false);
		} else {
			throw new IllegalArgumentException("Invalid Value In the Column.");
		}
		if (column[8].equals("1")) {
			customerDTO.setSmsService(true);
		} else if (column[8].equals("0")) {
			customerDTO.setSmsService(false);
		} else {
			throw new IllegalArgumentException("Invalid Value In the Column.");
		}
		CustomerProfile profile = customerProfileRepository.findByProfileUniqueId(column[9]);
		if (profile != null) {
			customerDTO.setCustomerProfileId(profile.getId());
		} else {
			throw new IllegalArgumentException("Invalid Profile In the Column, No profile with the given id exists.");
		}
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getMaker() != null && currentUser.getChecker())
			customerDTO.setStatus(CustomerStatus.Approved);
		else
			customerDTO.setStatus(CustomerStatus.Hold);
		customerDTO.setCreatedBy(currentUser.getId().toString());
		return customerDTO;
	};

	@Override
	public String uploadBulkCustomer(MultipartFile file, String destinationPath) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty!");
		}
		if (FileUtils.isValidFileType(file.getOriginalFilename(), "csv")) {
			String filePath = destinationPath + file.getOriginalFilename() + "_" + System.currentTimeMillis();
			FileUtils.upload(file, filePath);
			List<CustomerDTO> customerDtoList = processCsvFileOfCustomer(filePath);
			for (CustomerDTO customerDto : customerDtoList) {
				saveCustomer(customerDto);
			}
		} else {
			throw new IllegalArgumentException("Invalid file type!");
		}
		return null;
	}

	@Override
	public void changeMobileNumber(String uniqueId, String currentMobileNumber, String newMobileNumber,
			String comment) {

		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		customer.setMobileNumber(newMobileNumber);
		customer.setComment(comment);
		customer.setCommentedby(AuthenticationUtil.getCurrentUser().getUserName());
		customer = customerRepository.save(customer);

		customerLogApi.save(customer.getUniqueId(), comment, AuthenticationUtil.getCurrentUser().getUserName());

		Bank bank = customer.getBank();
		BankOAuthClient bankClient = bankAuthRepository.findByBank(bank);
		String clientId = bankClient.getoAuthClientId();
		User user = userRepository.findByUsername(clientId + currentMobileNumber);
		user.setUserName(clientId + newMobileNumber);
		user.setSmsUserName(newMobileNumber);
		Map<String, String> passwordMap = passwordGenerator.generateMPinForCustomer();
		user.setPassword(passwordMap.get("passwordEncoded"));
		user.setToken(passwordGenerator.generateToken());
		String mpin = passwordMap.get("password");
		user = userRepository.save(user);
		User currentUser = AuthenticationUtil.getCurrentUser();
		String message = StringConstants.MOBILE_NUMBER_EDIT;

		SmsLog smsLog = new SmsLog();
		if (currentUser.getUserType() == UserType.BankBranch) {
			BankBranch bankBranch = bankBranchRepository.findOne(currentUser.getAssociatedId());
			smsLog.setBankBranch(bankBranch);
		}
		smsLog.setBank(bank);
		// smsLog.setBank(bankBranch.getBank());
		// smsLog.setBankBranch(bankBranch);
		message = message.replace("{customer}", customer.getFirstName());
		message = message.replace("{mpin}", mpin);
		message = message.replace("{username}", user.getSmsUserName());
		message = message.replace("{bank}", bank.getName().split(" ")[0]);

		smsLog.setMessage(message);
		smsLog.setSmsForUser(UserType.Customer);
		smsLog.setSmsFor(customer.getUniqueId());
		smsLog.setStatus(SmsStatus.INITIATED);
		// smsLog.setSmsFrom(bankBranch.getId());
		smsLog.setSmsFromUser(UserType.BankBranch);
		smsLog.setSmsType(SmsType.MOBILE_NO_MODIFICATION);
		smsLog.setForMobileNo(customer.getMobileNumber());
		smsLogRepository.save(smsLog);
	}

	@Scheduled(fixedRate = 10000)
	public void sendMobileModificationMessage() throws Exception {
		List<SmsLog> smsLogs = smsLogRepository.findSmsLogBySmsTypeAndStatusAndUserType(SmsType.MOBILE_NO_MODIFICATION,
				SmsStatus.INITIATED, UserType.Customer);
		Map<String, String> responseMap = new HashMap<String, String>();
		if (!smsLogs.isEmpty()) {
			SmsLog sLog = new SmsLog();
			for (int i = 0; i < smsLogs.size(); i++) {

				if (smsLogs.get(i).getBank().getSmsCount() <= 0) {
					sLog.setStatus(SmsStatus.BANK_OUT_OF_SMS);
					smsLogRepository.save(smsLogs.get(i));
				} else {
					try {
						// before sending sms check if the bank exceeds the
						// smspackage or not
						String uniqueId = smsLogs.get(i).getSmsFor();
						Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
						String message = smsLogs.get(i).getMessage();
						responseMap = sparrowApi.sendSMS(message, customer.getMobileNumber());
						smsLogs.get(i).setStatus(SmsStatus.QUEUED);
						sLog = smsLogRepository.save(smsLogs.get(i));
						if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
							sLog.setStatus(SmsStatus.DELIVERED);
							smsLogRepository.save(sLog);
							Bank bank = bankRepository.findOne(smsLogs.get(i).getBank().getId());
							bank.setSmsCount(bank.getSmsCount() - 1);
							bankRepository.save(bank);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public boolean getCustomerByMobileNo(String newMobileNumber, Long associatedId, UserType currentUserType) {
		Bank bank = null;
		if (currentUserType.equals(UserType.Bank)) {
			bank = bankRepository.findOne(associatedId);
		} else if (currentUserType.equals(UserType.BankBranch)) {
			BankBranch bankBranch = bankBranchRepository.findOne(associatedId);
			bank = bankBranch.getBank();
		}
		Customer customer = customerRepository.findByMobileNumberAndBank(newMobileNumber, bank);
		if (customer != null) {
			return true;
		} else
			return false;
	}

	@Override
	public Long countCustomer(long currentUserId) {
		User currentUser = userRepository.findOne(currentUserId);
		if (currentUser.getUserType() == UserType.Admin) {
			return customerRepository.countAllCustomer();
		} else if (currentUser.getUserType() == UserType.Bank) {
			return customerRepository.countAllCustomerByBank(currentUser.getAssociatedId());
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return customerRepository.countAllCustomerByBranch(currentUser.getAssociatedId());
		}
		return 0L;
	}

	@Override
	public Long countCustomerWithoutTransaction(long currentUserId) {
		User currentUser = userRepository.findOne(currentUserId);
		if (currentUser.getUserType() == UserType.Admin) {
			return customerRepository.countCustomerWithoutTransaction(UserType.Customer);
		} else if (currentUser.getUserType() == UserType.Bank) {
			return customerRepository.countCustomerWithoutTransactionOfBank(UserType.Customer,
					currentUser.getAssociatedId());
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return customerRepository.countCustomerWithoutTransactionOfBranch(UserType.Customer,
					currentUser.getAssociatedId());
		}
		return 0L;
	}

	@Override
	public void createSmsLog(CustomerBankAccountDTO customerBankAccountDto, String smsType, String message) {
		SmsLog smsLog = new SmsLog();
		CustomerBankAccount customerBankAccount = customerbankAccountRepository.findOne(customerBankAccountDto.getId());
		Customer customer = customerBankAccount.getCustomer();
		customer = customerRepository.findCustomerByUniqueId(customer.getUniqueId());
		long bankId = 0;
		long bankBranchId = 0;
		boolean createdByBank = false;
		// String authority = createdBy.getAuthority();
		// String message =
		// smsModeRepository.findSmsModeMessageByBankAndSmsType(
		// SmsType.valueOf(smsType), Status.Active);

		if (message != null) {
			// message = message.replace("password", "mpin");
			// message = message.replace("{username}", user.getUserName());
			// message = message.replace("{mpin}", user.getSecretKey());
			//
			// message = message.replace("{pin}", user.getPin());
			smsLog.setBank(customerBankAccount.getBranch().getBank());

			smsLog.setSmsForUser(UserType.Customer);
			smsLog.setSmsFor(customer.getUniqueId());
			smsLog.setForMobileNo(customer.getMobileNumber());
			smsLog.setMessage(message);
			smsLog.setStatus(SmsStatus.INITIATED);
			smsLog.setSmsFromUser(createdByBank ? UserType.Bank : UserType.BankBranch);
			smsLog.setSmsFrom(createdByBank ? bankId : bankBranchId);
			smsLog.setSmsType(SmsType.valueOf(smsType));
			// if (smsType.equals(SmsType.BULK)) {
			// smsLog.setSmsCategoryType(SmsCatgoryType.BulkSms);
			// } else
			// smsLog.setSmsCategoryType(SmsCatgoryType.TransactionSms);
			smsLogRepository.save(smsLog);

		} else {
			smsLog.setBank(customerBankAccount.getBranch().getBank());
			smsLog.setSmsForUser(UserType.Customer);
			smsLog.setSmsFor(customer.getUniqueId());
			smsLog.setForMobileNo(customer.getMobileNumber());
			smsLog.setMessage(message);
			smsLog.setStatus(SmsStatus.INITIATED);
			smsLog.setSmsFromUser(createdByBank ? UserType.Bank : UserType.BankBranch);
			smsLog.setSmsFrom(createdByBank ? bankId : bankBranchId);
			smsLog.setSmsType(SmsType.valueOf(smsType));
			// if (smsType.equals(SmsType.BULK)) {
			// smsLog.setSmsCategoryType(SmsCatgoryType.BulkSms);
			// } else
			// smsLog.setSmsCategoryType(SmsCatgoryType.TransactionSms);
			smsLogRepository.save(smsLog);
		}

	}

	// @Scheduled(fixedRate = 10000)
	public void sendAlertSms() throws Exception {
		List<SmsLog> smsLogs = smsLogRepository.findSmsLogBySmsTypeAndStatusAndUserType(SmsType.BULK,
				SmsStatus.INITIATED, UserType.Customer);
		Map<String, String> responseMap = new HashMap<String, String>();
		if (!smsLogs.isEmpty()) {
			SmsLog sLog = new SmsLog();
			for (int i = 0; i < smsLogs.size(); i++) {
				if (smsLogs.get(i).getBank().getSmsCount() <= 0) {
					sLog.setStatus(SmsStatus.BANK_OUT_OF_SMS);
					smsLogRepository.save(smsLogs.get(i));
				} else {
					try {
						// before sending sms check if the bank exceeds the
						// smspackage or not
						String uniqueId = smsLogs.get(i).getSmsFor();
						Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
						String message = smsLogs.get(i).getMessage();
						responseMap = sparrowApi.sendSMS(message, customer.getMobileNumber());
						smsLogs.get(i).setStatus(SmsStatus.QUEUED);
						sLog = smsLogRepository.save(smsLogs.get(i));
						if (responseMap.get("code") == "200" || responseMap.get("code").equalsIgnoreCase("200")) {
							sLog.setStatus(SmsStatus.DELIVERED);
							smsLogRepository.save(sLog);
							Bank bank = bankRepository.findOne(smsLogs.get(i).getBank().getId());
							bank.setSmsCount(bank.getSmsCount() - 1);
							bankRepository.save(bank);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public List<CustomerDTO> findCustomerList(List<Long> customerIdList) {
		List<CustomerDTO> customerList = new ArrayList<>();
		for (Long id : customerIdList) {
			Customer customer = customerRepository.findOne(id);
			customerList.add(convertUtil.convertCustomer(customer));
		}
		return customerList;
	}

	@Override
	public String getUsernameByCustomer(Long id) {
		Customer customer = customerRepository.findOne(id);
		return customer.getUser().getSmsUserName();
	}

	@Override
	public void createSmsLog(CustomerDTO customerdTO, String smsType, String message) {
		SmsLog smsLog = new SmsLog();
		Customer customer = customerRepository.findOne(customerdTO.getId());
		customer = customerRepository.findCustomerByUniqueId(customer.getUniqueId());
		long bankId = 0;
		long bankBranchId = 0;
		boolean createdByBank = false;
		// String authority = createdBy.getAuthority();
		// String message =
		// smsModeRepository.findSmsModeMessageByBankAndSmsType(
		// SmsType.valueOf(smsType), Status.Active);

		if (message != null) {
			// message = message.replace("password", "mpin");
			// message = message.replace("{username}", user.getUserName());
			// message = message.replace("{mpin}", user.getSecretKey());
			//
			// message = message.replace("{pin}", user.getPin());
			if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)) {
				smsLog.setBank(bankRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId()));
			}

			if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)) {
				BankBranch bankBranch = bankBranchRepository
						.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
				smsLog.setBankBranch(bankBranch);
				smsLog.setBank(bankBranch.getBank());
			}
			smsLog.setSmsForUser(UserType.Customer);
			smsLog.setSmsFor(customer.getUniqueId());
			smsLog.setForMobileNo(customer.getMobileNumber());
			smsLog.setMessage(message);
			smsLog.setStatus(SmsStatus.INITIATED);
			smsLog.setSmsFromUser(createdByBank ? UserType.Bank : UserType.BankBranch);
			smsLog.setSmsFrom(createdByBank ? bankId : bankBranchId);
			smsLog.setSmsType(SmsType.valueOf(smsType));
			// if (smsType.equals(SmsType.BULK)) {
			// smsLog.setSmsCategoryType(SmsCatgoryType.BulkSms);
			// } else
			// smsLog.setSmsCategoryType(SmsCatgoryType.TransactionSms);
			smsLogRepository.save(smsLog);

		} else {
			if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)) {
				smsLog.setBank(bankRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId()));
			}
			if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)) {
				BankBranch bankBranch = bankBranchRepository
						.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
				smsLog.setBankBranch(bankBranch);
				smsLog.setBank(bankBranch.getBank());
			}
			smsLog.setSmsForUser(UserType.Customer);
			smsLog.setSmsFor(customer.getUniqueId());
			smsLog.setForMobileNo(customer.getMobileNumber());
			smsLog.setMessage(message);
			smsLog.setStatus(SmsStatus.INITIATED);
			smsLog.setSmsFromUser(createdByBank ? UserType.Bank : UserType.BankBranch);
			smsLog.setSmsFrom(createdByBank ? bankId : bankBranchId);
			smsLog.setSmsType(SmsType.valueOf(smsType));
			// if (smsType.equals(SmsType.BULK)) {
			// smsLog.setSmsCategoryType(SmsCatgoryType.BulkSms);
			// } else
			// smsLog.setSmsCategoryType(SmsCatgoryType.TransactionSms);
			smsLogRepository.save(smsLog);
		}

	}

	@Override
	public boolean changeStatus(String uniqueId, CustomerStatus status, String remarks, String changedBy) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		if (customer != null) {
			customer.setStatus(status);
			customer.setLastModified(new Date());
			customer.setComment(remarks);
			if (AuthenticationUtil.getCurrentUser() != null) {
				customer.setCommentedby(AuthenticationUtil.getCurrentUser().getUserName());
			} else {
				customer.setCommentedby("SYSTEM");
			}
			customer = customerRepository.save(customer);
			if (customer.getFirstApprove() == null) {
				customer.setFirstApprove(true);
				customer = customerRepository.save(customer);
			}
			if (status.equals(CustomerStatus.Approved) && !customer.getFirstApprove()) {
				try {
					CustomerProfile customerProfile = customerProfileRepository.findByCustomer(customer);
					if (customerProfile.getRegistrationCharge() != null
							&& customerProfile.getRegistrationCharge() > 0) {
						HashMap<String, String> registrationCharge;
						try {
							if (customer.getStatus().equals(CustomerStatus.Approved)) {
								CustomerBankAccount customerBankAccount = customerbankAccountRepository
										.findPrimaryAccount(customer, true);
								registrationCharge = deductServiceCharge(customerBankAccount, customerProfile.getId(),
										BankServiceCharge.Registration);
								if (registrationCharge.get("status").equals("success")) {
									customerLogApi.save(customer.getUniqueId(), "Registration charge succesfully paid.",
											changedBy);
									customer.setApprovalMessage("Registration charge succesfully paid.");
									customer = customerRepository.save(customer);
								} else {
									customer.setApprovalMessage(registrationCharge.get("Result Message"));
									customer.setStatus(CustomerStatus.Hold);
									customer.setFirstApprove(false);
									customer = customerRepository.save(customer);
									return false;
								}
							}
						} catch (Exception e) {
							customer.setApprovalMessage("Error while trasferring fund.");
							customer.setStatus(CustomerStatus.Hold);
							customer.setFirstApprove(false);
							customer = customerRepository.save(customer);
							return false;
						}
					}
					createSmsLog(customer, SmsType.REGISTRATION, userRepository.findByUsername(changedBy));
					customer.setFirstApprove(true);
					customer = customerRepository.save(customer);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			User user = customer.getUser();
			if (customer.getStatus().equals(CustomerStatus.Approved)) {
				user.setStatus(Status.Active);
			} else {
				user.setStatus(Status.Inactive);
			}
			userRepository.save(user);
			customerLogApi.save(customer.getUniqueId(), remarks, changedBy);
			return true;
		}
		return false;
	}

	@Override
	public List<CustomerDTO> getUserByMobileNumberConCat(String data, String bankCode) {
		List<Customer> customerList = customerRepository.getUserByMobileNumberConCat(data, bankCode);
		return convertUtil.convertCustomerToDto(customerList);
	}

	@Override
	public Boolean resetToken(String uniqueId) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		User user = customer.getUser();
		user.setToken(passwordGenerator.generateToken());
		user = userRepository.save(user);
		try {
			FcmServerSetting fcmServerSetting = fcmServerSettingRepository
					.findByBankAndFcmServerKey(user.getBank().getId(), user.getServerKey());
			pushNotification.sendNotification("Reset Device", "Resetting device,initiate logout.",
					user.getDeviceToken(), "Reset Device", fcmServerSetting.getFcmServerID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setDeviceToken(null);
		user = userRepository.save(user);

		return deleteOauthToken(user.getUserName());
	}

	@Override
	public boolean deleteOauthToken(String username) {
		try {
			List<OAuthAccessToken> oauthTokenList = oAuthAccessTokenRepository.findByUsername(username);
			if (oauthTokenList != null && oauthTokenList.size() != 0) {
				oAuthAccessTokenRepository.delete(oauthTokenList);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Long countCustomerByDate(Date currentDate) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Admin) {
			return customerRepository.countCustomerFromADate(currentDate);
		} else if (currentUser.getUserType() == UserType.Bank) {
			return customerRepository.countCustomerThisMonthFromADateByBankId(currentUser.getAssociatedId(),
					currentDate);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return customerRepository.countCustomerFromADateByBranchId(currentUser.getAssociatedId(), currentDate);
		}
		return 0L;
	}

	@Override
	public Long countCustomerWithoutTransactionByDate(Date date) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Admin) {
			return customerRepository.countCustomerWithoutTransactionByDate(UserType.Customer, date);
		} else if (currentUser.getUserType() == UserType.Bank) {
			return customerRepository.countCustomerWithoutTransactionOfBankAndDate(UserType.Customer,
					currentUser.getAssociatedId(), date);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return customerRepository.countCustomerWithoutTransactionOfBranchAndDate(UserType.Customer,
					currentUser.getAssociatedId(), date);
		}
		return 0L;
	}

	@Override
	public List<CustomerDTO> getCustomerWithoutTransactionByDate(long associatedId, Date date) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		List<Customer> customerList = new ArrayList<>();
		if (currentUser.getUserType() == UserType.Admin) {
			customerList = customerRepository.getCustomerWithoutTransactionByDate(UserType.Customer, date);
		} else if (currentUser.getUserType() == UserType.Bank) {
			customerList = customerRepository.getCustomerWithoutTransactionOfBankByDate(UserType.Customer, associatedId,
					date);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			customerList = customerRepository.getCustomerWithoutTransactionOfBranchByDate(UserType.Customer,
					associatedId, date);
		}
		if (customerList != null && !customerList.isEmpty()) {
			return convertUtil.convertCustomerToDto(customerList);
		}
		return null;
	}

	@Override
	public HashMap<String, Object> getCustomerRegestrationReport(Long bankId, Long branchId, String fromDate,
			String toDate, long currentUserId) {
		HashMap<String, Object> registrationReport = new HashMap<>();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Customer.class);
		try {
			if (fromDate != null && !(fromDate.trim().equals(""))) {
				criteria.add(Restrictions.ge("created", (Date) formatter.parse(fromDate)));
				registrationReport.put("fromDate", fromDate);
			} else {
				registrationReport.put("fromDate", "-");
			}
			if (toDate != null && !(toDate.trim().equals(""))) {
				criteria.add(Restrictions.lt("created", (Date) formatter.parse(toDate)));
				registrationReport.put("toDate", toDate);
			} else {
				registrationReport.put("toDate", "-");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (branchId != null) {
			BankBranch branch = bankBranchRepository.findOne(branchId);
			criteria.add(Restrictions.eq("bankBranch", branch));
			registrationReport.put("branchCode", branch.getBranchCode());
			registrationReport.put("bankCode", branch.getBank().getSwiftCode());
			registrationReport.put("loanAccount",
					customerbankAccountRepository.countByBranchAndAccountMode(branch.getId(), AccountMode.LOAN));
			registrationReport.put("savingAccount",
					customerbankAccountRepository.countByBranchAndAccountMode(branch.getId(), AccountMode.SAVING));
			registrationReport.put("currentAccount",
					customerbankAccountRepository.countByBranchAndAccountMode(branch.getId(), AccountMode.CURRENT));
			registrationReport.put("odAccount",
					customerbankAccountRepository.countByBranchAndAccountMode(branch.getId(), AccountMode.OD));
			registrationReport.put("activeUser", customerRepository.countAllCustomerByBranch(branch.getId())
					- customerRepository.countCustomerWithoutTransactionOfBranch(UserType.Customer, branch.getId()));
			registrationReport.put("blockedUser",
					customerRepository.countCustomerByBranchAndStatus(branch.getId(), CustomerStatus.Blocked));
		} else if (bankId != null) {
			Bank bank = bankRepository.findOne(bankId);
			criteria.add(Restrictions.eq("bank", bank));
			registrationReport.put("bankCode", bank.getSwiftCode());
			registrationReport.put("loanAccount",
					customerbankAccountRepository.countByBankAndAccountMode(bank.getId(), AccountMode.LOAN));
			registrationReport.put("savingAccount",
					customerbankAccountRepository.countByBankAndAccountMode(bank.getId(), AccountMode.SAVING));
			registrationReport.put("currentAccount",
					customerbankAccountRepository.countByBankAndAccountMode(bank.getId(), AccountMode.CURRENT));
			registrationReport.put("odAccount",
					customerbankAccountRepository.countByBankAndAccountMode(bank.getId(), AccountMode.OD));
			registrationReport.put("branchCode", "-");
			registrationReport.put("activeUser", customerRepository.countAllCustomerByBank(bank.getId())
					- customerRepository.countCustomerWithoutTransactionOfBank(UserType.Customer, bank.getId()));
			registrationReport.put("blockedUser",
					customerRepository.countCustomerByBankAndStatus(bank.getId(), CustomerStatus.Blocked));
		} else {
			registrationReport.put("loanAccount", customerbankAccountRepository.countByAccountMode(AccountMode.LOAN));
			registrationReport.put("savingAccount",
					customerbankAccountRepository.countByAccountMode(AccountMode.SAVING));
			registrationReport.put("currentAccount",
					customerbankAccountRepository.countByAccountMode(AccountMode.CURRENT));
			registrationReport.put("odAccount", customerbankAccountRepository.countByAccountMode(AccountMode.OD));
			registrationReport.put("bankCode", "-");
			registrationReport.put("branchCode", "-");
			registrationReport.put("activeUser", customerRepository.countAllCustomer()
					- customerRepository.countCustomerWithoutTransaction(UserType.Customer));
			registrationReport.put("blockedUser", customerRepository.countCustomerByStatus(CustomerStatus.Blocked));
		}
		List<Customer> customerList = criteria.list();
		registrationReport.put("totalRegisteredCustomer", customerList.size());
		registrationReport.put("customerList", convertUtil.convertCustomerToDto(customerList));
		return registrationReport;
	}

	@Override
	public Long countCustomerByStatus(long currentUserId, CustomerStatus status) {
		User currentUser = userRepository.findOne(currentUserId);
		if (currentUser.getUserType() == UserType.Admin) {
			return customerRepository.countCustomerByStatus(status);
		} else if (currentUser.getUserType() == UserType.Bank) {
			return customerRepository.countCustomerByBankAndStatus(currentUser.getAssociatedId(), status);
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			return customerRepository.countCustomerByBranchAndStatus(currentUser.getAssociatedId(), status);
		}
		return 0L;
	}

	@Override
	public Long countCustomerThisNepaliMonthByBankAndDate(String bankId, Date thisMonthYear, Date previousDate) {
		// TODO Auto-generated method stub
		return customerRepository.countCustomerThisMonthOnlyFromADateByBankId(Long.parseLong(bankId), thisMonthYear,
				previousDate);
	}

	@Override
	public Long countUptoGivenMonthCustomerByBankAndDate(String bankId, Date previouDate) {
		// TODO Auto-generated method stub
		return (Long) customerRepository.countCustomerUpToLastMonth(Long.parseLong(bankId), previouDate);
	}

	@Override
	public Long countCustomerByBank(String bankId) {
		// TODO Auto-generated method stub
		return customerRepository.countAllCustomerByBank(Long.parseLong(bankId));
	}

	@Override
	public List<CustomerDTO> getCustomer(String fromDate, String toDate, String branchCode, String bankCode) {

		entityManager = entityManager.getEntityManagerFactory().createEntityManager();

		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Customer.class);
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType() == UserType.Bank) {
			if (branchCode != null && !branchCode.trim().equals("")) {
				BankBranch bankBranch = bankBranchRepository.findByBankAndBranchCode(currentUser.getAssociatedId(),
						branchCode);
				criteria.add(Restrictions.eq("bankBranch", bankBranch));
			} else {
				criteria.add(Restrictions.eq("bank", bankRepository.findOne(currentUser.getAssociatedId())));
			}
		} else if (currentUser.getUserType() == UserType.BankBranch) {
			criteria.add(Restrictions.eq("bankBranch", bankBranchRepository.findOne(currentUser.getAssociatedId())));
		}

		if (bankCode != null && !(bankCode.trim().equals(""))) {
			criteria.add(Restrictions.eq("bank", bankRepository.findBankByCode(bankCode)));
		}

		if (fromDate != null && !(fromDate.trim().equalsIgnoreCase(""))
				&& !(fromDate.trim().equalsIgnoreCase("undefined"))) {

			criteria.add(Restrictions.ge("created", DateUtil.getDate(fromDate, DateTypes.FROM_DATE)));
		}

		if (toDate != null && !(toDate.trim().equalsIgnoreCase("")) && !(toDate.trim().equalsIgnoreCase("undefined"))) {

			criteria.add(Restrictions.le("created", DateUtil.getDate(toDate, DateTypes.TO_DATE)));
		}

		criteria.addOrder(Order.desc("id"));
		List<Customer> customer = criteria.list();
		return convertUtil.convertCustomerToDto(customer);

	}

	@Override
	public HashMap<String, String> renewCustomer(String uniqueId, String createdBy) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		CustomerProfile profile = customerProfileRepository.findByCustomer(customer);
		CustomerBankAccount bankAccount = customerbankAccountRepository.findPrimaryAccount(customer, true);
		HashMap<String, String> response = deductServiceCharge(bankAccount, profile.getId(), BankServiceCharge.Renew);
		if (response.get("status").equals("success")) {
			changeStatus(customer.getUniqueId(), CustomerStatus.Approved, "Customer Renewed", createdBy);
			customer = customerRepository.findOne(customer.getId());
			customer.setLastRenewed(new Date());
			customerRepository.save(customer);
		} else {
			String remarks = null;
			if (response.get("isoCode") != null) {
				remarks = "Unable To Renew: " + response.get("Result Message") + ". iso code: "
						+ response.get("isoCode");
			} else {
				remarks = "Unable To Renew: " + response.get("Result Message");
			}
			customerLogApi.save(customer.getUniqueId(), remarks, createdBy);
		}
		return response;
	}

	@Override
	public List<CustomerDTO> getRenewedCustomer(int days, Long currentUserId) {
		User user = userRepository.findOne(currentUserId);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, -days);
		if (user.getUserType() == UserType.Admin) {
			List<Customer> customerList = customerRepository.getCustomerAfterLastRenewDate(calendar.getTime());
			return convertUtil.convertCustomerToDto(customerList);
		} else if (user.getUserType() == UserType.Bank) {
			List<Customer> customerList = customerRepository.getCustomerAfterLastRenewDateAndBank(calendar.getTime(),
					user.getAssociatedId());
			return convertUtil.convertCustomerToDto(customerList);
		} else if (user.getUserType() == UserType.BankBranch) {
			List<Customer> customerList = customerRepository.getCustomerAfterLastRenewDateAndBranch(calendar.getTime(),
					user.getAssociatedId());
			return convertUtil.convertCustomerToDto(customerList);
		}
		return null;
	}

	@Override
	public CustomerDTO getCustomerByBankAndMobileNumber(Long bankId, String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumberAndBank(mobileNumber, bankRepository.findOne(bankId));
		if (customer != null)
			return convertUtil.convertCustomer(customer);
		return null;
	}

	@Override
	public List<CustomerDTO> getAllCustomer() {
		List<Customer> customerList = customerRepository.findAll();
		if (customerList != null)
			return convertUtil.convertCustomerToDto(customerList);
		return null;
	}
}
