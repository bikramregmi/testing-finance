package com.wallet.serviceprovider.epay;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlinePaymentRequest {
     private String P_LOGIN_PASSWD;
     
     private String P_SERVICE;
     
     private String P_MSISDN;
     
     private String P_PAY_AMOUNT;
     
     private String P_RECEIPT_NUM;
     
     private String P_DATE;
     
     private String P_LOGIN_NAME;

	public String getP_LOGIN_PASSWD() {
		return P_LOGIN_PASSWD;
	}

	public void setP_LOGIN_PASSWD(String p_LOGIN_PASSWD) {
		P_LOGIN_PASSWD = p_LOGIN_PASSWD;
	}

	public String getP_SERVICE() {
		return P_SERVICE;
	}

	public void setP_SERVICE(String p_SERVICE) {
		P_SERVICE = p_SERVICE;
	}

	public String getP_MSISDN() {
		return P_MSISDN;
	}

	public void setP_MSISDN(String p_MSISDN) {
		P_MSISDN = p_MSISDN;
	}

	public String getP_PAY_AMOUNT() {
		return P_PAY_AMOUNT;
	}

	public void setP_PAY_AMOUNT(String p_PAY_AMOUNT) {
		P_PAY_AMOUNT = p_PAY_AMOUNT;
	}

	public String getP_RECEIPT_NUM() {
		return P_RECEIPT_NUM;
	}

	public void setP_RECEIPT_NUM(String p_RECEIPT_NUM) {
		P_RECEIPT_NUM = p_RECEIPT_NUM;
	}

	public String getP_DATE() {
		return P_DATE;
	}

	public void setP_DATE(String p_DATE) {
		P_DATE = p_DATE;
	}

	public String getP_LOGIN_NAME() {
		return P_LOGIN_NAME;
	}

	public void setP_LOGIN_NAME(String p_LOGIN_NAME) {
		P_LOGIN_NAME = p_LOGIN_NAME;
	}
	
	
}
