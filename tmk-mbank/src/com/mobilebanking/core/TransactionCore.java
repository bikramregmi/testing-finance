package com.mobilebanking.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.ILedgerApi;
import com.mobilebanking.api.ITransactionApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankAccountSettings;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.BankCommission;
import com.mobilebanking.entity.CommissionAmount;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.LedgerDTO;
import com.mobilebanking.model.LedgerStatus;
import com.mobilebanking.model.LedgerType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.TransactionDTO;
import com.mobilebanking.model.TransactionStatus;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.BankAccountSettingsRepository;
import com.mobilebanking.repositories.MerchantManagerRepository;
import com.mobilebanking.repositories.MerchantServiceRepository;
import com.mobilebanking.repositories.TransactionRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.ClientException;

@Service
public class TransactionCore {
	// the class is to be modified as the Merchant account is maintained

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ITransactionApi transactionApi;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ILedgerApi ledgerApi;

	@Autowired
	private MerchantServiceRepository merchantServiceRepository;

	@Autowired
	private MerchantManagerRepository merchantManagerRepository;

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private BankAccountSettingsRepository bankAccountSettingsRepository;

	public long transactionCustomerToParking(MerchantManager merchantManager, long aid, double amount,
			Customer sourceCustomer, HashMap<String, String> hashRequest) {

		Transaction transaction = new Transaction();
		TransactionDTO dto = new TransactionDTO();
		if (merchantManager.getMerchantsAndServices().getMerchant() != null) {
			dto.setOriginatingUser(aid);
			dto.setDestination(hashRequest.get("serviceTo"));
			dto.setTransactionOwner(aid);
			dto.setAmount(amount);
			dto.setRequestDetail(hashRequest);
			dto.setTransactionType(TransactionType.Mpayment);

			User user = userRepository.findByUserTypeAndAssociatedId(UserType.Customer, sourceCustomer.getId());
			Account account = accountRepository.findByUser(user);

			dto.setSourceAccount(account.getAccountNumber());
			// dto.setSourceBankAccount(sourceCustomer.get);
			dto.setDestinationAccount("parkingAccount");
			dto.setTransactionStatus(TransactionStatus.Pending);
			transaction = transactionApi.createTransaction(dto, merchantManager);

		}
		return transaction.getId();
	}

