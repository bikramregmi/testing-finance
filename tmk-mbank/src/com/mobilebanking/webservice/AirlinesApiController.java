package com.mobilebanking.webservice;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.airlines.model.ArsNationality;
import com.mobilebanking.airlines.model.FlightAvaibilityWrapper;
import com.mobilebanking.airlines.model.FlightRequestDTO;
import com.mobilebanking.airlines.model.FlightReservationDTO;
import com.mobilebanking.airlines.model.FlightReservationResponse;
import com.mobilebanking.airlines.model.PassengerTotalDto;
import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.api.IMerchantManagerApi;
import com.mobilebanking.api.IMerchantPaymentRefactorApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.api.IOnlinePaymentAirlinesApi;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.MerchantManagerDTO;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.mobile.AirlinesResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.plasmatech.FlightAvailabilityRes;
import com.mobilebanking.plasmatech.FlightSector;
import com.mobilebanking.plasmatech.PassengerDto;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.MPinVaidation;


@Controller
@RequestMapping("/api")
public class AirlinesApiController {

	@Autowired
	private IMerchantManagerApi merchantManagerApi;

	@Autowired
	private IMerchantApi merchantApi;

	@Autowired
	private IOnlinePaymentAirlinesApi airlinesApi;

	@Autowired
	private IMerchantPaymentRefactorApi merchantPaymentService;

	@Autowired
	private MPinVaidation mPinValidation;

	@Autowired
	private IMerchantServiceApi serviceApi;

