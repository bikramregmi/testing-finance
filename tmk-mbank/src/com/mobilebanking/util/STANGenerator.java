/**
 * 
 */
package com.mobilebanking.util;

import java.util.Random;

/**
 * @author bibek
 *
 */
public class STANGenerator {
	
	public static String StanForIso(String prefix) {
		String stan = prefix;
		final String characters = "1234567890";
		final int charactesLength = characters.length();
	    Random random = new Random();
	    if (prefix.equals("")) {
	    	for (int i=0;i<6;i++) {
				stan = stan + characters.charAt(random.nextInt(charactesLength));
			}
	    } else {
	    	for (int i=0;i<6-prefix.length();i++) {
				stan = stan + characters.charAt(random.nextInt(charactesLength));
			}
	    }
		System.out.println(stan + " STAN ");
		return stan;
	}

}
