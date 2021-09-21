package com.mobilebanking.model;

public enum Order {
	ASC("ASC"), DESC("DESC");

	private final String value;

	private Order(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static Order getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (Order v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
}
