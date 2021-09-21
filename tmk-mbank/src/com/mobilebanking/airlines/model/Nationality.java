package com.mobilebanking.airlines.model;

import java.util.ArrayList;
import java.util.List;


public enum Nationality {
Nepalese("NP"), Indian("IN");
	
	private final String value;

	private Nationality(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static Nationality getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (Nationality v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
	
	public static List<String> getAllNationalitiesValues(){
		
		List<String> enumList = new ArrayList<String>();
		for (Nationality v : values()){
			enumList.add(v.getValue());
		}
		return enumList;
	}
	
}
