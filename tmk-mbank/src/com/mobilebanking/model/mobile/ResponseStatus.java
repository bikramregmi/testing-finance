package com.mobilebanking.model.mobile;

public enum ResponseStatus {

	SUCCESS("M0000","Success"),
	
	FAILURE("M0001", "Failure"),

	INTERNAL_SERVER_ERROR("M0001", "Internal Server Error. Please contact Administrator"),

	INVALID_SESSION("M0003","Invalid Session"),

	BAD_REQUEST("M0004","Bad Request"),
	
	VALIDATION_FAILED("M0005","Validation Failed"),
	
	UNAUTHORIZED_USER("M0006","Un-authorized User"),
	
	INSUFFICIENT_BALANCE("M0007", "Insufficient Balance"),
	
	ACCOUNT_NUMBER_REQUIRED("M0008", "Account Number Required"),
	
	ACCOUNT_NUMBER_INVALID("M0009", "Account Number Invalid"),
	
	AMOUNT_REQUIRED("M0010", "Amount is Required"),
	
	VERIFICATION_CODE_REQUIRED("M0011", "Verification Code Required"), 
	
	INVALID_VERIFICATION_CODE("M0012", "Invalid Verification Token"), 
	AUTHORIZATION_HEADER("M0013", "Authorization is empty or not valid"),
	USER_REQUIRED("M0014", "Username is Required"),
	SMS_FAILED_VERIFCATION("M0015", "Could Not Send Sms. Please contact Administrator"),
	ACCOUNT_NOT_VERIFIED("M0016", "Account Not Verified. Please contact Administrator"), 
	SERVICE_IDENTIFIER_REQUIRED("M0016", "Service Identifier Required"),
	CUSTOMER_NOT_FOUND("M0017", "Customer Not Found"),
	USERNAME_NOT_VALID("M0018", "Username Not Valid"),
	USER_ALREADY_EXIST("M0019", "User Already Exist"),
	STATES_UNAVAILABLE("M0020", "States are unavailable or empty list"),
	CITIES_UNAVAILABLE("M0021", "Cities are unavailable or empty list"),
	DATA_SAVE_ERROR("M002", "Technical Error, Please Try Again Later"),
	BANKS_UNAVAILABLE("M0022", "Banks are unvailable or empty list"),
	BANKBRANCH_UNAVLIABLE("M0023", "Bank Branches are unavailabe or empty list"),
	TOKEN_NOT_AVAILABLE("M0024", "Token for present Username not available"),
	MPIN_DOES_NOT_MATCH("M0025", "Mpin does not match, Please enter valid Mpin"),
	AMBIGUOUS_TRANSACTION("M0026", "Technical Error Please Contact Administrator"),
	CLIENT_REQUIRED("M0027", "Client Identification Required"),
	ACCOUNT_NOT_VERIFIED_CLIENT("M0028", "Account Not Verified with selected client"),
	PHONE_NUMBER_REQUIRED("M0029", "Phone/Mobile Number Required"),
	NOTICE_UNAVAILABLE("M0030", "Notice are unavailable or empty list"), TRANSACTION_LIMIT_REACHED("M0031", "Transaction Limit Reached"), 
	CUSTOMER_BLOCKED("M0032", "Customer Blocked"), CARDLESS_BANK_REQUIRED("M0033", "Cardless Bank Required"),
	INVALID_MOBILE_NUMBER("M0034","Invalid Mobile Number"), No_MINISTATEMENT("M0035", "No ministatement is available at the moment. Please try again later."), 
	CUSTOMER_CODE_REQUIRED("M0036", "Customer code is Required"),COUNTER_REQUIRED("M0037", "Counter is Required"),MONTH_REQUIRED("M0038", "Month is Required"),
    EMAIL_REQUIRED("M0037","Email is Required"),
	START_DATE_REQUIRED("M0038","Start Date is Requred"),
	END_DATE_REQUIRED("M0039","End Date is Required"),No_TRANSACTION("M0035", "No transaction is available at the moment. Please try again later."),;

	private ResponseStatus() {

	}
	
	private String key;

	private String value;

	private ResponseStatus(String key,String value) {
		this.key=key;
		this.value = value;
	}
	
	public String getKey(){
		return key;
	}
	public String getValue() {
		return value;
	}

	public static ResponseStatus getEnumByValue(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (ResponseStatus v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
	
	public static ResponseStatus getEnumByKey(String key) {
		if (key == null)
			throw new IllegalArgumentException();
		for (ResponseStatus v : values())
			if (key.equalsIgnoreCase(v.getKey()))
				return v;
		throw new IllegalArgumentException();
	}

}
