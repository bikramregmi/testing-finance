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
import com.cas.entity.User;
import com.cas.exceptions.ClientException;
import com.cas.model.AccountRequestDTO;
import com.cas.model.AccountDTO;
import com.cas.model.CheckBalanceDTO;
import com.cas.model.FailureResponseDTO;
import com.cas.model.LoadFundValidationDTO;
import com.cas.model.ResponseStatus;
import com.cas.model.SearchTransactionDTO;
import com.cas.model.SuccessResponseDTO;
import com.cas.model.TransactionDTO;
import com.cas.model.TransactionType;
import com.cas.model.FundTransferValidationDTO;
import com.cas.validation.TransactionValidation;

@Controller
@RequestMapping("/API")
public class TransactionController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	private IAuthenticationApi authenticationApi;
	private TransactionValidation transactionValidation;
	private ITransactionApi transactionApi;

	public TransactionController(IAuthenticationApi authenticationApi,
			TransactionValidation transactionValidation,
			ITransactionApi transactionApi) {
		this.authenticationApi = authenticationApi;
		this.transactionValidation = transactionValidation;
		this.transactionApi = transactionApi;
	}

	@RequestMapping(value = "/Transaction/Create", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Object> createTransaction(@RequestBody TransactionDTO txn,
			HttpServletRequest request, HttpServletResponse response) {
		SuccessResponseDTO success = new SuccessResponseDTO();
		FailureResponseDTO failure = new FailureResponseDTO();
		FundTransferValidationDTO fundTransferError = new FundTransferValidationDTO();
		LoadFundValidationDTO loadFundError = new LoadFundValidationDTO();
		TransactionDTO dto=new TransactionDTO();
		try {
			if (authenticationApi.Authenticate(request, response)) {
				if(txn.getTransactionType().equals(TransactionType.Transfer)){
					fundTransferError = transactionValidation.FundTransferValidation(txn);
					if (fundTransferError.isValid()) {
						dto=transactionApi.createTransaction(txn);
						success.setStatus(ResponseStatus.Success);
						success.setMessage("Transaction Created");
						success.setDetails(dto);
					} else {
						success.setStatus(ResponseStatus.Invalid_Request);
						success.setMessage("Transaction Validation Error");
						success.setDetails(fundTransferError);
					}
				}
				else if(txn.getTransactionType().equals(TransactionType.Load)){
					loadFundError = transactionValidation.LoadFundValidation(txn);
					if (loadFundError.isValid()) {
						dto=transactionApi.createTransaction(txn);
						success.setStatus(ResponseStatus.Success);
						success.setMessage("Transaction Created");
						success.setDetails(dto);
					} else {
						success.setStatus(ResponseStatus.Invalid_Request);
						success.setMessage("Transaction Validation Error");
						success.setDetails(loadFundError);
					}
				}
				else{
					failure.setStatus(ResponseStatus.Invalid_Request);
					failure.setMessage("Invalid Transaction Type");
					return new ResponseEntity<Object>(failure, HttpStatus.OK);
				}
				
				return new ResponseEntity<Object>(success, HttpStatus.OK);
			}
			failure.setStatus(ResponseStatus.Invalid_Session);
			failure.setMessage("Invalid Session");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		} catch (ClientException e) {
			failure.setStatus(ResponseStatus.Invalid_Request);
			failure.setMessage(e.getMessage());
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		} catch (Exception e) {
			failure.setStatus(ResponseStatus.Internal_Server_Error);
			failure.setMessage("Server Error");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/Transaction/Process", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Object> processTransaction(
			@RequestBody SearchTransactionDTO txn, HttpServletRequest request,
			HttpServletResponse response) {
		SuccessResponseDTO success = new SuccessResponseDTO();
		FailureResponseDTO failure = new FailureResponseDTO();
		FundTransferValidationDTO error = new FundTransferValidationDTO();
		TransactionDTO dto=new TransactionDTO();
		try {
			if (authenticationApi.Authenticate(request, response)) {
				long txnId = Long.parseLong(txn.getTransactionId());
				dto=transactionApi.completeTransaction(txnId);
				success.setStatus(ResponseStatus.Success);
				success.setMessage("Transaction Processed");
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
		} catch (Exception e) {
			failure.setStatus(ResponseStatus.Internal_Server_Error);
			failure.setMessage("Server Error");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/Transaction/Reverse", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Object> reverseTransaction(
			@RequestBody SearchTransactionDTO txn, HttpServletRequest request,
			HttpServletResponse response) {
		SuccessResponseDTO success = new SuccessResponseDTO();
		FailureResponseDTO failure = new FailureResponseDTO();
		FundTransferValidationDTO error = new FundTransferValidationDTO();
		TransactionDTO dto=new TransactionDTO();
		try {
			if (authenticationApi.Authenticate(request, response)) {
				long txnId = Long.parseLong(txn.getTransactionId());
				dto=transactionApi.reverseTransaction(txnId);
				success.setStatus(ResponseStatus.Success);
				success.setMessage("Transaction Reversed");
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
		} catch (Exception e) {
			failure.setStatus(ResponseStatus.Internal_Server_Error);
			failure.setMessage("Server Error");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/Transaction/Cancel", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Object> cancelTransaction(
			@RequestBody SearchTransactionDTO txn, HttpServletRequest request,
			HttpServletResponse response) {
		SuccessResponseDTO success = new SuccessResponseDTO();
		FailureResponseDTO failure = new FailureResponseDTO();
		FundTransferValidationDTO error = new FundTransferValidationDTO();
		TransactionDTO dto=new TransactionDTO();
		try {
			if (authenticationApi.Authenticate(request, response)) {
				long txnId = Long.parseLong(txn.getTransactionId());
				dto=transactionApi.cancelTransaction(txnId);
				success.setStatus(ResponseStatus.Success);
				success.setMessage("Transaction Cancelled");
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
		} catch (Exception e) {
			failure.setStatus(ResponseStatus.Internal_Server_Error);
			failure.setMessage("Server Error");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/Transaction/Detail", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Object> getTransactionDetail(
			@RequestBody SearchTransactionDTO txn, HttpServletRequest request,
			HttpServletResponse response) {
		SuccessResponseDTO success = new SuccessResponseDTO();
		FailureResponseDTO failure = new FailureResponseDTO();
		FundTransferValidationDTO error = new FundTransferValidationDTO();
		try {
			if (authenticationApi.Authenticate(request, response)) {
				long txnId = Long.parseLong(txn.getTransactionId());
				TransactionDTO dto=new TransactionDTO();
				dto=transactionApi.getTransactionDetail(txnId);
				success.setStatus(ResponseStatus.Success);
				success.setMessage("Transaction Detail");
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
		} catch (Exception e) {
			failure.setStatus(ResponseStatus.Internal_Server_Error);
			failure.setMessage("Server Error");
			return new ResponseEntity<Object>(failure, HttpStatus.OK);
		}
	}
}