	public Map<String, Double> getCommission(CommissionAmount commissionAmount, BankCommission bankCommission,
			double amount) {
		Map<String, Double> commissionMap = new HashMap<String, Double>();
		try {
			double commissionPercentage = commissionAmount.getCommissionPercentage();
			double commissionFlat = commissionAmount.getCommissionFlat();
			commissionPercentage = (commissionPercentage * amount) / 100;

			if (commissionFlat > commissionPercentage) {
				commissionMap.put("commission", commissionFlat);
				double commissionBankFlat = bankCommission.getCommissionFlat();
				commissionMap.put("bankCommission", commissionBankFlat);
				double channelPartnerCommissionFlat = bankCommission.getChannelPartnerCommissionFlat();
				commissionMap.put("channelPartnerCommission", channelPartnerCommissionFlat);
				double operatorCommissionFlat;
				if (channelPartnerCommissionFlat > 0.0) {
					operatorCommissionFlat = commissionFlat - commissionBankFlat - channelPartnerCommissionFlat;
				} else {
					operatorCommissionFlat = commissionFlat - commissionBankFlat;
				}
				if (operatorCommissionFlat < 0) {
					operatorCommissionFlat = 0.0;
				}
				commissionMap.put("operatorCommission", operatorCommissionFlat);

				if (commissionMap.get("bankCommission") == 0.0 && commissionMap.get("channelPartnerCommission") == 0.0
						&& commissionMap.get("operatorCommission") == 0.0) {
					commissionMap.put("commission", 0.0);
				}
			} else {
				commissionMap.put("commission", commissionPercentage);
				double commissionBankPercentage = ((bankCommission.getCommissionPercentage() )* amount) / 100;
				commissionMap.put("bankCommission", commissionBankPercentage);

				double channelPartnerCommissionPercentage = ((bankCommission.getChannelPartnerCommissionPercentage()
						* amount) / 100);
				commissionMap.put("channelPartnerCommission", channelPartnerCommissionPercentage);
				BigDecimal operatorCommissionPercentage;
					operatorCommissionPercentage = new BigDecimal(((bankCommission.getOperatorCommissionPercentage())* amount) / 100,
							MathContext.DECIMAL64);
				if (operatorCommissionPercentage.doubleValue() < 0) {
					operatorCommissionPercentage = new BigDecimal(0.0);
				}
				commissionMap.put("operatorCommission", operatorCommissionPercentage.doubleValue());
				
				BigDecimal cashBackPercentage;
				cashBackPercentage = new BigDecimal((bankCommission.getCashBack() * amount) / 100,
						MathContext.DECIMAL64);
			if (cashBackPercentage.doubleValue() < 0) {
				cashBackPercentage = new BigDecimal(0.0);
			}
			commissionMap.put("cashBack", cashBackPercentage.doubleValue());
				if (commissionMap.get("bankCommission") == 0.0 && commissionMap.get("channelPartnerCommission") == 0.0
						&& commissionMap.get("operatorCommission") == 0.0) {
					commissionMap.put("commission", 0.0);
				}
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			commissionMap.put("operatorCommission", 0.0);
			commissionMap.put("channelPartnerCommission", 0.0);
			commissionMap.put("bankCommission", 0.0);
			commissionMap.put("commission", 0.0);

		}
		return commissionMap;
	}

	public void ledgerCustomerToParking(double amount, Customer sourceCustomer) throws ClientException {

		LedgerDTO ledgetDTO = new LedgerDTO();
		ledgetDTO.setAmount(amount);

		User user = userRepository.findByUserTypeAndAssociatedId(UserType.Customer, sourceCustomer.getId());
		Account account = accountRepository.findByUser(user);
		ledgetDTO.setFromAccount(account.getAccountNumber());
		ledgetDTO.setToAccount("parkingAccount");
		ledgetDTO.setRemarks("Transfer To Parking account");
		ledgetDTO.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDTO.setTransactionType(LedgerType.TRANSFER);
		ledgetDTO.setExternalRefId(0L);
		ledgetDTO = ledgerApi.createLedger(ledgetDTO);

	}

	public void accountCustomerToParking(Customer sourceCustomer, double amount) {

		User user = userRepository.findByUserTypeAndAssociatedId(UserType.Customer, sourceCustomer.getId());
		Account fromAccount = accountRepository.findByUser(user);

		Account toAccount = accountApi.searchByAccountId("parkingAccount");
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);

	}

	public String transactionParkingToMerchant(long id, MerchantManager manager, HashMap<String, String> hashResponse) {
		TransactionDTO dto = new TransactionDTO();
		dto.setId(id);
		dto.setTransactionStatus(TransactionStatus.Complete);

		// MerchantService merchantService = merchantServiceRepository
		// .findMerchantServiceByIdentifier(service.getUniqueIdentifier());
		// Merchant merchant = merchantManagerRepository
		// .getselected(merchantService.getUniqueIdentifier(), Status.Active,
		// true).getMerchantsAndServices()
		// .getMerchant();
		Account account = accountRepository
				.findAccountByAccountNumber(manager.getMerchantsAndServices().getMerchant().getAccountNumber());
		dto.setDestinationAccount(account.getAccountNumber());
		dto.setResponseDetail(hashResponse);
		if(hashResponse != null && hashResponse.get("RefStan") != null){
			dto.setMerchantRefstan(hashResponse.get("RefStan"));
		}
		dto = transactionApi.editTransaction(dto);
		return dto.getTransactionIdentifier();
	}

	public void ledgerParkingToMerchant(double amount, MerchantManager manager, String transactionIdentifier)
			throws ClientException {
		LedgerDTO ledgetDTO = new LedgerDTO();
		ledgetDTO.setAmount(amount);
		ledgetDTO.setFromAccount("parkingAccount");

		// MerchantService merchantService = merchantServiceRepository
		// .findMerchantServiceByIdentifier(service.getUniqueIdentifier());
		// Merchant merchant = merchantManagerRepository
		// .getselected(merchantService.getUniqueIdentifier(), Status.Active,
		// true).getMerchantsAndServices()
		// .getMerchant();
		Account account = accountRepository
				.findAccountByAccountNumber(manager.getMerchantsAndServices().getMerchant().getAccountNumber());

		ledgetDTO.setToAccount(account.getAccountNumber());

		ledgetDTO.setRemarks("Transfer To Merchant account for "
				+ manager.getMerchantsAndServices().getMerchantService().getService() + " for "
				+ transactionIdentifier);
		ledgetDTO.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDTO.setTransactionType(LedgerType.TRANSFER);
		ledgetDTO.setExternalRefId(0L);
		ledgetDTO.setTransactionId(transactionIdentifier);
		ledgetDTO = ledgerApi.createLedger(ledgetDTO);

	}

