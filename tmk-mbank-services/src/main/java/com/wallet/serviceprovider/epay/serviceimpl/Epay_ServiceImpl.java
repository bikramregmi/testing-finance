package com.wallet.serviceprovider.epay.serviceimpl;

import java.io.StringReader;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.InputSource;

import com.wallet.serviceprovider.bussewa.Refresh;
import com.wallet.serviceprovider.bussewa.RefreshResponse;
import com.wallet.serviceprovider.epay.OnlinePaymentRequest;
import com.wallet.serviceprovider.epay.RequestQueryResponse;
import com.wallet.serviceprovider.epay.service.Epay_Service;
@Service
public class Epay_ServiceImpl implements Epay_Service {
	
	
	 private static final Logger log = LoggerFactory.getLogger(Epay_ServiceImpl.class);
	 
	 
	@Override
	public void getBalance() throws UnknownHostException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String url = "https://202.166.198.236/external/new/HTTP_GET_BALANCE";

		RestTemplate restTemplate = new RestTemplate();
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url) .
				 queryParam("P_LOGIN_NAME", "gateway")
			        .queryParam("P_LOGIN_PASSWD", "hbeoLp6HRT");
		 try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		 HttpEntity<String> response=  restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.GET,
				 entity, String.class);
		 
	        log.info(response.getBody());
	        try {
	        InputSource is = new InputSource(new StringReader(response.getBody()));
	        JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(RequestQueryResponse.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			RequestQueryResponse requestQueryResponse = (RequestQueryResponse) jaxbUnmarshaller.unmarshal(is);
			//Customer customer = (Customer) jaxbUnmarshaller.unmarshal(file);
			System.out.println(requestQueryResponse.getAccount().getAmount());
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

	}
	
	
	@Override
	public void getStatus() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String url = "https://202.166.198.236/external/new/HTTP_GET_STATUS";

		RestTemplate restTemplate = new RestTemplate();
		
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url) .
				 queryParam("P_LOGIN_NAME", "gateway")
			        .queryParam("P_LOGIN_PASSWD", "hbeoLp6HRT")
			        .queryParam("P_RECEIPT_NUM", "1");
		 
		 try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		 
		 HttpEntity<String> response=  restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.GET,
				 entity, String.class);
		 log.info(response.getBody());
		 
		 try {
		        InputSource is = new InputSource(new StringReader(response.getBody()));
		        JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(RequestQueryResponse.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				RequestQueryResponse requestQueryResponse = (RequestQueryResponse) jaxbUnmarshaller.unmarshal(is);
				
				System.out.println(requestQueryResponse.getError().getSqlcode());
				} catch (JAXBException e1) {
					e1.printStackTrace();
				}
	}

	@Override
	public void addPayment() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String url = "https://202.166.198.236/external/new/HTTP_ADD_PAYMENT";

		RestTemplate restTemplate = new RestTemplate();
		
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url) .
				 queryParam("P_LOGIN_NAME", "gateway")
			        .queryParam("P_LOGIN_PASSWD", "hbeoLp6HRT").
			        queryParam("P_SERVICE", "221")
			        .queryParam("P_MSISDN", "9800019082");
		 
