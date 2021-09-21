package com.cas.model;


public enum TransactionStatus {

	Pending("Pending"), Complete("Complete"), Cancel("Cancel"), Reversed(
			"Reversed");

	private final String value;

	private TransactionStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static TransactionStatus getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (TransactionStatus v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
}
