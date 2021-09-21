package com.mobilebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.UserRepository;

@Controller
public class MbankUpdateController {
	
	@Autowired CustomerRepository customerRepository;
	
	@Autowired BankRepository bankRepository;
	
	@Autowired CustomerBankAccountRepository customerBankAccountRepository;
	
	@Autowired UserRepository userRepository;
	
	@Autowired BankOAuthClientRepository bankAuthRepository;
	
	@Autowired private BankBranchRepository bankBranchRepository;
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/updateCustomerNavajeevan")
	public String reportCommissionTransaction(RedirectAttributes redirectAttributes, ModelMap modelMap, Model model
		) {
				List<TransactionDTO> filteredTransaction = (List<TransactionDTO>) model.asMap().get("transactionList");
				
				List<Customer> customerList = customerRepository.findAll();
				
//				Bank bank = bankRepository.findOne((long) 1);
				
				for(Customer customer : customerList){
					CustomerBankAccount customerBankAccount = customerBankAccountRepository.findPrimaryAccount(customer,true);
				if(customerBankAccount!=null){
					customer.setBank(customerBankAccount.getBranch().getBank());
					customer.setBankBranch(customerBankAccount.getBranch());
					customerRepository.save(customer);
				}else{
					List<CustomerBankAccount> customerBankAccountList = customerBankAccountRepository.findCustomerAccountByMobileNumber(customer.getMobileNumber());
//					customer.setBank(customerBankAccountList.get(0).getBranch().getBank());
//					customer.setBankBranch(customerBankAccountList.get(0).getBranch());
//					customerRepository.save(customer);
					if(customerBankAccountList!=null && !(customerBankAccountList.isEmpty())){
						customer.setBank(customerBankAccountList.get(0).getBranch().getBank());
						customer.setBankBranch(customerBankAccountList.get(0).getBranch());
						customerRepository.save(customer);
					}else{
						List<BankBranch> branchList = bankBranchRepository.findBankBranchByBank(1L);
						customer.setBank(branchList.get(0).getBank());
						customer.setBankBranch(branchList.get(0));
						customerRepository.save(customer);
					}
				}
				}
				return "mbank/success";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/updateUserNavajeevan")
	public String updateUser(RedirectAttributes redirectAttributes, ModelMap modelMap, Model model
		) {
				List<TransactionDTO> filteredTransaction = (List<TransactionDTO>) model.asMap().get("transactionList");
				
				List<Customer> customerList = customerRepository.findAll();
				
//				Bank bank = bankRepository.findOne((long) 1);
				
				for(Customer customer : customerList){
					BankOAuthClient bankAouthClient = bankAuthRepository.findByBank(customer.getBank());
					String clientID = bankAouthClient.getoAuthClientId();
					User user = userRepository.findByUsername(customer.getMobileNumber());
					user.setSmsUserName(customer.getMobileNumber());
					user.setUserName(clientID+customer.getMobileNumber());
					user.setBank(customer.getBank());
					userRepository.save(user);
				}
				
				return "mbank/success";
	
	}*/
}
