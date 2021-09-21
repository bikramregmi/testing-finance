package com.mobilebanking.model;

public enum DocumentType {
	PASSPORT("Passport"), CITIZENSHIP("Citizenship"), LICENSE("Driving License");
	private final String value;

	private DocumentType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static DocumentType getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (DocumentType v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}

}
