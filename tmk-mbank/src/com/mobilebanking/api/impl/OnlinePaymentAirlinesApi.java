package com.mobilebanking.api.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mobilebanking.airlines.model.AirlinerOauth;
import com.mobilebanking.airlines.model.ArsNationality;
import com.mobilebanking.airlines.model.FlightAvaibilityWrapper;
import com.mobilebanking.airlines.model.FlightRequestDTO;
import com.mobilebanking.airlines.model.FlightReservationDTO;
import com.mobilebanking.airlines.model.FlightReservationResponse;
import com.mobilebanking.airlines.model.IssueTicketResponse;
import com.mobilebanking.airlines.model.PassengerTotalDto;
import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.api.IMerchantManagerApi;
import com.mobilebanking.api.IOnlinePaymentAirlinesApi;
import com.mobilebanking.entity.AirlinesCommissionManagement;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.MerchantManagerDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.mobile.EnetPaymentResponse;
import com.mobilebanking.plasmatech.FlightAvailabilityRes;
import com.mobilebanking.plasmatech.FlightSector;
import com.mobilebanking.plasmatech.IssueTicketRes;
import com.mobilebanking.plasmatech.Passenger;
import com.mobilebanking.plasmatech.airlines.FlightSectorDetails;
import com.mobilebanking.repositories.AirlinesCommissionManagementRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.util.PdfExporter;
import com.mobilebanking.util.StringConstants;

@Service
public class OnlinePaymentAirlinesApi implements IOnlinePaymentAirlinesApi {

	static String accessToken = null;
	static String refreshToken = null;
	static String tokenType = null;
	static String baseUrl = null;
	static String username = null;
	static String password = null;

	@Autowired
	private IMerchantManagerApi merchantManagerApi;

	@Autowired
	private IMerchantApi merchantApi;

	@Autowired
	private AirlinesCommissionManagementRepository airlinesCommissionManagementRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private IEmailApi emailApi;

	@Autowired
	private PdfExporter pdfExporter;

	private Logger logger = LoggerFactory.getLogger(OnlinePaymentAirlinesApi.class);

	void getApiUrl() {
		MerchantManagerDTO merchantManager = merchantManagerApi.getselected(StringConstants.AIRLINES_BOOKING,
				Status.Active, true);
		MerchantDTO merchantDto = merchantApi.findMerchantById(merchantManager.getMerchantId());
		baseUrl = merchantDto.getApiUrl();
		username = merchantDto.getApiUsername();
		password = merchantDto.getApiPassword();

	}

