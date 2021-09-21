/**
 * 
 */
package com.wallet.iso8583.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wallet.iso8583.model.MiniStatement;


/**
 * @author bibek
 *
 */
@Component
public class ParseFields {
	
	//navajeevan
	public Double parseAmountField54(String data) {
		double amount = 0.0;
		String amountString = "";
		data = data.substring(7);
		boolean isCredit = true;
		if (data.substring(0, 1).equalsIgnoreCase("D")) {
			isCredit = false;
		}
		data = data.substring(1);
		amountString = data.substring(1,12);
		amountString = amountString.replaceFirst ("^0*", "");
		if (! amountString.trim().isEmpty()) {
			System.out.println(amountString + " AMOUNT STRING");
			String decimalPlaces = "." + amountString.substring(amountString.length()-2, amountString.length());
			amountString = amountString.substring(0,amountString.length()-2) + decimalPlaces;
			if (! isCredit) {
				amount = (-1)* Double.parseDouble(amountString);
			} else {
				amount = Double.parseDouble(amountString);
			}
		}
		
		return amount;
	}
	
	//navajeevan
	public List<MiniStatement> getMiniStatementsField48(String data) {
		List<MiniStatement> miniStatementList = new ArrayList<MiniStatement>();
		String remainingMessage = data;
		while (! remainingMessage.trim().isEmpty()) {
			MiniStatement mini = new MiniStatement();
			mini.setTransactionCode(remainingMessage.substring(0,3));
			remainingMessage = remainingMessage.substring(3);
			boolean isCredit = true;
			if (remainingMessage.substring(0,1).equalsIgnoreCase("d")) {
				mini.setCredit(false );
				isCredit = false;
				remainingMessage = remainingMessage.substring(1);
			} else {
				mini.setCredit(true);
				remainingMessage = remainingMessage.substring(1);
			}
			String amountString = remainingMessage.substring(0,12);
			amountString = amountString.replaceFirst ("^0*", "");
			String amount = "";
			Double finalAmount = 0.0;
			if (! amountString.trim().isEmpty()) {
				
				String decimalPlaces = amountString.substring(amountString.length()-2, amountString.length());
				decimalPlaces = "." + decimalPlaces;
				amount = "00" + decimalPlaces;
				amount = amountString.substring(0,amountString.length()-2) + decimalPlaces;
				finalAmount = Double.parseDouble(amount);
			}
			mini.setAmount(finalAmount);
			remainingMessage = remainingMessage.substring(12);
			String dateString = remainingMessage.substring(0,4) + "-" + remainingMessage.substring(4,6) + "-" + remainingMessage.substring(6,8);
			mini.setTransactionDate(dateString);
			remainingMessage = remainingMessage.substring(8);
			miniStatementList.add(mini);
		}
		return miniStatementList;
	}

}
