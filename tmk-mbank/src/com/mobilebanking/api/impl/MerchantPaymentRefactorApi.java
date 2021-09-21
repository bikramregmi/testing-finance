package com.mobilebanking.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.airlines.model.PassengerTotalDto;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.IMerchantPaymentRefactorApi;
import com.mobilebanking.api.ITransactionLogApi;
import com.mobilebanking.core.CommissionCore;
import com.mobilebanking.core.TransactionCore;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.AccountPosting;
import com.mobilebanking.entity.AirliesResponseDetail;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankAccountSettings;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.entity.SettlementLog;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.IsoResponseCode;
import com.mobilebanking.model.SettlementStatus;
import com.mobilebanking.model.SettlementType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;
import com.mobilebanking.plasmatech.IssueTicketRes;
import com.mobilebanking.plasmatech.Passenger;
import com.mobilebanking.repositories.AccountPostingRepository;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.AirlinesResponseDetailRepository;
import com.mobilebanking.repositories.BankAccountSettingsRepository;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankMerchantAccountsRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.MerchantManagerRepository;
import com.mobilebanking.repositories.MerchantServiceRepository;
import com.mobilebanking.repositories.SettlementLogRepository;
import com.mobilebanking.repositories.TransactionRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.ServiceDetector;
import com.mobilebanking.util.StringConstants;
import com.wallet.serviceprovider.ntc.WebServiceException_Exception;
import com.wallet.serviceprovider.paypoint.paypointResponse.NeaBillAmountResponse;
import com.wallet.serviceprovider.paypoint.paypointResponse.WorldLinkPackage;
import com.wallet.serviceprovider.paypoint.service.PayPoint_GetCompanyInfoService;

@Service
public class MerchantPaymentRefactorApi implements IMerchantPaymentRefactorApi {

	@Autowired
	private MerchantServiceRepository serviceRepository;
	@Autowired
	private MerchantManagerRepository managerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private BankBranchRepository bankBranchRepository;
	@Autowired
	private TransactionCore transactionCore;
	@Autowired
	private ServiceDetector serviceDectetor;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private CommissionCore commissionCore;
	@Autowired
	private PayPoint_GetCompanyInfoService payPointService;
	@Autowired
	private CustomerBankAccountRepository customerBankRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ICustomerApi customerApi;
	@Autowired
	private BankAccountSettingsRepository bankAccountSettingsRepository;
	@Autowired
	private ITransactionLogApi transactionLogApi;
	@Autowired
	private BankMerchantAccountsRepository bankMerchantAccountRepository;
	@Autowired
	private AccountPostingRepository accountPostingRepository;
	@Autowired
	private SettlementLogRepository settlementLogRepository;
	@Autowired
	private AirlinesResponseDetailRepository airlinesDetailRepository;

	@Override
	public HashMap<String, String> merchantPayment(String serviceId, String transactionIdentifier, double amount,
			long userAssociateId, String accountNumber, HashMap<String, String> hashInput) throws Exception {
		HashMap<String, String> hashResponse = new HashMap<String, String>();
		MerchantService service = serviceRepository.findMerchantServiceByIdentifier(serviceId);
		if (service != null) {
			MerchantManager merchantManager = managerRepository.getselected(service.getUniqueIdentifier(),
					Status.Active, true);
			if (merchantManager != null) {/*
											 * this line is removed by amrit for
											 * test merchantManager.
											 * getMerchantsAndServices().
											 * getMerchant();
											 */
				User user = userRepository.findOne(userAssociateId);
				if (user.getUserType().equals(UserType.Customer) || user.getUserType() == UserType.Customer) {
					CustomerBankAccount customerBankAccount = customerBankRepository
							.findCustomerAccountByAccountNumber(accountNumber, user.getAssociatedId());
					BankBranch bankBranch = customerBankAccount.getBranch();
					hashResponse = merchantPaymentCustomer(merchantManager, transactionIdentifier, service,
							userAssociateId, amount, bankBranch, hashInput, customerBankAccount);
				} else if (user.getUserType().equals(UserType.Bank) || user.getUserType() == UserType.Bank) {
					Bank bank = bankRepository.findOne(user.getAssociatedId());
					hashResponse = merchantPaymentBank(merchantManager, transactionIdentifier, service, userAssociateId,
							amount, bank, hashInput);
				} else if (user.getUserType().equals(UserType.BankBranch)
						|| user.getUserType() == UserType.BankBranch) {
					BankBranch bankBranch = bankBranchRepository.findOne(user.getAssociatedId());
					hashResponse = merchantPaymentBankBranch(merchantManager, transactionIdentifier, service,
							userAssociateId, amount, bankBranch, hashInput);
				}
			} else {
				hashResponse.put("status", "failure");
				hashResponse.put("Result Message",
						"The service is currently unavailable. Please contact Administrator.");
			}

		}

		return hashResponse;
	}

	@Override
	public HashMap<String, Object> merchantPaymentAirlies(String serviceId, String strFlightId, double amount,
			Long userAssociateId, String accountNumber, HashMap<String, Object> hashRequest) {
		HashMap<String, Object> hashResponse = new HashMap<String, Object>();
		MerchantService service = serviceRepository.findMerchantServiceByIdentifier(serviceId);
		if (service != null) {
			MerchantManager merchantManager = managerRepository.getselected(service.getUniqueIdentifier(),
					Status.Active, true);
			if (merchantManager != null) {
				merchantManager.getMerchantsAndServices().getMerchant();
				User user = userRepository.findOne(userAssociateId);
				if (user.getUserType().equals(UserType.Customer) || user.getUserType() == UserType.Customer) {
					customerRepository.getCustomerByUser(user.getId());
					CustomerBankAccount customerBankAccount = customerBankRepository
							.findCustomerAccountByAccountNumber(accountNumber, user.getAssociatedId());
					BankBranch bankBranch = customerBankAccount.getBranch();
					try {
						hashResponse = airlinesPayment(merchantManager, strFlightId, service, userAssociateId, amount,
								bankBranch, hashRequest, customerBankAccount);
					} catch (ClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					hashResponse.put("status", "failure");
					hashResponse.put("Result Message",
							"The service is currently unavailable. Please contact Administrator.");
				}

			}
		}
		return hashResponse;
	}

	/*
	 * private HashMap<String,String> merchantPaymentCustomer(MerchantManager
	 * manager, String transactionIdentifier, MerchantService service, Long
	 * userAssociateId, double amount, BankBranch bankBranch, HashMap<String,
	 * String> hashInput, CustomerBankAccount customerBankAccount) throws
	 * ClientException, UnknownHostException, WebServiceException_Exception,
	 * JAXBException, Exception { HashMap<String, String> hashResponse = new
	 * HashMap<String, String>(); boolean valid = true; Customer customer =
	 * customerRepository.getCustomerByUser(userAssociateId); HashMap<String,
	 * String> hashRequest = new HashMap<String, String>();
	 * hashRequest.put("serviceTo", transactionIdentifier);
	 * hashRequest.put("amount", "" + amount); hashRequest.put("serviceId",
	 * service.getUniqueIdentifier()); hashRequest.put("Utility Code" , ""+
	 * hashInput.get("Utility Code")); hashRequest.put("Bill Number" , ""+
	 * hashInput.get("Bill Number")); hashRequest.put("Reserve Info" , ""+
	 * hashInput.get("Reserve Info")); hashRequest.put("RefStan" , ""+
	 * hashInput.get("RefStan")); hashRequest.put("Commission Type" , ""+
	 * hashInput.get("Commission Type")); hashRequest.put("Commission Value" ,
	 * ""+ hashInput.get("Commission Value")); hashRequest.put("package",
	 * "true"); //debit customer account from bank just before creating
	 * transaction long id =
	 * transactionCore.transactionBankBranchToParking(manager, userAssociateId,
	 * amount, bankBranch, hashRequest);
	 * transactionCore.ledgerBankBranchToParking(amount, bankBranch,id);
	 * transactionCore.accountBankBranchToParking(bankBranch, amount); //call to
	 * iso for moving into parking account Transaction transaction =
	 * transactionRepository.findOne(id); String isoResponse =null;
	 * BankAccountSettings bankAccountSettings =
	 * bankAccountSettingsRepository.getBankAccountSettingsByBankCodeAndStatus(
	 * transaction.getBank().getSwiftCode(), Status.Active); // String
	 * isoResponse = isoCallForParkingForCustomer(bankBranch.getBank(), amount,
	 * // customerBankAccount.getAccountNumber(),
	 * bankAccountSettings.getBankParkingAccountNumber()); if
	 * (isoResponse.equals("failure")) { hashResponse.put("status", "failure");
	 * hashResponse.put("message", "Could not process the transaction");
	 * transactionLogApi.createTransactionLog(transaction,
	 * "Transaction Processing Failed", UserType.Customer,
	 * customer.getUniqueId()); return hashResponse; }
	 * createAccountPosting(customerBankAccount.getAccountNumber(),
	 * bankAccountSettings.getBankParkingAccountNumber(), amount, transaction);
	 * hashRequest.put("transactionId",
	 * transactionRepository.findOne(id).getTransactionIdentifier());
	 * 
	 * hashResponse =
	 * serviceDectetor.detectService(service.getUniqueIdentifier(),
	 * hashRequest);
	 * 
	 * transactionLogApi.createTransactionLog(transaction,
	 * "Transaction Pending", UserType.Customer, ""+customer.getUniqueId()); if
	 * (hashResponse == null || hashResponse.isEmpty()) { valid = false; } else
	 * { if (hashResponse.get("status").equals("success")) { long bankId =
	 * transaction.getBank().getId(); long merchantId =
	 * transaction.getMerchantManager().getMerchantsAndServices().getMerchant().
	 * getId(); transactionLogApi.createTransactionLog(transaction,
	 * "Transaction Completed", UserType.Customer, ""+customer.getUniqueId());
	 * 
	 * 
	 * String destinationAccountNumber =
	 * bankAccountSettings.getOperatorAccountNumber(); String
	 * sourceAccountNumber = bankAccountSettings.getOperatorAccountNumber();
	 * //need to create an input field in the UI //compare all those status
	 * first and then ledger those customerApi.fundTransfer(sourceAccountNumber,
	 * destinationAccountNumber, transaction.getOperatorCommissionAmount());
	 * createAccountPosting(sourceAccountNumber, destinationAccountNumber,
	 * transaction.getOperatorCommissionAmount(), transaction); //channel
	 * partner destinationAccountNumber =
	 * bankAccountSettings.getChannelPartnerAccountNumber();
	 * 
	 * customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber,
	 * transaction.getChannelPartnerCommissionAmount());
	 * createAccountPosting(sourceAccountNumber, destinationAccountNumber,
	 * transaction.getChannelPartnerCommissionAmount(), transaction);
	 * 
	 * //now to merchant String merchantBankAccount =
	 * bankMerchantAccountRepository.findMerchantAccountByBankAndMerchantStatus(
	 * bankId, merchantId, Status.Active); if (merchantBankAccount != null) {
	 * customerApi.fundTransfer(sourceAccountNumber,merchantBankAccount,
	 * transaction.getAmount() - transaction.getTotalCommission());
	 * createAccountPosting(sourceAccountNumber, destinationAccountNumber,
	 * transaction.getChannelPartnerCommissionAmount(), transaction); } //
	 * destinationAccountNumber =
	 * bankAccountSettingsRepository.findAccountNumberOfMerchantByBank(bankId,
	 * AccountType.MERCHANT, //
	 * transaction.getMerchantManager().getMerchantsAndServices().getMerchant().
	 * getId()); // customerApi.fundTransfer(sourceAccountNumber,
	 * destinationAccountNumber, transaction.getAmount() -
	 * transaction.getBankCommissionAmount()); destinationAccountNumber =
	 * bankAccountSettings.getBankPoolAccountNumber(); sourceAccountNumber =
	 * bankAccountSettings.getBankParkingAccountNumber();
	 * 
	 * customerApi.fundTransfer(sourceAccountNumber, destinationAccountNumber,
	 * transaction.getTotalCommission()-(transaction.getOperatorCommissionAmount
	 * ()+transaction.getChannelPartnerCommissionAmount()));
	 * transactionIdentifier = transactionCore.transactionParkingToService(id,
	 * service, hashResponse); transactionCore.ledgerParkingToService(amount,
	 * service,transactionIdentifier);
	 * transactionCore.accountParkingToService(amount, service); } else
	 * if(hashResponse.get("status").equals("unknown")) {
	 * transactionCore.setAmbiguousTransaction(id,hashResponse); }
	 * 
	 * else { //reverse money of customer back to bank
	 * customerApi.fundTransfer(bankAccountSettings.getBankParkingAccountNumber(
	 * ), customerBankAccount.getAccountNumber(), amount); valid = false;
	 * transactionIdentifier =
	 * transactionCore.reverseTransactionParkingToBankBranch(id, bankBranch,
	 * hashResponse); transactionCore.reverseLedgerParkingToBankBranch(amount,
	 * bankBranch,transactionIdentifier);
	 * transactionCore.reverseAccountParkingToBankBranch(amount, bankBranch); }
	 * } return hashResponse; }
	 */

	public void createAccountPosting(String fromAccountNumber, String toAccountNumber, double amount,
			Transaction transaction) {
		transaction = transactionRepository.findOne(transaction.getId());
		String remarks = StringConstants.ACCOUNT_POSTING_FUND_TRANSFER;
		remarks = remarks.replace("{amount}", "" + amount);
		remarks = remarks.replace("{accountNumber1}", fromAccountNumber);
		remarks = remarks.replace("{accountNumber2}", toAccountNumber);
		AccountPosting accountPosting = new AccountPosting();
		accountPosting.setAmount(amount);
		accountPosting.setFromAccountNumber(fromAccountNumber);
		accountPosting.setToAccountNumber(toAccountNumber);
		accountPosting.setRemarks(remarks);
		accountPosting.setTransaction(transaction);
		accountPostingRepository.save(accountPosting);

	}

	public HashMap<String, String> isoCallForParkingForCustomer(Bank bank, double amount, String customerAccountNumber,
			String parkingAccountNumber, String remarksOne, String remarksTwo, String desc1) {
		HashMap<String, String> response = null;

		try {
			response = customerApi.fundTransfer(customerAccountNumber, parkingAccountNumber, amount, bank, remarksOne,
					remarksTwo, desc1);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "faliure");
			response.put("code", "message_exception_error");
			return response;
		}

	}

