package com.cas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static Date getCustomDateFromDate(Date date) {
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			return c.getTime();
		} else {
			return null;
		}
	}

	public static Date getCustomDateToDate(Date date) {
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			return c.getTime();
		} else {
			return null;
		}
	}

	public static Date getCurrentDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	public static String formatDate(Date d) {
		if (d == null) {
			return "N/A";

		}
		else {
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			return formater.format(d);
		}
	}

	public static String formatDateShort(Date d) {
		if (d == null) {
			return "N/A";

		}
		else {
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy");
			return formater.format(d);
		}
	}

	/**
	 * convert {@link String} to {@link Date}
	 * 
	 * @param stringDate
	 * @return
	 */
	public static Date convertStringToDate(String stringDate) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//		DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		try {
			return dateFormat.parse(stringDate);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static Date getStartDateOfCurrentMonth() {
		Calendar calendar = getCalendarForNow();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndDateOfCurrentMonth() {
		Calendar calendar = getCalendarForNow();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	private static Calendar getCalendarForNow() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		return calendar;
	}
}
