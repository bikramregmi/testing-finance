package com.cas.model;


public enum UserStatus {

	INACTIVE("Inactive"), ACTIVE("Active"), DELETED("Deleted");

	private final String value;

	private UserStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static UserStatus getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (UserStatus v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
}
