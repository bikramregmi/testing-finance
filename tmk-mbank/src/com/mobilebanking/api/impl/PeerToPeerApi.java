package com.mobilebanking.api.impl;
/*package com.wallet.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IAccountApi;
import com.wallet.api.ILedgerApi;
import com.wallet.api.IPeerToPeerApi;
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
import com.wallet.repositories.UserRepository;
import com.wallet.util.Authorities;
import com.wallet.util.ClientException;

@Service
public class PeerToPeerApi implements IPeerToPeerApi {

	@Autowired
	private ITransactionApi transactionApi;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ITransactionMetaApi transactionMetaApi;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private ILedgerApi ledgerApi;

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private AccountRepository accountRepository;

	// @Override
	// public void peerToPeerTransfer(User fromUser, double amount, String
	// toUser) throws ClientException {
	//
	// User sourceUser = userRepository.findOne(fromUser.getId());
	// User destinationUser = userRepository.findByUsername(toUser);
	//
	// setTransaction(sourceUser, destinationUser, amount);
	// setLedger(sourceUser, destinationUser, amount);
	// setAccount(sourceUser, destinationUser, amount);
	// }

	@Override
	public void peerToPeerTransfer(User fromUser, double amount, String toUser, String owner) throws ClientException {

		User sourceUser = userRepository.findOne(fromUser.getId());
		User destinationUser = userRepository.findByUsername(toUser);
		User transactionOwner = userRepository.findByUsername(owner);

		if (sourceUser.getAuthority().contains(Authorities.CUSTOMER)
				&& destinationUser.getAuthority().contains(Authorities.CUSTOMER)) {
			setCustomerToCustomerTransaction(sourceUser, destinationUser, amount);
			setCustomerToCustomerLedger(sourceUser, destinationUser, amount);
			setCustomerToCustomerAccount(sourceUser, destinationUser, amount);
		} else if (sourceUser.getAuthority().contains(Authorities.AGENT)
				&& destinationUser.getAuthority().contains(Authorities.AGENT)) {
			setAgentToAgentTransaction(sourceUser, destinationUser, amount, transactionOwner);
			setAgentToAgentLedger(sourceUser, destinationUser, amount);
			setAgentToAgentAccount(sourceUser, destinationUser, amount);
		} else if (sourceUser.getAuthority().contains(Authorities.AGENT)
				&& destinationUser.getAuthority().contains(Authorities.CUSTOMER)) {
			setAgentToCustomerTransaction(sourceUser, destinationUser, amount, transactionOwner);
			setAgentToCustomerLedger(sourceUser, destinationUser, amount);
			setAgentToCustomerAccount(sourceUser, destinationUser, amount);
		}
	}

	public void setCustomerToCustomerTransaction(User sourceUser, User destinationUser, double amount) {

		TransactionDTO transactionDto = new TransactionDTO();

		Customer sourceCustomer = customerRepository.findOne(sourceUser.getAssociatedId());
		transactionDto.setSourceAccount(sourceCustomer.getAccountNo());

		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		transactionDto.setDestinationAccount(destinationCustomer.getAccountNo());

		transactionDto.setOriginatingUser(sourceUser.getId());
		transactionDto.setDestinationUser(destinationUser.getId());
		transactionDto.setTransactionOwner(sourceUser.getId());
		transactionDto.setAmount(amount);
		transactionDto.setTransactionType(TransactionType.Transfer);
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		Transaction transaction = transactionApi.createTransaction(transactionDto);
	
	}

	public void setCustomerToCustomerLedger(User sourceUser, User destinationUser, double amount) {
		LedgerDTO ledgetDto = new LedgerDTO();

		Customer sourceCustomer = customerRepository.findOne(sourceUser.getAssociatedId());
		ledgetDto.setFromAccount(sourceCustomer.getAccountNo());

		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		ledgetDto.setToAccount(destinationCustomer.getAccountNo());

		ledgetDto.setAmount(amount);
		ledgetDto.setRemarks("Peer To Peer Transfer");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.TRANSFER);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	public void setCustomerToCustomerAccount(User sourceUser, User destinationUser, double amount) {

		Customer sourceCustomer = customerRepository.findOne(sourceUser.getAssociatedId());
		Account fromAccount = accountApi.searchByAccountId(sourceCustomer.getAccountNo());

		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		Account toAccount = accountApi.searchByAccountId(destinationCustomer.getAccountNo());

		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);

	}

	public void setAgentToAgentTransaction(User sourceUser, User destinationUser, double amount,
			User transactionOwner) {

		TransactionDTO transactionDto = new TransactionDTO();

		Agent sourceAgent = agentRepository.findOne(sourceUser.getAssociatedId());
		transactionDto.setSourceAccount(sourceAgent.getAccountNo());

		Agent destinationAgent = agentRepository.findOne(destinationUser.getAssociatedId());
		transactionDto.setDestinationAccount(destinationAgent.getAccountNo());

		transactionDto.setOriginatingUser(sourceUser.getId());
		transactionDto.setDestinationUser(destinationUser.getId());
		transactionDto.setTransactionOwner(transactionOwner.getId());
		transactionDto.setAmount(amount);
		transactionDto.setTransactionType(TransactionType.Transfer);
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		Transaction transaction = transactionApi.createTransaction(transactionDto);
	
	}

	public void setAgentToAgentLedger(User sourceUser, User destinationUser, double amount) {
		LedgerDTO ledgetDto = new LedgerDTO();

		Agent sourceAgent = agentRepository.findOne(sourceUser.getAssociatedId());
		ledgetDto.setFromAccount(sourceAgent.getAccountNo());

		Agent destinationAgent = agentRepository.findOne(destinationUser.getAssociatedId());
		ledgetDto.setToAccount(destinationAgent.getAccountNo());

		ledgetDto.setAmount(amount);
		ledgetDto.setRemarks("Peer To Peer Transfer");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.TRANSFER);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAgentToAgentAccount(User sourceUser, User destinationUser, double amount) {

		Agent sourceAgent = agentRepository.findOne(sourceUser.getAssociatedId());
		Account fromAccount = accountApi.searchByAccountId(sourceAgent.getAccountNo());

		Agent destinationAgent = agentRepository.findOne(destinationUser.getAssociatedId());
		Account toAccount = accountApi.searchByAccountId(destinationAgent.getAccountNo());

		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);

	}

	public void setAgentToCustomerTransaction(User sourceUser, User destinationUser, double amount,
			User transactionOwner) {

		TransactionDTO transactionDto = new TransactionDTO();

		Agent sourceAgent = agentRepository.findOne(sourceUser.getAssociatedId());
		transactionDto.setSourceAccount(sourceAgent.getAccountNo());

		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		transactionDto.setDestinationAccount(destinationCustomer.getAccountNo());

		transactionDto.setOriginatingUser(sourceUser.getId());
		transactionDto.setDestinationUser(destinationUser.getId());
		transactionDto.setTransactionOwner(transactionOwner.getId());
		transactionDto.setAmount(amount);
		transactionDto.setTransactionType(TransactionType.Transfer);
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		Transaction transaction = transactionApi.createTransaction(transactionDto);
		
	}

	public void setAgentToCustomerLedger(User sourceUser, User destinationUser, double amount) {
		LedgerDTO ledgetDto = new LedgerDTO();

		Agent sourceAgent = agentRepository.findOne(sourceUser.getAssociatedId());
		ledgetDto.setFromAccount(sourceAgent.getAccountNo());

		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		ledgetDto.setToAccount(destinationCustomer.getAccountNo());

		ledgetDto.setAmount(amount);
		ledgetDto.setRemarks("Peer To Peer Transfer");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.TRANSFER);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAgentToCustomerAccount(User sourceUser, User destinationUser, double amount) {

		Agent sourceAgent = agentRepository.findOne(sourceUser.getAssociatedId());
		Account fromAccount = accountApi.searchByAccountId(sourceAgent.getAccountNo());

		Customer destinationCustomer = customerRepository.findOne(destinationUser.getAssociatedId());
		Account toAccount = accountApi.searchByAccountId(destinationCustomer.getAccountNo());

		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);

	}
	
	


}
*/