	@Override
	public WorldLinkPackage worldLinkCheck(String serviceId, String transactionId, double amount, Long id)
			throws JAXBException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		WorldLinkPackage worldLinkPackage = new WorldLinkPackage();
		MerchantManager merchantManager = null;
		if (serviceId.equals(StringConstants.WLINK_TOPUP))
			merchantManager = managerRepository.getselected(StringConstants.WLINK_TOPUP, Status.Active, true);
		else if (serviceId.equals(StringConstants.VIANET))
			merchantManager = managerRepository.getselected(StringConstants.VIANET, Status.Active, true);
		if (merchantManager == null) {
			hashRequest.put("status", "failure");
			hashRequest.put("Result Message",
					"The Selected Merchant is Not Available. Please Contact the Administrator.");
			worldLinkPackage.setHashResponse(hashRequest);
			return worldLinkPackage;
		} else {
			hashRequest.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
			hashRequest.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
			hashRequest.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());
		}
		MerchantService service = serviceRepository.findMerchantServiceByIdentifier(serviceId);
		hashRequest.put("serviceTo", transactionId);
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		if (serviceId.equals(StringConstants.VIANET)) {
			hashRequest.put("company code", "716");
			hashRequest.put("serviceCode", "0");
		} else if (serviceId.equals(StringConstants.WLINK_TOPUP)) {
			hashRequest.put("company code", "597");
			hashRequest.put("serviceCode", "0");
		}
		worldLinkPackage = payPointService.checkPakageForWorldLink(hashRequest);

		return worldLinkPackage;
	}

	@Override
	public HashMap<String, String> merchantPayment(String serviceId, String transactionIdentifier, double amount,
			Long userAssociateId, HashMap<String, String> hashInput) throws Exception {
		HashMap<String, String> hashResponse = new HashMap<String, String>();
		MerchantService service = serviceRepository.findMerchantServiceByIdentifier(serviceId);
		if (service != null) {
			MerchantManager merchantManager = managerRepository.getselected(service.getUniqueIdentifier(),
					Status.Active, true);
			if (merchantManager != null) {
				merchantManager.getMerchantsAndServices().getMerchant();
				User user = userRepository.findOne(userAssociateId);
				if (user.getUserType().equals(UserType.Bank) || user.getUserType() == UserType.Bank) {
					Bank bank = bankRepository.findOne(user.getAssociatedId());
					hashResponse = merchantPaymentBank(merchantManager, transactionIdentifier, service, userAssociateId,
							amount, bank, hashInput);
				} else if (user.getUserType().equals(UserType.BankBranch)
						|| user.getUserType() == UserType.BankBranch) {
					BankBranch bankBranch = bankBranchRepository.findOne(user.getAssociatedId());
					hashResponse = merchantPaymentBankBranch(merchantManager, transactionIdentifier, service,
							userAssociateId, amount, bankBranch, hashInput);
				}
			}
		}

		return hashResponse;
	}

	@Override
	public NeaBillAmountResponse getNeaPaymentAmount(String serviceId, String transactionId, String scno,
			String officeCode) throws JAXBException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		MerchantManager merchantManager = managerRepository.getselected(StringConstants.NEA, Status.Active, true);
		NeaBillAmountResponse neaBillResponse = new NeaBillAmountResponse();
		if (merchantManager == null) {
			hashRequest.put("status", "failure");
			hashRequest.put("Result Message",
					"The Selected Merchant is Not Available. Please Contact the Administrator.");
			neaBillResponse.setHashResponse(hashRequest);
			return neaBillResponse;
		} else {
			hashRequest.put("username", merchantManager.getMerchantsAndServices().getMerchant().getApiUsername());
			hashRequest.put("password", merchantManager.getMerchantsAndServices().getMerchant().getApiPassWord());
			hashRequest.put("apiurl", merchantManager.getMerchantsAndServices().getMerchant().getMerchantApiUrl());
		}
		MerchantService service = serviceRepository.findMerchantServiceByIdentifier(serviceId);
		hashRequest.put("serviceTo", scno);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		hashRequest.put("customerId", transactionId);
		hashRequest.put("officeCode", officeCode);
		hashRequest.put("company code", "598");
		hashRequest.put("serviceCode", "0");
		neaBillResponse = payPointService.checkNeaPaymentAmount(hashRequest);
		return neaBillResponse;
	}

	// @Override
	// public HashMap<String, String> merchantPayment(String serviceId, String
	// transactionIdentifier, double amount,
	// long userId, String accountNumber) throws UnknownHostException,
	// ClientException, WebServiceException_Exception, JAXBException, Exception
	// {
	// boolean valid = true;
	// HashMap<String,String> hashResponse = new HashMap<String,String>();
	// MerchantService service =
	// serviceRepository.findMerchantServiceByIdentifier(serviceId);
	// if (service != null) {
	// MerchantManager manager = managerRepository.getselected(serviceId,
	// Status.Active, true);
	// Merchant merchant = manager.getMerchantsAndServices().getMerchant();
	// User user = userRepository.findOne(userId);
	// if (user.getUserType() == UserType.Customer ||
	// user.getUserType().equals(UserType.Customer)) {
	// Customer sourceCustomer =
	// customerRepository.findOne(user.getAssociatedId());
	// CustomerBankAccount customerBankAccount =
	// customerBankAccountRepository.findCustomerAccountByAccountNumber(
	// accountNumber, sourceCustomer.getId());
	// hashResponse = merchantPaymentCustomer(manager, transactionIdentifier,
	// service, sourceCustomer.getId(),
	// amount, sourceCustomer, customerBankAccount);
	// hashResponse.put("serviceTo", transactionIdentifier);
	// hashResponse.put("transactionDate", ""+new Date());
	// } else if (user.getUserType() == UserType.Bank ||
	// user.getUserType().equals(UserType.Bank)) {
	//
	// Bank bank = bankRepository.findOne(user.getAssociatedId());
	// hashResponse = merchantPaymentBank(manager, transactionIdentifier,
	// service, userId, amount, bank);
	//
	// hashResponse.put("transactionDate", ""+new Date());
	// hashResponse.put("ServiceTo", transactionIdentifier);
	//
	// } else if (user.getUserType() == UserType.BankBranch ||
	// user.getUserType().equals(UserType.BankBranch)) {
	//
	// BankBranch bankBranch =
	// bankBranchRepository.findOne(user.getAssociatedId());
	//
	// hashResponse = merchantPaymentBankBranch(manager, transactionIdentifier,
	// service,
	// userId, amount, bankBranch);
	// hashResponse.put("transactionDate", ""+new Date());
	// hashResponse.put("ServiceTo", transactionIdentifier);
	// }
	// }
	// return hashResponse;
	// }

	public HashMap<String, String> merchantPaymentBank(MerchantManager manager, String transactionIdentifier,
			MerchantService service, long userAssociatedId, double amount, Bank bank, HashMap<String, String> hash)
			throws ClientException, UnknownHostException, WebServiceException_Exception, JAXBException, Exception {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		if (!manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.SMART_CELL)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.BROADLINK)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.BROADTEL)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.DISHHOME_PIN)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.NET_TV)) {
			hashRequest.put("serviceTo", transactionIdentifier);
		}
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());

		HashMap<String, String> hashResponse = new HashMap<>();
		long id = transactionCore.transactionBankToParking(manager, userAssociatedId, amount, bank, hashRequest);
		transactionCore.ledgerBankToParking(amount, bank, id);
		Transaction transaction = transactionRepository.findOne(id);
		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(), Status.Active);
		HashMap<String, String> isoResponse = new HashMap<>();
		if (StringConstants.IS_PRODUCTION) {
			isoResponse = customerApi.fundTransfer(bank.getBankTranferAccount(),
					bankAccountSettings.getBankParkingAccountNumber(), amount, bank);
		} else {
			isoResponse.put("status", "success");
		}

		// transactionCore.accountBankToParking(bank,amount);
		// createAccountPosting(sourceAccountNumber, destinationAccountNumber,
		// transaction.getChannelPartnerCommissionAmount(), transaction);

		if (isoResponse.get("status").equals("failure")) {
			hashResponse.put("status", "failure");
			hashResponse.put("Result Message", "Could not process the transaction,Please try again later");
			transactionLogApi.createTransactionLog(transaction, "Transaction Failed", UserType.BankBranch,
					"" + bank.getId());
			String txnIdentifier = transactionCore.reverseTransactionParkingToBank(id, bank, hashResponse);
			// Account toAccount =
			// accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
			// commissionCore.transferBalanceFromPool(transaction, toAccount);
			transactionCore.reverseLedgerParkingToBank(amount, bank, txnIdentifier);
			return hashResponse;
		}
		createAccountPosting(bank.getBankTranferAccount(), bankAccountSettings.getBankParkingAccountNumber(), amount,
				transaction);
		Account fromAccount = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		commissionCore.transferBalanceToPool(transaction, fromAccount);

		if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.WLINK_TOPUP)) {
			hashRequest.put("Utility Code", "" + hash.get("Utility Code"));
			hashRequest.put("Bill Number", "" + hash.get("Bill Number"));
			hashRequest.put("Reserve Info", "" + hash.get("Reserve Info"));
			hashRequest.put("RefStan", "" + hash.get("RefStan"));
			hashRequest.put("Commission Type", "" + hash.get("Commission Type"));
			hashRequest.put("Commission Value", "" + hash.get("Commission Value"));
			hashRequest.put("package", "true");
			hashRequest.put("packageId", hash.get("packageId"));
		} else if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.SUBISU)) {
			hashRequest.put("subisuUsername", hash.get("subisuUsername"));
		} else if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.NEA)) {

			hashRequest.put("officeCode", hash.get("officeCode"));
			hashRequest.put("Utility Code", "" + hash.get("Utility Code"));
			hashRequest.put("Bill Number", "" + hash.get("Bill Number"));
			hashRequest.put("RefStan", "" + hash.get("RefStan"));
			hashRequest.put("Commission Value", "" + hash.get("Commission Value"));
			hashRequest.put("payment", hash.get("payment"));
			hashRequest.put("customerId", hash.get("customerId"));
		}

		hashRequest.put("transactionId", transactionRepository.findOne(id).getTransactionIdentifier());
		hashResponse = serviceDectetor.detectService(service.getUniqueIdentifier(), hashRequest);
		;

		transactionLogApi.createTransactionLog(transaction, "Transaction Pending", UserType.Bank, "" + bank.getId());
		if (hashResponse == null || hashResponse.isEmpty()) {
		} else {
			if (hashResponse.get("status").equals("success")) {

				Account toAccount = accountRepository
						.findAccountByAccountNumber(manager.getMerchantsAndServices().getMerchant().getAccountNumber());
				String transactionId = transactionCore.transactionParkingToMerchant(id, manager, hashResponse);
				transactionCore.ledgerParkingToMerchant(amount - transaction.getTotalCommission(), manager,
						transactionId);
				// transactionCore.accountParkingToService(amount, service);
				commissionCore.transferBalanceFromPoolToMerchant(transaction, toAccount,
						amount - transaction.getTotalCommission());

				// commissionCore.updateBalanceToBank(transaction);
				fromAccount = accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());

				commissionCore.allocateCommissions(transaction);

				transactionLogApi.createTransactionLog(transaction, "Transaction Completed", UserType.Bank,
						"" + bank.getId());
				long bankId = transaction.getBank().getId();
				long merchantId = transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getId();

				String destinationAccountNumber = bankAccountSettings.getOperatorAccountNumber();
				// need a change here
				String sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber(); // need
																								// to
																								// create
																								// an
																								// input
																								// field
																								// in
																								// the
																								// UI
				if (transaction.getOperatorCommissionAmount() > 0.0) {
					SettlementLog settlementLog = new SettlementLog();
					settlementLog.setBank(bank);
					settlementLog.setTransaction(transaction);
					settlementLog.setSettlementType(SettlementType.OPERATOR);
					settlementLog.setFromAccountNumber(sourceAccountNumber);
					settlementLog.setToAccountNumber(destinationAccountNumber);
					settlementLog.setAmount(transaction.getOperatorCommissionAmount());

					HashMap<String, String> operatorCommissionResponse = new HashMap<>();
					if (StringConstants.IS_PRODUCTION) {
						operatorCommissionResponse = customerApi.fundTransfer(sourceAccountNumber,
								destinationAccountNumber, transaction.getOperatorCommissionAmount(), bank);
					} else {
						operatorCommissionResponse.put("status", "success");
					}
					if (operatorCommissionResponse.get("status").equals("success")) {
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
					} else {
						settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
					}
					settlementLog.setResponseCode(operatorCommissionResponse.get("code"));
					settlementLogRepository.save(settlementLog);

					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getOperatorCommissionAmount(), transaction);
				}
				// channel partner
				destinationAccountNumber = bankAccountSettings.getChannelPartnerAccountNumber();
				if (transaction.getChannelPartnerCommissionAmount() > 0.0) {
					if (StringConstants.IS_PRODUCTION) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bank);
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.CHANNELPARTNER);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(destinationAccountNumber);
						settlementLog.setAmount(transaction.getChannelPartnerCommissionAmount());
						HashMap<String, String> channelPartnerCommissionResponse = customerApi.fundTransfer(
								sourceAccountNumber, destinationAccountNumber,
								transaction.getChannelPartnerCommissionAmount(), bank);
						if (channelPartnerCommissionResponse.get("status").equals("success")) {
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						} else {
							settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						}
						settlementLog.setResponseCode(channelPartnerCommissionResponse.get("code"));
						settlementLogRepository.save(settlementLog);
					}
					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getChannelPartnerCommissionAmount(), transaction);

				}
				// sourceAccountNumber =
				// bankAccountSettings.getBankParkingAccountNumber();

				// TODO NEEED TO CHECK THIS
				if (transaction.getBankCommissionAmount() > 0.0) {
					destinationAccountNumber = bankAccountSettings.getBankPoolAccountNumber();
					if (StringConstants.IS_PRODUCTION) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bank);
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.BANK);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(destinationAccountNumber);
						settlementLog.setAmount(transaction.getBankCommissionAmount());
						HashMap<String, String> bankCommissionResponse = customerApi.fundTransfer(sourceAccountNumber,
								destinationAccountNumber, transaction.getBankCommissionAmount(), bank);
						if (bankCommissionResponse.get("status").equals("success")) {
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						} else {
							settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						}
						settlementLog.setResponseCode(bankCommissionResponse.get("code"));
						settlementLogRepository.save(settlementLog);

					}
					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getTotalCommission() - (transaction.getOperatorCommissionAmount()
									+ transaction.getChannelPartnerCommissionAmount()),
							transaction);
				}

				// now to merchant
				String merchantBankAccount = bankMerchantAccountRepository
						.findMerchantAccountByBankAndMerchantStatus(bankId, merchantId, Status.Active);
				if (merchantBankAccount != null) {
					if (StringConstants.IS_PRODUCTION) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bank);
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.MERCHANT);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(merchantBankAccount);
						settlementLog.setAmount(transaction.getAmount()
								- (transaction.getBankCommissionAmount() + transaction.getOperatorCommissionAmount()
										+ transaction.getChannelPartnerCommissionAmount()));
						HashMap<String, String> merchantCommissionResponse = customerApi.fundTransfer(
								sourceAccountNumber, merchantBankAccount,
								transaction.getAmount() - (transaction.getBankCommissionAmount()
										+ transaction.getOperatorCommissionAmount()
										+ transaction.getChannelPartnerCommissionAmount()),
								bank);

						if (merchantCommissionResponse.get("status").equals("success")) {
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
							/*
							 * if(transaction.getSettlementStatus()!=null){
							 * if(!transaction.getSettlementStatus().equals(
							 * SettlementStatus.FAILURE)){
							 * transaction.setSettlementStatus(SettlementStatus.
							 * SUCCESS); transaction =
							 * transactionRepository.save(transaction); }
							 */

						} else {
							settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						}
						settlementLog.setResponseCode(merchantCommissionResponse.get("code"));
						settlementLogRepository.save(settlementLog);

					}

					createAccountPosting(sourceAccountNumber, merchantBankAccount,
							amount - transaction.getTotalCommission(), transaction);
				}
				// customerApi.fundTransfer(sourceAccountNumber,
				// destinationAccountNumber, transaction.getAmount() -
				// transaction.getBankCommissionAmount());

				// now update transaction status to complete

				transaction = transactionRepository.findOne(id);

				List<SettlementLog> settlementLogList = settlementLogRepository.findByTransaction(transaction);

				for (SettlementLog settlementLog : settlementLogList) {

					if (settlementLog.getSettlementStatus().equals(SettlementStatus.FAILURE)) {
						transaction.setSettlementStatus(SettlementStatus.FAILURE);
						transaction = transactionRepository.save(transaction);
						break;
					}
					transaction.setSettlementStatus(SettlementStatus.SUCCESS);
					transaction = transactionRepository.save(transaction);

				}

			} else if (hashResponse.get("status").equals("unknown")) {
				transactionLogApi.createTransactionLog(transaction, "Transaction Unknown: No response from Merchant",
						UserType.Bank, "" + bank.getId());
				transactionCore.setAmbiguousTransaction(id, hashResponse);
			} else {
				if (StringConstants.IS_PRODUCTION) {
					SettlementLog settlementLog = new SettlementLog();
					settlementLog.setBank(bank);
					settlementLog.setTransaction(transaction);
					settlementLog.setSettlementType(SettlementType.BANK);
					settlementLog.setFromAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
					settlementLog.setToAccountNumber(bank.getBankTranferAccount());
					settlementLog.setAmount(amount);
					HashMap<String, String> isoReverseResponse = customerApi.fundTransfer(
							bankAccountSettings.getBankParkingAccountNumber(), bank.getBankTranferAccount(), amount,
							bank);
					if (isoReverseResponse.get("status").equals("success")) {
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						transaction = transactionRepository.findOne(id);
						transaction.setSettlementStatus(SettlementStatus.SUCCESS);
						transactionRepository.save(transaction);
					} else {
						settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						transaction = transactionRepository.findOne(id);
						transaction.setSettlementStatus(SettlementStatus.FAILURE);
						transactionRepository.save(transaction);
					}
					settlementLog.setResponseCode(isoReverseResponse.get("code"));
					settlementLogRepository.save(settlementLog);
				}
				/*
				 * isoResponse = customerApi.fundTransfer(bankAccountSettings.
				 * getBankPoolAccountNumber(),bank.getBankTranferAccount(),
				 * amount, bankBranch.getBank());
				 */
				createAccountPosting(bankAccountSettings.getBankParkingAccountNumber(), bank.getBankTranferAccount(),
						amount, transaction);
				transactionLogApi.createTransactionLog(transaction, "Transaction Failed", UserType.Bank,
						"" + bank.getId());
				String txnIdentifier = transactionCore.reverseTransactionParkingToBank(id, bank, hashResponse);
				// transactionCore.reverseAccountParkingToBank(amount, bank);
				Account toAccount = accountRepository
						.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				commissionCore.transferBalanceFromPool(transaction, toAccount);
				transactionCore.reverseLedgerParkingToBank(amount, bank, txnIdentifier);
				// also reverse the requested fund towards bank
			}
		}
		hashResponse.put("bankName", bank.getName());
		hashResponse.put("serviceIcon",
				manager.getMerchantsAndServices().getMerchantService().getDocuments().getIdentifier() + "."
						+ manager.getMerchantsAndServices().getMerchantService().getDocuments().getExtention());
		/*
		 * System.out.
		 * println("***************VALUES ADDED BY AMRIT************************"
		 * );
		 * System.out.println(hashResponse.get("bankBranch")+" "+hashResponse.
		 * get("bankName")+ " "+hashResponse.get("serviceIcon"));
		 * 
		 * System.out.
		 * println("************************end values ***********************"
		 * );
		 */
		hashResponse.put("transactionIdentifier", hashRequest.get("transactionId"));
		hashResponse.put("transactionDate", "" + new Date());
		hashResponse.put("ServiceTo", transactionIdentifier);
		return hashResponse;
	}

	public HashMap<String, String> merchantPaymentBankBranch(MerchantManager manager, String transactionIdentifier,
			MerchantService service, long userAssociatedId, double amount, BankBranch bankBranch,
			HashMap<String, String> hash)
			throws ClientException, UnknownHostException, WebServiceException_Exception, JAXBException, Exception {
		HashMap<String, String> hashResponse = new HashMap<String, String>();
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		if (!manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.SMART_CELL)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.BROADLINK)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.BROADTEL)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.DISHHOME_PIN)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.NET_TV)) {
			hashRequest.put("serviceTo", transactionIdentifier);
		}
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());

		long id = transactionCore.transactionBankBranchToParking(manager, userAssociatedId, amount, bankBranch,
				hashRequest);
		// transactionCore.ledgerBankBranchToParking(amount, bankBranch, id);
		transactionCore.ledgerBankToParking(amount, bankBranch.getBank(), id);
		Transaction transaction = transactionRepository.findOne(id);
		String transactionId = transaction.getTransactionIdentifier();
		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(), Status.Active);
		HashMap<String, String> isoResponse = new HashMap<>();
		if (StringConstants.IS_PRODUCTION) {
			isoResponse = customerApi.fundTransfer(bankBranch.getBank().getBankTranferAccount(),
					bankAccountSettings.getBankParkingAccountNumber(), amount, bankBranch.getBank());
		} else {
			isoResponse.put("status", "success");
		}
		// transactionCore.accountBankBranchToParking(bankBranch, amount);

		if (isoResponse.get("status").equals("failure")) {
			hashResponse.put("status", "failure");
			hashResponse.put("Result Message", "Could not process the transaction,Please try again later");
			transactionLogApi.createTransactionLog(transaction, "Transaction Failed", UserType.BankBranch,
					"" + bankBranch.getId());
			String txnIdentifier = transactionCore.reverseTransactionParkingToBankBranch(id, bankBranch, hashResponse);
			// Account toAccount =
			// accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
			// commissionCore.transferBalanceFromPool(transaction, toAccount);
			transactionCore.reverseLedgerParkingToBankBranch(amount, bankBranch, txnIdentifier);
			return hashResponse;
		}
		createAccountPosting(bankBranch.getBank().getBankTranferAccount(),
				bankAccountSettings.getBankPoolAccountNumber(), amount, transaction);
		Account fromAccount = accountRepository.findAccountByAccountNumber(bankBranch.getBank().getAccountNumber());
		commissionCore.transferBalanceToPool(transaction, fromAccount);

		if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.WLINK_TOPUP)) {
			hashRequest.put("Utility Code", "" + hash.get("Utility Code"));
			hashRequest.put("Bill Number", "" + hash.get("Bill Number"));
			hashRequest.put("Reserve Info", "" + hash.get("Reserve Info"));
			hashRequest.put("RefStan", "" + hash.get("RefStan"));
			hashRequest.put("Commission Type", "" + hash.get("Commission Type"));
			hashRequest.put("Commission Value", "" + hash.get("Commission Value"));
			hashRequest.put("package", "true");
			hashRequest.put("packageId", hash.get("packageId"));
		} else if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.SUBISU)) {
			hashRequest.put("subisuUsername", hash.get("subisuUsername"));
		} else if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.NEA)) {

			hashRequest.put("officeCode", hash.get("officeCode"));
			hashRequest.put("Utility Code", "" + hash.get("Utility Code"));
			hashRequest.put("Bill Number", "" + hash.get("Bill Number"));
			hashRequest.put("RefStan", "" + hash.get("RefStan"));
			hashRequest.put("Commission Value", "" + hash.get("Commission Value"));
			hashRequest.put("payment", hash.get("payment"));
			hashRequest.put("customerId", hash.get("customerId"));
		}

		hashRequest.put("transactionId", transactionRepository.findOne(id).getTransactionIdentifier());

		hashResponse = serviceDectetor.detectService(service.getUniqueIdentifier(), hashRequest);
		transactionLogApi.createTransactionLog(transaction, "Transaction Pending", UserType.BankBranch,
				"" + bankBranch.getId());
		if (hashRequest == null || hashRequest.isEmpty()) {
		} else {
			if (hashResponse.get("status").equals("success")) {
				transactionLogApi.createTransactionLog(transaction, "Transaction Completed", UserType.BankBranch,
						"" + bankBranch.getId());
				long bankId = transaction.getBank().getId();
				long merchantId = transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getId();

				String destinationAccountNumber = bankAccountSettings.getOperatorAccountNumber();
				// need a change here
				String sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber(); // need
																								// to
																								// create
																								// an
																								// input
																								// field
																								// in
																								// the
																								// UI
				if (transaction.getOperatorCommissionAmount() > 0.0) {
					HashMap<String, String> operatorCommissionResponse = new HashMap<>();

					SettlementLog settlementLog = new SettlementLog();
					settlementLog.setBank(bankBranch.getBank());
					settlementLog.setTransaction(transaction);
					settlementLog.setSettlementType(SettlementType.OPERATOR);
					settlementLog.setFromAccountNumber(sourceAccountNumber);
					settlementLog.setToAccountNumber(destinationAccountNumber);
					settlementLog.setAmount(transaction.getOperatorCommissionAmount());

					if (StringConstants.IS_PRODUCTION) {
						operatorCommissionResponse = customerApi.fundTransfer(sourceAccountNumber,
								destinationAccountNumber, transaction.getOperatorCommissionAmount(),
								bankBranch.getBank());
					} else {
						operatorCommissionResponse.put("status", "success");
					}
					if (operatorCommissionResponse.get("status").equals("success")) {
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
					} else {
						settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						transaction.setSettlementStatus(SettlementStatus.FAILURE);
						transaction = transactionRepository.save(transaction);
					}
					settlementLog.setResponseCode(operatorCommissionResponse.get("code"));
					settlementLogRepository.save(settlementLog);

				}
				createAccountPosting(sourceAccountNumber, destinationAccountNumber,
						transaction.getOperatorCommissionAmount(), transaction);

				// channel partner
				destinationAccountNumber = bankAccountSettings.getChannelPartnerAccountNumber();
				if (transaction.getChannelPartnerCommissionAmount() > 0.0) {
					if (StringConstants.IS_PRODUCTION) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bankBranch.getBank());
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.CHANNELPARTNER);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(destinationAccountNumber);
						settlementLog.setAmount(transaction.getChannelPartnerCommissionAmount());
						HashMap<String, String> channelPartnerCommissionResponse = customerApi.fundTransfer(
								sourceAccountNumber, destinationAccountNumber,
								transaction.getChannelPartnerCommissionAmount(), bankBranch.getBank());

						if (channelPartnerCommissionResponse.get("status").equals("success")) {
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						} else {
							settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
							transaction.setSettlementStatus(SettlementStatus.FAILURE);
							transaction = transactionRepository.save(transaction);
						}
						settlementLog.setResponseCode(channelPartnerCommissionResponse.get("code"));
						settlementLogRepository.save(settlementLog);

					}

					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getChannelPartnerCommissionAmount(), transaction);

				}
				// sourceAccountNumber =
				// bankAccountSettings.getBankParkingAccountNumber();

				// TODO NEEED TO CHECK THIS
				if (transaction.getBankCommissionAmount() > 0.0) {
					if (StringConstants.IS_PRODUCTION) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bankBranch.getBank());
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.BANK);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(destinationAccountNumber);
						settlementLog
								.setAmount(transaction.getTotalCommission() - (transaction.getOperatorCommissionAmount()
										+ transaction.getChannelPartnerCommissionAmount()));
						String bankCommissionResponse = destinationAccountNumber = bankAccountSettings
								.getBankPoolAccountNumber();
						customerApi
								.fundTransfer(sourceAccountNumber, destinationAccountNumber,
										transaction.getTotalCommission() - (transaction.getOperatorCommissionAmount()
												+ transaction.getChannelPartnerCommissionAmount()),
										bankBranch.getBank());
						if (bankCommissionResponse.equals("success")) {
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						} else {
							settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
							transaction.setSettlementStatus(SettlementStatus.FAILURE);
							transaction = transactionRepository.save(transaction);
						}
						settlementLogRepository.save(settlementLog);

					}
					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getTotalCommission() - (transaction.getOperatorCommissionAmount()
									+ transaction.getChannelPartnerCommissionAmount()),
							transaction);
				}
				// now to merchant
				String merchantBankAccount = bankMerchantAccountRepository
						.findMerchantAccountByBankAndMerchantStatus(bankId, merchantId, Status.Active);
				if (merchantBankAccount != null) {
					if (StringConstants.IS_PRODUCTION) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bankBranch.getBank());
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.MERCHANT);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(merchantBankAccount);
						settlementLog.setAmount(transaction.getAmount() - transaction.getTotalCommission());
						HashMap<String, String> merchantCommissionResponse = customerApi.fundTransfer(
								sourceAccountNumber, merchantBankAccount,
								transaction.getAmount() - transaction.getTotalCommission(), bankBranch.getBank());

						if (merchantCommissionResponse.get("status").equals("success")) {
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
							if (transaction.getSettlementStatus() != null) {
								if (!transaction.getSettlementStatus().equals(SettlementStatus.FAILURE)) {
									transaction.setSettlementStatus(SettlementStatus.SUCCESS);
									transaction = transactionRepository.save(transaction);
								}
							}
						} else {
							settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
							transaction.setSettlementStatus(SettlementStatus.FAILURE);
							transaction = transactionRepository.save(transaction);
						}
						settlementLog.setResponseCode(merchantCommissionResponse.get("code"));
						settlementLogRepository.save(settlementLog);

					}
					createAccountPosting(sourceAccountNumber, merchantBankAccount,
							amount - transaction.getTotalCommission(), transaction);
				}

				transactionCore.ledgerParkingToMerchant(amount - transaction.getTotalCommission(), manager,
						transactionId);
				// transactionCore.accountParkingToService(amount, service);
				Account toAccount = accountRepository
						.findAccountByAccountNumber(manager.getMerchantsAndServices().getMerchant().getAccountNumber());
				commissionCore.transferBalanceFromPoolToMerchant(transaction, toAccount,
						amount - transaction.getTotalCommission());

				fromAccount = accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				// commissionCore.transferBalanceToPool(transaction,
				// fromAccount);
				commissionCore.allocateCommissions(transaction);

			} else if (hashResponse.equals("unknown")) {
				transactionLogApi.createTransactionLog(transaction, "Transaction Unknown: No Response From Merchant",
						UserType.BankBranch, "" + bankBranch.getId());
				transactionCore.setAmbiguousTransaction(id, hashResponse);
			} else {
				// BankAccountSettings bankAccountSettings =
				// bankAccountSettingsRepository.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(),
				// Status.Active);
				if (StringConstants.IS_PRODUCTION) {
					SettlementLog settlementLog = new SettlementLog();
					settlementLog.setBank(bankBranch.getBank());
					settlementLog.setTransaction(transaction);
					settlementLog.setSettlementType(SettlementType.BANK);
					settlementLog.setFromAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
					settlementLog.setToAccountNumber(bankBranch.getBank().getBankTranferAccount());
					settlementLog.setAmount(amount);
					HashMap<String, String> isoReverseResponse = customerApi.fundTransfer(
							bankAccountSettings.getBankParkingAccountNumber(),
							bankBranch.getBank().getBankTranferAccount(), amount, bankBranch.getBank());
					if (isoReverseResponse.get("status").equals("success")) {
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						transaction.setSettlementStatus(SettlementStatus.SUCCESS);
						transactionRepository.save(transaction);
					} else {
						settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						transaction.setSettlementStatus(SettlementStatus.FAILURE);
						transactionRepository.save(transaction);
					}
					settlementLog.setResponseCode(isoReverseResponse.get("code"));
					settlementLogRepository.save(settlementLog);
				}
				// isoResponse =
				// customerApi.fundTransfer(bankAccountSettings.getBankPoolAccountNumber(),bankBranch.getBank().getBankTranferAccount(),
				// amount, bankBranch.getBank());
				createAccountPosting(bankAccountSettings.getBankParkingAccountNumber(),
						bankBranch.getBank().getBankTranferAccount(), amount, transaction);
				transactionLogApi.createTransactionLog(transaction, "Transaction Failed", UserType.BankBranch,
						"" + bankBranch.getId());
				String txnIdentifier = transactionCore.reverseTransactionParkingToBankBranch(id, bankBranch,
						hashResponse);
				// transactionCore.reverseAccountParkingToBankBranch(amount,
				// bankBranch);
				Account toAccount = accountRepository
						.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				commissionCore.transferBalanceFromPool(transaction, toAccount);
				transactionCore.reverseLedgerParkingToBankBranch(amount, bankBranch, txnIdentifier);
			}
		}
		hashResponse.put("transactionIdentifier", hashRequest.get("transactionId"));
		hashResponse.put("transactionDate", "" + new Date());
		hashResponse.put("ServiceTo", transactionIdentifier);
		// added by amrit
		hashResponse.put("bankBranch", bankBranch.getName());
		hashResponse.put("bankName", bankBranch.getBank().getName());
		hashResponse.put("serviceIcon",
				manager.getMerchantsAndServices().getMerchantService().getDocuments().getIdentifier() + "."
						+ manager.getMerchantsAndServices().getMerchantService().getDocuments().getExtention());
		return hashResponse;
	}

	private HashMap<String, String> merchantPaymentCustomer(MerchantManager manager, String requestData,
			MerchantService service, Long userAssociateId, double amount, BankBranch bankBranch,
			HashMap<String, String> hash, CustomerBankAccount customerBankAccount)
			throws ClientException, UnknownHostException, WebServiceException_Exception, JAXBException, Exception {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		HashMap<String, String> hashResponse = new HashMap<String, String>();
		if (!manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.SMART_CELL)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.BROADLINK)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.BROADTEL)
				|| !manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.DISHHOME_PIN)) {
			hashRequest.put("serviceTo", requestData);
		}
		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		Customer sourceCustomer = customerBankAccount.getCustomer();

		long id = transactionCore.customerToParkingTransaction(manager, sourceCustomer.getId(), amount,
				customerBankAccount.getBranch(), hashRequest, customerBankAccount);
		Transaction transaction = transactionRepository.findOne(id);
		transactionCore.ledgerBankToParking(amount, customerBankAccount.getBranch().getBank(), id);

		// creating settlement log to check commission distribution in pdx
		// client

		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(), Status.Active);
		String remarksOne = transaction.getTransactionIdentifier();
		String desc1 = service.getService();
		String remarksTwo = requestData;
		HashMap<String, String> isoResponse = new HashMap<>();
		if (StringConstants.IS_PRODUCTION) {

			isoResponse = isoCallForParkingForCustomer(customerBankAccount.getBranch().getBank(), amount,
					customerBankAccount.getAccountNumber(), bankAccountSettings.getBankParkingAccountNumber(),
					remarksOne, remarksTwo, desc1);
		} else {
			isoResponse.put("status", "success");
			// isoResponse.put("code", "99");
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
					hashResponse.put("Result Message", "Could not process the transaction,Please try again later");
				}
			} catch (Exception e) {
				// e.printStackTrace();
				if (isoResponse.get("code") != null)
					hashResponse.put("isoCode", isoResponse.get("code"));
				hashResponse.put("Result Message", "Could not process the transaction,Please try again later");
			}
			// hashResponse.put("Result Message", "Could not process the
			// transaction,Please try again later");
			remarksTwo = remarksTwo + " Refund";
			transactionLogApi.createTransactionLog(transaction, "Transaction Processing Failed", UserType.Customer,
					customerBankAccount.getCustomer().getUniqueId());
			String txnIdentifier = transactionCore.reverseTransactionParkingToCustomer(id, customerBankAccount,
					hashResponse);
			transactionCore.reverseLedgerParkingToBank(amount, bankBranch.getBank(), txnIdentifier);
			createAccountPosting(bankAccountSettings.getBankParkingAccountNumber(),
					customerBankAccount.getAccountNumber(), amount, transaction);
			return hashResponse;
		}
		Account fromAccount = accountRepository.findAccountByAccountNumber(bankBranch.getBank().getAccountNumber());
		commissionCore.transferBalanceToPool(transaction, fromAccount);
		createAccountPosting(customerBankAccount.getAccountNumber(), bankAccountSettings.getBankParkingAccountNumber(),
				amount, transaction);

		if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.WLINK_TOPUP)
				|| manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
						.equals(StringConstants.VIANET)) {
			hashRequest.put("Utility Code", "" + hash.get("Utility Code"));
			hashRequest.put("Bill Number", "" + hash.get("Bill Number"));
			hashRequest.put("Reserve Info", "" + hash.get("Reserve Info"));
			hashRequest.put("RefStan", "" + hash.get("RefStan"));
			hashRequest.put("Commission Type", "" + hash.get("Commission Type"));
			hashRequest.put("Commission Value", "" + hash.get("Commission Value"));

			try {
				hash.get("packageId");
				hashRequest.put("package", "true");
				hashRequest.put("packageId", hash.get("packageId"));
			} catch (NullPointerException e) {
				// TODO: handle exception
			}

		} else if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.SUBISU)) {
			/* hashRequest.put("username", hash.get("username")); */
			hashRequest.put("subisuUsername", hash.get("subisuUsername"));
		} else if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.NEA)) {

			hashRequest.put("officeCode", hash.get("officeCode"));
			hashRequest.put("Utility Code", "" + hash.get("Utility Code"));
			hashRequest.put("Bill Number", "" + hash.get("Bill Number"));
			hashRequest.put("RefStan", "" + hash.get("RefStan"));
			hashRequest.put("Commission Value", "" + hash.get("Commission Value"));
			hashRequest.put("payment", hash.get("payment"));
			hashRequest.put("customerId", hash.get("customerId"));
		} else if (manager.getMerchantsAndServices().getMerchantService().getUniqueIdentifier()
				.equals(StringConstants.KHANE_PANI)) {
			hashRequest.put("counter", hash.get("counter"));
		}

		// transactionCore.ledgerCustomerToParking(amount, sourceCustomer);
		// transactionCore.accountCustomerToParking(sourceCustomer, amount);

		hashRequest.put("transactionId", transaction.getTransactionIdentifier());

		hashResponse = serviceDectetor.detectService(service.getUniqueIdentifier(), hashRequest);

		transactionLogApi.createTransactionLog(transaction, "Transaction Pending", UserType.Customer,
				sourceCustomer.getUniqueId());
		if (hashResponse == null || hashResponse.isEmpty()) {
		} else {
			if (hashResponse.get("status").equals("success")) {

				String txnIdentifier = transactionCore.transactionParkingToMerchant(id, manager, hashResponse);
				transactionCore.ledgerParkingToMerchant(amount - transaction.getTotalCommission(), manager,
						txnIdentifier);
				// transactionCore.accountParkingToService(amount, service);
				// commissionCore.updateBalanceToBank(transaction);
				Account toAccount = accountRepository.findAccountByAccountNumber(
						transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getAccountNumber());

				commissionCore.transferBalanceFromPoolToMerchant(transaction, toAccount,
						amount - transaction.getTotalCommission());
				commissionCore.allocateCommissions(transaction);

				hashResponse.put("transactionIdentifier", transaction.getTransactionIdentifier());

				transactionLogApi.createTransactionLog(transaction, "Transaction Completed", UserType.Customer,
						sourceCustomer.getUniqueId());
				long bankId = transaction.getBank().getId();
				long merchantId = transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getId();

				String destinationAccountNumber = bankAccountSettings.getOperatorAccountNumber();
				String sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber();
				String remarksCommission = "Comm " + remarksTwo;
				// Operator Commission
				if (transaction.getOperatorCommissionAmount() != null) {
					if (transaction.getOperatorCommissionAmount() > 0.0) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bankBranch.getBank());
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.OPERATOR);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(destinationAccountNumber);
						settlementLog.setAmount(transaction.getOperatorCommissionAmount());
						/*
						 * HashMap<String, String> operatorCommissionResponse =
						 * new HashMap<>(); if (StringConstants.IS_PRODUCTION) {
						 * operatorCommissionResponse =
						 * customerApi.fundTransfer(sourceAccountNumber,
						 * destinationAccountNumber,
						 * transaction.getOperatorCommissionAmount(),
						 * bankBranch.getBank(), remarksOne, remarksTwo, desc1);
						 * } else { operatorCommissionResponse.put("status",
						 * "success"); } if
						 * (operatorCommissionResponse.get("status").equals(
						 * "success")) {
						 * settlementLog.setSettlementStatus(SettlementStatus.
						 * SUCCESS); } else {
						 * settlementLog.setSettlementStatus(SettlementStatus.
						 * FAILURE); } settlementLog.setResponseCode(
						 * channelPartnerCommissionResponse.get("code"));
						 */
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						settlementLog.setResponseCode("no_settlement");
						settlementLogRepository.save(settlementLog);
					}
					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getOperatorCommissionAmount(), transaction);

				}
				// channel partner
				if (transaction.getChannelPartnerCommissionAmount() != null) {
					if (transaction.getChannelPartnerCommissionAmount() > 0.0) {
						destinationAccountNumber = bankAccountSettings.getChannelPartnerAccountNumber();
						if (StringConstants.IS_PRODUCTION) {
							SettlementLog settlementLog = new SettlementLog();
							settlementLog.setBank(bankBranch.getBank());
							settlementLog.setTransaction(transaction);
							settlementLog.setSettlementType(SettlementType.CHANNELPARTNER);
							settlementLog.setFromAccountNumber(sourceAccountNumber);
							settlementLog.setToAccountNumber(destinationAccountNumber);
							settlementLog.setAmount(transaction.getChannelPartnerCommissionAmount());
							/*
							 * HashMap<String, String>
							 * channelPartnerCommissionResponse =
							 * customerApi.fundTransfer( sourceAccountNumber,
							 * destinationAccountNumber,
							 * transaction.getChannelPartnerCommissionAmount(),
							 * bankBranch.getBank(), remarksOne, remarksTwo,
							 * desc1); if
							 * (channelPartnerCommissionResponse.get("status").
							 * equals("success")) {
							 * settlementLog.setSettlementStatus(
							 * SettlementStatus.SUCCESS); } else {
							 * settlementLog.setSettlementStatus(
							 * SettlementStatus.FAILURE); }
							 * settlementLog.setResponseCode(
							 * channelPartnerCommissionResponse.get("code"));
							 */
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
							settlementLog.setResponseCode("no_settlement");
							settlementLogRepository.save(settlementLog);
						}
						createAccountPosting(sourceAccountNumber, destinationAccountNumber,
								transaction.getChannelPartnerCommissionAmount(), transaction);
					}
				}
				// now to bank

				if (transaction.getCashBack() != null && transaction.getCashBack() > 0) {
					destinationAccountNumber = customerBankAccount.getAccountNumber();
					if (StringConstants.IS_PRODUCTION) {

						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bankBranch.getBank());
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.CUSTOMER_CASHBACK);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(destinationAccountNumber);
						settlementLog.setAmount(transaction.getCashBack());
						HashMap<String, String> cashBackResponse = customerApi.fundTransfer(sourceAccountNumber,
								destinationAccountNumber, transaction.getCashBack(), bankBranch.getBank(), remarksOne,
								remarksTwo, desc1);
						if (cashBackResponse.get("status").equals("success")) {
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						} else {
							settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						}
						settlementLog.setResponseCode(cashBackResponse.get("code"));
						settlementLogRepository.save(settlementLog);
					}
					createAccountPosting(sourceAccountNumber, destinationAccountNumber, transaction.getCashBack(),
							transaction);
				}

				if (transaction.getBankCommissionAmount() != null) {
					if (transaction.getBankCommissionAmount() > 0.0) {
						destinationAccountNumber = bankAccountSettings.getBankPoolAccountNumber();
						sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber();
						if (StringConstants.IS_PRODUCTION) {
							SettlementLog settlementLog = new SettlementLog();
							settlementLog.setBank(bankBranch.getBank());
							settlementLog.setTransaction(transaction);
							settlementLog.setSettlementType(SettlementType.BANK);
							settlementLog.setFromAccountNumber(sourceAccountNumber);
							settlementLog.setToAccountNumber(destinationAccountNumber);
							settlementLog.setAmount(transaction.getBankCommissionAmount());
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
							settlementLog.setResponseCode("no_settlement");
							settlementLogRepository.save(settlementLog);
						}

						createAccountPosting(sourceAccountNumber, destinationAccountNumber,
								transaction.getTotalCommission() - (transaction.getOperatorCommissionAmount()
										+ transaction.getChannelPartnerCommissionAmount()),
								transaction);
					}
				}

				// now to merchant
				String merchantBankAccount = bankMerchantAccountRepository
						.findMerchantAccountByBankAndMerchantStatus(bankId, merchantId, Status.Active);
				if (merchantBankAccount != null) {
					if (StringConstants.IS_PRODUCTION) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bankBranch.getBank());
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.MERCHANT);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(merchantBankAccount);
						settlementLog.setAmount(transaction.getAmount() - (transaction.getOperatorCommissionAmount()
								+ transaction.getChannelPartnerCommissionAmount()
								+ transaction.getBankCommissionAmount() - transaction.getCashBack()));
						settlementLog.setTransferAmount(transaction.getAmount()-transaction.getCashBack());
						HashMap<String, String> merchantCommissionResponse = null;
						if (transaction.getCashBack() != null && transaction.getCashBack() > 0) {
							merchantCommissionResponse = customerApi.fundTransfer(
									sourceAccountNumber, merchantBankAccount,
									transaction.getAmount() - transaction.getCashBack(), bankBranch.getBank(),
									remarksOne, remarksTwo, desc1);
						}else{
							merchantCommissionResponse = customerApi.fundTransfer(
									sourceAccountNumber, merchantBankAccount,
									transaction.getAmount(), bankBranch.getBank(),
									remarksOne, remarksTwo, desc1);
						}
						if (merchantCommissionResponse.get("status").equals("success")) {
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						} else {
							settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						}
						settlementLog.setResponseCode(merchantCommissionResponse.get("code"));
						settlementLogRepository.save(settlementLog);
					}

					createAccountPosting(sourceAccountNumber, merchantBankAccount,
							transaction.getAmount() - transaction.getTotalCommission(), transaction);
				}

				transaction = transactionRepository.findOne(id);
				List<SettlementLog> settlementLogList = settlementLogRepository.findByTransaction(transaction);
				for (SettlementLog settlementLog : settlementLogList) {
					if (settlementLog.getSettlementStatus().equals(SettlementStatus.FAILURE)) {
						transaction.setSettlementStatus(SettlementStatus.FAILURE);
						transaction = transactionRepository.save(transaction);
						break;
					}
					transaction.setSettlementStatus(SettlementStatus.SUCCESS);
					transaction = transactionRepository.save(transaction);
				}

			} else if (hashResponse.get("status").equals("unknown")) {
				hashResponse.put("transactionIdentifier", transaction.getTransactionIdentifier());
				transactionLogApi.createTransactionLog(transaction, "Transaction Unknown: No Response from Merchant",
						UserType.Customer, sourceCustomer.getUniqueId());
				transactionCore.setAmbiguousTransaction(id, hashResponse);
			} else {
				remarksTwo = remarksTwo + " Refund";
				if (StringConstants.IS_PRODUCTION) {
					SettlementLog settlementLog = new SettlementLog();
					settlementLog.setBank(bankBranch.getBank());
					settlementLog.setTransaction(transaction);
					settlementLog.setSettlementType(SettlementType.CUSTOMER);
					settlementLog.setFromAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
					settlementLog.setToAccountNumber(customerBankAccount.getAccountNumber());
					settlementLog.setAmount(amount);
					HashMap<String, String> isoReverseResponse = isoCallForParkingForCustomer(
							customerBankAccount.getBranch().getBank(), amount,
							bankAccountSettings.getBankParkingAccountNumber(), customerBankAccount.getAccountNumber(),
							remarksOne, remarksTwo, desc1);
					if (isoReverseResponse.get("status").equals("success")) {
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						transaction = transactionRepository.findOne(id);
						transaction.setSettlementStatus(SettlementStatus.SUCCESS);
						transactionRepository.save(transaction);
					} else {
						settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						transaction = transactionRepository.findOne(id);
						transaction.setSettlementStatus(SettlementStatus.FAILURE);
						transactionRepository.save(transaction);
					}
					settlementLog.setResponseCode(isoReverseResponse.get("code"));
					settlementLogRepository.save(settlementLog);
				}
				transactionLogApi.createTransactionLog(transaction, "Transaction Failed", UserType.Customer,
						sourceCustomer.getUniqueId());

				String txnIdentifier = transactionCore.reverseTransactionParkingToCustomer(id, customerBankAccount,
						hashResponse);
				transactionCore.reverseLedgerParkingToBank(amount, bankBranch.getBank(), txnIdentifier);
				Account toAccount = accountRepository
						.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				commissionCore.transferBalanceFromPool(transaction, toAccount);

				createAccountPosting(bankAccountSettings.getBankParkingAccountNumber(),
						customerBankAccount.getAccountNumber(), amount, transaction);

				// transactionCore.reverseAccountParkingToCustomer(amount,
				// customerBankAccount.getCustomer());
				// transactionCore.reverseLedgerParkingToCustomer(amount,
				// customerBankAccount.getCustomer(),
				// transaction.getTransactionIdentifier());

			}
		}

		// now call the iso for customer deducation of money from Bank
		return hashResponse;
	}

	private HashMap<String, Object> airlinesPayment(MerchantManager manager, String requestData,
			MerchantService service, Long userAssociateId, double amount, BankBranch bankBranch,
			HashMap<String, Object> hash, CustomerBankAccount customerBankAccount) throws ClientException {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		HashMap<String, Object> hashResponse = new HashMap<String, Object>();
		HashMap<String, String> convertedHashRequest = new HashMap<>();

		hashRequest.put("amount", "" + amount);
		hashRequest.put("serviceId", service.getUniqueIdentifier());
		Customer sourceCustomer = customerBankAccount.getCustomer();

		PassengerTotalDto pessangerDto = (PassengerTotalDto) hash.get("pessangerinfo");
		hash.remove("pessangerinfo");
		for (Map.Entry<String, Object> entry : hash.entrySet()) {

			if (entry.getValue() instanceof String) {
				convertedHashRequest.put(entry.getKey(), (String) entry.getValue());
			}
		}

		long id = transactionCore.customerToParkingTransaction(manager, sourceCustomer.getId(), amount,
				customerBankAccount.getBranch(), hashRequest, customerBankAccount);
		Transaction transaction = transactionRepository.findOne(id);
		transactionCore.ledgerBankToParking(amount, customerBankAccount.getBranch().getBank(), id);

		// creating settlement log to check commission distribution in pdx
		// client

		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(), Status.Active);
		String remarksOne = transaction.getTransactionIdentifier();
		String desc1 = service.getService();
		String remarksTwo = requestData;
		HashMap<String, String> isoResponse = new HashMap<>();
		HashMap<String, String> convertedHashResponse = new HashMap<>();
		if (StringConstants.IS_PRODUCTION) {

			isoResponse = isoCallForParkingForCustomer(customerBankAccount.getBranch().getBank(), amount,
					customerBankAccount.getAccountNumber(), bankAccountSettings.getBankParkingAccountNumber(),
					remarksOne, remarksTwo, desc1);

		} else {
			isoResponse.put("status", "success");
			// isoResponse.put("code", "99");
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
					hashResponse.put("Result Message", "Could not process the transaction,Please try again later");
				}
			} catch (Exception e) {
				// e.printStackTrace();
				if (isoResponse.get("code") != null)
					hashResponse.put("isoCode", isoResponse.get("code"));
				hashResponse.put("Result Message", "Could not process the transaction,Please try again later");
			}

			for (Map.Entry<String, Object> entry : hashResponse.entrySet()) {
				if (entry.getValue() instanceof String) {
					convertedHashResponse.put(entry.getKey(), (String) entry.getValue());
				}
			}

			// hashResponse.put("Result Message", "Could not process the
			// transaction,Please try again later");
			remarksTwo = remarksTwo + " Refund";
			transactionLogApi.createTransactionLog(transaction, "Transaction Processing Failed", UserType.Customer,
					customerBankAccount.getCustomer().getUniqueId());
			transactionCore.reverseTransactionParkingToCustomer(id, customerBankAccount, convertedHashResponse);
			createAccountPosting(bankAccountSettings.getBankParkingAccountNumber(),
					customerBankAccount.getAccountNumber(), amount, transaction);
			return hashResponse;
		}
		Account fromAccount = accountRepository.findAccountByAccountNumber(bankBranch.getBank().getAccountNumber());
		commissionCore.transferBalanceToPool(transaction, fromAccount);
		createAccountPosting(customerBankAccount.getAccountNumber(), bankAccountSettings.getBankParkingAccountNumber(),
				amount, transaction);

		// transactionCore.ledgerCustomerToParking(amount, sourceCustomer);
		// transactionCore.accountCustomerToParking(sourceCustomer, amount);

		hashRequest.put("transactionId", transaction.getTransactionIdentifier());

		hashResponse = serviceDectetor.detectService(service.getUniqueIdentifier(), pessangerDto);

		transactionLogApi.createTransactionLog(transaction, "Transaction Pending", UserType.Customer,
				sourceCustomer.getUniqueId());
		if (hashResponse == null || hashResponse.isEmpty()) {
		} else {
			if (hashResponse.get("status").equals("success")) {

				HashMap<String, Object> airlinesResponse = (HashMap<String, Object>) hashResponse
						.get("airlinesResponse");
				for (Map.Entry<String, Object> entry : airlinesResponse.entrySet()) {
					if (entry.getValue() instanceof String) {
						convertedHashResponse.put(entry.getKey(), (String) entry.getValue());
					}
				}

				String txnIdentifier = transactionCore.transactionParkingToMerchant(id, manager, convertedHashResponse);
				transactionCore.ledgerParkingToMerchant(amount, manager, txnIdentifier);
				// transactionCore.accountParkingToService(amount, service);
				// commissionCore.updateBalanceToBank(transaction);
				Account toAccount = accountRepository.findAccountByAccountNumber(
						transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getAccountNumber());

				commissionCore.transferBalanceFromPoolToMerchant(transaction, toAccount,
						amount - transaction.getTotalCommission());
				commissionCore.allocateCommissions(transaction);

				hashResponse.put("transactionIdentifier", transaction.getTransactionIdentifier());

				transactionLogApi.createTransactionLog(transaction, "Transaction Completed", UserType.Customer,
						sourceCustomer.getUniqueId());

				IssueTicketRes issueTicketResponse = (IssueTicketRes) hashResponse.get("issueTicket");
				for (Passenger passengerDetail : issueTicketResponse.getPassenger()) {
					AirliesResponseDetail airlinesDetail = new AirliesResponseDetail();
					try {
						PropertyUtils.copyProperties(airlinesDetail, passengerDetail);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					airlinesDetail.setTransactionIdentifier(transaction.getTransactionIdentifier());
					airlinesDetailRepository.save(airlinesDetail);
				}

				long bankId = transaction.getBank().getId();
				long merchantId = transaction.getMerchantManager().getMerchantsAndServices().getMerchant().getId();

				String destinationAccountNumber = bankAccountSettings.getOperatorAccountNumber();
				String sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber();
				String remarksCommission = "Comm " + remarksTwo;
				if (transaction.getOperatorCommissionAmount() != null) {
					if (transaction.getOperatorCommissionAmount() > 0.0) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bankBranch.getBank());
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.OPERATOR);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(destinationAccountNumber);
						settlementLog.setAmount(transaction.getOperatorCommissionAmount());

						/*
						 * HashMap<String, String> operatorCommissionResponse =
						 * new HashMap<>(); if (StringConstants.IS_PRODUCTION) {
						 * operatorCommissionResponse =
						 * customerApi.fundTransfer(sourceAccountNumber,
						 * destinationAccountNumber,
						 * transaction.getOperatorCommissionAmount(),
						 * bankBranch.getBank(), remarksOne, remarksTwo, desc1);
						 * } else { operatorCommissionResponse.put("status",
						 * "success"); } if
						 * (operatorCommissionResponse.get("status").equals(
						 * "success")) {
						 * settlementLog.setSettlementStatus(SettlementStatus.
						 * SUCCESS); } else {
						 * settlementLog.setSettlementStatus(SettlementStatus.
						 * FAILURE); } settlementLog.setResponseCode(
						 * operatorCommissionResponse.get("code"));
						 */
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						settlementLog.setResponseCode("no_settlement");
						settlementLogRepository.save(settlementLog);
					}
					createAccountPosting(sourceAccountNumber, destinationAccountNumber,
							transaction.getOperatorCommissionAmount(), transaction);

				}
				// channel partner
				if (transaction.getChannelPartnerCommissionAmount() != null) {
					if (transaction.getChannelPartnerCommissionAmount() > 0.0) {
						destinationAccountNumber = bankAccountSettings.getChannelPartnerAccountNumber();
						if (StringConstants.IS_PRODUCTION) {
							SettlementLog settlementLog = new SettlementLog();
							settlementLog.setBank(bankBranch.getBank());
							settlementLog.setTransaction(transaction);
							settlementLog.setSettlementType(SettlementType.CHANNELPARTNER);
							settlementLog.setFromAccountNumber(sourceAccountNumber);
							settlementLog.setToAccountNumber(destinationAccountNumber);
							settlementLog.setAmount(transaction.getChannelPartnerCommissionAmount());
							/*
							 * HashMap<String, String>
							 * channelPartnerCommissionResponse =
							 * customerApi.fundTransfer( sourceAccountNumber,
							 * destinationAccountNumber,
							 * transaction.getChannelPartnerCommissionAmount(),
							 * bankBranch.getBank(), remarksOne, remarksTwo,
							 * desc1); if
							 * (channelPartnerCommissionResponse.get("status").
							 * equals("success")) {
							 * settlementLog.setSettlementStatus(
							 * SettlementStatus.SUCCESS); } else {
							 * settlementLog.setSettlementStatus(
							 * SettlementStatus.FAILURE); }
							 * settlementLog.setResponseCode(
							 * channelPartnerCommissionResponse.get("code"));
							 */
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
							settlementLog.setResponseCode("no_settlement");
							settlementLogRepository.save(settlementLog);
						}
						createAccountPosting(sourceAccountNumber, destinationAccountNumber,
								transaction.getChannelPartnerCommissionAmount(), transaction);
					}
				}
				// now to bank
				if (transaction.getBankCommissionAmount() != null) {
					if (transaction.getBankCommissionAmount() > 0.0) {
						destinationAccountNumber = bankAccountSettings.getBankPoolAccountNumber();
						sourceAccountNumber = bankAccountSettings.getBankParkingAccountNumber();
						if (StringConstants.IS_PRODUCTION) {
							SettlementLog settlementLog = new SettlementLog();
							settlementLog.setBank(bankBranch.getBank());
							settlementLog.setTransaction(transaction);
							settlementLog.setSettlementType(SettlementType.BANK);
							settlementLog.setFromAccountNumber(sourceAccountNumber);
							settlementLog.setToAccountNumber(destinationAccountNumber);
							settlementLog.setAmount(transaction.getBankCommissionAmount());
							/*
							 * HashMap<String, String> bankCommissionResponse =
							 * customerApi.fundTransfer( sourceAccountNumber,
							 * destinationAccountNumber,
							 * transaction.getBankCommissionAmount(),
							 * bankBranch.getBank(), remarksOne, remarksTwo,
							 * desc1);
							 * 
							 * if (bankCommissionResponse.get("status").equals(
							 * "success")) { settlementLog.setSettlementStatus(
							 * SettlementStatus.SUCCESS); } else {
							 * settlementLog.setSettlementStatus(
							 * SettlementStatus.FAILURE); }
							 * settlementLog.setResponseCode(
							 * bankCommissionResponse.get("code"));
							 */
							settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
							settlementLog.setResponseCode("no_settlement");
							settlementLogRepository.save(settlementLog);
						}

						createAccountPosting(sourceAccountNumber, destinationAccountNumber,
								transaction.getTotalCommission() - (transaction.getOperatorCommissionAmount()
										+ transaction.getChannelPartnerCommissionAmount()),
								transaction);
					}
				}

				// now to merchant
				String merchantBankAccount = bankMerchantAccountRepository
						.findMerchantAccountByBankAndMerchantStatus(bankId, merchantId, Status.Active);
				if (merchantBankAccount != null) {
					if (StringConstants.IS_PRODUCTION) {
						SettlementLog settlementLog = new SettlementLog();
						settlementLog.setBank(bankBranch.getBank());
						settlementLog.setTransaction(transaction);
						settlementLog.setSettlementType(SettlementType.MERCHANT);
						settlementLog.setFromAccountNumber(sourceAccountNumber);
						settlementLog.setToAccountNumber(merchantBankAccount);
						settlementLog.setAmount(transaction.getAmount() - (transaction.getOperatorCommissionAmount()
								+ transaction.getChannelPartnerCommissionAmount()
								+ transaction.getBankCommissionAmount()));
						/*
						 * HashMap<String, String> merchantCommissionResponse =
						 * customerApi.fundTransfer( sourceAccountNumber,
						 * merchantBankAccount, transaction.getAmount() -
						 * (transaction.getOperatorCommissionAmount() +
						 * transaction.getChannelPartnerCommissionAmount() +
						 * transaction.getBankCommissionAmount()),
						 * bankBranch.getBank(), remarksOne, remarksTwo, desc1);
						 * // transactionCore.ledgerParkingToMerchant(amount -
						 * // transaction.getTotalCommission(), manager, //
						 * requestData); if
						 * (merchantCommissionResponse.get("status").equals(
						 * "success")) {
						 * settlementLog.setSettlementStatus(SettlementStatus.
						 * SUCCESS); } else {
						 * settlementLog.setSettlementStatus(SettlementStatus.
						 * FAILURE); } settlementLog.setResponseCode(
						 * merchantCommissionResponse.get("code"));
						 */
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						settlementLog.setResponseCode("no_settlement");
						settlementLogRepository.save(settlementLog);
					}

					createAccountPosting(sourceAccountNumber, merchantBankAccount,
							transaction.getAmount() - transaction.getTotalCommission(), transaction);
				}

				transaction = transactionRepository.findOne(id);

				/*
				 * List<SettlementLog> settlementLogList =
				 * settlementLogRepository.findByTransaction(transaction); for
				 * (SettlementLog settlementLog : settlementLogList) { if
				 * (settlementLog.getSettlementStatus().equals(SettlementStatus.
				 * FAILURE)) {
				 * transaction.setSettlementStatus(SettlementStatus.FAILURE);
				 * transaction = transactionRepository.save(transaction); break;
				 * } transaction.setSettlementStatus(SettlementStatus.SUCCESS);
				 * transaction = transactionRepository.save(transaction); }
				 */

				transaction.setSettlementStatus(SettlementStatus.SUCCESS);
				transaction = transactionRepository.save(transaction);
				try {
					hashResponse.put("downloadUrl", airlinesResponse.get("ticketUrl"));
				} catch (NullPointerException e) {

				}

			} else if (hashResponse.get("status").equals("unknown")) {

				for (Map.Entry<String, Object> entry : hashResponse.entrySet()) {
					if (entry.getValue() instanceof String) {
						convertedHashResponse.put(entry.getKey(), (String) entry.getValue());
					}
				}

				hashResponse.put("transactionIdentifier", transaction.getTransactionIdentifier());
				transactionLogApi.createTransactionLog(transaction, "Transaction Unknown: No Response from Merchant",
						UserType.Customer, sourceCustomer.getUniqueId());
				transactionCore.setAmbiguousTransaction(id, convertedHashResponse);
			} else {

				remarksTwo = remarksTwo + " Refund";
				if (StringConstants.IS_PRODUCTION) {
					SettlementLog settlementLog = new SettlementLog();
					settlementLog.setBank(bankBranch.getBank());
					settlementLog.setTransaction(transaction);
					settlementLog.setSettlementType(SettlementType.CUSTOMER);
					settlementLog.setFromAccountNumber(bankAccountSettings.getBankParkingAccountNumber());
					settlementLog.setToAccountNumber(customerBankAccount.getAccountNumber());
					settlementLog.setAmount(amount);
					HashMap<String, String> isoReverseResponse = isoCallForParkingForCustomer(
							customerBankAccount.getBranch().getBank(), amount,
							bankAccountSettings.getBankParkingAccountNumber(), customerBankAccount.getAccountNumber(),
							remarksOne, remarksTwo, desc1);
					if (isoReverseResponse.get("status").equals("success")) {
						settlementLog.setSettlementStatus(SettlementStatus.SUCCESS);
						transaction = transactionRepository.findOne(id);
						transaction.setSettlementStatus(SettlementStatus.SUCCESS);
						transactionRepository.save(transaction);
					} else {
						settlementLog.setSettlementStatus(SettlementStatus.FAILURE);
						transaction = transactionRepository.findOne(id);
						transaction.setSettlementStatus(SettlementStatus.FAILURE);
						transactionRepository.save(transaction);
					}
					settlementLog.setResponseCode(isoReverseResponse.get("code"));
					settlementLogRepository.save(settlementLog);
				}
				transactionLogApi.createTransactionLog(transaction, "Transaction Failed", UserType.Customer,
						sourceCustomer.getUniqueId());

				for (Map.Entry<String, Object> entry : hashResponse.entrySet()) {
					if (entry.getValue() instanceof String) {
						convertedHashResponse.put(entry.getKey(), (String) entry.getValue());
					}
				}
				String txnIdentifier = transactionCore.reverseTransactionParkingToCustomer(id, customerBankAccount,
						convertedHashResponse);
				transactionCore.reverseLedgerParkingToBank(amount, bankBranch.getBank(), txnIdentifier);
				Account toAccount = accountRepository
						.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
				commissionCore.transferBalanceFromPool(transaction, toAccount);

				createAccountPosting(bankAccountSettings.getBankParkingAccountNumber(),
						customerBankAccount.getAccountNumber(), amount, transaction);

				// transactionCore.reverseAccountParkingToCustomer(amount,
				// customerBankAccount.getCustomer());
				// transactionCore.reverseLedgerParkingToCustomer(amount,
				// customerBankAccount.getCustomer(),
				// transaction.getTransactionIdentifier());

			}
		}
		hashResponse.put("transactionIdentifier", hashRequest.get("transactionId"));
		hashResponse.put("transactionDate", "" + new Date());
		hashResponse.put("ServiceTo", requestData);
		// now call the iso for customer deducation of money from Bank
		return hashResponse;
	}

}