	public void accountParkingToService(double amount, MerchantService service) {
		Account fromAccountSC = accountApi.searchByAccountId("parkingAccount");

		// MerchantService merchantService = merchantServiceRepository
		// .findMerchantServiceByIdentifier(service.getUniqueIdentifier());
		// Merchant merchant = merchantManagerRepository
		// .getselected(merchantService.getUniqueIdentifier(), Status.Active,
		// true).getMerchantsAndServices()
		// .getMerchant();
		Account account = accountRepository.findAccountByAccountNumber(service.getAccountNumber());

		Account toAccountSC = accountApi.searchByAccountId(account.getAccountNumber());
		fromAccountSC.setBalance(fromAccountSC.getBalance() - amount);
		accountRepository.save(fromAccountSC);
		toAccountSC.setBalance(toAccountSC.getBalance() + amount);
		accountRepository.save(toAccountSC);
	}

	public String reverseTransactionParkingToCustomer(long transactionId, CustomerBankAccount customerBankAccount,
			HashMap<String, String> hashResponse) {

		// User user =
		// userRepository.findByUserTypeAndAssociatedId(UserType.Customer,
		// CUS.getId());
		// Account account = accountRepository.findByUser(user);

		TransactionDTO dto = new TransactionDTO();
		dto.setId(transactionId);
		dto.setTransactionStatus(TransactionStatus.CancelledWithRefund);
		dto.setDestinationAccount(customerBankAccount.getAccountNumber());
		dto.setResponseDetail(hashResponse);
		if(hashResponse != null && hashResponse.get("RefStan") != null){
			dto.setMerchantRefstan(hashResponse.get("RefStan"));
		}
		dto = transactionApi.editTransaction(dto);
		return dto.getTransactionIdentifier();
	}

	public void reverseLedgerParkingToCustomer(double amount, Customer customer, String transactionIdentifier)
			throws ClientException {
		LedgerDTO ledgetDTO = new LedgerDTO();
		User user = userRepository.findByUserTypeAndAssociatedId(UserType.Customer, customer.getId());
		Account account = accountRepository.findByUser(user);

		ledgetDTO.setAmount(amount);
		ledgetDTO.setFromAccount("parkingAccount");
		ledgetDTO.setToAccount(account.getAccountNumber());
		ledgetDTO.setRemarks("Transfer To Customer Account");
		ledgetDTO.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDTO.setTransactionType(LedgerType.TRANSFER);
		ledgetDTO.setExternalRefId(0L);
		ledgetDTO.setTransactionId(transactionIdentifier);
		ledgetDTO = ledgerApi.createLedger(ledgetDTO);
	}

	public void reverseAccountParkingToCustomer(double amount, Customer customer) {

		User user = userRepository.findByUserTypeAndAssociatedId(UserType.Customer, customer.getId());
		Account toAccountSC = accountRepository.findByUser(user);

		Account fromAccountSC = accountApi.searchByAccountId("parkingAccount");
		fromAccountSC.setBalance(fromAccountSC.getBalance() - amount);
		accountRepository.save(fromAccountSC);
		toAccountSC.setBalance(toAccountSC.getBalance() + amount);
		accountRepository.save(toAccountSC);

	}

