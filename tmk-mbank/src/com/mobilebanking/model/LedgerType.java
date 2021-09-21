package com.mobilebanking.model;

public enum LedgerType {

	LOADFUND("Load Fund"),TRANSFER("Fund Transfer"), WITHDRAW("Withdraw"),
	 CASHIN("Cash In"), CASHOUT("Cash Out"),
	REVERSAL("Reversal Transaction"),COMMISSION("Commission Settlement"), DISCOUNT("Discount"),
	UPDATE_CREDIT_LIMIT("Update Credit Limit"),DECREASE_BALANCE("Decrease Balance");
	private final String value;
	private LedgerType(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
	public String getValue() {
		return value;
	}
	public static LedgerType getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (LedgerType v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
}
