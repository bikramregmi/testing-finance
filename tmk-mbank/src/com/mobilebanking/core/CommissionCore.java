package com.mobilebanking.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.Ledger;
import com.mobilebanking.entity.Merchant;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.model.AccountType;
import com.mobilebanking.model.LedgerStatus;
import com.mobilebanking.model.LedgerType;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.LedgerRepository;
import com.mobilebanking.util.StringConstants;

@Service
public class CommissionCore {

	@Autowired
	private TransactionCore transactionCore;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private LedgerRepository ledgerRepository;
//	private Bank
	public void pollAndAllocateCommission(Transaction transaction) throws Exception {
		
		Double totalCommission = transaction.getTotalCommission();
		Account account = accountRepository.findAccountByAccountHeadAndUserType(StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		account.setBalance(totalCommission+account.getBalance()	);
		account = accountRepository.save(account);
		
		ledgerPostingForPoolAccount(
				transaction.getMerchantManager().getMerchantsAndServices().getMerchantService(), 
				account, totalCommission, transaction.getTransactionIdentifier());
		Account bankAccount = accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
		if (bankAccount.getBalance() != null) {
			bankAccount.setBalance(bankAccount.getBalance() - totalCommission);
		}
		ledgerPostingGeneral(bankAccount, account, LedgerType.COMMISSION,transaction.getTransactionIdentifier(), totalCommission);
	}
	
	public void allocateCommissionToBank(Transaction transaction, Bank bank) {
		Double totalCommission = transaction.getTotalCommission();
		Double bankCommission = transaction.getBankCommissionAmount();
		String accountNumber = bank.getAccountNumber();
		Account toAccount = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		Account fromAccount = accountRepository.findAccountByAccountHeadAndUserType(StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		double toAccountBalance = toAccount.getBalance();
		double fromAccountBalance = fromAccount.getBalance();
		toAccount.setBalance(toAccountBalance  + bankCommission);
		fromAccount.setBalance(fromAccountBalance - bankCommission);
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		ledgerPostingForCommissionDistribution(fromAccount, toAccount, transaction.getBankCommissionAmount(), transaction.getTransactionIdentifier());
		
	}
	
	public void ledgerPostingForPoolAccount(MerchantService merchantService, Account poolAccount, double totalCommission, String transactionIdentifier) throws Exception {
		Ledger ledger = new Ledger();
		String remarks = StringConstants.COMMISSION_TO_POOL;
		remarks = remarks.replace("{service}", merchantService.getService());
		remarks = remarks.replace("{poolAccount}", poolAccount.getAccountHead());
		ledger.setAmount(totalCommission);
		ledger.setFromAccount(poolAccount);
		ledger.setRemarks(remarks);
		ledger.setTransactionId(transactionIdentifier);
		ledger.setCredit(true);
		ledger.setStatus(LedgerStatus.PENDING);
		ledger.setType(LedgerType.COMMISSION);
		ledger.setFromBalance(poolAccount.getBalance());
		
		ledger = ledgerRepository.save(ledger);
		if (ledger != null) {
			ledger.setStatus(LedgerStatus.COMPLETE);
			ledgerRepository.save(ledger);
		}
	}
	
	public void ledgerPostingForCommissionDistribution(Account poolAccount, Account toAccount, double amount, String transactionIdentifier) {
		Ledger ledger = new Ledger();
		String remarks = StringConstants.LEDGER_REMARKS;
		remarks = remarks.replace("ACCOUNT_ONE", poolAccount.getAccountHead());
		remarks = remarks.replace("ACCOUNT_TWO", toAccount.getAccountHead());
		ledger.setAmount(amount);
		ledger.setRemarks(remarks);
		ledger.setFromAccount(poolAccount);
		ledger.setToAccount(toAccount);
		ledger.setTransactionId(transactionIdentifier);
		ledger.setCredit(false);
		ledger.setStatus(LedgerStatus.PENDING);
		ledger.setType(LedgerType.COMMISSION);
		ledger = ledgerRepository.save(ledger);
		if (ledger != null) {
			ledger.setStatus(LedgerStatus.COMPLETE);
			ledgerRepository.save(ledger);
		}
	}
	
	public void updateBalanceToBank(Transaction transaction) {
		Bank bank = transaction.getBank();
		Merchant merchant = transaction.getMerchantManager().getMerchantsAndServices().getMerchant();
		Account merchantAccount = accountRepository.findAccountByAccountNumber(merchant.getAccountNumber());
		//now debit the merchant account and credit bank account
		if (merchantAccount != null) {
			if (merchantAccount.getBalance() != null) {
				merchantAccount.setBalance(merchantAccount.getBalance() - transaction.getAmount());
			} else {
				merchantAccount.setBalance((-1)*transaction.getAmount());
			}
			accountRepository.save(merchantAccount);
			Account bankAccount = accountRepository.findAccountOfBank(transaction.getBank().getId(),AccountType.BANK);
			if (bankAccount.getBalance() != null) {
				bankAccount.setBalance(bankAccount.getBalance() + transaction.getAmount());
			} else {
				bankAccount.setBalance(transaction.getAmount());
			}
			
			
			accountRepository.save(bankAccount);
			ledgerPostingGeneral(merchantAccount, bankAccount, LedgerType.TRANSFER, transaction.getTransactionIdentifier(), transaction.getAmount());
		}
		
		
	}
	
	public void transferBalanceToPool(Transaction transaction, Account fromAccount) {
		Account poolAccount = accountRepository.findAccountByAccountHeadAndUserType(
				StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		poolAccount.setBalance(poolAccount.getBalance() + transaction.getAmount());
		fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
		accountRepository.save(poolAccount);
		accountRepository.save(fromAccount);
		ledgerPostingGeneral(fromAccount, poolAccount, LedgerType.TRANSFER, transaction.getTransactionIdentifier(), transaction.getAmount());
		
	}
	
	public void transferBalanceFromPool(Transaction transaction, Account toAccount) {
		Account poolAccount = accountRepository.findAccountByAccountHeadAndUserType(
				StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		poolAccount.setBalance(poolAccount.getBalance() - transaction.getAmount());
		toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());
		accountRepository.save(poolAccount);
		accountRepository.save(toAccount);
		ledgerPostingGeneral(toAccount, poolAccount, LedgerType.TRANSFER, transaction.getTransactionIdentifier(), transaction.getAmount());
		
	}
	
	public void transferBalanceFromPoolToMerchant(Transaction transaction, Account toAccount, double amount) {
		Account poolAccount = accountRepository.findAccountByAccountHeadAndUserType(
				StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		poolAccount.setBalance(poolAccount.getBalance() - amount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		accountRepository.save(poolAccount);
		accountRepository.save(toAccount);
		ledgerPostingGeneral(toAccount, poolAccount, LedgerType.TRANSFER, transaction.getTransactionIdentifier(), transaction.getAmount());
	}

	public void transferBalanceFromMerchantToPool(Transaction transaction, Account fromAccount, double amount) {

		Account poolAccount = accountRepository.findAccountByAccountHeadAndUserType(
				StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		poolAccount.setBalance(poolAccount.getBalance() + transaction.getAmount());
		fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
		accountRepository.save(poolAccount);
		accountRepository.save(fromAccount);
		ledgerPostingGeneral(poolAccount, fromAccount, LedgerType.TRANSFER, transaction.getTransactionIdentifier(), transaction.getAmount());
	}
	public void allocateCommissions(Transaction transaction) {
		Bank bank = transaction.getBank();
		Account bankAccount = accountRepository.findAccountByAccountNumber(bank.getAccountNumber());
		Account poolAccount = accountRepository.findAccountByAccountHeadAndUserType(
				StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		Account commissionAccount = accountRepository.findAccountByAccountHeadAndUserType(
				StringConstants.POOL_COMMISSION_ACCOUNT, AccountType.COMMISSION_POOL);
		poolAccount.setBalance(poolAccount.getBalance() - transaction.getTotalCommission());
		commissionAccount.setBalance(commissionAccount.getBalance() + transaction.getTotalCommission());
		accountRepository.save(poolAccount);
		commissionAccount = accountRepository.save(commissionAccount);
		ledgerPostingGeneral(poolAccount, commissionAccount, LedgerType.COMMISSION, 
				transaction.getTransactionIdentifier(), transaction.getTotalCommission());
		//allocate to bank commission
		bankAccount.setBalance(bankAccount.getBalance() + transaction.getBankCommissionAmount());
		accountRepository.save(bankAccount);
		commissionAccount.setBalance(commissionAccount.getBalance() - transaction.getBankCommissionAmount());
		commissionAccount = accountRepository.save(commissionAccount);
		String channelPartnerAccountNumber = bank.getChannelPartnerAccountNumber();
		if (channelPartnerAccountNumber != null) {
			Account channelPartnerAccount = accountRepository.findAccountByAccountNumber(channelPartnerAccountNumber);
			channelPartnerAccount.setBalance(channelPartnerAccount.getBalance() + transaction.getChannelPartnerCommissionAmount());;
			commissionAccount.setBalance(commissionAccount.getBalance() - transaction.getChannelPartnerCommissionAmount());
			commissionAccount = accountRepository.save(commissionAccount);
			accountRepository.save(channelPartnerAccount);
			ledgerPostingGeneral(commissionAccount, channelPartnerAccount, LedgerType.COMMISSION, 
					transaction.getTransactionIdentifier(), transaction.getChannelPartnerCommissionAmount());
		} else {
			if (transaction.getBank().getChannelPartner() != null) {
				Account channelPartnerAccount = accountRepository.findAccountByAccountNumber(
						transaction.getBank().getChannelPartner().getAccountNumber());
				if (channelPartnerAccount != null) {
					channelPartnerAccount.setBalance(channelPartnerAccount.getBalance() + transaction.getChannelPartnerCommissionAmount());
					commissionAccount.setBalance(commissionAccount.getBalance() - transaction.getChannelPartnerCommissionAmount());
					ledgerPostingGeneral(commissionAccount, channelPartnerAccount, LedgerType.COMMISSION, 
							transaction.getTransactionIdentifier(), transaction.getChannelPartnerCommissionAmount());
					commissionAccount.setBalance(commissionAccount.getBalance() - transaction.getChannelPartnerCommissionAmount());
					commissionAccount = accountRepository.save(commissionAccount);
					accountRepository.save(channelPartnerAccount);
					ledgerPostingGeneral(commissionAccount, channelPartnerAccount, LedgerType.COMMISSION, 
							transaction.getTransactionIdentifier(), transaction.getChannelPartnerCommissionAmount());
					
				}

			}
			
		}
				
		//allocate to bank's operator
		String operatorAccountNumber = bank.getOperatorAccountNumber();
		if (operatorAccountNumber != null) {
			Account operatorAccount = accountRepository.findAccountByAccountNumber(operatorAccountNumber);
			operatorAccount.setBalance(operatorAccount.getBalance() + transaction.getOperatorCommissionAmount());
			commissionAccount.setBalance(commissionAccount.getBalance() - transaction.getOperatorCommissionAmount());
			commissionAccount = accountRepository.save(commissionAccount);
			accountRepository.save(operatorAccount);
			ledgerPostingGeneral(commissionAccount, operatorAccount, LedgerType.COMMISSION, 
					transaction.getTransactionIdentifier(), transaction.getOperatorCommissionAmount());
		} else {
			Account operatorAccount = accountRepository.findAccountByAccountNumber(StringConstants.OPERATOR_DEFAULT_ACCOUNT_NUMBER);
			if (operatorAccount != null) {
				operatorAccount.setBalance(operatorAccount.getBalance() + transaction.getOperatorCommissionAmount());
				commissionAccount.setBalance(commissionAccount.getBalance() - transaction.getOperatorCommissionAmount());
				commissionAccount = accountRepository.save(commissionAccount);
				accountRepository.save(operatorAccount);
				ledgerPostingGeneral(commissionAccount, operatorAccount, LedgerType.COMMISSION, 
						transaction.getTransactionIdentifier(), transaction.getOperatorCommissionAmount());
			}
			
		}
	
	}
	
	public void reverseAllocatedCommission(Transaction transaction) {
		
		Account bankAccount = accountRepository.findAccountByAccountNumber(transaction.getBank().getAccountNumber());
		Account poolAccount = accountRepository.findAccountByAccountHeadAndUserType(
				StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		
		String channelPartnerAccountNumber = transaction.getBank().getChannelPartnerAccountNumber();
		if (channelPartnerAccountNumber != null) {
			if(transaction.getChannelPartnerCommissionAmount()>0.0){
		Account channelPartnerAccount = accountRepository.findAccountByAccountNumber(channelPartnerAccountNumber);
		channelPartnerAccount.setBalance(channelPartnerAccount.getBalance() - transaction.getChannelPartnerCommissionAmount());;
		poolAccount.setBalance(poolAccount.getBalance() + transaction.getChannelPartnerCommissionAmount());
		poolAccount = accountRepository.save(poolAccount);
		accountRepository.save(channelPartnerAccount);
		ledgerPostingGeneral(poolAccount, channelPartnerAccount, LedgerType.REVERSAL, 
				transaction.getTransactionIdentifier(), transaction.getChannelPartnerCommissionAmount());
			}
			}
		
		
		String operatorAccountNumber = transaction.getBank().getOperatorAccountNumber();
		
		if (operatorAccountNumber != null) {
			if(transaction.getOperatorCommissionAmount()>0.0){
			Account operatorAccount = accountRepository.findAccountByAccountNumber(operatorAccountNumber);
			operatorAccount.setBalance(operatorAccount.getBalance() - transaction.getOperatorCommissionAmount());
			poolAccount.setBalance(poolAccount.getBalance() + transaction.getOperatorCommissionAmount());
			poolAccount = accountRepository.save(poolAccount);
			accountRepository.save(operatorAccount);
			ledgerPostingGeneral(poolAccount, operatorAccount, LedgerType.REVERSAL, 
					transaction.getTransactionIdentifier(), transaction.getOperatorCommissionAmount());
			}
			}
		
		//allocate to bank commission
		if(transaction.getBankCommissionAmount()>0.0){
				bankAccount.setBalance(bankAccount.getBalance() - transaction.getBankCommissionAmount());
				accountRepository.save(bankAccount);
				poolAccount.setBalance(poolAccount.getBalance() + transaction.getBankCommissionAmount());
				poolAccount = accountRepository.save(poolAccount);
				ledgerPostingGeneral(poolAccount, bankAccount, LedgerType.REVERSAL, 
						transaction.getTransactionIdentifier(), transaction.getOperatorCommissionAmount());
		}
	}

	
	public void ledgerPostingGeneral(Account fromAccount, Account toAccount, LedgerType ledgerType, String transactionIdentifier, double amount) {
		Ledger ledger = new Ledger();
		String remarks = StringConstants.LEDGER_REMARKS;
		remarks = remarks.replace("ACCOUNT_ONE", fromAccount.getAccountHead());
		remarks = remarks.replace("ACCOUNT_TWO", toAccount.getAccountHead());
		ledger.setAmount(amount);
		ledger.setRemarks(remarks);
		ledger.setFromAccount(fromAccount);
		ledger.setToAccount(toAccount);
		ledger.setTransactionId(transactionIdentifier);
		ledger.setStatus(LedgerStatus.PENDING);
		ledger.setType(ledgerType);
		ledger = ledgerRepository.save(ledger);
		if (ledger != null) {
			ledger.setStatus(LedgerStatus.COMPLETE);
			ledgerRepository.save(ledger);
		}
		
	}


	
}
