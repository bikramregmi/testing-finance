package com.mobilebanking.util;

public enum DateTypes {
    TO_DATE("toDate"),FROM_DATE("fromDate");
	
	private final String value;

	private DateTypes(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}
}
