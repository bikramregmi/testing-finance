package com.mobilebanking.api.impl;
/*package com.wallet.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IAccountApi;
import com.wallet.api.IConfirmationCodeApi;
import com.wallet.api.ILedgerApi;
import com.wallet.api.ITransactionApi;
import com.wallet.api.ITransactionMetaApi;
import com.wallet.entity.Account;
import com.wallet.entity.Agent;
import com.wallet.entity.ConfirmationCode;
import com.wallet.entity.Customer;
import com.wallet.entity.FinancialInstitution;
import com.wallet.entity.Transaction;
import com.wallet.entity.User;
import com.wallet.model.ConfirmationCodeDTO;
import com.wallet.model.ConfirmationCodeType;
import com.wallet.model.LedgerDTO;
import com.wallet.model.LedgerStatus;
import com.wallet.model.LedgerType;
import com.wallet.model.TransactionDTO;
import com.wallet.model.TransactionStatus;
import com.wallet.model.TransactionType;
import com.wallet.repositories.AccountRepository;
import com.wallet.repositories.AgentRepository;
import com.wallet.repositories.ConfirmationCodeRepository;
import com.wallet.repositories.CustomerRepository;
import com.wallet.repositories.FinancialInstitutionRepository;
import com.wallet.repositories.UserRepository;
import com.wallet.util.AuthenticationUtil;
import com.wallet.util.ClientException;
import com.wallet.util.ConvertUtil;

@Service
public class ConfirmationCodeApi implements IConfirmationCodeApi {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConfirmationCodeRepository confirmationCodeRepository;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserRepository userRepository;

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
	private ITransactionMetaApi transactionMetaApi;

	@Override
	public ConfirmationCodeDTO createConfirmationCode(ConfirmationCodeType casType, String generatedTo,
			double cicoAmount) {

		Agent agent = agentRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
		Customer customer = customerRepository.findOne(userRepository.findByUsername(generatedTo).getAssociatedId());
		ConfirmationCode confirmationCode = new ConfirmationCode();
		confirmationCode.setCasType(casType);
		confirmationCode.setCicoAmount(cicoAmount);
		confirmationCode.setCode(generateCode());
		confirmationCode.setGeneratedTo(customer);
		confirmationCode.setGenerator(agent);
		confirmationCode.setValid(true);
		confirmationCode = confirmationCodeRepository.save(confirmationCode);
		return ConvertUtil.convertConfirmationCodeToDTO(confirmationCode);
	}

	private String generateCode() {

		boolean valid = true;
		String code = "";
		while (valid) {

			String st = getRanCode(6);

			ConfirmationCode confirmationCode = confirmationCodeRepository.findByCode(st);

			if (confirmationCode == null) {
				valid = false;
				code = st;
			}
		}
		return code;
	}

	public static String getRanCode(int length) {

		String mystr = "abcdefghijklmnopqrstuvwxyz";
		String[] array = mystr.split("");
		String restr = "";
		for (int i = 0; i < length; i++) {
			int nm = (int) (Math.random() * mystr.length());
			restr += array[nm];
		}

		return restr;

	}

	@Override
	public boolean consumeConfirmation(ConfirmationCodeType casType, String generatedTo, double cicoAmount, long cid,
			String code) {

		boolean valid = false;
		Agent agent = agentRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
		Customer customer = customerRepository.findOne(userRepository.findByUsername(generatedTo).getAssociatedId());
		ConfirmationCode confirmationCode = confirmationCodeRepository.findByDetail(code, cid, customer, agent,
				cicoAmount, true);

		if (confirmationCode != null) {
			valid = true;
			if (confirmationCode.getCasType() == casType.CashIn) {
				
				long tid=cashInTransactionToPoolAccount(casType, generatedTo, cicoAmount);
				cashInLedgerToPoolAccount(cicoAmount, agent.getAccountNo());
				cashInAccountToPoolAccount(agent.getAccountNo(), cicoAmount);
				cashInTransactionToPoolAccountEdit(tid, customer.getAccountNo());
				cashInLedgerToPoolToCustomer(cicoAmount, customer.getAccountNo());
				cashInAccountToPoolTocustomer(customer.getAccountNo(), cicoAmount);
				confirmationCode.setValid(false);
				confirmationCodeRepository.save(confirmationCode);
				valid = true;
				
			} else if (confirmationCode.getCasType() == casType.CashOut) {
				long tid=cashOutTransactionToPoolAccount(casType, generatedTo, cicoAmount);
				cashOutLedgerToPoolAccount(cicoAmount, customer.getAccountNo());
				cashOutAccountToPoolAccount(customer.getAccountNo(), cicoAmount);
				cashOutTransactionToPoolAccountEdit(tid, agent.getAccountNo());
				cashOutLedgerToPoolToAgent( cicoAmount, agent.getAccountNo());
				cashOutAccountToPoolToAgent(agent.getAccountNo(),  cicoAmount);
				confirmationCode.setValid(false);
				confirmationCodeRepository.save(confirmationCode);
				valid = true;
			} else {
				valid = false;
			}
		}

		return valid;

	}

	private long cashInTransactionToPoolAccount(ConfirmationCodeType casType, String generatedTo, double cicoAmount) {
		TransactionDTO transactionDto = new TransactionDTO();

		User sourceUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
		User destinationUser = userRepository.findByUsername(generatedTo);
		Customer customer = customerRepository.findOne(destinationUser.getAssociatedId());
		Agent agent = agentRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());

		transactionDto.setDestinationAccount("parkingAccount");
		transactionDto.setDestinationUser(destinationUser.getId());
		transactionDto.setTransactionOwner(sourceUser.getId());
		transactionDto.setOriginatingUser(sourceUser.getId());
		transactionDto.setAmount(cicoAmount);
		transactionDto.setTransactionType(TransactionType.CashIn);
		transactionDto.setSourceAccount(agent.getAccountNo());
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		Transaction transaction = transactionApi.createTransaction(transactionDto);
		
		
		return transaction.getId();

	}

	private void cashInLedgerToPoolAccount(double amount, String destinationAccount) {

		LedgerDTO ledgetDto = new LedgerDTO();

		ledgetDto.setToAccount("parkingAccount");
		ledgetDto.setAmount(amount);
		ledgetDto.setFromAccount(destinationAccount);
		ledgetDto.setRemarks("Cash In");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.CASHIN);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cashInAccountToPoolAccount(String agentAccount, double amount) {

		Account fromAccount = accountApi.searchByAccountId(agentAccount);
		Account toAccount = accountApi.searchByAccountId("parkingAccount");
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);
	}
	
	
	private void cashInTransactionToPoolAccountEdit(long id, String destinationAccount) {
		TransactionDTO transactionDto = new TransactionDTO();
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		transactionDto.setId(id);
		transactionDto.setDestinationAccount(destinationAccount);
		transactionApi.editTransaction(transactionDto);
		
	}

	private void cashInLedgerToPoolToCustomer(double amount, String destinationAccount) {

		LedgerDTO ledgetDto = new LedgerDTO();

		ledgetDto.setToAccount(destinationAccount);
		ledgetDto.setAmount(amount);
		ledgetDto.setFromAccount("parkingAccount");
		ledgetDto.setRemarks("Cash In To Customer");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.CASHIN);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cashInAccountToPoolTocustomer(String customerAccount, double amount) {

		Account fromAccount = accountApi.searchByAccountId("parkingAccount");
		Account toAccount = accountApi.searchByAccountId(customerAccount);
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);
	}
	
	
	
	
	
	// cash out start here
	
	
	
	
	
	private long cashOutTransactionToPoolAccount(ConfirmationCodeType casType, String generatedTo, double cicoAmount) {
		TransactionDTO transactionDto = new TransactionDTO();

		User sourceUser = userRepository.findByUsername(generatedTo);
		User destinationUser = userRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());
		Customer customer = customerRepository.findOne(sourceUser.getAssociatedId());
		Agent agent = agentRepository.findOne(AuthenticationUtil.getCurrentUser().getAssociatedId());

		transactionDto.setDestinationAccount("parkingAccount");
		transactionDto.setDestinationUser(destinationUser.getId());
		transactionDto.setTransactionOwner(destinationUser.getId());
		transactionDto.setOriginatingUser(sourceUser.getId());
		transactionDto.setAmount(cicoAmount);
		transactionDto.setTransactionType(TransactionType.CashOut);
		transactionDto.setSourceAccount(agent.getAccountNo());
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		Transaction transaction = transactionApi.createTransaction(transactionDto);
		
		
		return transaction.getId();

	}

	private void cashOutLedgerToPoolAccount(double amount, String destinationAccount) {

		LedgerDTO ledgetDto = new LedgerDTO();
		ledgetDto.setToAccount("parkingAccount");
		ledgetDto.setAmount(amount);
		ledgetDto.setFromAccount(destinationAccount);
		ledgetDto.setRemarks("Cash Out");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.CASHOUT);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cashOutAccountToPoolAccount(String customerAccount, double amount) {

		Account fromAccount = accountApi.searchByAccountId(customerAccount);
		Account toAccount = accountApi.searchByAccountId("parkingAccount");
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);
	}
	
	
	private void cashOutTransactionToPoolAccountEdit(long id, String destinationAccount) {
		TransactionDTO transactionDto = new TransactionDTO();
		transactionDto.setTransactionStatus(TransactionStatus.Complete);
		transactionDto.setId(id);
		transactionDto.setDestinationAccount(destinationAccount);
		transactionApi.editTransaction(transactionDto);
		
	}

	private void cashOutLedgerToPoolToAgent(double amount, String agentAccount) {

		LedgerDTO ledgetDto = new LedgerDTO();

		ledgetDto.setToAccount(agentAccount);
		ledgetDto.setAmount(amount);
		ledgetDto.setFromAccount("parkingAccount");
		ledgetDto.setRemarks("Cash Out To Agent");
		ledgetDto.setTransactionStatus(LedgerStatus.COMPLETE);
		ledgetDto.setTransactionType(LedgerType.CASHIN);
		ledgetDto.setExternalRefId(0L);
		try {
			ledgetDto = ledgerApi.createLedger(ledgetDto);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cashOutAccountToPoolToAgent(String agentAccount, double amount) {

		Account fromAccount = accountApi.searchByAccountId("parkingAccount");
		Account toAccount = accountApi.searchByAccountId(agentAccount);
		fromAccount.setBalance(fromAccount.getBalance() - amount);
		accountRepository.save(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(toAccount);
	}
	
	

}
*/