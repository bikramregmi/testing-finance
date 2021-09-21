package com.mobilebanking.webservice;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.StringConstants;

@Controller
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired
	private ICustomerApi customerApi;
	@Autowired
	private IUserApi userApi;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping(value="/verify", method=RequestMethod.GET)
	public ResponseEntity<ResponseDTO> verifyCode(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("verification_code") == null) {
			response.setCode(ResponseStatus.VERIFICATION_CODE_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.VERIFICATION_CODE_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				String token = userApi.verifyCode(
						request.getParameter("verification_code"), AuthenticationUtil.getCurrentUser().getUserName());
				if (token == null) {
					response.setCode(ResponseStatus.INVALID_VERIFICATION_CODE);
					response.setStatus("FAILURE");
					response.setDetails(new ArrayList<>());
					response.setMessage(ResponseStatus.INVALID_VERIFICATION_CODE.getValue());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
				
				response.setCode(ResponseStatus.SUCCESS);
				response.setStatus("SUCCESS");
				response.setMessage(ResponseStatus.SUCCESS.getValue());
				response.setDetails(token);
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
			}
		}
		
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/resendsmsverification", method=RequestMethod.GET)
	public ResponseEntity<ResponseDTO> resendSmsForVerification(HttpServletRequest request) {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.AUTHORIZATION_HEADER);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage(ResponseStatus.AUTHORIZATION_HEADER.getValue());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("username") == null) {
			response.setCode(ResponseStatus.USER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.USER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				try {
					boolean sendSms = customerApi.resendSmsForVerification(request.getParameter("username"));
					if (sendSms) {
						response.setCode(ResponseStatus.SUCCESS);
						response.setStatus("SUCCESS");
						response.setMessage(ResponseStatus.SUCCESS.getValue());
						response.setDetails("Successfully Sent SMS");
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					} else {
						response.setCode(ResponseStatus.SMS_FAILED_VERIFCATION);
						response.setMessage(ResponseStatus.SMS_FAILED_VERIFCATION.getValue());
						response.setDetails("Could Not Send SMS");
						response.setStatus("SUCCESS");
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
					}
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
			}
		}
		
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/resendtoken", method=RequestMethod.GET)
	public ResponseEntity<ResponseDTO> resendToken(HttpServletRequest request) {
		ResponseDTO  response = new ResponseDTO();
		
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.AUTHORIZATION_HEADER);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage(ResponseStatus.AUTHORIZATION_HEADER.getValue());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("username") == null) {
			response.setCode(ResponseStatus.USER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.USER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				try {
					String token = customerApi.resendToken(request.getParameter("username"));
					if (token == null) {
						response.setCode(ResponseStatus.TOKEN_NOT_AVAILABLE);
						response.setDetails(new ArrayList<>());
						response.setStatus("FAILURE");
						response.setMessage(ResponseStatus.TOKEN_NOT_AVAILABLE.getValue());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
					} else if (token.equalsIgnoreCase(StringConstants.UNVERIFIED)) {
						response.setCode(ResponseStatus.ACCOUNT_NOT_VERIFIED);
						response.setStatus("FAILURE");
						response.setMessage(ResponseStatus.ACCOUNT_NOT_VERIFIED.getValue());
						response.setDetails(new ArrayList<>());
						return new ResponseEntity<ResponseDTO> (response, HttpStatus.OK);
					} else {
						response.setCode(ResponseStatus.SUCCESS);
						response.setStatus("SUCCESS");
						response.setMessage(ResponseStatus.SUCCESS.getValue());
						response.setDetails(token);
						return new ResponseEntity<ResponseDTO> (response, HttpStatus.OK);
					}
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					response.setStatus("FAILURE");
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
			}
		}
		
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
		
	}
	
	@RequestMapping(value="/verifytoken", method=RequestMethod.GET)
	public ResponseEntity<ResponseDTO> verifyToken(HttpServletRequest request) throws ClientException {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());

		if(customer.getState()!=null && customer.getStatus().equals(CustomerStatus.Blocked)){
			response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
			response.setStatus("FAILURE");
			response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", ""));
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("username") == null) {
			response.setCode(ResponseStatus.USER_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.USER_REQUIRED.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("token") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setMessage("Token cannot be empty");
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO> (response, HttpStatus.BAD_REQUEST);
		}
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				boolean isTokenVerified = customerApi.verifyToken(request.getParameter("username"), request.getParameter("token"));
				String responseMessage = isTokenVerified ? "1" : "0";
				response.setCode(ResponseStatus.SUCCESS);
				response.setMessage(ResponseStatus.SUCCESS.getValue());
				response.setStatus("SUCCESS");
				response.setDetails(responseMessage);
				return new ResponseEntity<ResponseDTO> (response, HttpStatus.OK);
			}
		}
		
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/verifyloginforbank", method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> verifyLoginForBank(HttpServletRequest request) throws ClientException {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getParameter("username") == null) {
			response.setCode(ResponseStatus.USER_REQUIRED);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage(ResponseStatus.USER_REQUIRED.getValue());
			return new ResponseEntity<ResponseDTO> (response, HttpStatus.FORBIDDEN);
		}
		
		CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());

		if(customer.getState()!=null && customer.getStatus().equals(CustomerStatus.Blocked)){
			response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
			response.setStatus("FAILURE");
			response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", ""));
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (request.getHeader("client") == null) {
			response.setCode(ResponseStatus.CLIENT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				try {
					boolean valid = userApi.verifyUserAsPerbank(AuthenticationUtil.getCurrentUser().getUserName(), 
							request.getHeader("client"));
					System.out.println("VALID MESAGE" + valid);
					if (valid) {
						response.setCode(ResponseStatus.SUCCESS);
						response.setStatus("SUCCESS");
						response.setMessage(ResponseStatus.SUCCESS.getValue());
						response.setDetails("Client and Customer Verified");
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					}
					response.setCode(ResponseStatus.ACCOUNT_NOT_VERIFIED_CLIENT);
					response.setStatus("FAILURE");
					response.setMessage(ResponseStatus.ACCOUNT_NOT_VERIFIED_CLIENT.getValue());
					response.setDetails(new ArrayList<>());
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					response.setStatus("FAILURE");
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