//		 Map<String, Long> uriParams = new HashMap<String, Long>();
//		 uriParams.put("P_SERVICE ", (long) 221);
//		 uriParams.put("P_MSISDN ", Long.parseLong("9800019082"));
		 
		 try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		 
		 HttpEntity<String> response=  restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.POST,
				 entity, String.class);
		 log.info(response.getBody());
	}
	
	@Override
	public void addPayment(HashMap<String, String> myhash) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String url = "https://202.166.198.236/external/new/HTTP_ADD_PAYMENT";

		RestTemplate restTemplate = new RestTemplate();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());
		
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url) .
				 queryParam("P_LOGIN_NAME", "gateway")
			        .queryParam("P_LOGIN_PASSWD", "hbeoLp6HRT").
			        queryParam("P_SERVICE", "221")
			        .queryParam("P_MSISDN", "9800019082")
			        .queryParam("P_RECEIPT_NUM", "1")
			        .queryParam("P_PAY_AMOUNT", myhash.get("amount"))
			        .queryParam("P_DATE",formattedDate);
		 
		 try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		 
		 HttpEntity<String> response=  restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.POST,
				 entity, String.class);
		 log.info(response.getBody());
		
	}
	
	@Override
	public void onlinePayment(HashMap<String, String> myhash) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String url = "https://202.166.198.236/external/new/HTTP_ONLINE_PAYMENT";

		RestTemplate restTemplate = new RestTemplate();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url) .
				 queryParam("P_LOGIN_NAME", "gateway")
			        .queryParam("P_LOGIN_PASSWD", "hbeoLp6HRT").
			        queryParam("P_SERVICE", "221")
			        .queryParam("P_MSISDN", "9800019082")
			        .queryParam("P_PAY_AMOUNT", myhash.get("amount"))
			        //.queryParam("P_PAY_AMOUNT", "10")
			        .queryParam("P_RECEIPT_NUM", "1")
			        .queryParam("P_DATE",formattedDate);
		 
		 try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		 
		 HttpEntity<String> response=  restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.POST,
				 entity, String.class);
		 log.info(response.getBody());
		
	}

	
	public void bypassSslCertificate() throws NoSuchAlgorithmException, KeyManagementException{
		
		// Create a trust manager that does not validate certificate chains
		
	    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	    };

	    // Install the all-trusting trust manager
	    SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	    // Create all-trusting host name verifier
	    HostnameVerifier allHostsValid = new HostnameVerifier() {
	        public boolean verify(String hostname, SSLSession session) {
	            return true;
	        }
	    };

	    // Install the all-trusting host verifier
	    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		}


	@Override
	public void onlinePaymentTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String url = "https://202.166.198.236/external/new/HTTP_ONLINE_PAYMENT";

		RestTemplate restTemplate = new RestTemplate();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url) .
				 queryParam("P_LOGIN_NAME", "gateway")
			        .queryParam("P_LOGIN_PASSWD", "hbeoLp6HRT").
			        queryParam("P_SERVICE", "221")
			        .queryParam("P_MSISDN", "9800019082")
			        //.queryParam("P_PAY_AMOUNT", myhash.get("amount"))
			        .queryParam("P_PAY_AMOUNT", "10")
			        .queryParam("P_RECEIPT_NUM", "1")
			        .queryParam("P_DATE",formattedDate); 
		 
		 try {
				bypassSslCertificate();
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
	
		 HttpEntity<String> response=  restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.POST,
				 entity, String.class);
		 log.info(response.getBody());
		
		 
		 
		 
//
//		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//		String formattedDate = dateFormat.format(new Date());
//		String url = "https://202.166.198.236/external/new/HTTP_ONLINE_PAYMENT";
//		RestTemplate restTemplate = new RestTemplate();
//		OnlinePaymentRequest online = new OnlinePaymentRequest();
//		online.setP_DATE(formattedDate);
//		online.setP_LOGIN_NAME( "gateway");
//		online.setP_LOGIN_PASSWD("hbeoLp6HRT");
//		online.setP_MSISDN("9800019082");
//		online.setP_PAY_AMOUNT("10");
//		online.setP_SERVICE("221");
//		online.setP_RECEIPT_NUM("1");
//		
//		HttpEntity<OnlinePaymentRequest> request = new HttpEntity<OnlinePaymentRequest>(online, headers);
//		
//		try {
//			bypassSslCertificate();
//		} catch (KeyManagementException | NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		
//		RequestQueryResponse response = restTemplate.postForObject(url, request, RequestQueryResponse.class);
//		 log.info(response.getOPERATION_DATE());
//		
	}






}
