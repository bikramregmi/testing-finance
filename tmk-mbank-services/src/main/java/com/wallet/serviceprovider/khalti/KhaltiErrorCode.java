package com.wallet.serviceprovider.khalti;

import java.util.HashMap;
import java.util.Map;

public enum KhaltiErrorCode {

	TOKEN_NOT_PROVIDED("1001", "Token not provided"),
	INVALID_TOKEN_1("1002","Invalid token"),
	INVALID_TOKEN_2("1003","Invalid token"),
	INVALID_TOKEN_3("1004","Invalid token"),
	INVALID_TOKEN_4("1005","Invalid token"),
	INVALID_TOKEN_5("1006","Invalid token"),
	ACCOUNT_EXPIRED("1007","Account has expired"),
	PROBLEM_USING_SERVICE("1008","Problem using the service"),
	NO_PERMISSION("1009","Account doesn't have the permission"),
	VALIDATION_ERROR_1("1010","Validation error"),
	VALIDATION_ERROR_2("1011","Validation error"),
	REFERENCE_NOT_PROVIDED("1012","Reference id not provided"),
	DUPLICATE_REFERENCE("1013","Request with duplicate reference id obtained"),
	PARSE_ERROR_1("1014","Parse error"),
	PARSE_ERROR_2("1015","Parse error"),
	INSUFFICIENT_CREDIT("1016","Insufficient user credits"),
	SERVER_ERROR("1017","Server error"),
	ERROR_USING_SERVICE("1018","Error while using service"),
	SERVER_MAINTENANCE("1019","Service in maintenance mode"),
	ERC_NOT_OBTAINED("1020","ERC for the amount could not be obtained"),
	PROVIDER_ERROR("1021","Error from Provider");

	private String key;

	private String value;
	
	private static Map<String, String> mMap;

	private KhaltiErrorCode(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public static KhaltiErrorCode getEnumByValue(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (KhaltiErrorCode v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}

	public static KhaltiErrorCode getEnumByKey(String key) {
		if (key == null)
			throw new IllegalArgumentException();
		for (KhaltiErrorCode v : values())
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
        for (KhaltiErrorCode s : KhaltiErrorCode.values()) {
            mMap.put(s.key, s.value);
        }
    }
}
