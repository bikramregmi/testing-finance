package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankOAuthClient;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.util.ConvertUtil;

/**
 * @author bibek
 *
 */
@Service
public class CustomerBankAccountApi implements ICustomerBankAccountApi {
	
	@Autowired
	CustomerBankAccountRepository customerBankAccountRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	private BankOAuthClientRepository bankAuthRepository;
	
	@Autowired
	private ConvertUtil convertUtil;
	
	@Override
	public void saveCustomerBankAccount(CustomerBankAccountDTO customerBankAccountDto) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.ICustomerBankAccountApi#findCustomerBankAccountById(long)
	 */
	@Override
	public CustomerBankAccountDTO findCustomerBankAccountById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.ICustomerBankAccountApi#findCustomerBankAccountByCustomer(long)
	 */
	@Override
	public List<CustomerBankAccountDTO> findCustomerBankAccountByCustomer(String uniqueId) {
		List<CustomerBankAccount> accountList = customerBankAccountRepository.findCustomerBankAccountOfCustomer(uniqueId);
		if (! accountList.isEmpty()) {
			return ConvertUtil.convertCustomerBankAccountToDto(accountList);
		}
			
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.ICustomerBankAccountApi#findCustomerBankAccountOfParticularBank(long)
	 */
	@Override
	public List<CustomerBankAccountDTO> findCustomerAllAccountsOfParticularBank(long bankId, long branchId) {
		List<CustomerBankAccount> customerBankAccountList = ConvertUtil.convertIterableToList(
				customerBankAccountRepository.findCustomerBankAccountOfParticularBank(bankId, branchId));
		if (! customerBankAccountList.isEmpty()) {
			return ConvertUtil.convertCustomerBankAccountToDto(customerBankAccountList);
		}
		return null;
	}

	@Override
	public CustomerBankAccountDTO findCustomerBankAccountOfParticularBank(long bankId, long branchId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerBankAccountDTO> findCustomerBankAccounts(String uniqueId) {
		List<CustomerBankAccount> customerBankAccounts = customerBankAccountRepository.findCustomerBankAccountOfCustomer(uniqueId);
		if (! customerBankAccounts.isEmpty()) {
			return ConvertUtil.convertCustomerBankAccountToDto(customerBankAccounts);
		}
		return null;
	}

	@Override
	public List<CustomerBankAccountDTO> findCustomerAllAccountsByBranchAndCustomer(long branchId, String uniqueId) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		List<CustomerBankAccount> customerBankAccounts = customerBankAccountRepository.findCustomerAllAccountsByBranchAndCustomer(branchId, customer);
		if (! customerBankAccounts.isEmpty()) {
			return ConvertUtil.convertCustomerBankAccountToDto(customerBankAccounts);
		}
		return null;
	}

	@Override
	public List<CustomerBankAccountDTO> findCustomerAllAccountsByBankAndCustomer(long bankId, String uniqueId) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		List<CustomerBankAccount> customerBankAccounts = customerBankAccountRepository.findCustomerAllAccountsByBankAndCustomer(bankId, customer);
		if (! customerBankAccounts.isEmpty()) {
			return ConvertUtil.convertCustomerBankAccountToDto(customerBankAccounts);
		}
		return null;
	}

	@Override
	public CustomerBankAccountDTO findCustomerBankAccountByAccountNo(String accountNumber, long customerId) {
		CustomerBankAccount customerBankAccounts = customerBankAccountRepository.findCustomerAccountByAccountNumber(accountNumber, customerId);
		if(customerBankAccounts!=null){
			return ConvertUtil.convertCustomerBankAccount(customerBankAccounts);
		}
		return null;
		
	}

	@Override
	public List<CustomerBankAccountDTO> findCustomerAllAccountsByBank(long bankId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerBankAccountDTO findCustomerBankAccountByAccountNumberAndBank(String accountNumber,long bankId) {
		CustomerBankAccount customerBankAccounts = customerBankAccountRepository.findByAccountNumberAndBankId(accountNumber,bankId);
		if(customerBankAccounts!=null)
		return ConvertUtil.convertCustomerBankAccount(customerBankAccounts);
		else
			return null;
	}

	@Override
	public List<CustomerBankAccountDTO> findCustomerBankAccountByCustomer(String uniqueId, String clientId) {
		Customer customer = customerRepository.findCustomerByUniqueId(uniqueId);
		BankOAuthClient bankAouthClient = bankAuthRepository.findByOAuthClientId(clientId);
		Bank bank = bankAouthClient.getBank();
		List<CustomerBankAccount> customerBankAccounts = customerBankAccountRepository.findCustomerAllAccountsByBankAndCustomer(bank.getId(), customer);
		if (! customerBankAccounts.isEmpty()) {
			return ConvertUtil.convertCustomerBankAccountToDto(customerBankAccounts);
		}
		return null;
	}

	@Override
	public List<CustomerBankAccountDTO> findAllAccounts() {
		List<CustomerBankAccount> accountList = customerBankAccountRepository.findAll();
		if (accountList != null) {
			return ConvertUtil.convertCustomerBankAccountToDto(accountList);
		}
		return null;
	}

	@Override
	public CustomerDTO findCustomerByBankAccountNumberAndBank(String accountNumber, long bankId) {
		CustomerBankAccount customerBankAccount = customerBankAccountRepository.findByAccountNumberAndBankId(accountNumber, bankId);
		if(customerBankAccount!=null){
			return convertUtil.convertCustomer(customerBankAccount.getCustomer());
		}
		return null;
	}


}
