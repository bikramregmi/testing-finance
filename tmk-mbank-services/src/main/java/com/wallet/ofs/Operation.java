package com.wallet.ofs;

public enum Operation {
	FUNDSTRANSFER("FUNDS.TRANSFER");
	
	private String value;
	
	private Operation(String value) {
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
