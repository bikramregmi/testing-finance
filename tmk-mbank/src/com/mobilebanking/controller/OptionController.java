package com.mobilebanking.controller;
//package com.remittance.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ws.rs.core.MediaType;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.ObjectWriter;
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.cas.model.AccountDTO;
//import com.cas.model.ResponseStatus;
//import com.cas.model.SuccessResponseDTO;
//import com.remittance.model.RemitChannel;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import org.springframework.stereotype.Controller;
//
//public class OptionController {
//
//	public String accountOption() {
//
//		try {
//			Client client = Client.create();
//			WebResource webResource = client.resource("http:localhost:8080/cas");
//
//			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//
//			if (response.getStatus() != 200)
//				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//
//			String output = response.getEntity(String.class);
//			JSONObject jObject = new JSONObject(output);
//			if (RemitChannel.agent != null) {
//				SuccessResponseDTO successResponse = new SuccessResponseDTO();
//				successResponse.setStatus(ResponseStatus.Success);
//				successResponse.setMessage(jObject.getString("message"));
//				int size = jObject.getJSONArray("details").length();
//				JSONArray jArray = jObject.getJSONArray("details");
//				List<AccountDTO> details = new ArrayList<AccountDTO>();
//				for (int i = 0; i < size; i++) {
//					AccountDTO account = new AccountDTO();
//					account.setAccountNo(jArray.getJSONObject(i).getString("accountNo"));
//					account.setBalance(jArray.getJSONObject(i).getDouble("balance"));
//					account.setCountry(jArray.getJSONObject(i).getString("country"));
//					account.setCurrency(jArray.getJSONObject(i).getString("currency"));
//					details.add(account);
//				}
//				successResponse.setDetails(details);
//
//				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//				String responseString = ow.writeValueAsString(successResponse);
//				System.out.println("Output from Server .... \n");
//				System.out.println(responseString);
//			} else if (RemitChannel.online != null) {
//				SuccessResponseDTO successResponse = new SuccessResponseDTO();
//				successResponse.setStatus(ResponseStatus.Success);
//				successResponse.setMessage(jObject.getString("message"));
//				int size = jObject.getJSONArray("details").length();
//				JSONArray jArray = jObject.getJSONArray("details");
//				List<AccountDTO> details = new ArrayList<AccountDTO>();
//				for (int i = 0; i < size; i++) {
//					AccountDTO account = new AccountDTO();
//					account.setAccountNo(jArray.getJSONObject(i).getString("accountNo"));
//					account.setBalance(jArray.getJSONObject(i).getDouble("balance"));
//					account.setCountry(jArray.getJSONObject(i).getString("country"));
//					account.setCurrency(jArray.getJSONObject(i).getString("currency"));
//					details.add(account);
//				}
//				successResponse.setDetails(details);
//
//				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//				String responseString = ow.writeValueAsString(successResponse);
//				System.out.println("Output from Server .... \n");
//				System.out.println(responseString);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return "redirect:/";
//	}
//
//}
