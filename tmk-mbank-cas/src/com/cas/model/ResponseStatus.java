package com.cas.model;


public enum ResponseStatus {

	Success("S00"),

	Failure("F00"),

	Internal_Server_Error("F01"),
	
	Invalid_Session("F02"),

	Invalid_Request("F03");

	private ResponseStatus() {

	}

	private String value;

	private ResponseStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static ResponseStatus getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (ResponseStatus v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}

}
