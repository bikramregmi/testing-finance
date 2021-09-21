package com.mobilebanking.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.IHomeScreenImageApi;
import com.mobilebanking.api.INeaOfficeCodeApi;
import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.NeaOfficeCodeDTO;
import com.mobilebanking.model.SparrowSettingsDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;

@Controller
@RequestMapping("/get")
public class GetApiController {

	@Autowired
	private IStateApi stateApi;
	
	@Autowired
	private ICityApi cityApi;
	
	@Autowired
	private IBankApi bankApi;
	
	@Autowired
	private IBankBranchApi bankBranchApi;
	
	@Autowired
	private INeaOfficeCodeApi neaOfficeCodeApi;
	
	@Autowired
	private ISparrowApi sparrowApi;
	
	@Autowired
	private IHomeScreenImageApi homeScreenImageApi;
	
	@RequestMapping(value = "/states", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getAllStates(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		try {
			List<StateDTO> states = stateApi.getAllState();
			if (!states.isEmpty()) {
				response.setCode(ResponseStatus.SUCCESS);
				response.setMessage(ResponseStatus.SUCCESS.getValue());
				response.setStatus("SUCCESS");
				response.setDetails(states);
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
			}
			response.setCode(ResponseStatus.STATES_UNAVAILABLE);
			response.setMessage(ResponseStatus.STATES_UNAVAILABLE.getValue());
			response.setStatus("FAILURE");
			response.setDetails(states);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);

		} catch (Exception e) {
			response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getAllCities(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		try {
			List<CityDTO> cities = cityApi.getAllCity();
			if (!cities.isEmpty()) {
				response.setCode(ResponseStatus.SUCCESS);
				response.setMessage(ResponseStatus.SUCCESS.getValue());
				response.setStatus("SUCCESS");
				response.setDetails(cities);
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
			}
			response.setCode(ResponseStatus.CITIES_UNAVAILABLE);
			response.setMessage(ResponseStatus.CITIES_UNAVAILABLE.getValue());
			response.setStatus("FAILURE");
			response.setDetails(cities);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);

		} catch (Exception e) {
			response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/banks", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getAllBanks(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		try {
			List<BankDTO> banks = bankApi.geteBankByStatus(Status.Active);
			if (!banks.isEmpty()) {
				response.setCode(ResponseStatus.SUCCESS);
				response.setMessage(ResponseStatus.SUCCESS.getValue());
				response.setStatus("SUCCESS");
				response.setDetails(banks);
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
			}
			response.setCode(ResponseStatus.BANKS_UNAVAILABLE);
			response.setMessage(ResponseStatus.BANKS_UNAVAILABLE.getValue());
			response.setStatus("FAILURE");
			response.setDetails(banks);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/bankbranches", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getAllBankBranches(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (request.getHeader("client") == null) {
				response.setCode(ResponseStatus.CLIENT_REQUIRED);
				response.setStatus("FAILURE");
				response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
				response.setDetails(new ArrayList<>());
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
			}
			String clientId = request.getHeader("client");
			List<BankBranchDTO> bankBranches = bankBranchApi.listBankByClientId(clientId);
			if (!bankBranches.isEmpty()) {
				response.setCode(ResponseStatus.SUCCESS);
				response.setMessage(ResponseStatus.SUCCESS.getValue());
				response.setStatus("SUCCESS");
				response.setDetails(bankBranches);
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
			}
			response.setCode(ResponseStatus.BANKBRANCH_UNAVLIABLE);
			response.setMessage(ResponseStatus.BANKS_UNAVAILABLE.getValue());
			response.setStatus("FAILURE");
			response.setDetails(bankBranches);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@RequestMapping(value = "/bannerimage", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getBannerimages(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (request.getHeader("client") == null) {
				response.setCode(ResponseStatus.CLIENT_REQUIRED);
				response.setStatus("FAILURE");
				response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
				response.setDetails(new ArrayList<>());
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
			}
			String clientId = request.getHeader("client");
			List<String> imageList = homeScreenImageApi.getImagesByClient(clientId);
			if (!imageList.isEmpty()) {
				response.setCode(ResponseStatus.SUCCESS);
				response.setMessage(ResponseStatus.SUCCESS.getValue());
				response.setStatus("SUCCESS");
				response.setDetails(imageList);
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
			}
			response.setCode(ResponseStatus.BANKBRANCH_UNAVLIABLE);
			response.setMessage(ResponseStatus.BANKS_UNAVAILABLE.getValue());
			response.setStatus("FAILURE");
			response.setDetails(imageList);
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getServices(HttpServletRequest request) throws Exception {
		ResponseDTO response = new ResponseDTO();
		response.setCode(ResponseStatus.SUCCESS);
		response.setStatus("SUCCESS");
		response.setMessage(ResponseStatus.SUCCESS.getValue());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/neaofficecode")
	ResponseEntity<ResponseDTO> getNeaOfficeCode(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO responseDto = new ResponseDTO();
		try {
			List<NeaOfficeCodeDTO> officeCodeList = neaOfficeCodeApi.getAllOfficeCodesByStatus(Status.Active);
			if (officeCodeList != null) {
				responseDto.setCode(ResponseStatus.SUCCESS);
				responseDto.setStatus(ResponseStatus.SUCCESS.getValue());
				responseDto.setMessage("Office Name(s) and Code(s) Retrieved Successfully.");
				responseDto.setDetails(officeCodeList);
				return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.OK);
			} else {
				responseDto.setCode(ResponseStatus.BAD_REQUEST);
				responseDto.setStatus(ResponseStatus.BAD_REQUEST.getValue());
				responseDto.setMessage("Office Name(s) and Code(s) Currently Not Available");
				return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
			responseDto.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			responseDto.setMessage("Some error occured while processing. Please try again after few moments.");
			return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/sparrowshortcode")
	ResponseEntity<ResponseDTO> getSparrowShortCode(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO responseDto = new ResponseDTO();
		try {
			SparrowSettingsDTO sparrowSettings = sparrowApi.getSettings();
			if (sparrowSettings != null) {
				responseDto.setCode(ResponseStatus.SUCCESS);
				responseDto.setStatus(ResponseStatus.SUCCESS.getValue());
				responseDto.setMessage("Short Code Retrieved Successfully.");
				responseDto.setDetails(sparrowSettings.getShortCode());
				return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.OK);
			} else {
				responseDto.setCode(ResponseStatus.BAD_REQUEST);
				responseDto.setStatus(ResponseStatus.BAD_REQUEST.getValue());
				responseDto.setMessage("Short Code Currently Not Available");
				return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
			responseDto.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
			responseDto.setMessage("Some error occured while processing. Please try again after few moments.");
			return new ResponseEntity<ResponseDTO>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
