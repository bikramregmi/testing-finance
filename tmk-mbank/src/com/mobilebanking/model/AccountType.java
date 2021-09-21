package com.mobilebanking.model;

public enum AccountType {
	CUSTOMER("Customer"), BANK("Bank"), MERCHANT("Merchant"), MERCHANT_SERVICE("Merchant Service"),CHANNELPARTNER("Channel Partner"),
	POOL("Pool"), PARKING("Parking"), OPERATOR("Operator"), FINANCIALINSTITUTION("Financials"), COMMISSION_POOL("CommissionPool"),
	BANKPOOL("BankPool"), BANKOPERATOR("BankOperator"),CASH("cash"),CARDLESS_BANK("Cardless Bank");
	private final String value;

	private AccountType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static AccountType getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (AccountType v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}

}
