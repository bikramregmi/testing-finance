package com.cas.validation;

import com.cas.api.IAccountApi;
import com.cas.api.ITransactionApi;
import com.cas.entity.Account;
import com.cas.entity.Transaction;
import com.cas.model.LoadFundValidationDTO;
import com.cas.model.TransactionDTO;
import com.cas.model.TransactionStatus;
import com.cas.model.TransactionType;
import com.cas.model.FundTransferValidationDTO;


public class TransactionValidation {
	
	private IAccountApi accountApi;
	private ITransactionApi transactionApi;
	
	public TransactionValidation(IAccountApi accountApi,ITransactionApi transactionApi){
		this.accountApi=accountApi;
		this.transactionApi=transactionApi;
	}

	public FundTransferValidationDTO FundTransferValidation(TransactionDTO dto){
		
		FundTransferValidationDTO error=new FundTransferValidationDTO();
		boolean valid=true;
		
		
		Account fromAccount=accountApi.searchByAccountId(dto.getFromAccount());
		Account toAccount=accountApi.searchByAccountId(dto.getToAccount());
		if(dto.getTransactionId()==null || dto.getTransactionId().isEmpty()){
			error.setTransactionId("Invalid Transaction Id");
			valid=false;
		}
		else{
			try{
				long txnId=Long.parseLong(dto.getTransactionId());
				Transaction txn=transactionApi.searchByTransactionId(txnId);
				if(txn!=null){
					error.setTransactionId("Duplicate Transaction Id");
					valid=false;
				}
			}
			catch(Exception e){
				error.setTransactionId("Transaction Id Must Be Of Data Long");
				valid=false;
			}
			
		}
		
		
		if(dto.getExchangeRate()==0){
			error.setExchangeRate("Exchange Rate Cannot Be Zero");
			valid=false;
		}
		
		if(dto.getExchangeRate()<0){
			error.setExchangeRate("Exchange Rate Cannot Be Negative Value");
			valid=false;
		}
		
		if(dto.getSentAmount()<0){
			error.setSentAmount("Sent Amount Cannot Be Negative Value");
			valid=false;
		}
		
		if(dto.getReceivedAmount()<0){
			error.setReceivedAmount("Received Amount Cannot Be Negative Value");
			valid=false;
		}
		
		if(dto.getFromAccount().equals(dto.getToAccount())){
			error.setSentAmount("Cannot Send Amount To Same Account");
			error.setToAccount("Cannot Receive Amount From Same Account");
			valid=false;
		}
		
		if(dto.getFromAccount()==null || dto.getFromAccount().isEmpty()){
			error.setFromAccount("Sender Account Cannot Be Empty");
			valid=false;
		}
		
		if(dto.getToAccount()==null || dto.getToAccount().isEmpty()){
			error.setToAccount("Receiver Account Cannot Be Empty");
			valid=false;
		}
		
		if(dto.getFromCurrency()==null || dto.getFromCurrency().isEmpty()){
			error.setFromCurrency("Sending Currency Cannot Be Empty");
			valid=false;
		}
		
		if(dto.getToCurrency()==null || dto.getToCurrency().isEmpty()){
			error.setToCurrency("Receiving Currency Cannot Be Empty");
			valid=false;
		}
		
		if(!dto.getTransactionStatus().equals(TransactionStatus.Pending) && !dto.getTransactionStatus().equals(TransactionStatus.Complete)){
			error.setTransactionStatus("Invalid Transaction Status");
			valid=false;
		}
		
		if(!dto.getTransactionType().equals(TransactionType.Load) && !dto.getTransactionType().equals(TransactionType.Transfer)){
			error.setTransactionType("Invalid Transaction Type");
			valid=false;
		}
		
		if(fromAccount==null){
			error.setFromAccount("Invalid Sender Account Number");
			error.setFromCurrency("Invalid Sender Account to Check Currency");
			valid=false;
		}
		else{
			if(!fromAccount.getCountry().getCurrency().equals(dto.getFromCurrency())){
				error.setFromCurrency("Invalid Currency For Sender Account");
				valid=false;
			}
			if(fromAccount.getBalance()<dto.getSentAmount()){
				error.setFromAccount("Insufficient Amount in Sender Account");
				valid=false;
			}
		}
		
		if(toAccount==null){
			error.setToAccount("Invalid Receiver Account Number");
			error.setToCurrency("Invalid Receiver Account to Check Currency");
			valid=false;
		}
		else{
			if(!toAccount.getCountry().getCurrency().equals(dto.getToCurrency())){
				error.setToCurrency("Invalid Currency For Receiving Account");
				valid=false;
			}
		}
		error.setValid(valid);	
		return error;
		
	}

	public LoadFundValidationDTO LoadFundValidation(TransactionDTO dto) {
		LoadFundValidationDTO error=new LoadFundValidationDTO();
		boolean valid=true;
		Account account=accountApi.searchByAccountId(dto.getToAccount());
		if(dto.getTransactionId()==null || dto.getTransactionId().isEmpty()){
			error.setTransactionId("Invalid Transaction Id");
			valid=false;
		}
		else{
			try{
				long txnId=Long.parseLong(dto.getTransactionId());
				Transaction txn=transactionApi.searchByTransactionId(txnId);
				if(txn!=null){
					error.setTransactionId("Duplicate Transaction Id");
					valid=false;
				}
			}
			catch(Exception e){
				error.setTransactionId("Transaction Id Must Be Of Data Long");
				valid=false;
			}
			
		}
		
		if(dto.getReceivedAmount()<0){
			error.setReceivedAmount("Received Amount Cannot Be Negative Value");
			valid=false;
		}
		
		
		if(dto.getToAccount()==null || dto.getToAccount().isEmpty()){
			error.setToAccount("Receiver Account Cannot Be Empty");
			valid=false;
		}
		
		if(dto.getToCurrency()==null || dto.getToCurrency().isEmpty()){
			error.setToCurrency("Receiving Currency Cannot Be Empty");
			valid=false;
		}
		
		if(!dto.getTransactionStatus().equals(TransactionStatus.Pending) && !dto.getTransactionStatus().equals(TransactionStatus.Complete)){
			error.setTransactionStatus("Invalid Transaction Status");
			valid=false;
		}
		
		if(!dto.getTransactionType().equals(TransactionType.Load) && !dto.getTransactionType().equals(TransactionType.Transfer)){
			error.setTransactionType("Invalid Transaction Type");
			valid=false;
		}
		
		
		if(account==null){
			error.setToAccount("Invalid Receiver Account Number");
			error.setToCurrency("Invalid Receiver Account to Check Currency");
			valid=false;
		}
		else{
			if(!account.getCountry().getCurrency().equals(dto.getToCurrency())){
				error.setToCurrency("Invalid Currency For Receiving Account");
				valid=false;
			}
		}
		error.setValid(valid);	
		return error;
	}
}
