/**
 * 
 */
package com.mobilebanking.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bibek
 *
 */
public class DateUtil {
	public static boolean isCustomerExpired(Date lastRenewedDate, Date todayDate){
		Calendar lastRenewed = Calendar.getInstance();
		lastRenewed.setTime(lastRenewedDate);
		lastRenewed.add(Calendar.YEAR, 1);
		Calendar today = Calendar.getInstance();
		today.setTime(todayDate);
		if(today.compareTo(lastRenewed)>=0){
			return true;
		}
		return false;
	}
	public static Date convertStringToDate(String dateString) {
		Date date = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			date = dateFormat.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public Date getDateBackByGivenMinutes(int minutes) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());
		calendar.add(calendar.MINUTE, minutes);
		System.out.println("this is converted time" + calendar.getTime());
		return calendar.getTime();
	}

	public static String convertDateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY HH:mm");
		return dateFormat.format(date);
	}

	public static String convertDateToStringForIso(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
		String isoDate = dateFormat.format(date);
		String[] dateArray = isoDate.split("/");
		String newDate = dateArray[0] + dateArray[1];
		String[] newTimeArray = dateArray[2].trim().split(":");
		String newTime = newTimeArray[0].substring(5) + newTimeArray[1] + newTimeArray[2];
		return (newDate + newTime);

	}

	// this is added by amrit to convert the input date of html to Java Date

	public static Date convertStringToDate(String dateString, String format) {
		Date date = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			date = dateFormat.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	// this is to convert angular date to java date
	public static Date getDate(String date) {
		Date newDate = null;

		date = date.replace('/', '-');

		System.out.println("date input :" + date);
		if (date.trim().length() <= 10) {

			String[] dateArray = date.split("-");
			if (dateArray.length != 3) {
				System.out.println("Incorrect input date !!");
				return null;
			} else {
				newDate = new Date();
				if (dateArray[0].length() == 4) {
					System.out.println("year : " + Integer.parseInt(dateArray[0]));
					newDate.setYear(Integer.parseInt(dateArray[0]) - 1900);
					if (dateArray[1].length() < 2) {
						dateArray[1] = "0" + dateArray[1];
					}
					if (dateArray[2].length() < 2) {

						dateArray[2] = "0" + dateArray[2];
					}

					newDate.setMonth(Integer.parseInt(dateArray[1]));
					newDate.setDate(Integer.parseInt(dateArray[2]));
				} else {
					if (dateArray[0].length() < 2) {
						dateArray[0] = "0" + dateArray[0];
					}

					if (dateArray[1].length() < 2) {
						dateArray[1] = "0" + dateArray[1];
					}

					// System.out.println("year :
					// "+Integer.parseInt(dateArray[2]));
					newDate.setYear(Integer.parseInt(dateArray[2]) - 1900);
					newDate.setMonth(Integer.parseInt(dateArray[1]));
					newDate.setDate(Integer.parseInt(dateArray[0]));
					newDate.setHours(00);
					newDate.setMinutes(00);
					newDate.setSeconds(00);
				}
			}
		} else {
			System.out.println("invalid date input !!");
			return null;
		}
		// System.out.println("********************************output date :
		// "+newDate);

		return newDate;

	}// end

	// added on 20 may 2018
	public static Date getDate(String date, DateTypes dateype) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			Date testDate = formatter.parse(date);
			cal.setTime(testDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (dateype.equals(DateTypes.FROM_DATE)) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		}
		DateFormat formatterWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatterWithTime.parse(formatterWithTime.format(cal.getTime()));
		} catch (ParseException e) {
			return null;
		}

	}// end added on 20 may 2018

	// this is to get the current date from the system. in case we need to make
	// certain change here.
	public static Date getCurrentDate() {
		Date todaysDate = new Date();
		// System.out.println("Today ************"+todaysDate);
		return todaysDate;
	}
	
	
	

	public static Date getDateWith0Time() {
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

	public static Date getWeekBackDate() {
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);

		date.setDate(date.getDate() - 7);
		return date;
	}

	public static Date getMonthBackDate() {
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);

		date.setMonth(date.getMonth() - 1);
		return date;
	}
	//added by amrit for nepali date converrsion
	private static Map<Integer, int[]> getNepaliDateMap(){
		Map<Integer, int[]> nepaliMap = new HashMap<Integer, int[]>();

		nepaliMap.put(2000, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2001, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2002, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2003, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2004, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2005, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2006, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2007, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2008, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 });
		nepaliMap.put(2009, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2010, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2011, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2012, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
		nepaliMap.put(2013, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2014, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2015, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2016, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
		nepaliMap.put(2017, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2018, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2019, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2020, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2021, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2022, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
		nepaliMap.put(2023, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2024, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2025, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2026, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2027, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2028, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2029, new int[] { 0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2030, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2031, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2032, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2033, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2034, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2035, new int[] { 0, 30, 32, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 });
		nepaliMap.put(2036, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2037, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2038, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2039, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
		nepaliMap.put(2040, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2041, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2042, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2043, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
		nepaliMap.put(2044, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2045, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2046, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2047, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2048, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2049, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
		nepaliMap.put(2050, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2051, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2052, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2053, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
		nepaliMap.put(2054, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2055, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2056, new int[] { 0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2057, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2058, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2059, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2060, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2061, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2062, new int[] { 0, 30, 32, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31 });
		nepaliMap.put(2063, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2064, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2065, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2066, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 });
		nepaliMap.put(2067, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2068, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2069, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2070, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
		nepaliMap.put(2071, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2072, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2073, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
		nepaliMap.put(2074, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2075, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2076, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
		nepaliMap.put(2077, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
		nepaliMap.put(2078, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2079, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
		nepaliMap.put(2080, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
		nepaliMap.put(2081, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2082, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2083, new int[] { 0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2084, new int[] { 0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2085, new int[] { 0, 31, 32, 31, 32, 30, 31, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2086, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2087, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2088, new int[] { 0, 30, 31, 32, 32, 30, 31, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2089, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
		nepaliMap.put(2090, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
		return nepaliMap;

	}
	
	private static long getTotalNepaliDays(int fromNepYear, int fromNepMonth, int fromNepDay, int toNepYear,int toNepMonth, int toNepDay) {
		long totalNepDaysCount = 0;
		try {
		int nepYearFrom =fromNepYear;

		int nepMonthFrom =fromNepMonth;

		int nepDayFrom = fromNepDay;
		
		int nepYearTo = toNepYear;

		int nepMonthTo = toNepMonth;

		int nepDayTo =toNepDay;
		
      //  System.out.println("from year:"+nepYearFrom+" to year :"+nepYearTo);
        //System.out.println("from month:"+nepMonthFrom+" to month :"+nepMonthTo);
        //System.out.println("from day:"+nepDayFrom+" to day :"+nepDayTo);
		// count total days in-terms of year
		for (int i = nepYearFrom ; i < nepYearTo; i++) {
		      for (int j = 1; j <= 12; j++) {
		            totalNepDaysCount += getNepaliDateMap().get(i)[j];
		      }
		}

		// count total days in-terms of month
		for (int j = nepMonthFrom; j < nepMonthTo; j++) {
		      totalNepDaysCount += getNepaliDateMap().get(nepYearTo)[j];
		}

		// count total days in-terms of date
		totalNepDaysCount += nepDayTo - nepDayFrom;
		}
		catch(Exception e){
			System.out.println("exception occur to count nep date."+ e.getMessage());
			return totalNepDaysCount;
		}
		return totalNepDaysCount;

	}
	
	private static String[] getDayMonthYearArray(String date) {
		if(date.trim().length()<=10) {
			date=date.replace('/','-');
		return	date.split("-");
		}
		else {
			System.out.println("Date length is more than 10 so trimed it to first 10 characters!!");
		     return getDayMonthYearArray(date.substring(0, 10));
		}
	}
	
	
	private static Calendar getCalender(String date) {
		Calendar cal=Calendar.getInstance();
		
			String[] dateArray=getDayMonthYearArray(date);
		    if(dateArray.length!=3) {
		    	System.out.println("Incorrect input date !!");
		    	return null;
		    }
		    else {
		
		    	if(dateArray[0].length()==4) {
		    		//System.out.println("year : "+Integer.parseInt(dateArray[0]));
		    		
		    	     if(dateArray[1].length()<2) {
		    		   dateArray[1]="0"+dateArray[1];
		    	      }
		    			if(dateArray[2].length()<2) {
		    				
		    				dateArray[2]="0"+dateArray[2];
		    			}
		    		
		    		
		    			cal.set(Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1])-1, Integer.parseInt(dateArray[2]));
			    		
		    	}
		    	else {
		    		if(dateArray[0].length()<2) {
		    			dateArray[0]="0"+dateArray[0];
		    		}
		    		
		    			if(dateArray[1].length()<2) {
		    				dateArray[1]="0"+dateArray[1];
		    			}
		    			cal.set(Integer.parseInt(dateArray[2]),Integer.parseInt(dateArray[1])-1, Integer.parseInt(dateArray[0]));
			    		
		    	
		    		}    
		    }
		    
		
		return cal;
	}
	private static int[] getEnglishMonths(boolean leapyear) {

      if(leapyear) {
    	 return new int[] { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
      }
      else {
          return new int[] { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
      }
       
	}
	private static boolean isLeapYear(int year) {
	      if (year % 100 == 0) {
	  
	            return year % 400 == 0;
	      } else {
	            return year % 4 == 0;
	      }
	}
	
	public static Date convertToEnglishDateFromNepaliDate(String nepaliDate, boolean excludeNepaliDay) {
		// calculation of equivalent english date...
		int engYear = 1943;//startingEngYear; means year 2000
		int engMonth = 4;// startingEngMonth; means month baisakh
		int engDay = 14;// startingEngDay; means day 01
		int dayOfWeek = Calendar.WEDNESDAY;
		int endDayOfMonth = 0;
	//	Calendar nepaliCal= getCalender(nepaliDate);
		String[] dateStr=getDayMonthYearArray(nepaliDate);
		if(excludeNepaliDay && dateStr[1]!=null) {
			System.out.println(" Inside exlude nepali date with Year :"+dateStr[0]+" month: "+dateStr[1]+"day is :"+dateStr[2]);
		    dateStr[2]=""+(getNepaliDateMap().get(Integer.parseInt(dateStr[0]))[Integer.valueOf(dateStr[1].trim())]);
		
		}
		
        long totalNepaliDaysCount=getTotalNepaliDays(2000,1,1,Integer.parseInt(dateStr[0]),Integer.parseInt(dateStr[1]),Integer.parseInt(dateStr[2]));
      // System.out.println("the day count of nepali is :"+totalNepaliDaysCount);
		while (totalNepaliDaysCount != 0) {
		     endDayOfMonth=getEnglishMonths(isLeapYear(engYear))[engMonth];
		      engDay++;
		      dayOfWeek++;
		      if (engDay > endDayOfMonth) {
		            engMonth++;
		            engDay = 1;
		            if (engMonth > 12) {
		                  engYear++;
		                  engMonth = 1;
		            }
		      }
		      if (dayOfWeek > 7) {
		            dayOfWeek = 1;
		      }
		      totalNepaliDaysCount--;
		}
		Calendar englishCalindar=Calendar.getInstance();
		englishCalindar.set(engYear, engMonth-1, engDay);
		englishCalindar.set(Calendar.HOUR_OF_DAY,23);
		englishCalindar.set(Calendar.SECOND,59);
		englishCalindar.set(Calendar.MINUTE,59);
	//System.out.println("the english date equivalent is : "+englishCalindar.getTime());
		return englishCalindar.getTime();
		
	}
	
	public static Date convertPreviousMonthOfNepaliDateToEnglish(String date, int numberOfMonthsbefore) {
		//System.out.println("The conversion of date with month number before is: "+numberOfMonthsbefore);
		String[] dateArray=getDayMonthYearArray(date);
		String datePrev="2000-01-01";
		if(dateArray[1]=="1" || dateArray[1]=="01") {
			 datePrev=(Integer.parseInt(dateArray[0])-1)+"-12"+"-30";
		}
		else {
        datePrev=dateArray[0]+"-"+(Integer.parseInt(dateArray[1])-numberOfMonthsbefore)+"-"+dateArray[2];
		}
        return convertToEnglishDateFromNepaliDate(datePrev, true);
	}
	public static Integer countDays(Date lastRenewedDate, Date todayDate) {
		Long diff = todayDate.getTime()-lastRenewedDate.getTime();
		int days = (int) ((((diff/1000)/60)/60)/24);
		return days;
	}
}
