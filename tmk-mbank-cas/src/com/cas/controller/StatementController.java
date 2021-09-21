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
import com.cas.api.ITransactionApi;
import com.cas.api.IUserApi;
import com.cas.entity.Account;
import com.cas.entity.User;
import com.cas.exceptions.ClientException;
import com.cas.model.AccountRequestDTO;
import com.cas.model.AccountDTO;
import com.cas.model.CheckBalanceDTO;
import com.cas.model.FailureResponseDTO;
import com.cas.model.ResponseStatus;
import com.cas.model.StatementDTO;
import com.cas.model.SuccessResponseDTO;
import com.cas.model.TransactionDTO;

@Controller
@RequestMapping("/API")
public class StatementController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	private IAuthenticationApi authenticationApi;
	private IAccountApi accountApi;
	private ITransactionApi transactionApi;

	public StatementController(IAuthenticationApi authenticationApi,IAccountApi accountApi,ITransactionApi transactionApi) {
		this.authenticationApi = authenticationApi;
		this.accountApi=accountApi;
		this.transactionApi=transactionApi;
	}

	@RequestMapping(value = "/Statement", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Object> createAccount(@RequestBody CheckBalanceDTO acc,HttpServletRequest request,
			HttpServletResponse response)  {
		SuccessResponseDTO success = new SuccessResponseDTO();
		FailureResponseDTO failure = new FailureResponseDTO();
		try {
			if (authenticationApi.Authenticate(request, response)) {
				Account account=accountApi.searchByAccountId(acc.getAccountNo());
				if(account==null){
					failure.setStatus(ResponseStatus.Invalid_Request);
					failure.setMessage("Invalid Account No");
					return new ResponseEntity<Object>(failure, HttpStatus.OK);
				}
				List<TransactionDTO> dto=new ArrayList<TransactionDTO>();
				dto=transactionApi.getStatement(account);
				success.setStatus(ResponseStatus.Success);
				success.setMessage("Statement List"); 
				success.setDetails(dto);
				return new ResponseEntity<Object>(success, HttpStatus.OK);
			}
			failure.setStatus(ResponseStatus.Invalid_Session);
			failure.setMessage("Invalid Session");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
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
