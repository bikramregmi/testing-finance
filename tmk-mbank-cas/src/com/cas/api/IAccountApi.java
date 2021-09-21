package com.cas.api;

import com.cas.entity.Account;
import com.cas.entity.User;
import com.cas.exceptions.ClientException;
import com.cas.model.AccountDTO;

public interface IAccountApi {

	AccountDTO createAccount(User user, String country) throws ClientException;

	AccountDTO checkBalance(String accountNo) throws ClientException;

	Account searchByAccountId(String fromAccount);
	
	

}
