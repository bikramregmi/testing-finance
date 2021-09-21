package com.wallet.serviceprovider.bussewa.serviceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.serviceprovider.bussewa.BackToPreviousPage;
import com.wallet.serviceprovider.bussewa.BackToPreviousPageResponse;
import com.wallet.serviceprovider.bussewa.GetRouteList;
import com.wallet.serviceprovider.bussewa.MultiPassengerDetailRequest;
import com.wallet.serviceprovider.bussewa.PassengerDetailInput;
import com.wallet.serviceprovider.bussewa.PassengerDetailInputResponse;
import com.wallet.serviceprovider.bussewa.PaymentConfirmation;
import com.wallet.serviceprovider.bussewa.PaymentConfirmationResponse;
import com.wallet.serviceprovider.bussewa.Refresh;
import com.wallet.serviceprovider.bussewa.RefreshResponse;
import com.wallet.serviceprovider.bussewa.TicketBooking;
import com.wallet.serviceprovider.bussewa.TicketBookingResponse;
import com.wallet.serviceprovider.bussewa.TicketCancellation;
import com.wallet.serviceprovider.bussewa.TicketReconfirmation;
import com.wallet.serviceprovider.bussewa.TicketReconfirmationResponse;
import com.wallet.serviceprovider.bussewa.TripResponse;
import com.wallet.serviceprovider.bussewa.TripSearch;
import com.wallet.serviceprovider.bussewa.TripSearchResponse;
import com.wallet.serviceprovider.bussewa.service.Bus_SewaService;

@Service
public class Bus_SewaServiceImpl implements Bus_SewaService {

	private static final Logger log = LoggerFactory.getLogger(Bus_SewaServiceImpl.class);

	@Override
	public void getRoutes() throws JsonParseException, JsonMappingException, IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = "http://103.233.182.131:5080/customer/webresources/generic/routes";

		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		HttpEntity<String> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, entity,
				String.class);

		ObjectMapper mapper = new ObjectMapper();
		GetRouteList routes = mapper.readValue(response.getBody(), GetRouteList.class);

		log.info(routes.getRoutes().toString());

		log.info(response.getBody());

	}

	@Override
	public void getTrips() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");

		String url = "http://103.233.182.131:5080/customer/webresources/generic/trips";
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-mm-dd");
		String formattedDate = dateFormat.format(calender.getTime());

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		TripSearch tripSearch = new TripSearch();
		tripSearch.setDate("2017-03-27");
		tripSearch.setFrom("Kathmandu");
		tripSearch.setTo("Kakadvitta");
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("from", "Kathmandu");
		// map.put("to", "Kakadvitta");
		// map.put("date", "2017-03-27");

		HttpEntity<TripSearch> request = new HttpEntity<TripSearch>(tripSearch, headers);
		TripSearchResponse response = restTemplate.postForObject(url, request, TripSearchResponse.class);

		try {
			log.info(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// log.info(response.getBody());
	}

	@Override
	public void refresh() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");

		String url = "http://103.233.182.131:5080/customer/webresources/generic/refresh";
		RestTemplate restTemplate = new RestTemplate();

		Refresh refresh = new Refresh();
		refresh.setId("NzM0NjVhZG1pbg");
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("id", "NzM0NjVhZG1pbg");

		HttpEntity<Refresh> request = new HttpEntity<Refresh>(refresh, headers);
		RefreshResponse response = restTemplate.postForObject(url, request, RefreshResponse.class);
		try {
			// log.info(refresh.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info(response.toString());
	}

	@Override
	public void bookTicket(TicketBooking ticketBooking) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");

		RestTemplate restTemplate = new RestTemplate();
		String url = "http://103.233.182.131:5080/customer/webresources/generic/book";

		// Map<String, String> map = new HashMap<String, String>();
		// map.put("id", "NzM0NjVhZG1pbg");
		// map.put("seats", "");
		// map.put("from", "Kathmandu");
		// map.put("to", "Kakadvitta");
		// map.put("signature",
		// "936a64187a4f60f52b61f2f06edb52004e861610b0f5e2df267587804bfc036f");

		HttpEntity<TicketBooking> request = new HttpEntity<TicketBooking>(ticketBooking, headers);

		TicketBookingResponse response = restTemplate.postForObject(url, request, TicketBookingResponse.class);
		try {
			log.info(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void backToPreviousPage(BackToPreviousPage backToPreviousPage) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://103.233.182.131:5080/customer/webresources/generic/cancel";
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("id", "NzM0NjVhZG1pbg");
		// map.put("ticketSrlNo", "25932");

		HttpEntity<BackToPreviousPage> request = new HttpEntity<BackToPreviousPage>(backToPreviousPage, headers);

		BackToPreviousPageResponse response = restTemplate.postForObject(url, request,
				BackToPreviousPageResponse.class);

		try {
			log.info(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void passengerInfo(TripResponse tripResponse, MultiPassengerDetailRequest deatailRequest,
			PassengerDetailInput passengerDetailInput) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://103.233.182.131:5080/customer/webresources/generic/passengerInfo";
		Map<String, String> map = new HashMap<String, String>();

		if (tripResponse.isMultiDetail() == false && tripResponse.getInputField() == "") {

			// map.put("name", "jhappu");
			// map.put("contactNumber", "9847217031");
			// map.put("boardingPoint", "Lainchaur(07:00 AM)");
			// map.put("securityCode", "25950");
			// map.put("ticketSerailNumber", "26412");
			//
			HttpEntity<PassengerDetailInput> request = new HttpEntity<PassengerDetailInput>(passengerDetailInput,
					headers);
			PassengerDetailInputResponse response = restTemplate.postForObject(url, request,
					PassengerDetailInputResponse.class);
			try {
				log.info(response.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (tripResponse.isMultiDetail() == true && tripResponse.getInputField() == "") {
			HttpEntity<MultiPassengerDetailRequest> request = new HttpEntity<MultiPassengerDetailRequest>(
					deatailRequest, headers);
			PassengerDetailInputResponse response = restTemplate.postForObject(url, request,
					PassengerDetailInputResponse.class);

			log.info(response.toString());
		}

	}

	@Override
	public void confirmTicket(PaymentConfirmation paymentConfirmation) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://103.233.182.131:5080/customer/webresources/generic/paymentConfirm";
		HttpEntity<PaymentConfirmation> request = new HttpEntity<PaymentConfirmation>(paymentConfirmation, headers);
		PaymentConfirmationResponse response = restTemplate.postForObject(url, request,
				PaymentConfirmationResponse.class);

		log.info(response.toString());
	}

	@Override
	public void cancelTicket(TicketCancellation ticketCancellation) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://103.233.182.131:5080/customer/webresources/generic/cancelTicket";
		HttpEntity<TicketCancellation> request = new HttpEntity<TicketCancellation>(ticketCancellation, headers);
		TicketReconfirmationResponse response = restTemplate.postForObject(url, request,
				TicketReconfirmationResponse.class);

		log.info(response.toString());

	}

	@Override
	public void reconfirmTicket(TicketReconfirmation ticketReconfirmation) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Basic dXNlckVuZXQ6dXNlckVuZXQ=");
		headers.set("user", "enet");
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://103.233.182.131:5080/customer/webresources/generic/confirmationCheck";
		HttpEntity<TicketReconfirmation> request = new HttpEntity<TicketReconfirmation>(ticketReconfirmation, headers);
		TicketReconfirmationResponse response = restTemplate.postForObject(url, request,
				TicketReconfirmationResponse.class);

		log.info(response.toString());

	}

}
