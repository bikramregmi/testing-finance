package com.mobilebanking.util;

public enum ETransactionChannelForm {
	
	MP_BP("Merchant Point_Bill Payments"),
	CI_BP("Customer Initiated(not covered before)_Bill Payments"),
	CI_TR("Customer Initiated(not covered before)_Transfer(A/c to A/c)"),
	CI_OTR("Customer Initiated(not covered before)_Other Transfer (Cardless or OTC)"),
	MIOECT_MBT("Mobile, Internet and electronic card Transaction_Mobile Banking Transaction");
	
	private final String value;
	private ETransactionChannelForm(String value) {
		this.value=value;
	}
	@Override
	 public String toString() {
		 return value;
	 }
	public String getValue() {
		return value;
	}
}
