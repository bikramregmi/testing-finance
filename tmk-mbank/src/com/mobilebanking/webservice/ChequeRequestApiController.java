package com.mobilebanking.webservice;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IChequeBlockRequestApi;
import com.mobilebanking.api.IChequeRequestApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.ChequeBlockRequestDTO;
import com.mobilebanking.model.ChequeRequestDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.MPinVaidation;

@Controller
@RequestMapping("/api")
public class ChequeRequestApiController {

	
	@Autowired
	private ICustomerApi customerApi;
	
	@Autowired
	private IChequeRequestApi chequeRequestApi;
	
	@Autowired
	private IChequeBlockRequestApi chequeBlockRequestApi;
	
	@Autowired
	private ICustomerBankAccountApi customerBankAccountApi;
	
	@Autowired
	private IUserApi userApi;
	
	@Autowired
	private MPinVaidation mPinValidation;
	
	@RequestMapping(value="/chequerequest", method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> addRecipient(HttpServletRequest request, @RequestBody  ChequeRequestDTO chequeRequestDTO) {
		ResponseDTO response = new ResponseDTO();
		
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
//		 customerValidation.customerValidation(customerDTO);
//		String username = request.getParameter("username");
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				UserDTO userDto = userApi.getUserWithId((AuthenticationUtil.getCurrentUser().getId()));
				userDto.setOldPassword(chequeRequestDTO.getmPin());
			 Boolean valid = mPinValidation.mpinValidation(userDto);
			 if(valid){
				try {
					CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi.findCustomerBankAccountByAccountNo(chequeRequestDTO.getAccountNumber(),customer.getId());
					if(customer.getState()!=null && customer.getStatus().equals(CustomerStatus.Blocked)){
						response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
						response.setStatus("FAILURE");
						response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}
					//					customerDTO.setCreatedBy(username);
					
					chequeRequestApi.save(chequeRequestDTO.getAccountNumber(), chequeRequestDTO.getChequeLeaves(),customer.getId());
					response.setCode(ResponseStatus.SUCCESS);
					response.setStatus("SUCCESS");
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails("Your Cheque book request for "+chequeRequestDTO.getChequeLeaves()+" leaves has been requested  as of "+ new Date() +".Please contact "+ customerBankAcocunt.getBank()+" for details. Thank you"); 
					return new ResponseEntity<ResponseDTO> (response,HttpStatus.OK);
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
			}else{
				response.setCode(ResponseStatus.MPIN_DOES_NOT_MATCH);
				response.setStatus("FAILURE");
				response.setMessage(ResponseStatus.MPIN_DOES_NOT_MATCH.getValue());
				response.setDetails(new ArrayList<>());
				return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
		}
		}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}
	
	
	@RequestMapping(value="/chequeblockrequest", method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> chequeBlockRequest(HttpServletRequest request, @RequestBody  ChequeBlockRequestDTO chequeBlockRequestDTO) {
		ResponseDTO response = new ResponseDTO();
		
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		
//		 customerValidation.customerValidation(customerDTO);
//		String username = request.getParameter("username");
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				UserDTO userDto = userApi.getUserWithId((AuthenticationUtil.getCurrentUser().getId()));
				userDto.setOldPassword(chequeBlockRequestDTO.getmPin());
			 Boolean valid = mPinValidation.mpinValidation(userDto);
			 if(valid){
				try {
					CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi.findCustomerBankAccountByAccountNo(chequeBlockRequestDTO.getAccountNumber(),customer.getId());
					
					if(customer.getState()!=null && customer.getStatus().equals(CustomerStatus.Blocked)){
						response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
						response.setStatus("FAILURE");
						response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", customerBankAcocunt.getBank()));
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}
					if(chequeBlockRequestApi.save(chequeBlockRequestDTO.getAccountNumber(), chequeBlockRequestDTO.getChequeNumber(),customer.getId())!=null){
					response.setCode(ResponseStatus.SUCCESS);
					response.setStatus("SUCCESS");
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails("Your Cheque block request of Cheque No. "+ chequeBlockRequestDTO.getChequeNumber()+" has been requested as of "+ new Date()+ ".Please call "+customerBankAcocunt.getBank()+ " for confirmation. Thank you"); 
					return new ResponseEntity<ResponseDTO> (response,HttpStatus.OK);
					}else{
						response.setCode(ResponseStatus.DATA_SAVE_ERROR);
						response.setStatus("FAILURE");
						response.setMessage(ResponseStatus.DATA_SAVE_ERROR.getValue());
						response.setDetails(new ArrayList<>());
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.METHOD_FAILURE);
					}
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
			
		
		}else{
			response.setCode(ResponseStatus.MPIN_DOES_NOT_MATCH);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.MPIN_DOES_NOT_MATCH.getValue());
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
		}
			}
			}
			
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}
	
	
}
