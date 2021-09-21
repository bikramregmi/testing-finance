package com.mobilebanking.util;

import org.springframework.stereotype.Component;

@Component
public class AccountUtil {
	
	public String generateAccountNumber() {
		String accountNumber = "";
		accountNumber = "MBANK" + System.currentTimeMillis();
		return accountNumber;
	}
}
