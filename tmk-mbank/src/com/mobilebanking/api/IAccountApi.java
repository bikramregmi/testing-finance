package com.mobilebanking.api;

import java.util.HashMap;

import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.model.AccountDTO;
import com.mobilebanking.model.LedgerType;
import com.mobilebanking.util.ClientException;

public interface IAccountApi {
	AccountDTO createDefaultAccount(String user) throws ClientException;

	AccountDTO checkBalance(String accountNumber) throws ClientException;

	Account searchByAccountId(String fromAccount);

	boolean updateAccountBalance(String accountId, double amount);

	Account updateAccount(double amount, Account account, boolean isCredit) throws ClientException;

	AccountDTO createAccount(Double balance, String userName) throws ClientException;

	AccountDTO findByAccountNumber(String accountNo) throws ClientException;

	Account loadBalance(double amount, Account account, boolean isCredit) throws ClientException;

	void ledgerPosting(Account fromAccount, Account toAccount, double amount, boolean isCredit, String remarks,Long currentUserId)
			throws Exception;

	void ledgerPosting(Account fromAccount, Account toAccount, double amount, boolean isCredit, Double initialBalance,
			LedgerType ledgerType, String remarks,Long currentUserId) throws Exception;

	void updateCreditLimit(String bank, Double amount, String remarks,Long currentUserId) throws Exception;

	Double getSpendableAmount(Bank bank);

	HashMap<String, Double> getCreditBalanceAndBalanceByBank(long id);

	double getSpendableAmount(long bankId);

	void loadFund(AccountDTO accountDto, String remarks,Long currentUserId) throws Exception;

	boolean decreaseFund(AccountDTO accountDto, String remarks,Long currentUserId) throws Exception;

	public AccountDTO getAccountByBankAndCardlessBank(Long bankId, Long cardlessBankId);

	void updateCardlessBankBalance(Long bank, Long cardlessBank, Double amount);
}
