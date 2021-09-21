package com.mobilebanking.api.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.CreditLimit;
import com.mobilebanking.entity.Ledger;
import com.mobilebanking.model.AccountDTO;
import com.mobilebanking.model.AccountType;
import com.mobilebanking.model.LedgerStatus;
import com.mobilebanking.model.LedgerType;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CardlessBankRepository;
import com.mobilebanking.repositories.CreditLimitRepository;
import com.mobilebanking.repositories.LedgerRepository;
import com.mobilebanking.util.AccountUtil;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.ClientExceptionMessageConstants;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.StringConstants;

@Service
public class AccountApi implements IAccountApi {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private LedgerRepository ledgerRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private CreditLimitRepository creditLimitRepository;

	@Autowired
	private CardlessBankRepository cardlessBankRepository;

	@Autowired
	private AccountUtil accountUtil;

	private final Logger logger = LoggerFactory.getLogger(AccountApi.class);

	@Override
	public AccountDTO createAccount(Double balance, String userName) throws ClientException {
		Account account = new Account();

		String accountNo = "TMK" + System.currentTimeMillis();
		account.setAccountNumber(accountNo);
		account.setBalance(balance);
		return ConvertUtil.convertAccount(accountRepository.save(account));

	}

	@Override
	public AccountDTO checkBalance(String accountNumber) throws ClientException {
		Account acc = accountRepository.findAccountByAccountNumber(accountNumber);
		if (acc == null) {
			throw new ClientException("Invalid Account Number");
		} else {
			return ConvertUtil.convertAccount(acc);
		}
	}

	@Override
	public AccountDTO findByAccountNumber(String accountNumber) throws ClientException {
		Account acc = accountRepository.findAccountByAccountNumber(accountNumber);
		if (acc == null) {
			throw new ClientException("Invalid Account Number");
		} else {
			return ConvertUtil.convertAccount(acc);
		}
	}

	@Override
	public Account searchByAccountId(String account) {
		return accountRepository.findAccountByAccountNumber(account);
	}

	@Override
	public AccountDTO createDefaultAccount(String user) throws ClientException {
		Account account = new Account();
		account.setAccountNumber("TMK" + System.currentTimeMillis());
		account.setBalance(0.0);
		logger.debug("inside account" + account);
		accountRepository.save(account);
		return ConvertUtil.convertAccount(account);

	}

	@Override
	public boolean updateAccountBalance(String accountNo, double amount) {
		Account account = accountRepository.findAccountByAccountNumber(accountNo);
		if (account == null) {
			return false;
		}
		account.setBalance(amount);

		return true;
	}

	@Override
	public Account loadBalance(double amount, Account account, boolean isCredit) throws ClientException {
		double balance = account.getBalance();
		if (isCredit) {
			account.setBalance(balance + amount);
		} else {
			if (balance > amount) {
				account.setBalance(balance - amount);
			} else {
				throw new ClientException(ClientExceptionMessageConstants.ACCOUNT_BALANCE_INSUFFICIENT);
			}
		}

		return accountRepository.save(account);
	}

	@Override
	public void loadFund(AccountDTO accountDto, String remarks,Long currentUserId) throws Exception {
		Bank bank = bankRepository.findByBank(accountDto.getBank());
		Account account = accountRepository.findAccountOfBank(bank.getId(), AccountType.BANK);
		Account cashAccount = accountRepository.findAccountByAccountHeadAndUserType("Cash Account", AccountType.CASH);

		account.setBalance(account.getBalance() + accountDto.getBalance());
		accountRepository.save(account);

		cashAccount.setBalance(cashAccount.getBalance() - accountDto.getBalance());
		accountRepository.save(cashAccount);

		ledgerPosting(cashAccount, account, accountDto.getBalance(), accountDto.isCredit(), remarks,currentUserId);
	}

