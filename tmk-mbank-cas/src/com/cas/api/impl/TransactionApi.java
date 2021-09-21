package com.cas.api.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cas.api.IAccountApi;
import com.cas.api.ITransactionApi;
import com.cas.entity.Account;
import com.cas.entity.Transaction;
import com.cas.exceptions.ClientException;
import com.cas.model.StatementDTO;
import com.cas.model.TransactionDTO;
import com.cas.model.TransactionStatus;
import com.cas.model.TransactionType;
import com.cas.repositories.AccountRepository;
import com.cas.repositories.TransactionRepository;
import com.cas.util.ConvertUtil;

public class TransactionApi implements ITransactionApi {

	private final TransactionRepository transactionRepository;
	private final IAccountApi accountApi;
	private final AccountRepository accountRepository;
	private final Logger logger = LoggerFactory.getLogger(UserApi.class);

	public TransactionApi(TransactionRepository transactionRepository,
			IAccountApi accountApi, AccountRepository accountRepository) {
		this.transactionRepository = transactionRepository;
		this.accountApi = accountApi;
		this.accountRepository = accountRepository;
	}

	@Override
	public Transaction searchByTransactionId(long transactionId) {
		return transactionRepository.findByTransactionId(transactionId);
	}

	@Override
	public TransactionDTO createTransaction(TransactionDTO dto)
			throws ClientException {
		long txnId = 0;
		Account fromAccount = accountApi
				.searchByAccountId(dto.getFromAccount());
		Account toAccount = accountApi.searchByAccountId(dto.getToAccount());
		if (dto.getTransactionType().equals(TransactionType.Transfer)) {
			if (fromAccount == null || toAccount == null) {
				throw new ClientException("Invalid Account Details");
			}
		} else if (dto.getTransactionType().equals(TransactionType.Load)) {
			if (toAccount == null) {
				throw new ClientException("Invalid Account Details");
			}
		} else {
			throw new ClientException("Invalid Transaction Type");
		}
		try {
			txnId = Long.parseLong(dto.getTransactionId());
		} catch (Exception e) {
			throw new ClientException("Transaction Id Must Be Of Data Long");
		}
		Transaction txn = new Transaction();
		txn.setExchangeRate(dto.getExchangeRate());
		txn.setFromAccount(fromAccount);
		txn.setToAccount(toAccount);
		txn.setReceivedAmount(dto.getReceivedAmount());
		txn.setRemarks(dto.getRemarks());
		txn.setSentAmount(dto.getSentAmount());
		txn.setStatus(dto.getTransactionStatus());
		txn.setTransactionId(txnId);
		txn.setType(dto.getTransactionType());
		txn.setUniqueId(System.currentTimeMillis());
		transactionRepository.save(txn);
		if (dto.getTransactionStatus().equals(TransactionStatus.Complete)) {
			processTransaction(txnId);
		}
		return ConvertUtil.convertTransactionToDTO(txn);
	}

	@Override
	public TransactionDTO completeTransaction(long transactionId)
			throws ClientException {
		Transaction txn = transactionRepository
				.findByTransactionId(transactionId);
		if (txn != null) {
			if (txn.getStatus().equals(TransactionStatus.Pending)) {
				txn.setStatus(TransactionStatus.Complete);
				txn.setLastModified(new Date());
				transactionRepository.save(txn);
				processTransaction(txn.getTransactionId());
				return ConvertUtil.convertTransactionToDTO(txn);
			} else if (txn.getStatus().equals(TransactionStatus.Complete)) {
				throw new ClientException("Transaction Already Processed");
			} else if (txn.getStatus().equals(TransactionStatus.Reversed)) {
				throw new ClientException("Transaction Already Reversed");
			} else if (txn.getStatus().equals(TransactionStatus.Cancel)) {
				throw new ClientException("Transaction Already Cancelled");
			} else {
				throw new ClientException("Invalid Transaction Status");
			}
		} else {
			throw new ClientException("Invalid Transaction Id");
		}
	}

	@Override
	public TransactionDTO reverseTransaction(long transactionId)
			throws ClientException {
		Transaction txn = transactionRepository
				.findByTransactionId(transactionId);
		TransactionDTO dto = new TransactionDTO();
		if (txn != null) {
			if (txn.getStatus().equals(TransactionStatus.Complete)) {
				if (txn.getType().equals(TransactionType.Transfer)) {
					dto = reverseFundTransfer(txn);
				} else if (txn.getType().equals(TransactionType.Load)) {
					dto = reverseLoadFund(txn);
				}
				return dto;
			} else if (txn.getStatus().equals(TransactionStatus.Pending)) {
				throw new ClientException("Transaction Not yet Processed");
			} else if (txn.getStatus().equals(TransactionStatus.Reversed)) {
				throw new ClientException("Transaction Already Reversed");
			} else if (txn.getStatus().equals(TransactionStatus.Cancel)) {
				throw new ClientException("Transaction Already Cancelled");
			} else {
				throw new ClientException("Invalid Transaction Status");
			}
		} else {
			throw new ClientException("Invalid Transaction Id");
		}
	}

