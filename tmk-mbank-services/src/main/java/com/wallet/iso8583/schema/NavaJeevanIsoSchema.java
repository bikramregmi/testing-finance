/**
 * 
 */
package com.wallet.iso8583.schema;

import java.util.HashMap;

/**
 * @author bibek
 *
 */
public class NavaJeevanIsoSchema {
	
public static HashMap<String, String> isoSchema = new HashMap<String, String>();
	
	public NavaJeevanIsoSchema() {
		
		isoSchema.put("1","BITMAP");
		isoSchema.put("2", "CHAR-2-180-0_0");
		isoSchema.put("3", "NUMERIC-0-6-0_0");
		isoSchema.put("4","NUMERIC-0-12-0_0");
		isoSchema.put("5","NUMERIC-0-19-0_0");
		isoSchema.put("7", "NUMERIC-0-10-0_0");
		isoSchema.put("9","NUMERIC-0-8-0_0");
		isoSchema.put("10","NUMERIC-0-8-0_0");
		isoSchema.put("11", "CHAR-0-6-0_0");
		isoSchema.put("12", "NUMERIC-0-6-0_0");
		isoSchema.put("13", "NUMERIC-0-4-0_0");
		isoSchema.put("14", "NUMERIC-0-4-0_0");
		isoSchema.put("15", "NUMERIC-0-4-0_0");
		isoSchema.put("17", "NUMERIC-0-4-0_0");
		isoSchema.put("18", "NUMERIC-0-4-0_0");
		isoSchema.put("18", "NUMERIC-0-4-0_0");
		isoSchema.put("22", "NUMERIC-0-3-0_0");
		isoSchema.put("24", "NUMERIC-0-3-0_0");
		isoSchema.put("25", "NUMERIC-0-2-0_0");
		isoSchema.put("26", "NUMERIC-0-2-0_0");
		isoSchema.put("32", "NUMERIC-2-11-0_0");
		isoSchema.put("33", "NUMERIC-2-11-0_0");
		isoSchema.put("35", "CHAR-2-37-0_0");
		isoSchema.put("37", "NUMERIC-0-12-0_0");
		isoSchema.put("38", "NUMERIC-0-6-0_0");
		isoSchema.put("39", "CHAR-0-2-0_0");
		isoSchema.put("41", "CHAR-0-8-0_0");
		isoSchema.put("42", "CHAR-0-15-0_0");
		isoSchema.put("43", "CHAR-0-40-0_0");
		isoSchema.put("45", "CHAR-0-76-0_0");
		isoSchema.put("46", "CHAR-0-5-0_0");
		isoSchema.put("47", "CHAR-3-200-0_0");
		isoSchema.put("48", "CHAR-3-999-0_0");
		isoSchema.put("49", "CHAR-0-3-0_0");
		isoSchema.put("50", "CHAR-2-3-0_0");
		isoSchema.put("51", "CHAR-2-3-0_0");
		isoSchema.put("52","CHAR-2-16-0_0");
		isoSchema.put("54", "CHAR-03-40-0_0");
		isoSchema.put("57","CHAR-2-50-0_0");
		isoSchema.put("58","CHAR-2-99-0_0");
		isoSchema.put("59","CHAR-2-99-0_0");
		isoSchema.put("60","CHAR-3-999-0_0");
		isoSchema.put("65","NUMERIC-1-1-0_0");
		isoSchema.put("70","NUMERIC-0-3-0_0");
		isoSchema.put("79","CHAR-2-10-0_0");
		isoSchema.put("87", "CHAR-2-10-0_0");
		isoSchema.put("90", "CHAR-0-42-0_0");
		isoSchema.put("100","NUM-2-8-0_0");
		isoSchema.put("101","CHAR-2-99-0_0");
		isoSchema.put("102", "CHAR-2-38-0_0");
		isoSchema.put("103", "CHAR-2-40-0_0");
		isoSchema.put("104", "CHAR-2-16-0_0");
		isoSchema.put("114","CHAR-3-999-1_1");
		isoSchema.put("120","CHAR-3-999-1_1");
		isoSchema.put("122", "CHAR-3-999-0_0");
		isoSchema.put("121", "CHAR-3-999-0_0");
		isoSchema.put("123", "CHAR-3-999-0_0");
		isoSchema.put("124", "CHAR-3-999-0_0");
		isoSchema.put("125", "CHAR-4-999-0_0");
		isoSchema.put("126", "CHAR-4-999-0_0");

		isoSchema.put("130","CHAR-2-90-0_0");
	}
	
	public HashMap<String, String> getIsoSchema() {
		return isoSchema;
	}
}
