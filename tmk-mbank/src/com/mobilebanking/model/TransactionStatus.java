package com.mobilebanking.model;

public enum TransactionStatus {

	Initiated("Initiated"),Pending("Pending"),Complete("Complete"), Hold("Hold"),CancelledWithRefund("Cancelled With Refund"),
	CancelledWithoutRefund("Cancelled Without Refund"),Approved("Approved"),Paid("Paid"),Ambiguous("Ambiguous"),ReversalWithRefund("Reversal With Refund"),Reversed("Reversed");

	private  final String value;

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
