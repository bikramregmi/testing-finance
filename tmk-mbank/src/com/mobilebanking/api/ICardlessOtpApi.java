package com.mobilebanking.api;

import com.mobilebanking.model.CustomerBankAccountDTO;

public interface ICardlessOtpApi {

	Boolean saveAndShareOTP(CustomerBankAccountDTO customerBankAcocunt, String mobileNumber, double amount,Long cardlessBankId) throws Exception;
	 Boolean validateOtp(String otp,Long id) throws Exception;
//	public void saveAndShareOTP(C)
	String unpackIsoOtpData(String isoRequest);
	
	String echo(String isoRequest);
	
	String sendEcho(String ip, int port);
	
}
