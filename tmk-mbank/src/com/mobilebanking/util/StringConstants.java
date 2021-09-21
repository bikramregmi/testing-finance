package com.mobilebanking.util;

public class StringConstants {

	// TODO
	// this need to be changed while making war file**********

	public static final Boolean IS_PRODUCTION_AIRLINES = true;
	public static final Boolean IS_PRODUCTION = true;
	public static final Boolean IS_BANK = true;

	// public static final Boolean IS_PRODUCTION=true;

	public static final String OS_LINUX_DIRECTORY = "/opt/mbank";
	public static final String OS_WINDOWS_DIRECTORY = "D://mbank";
	
	public static final String OS_WINDOWS_DIRECTORY_SAMPLE = "D://mbank/sample";
	public static final String OS_LINUX_DIRECTORY_SAMPLE = "/opt/mbank/sample";

	public static final String DATA_READ = "Data Read Success";
	public static final String DATA_READING_ERROR = "Couldn't fetch data";
	public static final String DATA_SAVING_ERROR = "Validation Failed";
	public static final String DATA_SAVING_EXCEPTION = "Internal Server Error";
	public static final String DATA_SAVED = "Data Saved";

	public static final String USER_ADMIN = "admin";
	public static final String USER_SYSADMIN = "sysadmin";
	public static final String USER_SUPERAGENT = "superAgent";
	public static final String USER_AGENT = "agent";
	public static final String USER_CUSTOMER = "customer";
	public static final String USER_SENDER = "sender";
	public static final String USER_BENEFICIARY = "beneficiary";
	public static final String USER_MERCHANT = "merchant";

	public static final String POOL_AC_ID = "111";
	public static final String POOL_AC_NO = "100";
	public static final String POOL_AC_USER_ID = "111";

	public static final String COM_SENDER = "commissionSender";
	public static final String COM_RECEIVER = "commissionReceiver";

	public static final String COM_SETTLEMENT_REMIT_CURRENCY = "poolToRemitCommission";// in
																						// remit
																						// agent's
																						// currency
	public static final String COM_SETTLEMENT_PAYOUT_CURRENCY = "poolToPayoutCommission";// in
																							// payout
																							// agents
																							// currency

	public static final String COM_SETTLEMENT_REMIT_POOL_CURRENCY = "poolRemitDebit";// in
																						// pool
																						// account's
																						// currency
	public static final String COM_SETTLEMENT_PAYOUT_POOL_CURRENCY = "poolPayoutDebit";// in
																						// payout
																						// account's
																						// currency

	public static final String OPERATOR_ACCOUNT_HEAD = "Hamro Technologies";

	public static final String OPERATOR_DEFAULT_ACCOUNT_NUMBER = "hamroTechOperator";

	public static final String TRANSACTION_UNIQUE_NUMBER = "transactionUniqueNumber";

	public static final String LEDGER_REMARKS = "Balance transferred from ACCOUNT_ONE to ACCOUNT_TWO";

	public static final String POOL_ACCOUNT_HEAD = "POOL ACCOUNT";

	public static final String POOL_COMMISSION_ACCOUNT = "POOL COMMISSION ACCOUNT";

	public static final String SPARROW_SMS_CREDIT = "sparrow_sms_credit";

	public static final String SPARROW_SMS_CREDIT_CONSUMED = "sparrow_sms_credit_consumed";

	public static final String REGISTRATION_SMS = "Welcome to mBank registration. Your password for {username} is {password}";

	public static final String REGISTRATION_USERNAME = "Your username is {username} and your password is {password}. Please change password after first login. Mbank";

	public static final String REGISTRATION_CUSTOMER = "Welcome to {bank}. Your mpin for {username} is {mpin}. Thank You. -{bank}";
	public static final String LINKED_ACCOUNT_REGISTRATION = "Welcome {username}, You have already been registered previously and your new A/C has been assosiated with it. Please use your username and mpin for login. Thank you. -{bank}";
	public static final String BALANCE_ENQUIRY = "Your Balance for Account  {accountNumber} as of {date} is {balance}. Thank you. -{bank}";
	public static final String PAYMENT_SUCCESFUL = "{service} payment of {balance} was successful for {TargetNo} with TranId {transactionId} on {date}. Thank you. -{bank}.";

	public static final String FUND_TRANSFER = "Your fund transfer of NPR {amount} to A/C No. {accountNumber} was successful as of {date}. Thank You. -{bank}";
	public static final String FUND_TRANSFER_FAIL = "Your fund transfer of NPR {amount} to A/C No. {accountNumber} was failed. Please try again later. -{bank}";
	public static final String MOBILE_NUMBER_EDIT = "Dear {customer},Your mobile number for our banking system has been changed.Your new mpin for username {username} is {mpin}. Thank You. -{bank}";
	public static final String CUSTOMER_BLOCKED = "Sorry, you have been blocked from using our Banking application. Please contact your bank for more Info.Thank You. -{bank}";

