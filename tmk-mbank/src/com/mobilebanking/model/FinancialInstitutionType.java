package com.mobilebanking.model;

public enum FinancialInstitutionType {
	Bank("Bank"), MicroFinance("Micro Finance"), Finance("Finance"), Cooperative("Cooperative");
	private final String value;

	private FinancialInstitutionType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static FinancialInstitutionType getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (FinancialInstitutionType v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}

}
