package com.mobilebanking.util;

public interface Authorities {
	
	public static final String AUTHENTICATED = "ROLE_AUTHENTICATED";
	
	public static final String ADMINISTRATOR = "ROLE_ADMINISTRATOR";

	public static final String SUPER_AGENT = "ROLE_SUPER_AGENT";
	
	public static final String AGENT = "ROLE_AGENT";
	
	public static final String CUSTOMER = "ROLE_CUSTOMER";
	
	public static final String SENDER_CUSTOMER="ROLE_SENDER_CUSTOMER";
	
	public static final String BENEFICIARY_CUSTOMER="ROLE_BENEFICIARY_CUSTOMER";
	
	public static final String MERCHANT="ROLE_MERCHANT";
	
	public static final String SERVICE="ROLE_SERVICE";
	
	public static final String FINANCIAL_INSTITUTION="ROLE_FINANCIAL_INSTITUTION";
	
	public static final String BANK = "ROLE_BANK";
	
	public static final String BANK_BRANCH = "ROLE_BANK_BRANCH";
	
	public static final String BANK_ADMIN = "ROLE_BANK_ADMINISTRATOR";
	
	public static final String BANK_BRANCH_ADMIN = "ROLE_BANK_BRANCH_ADMINISTRATOR";

	public static final String CARDLESS_BANK = "ROLE_CARDLESS_BANK";
	
	public static final String CHANNELPARTNER = "ROLE_CHANNELPARTNER";
	
}
