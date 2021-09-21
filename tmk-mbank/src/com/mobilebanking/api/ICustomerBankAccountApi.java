/**
 * 
 */
package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;

/**
 * @author bibek
 *
 */
public interface ICustomerBankAccountApi {
	
	void saveCustomerBankAccount(CustomerBankAccountDTO customerBankAccountDto);
	
	CustomerBankAccountDTO findCustomerBankAccountById(long id);
	
	List<CustomerBankAccountDTO> findCustomerBankAccountByCustomer(String uniqueId);
	
	CustomerBankAccountDTO findCustomerBankAccountOfParticularBank(long bankId, long branchId);
	
	List<CustomerBankAccountDTO> findCustomerAllAccountsOfParticularBank(long bankId, long branchId);
	
	List<CustomerBankAccountDTO> findCustomerBankAccounts(String uniqueId);

	List<CustomerBankAccountDTO> findCustomerAllAccountsByBranchAndCustomer(long branchId, String uniqueId);

	List<CustomerBankAccountDTO> findCustomerAllAccountsByBank(long bankId);
	
	CustomerBankAccountDTO findCustomerBankAccountByAccountNo(String accountNumber, long customerId);
	List<CustomerBankAccountDTO> findCustomerAllAccountsByBankAndCustomer(long bankId, String uniqueId);


	CustomerBankAccountDTO findCustomerBankAccountByAccountNumberAndBank(String string,long bankId);

	List<CustomerBankAccountDTO> findCustomerBankAccountByCustomer(String uniqueId, String clientId);

	List<CustomerBankAccountDTO> findAllAccounts();

	CustomerDTO findCustomerByBankAccountNumberAndBank(String accountNumber, long bankId);
}
