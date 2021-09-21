package com.mobilebanking.model;

import java.util.HashMap;
import java.util.Map;

public enum IsoResponseCode {

//	INTERNAL_SERVER_ERROR("500", "Internal Server Error. Please contact Administrator"),

	UNABLE_TO_PROCESS("05", "Unable to process the transaction,Please try again later"),

	TRANSACTION_NOT_ALLOWED("39", "Transaction not allowed. Please contact bank"),

	INSUFFICIENT_FUND("51", "Insufficient fund to process transaction."),

	ACCOUNT_DORMANT("52", "The account is Dormant. Please contact bank for more information."),
	
	ACCOUNT_CLOSED("54", "The account is Closed. Please contact bank for more information."),
	
	ACCOUNT_RESTRICTED("62", "The account is Restricted. Please contact bank for more information."),

	INVALID_ACCOUNT("76", "Invalid account. Please contact bank for more information."),

	SYSTEM_ERR("96", "System error, please try again later."),
	
	;

	private String key;

	private String value;

	 private static Map<String, String> mMap;
	 
	private IsoResponseCode(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static IsoResponseCode getEnumByValue(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (IsoResponseCode v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}

	public static IsoResponseCode getEnumByKey(String key) {
		if (key == null)
			throw new IllegalArgumentException();
		for (IsoResponseCode v : values())
			if (key.equalsIgnoreCase(v.getKey()))
				return v;
		throw new IllegalArgumentException();
	}
	
	   public static String getValueByKey(String key) {
	        if (mMap == null) {
	            initializeMapping();
	        }
	        if (mMap.containsKey(key)) {
	            return mMap.get(key);
	        }
	        return null;
	    }

	
	private static void initializeMapping() {
        mMap = new HashMap<String, String>();
        for (IsoResponseCode s : IsoResponseCode.values()) {
            mMap.put(s.key, s.value);
        }
    }



}