	public long transactionBankToParking(MerchantManager merchantMerchant, long aid, double amount, Bank bank,
			HashMap<String, String> hashRequest) {
		TransactionDTO dto = new TransactionDTO();
		dto.setOriginatingUser(aid);
		dto.setDestination(hashRequest.get("serviceTo"));
		dto.setTransactionOwner(aid);
		dto.setAmount(amount);
		dto.setRequestDetail(hashRequest);
		dto.setTransactionType(TransactionType.Mpayment);
		Account account = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		dto.setSourceAccount(account.getAccountNumber());
		dto.setDestinationAccount("parkingAccount");
		dto.setTransactionStatus(TransactionStatus.Pending);
		// to do add merchant manager in transaction
		Transaction transaction = transactionApi.createTransactionbank(dto, merchantMerchant, bank);
		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(), Status.Active);
		transaction.setSourceBankAccount(bankAccountSettings.getBankParkingAccountNumber());
		transaction = transactionRepository.save(transaction);
		return transaction.getId();
	}

	public long transactionBankBranchToParking(MerchantManager manager, long associateId, double amount,
			BankBranch bankBranch, HashMap<String, String> hashRequest) {

		TransactionDTO dto = new TransactionDTO();
		dto.setOriginatingUser(associateId);
		dto.setDestination(hashRequest.get("serviceTo"));
		dto.setTransactionOwner(associateId);
		dto.setAmount(amount);
		dto.setRequestDetail(hashRequest);
		dto.setTransactionType(TransactionType.Mpayment);
		dto.setSourceAccount(bankBranch.getBank().getAccountNumber());
		dto.setDestinationAccount("parkingAccount");
		// dto.setSourceBankAccount(bran);
		dto.setTransactionStatus(TransactionStatus.Pending);
		Bank bank = bankBranch.getBank();
		Transaction transaction = transactionApi.createTransactionBankBranch(dto, manager, bankBranch);
		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(transaction.getBank().getSwiftCode(), Status.Active);
		transaction.setSourceBankAccount(bankAccountSettings.getBankParkingAccountNumber());
		transaction = transactionRepository.save(transaction);
		return transaction.getId();
	}

	public long customerToParkingTransaction(MerchantManager manager, long associateId, double amount,
			BankBranch bankBranch, HashMap<String, String> hashRequest, CustomerBankAccount customerBankAccount) {
		TransactionDTO dto = new TransactionDTO();
		dto.setOriginatingUser(associateId);
		dto.setDestination(hashRequest.get("serviceTo"));
		dto.setTransactionOwner(associateId);
		dto.setAmount(amount);
		dto.setRequestDetail(hashRequest);
		dto.setTransactionType(TransactionType.Mpayment);
		dto.setSourceAccount(bankBranch.getBank().getAccountNumber());
		dto.setSourceBankAccount(customerBankAccount.getAccountNumber());
		dto.setDestinationAccount("parkingAccount");
		dto.setTransactionStatus(TransactionStatus.Pending);
		Transaction transaction = transactionApi.createTransactionCustomer(dto, manager, bankBranch,
				customerBankAccount);
		return transaction.getId();
	}

	public void ledgerBankToParking(double amount, Bank bank, long transactionId) throws ClientException {
		LedgerDTO ledgerDTO = new LedgerDTO();
		String transactionIdentifier = transactionRepository.findOne(transactionId).getTransactionIdentifier();
		ledgerDTO.setAmount(amount);
		Account account = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		ledgerDTO.setFromAccount(account.getAccountNumber());
		ledgerDTO.setToAccount("parkingAccount");
		ledgerDTO.setRemarks("Transfer To Parking account");
		ledgerDTO.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgerDTO.setTransactionType(LedgerType.TRANSFER);
		ledgerDTO.setExternalRefId(0L);
		ledgerDTO.setTransactionId(transactionIdentifier);
		ledgerDTO = ledgerApi.createLedger(ledgerDTO);
	}

	public void accountBankToParking(Bank bank, double amount) {

		Account fromAccount = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		Account toAccount = accountApi.searchByAccountId("parkingAccount");
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);

		// now make a call to iso for transfer of amount to parking account
		// there also
		BankAccountSettings bankAccountSettings = bankAccountSettingsRepository
				.getBankAccountSettingsByBankCodeAndStatus(bank.getSwiftCode(), Status.Active);

	}

	public String reverseTransactionParkingToBank(long id, Bank bank, HashMap<String, String> hashResponse) {
		TransactionDTO dto = new TransactionDTO();
		dto.setId(id);
		dto.setTransactionStatus(TransactionStatus.CancelledWithRefund);
		Account account = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		dto.setDestinationAccount(account.getAccountNumber());
		dto.setResponseDetail(hashResponse);
		dto = transactionApi.editTransaction(dto);
		return dto.getTransactionIdentifier();

	}

	public void reverseAccountParkingToBank(double amount, Bank bank) {
		Account fromAccountSC = accountApi.searchByAccountId("parkingAccount");

		Account toAccountSC = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		fromAccountSC.setBalance(fromAccountSC.getBalance() - amount);
		accountRepository.save(fromAccountSC);
		toAccountSC.setBalance(toAccountSC.getBalance() + amount);
		accountRepository.save(toAccountSC);
	}

	public void reverseLedgerParkingToBank(double amount, Bank bank, String transactionIdentifier)
			throws ClientException {
		LedgerDTO ledgetDTO = new LedgerDTO();
		ledgetDTO.setAmount(amount);
		ledgetDTO.setFromAccount("parkingAccount");
		Account toAccountSC = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		ledgetDTO.setToAccount(toAccountSC.getAccountNumber());
		ledgetDTO.setRemarks("Transfer To Bank Account");
		ledgetDTO.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDTO.setTransactionType(LedgerType.TRANSFER);
		ledgetDTO.setExternalRefId(0L);
		ledgetDTO.setTransactionId(transactionIdentifier);
		ledgetDTO = ledgerApi.createLedger(ledgetDTO);

	}

	public void ledgerBankBranchToParking(double amount, BankBranch bankBranch, long transactionId)
			throws ClientException {
		LedgerDTO ledgerDTO = new LedgerDTO();
		String transactionIdentifier = transactionRepository.findOne(transactionId).getTransactionIdentifier();
		ledgerDTO.setAmount(amount);
		Account account = accountRepository.findAccountByAccountNumber(bankBranch.getBank().getAccountNumber());
		;
		ledgerDTO.setFromAccount(account.getAccountNumber());
		ledgerDTO.setToAccount("parkingAccount");
		ledgerDTO.setRemarks("Transfer To Parking account via Bank Branch");
		ledgerDTO.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgerDTO.setTransactionType(LedgerType.TRANSFER);
		ledgerDTO.setExternalRefId(0L);
		ledgerDTO.setTransactionId(transactionIdentifier);
		ledgerDTO = ledgerApi.createLedger(ledgerDTO);

	}

	public void accountBankBranchToParking(BankBranch bankBranch, double amount) {
		Account fromAccount = accountRepository.findAccountByAccountNumber(bankBranch.getBank().getAccountNumber());
		Account toAccount = accountApi.searchByAccountId("parkingAccount");
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);
	}

	public String reverseTransactionParkingToBankBranch(long id, BankBranch bankBranch,
			HashMap<String, String> hashResponse) {
		TransactionDTO dto = new TransactionDTO();
		dto.setId(id);
		dto.setTransactionStatus(TransactionStatus.CancelledWithRefund);
		Account account = accountRepository.findAccountByAccountNumber(bankBranch.getBank().getAccountNumber());
		dto.setDestinationAccount(account.getAccountNumber());
		dto.setResponseDetail(hashResponse);
		dto = transactionApi.editTransaction(dto);
		return dto.getTransactionIdentifier();
	}

	public void reverseLedgerParkingToBankBranch(double amount, BankBranch bankBranch, String transactionIdentifier)
			throws ClientException {
		LedgerDTO ledgetDTO = new LedgerDTO();
		ledgetDTO.setAmount(amount);
		ledgetDTO.setFromAccount("parkingAccount");
		Account toAccountSC = accountRepository.findAccountByAccountNumber(bankBranch.getBank().getAccountNumber());
		ledgetDTO.setToAccount(toAccountSC.getAccountNumber());
		ledgetDTO.setRemarks("Transfer To Bank Account");
		ledgetDTO.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDTO.setTransactionType(LedgerType.TRANSFER);
		ledgetDTO.setExternalRefId(0L);
		ledgetDTO.setTransactionId(transactionIdentifier);
		ledgetDTO = ledgerApi.createLedger(ledgetDTO);
	}

	public void reverseAccountParkingToBankBranch(double amount, BankBranch bankBranch) {
		Account fromAccountSC = accountApi.searchByAccountId("parkingAccount");

		Account toAccountSC = accountRepository.findAccountByAccountNumber(bankBranch.getBank().getAccountNumber());
		fromAccountSC.setBalance(fromAccountSC.getBalance() - amount);
		accountRepository.save(fromAccountSC);
		toAccountSC.setBalance(toAccountSC.getBalance() + amount);
		accountRepository.save(toAccountSC);
	}

	public void setAmbiguousTransaction(long id, HashMap<String, String> hashResponse) {
		TransactionDTO dto = new TransactionDTO();
		dto.setId(id);
		dto.setTransactionStatus(TransactionStatus.Ambiguous);
		dto.setResponseDetail(hashResponse);
		dto = transactionApi.editAmbiguousTransaction(dto);

	}
}
