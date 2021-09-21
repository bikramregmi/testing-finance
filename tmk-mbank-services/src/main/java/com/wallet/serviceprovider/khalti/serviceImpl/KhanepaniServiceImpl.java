package com.wallet.serviceprovider.khalti.serviceImpl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wallet.serviceprovider.khalti.CustomErrorHandler;
import com.wallet.serviceprovider.khalti.KhanepaniCounter;
import com.wallet.serviceprovider.khalti.KhanepaniCounterResponse;
import com.wallet.serviceprovider.khalti.KhanepaniCustomerDetailResponse;
import com.wallet.serviceprovider.khalti.KhanepaniPaymentResponse;
import com.wallet.serviceprovider.khalti.KhanepaniServiceChargeResponse;
import com.wallet.serviceprovider.khalti.service.KhanepaniService;

@Service
public class KhanepaniServiceImpl implements KhanepaniService {

	/*
	 * public static void main(String[] args) { KhanepaniServiceImpl service =
	 * new KhanepaniServiceImpl(); HashMap<String, String> requestHash = new
	 * HashMap<>(); requestHash.put("apiurl",
	 * "https://services.khalti.com/api/"); requestHash.put("transactionId",
	 * "123456"); requestHash.put("serviceTo", "1235"); requestHash.put("token",
	 * "TEST:mXAbZwcDMafYgf9YyMci");
	 * System.out.println(service.payKhanepaniBill(requestHash)); }
	 */

	@Override
	public List<KhanepaniCounter> getCounters(HashMap<String, String> requestHash) {
		Gson gson = new GsonBuilder().create();
		HttpHeaders headers = new HttpHeaders();
		String url = requestHash.get("url") + "/servicegroup/counters/khanepani/";
		requestHash.remove("url");
		requestHash.replace("token", "MqdMOowQ2L1xclAPYN7C");
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(gson.toJson(requestHash), headers);
		RestTemplate restTemplate = new RestTemplate();

		try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		HttpEntity<KhanepaniCounterResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				KhanepaniCounterResponse.class);
		return response.getBody().getCounters();

	}

	@Override
	public KhanepaniCustomerDetailResponse getCustomerDetail(HashMap<String, String> requestHash) {
		Gson gson = new GsonBuilder().create();
		HttpHeaders headers = new HttpHeaders();
		HashMap<String, String> serviceChargeRequest = new HashMap<>();
		serviceChargeRequest.put("url", requestHash.get("url"));
		serviceChargeRequest.put("token", requestHash.get("token"));
		serviceChargeRequest.put("counter", requestHash.get("counter"));
		String url = requestHash.get("url") + "/servicegroup/details/khanepani/";
		requestHash.remove("url");
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(gson.toJson(requestHash), headers);
		RestTemplate restTemplate = new RestTemplate();
		try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			HttpEntity<KhanepaniCustomerDetailResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
					KhanepaniCustomerDetailResponse.class);
			if (response.getBody().getTotal_dues().equals(0)) {
				serviceChargeRequest.put("amount", response.getBody().getTotal_dues());
				response.getBody().setService_charge(getServiceCharge(serviceChargeRequest).getService_charge());
			}
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<String, String> payKhanepaniBill(HashMap<String, String> myHash) {
		Gson gson = new GsonBuilder().create();
		HttpHeaders headers = new HttpHeaders();
		String url = myHash.get("apiurl") + "/servicegroup/commit/khanepani/";
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		myHash.put("reference", myHash.get("transactionId"));
		myHash.put("customer_code", myHash.get("serviceTo"));
		myHash.remove("transactionId");
		myHash.remove("serviceTo");
		myHash.remove("apiurl");
		myHash.remove("serviceId");
		System.out.println(gson.toJson(myHash).toString());
		HttpEntity<?> entity = new HttpEntity<>(gson.toJson(myHash), headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new CustomErrorHandler());
		try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		HashMap<String, String> hashResponse = new HashMap<>();
		try {
			HttpEntity<KhanepaniPaymentResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
					KhanepaniPaymentResponse.class);
			gson = new GsonBuilder().setPrettyPrinting().create();
			System.out.println(gson.toJson(response).toString());
			if (response.getBody().getState().equalsIgnoreCase("success")) {
				hashResponse.put("status", "success");
				hashResponse.put("RefStan", response.getBody().getId());
				hashResponse.put("Result Message", "" + response.getBody().getMessage());
				hashResponse.put("dueAmount", response.getBody().getDue_amount());
			} else if (response.getBody().getError_code().equals("1010")
					|| response.getBody().getError_code().equals("1011")) {
				hashResponse.put("status", "failure");
				hashResponse.put("Key", "" + response.getBody().getError_code());
				hashResponse.put("Result Message", "" + response.getBody().getError_data().toString());
			} else {
				hashResponse.put("status", "failure");
				hashResponse.put("Key", "" + response.getBody().getError_code());
				hashResponse.put("Result Message", "" + response.getBody().getMessage());
			}
		} catch (Exception e) {
			hashResponse.put("status", "failure");
			hashResponse.put("Result Message", e.getMessage());
		}
		return hashResponse;
	}

	@Override
	public KhanepaniServiceChargeResponse getServiceCharge(HashMap<String, String> requestHash) {
		Gson gson = new GsonBuilder().create();
		HttpHeaders headers = new HttpHeaders();
		String url = requestHash.get("url") + "/servicegroup/servicecharge/khanepani/";
		requestHash.remove("url");
		requestHash.replace("token", "MqdMOowQ2L1xclAPYN7C");
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(gson.toJson(requestHash), headers);
		RestTemplate restTemplate = new RestTemplate();

		try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		HttpEntity<KhanepaniServiceChargeResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				KhanepaniServiceChargeResponse.class);
		return response.getBody();
	}

	public void bypassSslCertificate() throws NoSuchAlgorithmException, KeyManagementException {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };
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
}
