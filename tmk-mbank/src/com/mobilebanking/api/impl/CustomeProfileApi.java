package com.mobilebanking.api.impl;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerProfileApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.CustomerProfile;
import com.mobilebanking.entity.CustomerTransactionLog;
import com.mobilebanking.entity.FcmServerSetting;
import com.mobilebanking.entity.User;
import com.mobilebanking.fcm.PushNotification;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerProfileDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.ProfileStatus;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerProfileRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.CustomerTrasactionLogRepository;
import com.mobilebanking.repositories.FcmServerSettingRepository;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.DateUtil;
import com.mobilebanking.util.PasswordGenerator;

@Service
public class CustomeProfileApi implements ICustomerProfileApi {

	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	@Autowired
	private ConvertUtil convertUtil;

	@Autowired
	private CustomerTrasactionLogRepository customerTransactionRepository;

	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;

	@Autowired
	private CustomerTrasactionLogRepository customerTransactionLogRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private PushNotification pushNotification;

	@Autowired
	private FcmServerSettingRepository fcmServerSettingRepository;
	
	@Autowired
	private PasswordGenerator passwordGenerator;

	@Override
	public boolean saveProfile(CustomerProfileDTO customerProfileDto) {
		CustomerProfile customerProfile = new CustomerProfile();
		customerProfile.setName(customerProfileDto.getName());
		customerProfile.setPerTransactionLimit(customerProfileDto.getPerTxnLimit());
		customerProfile.setDailyTransactionLimit(customerProfileDto.getDailyTxnLimit());
		customerProfile.setWeeklyTransactionLimit(customerProfileDto.getWeeklyTxnLimit());
		customerProfile.setMonthlyTransactionLimit(customerProfileDto.getMonthlyTxnLimit());
		customerProfile.setDailyTransactionAmount(customerProfileDto.getDailyTransactionAmount());
		customerProfile.setRenewCharge(customerProfileDto.getRenewAmount());
		customerProfile.setRegistrationCharge(customerProfileDto.getRegistrationAmount());
		customerProfile.setPinResetCharge(customerProfileDto.getPinResetCharge());
		customerProfile.setStatus(ProfileStatus.Active);
		List<Customer> customerList = new ArrayList<>();
		customerProfile.setCustomer(customerList);
		customerProfile.setProfileUniqueId(passwordGenerator.generateProfileUniqueId());
		if (customerProfileDto.getBankId() != null) {
			Bank bank = bankRepository.findOne(customerProfileDto.getBankId());
			customerProfile.setBank(bank);
		}
		customerProfileRepository.save(customerProfile);
		return true;
	}

	@Override
	public boolean editProfile(CustomerProfileDTO customerProfileDto) {
		CustomerProfile customerProfile = customerProfileRepository.findById(customerProfileDto.getId());

		if (customerProfile.getBank() == null && customerProfileDto.getBankId() != null) {
			Bank bank = bankRepository.findOne(customerProfileDto.getBankId());
			customerProfile.setBank(bank);
		}

		customerProfile.setName(customerProfileDto.getName());
		customerProfile.setPerTransactionLimit(customerProfileDto.getPerTxnLimit());
		customerProfile.setDailyTransactionLimit(customerProfileDto.getDailyTxnLimit());
		customerProfile.setDailyTransactionAmount(customerProfileDto.getDailyTransactionAmount());
		customerProfile.setWeeklyTransactionLimit(customerProfileDto.getWeeklyTxnLimit());
		customerProfile.setMonthlyTransactionLimit(customerProfileDto.getMonthlyTxnLimit());
		customerProfile.setRenewCharge(customerProfileDto.getRenewAmount());
		customerProfile.setRegistrationCharge(customerProfileDto.getRegistrationAmount());
		customerProfile.setPinResetCharge(customerProfileDto.getPinResetCharge());
		customerProfileRepository.save(customerProfile);
		return true;
	}

