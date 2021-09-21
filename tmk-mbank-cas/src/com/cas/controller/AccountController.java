package com.cas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cas.api.IAccountApi;
import com.cas.api.IAuthenticationApi;
import com.cas.api.IUserApi;
import com.cas.entity.User;
import com.cas.exceptions.ClientException;
import com.cas.model.AccountRequestDTO;
import com.cas.model.AccountDTO;
import com.cas.model.CheckBalanceDTO;
import com.cas.model.FailureResponseDTO;
import com.cas.model.ResponseStatus;
import com.cas.model.SuccessResponseDTO;

@Controller
@RequestMapping("/API")
public class AccountController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	private IAuthenticationApi authenticationApi;
	private IAccountApi accountApi;
	private IUserApi userApi;
	private MessageSource messageSource;

	public AccountController(IAuthenticationApi authenticationApi,IAccountApi accountApi,IUserApi userApi) {
		this.authenticationApi = authenticationApi;
		this.accountApi=accountApi;
		this.userApi=userApi;
	}

	@RequestMapping(value = "/Account/Create", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Object> createAccount(@RequestBody AccountRequestDTO acc,HttpServletRequest request,
			HttpServletResponse response)  {
		SuccessResponseDTO success = new SuccessResponseDTO();
		FailureResponseDTO failure = new FailureResponseDTO();
		try {
			
				User user=userApi.searchByUserName(request.getHeader("username"));
				AccountDTO dto=accountApi.createAccount(user,acc.getCountry());
				success.setStatus(ResponseStatus.Success);
				success.setMessage("Account Created"); 
				success.setDetails(dto);
				return new ResponseEntity<Object>(success, HttpStatus.OK);
			
		} catch (ClientException e) {
			failure.setStatus(ResponseStatus.Invalid_Request);
			failure.setMessage(e.getMessage());
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}
		catch (Exception e) {
			failure.setStatus(ResponseStatus.Internal_Server_Error);
			failure.setMessage("Server Error");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/Account/Balance", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Object> checkBalance(@RequestBody CheckBalanceDTO acc,HttpServletRequest request,
			HttpServletResponse response)  {
		SuccessResponseDTO success = new SuccessResponseDTO();
		FailureResponseDTO failure = new FailureResponseDTO();
		try {
			
				AccountDTO dto=accountApi.checkBalance(acc.getAccountNo());
				success.setStatus(ResponseStatus.Success);
				success.setMessage("Account Balance"); 
				success.setDetails(dto);
				return new ResponseEntity<Object>(success, HttpStatus.OK);
			
		} catch (ClientException e) {
			failure.setStatus(ResponseStatus.Invalid_Request);
			failure.setMessage(e.getMessage());
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}
		catch (Exception e) {
			failure.setStatus(ResponseStatus.Internal_Server_Error);
			failure.setMessage("Server Error");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}

	}
}