	@Override
	public void ledgerPosting(Account fromAccount, Account toAccount, double amount, boolean isCredit, String remarks,Long currentUserId)
			throws Exception {
		Ledger ledger = new Ledger();
		ledger.setFromAccount(fromAccount);
		ledger.setToAccount(toAccount);
		ledger.setCredit(isCredit);
		if (remarks == null || remarks == "") {
			remarks = StringConstants.LEDGER_REMARKS;
			remarks = remarks.replace("ACCOUNT_ONE", fromAccount.getAccountHead());
			remarks = remarks.replace("ACCOUNT_TWO", toAccount.getAccountHead());
		}
		ledger.setRemarks(remarks);
		ledger.setStatus(LedgerStatus.COMPLETE);
		ledger.setType(LedgerType.LOADFUND);
		ledger.setAmount(amount);
		ledger.setFromBalance(fromAccount.getBalance());
		ledger.setToBalance(toAccount.getBalance());
		ledger.setTransactionId("" + System.currentTimeMillis());
		ledger.setUploadedBy(AuthenticationUtil.getCurrentUser());
		ledgerRepository.save(ledger);
	}

	@Override
	public Account updateAccount(double amount, Account account, boolean isCredit) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCreditLimit(String bank, Double amount, String remarks,Long currentUserId) throws Exception {
		Bank bankObj = bankRepository.findOne(Long.parseLong(bank));
		CreditLimit creditLimit = creditLimitRepository.findCreditLimitByBank(Long.parseLong(bank));
		Double initialCredit = 0.0;
		if (creditLimit != null) {
			initialCredit = creditLimit.getAmount();
			creditLimit.setAmount(amount);

		} else {
			creditLimit = new CreditLimit();
			creditLimit.setBank(bankObj);
			creditLimit.setAmount(amount);
		}
		creditLimitRepository.save(creditLimit);

		// to be added by amrit
		Account cashAccount = accountRepository.findAccountByAccountHeadAndUserType("Cash Account", AccountType.CASH);

		ledgerPosting(cashAccount, accountRepository.findAccountOfBank(bankObj.getId(), AccountType.BANK), amount, true,
				initialCredit, LedgerType.UPDATE_CREDIT_LIMIT, remarks,currentUserId);

		// end added
	}

	@Override
	public Double getSpendableAmount(Bank bank) {
		Double creditLimitOfBank = creditLimitRepository.findCreditAmountByBank(bank.getId());
		if (creditLimitOfBank == null) {
			return accountRepository.findBalanceOfBank(bank.getId(), AccountType.BANK);
		}
		double accountBalanceOfBank = accountRepository.findBalanceOfBank(bank.getId(), AccountType.BANK);

		return (creditLimitOfBank + accountBalanceOfBank);
	}

	@Override
	public HashMap<String, Double> getCreditBalanceAndBalanceByBank(long bankId) {
		HashMap<String, Double> balance = new HashMap<>();
		Double creditLimitOfBank = creditLimitRepository.findCreditAmountByBank(bankId);
		Double accountBalanceOfBank = accountRepository.findBalanceOfBank(bankId, AccountType.BANK);
		if (creditLimitOfBank != null) {
			balance.put("creditLimit", creditLimitOfBank);
		} else {
			balance.put("creditLimit", 0.0);
		}
		if (accountBalanceOfBank != null) {
			balance.put("balance", accountBalanceOfBank);
		} else {
			balance.put("balance", 0.0);
		}

		return balance;
	}

	@Override
	public double getSpendableAmount(long bankId) {

		Bank bank = bankRepository.findOne(bankId);
		Double creditLimitOfBank = creditLimitRepository.findCreditAmountByBank(bank.getId());
		if (creditLimitOfBank == null) {
			return accountRepository.findBalanceOfBank(bank.getId(), AccountType.BANK);
		}
		double accountBalanceOfBank = accountRepository.findBalanceOfBank(bank.getId(), AccountType.BANK);

		return (creditLimitOfBank + accountBalanceOfBank);
	}

	@Override
	public AccountDTO getAccountByBankAndCardlessBank(Long bankId, Long cardlessBankId) {
		Account account = accountRepository.findAccountByBankAndCardlessBank(bankId, cardlessBankId);
		if (account != null) {
			return ConvertUtil.convertAccount(accountRepository.save(account));
		}
		return null;
	}