	@Override
	public List<CustomerProfileDTO> listCustomerProfile(long bankId) {
		Bank bank = bankRepository.findOne(bankId);
		List<CustomerProfile> customerProfileList = null;
		if (bank != null) {
			customerProfileList = customerProfileRepository.findByBank(bank);
		} else {
			customerProfileList = new ArrayList<>();
		}
		customerProfileList.addAll(customerProfileRepository.findPreviousProfile());

		if (customerProfileList != null) {
			return convertUtil.convertCustomerProfileList(customerProfileList);
		}
		return null;
	}

	@Override
	public List<CustomerProfileDTO> listActiveCustomerProfile(Long bankId) {
		Bank bank = bankRepository.findOne(bankId);
		List<CustomerProfile> customerProfileList = null;
		if (bank != null) {
			customerProfileList = customerProfileRepository.findByBankAndStatus(bank.getId(), ProfileStatus.Inactive);
		} else {
			customerProfileList = new ArrayList<>();
		}
		customerProfileList.addAll(customerProfileRepository.findPreviousProfile());

		if (customerProfileList != null) {
			return convertUtil.convertCustomerProfileList(customerProfileList);
		}
		return null;
	}

	@Override
	public CustomerProfileDTO getCustomerProfile(String uuid) {
		Customer customer = customerRepository.findCustomerByUniqueId(uuid);
		CustomerProfile customerProfile = customerProfileRepository.findByCustomer(customer);
		if (customerProfile != null) {
			return convertUtil.convertCustomerProfile(customerProfile);
		}
		return null;
	}

	@Override
	@Transactional
	public HashMap<String, String> checkTransactionLimit(CustomerBankAccountDTO customerBankAccountDto,
			Double transactionamount) {

		CustomerBankAccount customerBankAccount = customerBankAccountRepository.findOne(customerBankAccountDto.getId());
		Customer customer = customerBankAccount.getCustomer();
		CustomerProfile customerProfile = customerProfileRepository.findByCustomer(customer);
		if (customerProfile == null) {
			customerProfile = customerProfileRepository.findByName("Default");
			if (customerProfile.getCustomer() != null) {
				customerProfile.getCustomer().add(customer);
			} else {
				List<Customer> customerList = new ArrayList<>();
				customerList.add(customer);
				customerProfile.setCustomer(customerList);
			}
			customerProfile = customerProfileRepository.save(customerProfile);
		} else {
			customerProfile = customerProfileRepository.findById(customerProfile.getId());
		}

		if (customerProfile.getStatus() == null) {
			customerProfile.setStatus(ProfileStatus.Active);
			customerProfile = customerProfileRepository.save(customerProfile);
		}

		CustomerTransactionLog customerTransactionLog = customerTransactionRepository
				.findByCustomerBankAccount(customerBankAccount);

		HashMap<String, String> hashResponse = new HashMap<>();
		if (customerTransactionLog != null) {
			if (transactionamount >= customerProfile.getPerTransactionLimit()) {
				hashResponse.put("message", "The transaction amount is greater than your transaction Limit");
				hashResponse.put("valid", "false");
				return hashResponse;
			}

			if (customerTransactionLog.getDailyTransactionLimit() >= customerProfile.getDailyTransactionLimit()) {
				hashResponse.put("message", "You have Reached your Daily Transaction Limit");
				hashResponse.put("valid", "false");
				return hashResponse;
			}

			if (customerTransactionLog.getWeeklyTransactionLimit() + transactionamount > customerProfile
					.getWeeklyTransactionLimit()) {
				hashResponse.put("message", "You have Reached your Weekly Transaction Limit");
				hashResponse.put("valid", "false");
				return hashResponse;
			}

			if (customerTransactionLog.getMonthlyTransactionLimit() + transactionamount > customerProfile
					.getMonthlyTransactionLimit()) {
				hashResponse.put("message", "You have Reached your Monthly Transaction Limit");
				hashResponse.put("valid", "false");
				return hashResponse;
			}

			if (customerTransactionLog.getDailyTransactionAmountLimit() != null
					&& customerProfile.getDailyTransactionAmount() != null) {
				if (customerTransactionLog.getDailyTransactionAmountLimit() + transactionamount > customerProfile
						.getDailyTransactionAmount()) {
					hashResponse.put("message", "You will exceed your Daily Transaction Limit with this transaction.");
					hashResponse.put("valid", "false");
					return hashResponse;
				}
			}
		}
		// if()
		hashResponse.put("valid", "true");
		return hashResponse;
	}

