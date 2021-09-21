package com.mobilebanking.api.impl;
/*package com.wallet.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IAccountApi;
import com.wallet.api.ILedgerApi;
import com.wallet.api.ILoadFundApi;
import com.wallet.api.ITransactionApi;
import com.wallet.api.ITransactionMetaApi;
import com.wallet.entity.Account;
import com.wallet.entity.Agent;
import com.wallet.entity.Customer;
import com.wallet.entity.FinancialInstitution;
import com.wallet.entity.Transaction;
import com.wallet.entity.User;
import com.wallet.model.LedgerDTO;
import com.wallet.model.LedgerStatus;
import com.wallet.model.LedgerType;
import com.wallet.model.TransactionDTO;
import com.wallet.model.TransactionStatus;
import com.wallet.model.TransactionType;
import com.wallet.repositories.AccountRepository;
import com.wallet.repositories.AgentRepository;
import com.wallet.repositories.CustomerRepository;
import com.wallet.repositories.FinancialInstitutionRepository;
import com.wallet.repositories.UserRepository;
import com.wallet.util.Authorities;
import com.wallet.util.ClientException;

@Service
public class LoadFundApi implements ILoadFundApi {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private ILedgerApi ledgerApi;

	@Autowired
	private FinancialInstitutionRepository financialInstitutionRepository;

	@Autowired
	private ITransactionApi transactionApi;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private ITransactionMetaApi transactionMetaApi;

	@Override
	public void loadFund(User toUser, String financialInstitution, double amount) throws ClientException {

		User destinationUser = userRepository.findOne(toUser.getId());
		FinancialInstitution financialIns = financialInstitutionRepository.findByName(financialInstitution);

		if (destinationUser.getAuthority().contains(Authorities.CUSTOMER)) {
			setCustomerTransaction(destinationUser, financialIns, amount);
			setCustomerLedger(destinationUser, financialIns, amount);
			setCustomerAccount(destinationUser, financialIns, amount);
		} else if (destinationUser.getAuthority().contains(Authorities.AGENT)) {
			setAgentTransaction(destinationUser, financialIns, amount);
			setAgentLedger(destinationUser, financialIns, amount);
			setAgentAccount(destinationUser, financialIns, amount);
		}

	}

	private void setCustomerTransaction(User destinationUser, FinancialInstitution financialIns, double amount) {
		TransactionDTO transactionDto = new TransactionDTO();

		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		transactionDto.setDestinationAccount(destinationCustomer.getAccountNo());

		transactionDto.setDestinationUser(destinationUser.getId());
		transactionDto.setTransactionOwner(destinationUser.getId());
		transactionDto.setAmount(amount);
		transactionDto.setTransactionType(TransactionType.Load);
		transactionDto.setSourceAccount(financialIns.getAccountNo());
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		Transaction transaction = transactionApi.createTransaction(transactionDto);
	

	}

	private void setCustomerLedger(User destinationUser, FinancialInstitution financialIns, double amount) {
		LedgerDTO ledgetDto = new LedgerDTO();
		
		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		ledgetDto.setToAccount(destinationCustomer.getAccountNo());
		
		ledgetDto.setAmount(amount);
		ledgetDto.setFromAccount(financialIns.getAccountNo());
		ledgetDto.setRemarks("Load Fund");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.LOADFUND);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setCustomerAccount(User destinationUser, FinancialInstitution financialIns, double amount) {
		Account fromAccount = accountApi.searchByAccountId(financialIns.getAccountNo());
		
		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		Account toAccount = accountApi.searchByAccountId(destinationCustomer.getAccountNo());
		
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);
	}
	
	private void setAgentTransaction(User destinationUser, FinancialInstitution financialIns, double amount) {
		TransactionDTO transactionDto = new TransactionDTO();

		Agent destinationAgent = agentRepository.findOne(destinationUser.getAssociatedId());
		transactionDto.setDestinationAccount(destinationAgent.getAccountNo());

		transactionDto.setDestinationUser(destinationUser.getId());
		transactionDto.setTransactionOwner(destinationUser.getId());
		transactionDto.setAmount(amount);
		transactionDto.setTransactionType(TransactionType.Load);
		transactionDto.setSourceAccount(financialIns.getAccountNo());
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		Transaction transaction = transactionApi.createTransaction(transactionDto);
		transactionMetaApi.createTransactionMeta(transaction);

	}

	private void setAgentLedger(User destinationUser, FinancialInstitution financialIns, double amount) {
		LedgerDTO ledgetDto = new LedgerDTO();
		
		Agent destinationAgent = agentRepository.findOne(destinationUser.getAssociatedId());
		ledgetDto.setToAccount(destinationAgent.getAccountNo());
		
		ledgetDto.setAmount(amount);
		ledgetDto.setFromAccount(financialIns.getAccountNo());
		ledgetDto.setRemarks("Load Fund");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.LOADFUND);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setAgentAccount(User destinationUser, FinancialInstitution financialIns, double amount) {
		Account fromAccount = accountApi.searchByAccountId(financialIns.getAccountNo());
		
		Agent destinationAgent = agentRepository.findOne(destinationUser.getAssociatedId());
		Account toAccount = accountApi.searchByAccountId(destinationAgent.getAccountNo());
		
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);
	}

}
*/