	@Override
	public void updateCardlessBankBalance(Long bank, Long cardlessBank, Double amount) {
		Account account = accountRepository.findAccountByBankAndCardlessBank(bank, cardlessBank);
		if (account == null) {
			account = new Account();
			account.setBalance(amount);
			account.setUser(cardlessBankRepository.findOne(cardlessBank).getUser());
			account.setAccountHead(StringConstants.CARDLESS_BANK_ACCOUNT_HEAD);
			account.setAccountType(AccountType.CARDLESS_BANK);
			account.setAccountNumber(accountUtil.generateAccountNumber());
			account.setBank(bankRepository.findOne(bank));
			account.setCardlessBank(cardlessBankRepository.findOne(cardlessBank));
			account = accountRepository.save(account);
		} else {
			account.setBalance(account.getBalance() + amount);
			accountRepository.save(account);
		}

	}

	// added by amrit
	@Override
	public void ledgerPosting(Account fromAccount, Account toAccount, double amount, boolean isCredit,
			Double initialBalance, LedgerType ledgerType, String remarks,Long currentUserId) {
		// TODO Auto-generated method stub
		Ledger ledger = new Ledger();
		ledger.setType(ledgerType);
		ledger.setFromAccount(fromAccount);
		ledger.setToAccount(toAccount);
		ledger.setFromBalance(fromAccount.getBalance());
		ledger.setToBalance(toAccount.getBalance());
		ledger.setCredit(isCredit);
		ledger.setAmount(amount);
		ledger.setUploadedBy(AuthenticationUtil.getCurrentUser());
		if (ledgerType == LedgerType.UPDATE_CREDIT_LIMIT) {
			if (remarks == null || remarks == "") {
				remarks = "Credit Limit :"
						+ StringConstants.LEDGER_REMARKS_FOR_UPDATE.replaceFirst("ACCOUNT", toAccount.getAccountHead());
				remarks = remarks.replaceFirst("BALANCE_ONE", " " + initialBalance);
				remarks = remarks.replaceFirst("BALANCE_TWO", " " + amount);
			} else {
				remarks = "Credit Limit : " + remarks;
			}
			ledger.setRemarks(remarks);

		} else if (ledgerType == LedgerType.DECREASE_BALANCE) {
			if (remarks == null || remarks == "") {
				remarks = "Decrease Balance :"
						+ StringConstants.DECREASE_BALANCE.replaceFirst("ACCOUNT", toAccount.getAccountHead());
				remarks = remarks.replaceFirst("BALANCE_ONE", " " + initialBalance);
				remarks = remarks.replaceFirst("BALANCE_TWO", " " + amount);
			} else {
				remarks = "Decrease Balance : " + remarks;
			}
			ledger.setRemarks(remarks);
			ledger.setTransactionId("" + System.currentTimeMillis());
			ledger.setStatus(LedgerStatus.COMPLETE);

		}
		ledger.setTransactionId("" + System.currentTimeMillis());
		ledger.setStatus(LedgerStatus.COMPLETE);
		ledgerRepository.save(ledger);

		// System.out.println("updated credit limit saved in the ledger !!");
	}

	@Override
	public boolean decreaseFund(AccountDTO accountDto, String remarks,Long currentUserId) throws Exception {
		// TODO Auto-generated method stub
		boolean status = false;
		Bank bank = bankRepository.findByBank(accountDto.getBank());
		Account account = accountRepository.findAccountOfBank(bank.getId(), AccountType.BANK);
		double initialAmount = account.getBalance();
		if (initialAmount >= accountDto.getBalance()) {
			Account cashAccount = accountRepository.findAccountByAccountHeadAndUserType("Cash Account",
					AccountType.CASH);

			account.setBalance(account.getBalance() - accountDto.getBalance());
			accountRepository.save(account);

			cashAccount.setBalance(cashAccount.getBalance() + accountDto.getBalance());
			accountRepository.save(cashAccount);
			status = true;
			ledgerPosting(cashAccount, account, accountDto.getBalance(), accountDto.isCredit(), initialAmount,
					LedgerType.DECREASE_BALANCE, remarks,currentUserId);

		}
		return status;
	}
	// added end
}
