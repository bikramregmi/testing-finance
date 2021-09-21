package com.cas.api;

import java.util.List;

import com.cas.entity.Account;
import com.cas.entity.Transaction;
import com.cas.exceptions.ClientException;
import com.cas.model.StatementDTO;
import com.cas.model.TransactionDTO;

public interface ITransactionApi {

	Transaction searchByTransactionId(long transactionId);

	TransactionDTO createTransaction(TransactionDTO txn) throws ClientException;

	TransactionDTO completeTransaction(long transactionId) throws ClientException;

	TransactionDTO reverseTransaction(long transactionId) throws ClientException;

	TransactionDTO cancelTransaction(long transactionId) throws ClientException;

	List<TransactionDTO> getStatement(Account account) throws Exception;

	TransactionDTO getTransactionDetail(long txnId);

	

}