	public static final String WITHDRAWAL_MESSAGE = "Dear {customer},Your A/C {accountnumber} has been withdrawn by {amount} on {transactiondate}. Thank You. -{bank}";
	public static final String DEPOSIT_MESSAGE = "Dear {customer},Your A/C {accountnumber} has been deposited by {amount} on {transactiondate}. Thank You. -{bank}";
	public static final String CBS_TRANSACTION_MESSAGE = "Dear {customer},Your A/C {accountnumber} has been {remarks} by {amount} on {transactiondate}. Thank You MBANK";

	public static final String CHEQUE_REQUEST = "Your Cheque book request for {leaves} leaves has been requested  as of {date}. Please contact {bank} for details. Thank you.";
	public static final String CHEQUE_BLOCK = "Your Cheque block request for Cheque No. {cheaqueNO} has been requested as of {date}. Please call {bank} for confirmation. Thank you.";
	public static final String DEFAULT_MESSAGE = "Invalid keyword";
	public static final String DEFAULT_SHORTCODE_MESSAGE = "Your Request was unsuccessful. Please try again later ";
	public static final String DISHOME_TOPUP = "dishhome_online_topup";
	public static final String NTC_PREPAID_TOPUP = "ntc_prepaid_topup";
	public static final String NTC_POSTPAID_TOPUP = "ntc_postpaid_topup";
	public static final String NCELL_TOPUP = "ncell_prepaid_topup";
	public static final String WLINK_TOPUP = "worldlink_online_topup";
	public static final String VIANET = "vianet_online_topup";
	public static final String SUBISU = "subisu_online_topup";
	public static final String SIMTV = "simtv_online_topup";
	public static final String PSTN = "pstn_online_topup";
	public static final String ADSLU = "adslu_online_topup";
	public static final String ADSLV = "adslv_online_topup";
	public static final String CDMA = "cdma_online_topup";
	public static final String NEA = "nea_online_topup";
	public static final String UTL = "utl_online_topup";
	public static final String SMARTCELL = "smartcell_topup";
	public static final String SMART_CELL = "smartcell_online_payment";
	public static final String BROADLINK = "broadlink_online_payment";
	public static final String BROADTEL = "broadtel_online_payment";
	public static final String DISHHOME_PIN = "dishhome_online_payment";
	public static final String NET_TV = "nettv_online_payment";
	public static final String KHANE_PANI = "khanepani_online_topup";

	public static final String AIRLINES_BOOKING = "ARS";

	public static final String SERVICE_PAYMENT_SUCCESFUL = "Your payment for {service} was succesfull.";
	public static final String CUSTOMER_TOPIC = "customer";

	// public static String OTP_MESSAGE = "Your OTP for cardless ATM withdrawl
	// is {otp}. Thank you {bank}.";

	public static String OTP_MESSAGE = "mbank has sent Cardless OTP to you.You may encash from any of Civil Bank ATMs using cardless Cash.Your OTP is {otp}. This OTP is valid for 2 hours.Find your nearest ATMs from our mBank app.";

	// "mbank has sent {amount} to you.You may encash from any of Civil ATMs
	// using cardless Cash.Yout ITP is {otp}";

	public static final String VERIFICATION_MESSAGE = "Your Verification Code is: {verificationCode}";
	public static final String UNVERIFIED = "unverified";

	public static final String COMMISSION_TO_POOL = "Commission received from {service} to {poolAccount} ";

	public static final String BALANCE_INQUIRY_MSG = "Your available balance for A/C {accountNumber} as of {date} {time} is NPR {balance}. Thank You.-{bank}";
	// public static String FUND_TRANSFER = "Your balance is {balance} Thankyou
	// MBANK";

	public static final String ACCOUNT_POSTING_FUND_TRANSFER = "Transferred {amount} from {accountNumber1} to {accountNumber2}";
	public static final String PIN_RESET_MESSAGE = "Your mPin has been reset. Your new mPin is {mPin}. Thank You";

	public static final String CARDLESS_BANK_ACCOUNT_HEAD = "Cardless_Bank_Account_Head";
	// added by amrit
	public static final String DEBIT = "debit";
	public static final String CREDIT = "credit";

	public static final String LEDGER_REMARKS_FOR_UPDATE = "Balance of ACCOUNT updated from BALANCE_ONE to BALANCE_TWO";
	public static final String DECREASE_BALANCE = "Balance of ACCOUNT decreased from BALANCE_ONE to BALANCE_TWO";

}