	@RequestMapping(method = RequestMethod.POST, value = "/arsnationality", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<RestResponseDTO> getArsNationality(HttpServletRequest request) {
		RestResponseDTO response = new RestResponseDTO();
		try {

			if (request.getHeader("Authorization") == null) {
				response.setResponseStatus(ResponseStatus.FAILURE);
				response.setStatus("FAILURE");
				response.setMessage("Authorization not valid or empty");
				return new ResponseEntity<RestResponseDTO>(response, HttpStatus.BAD_REQUEST);
			}
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
					MerchantManagerDTO merchantManager = merchantManagerApi
							.getselected(StringConstants.AIRLINES_BOOKING, Status.Active, true);
					MerchantDTO merchantDto = merchantApi.findMerchantById(merchantManager.getMerchantId());
					HashMap<String, String> hashRequest = new HashMap<>();
					hashRequest.put("apiurl", merchantDto.getApiUrl());
					hashRequest.put("username", merchantDto.getApiUsername());
					hashRequest.put("password", merchantDto.getApiPassword());
					hashRequest.put("mode", "nationality");
					ArsNationality[] nationality = (ArsNationality[]) airlinesApi
							.getSectorCodeAndNationality(hashRequest);

					if (nationality != null && nationality.length > 0) {
						response.setResponseStatus(ResponseStatus.SUCCESS);
						response.setStatus(ResponseStatus.SUCCESS.getValue());
						response.setMessage("Nationality List Retrieved Successfully.");
						response.setDetail(nationality);
						return new ResponseEntity<RestResponseDTO>(response, HttpStatus.OK);
					} else {
						response.setResponseStatus(ResponseStatus.BAD_REQUEST);
						response.setStatus(ResponseStatus.BAD_REQUEST.getValue());
						response.setMessage("Nationality List Currently Not Available");
						return new ResponseEntity<RestResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}

				}
			}
			response.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
			response.setStatus(ResponseStatus.UNAUTHORIZED_USER.getValue());
			response.setMessage("You do not have Authorization.");
			return new ResponseEntity<RestResponseDTO>(response, HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setMessage("Some error occured while processing. Please try again after few moments.");
			return new ResponseEntity<RestResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/arssectorcode", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<RestResponseDTO> arssectorcode(HttpServletRequest request) {
		RestResponseDTO response = new RestResponseDTO();
		try {

			if (request.getHeader("Authorization") == null) {
				response.setResponseStatus(ResponseStatus.FAILURE);
				response.setStatus("FAILURE");
				response.setMessage("Authorization not valid or empty");
				return new ResponseEntity<RestResponseDTO>(response, HttpStatus.BAD_REQUEST);
			}
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
					MerchantManagerDTO merchantManager = merchantManagerApi
							.getselected(StringConstants.AIRLINES_BOOKING, Status.Active, true);
					MerchantDTO merchantDto = merchantApi.findMerchantById(merchantManager.getMerchantId());
					HashMap<String, String> hashRequest = new HashMap<>();
					hashRequest.put("apiurl", merchantDto.getApiUrl());
					hashRequest.put("username", merchantDto.getApiUsername());
					hashRequest.put("password", merchantDto.getApiPassword());
					hashRequest.put("mode", "sectorCode");
					FlightSector sectorCode = (FlightSector) airlinesApi.getSectorCodeAndNationality(hashRequest);

					if (sectorCode != null) {
						response.setResponseStatus(ResponseStatus.SUCCESS);
						response.setStatus(ResponseStatus.SUCCESS.getValue());
						response.setMessage("Sector Code Retrieved Successfully.");
						response.setDetail(sectorCode.getSector());
						return new ResponseEntity<RestResponseDTO>(response, HttpStatus.OK);
					} else {
						response.setResponseStatus(ResponseStatus.BAD_REQUEST);
						response.setStatus(ResponseStatus.BAD_REQUEST.getValue());
						response.setMessage("Sector Code Currently Not Available");
						return new ResponseEntity<RestResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}

				}
			}
			response.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
			response.setStatus(ResponseStatus.UNAUTHORIZED_USER.getValue());
			response.setMessage("You do not have Authorization.");
			return new ResponseEntity<RestResponseDTO>(response, HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setMessage("Some error occured while processing. Please try again after few moments.");
			return new ResponseEntity<RestResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/arsflightavailability", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<RestResponseDTO> arsflightavailability(@RequestBody FlightRequestDTO flightRequestDto,
			HttpServletRequest request) {
		RestResponseDTO response = new RestResponseDTO();
		try {
			if (request.getHeader("Authorization") == null) {
				response.setResponseStatus(ResponseStatus.FAILURE);
				response.setStatus("FAILURE");
				response.setMessage("Authorization not valid or empty");
				return new ResponseEntity<RestResponseDTO>(response, HttpStatus.BAD_REQUEST);
			}
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
					FlightAvailabilityRes flightAvaibility = airlinesApi.getAvailableFlights(flightRequestDto, true);
					FlightAvaibilityWrapper wrapper = new FlightAvaibilityWrapper();
					wrapper.setFlightAvailability(flightAvaibility);
					if (flightAvaibility != null) {
						response.setResponseStatus(ResponseStatus.SUCCESS);
						response.setStatus(ResponseStatus.SUCCESS.getValue());
						response.setMessage("Available Flights Retrieved Successfully.");
						response.setDetail(wrapper);
						return new ResponseEntity<RestResponseDTO>(response, HttpStatus.OK);
					} else {
						response.setResponseStatus(ResponseStatus.BAD_REQUEST);
						response.setStatus(ResponseStatus.BAD_REQUEST.getValue());
						response.setMessage("Flights Currently Not Available");
						return new ResponseEntity<RestResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}

				}
			}
			response.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
			response.setStatus(ResponseStatus.UNAUTHORIZED_USER.getValue());
			response.setMessage("You do not have Authorization.");
			return new ResponseEntity<RestResponseDTO>(response, HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setMessage("Some error occured while processing. Please try again after few moments.");
			return new ResponseEntity<RestResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/arsflightreservation", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<RestResponseDTO> arsflightreservation(@RequestBody FlightReservationDTO flightRequestDto,
			HttpServletRequest request) {
		RestResponseDTO response = new RestResponseDTO();
		try {

			if (request.getHeader("Authorization") == null) {
				response.setResponseStatus(ResponseStatus.FAILURE);
				response.setStatus("FAILURE");
				response.setMessage("Authorization not valid or empty");
				return new ResponseEntity<RestResponseDTO>(response, HttpStatus.BAD_REQUEST);
			}
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {

					FlightReservationResponse flightReservation = airlinesApi.reserveFlight(flightRequestDto,AuthenticationUtil.getCurrentUser().getAssociatedId());

					if (flightReservation != null) {
						response.setResponseStatus(ResponseStatus.SUCCESS);
						response.setStatus(ResponseStatus.SUCCESS.getValue());
						response.setMessage("Available Flights Reserved Successfully.");
						response.setDetail(flightReservation);
						return new ResponseEntity<RestResponseDTO>(response, HttpStatus.OK);
					} else {
						response.setResponseStatus(ResponseStatus.BAD_REQUEST);
						response.setStatus(ResponseStatus.BAD_REQUEST.getValue());
						response.setMessage("Some thing went wrong please try again later.");
						return new ResponseEntity<RestResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}

				}
			}
			response.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
			response.setStatus(ResponseStatus.UNAUTHORIZED_USER.getValue());
			response.setMessage("You do not have Authorization.");
			return new ResponseEntity<RestResponseDTO>(response, HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setMessage("Some error occured while processing. Please try again after few moments.");
			return new ResponseEntity<RestResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/arsissueticket", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<AirlinesResponseDTO> arsissueticket(@RequestBody PassengerTotalDto pessengerDto,
			HttpServletRequest request) {
		AirlinesResponseDTO transactionResponse = new AirlinesResponseDTO();
		try {
			HashMap<String, Object> hashRequest = new HashMap<>();

			if (request.getHeader("Authorization") == null) {
				transactionResponse.setResponseStatus(ResponseStatus.FAILURE);
				transactionResponse.setStatus("FAILURE");
				transactionResponse.setMessage("Authorization not valid or empty");
				return new ResponseEntity<AirlinesResponseDTO>(transactionResponse, HttpStatus.BAD_REQUEST);
			}

			if (pessengerDto.getMobilePin() == null) {
				transactionResponse.setResponseStatus(ResponseStatus.FAILURE);
				transactionResponse.setStatus("FAILURE");
				transactionResponse.setMessage("Mpin not valid or empty");
				return new ResponseEntity<AirlinesResponseDTO>(transactionResponse, HttpStatus.BAD_REQUEST);
			}
			if (pessengerDto.getAccountNumber() == null) {
				transactionResponse.setResponseStatus(ResponseStatus.FAILURE);
				transactionResponse.setStatus("FAILURE");
				transactionResponse.setMessage("Account number not valid or empty");
				return new ResponseEntity<AirlinesResponseDTO>(transactionResponse, HttpStatus.BAD_REQUEST);
			}

			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
					for(PassengerDto pessenger :pessengerDto.getPassengerDto()){
						pessenger.setPaxType(pessenger.getPaxType().toUpperCase());
					}
					hashRequest.put("pessangerinfo", pessengerDto);
					MerchantServiceDTO service = serviceApi
							.findServiceByUniqueIdentifier(pessengerDto.getServiceIdentifier());
					boolean valid = mPinValidation.mpinValidation(AuthenticationUtil.getCurrentUser().getId(),
							pessengerDto.getMobilePin());
					if (valid) {

						HashMap<String, Object> hashResponse = merchantPaymentService.merchantPaymentAirlies(
								StringConstants.AIRLINES_BOOKING, pessengerDto.getStrFlightId(),
								Double.parseDouble(pessengerDto.getAmount()),
								AuthenticationUtil.getCurrentUser().getId(),pessengerDto.getAccountNumber(), hashRequest);

						if (hashResponse.get("status").equals("success")) {
							transactionResponse.setDate((String) hashResponse.get("transactionDate").toString());
							transactionResponse
									.setTransactionIdentifier((String) hashResponse.get("transactionIdentifier"));
							String message = StringConstants.SERVICE_PAYMENT_SUCCESFUL;
							message = message.replace("{service}", service.getService());
							transactionResponse.setMessage(message);
							transactionResponse.setAirlinesPdfUrl((String) hashResponse.get("downloadUrl"));
							transactionResponse.setResponseStatus(ResponseStatus.SUCCESS);
							transactionResponse.setStatus(ResponseStatus.SUCCESS.getKey());
							return new ResponseEntity<AirlinesResponseDTO>(transactionResponse, HttpStatus.OK);
						} else if (hashResponse.get("status").equals("unknown")) {
							transactionResponse.setStatus(ResponseStatus.AMBIGUOUS_TRANSACTION.getKey());
							transactionResponse.setDate((String) hashResponse.get("transactionDate"));
							try{
							transactionResponse.setAirlinesPdfUrl((String) hashResponse.get("downloadUrl"));
							}catch(NullPointerException e){
								
							}
							transactionResponse
									.setTransactionIdentifier((String) hashResponse.get("transactionIdentifier"));
							transactionResponse.setMessage("Technical Error Please Contact Administrator");
							return new ResponseEntity<AirlinesResponseDTO>(transactionResponse, HttpStatus.CONFLICT);
						} else {
							transactionResponse.setDate((String) hashResponse.get("transactionDate"));
							// transactionResponse.setMessage("Technical
							// Error Please Contact Administrator");
							// transactionResponse.setStatus(ResponseStatus.BAD_REQUEST.getKey());
							transactionResponse.setMessage((String) hashResponse.get("Result Message"));
							return new ResponseEntity<AirlinesResponseDTO>(transactionResponse,
									HttpStatus.BAD_REQUEST);
						}

					}
				}
				transactionResponse.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
				transactionResponse.setStatus(ResponseStatus.UNAUTHORIZED_USER.getValue());
				transactionResponse.setMessage("You do not have Authorization.");
				return new ResponseEntity<AirlinesResponseDTO>(transactionResponse, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			transactionResponse.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			transactionResponse.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			transactionResponse.setMessage("Some error occured while processing. Please try again after few moments.");
			return new ResponseEntity<AirlinesResponseDTO>(transactionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		transactionResponse.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
		transactionResponse.setStatus(ResponseStatus.UNAUTHORIZED_USER.getValue());
		transactionResponse.setMessage("You do not have Authorization.");
		return new ResponseEntity<AirlinesResponseDTO>(transactionResponse, HttpStatus.UNAUTHORIZED);
	}

}
