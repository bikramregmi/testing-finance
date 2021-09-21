package com.mobilebanking.validation;

import org.springframework.stereotype.Component;

import com.mobilebanking.model.CardlessBankDTO;
import com.mobilebanking.model.error.CardlessBankError;

@Component
public class CardlessBankValidation {

	public CardlessBankError validateCardlessBank(CardlessBankDTO cardlessBank) {
		CardlessBankError error = new CardlessBankError();
		String errorMessage;
		boolean valid = true;

		errorMessage = checkBank(cardlessBank.getBank());
		if (errorMessage != null) {
			error.setBank(errorMessage);
			valid = false;
		}

		errorMessage = checkHost(cardlessBank.getHost());
		if (errorMessage != null) {
			error.setHost(errorMessage);
			valid = false;
		}

		errorMessage = checkPort(cardlessBank.getPort());
		if (errorMessage != null) {
			error.setPort(errorMessage);
			valid = false;
		}

		errorMessage = checkUserSign(cardlessBank.getUserSign());
		if (errorMessage != null) {
			error.setUserSign(errorMessage);
			valid = false;
		}

		errorMessage = checkUserPassword(cardlessBank.getUserPassword());
		if (errorMessage != null) {
			error.setUserPassword(errorMessage);
			valid = false;
		}

		errorMessage = checkCompanyCode(cardlessBank.getCompanyCode());
		if (errorMessage != null) {
			error.setCompanyCode(errorMessage);
			valid = false;
		}
		
		errorMessage = checkState(cardlessBank.getState());
		if (errorMessage != null) {
			error.setState(errorMessage);
			valid = false;
		}
		
		errorMessage = checkCity(cardlessBank.getCity());
		if (errorMessage != null) {
			error.setCity(errorMessage);
			valid = false;
		}
		
		errorMessage = checkAddress(cardlessBank.getAddress());
		if (errorMessage != null) {
			error.setAddress(errorMessage);
			valid = false;
		}
		error.setValid(valid);

		return error;

	}
	
	public CardlessBankError validateCardlessBankEdit(CardlessBankDTO cardlessBank) {
		CardlessBankError error = new CardlessBankError();
		String errorMessage;
		boolean valid = true;

		errorMessage = checkBank(cardlessBank.getBank());
		if (errorMessage != null) {
			error.setBank(errorMessage);
			valid = false;
		}

		errorMessage = checkHost(cardlessBank.getHost());
		if (errorMessage != null) {
			error.setHost(errorMessage);
			valid = false;
		}

		errorMessage = checkPort(cardlessBank.getPort());
		if (errorMessage != null) {
			error.setPort(errorMessage);
			valid = false;
		}

		errorMessage = checkUserSign(cardlessBank.getUserSign());
		if (errorMessage != null) {
			error.setUserSign(errorMessage);
			valid = false;
		}

		errorMessage = checkUserPassword(cardlessBank.getUserPassword());
		if (errorMessage != null) {
			error.setUserPassword(errorMessage);
			valid = false;
		}

		errorMessage = checkCompanyCode(cardlessBank.getCompanyCode());
		if (errorMessage != null) {
			error.setCompanyCode(errorMessage);
			valid = false;
		}
		
		errorMessage = checkAddress(cardlessBank.getAddress());
		if (errorMessage != null) {
			error.setAddress(errorMessage);
			valid = false;
		}
		
		error.setValid(valid);

		return error;
	}

	private String checkAtmTermNo(String atmTermNo) {
		if(atmTermNo==null){
			return "Invalid ATM Term Number";
		}else if(atmTermNo.trim().equals("")){
			return "Invalid ATM Term Number";
		}
		return null;
	}

	private String checkAtmBinNo(String atmBinNo) {
		if(atmBinNo==null){
			return "Invalid ATM Bin Number";
		}else if(atmBinNo.trim().equals("")){
			return "Invalid ATM Bin Number";
		}
		return null;
	}

	private String checkDebitTheirRef(String debitTheirRef) {
		if(debitTheirRef==null){
			return "Invalid Debit Their Ref";
		}else if(debitTheirRef.trim().equals("")){
			return "Invalid Debit Their Ref";
		}
		return null;
	}

	private String checkCardlessBankAccountNo(String cardlessBankAccountNo) {
		if(cardlessBankAccountNo==null){
			return "Invalid Cardless Bank Account Number";
		}else if(cardlessBankAccountNo.trim().equals("")){
			return "Invalid Cardless Bank Account Number";
		}
		return null;
	}

	private String checkBankAccountNo(String bankAccountNo) {
		if(bankAccountNo==null){
			return "Invalid Bank Account Number";
		}else if(bankAccountNo.trim().equals("")){
			return "Invalid Bank Account Number";
		}
		return null;
	}

	private String checkAddress(String address) {
		if(address==null){
			return "Invalid Address";
		}else if(address.trim().equals("")){
			return "Invalid Address";
		}
		return null;
	}

	private String checkCity(String city) {
		if(city==null){
			return "Invalid City";
		}else if(city.trim().equals("")){
			return "Invalid City";
		}
		return null;
	}

	private String checkState(String state) {
		if(state==null){
			return "Invalid State";
		}else if(state.trim().equals("")){
			return "Invalid State";
		}
		return null;
	}

	private String checkCompanyCode(String companyCode) {
		if(companyCode==null){
			return "Invalid Company Code";
		}else if(companyCode.trim().equals("")){
			return "Invalid Company Code";
		}
		return null;
	}

	private String checkUserPassword(String userPassword) {
		if(userPassword==null){
			return "Invalid User Password";
		}else if(userPassword.trim().equals("")){
			return "Invalid User Password";
		}
		return null;
	}

	private String checkUserSign(String userSign) {
		if(userSign==null){
			return "Invalid User Sign";
		}else if(userSign.trim().equals("")){
			return "Invalid User Sign";
		}
		return null;
	}

	private String checkPort(String port) {
		if(port==null){
			return "Invalid Port";
		}else if(port.trim().equals("")){
			return "Invalid Port";
		}
		return null;
	}

	private String checkHost(String host) {
		if(host==null){
			return "Invalid Host";
		}else if(host.trim().equals("")){
			return "Invalid Host";
		}
		return null;
	}

	private String checkBank(String bank) {
		if(bank==null){
			return "Invalid Bank";
		}else if(bank.trim().equals("")){
			return "Invalid Bank";
		}
		return null;
	}

}
