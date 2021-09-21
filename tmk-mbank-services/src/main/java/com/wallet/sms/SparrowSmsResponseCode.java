/**
 * 
 */
package com.wallet.sms;

/**
 * @author bibek
 *
 */
public enum SparrowSmsResponseCode {
	
	SUCCESS("200", "Successfully Sent Message"), 
	REQUIREDFIELDMISSING("1000", "A Required Field Is Missing"), 
	INVALIDIP("1001", "Invalid IP Address"), 
	INVALIDTOKEN("1002", "Invalid Token"), 
	INACTIVE("1003", "Account Inactive"), 
	ACCINACTIVE("1004", "Account Inactive"), 
	EXPIRED("1005", "Account Has Been Expired"), 
	ACCEXPIRED("1006", "Account Has Been Expired"),
	INVALIDRECEIVER("1007", "Invalid Receiver"),
	INVALIDSENDER("1008", "Invalid Sender"), 
	EMPTYTEXT("1010", "Text Cannot Be Empty"),
	NOVALIDRECEIVER("1011", "No Valid Receiver"),
	NOCREDIT("1012", "No Credits Available"),
	INSUFFICIENTCREDIT("1013", "Insufficient Credits")
	;
	
	private final String key;
	
	private final String value;
	
	private SparrowSmsResponseCode(String value, String key) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public static String getValue(String value) {
		for (SparrowSmsResponseCode s: SparrowSmsResponseCode.values()) {
			if (s.value.equalsIgnoreCase(value)) {
				return s.getKey();
			}
		}
		return null;
	}
}