	@Override
	public void updateCustomerTransactionLog(CustomerBankAccount customerBankAccount, Double amount) {

		CustomerTransactionLog customerTransactionLog = customerTransactionRepository
				.findByCustomerBankAccount(customerBankAccount);
		if (customerTransactionLog != null) {
			customerTransactionLog.setDailyTransactionLimit(customerTransactionLog.getDailyTransactionLimit() + 1);
			customerTransactionLog
					.setWeeklyTransactionLimit(customerTransactionLog.getWeeklyTransactionLimit() + amount);
			customerTransactionLog
					.setMonthlyTransactionLimit(customerTransactionLog.getMonthlyTransactionLimit() + amount);
			if (customerTransactionLog.getDailyTransactionAmountLimit() != null) {
				customerTransactionLog.setDailyTransactionAmountLimit(
						customerTransactionLog.getDailyTransactionAmountLimit() + amount);
			} else {
				customerTransactionLog.setDailyTransactionAmountLimit(amount);
			}
			customerTransactionRepository.save(customerTransactionLog);
		} else {
			CustomerTransactionLog customerTransaction = new CustomerTransactionLog();
			customerTransaction.setDailyTransactionLimit((long) 1);
			customerTransaction.setWeeklyTransactionLimit(amount);
			customerTransaction.setMonthlyTransactionLimit(amount);
			customerTransaction.setCustomerBankAccount(customerBankAccount);
			customerTransaction.setDailyTransactionAmountLimit(amount);
			customerTransaction.setWeeklyTransactionDate(new Date());
			customerTransaction.setMonthlyTransactionDate(new Date());
			customerTransactionRepository.save(customerTransaction);
		}

	}

	@Override
	public void resetDailyTransactionLog(CustomerBankAccount customerBankAccount) {
		CustomerTransactionLog customerTransactionLog = customerTransactionRepository
				.findByCustomerBankAccount(customerBankAccount);
		if (customerTransactionLog != null) {
			customerTransactionLog.setDailyTransactionLimit((long) 0);
			customerTransactionLog.setDailyTransactionAmountLimit(((Double) 0.0));
			customerTransactionRepository.save(customerTransactionLog);
		}
	}

	@Override
	public void resetWeeklyTransactionLog(CustomerBankAccount customerBankAccount) {
		CustomerTransactionLog customerTransactionLog = customerTransactionRepository
				.findByCustomerBankAccount(customerBankAccount);
		if (customerTransactionLog != null) {
			customerTransactionLog.setWeeklyTransactionLimit((Double) 0.0);
			customerTransactionRepository.save(customerTransactionLog);
		}
	}

	@Override
	public void resetMonthlyTransactionLog(CustomerBankAccount customerBankAccount) {
		CustomerTransactionLog customerTransactionLog = customerTransactionRepository
				.findByCustomerBankAccount(customerBankAccount);
		if (customerTransactionLog != null) {
			customerTransactionLog.setMonthlyTransactionLimit(0.0);
			customerTransactionRepository.save(customerTransactionLog);
		}
	}

	@Override
	public CustomerProfileDTO getProfileById(long profileId) {
		CustomerProfile customerProfile = customerProfileRepository.findOne(profileId);
		if (customerProfile != null) {
			return convertUtil.convertCustomerProfile(customerProfile);
		}
		return null;
	}