	@Override
	public Object getSectorCodeAndNationality(HashMap<String, String> hashRequest) throws JAXBException {
		// baseUrl = hashRequest.get("apiurl");
		// username = hashRequest.get("username");
		// password = hashRequest.get("password");

		getApiUrl();

		if (hashRequest.get("mode") == null) {
			hashRequest.put("mode", "both");
		}
		String mode = hashRequest.get("mode");

		if (accessToken == null) {
			getToken();
		}
		HttpHeaders headers = new HttpHeaders();
		// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", tokenType + " " + accessToken);
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<RestResponseDTO> response;
		FlightSectorDetails flightDetails = new FlightSectorDetails();
		String url = baseUrl + "/rest/api/v1/arssectorcode";
		HashMap<String, Object> hashMap = new HashMap<>();
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		try {
			if (mode.equals("sectorCode") || mode.equals("both")) {

				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.POST, httpEntity,
						RestResponseDTO.class);

				if (response.getBody().getStatus().equalsIgnoreCase("S00")) {
					InputSource is = new InputSource(new StringReader(response.getBody().getDetail().toString()));
					JAXBContext jaxbContext;
					jaxbContext = JAXBContext.newInstance(FlightSector.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					FlightSector sectorCode = (FlightSector) jaxbUnmarshaller.unmarshal(is);
					if (mode.equals("sectorCode")) {
						return sectorCode;
					}
					hashMap.put("sectorCode", sectorCode);
				} else {
					return null;
				}
			}
			if (mode.equals("nationality") || mode.equals("both")) {
				url = baseUrl + "/rest/api/v1/arsnationality";
				builder = UriComponentsBuilder.fromUriString(url);
				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.POST, httpEntity,
						RestResponseDTO.class);
				if (response.getBody().getStatus().equalsIgnoreCase("S00")) {
					hashMap.put("nationality", response.getBody().getDetail());
					ObjectMapper objectMapper = new ObjectMapper();
					// Set pretty printing of json
					objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
					Gson gson = new Gson();
					// ArsNationality nationalityResponse =
					// gson.fromJson(gson.toJson(response.getBody().getDetail(),LinkedHashMap.class),ArsNationality.class);
					// List<ArsNationality> nationalityResponse =
					// (ArrayList<ArsNationality>)
					// gson.fromJson(gson.toJson(response.getBody().getDetail(),LinkedHashMap.class),
					// new TypeToken<ArrayList<ArsNationality>>() {
					// }.getType());
					String json = gson.toJson(response.getBody().getDetail());
					// List<ArsNationality> list =
					// gson.fromJson(gson.toJson(gson.toJson((response.getBody().getDetail()),
					// new TypeToken<List<ArsNationality>>(){}.getType()));

					ArsNationality[] nationalityResponse = objectMapper.readValue(json, ArsNationality[].class);

					if (mode.equals("nationality")) {
						return nationalityResponse;
					}
				} else
					return null;

			} else
				return null;

			flightDetails.setSectorAndNationalityDetail(hashMap);
			return flightDetails;
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				Boolean getToken = refreshToken();
				if (getToken == true)
					getSectorCodeAndNationality(hashRequest);
				else {
					accessToken = null;
					return null;
				}
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FlightAvailabilityRes getAvailableFlights(FlightRequestDTO flightRequestDto, Boolean isApp) {
		if (accessToken == null) {
			getToken();
		}
		HttpHeaders headers = new HttpHeaders();
		// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", tokenType + " " + accessToken);
		headers.set("Content-Type", "application/json");
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		if (!isApp) {
			DateTime flightDate = new DateTime(flightRequestDto.getDepartureDate());
			flightRequestDto.setDepartureDate(format.format(flightDate.toDate()));
			// String depatureDate = format.format(flightDate.toDate());
			DateTime returnDate = new DateTime(flightRequestDto.getArrivalDate());
			flightRequestDto.setArrivalDate(format.format(returnDate.toDate()));
		}

		// String returnDate1 = format.format(returnDate.toDate());

		String tripType;
		if (!isApp) {
			if (flightRequestDto.getIsRoundTrip() == true) {
				// tripType = "R";
				flightRequestDto.setTripType("R");
			} else {
				flightRequestDto.setTripType("O");
				// tripType = "O";

			}
		}

		String url = baseUrl + "/rest/api/v1/arsflightavailabilityinternal";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		// HttpEntity<FlightAvaibilityRequest> request = new
		// HttpEntity<>(flightRequest , headers);

		// FlightAvaibilityResonseDTO response = restTemplate.postForObject(url,
		// request, FlightAvaibilityResonseDTO.class);
		/*
		 * UriComponentsBuilder builder =
		 * UriComponentsBuilder.fromUriString(url).queryParam("flight_date",
		 * depatureDate) .queryParam("return_date",
		 * returnDate1).queryParam("sector_from", flightRequestDto.getSource())
		 * .queryParam("sector_to",
		 * flightRequestDto.getDestination()).queryParam("trip_type", tripType)
		 * .queryParam("adult_number", flightRequestDto.getAdult())
		 * .queryParam("child_number", flightRequestDto.getChild())
		 * .queryParam("nationality", flightRequestDto.getNationality());
		 */

		HttpEntity<FlightRequestDTO> request = new HttpEntity<FlightRequestDTO>(flightRequestDto, headers);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		/*
		 * try { String json = ow.writeValueAsString(flightRequestDto); //
		 * System.out.println(json); } catch (JsonProcessingException e1) { //
		 * TODO Auto-generated catch block e1.printStackTrace(); }
		 */

		try {
			HttpEntity<RestResponseDTO> response = restTemplate.exchange(builder.buildAndExpand().toUri(),
					HttpMethod.POST, request, RestResponseDTO.class);
			if (response.getBody().getStatus().equalsIgnoreCase("S00")) {
				Gson gson = new Gson();
				String jsonString = gson.toJson(response.getBody().getDetail());
				FlightAvaibilityWrapper flightAvailablitywrapper = gson.fromJson(jsonString,
						FlightAvaibilityWrapper.class);

				// ObjectMapper mapper = new ObjectMapper();
				//
				// flightAvailablity = mapper.readValue(jsonString,
				// FlightAvailabilityRes.class);
				return flightAvailablitywrapper.getFlightAvailability();
			} else
				return null;

		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				Boolean getToken = refreshToken();
				if (getToken == true)
					getAvailableFlights(flightRequestDto, isApp);
				else {
					accessToken = null;
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public FlightReservationResponse reserveFlight(FlightReservationDTO flightRequestDto, Long customerId) {
		if (accessToken == null) {
			getToken();
		}
		HttpHeaders headers = new HttpHeaders();
		// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", tokenType + " " + accessToken);
		headers.set("Content-Type", "application/json");
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();

		String url = baseUrl + "/rest/api/v1/arsflightreservationinternal";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

		HttpEntity<FlightReservationDTO> request = new HttpEntity<FlightReservationDTO>(flightRequestDto, headers);
		try {

			HttpEntity<RestResponseDTO> response = restTemplate.exchange(builder.buildAndExpand().toUri(),
					HttpMethod.POST, request, RestResponseDTO.class);
			if (response.getBody().getStatus().equalsIgnoreCase("S00")) {

				Gson gson = new Gson();
				FlightReservationResponse flightReservation = gson.fromJson(
						gson.toJson(response.getBody().getDetail(), LinkedHashMap.class),
						FlightReservationResponse.class);
				Customer customer = customerRepository.findOne(customerId);
				AirlinesCommissionManagement airlinesCommission = airlinesCommissionManagementRepository
						.findByCustomer(customer);
				if (airlinesCommission == null) {
					airlinesCommission = new AirlinesCommissionManagement();
					airlinesCommission.setCustomer(customer);
					airlinesCommission.setCommissionAmount(Double.parseDouble(flightRequestDto.getCashBack()));
					airlinesCommissionManagementRepository.save(airlinesCommission);
				} else {
					airlinesCommission.setCommissionAmount(Double.parseDouble(flightRequestDto.getCashBack()));
					airlinesCommissionManagementRepository.save(airlinesCommission);
				}

				return flightReservation;
			} else
				return null;

		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				Boolean getToken = refreshToken();
				if (getToken == true)
					reserveFlight(flightRequestDto, customerId);
				else {
					accessToken = null;
					return null;
				}
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<String, Object> executePayment(HashMap<String, String> myHash, PassengerTotalDto pessangerDto) {

		HashMap<String, Object> hashResponse = new HashMap<>();
		HashMap<String, String> airliesResponse = new HashMap<>();
		if (accessToken == null) {
			getToken();
		}
		HttpHeaders headers = new HttpHeaders();
		// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", tokenType + " " + accessToken);
		headers.set("Content-Type", "application/json");
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();

		pessangerDto.setMobilePin("1234");
		pessangerDto.setServiceIdentifier("ARS");
		pessangerDto.setChannel("MOBILE");
		String contactEmail = pessangerDto.getEmail();
		pessangerDto.setEmail("");
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + "/rest/api/v1/arsissueticket");
		HttpEntity<PassengerTotalDto> request = new HttpEntity<PassengerTotalDto>(pessangerDto, headers);

		try {

			HttpEntity<IssueTicketResponse> response = restTemplate.exchange(builder.buildAndExpand().toUri(),
					HttpMethod.POST, request, IssueTicketResponse.class);
			if (response.getBody().getStatus().equalsIgnoreCase("S00")) {
				response.getBody().setStatus("success");
				String filePath = StringConstants.OS_LINUX_DIRECTORY + "/" + "arsTickets/";
				Gson gson = new Gson();
				IssueTicketRes issueTicketResponse = response.getBody().getTicketResponse();

				hashResponse.put("status", "success");
				hashResponse.put("issueTicket", issueTicketResponse);
				airliesResponse.put("status", "success");
				airliesResponse.put("Result Message", response.getBody().getMessage());
				airliesResponse.put("Key", response.getBody().getStatus());

				String ticketResponse = gson.toJson(response.getBody().getDetail());
				EnetPaymentResponse enetPaymentResponse = gson.fromJson(ticketResponse, EnetPaymentResponse.class);
				airliesResponse.put("RefStan", enetPaymentResponse.getTransactionIdentifier());
				airliesResponse.put("ticketUrl", "mbank/airlinesPdfUrl/" + enetPaymentResponse.getTransactionIdentifier() + ".pdf");
				System.out.println(airliesResponse.get("ticketUrl"));
				hashResponse.put("airlinesResponse", airliesResponse);

				// downloadTicket(baseUrl +
				// enetPaymentResponse.getResponseDetail().getDownloadTicketUrl(),
				// enetPaymentResponse.getTransactionIdentifier());
				exportPdf(issueTicketResponse, enetPaymentResponse.getTransactionIdentifier());
				emailApi.sendAirlinesTicketEmail(enetPaymentResponse.getTransactionIdentifier(), contactEmail);
			} else {
				hashResponse.put("status", "faliure");
				airliesResponse.put("status", "faliure");
				airliesResponse.put("Result Message", response.getBody().getMessage());
				airliesResponse.put("Key", response.getBody().getStatus());
				hashResponse.put("airlinesResponse", airliesResponse);
			}

		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				Boolean getToken = refreshToken();
				if (getToken == true)
					executePayment(myHash, pessangerDto);
				else {
					accessToken = null;
					hashResponse.put("status", "faliure");
					airliesResponse.put("status", "faliure");
					hashResponse.put("Result Message", "Unable to issue ticket, please try again later.");
					hashResponse.put("airlinesResponse", airliesResponse);
				}
			} else {

				hashResponse.put("status", "faliure");
				airliesResponse.put("status", "faliure");
				hashResponse.put("Result Message", "Unable to issue ticket, please try again later.");
				hashResponse.put("airlinesResponse", airliesResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			hashResponse.put("status", "faliure");
			airliesResponse.put("status", "faliure");
			hashResponse.put("Result Message", "Something went wrong, please try again later.");
			hashResponse.put("airlinesResponse", airliesResponse);
		}
		return hashResponse;

	}

	public static void main(String[] args) {
		OnlinePaymentAirlinesApi api = new OnlinePaymentAirlinesApi();
		api.downloadTicket("http://dev.enetpayment.com/downloadarstickets/291525261354493.pdf", "ticket");
	}

	private void exportPdf(IssueTicketRes issueTicketResponse, String transactionId)
			throws DocumentException, MalformedURLException, IOException {

		String airlineName = calculateAirlineName(issueTicketResponse.getPassenger().get(0).getAirline());
		if (airlineName != null) {

			logger.info("Creating ARS Ticket PDF");
			// do here
			Document document = new Document(PageSize.A4);
			String path = getBasePath(transactionId + ".pdf", "arsTickets");
			PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();

			String imageFile = getBasePath("companylogo.jpg", "arsTickets");
			Image img = Image.getInstance(imageFile);

			img.scaleAbsoluteHeight(40);
			img.scaleAbsoluteWidth(138);

			String airlineLogo = getBasePath(issueTicketResponse.getPassenger().get(0).getAirline() + ".jpg",
					"arsTickets");
			Image airlineImg = Image.getInstance(airlineLogo);

			airlineImg.scaleAbsoluteHeight(40);
			airlineImg.scaleAbsoluteWidth(200);

			for (Passenger passenger : issueTicketResponse.getPassenger()) {

				PdfPTable table0 = new PdfPTable(2);
				table0.setWidthPercentage(100);
				table0.setHorizontalAlignment(Element.ALIGN_LEFT);
				PdfPCell cellOne = new PdfPCell(img);
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);
				table0.addCell(cellOne);

				PdfPCell cellAirline = new PdfPCell(airlineImg);
				cellAirline.setBorder(Rectangle.NO_BORDER);
				cellAirline.setHorizontalAlignment(Element.ALIGN_RIGHT);

				table0.addCell(cellAirline);
				document.add(table0);

				document = pdfExporter.forSinglePageTicket(document, passenger, transactionId, "Complete");

			}

			document.close();
			logger.info("Finish Creating ARS Ticket PDF");
			// return path;

		}
	}

	private String getArsTicketPath(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getBasePath(String fileName, String folder) {
		String filePath = StringConstants.OS_LINUX_DIRECTORY + "/" + folder;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		filePath += "/" + fileName;
		return filePath;
	}

	public String calculateAirlineName(String airlineCode) {
		if (airlineCode.equals("RMK")) {
			return "Simrik Airlines";
		} else if (airlineCode.equals("U4")) {
			return "Buddha Air";
		} else if (airlineCode.equals("S1")) {
			return "Saurya Airlines";
		} else if (airlineCode.equals("YT")) {
			return "Yeti Airlines";
		} else if (airlineCode.equals("GA")) {
			return "Goma Airlines";
		} else if (airlineCode.equals("NA")) {
			return "Nepal Airlines";
		} else {
			return airlineCode;
		}

	}

	private void downloadTicket(String downloadTicketUrl, String fileName) {
		try {
			URL url = new URL(downloadTicketUrl);
			InputStream in = url.openStream();
			String filePath = StringConstants.OS_LINUX_DIRECTORY + "/" + "arsTickets";
			if (!StringConstants.IS_PRODUCTION)
				Files.copy(in, Paths.get("E:\\airticket.pdf"), StandardCopyOption.REPLACE_EXISTING);
			else
				Files.copy(in, Paths.get(filePath + "/" + fileName + ".pdf"), StandardCopyOption.REPLACE_EXISTING);
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean getToken() {
		getApiUrl();
		HttpHeaders headers = new HttpHeaders();
		// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity(headers);
		RestTemplate restTemplate = new RestTemplate();
		String url = baseUrl + "/oauth/token";
		String grant_type = "password";
		String client_id;
		String client_secret;
		if(StringConstants.IS_PRODUCTION_AIRLINES){
			client_id = "IDV9CTSLUA";
			client_secret = "174198";
		}else{
			client_id = "A76U2Z7DKL";
			client_secret = "186456";
		}
		// String client_id = "A76U2Z7DKL";
		// String client_secret = "186456";
		// String username = username;
		// String password = "gfdsa@";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("grant_type", grant_type)
				.queryParam("client_id", client_id).queryParam("client_secret", client_secret)
				.queryParam("username", username).queryParam("password", password);
		try {
			HttpEntity<AirlinerOauth> response = restTemplate.exchange(builder.buildAndExpand().toUri(),
					HttpMethod.POST, httpEntity, AirlinerOauth.class);
			accessToken = response.getBody().getAccess_token();
			refreshToken = response.getBody().getRefresh_token();
			tokenType = response.getBody().getToken_type();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public Boolean refreshToken() {
		getApiUrl();
		HttpHeaders headers = new HttpHeaders();
		// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity(headers);
		RestTemplate restTemplate = new RestTemplate();
		String url = baseUrl + "/oauth/token";
		String grant_type = "refresh_token";
		String client_id;
		String client_secret;
		if(StringConstants.IS_PRODUCTION_AIRLINES){
			client_id = "IDV9CTSLUA";
			client_secret = "174198";
		}else{
			client_id = "A76U2Z7DKL";
			client_secret = "186456";
		}
		// String client_id = "A76U2Z7DKL";
		// String client_secret = "186456";
		// String username = username;
		// String password = "gfdsa@";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("grant_type", grant_type)
				.queryParam("client_id", client_id).queryParam("client_secret", client_secret)
				.queryParam("username", username).queryParam("password", password)
				.queryParam("refresh_token", refreshToken);
		try {
			HttpEntity<AirlinerOauth> response = restTemplate.exchange(builder.buildAndExpand().toUri(),
					HttpMethod.POST, httpEntity, AirlinerOauth.class);
			accessToken = response.getBody().getAccess_token();
			refreshToken = response.getBody().getRefresh_token();
			tokenType = response.getBody().getToken_type();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

	}

}
