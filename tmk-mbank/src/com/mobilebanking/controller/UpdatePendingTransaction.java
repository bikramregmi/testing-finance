package com.mobilebanking.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.core.CommissionCore;
import com.mobilebanking.core.TransactionCore;
import com.mobilebanking.repositories.AccountPostingRepository;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.TransactionRepository;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.SendEmail;

@Controller
public class UpdatePendingTransaction {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionCore transactionCore;
	
	@Autowired
	private CommissionCore commissionCore;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountPostingRepository accountPostingRepository;
	
	@Autowired
	SendEmail sendEmail;
	
	@RequestMapping(method = RequestMethod.GET, value="/mailTest")
	public String updatePendingTransaction() throws ClientException, MessagingException{
	
		sendEmail.sendMail("sameer.khanal545@gmail.com", 500.0, "navajeevan coop");
		
		return "no idea";
		
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value="/pending")
	public String updatePendingTransaction(@RequestParam("id") String identifier) throws ClientException{
		Transaction transaction = transactionRepository.getTransactionByTransactionIdentifier(identifier);
		MerchantManager manager = transaction.getMerchantManager();
		Double amount = transaction.getAmount();
		Long id = transaction.getId();
		String sourceAccountNumber="";
		String destinationAccountNumber = "";
		HashMap<String, String> hashResponse = new HashMap<>();
		hashResponse.put("status", "success");
		hashResponse.put("Key", "" + "d8899078-8364-45b5-b6b4-b325cdc0923c");
		hashResponse.put("Result Message", "" + "Operation is succesfully completed");
		createAccountPosting(sourceAccountNumber, destinationAccountNumber,
				transaction.getAmount() - transaction.getTotalCommission(), transaction);
		String txnIdentifier = transactionCore.transactionParkingToMerchant(id, manager, hashResponse);
		transactionCore.ledgerParkingToMerchant(amount - transaction.getTotalCommission(), manager,
				txnIdentifier);
		// transactionCore.accountParkingToService(amount, service);
		// commissionCore.updateBalanceToBank(transaction);
		Account fromAccount = accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
		commissionCore.transferBalanceFromPoolToMerchant(transaction, fromAccount,
				amount - transaction.getTotalCommission());
		commissionCore.allocateCommissions(transaction);
			

		return "redirect:/transaction/report/financial";
	}
	
	public void createAccountPosting(String fromAccountNumber, String toAccountNumber, double amount,
			Transaction transaction) {
		String remarks = StringConstants.ACCOUNT_POSTING_FUND_TRANSFER;
		remarks = remarks.replace("{amount}", "" + amount);
		remarks = remarks.replace("{accountNumber1}", fromAccountNumber);
		remarks = remarks.replace("{accountNumber2}", toAccountNumber);
		AccountPosting accountPosting = new AccountPosting();
		accountPosting.setAmount(amount);
		accountPosting.setFromAccountNumber(fromAccountNumber);
		accountPosting.setToAccountNumber(toAccountNumber);
		accountPosting.setRemarks(remarks);
		accountPosting.setTransaction(transaction);
		accountPostingRepository.save(accountPosting);

	}*/

}