	/*
	 * @Scheduled(cron = "0 0 0 * * *") void checkAndUpdateTrailPeriod() {
	 * List<CustomerProfile> customerProfiles =
	 * customerProfileRepository.findByRegistrationCharge(true);
	 * 
	 * for (CustomerProfile customerProfile : customerProfiles) { for(Customer
	 * customer :customerProfile.getCustomer() ){ if (!customer.getIsBlocked())
	 * { List<CustomerBankAccount> customerBankAccountList =
	 * customerBankAccountRepository.findByCustomer(customer);
	 * 
	 * CustomerRegistrationLog customerRestrationLog =
	 * customerRegistrationLogRepository .findByCustomer(customer);
	 * 
	 * if (customerRestrationLog.getTrialInterval() <= 0) {
	 * 
	 * if (customerProfile.getIsRegistrationCharge() == true) {
	 * 
	 * // fund transfer from customer account to bank CustomerBankAccount
	 * customerBankAccount = null; // fund transfer from customer account to
	 * bank for(CustomerBankAccount customerAccount :customerBankAccountList){
	 * if (customerAccount.isPrimary()){ customerBankAccount = customerAccount;
	 * } };
	 * 
	 * String fundTrasferResponse =
	 * customerApi.fundTransfer(customerBankAccount.getAccountNumber(),
	 * customerBankAccount.getBranch().getBank().getAccountNumber(),
	 * customerProfile.getRegistrationCharge(),customerBankAccount.getBranch().
	 * getBank()); if (fundTrasferResponse.equals("success")) {
	 * customer.setIsTrialPeriod(false); customerRepository.save(customer);
	 * customerProfile.setRegistrationStatus(RegistrationStatus.REGISTERED);
	 * customerProfileRepository.save(customerProfile); Long id =
	 * transactionCore.transactioncCustomerToBank(customerBankAccount,
	 * customerProfile.getRegistrationCharge()); String transactionId =
	 * transactionRepository.findOne(id).getTransactionIdentifier();
	 * transactionCore.ledgerCustomerToBank(customerProfile.
	 * getRegistrationCharge(), transactionId, customerBankAccount); //
	 * transactionCore.accountCustomerToBank(customerProfile.
	 * getRegistrationCharge(),customerBankAccount); } else if
	 * (fundTrasferResponse.equals("failure")) { customer.setIsBlocked(true);
	 * customerRepository.save(customer);
	 * customerProfile.setRegistrationStatus(RegistrationStatus.PENDING);
	 * customerProfileRepository.save(customerProfile); }
	 * 
	 * } } else { customerRestrationLog.setTrialInterval(customerRestrationLog.
	 * getTrialInterval() - 1);
	 * customerRegistrationLogRepository.save(customerRestrationLog); }
	 * 
	 * } } } }
	 */

	@Override
	@Scheduled(cron = "0 0 0 * * *")

	// @Scheduled(fixedRate = 10000)
	public void checkAndUpdateRenewPeriod() {
		checkTransactionLogs();
		checkBankLowBalanceAlert();
		checkCustomerRenew();
	}

