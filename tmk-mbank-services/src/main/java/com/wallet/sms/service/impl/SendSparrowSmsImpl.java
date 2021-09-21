/**
 * 
 */
package com.wallet.sms.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.wallet.sms.SparrowSmsCreditResponse;
import com.wallet.sms.SparrowSmsResponse;
import com.wallet.sms.service.SendSparrowSmsApi;

/**
 * @author bibek
 *
 */
@Service
public class SendSparrowSmsImpl implements SendSparrowSmsApi {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void sendSparrowSmsApi(String message, String mobileNumber,HashMap<String, String> sparrowDetail) throws UnknownHostException, InvocationTargetException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity(headers);
		RestTemplate restTemplate = new RestTemplate();
		/*String url = "http://api.sparrowsms.com/v2/sms/";		
		String from = "32048";
		String token = "j5Iyyj8fAKCMRnLDdGnt";*/
		String url = sparrowDetail.get("url");
		String from = sparrowDetail.get("from");
		String token = sparrowDetail.get("token");
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).
				queryParam("from", from).queryParam("to", mobileNumber).
				queryParam("text", message).queryParam("token", token);
		try{
			HttpEntity<SparrowSmsResponse> response =  restTemplate.exchange(builder.buildAndExpand().toUri(), 
				HttpMethod.GET, httpEntity, SparrowSmsResponse.class);
			System.out.println(response.getBody().getResponse() + "TEDDGF");
			//SparrowSmsResponse sms = response.getClass();
			logger.info(response.getBody().getResponse() + "TORI");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public Map<String, String> sendSms(String message, String mobileNumber,HashMap<String, String> sparrowDetail)
			throws UnknownHostException, InvocationTargetException {
		Map<String, String> responseMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity(headers);
		RestTemplate restTemplate = new RestTemplate();
		/*String url = "http://api.sparrowsms.com/v2/sms/";		
		String from = "32048";
		String token = "j5Iyyj8fAKCMRnLDdGnt";*/
		String url = sparrowDetail.get("url");
		String from = sparrowDetail.get("from");
		String token = sparrowDetail.get("token");
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).
				queryParam("from", from).queryParam("to", mobileNumber).
				queryParam("text", message).queryParam("token", token);
		HttpEntity<SparrowSmsResponse> response =  restTemplate.exchange(builder.buildAndExpand().toUri(), 
			HttpMethod.GET, httpEntity, SparrowSmsResponse.class);
		System.out.println(response + " RESPONSE");
		logger.info(response.getBody().getResponse() + "Response Test");
		responseMap.put("code", response.getBody().getResponse_code());
		responseMap.put("response", response.getBody().getResponse());
		System.out.println(responseMap +  " this is response map");
		return responseMap;
	}

	@Override
	public Map<String, String> getCredits(HashMap<String, String> sparrowDetail) {
		Map<String, String> responseMap = new HashMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity(headers);
		RestTemplate restTemplate = new RestTemplate();
		/*String url = "http://api.sparrowsms.com/v2/credit/";
		String token = "j5Iyyj8fAKCMRnLDdGnt";*/
		String url = sparrowDetail.get("url");
		String token = sparrowDetail.get("token");
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).
				queryParam("token", token);
		HttpEntity<SparrowSmsCreditResponse> response =  restTemplate.exchange(builder.buildAndExpand().toUri(), 
				HttpMethod.GET, httpEntity, SparrowSmsCreditResponse.class);
		logger.info(response.getBody().getResponse_code() + "Response Code");
		responseMap.put("code", response.getBody().getResponse_code());
		responseMap.put("credits", String.valueOf(response.getBody().getCredits_available()));
		responseMap.put("creditsConsumed", String.valueOf(response.getBody().getCredits_consumed()));
		return responseMap;
	}
				
}
