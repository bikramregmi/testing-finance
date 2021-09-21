package com.cas.model;


public enum TransactionType {

	Transfer("Fund Transfer"), Load("Fund Load");
	private final String value;

	private TransactionType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static TransactionType getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (TransactionType v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
}