	private void checkCustomerRenew() {
		try {
			for (Customer customer : customerRepository.findAll()) {
				try {
					customer = customerRepository.findOne(customer.getId());
					if (customer.getStatus() == CustomerStatus.Approved
							|| customer.getStatus() == CustomerStatus.Expired) {
						boolean expired = false;
						if (customer.getLastRenewed() == null) {
							if (DateUtil.isCustomerExpired(customer.getCreated(), new Date())) {
								expired = true;
							}
						} else if (DateUtil.isCustomerExpired(customer.getLastRenewed(), new Date())) {
							expired = true;
						}
						if (expired) {
							customerApi.changeStatus(customer.getUniqueId(), CustomerStatus.Expired, "Customer Expired",
									"SYSTEM");
							customer = customerRepository.findOne(customer.getId());
							customer.setExpiredDate(new Date());
							customer = customerRepository.save(customer);
							customerApi.renewCustomer(customer.getUniqueId(), "SYSTEM");
							try {
								User user = customer.getUser();
								FcmServerSetting fcmServerSetting = fcmServerSettingRepository
										.findByBankAndFcmServerKey(user.getBank().getId(), user.getServerKey());
								pushNotification.sendNotification("Customer Expired",
										"Customer Expired,initiate logout.", user.getDeviceToken(), "Customer Expired",
										fcmServerSetting.getFcmServerID());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkBankLowBalanceAlert() {
		try {
			Iterable<Bank> bankList = bankRepository.findAll();
			for (Bank bank : bankList) {
				try {
					if (bank.getIsAlertSent() != null && bank.getIsAlertSent() == true) {
						bank.setIsAlertSent(false);
						bankRepository.save(bank);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkTransactionLogs() {
		try {
			List<CustomerTransactionLog> customerTransactionLogsList = customerTransactionRepository.findAll();
			new SimpleDateFormat("dd MM yyyy");
			for (CustomerTransactionLog customerTransactionLog : customerTransactionLogsList) {
				try {
					customerTransactionLog.setDailyTransactionLimit(0L);
					customerTransactionLog.setDailyTransactionAmountLimit(0.0);
					customerTransactionLog = customerTransactionRepository.save(customerTransactionLog);
					long days = ChronoUnit.DAYS.between(customerTransactionLog.getWeeklyTransactionDate().toInstant(),
							new Date().toInstant());
					if (days >= 7) {
						customerTransactionLog = customerTransactionRepository.findOne(customerTransactionLog.getId());
						customerTransactionLog.setWeeklyTransactionDate(new Date());
						customerTransactionLog.setWeeklyTransactionLimit(0.0);
						customerTransactionLog = customerTransactionLogRepository.save(customerTransactionLog);
					}
					long monthlydays = ChronoUnit.DAYS.between(
							customerTransactionLog.getMonthlyTransactionDate().toInstant(), new Date().toInstant());
					if (monthlydays >= 30) {
						customerTransactionLog = customerTransactionRepository.findOne(customerTransactionLog.getId());
						customerTransactionLog.setMonthlyTransactionDate(new Date());
						customerTransactionLog.setMonthlyTransactionLimit(0.0);
						customerTransactionLog = customerTransactionLogRepository.save(customerTransactionLog);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public CustomerProfile addCustomerToProfile(long customerProfileId, Customer customer) {
		CustomerProfile customerProfile = customerProfileRepository.findById(customerProfileId);
		if (customerProfile.getCustomer() != null) {
			customerProfile.getCustomer().add(customer);
		} else {
			List<Customer> customerList = new ArrayList<>();
			customerList.add(customer);
			customerProfile.setCustomer(customerList);
		}
		customerProfile = customerProfileRepository.save(customerProfile);
		return customerProfile;
	}

	@Override
	// @Transactional
	public CustomerProfile updateCustomerToProfile(long customerProfileId, Customer customer) {
		CustomerProfile customerProfile = customerProfileRepository.findByCustomer(customer);
		if (customerProfile != null) {
			customerProfile = customerProfileRepository.findById(customerProfile.getId());
			customerProfile.getCustomer().remove(customer);
			customerProfileRepository.save(customerProfile);
		}
		CustomerProfile customerProfilenew = customerProfileRepository.findById(customerProfileId);
		if (customerProfilenew.getCustomer() != null) {
			customerProfilenew.getCustomer().add(customer);
		} else {
			List<Customer> customerList = new ArrayList<>();
			customerList.add(customer);
			customerProfile.setCustomer(customerList);
		}
		customerProfileRepository.save(customerProfilenew);
		return customerProfile;
	}

	@Override
	public boolean changeStatus(long customerProfileId) {
		CustomerProfile customerProfile = customerProfileRepository.findOne(customerProfileId);
		if (customerProfile.getStatus() == null) {
			customerProfile.setStatus(ProfileStatus.Active);
		} else {
			if (customerProfile.getStatus().equals(ProfileStatus.Active)) {
				customerProfile.setStatus(ProfileStatus.Inactive);
			} else {
				customerProfile.setStatus(ProfileStatus.Active);
			}

		}
		customerProfile = customerProfileRepository.save(customerProfile);
		return true;
	}

	@Override
	public BankDTO getBankByProfile(long profileId) {
		Bank bank = customerProfileRepository.findBankByProfile(profileId);
		return ConvertUtil.convertBank(bank);
	}

}