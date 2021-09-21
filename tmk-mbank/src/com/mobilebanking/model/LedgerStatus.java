package com.mobilebanking.model;

	public enum LedgerStatus {
		PENDING("Pending"), COMPLETE("Complete"), CANCEL("Cancel"), REVERSED("Reversed");
		private final String value;
		private LedgerStatus(String value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return value;
		}
		public String getValue() {
			return value;
		}
		public static LedgerStatus getEnum(String value) {
			if (value == null)
				throw new IllegalArgumentException();
			for (LedgerStatus v : values())
				if (value.equalsIgnoreCase(v.getValue()))
					return v;
			throw new IllegalArgumentException();
		}
	}
 
