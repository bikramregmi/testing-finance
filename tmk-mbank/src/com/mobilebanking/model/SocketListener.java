package com.mobilebanking.model;

import java.io.IOException;
import java.net.UnknownHostException;

public class SocketListener {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		/*Socket socket = new Socket("10.0.4.59", 445);

		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		while(true)
		{
			SocketListener socketListener = new SocketListener();
			String isoRequest = "";
			try {
				isoRequest = in.readLine();
				System.out.println(isoRequest);
//				String isoResponse = socketListener.validateOtpFromServer(isoRequest);
				
			} catch (IOException e) {
				// error ("System: " + "Connection to server lost!");
				System.exit(1);
				break;
			}
		}*/
		/*Socket socket = new Socket("10.0.4.59", 445);
		NodeServerRequestHandler nsr = new NodeServerRequestHandler(socket);
		 Thread t = new Thread(nsr);
	        t.start();*/
	}

	/*private  String validateOtpFromServer(String isoRequest) {
		HttpHeaders headers = new HttpHeaders();
//		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
//		String url = "https://202.166.198.236/external/new/HTTP_GET_STATUS";

		String url = "https://10.0.4.103/validateOtp/";
		RestTemplate restTemplate = new RestTemplate();
		
		 UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url) .
				 queryParam("isoRequest", isoRequest);
		 
		 try {
			bypassSslCertificate();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		 
		 HttpEntity<String> response=  restTemplate.exchange(builder.buildAndExpand().toUri() , HttpMethod.GET,
				 entity, String.class);
		 
		return response.getBody().toString();
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
		}*/

	
}
