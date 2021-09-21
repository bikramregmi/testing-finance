package com.cas.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import com.cas.entity.Account;
import com.cas.entity.Transaction;
import com.cas.model.AccountDTO;
import com.cas.model.StatementDTO;
import com.cas.model.TransactionDTO;
import com.cas.model.TransactionType;


public class ConvertUtil implements MessageSourceAware {
	private static Logger logger = LoggerFactory.getLogger(ConvertUtil.class);
	
	@Autowired
	private static MessageSource messageSource;

	private ConvertUtil() {
	}

	public static List<AccountDTO> convertAccountList(List<Account> account) {
		List<AccountDTO> dtoList = new ArrayList<AccountDTO>();

		for (Account acc : account) {
			dtoList.add(convertAccount(acc));
		}
		return dtoList;
	}

	public static AccountDTO convertAccount(Account account) {
		AccountDTO dto = new AccountDTO();
		dto.setAccountNo(account.getAccountNo());
		dto.setBalance(account.getBalance());
		dto.setCountry(account.getCountry().getCountryCode());
		dto.setCurrency(account.getCountry().getCurrency());
		return dto;
	}

//	public static StatementDTO convertTransactionToStatement(
//			List<Transaction> txn,Account account) throws Exception {
//		StatementDTO dto=new StatementDTO();
//		Map<String,String> statementList=new HashMap();
//		for(Transaction t:txn){
//			System.out.println(t.getTransactionId());
//			String statement=null;
//			if(t.getType().equals(TransactionType.Transfer)){
//				if(t.getFromAccount().getAccountNo().equals(account.getAccountNo())){
//						statement="You Have Transfered "+ account.getCountry().getCurrency()+"."+t.getSentAmount()+" From Your Account.";
//				}
//				else{
//					statement=account.getCountry().getCurrency()+"."+t.getReceivedAmount()+" Has Been Transfered To Your Account.";
//				}
//			}
//			else if(t.getType().equals(TransactionType.Load)){
//				statement=account.getCountry().getCurrency()+"."+t.getReceivedAmount()+" Amount Has Been Loaded To Your Account.";
//			}
//			else{
//				throw new Exception("Error In Account Statement");
//			}
//			statementList.put(Long.toString(t.getTransactionId()),statement);
//		}
//		dto.setStatement(statementList);
//		return dto;
//	}
	

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public static List<TransactionDTO> convertTransactionListToDTO(List<Transaction> txnList){
		List<TransactionDTO> dtoList=new ArrayList<TransactionDTO>();
		for(Transaction txn:txnList){
			dtoList.add(convertTransactionToDTO(txn));
		}
		return dtoList;
	}

	public static TransactionDTO convertTransactionToDTO(Transaction txn) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		TransactionDTO dto=new TransactionDTO();
		dto.setExchangeRate(txn.getExchangeRate());
		dto.setReceivedAmount(txn.getReceivedAmount());
		dto.setRemarks(txn.getRemarks());
		dto.setSentAmount(txn.getSentAmount());
		dto.setTransactionId(Long.toString(txn.getTransactionId()));
		dto.setTransactionStatus(txn.getStatus());
		dto.setTransactionType(txn.getType());
		if(txn.getFromAccount()!=null){
			dto.setFromAccount(txn.getFromAccount().getAccountNo());
			dto.setFromCurrency(txn.getFromAccount().getCountry().getCurrency());
		}
		if(txn.getToAccount()!=null){
			dto.setToAccount(txn.getToAccount().getAccountNo());
			dto.setToCurrency(txn.getToAccount().getCountry().getCurrency());
		}
		dto.setDate(dateFormatter.format(txn.getCreated()));
		return dto;
	}
}
