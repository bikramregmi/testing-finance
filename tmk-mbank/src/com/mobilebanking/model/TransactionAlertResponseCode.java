package com.mobilebanking.model;

public enum TransactionAlertResponseCode {
	SUCCESS("000", "Success"),

	FAILURE("201", "Failure"),

	INTERNAL_SERVER_ERROR("500", "Internal Server Error. Please contact Administrator"),

	INVALID_ACCOUNT_NO("701", "Invalid Account Number"),

	SUBSCRIPTION_NOT_FOUND("705", "Customer don't have alert service subscribed"),

	INVALID_MOBILENUMBER("706", "Invalid Mobile Number"),

	INVALID_MESSAGE("710", "Invalid or Empty Message"),

	INVALID_CHANNEL_PARTNER("715", "Invalid Channel partner."),

	INVALID_PASSWORD("720", "Invalid Password."),
	
	INACTIVE_CHANNEL_PARTNER("725", "Inactive Channel Partner"),
	
	CUSTOMER_NOT_FOUND("820", "No Customer found"),

	INVALID_SWIFTCODE("825", "Invalid Swift Code"),

	INVALID_TRACEID("835", "Invalid Trace Id"),
	
	INVALID_DATE_TIMER("835", "Invalid Date"),
	
	BANK_NOT_FOUND("910", "No bank for this swift code was found"),
	
	;

	private TransactionAlertResponseCode() {

	}

	private String key;

	private String value;

	private TransactionAlertResponseCode(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static TransactionAlertResponseCode getEnumByValue(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (TransactionAlertResponseCode v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}

	public static TransactionAlertResponseCode getEnumByKey(String key) {
		if (key == null)
			throw new IllegalArgumentException();
		for (TransactionAlertResponseCode v : values())
			if (key.equalsIgnoreCase(v.getKey()))
				return v;
		throw new IllegalArgumentException();
	}
}