	@Override
	public TransactionDTO cancelTransaction(long transactionId)
			throws ClientException {
		Transaction txn = transactionRepository
				.findByTransactionId(transactionId);
		if (txn != null) {
			if (txn.getStatus().equals(TransactionStatus.Pending)) {
				txn.setStatus(TransactionStatus.Cancel);
				txn.setLastModified(new Date());
				transactionRepository.save(txn);
				return ConvertUtil.convertTransactionToDTO(txn);
			} else if (txn.getStatus().equals(TransactionStatus.Complete)) {
				throw new ClientException(
						"Transaction Already Completed.Cannot Cancel The Transaction But Can Reverse The Transaction");
			} else if (txn.getStatus().equals(TransactionStatus.Cancel)) {
				throw new ClientException("Transaction Already Cancelled");
			} else if (txn.getStatus().equals(TransactionStatus.Reversed)) {
				throw new ClientException("Transaction Already Reversed");
			} else {
				throw new ClientException("Invalid Transaction Status");
			}
		} else {
			throw new ClientException("Invalid Transaction Id");
		}
	}

	private void processTransaction(long transactionId) throws ClientException {
		Transaction txn = transactionRepository
				.findByTransactionId(transactionId);
		if (txn != null) {
			if (txn.getStatus().equals(TransactionStatus.Complete)) {
				if (txn.getType().equals(TransactionType.Transfer)) {
					fundTransfer(txn);
				} else if (txn.getType().equals(TransactionType.Load)) {
					loadFund(txn);
				}
			}
		} else {
			throw new ClientException("Invalid Transaction Id");
		}
	}

	private void fundTransfer(Transaction txn) throws ClientException {
		Account fromAccount = txn.getFromAccount();
		Account toAccount = txn.getToAccount();
		if (fromAccount.getBalance() >= txn.getSentAmount()) {
			fromAccount.setBalance(fromAccount.getBalance()
					- txn.getSentAmount());
			accountRepository.save(fromAccount);
			toAccount.setBalance(toAccount.getBalance()
					+ txn.getReceivedAmount());
			accountRepository.save(toAccount);
		} else {
			throw new ClientException("Insufficient Amount in Sender Account");
		}
	}

	private TransactionDTO reverseFundTransfer(Transaction txn)
			throws ClientException {
		Account fromAccount = txn.getFromAccount();
		Account toAccount = txn.getToAccount();
		if (toAccount.getBalance() >= txn.getReceivedAmount()) {
			txn.setStatus(TransactionStatus.Reversed);
			txn.setLastModified(new Date());
			transactionRepository.save(txn);
			fromAccount.setBalance(fromAccount.getBalance()
					+ txn.getSentAmount());
			accountRepository.save(fromAccount);
			toAccount.setBalance(toAccount.getBalance()
					- txn.getReceivedAmount());
			accountRepository.save(toAccount);
			return ConvertUtil.convertTransactionToDTO(txn);
		} else {
			throw new ClientException(
					"Insufficient Amount in Receiver Account For Reversal");
		}
	}

	private void loadFund(Transaction txn) throws ClientException {
		Account account = txn.getToAccount();
		account.setBalance(account.getBalance() + txn.getReceivedAmount());
		accountRepository.save(account);
	}

	private TransactionDTO reverseLoadFund(Transaction txn)
			throws ClientException {
		txn.setStatus(TransactionStatus.Reversed);
		txn.setLastModified(new Date());
		transactionRepository.save(txn);
		Account account = txn.getToAccount();
		account.setBalance(account.getBalance() - txn.getReceivedAmount());
		accountRepository.save(account);
		return ConvertUtil.convertTransactionToDTO(txn);
	}

	@Override
	public List<TransactionDTO> getStatement(Account account) throws Exception {
		List<Transaction> txn = transactionRepository.findByAccountId(account
				.getId());
		//return ConvertUtil.convertTransactionToStatement(txn, account);
		return ConvertUtil.convertTransactionListToDTO(txn);
	}

	@Override
	public TransactionDTO getTransactionDetail(long txnId) {
		Transaction txn = searchByTransactionId(txnId);
		return ConvertUtil.convertTransactionToDTO(txn);
	}

}
