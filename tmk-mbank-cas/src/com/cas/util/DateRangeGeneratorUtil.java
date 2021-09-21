package com.cas.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DateRangeGeneratorUtil {

	public static Map<Integer, Integer> getDay() {
		Map<Integer, Integer> day = new LinkedHashMap<Integer, Integer>();
		for (int i = 0; i < 31; i++) {
			day.put(i + 1, i + 1);
		}
		return day;
	}

	public static Map<Integer, String> getMonth() {
		HashMap<Integer, String> months = new LinkedHashMap<Integer, String>();
		months.put(1, "Jan");
		months.put(2, "Feb");
		months.put(3, "Mar");
		months.put(4, "Apr");
		months.put(5, "May");
		months.put(6, "Jun");
		months.put(7, "Jul");
		months.put(8, "Aug");
		months.put(9, "Sep");
		months.put(10, "Oct");
		months.put(11, "Nov");
		months.put(12, "Dec");
		return months;
	}

	public static Map<Integer, String> getYear(int minAge) {
		Calendar date = new GregorianCalendar();
		int currDate = date.get(Calendar.YEAR) - minAge;

		Map<Integer, String> year = new LinkedHashMap<Integer, String>();
		for (int i = currDate; i >= 1900; i--) {
			year.put(i, String.valueOf(i));
		}
		return year;
	}
}
