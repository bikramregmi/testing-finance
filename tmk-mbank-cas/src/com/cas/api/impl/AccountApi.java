package com.cas.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cas.api.IAccountApi;
import com.cas.entity.Account;
import com.cas.entity.Country;
import com.cas.entity.User;
import com.cas.exceptions.ClientException;
import com.cas.model.AccountDTO;
import com.cas.repositories.AccountRepository;
import com.cas.repositories.CountryRepository;
import com.cas.util.ConvertUtil;

public class AccountApi implements IAccountApi {

	private final AccountRepository accountRepository;
	private final CountryRepository countryRepository;
	private final Logger logger = LoggerFactory.getLogger(AccountApi.class);

	public AccountApi(AccountRepository accountRepository,CountryRepository countryRepository) {
		this.accountRepository = accountRepository;
		this.countryRepository=countryRepository;

	}
	
	@Override
	public AccountDTO createAccount(User user,String country) throws ClientException{
		Country c=countryRepository.findByCountryCode(country);
		if(c!=null){
			Account acc=new Account();
			acc.setAccountNo("TM"+System.currentTimeMillis());
			acc.setBalance(0.0);
			acc.setCountry(c);
			acc.setUser(user);
			accountRepository.save(acc);
			return ConvertUtil.convertAccount(acc);
		}
		throw new ClientException("Invalid Country Code");
		
	}

	@Override
	public AccountDTO checkBalance(String accountNo) throws ClientException {
		Account acc=accountRepository.findByAccountNo(accountNo);
		if(acc==null){
			throw new ClientException("Invalid Account Number");
		}
		else{
			return ConvertUtil.convertAccount(acc);
		}
	}

	@Override
	public Account searchByAccountId(String account) {
		return accountRepository.findByAccountNo(account);
	}

	
	
}
