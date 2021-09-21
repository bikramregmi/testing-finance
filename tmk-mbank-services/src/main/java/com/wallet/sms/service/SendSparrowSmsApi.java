/**
 * 
 */
package com.wallet.sms.service;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bibek
 *
 */
public interface SendSparrowSmsApi {
	
	void sendSparrowSmsApi(String message, String mobileNumber,HashMap<String, String> sparrowDetail) throws UnknownHostException, InvocationTargetException;
	
	Map<String, String> sendSms(String message, String mobileNumber,HashMap<String, String> sparrowDetail) throws UnknownHostException, InvocationTargetException;
	
	Map<String, String> getCredits(HashMap<String, String> sparrowDetail);
}
