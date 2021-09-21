package com.mobilebanking.model;

public enum AgentType {
	PARENT_AGENT("parentAgent"),CHILD_AGENT("childAgent"),ALL("all");
	
	private final String value;

	private AgentType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static AgentType getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (AgentType agentType : values())
			if (value.equalsIgnoreCase(agentType.getValue()))
				return agentType;
		throw new IllegalArgumentException();
	}
}
