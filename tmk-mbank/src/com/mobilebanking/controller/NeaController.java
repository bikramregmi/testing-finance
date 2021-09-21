package com.mobilebanking.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IMerchantPaymentRefactorApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.model.Hashes;
import com.mobilebanking.model.NeaHashParameters;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.TransactionResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.validation.MerchantPaymentValidation;
import com.wallet.serviceprovider.paypoint.paypointResponse.NeaBillAmountResponse;

@Controller
public class NeaController {

	@Autowired
	private IMerchantServiceApi merchantServiceApi;
	
	@Autowired
	private IMerchantPaymentRefactorApi merchantPaymentService;
	
	@Autowired
	private MerchantPaymentValidation merchantPaymentValidation;
	
	@RequestMapping(method = RequestMethod.POST, value = "/neapayment")
	public ResponseEntity<Object> getService(HttpServletRequest request, HttpServletResponse response,NeaHashParameters neaParameter) {
		RestResponseDTO responseDTO = new RestResponseDTO();
		try {
			HashMap<String, String> hash = new HashMap<>();
			// HashMap<String, String> hash = new HashMap<>();
			String serviceId = request.getParameter("generalserviceUnique");
			String transactionId = request.getParameter("scno");
			String scno = request.getParameter("scno");
			String officeCode = request.getParameter("officecode");
			String amount = request.getParameter("amount");
			String customerId = request.getParameter("generaltransactionId");
			hash.put("Utility Code",request.getParameter("utilityCode"));
			hash.put("Bill Number", request.getParameter("billBumber"));
			hash.put("RefStan",  request.getParameter("refStan"));
			hash.put("Commission Value",  request.getParameter("commissionValue"));
			hash.put("payment",  request.getParameter("payment"));
			hash.put("scno", scno);
			hash.put("officeCode", officeCode);
			hash.put("customerId", customerId);
			// Hashes hashes =
			// merchantPaymentValidation.generalMerchantValidation(serviceId,
			// transactionId, amount);
			Hashes hashes = new Hashes();
			HashMap<String, String> hashResponse = new HashMap<>();
			
//			hash.put("username", username);
			TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
			HashMap<String, String> validationResponse = merchantPaymentValidation.merchantPaymentValidation(
					transactionId, serviceId, Double.parseDouble(amount)/100, AuthenticationUtil.getCurrentUser().getId());

			if (validationResponse.get("message").equals("valid")) {
				hashes.setValid(true);
			} else {
				transactionResponse.setMessage(validationResponse.get("message"));
				transactionResponse.setStatus(ResponseStatus.VALIDATION_FAILED.getKey());
				return new ResponseEntity<Object>(transactionResponse, HttpStatus.FORBIDDEN);
			}
			if (hashes.isValid()) {
				/*
				 * else if(hash!=null
				 * &serviceId.equals(StringConstants.WLINK_TOPUP )){
				 * hashResponse =
				 * merchantPaymentService.merchantPayment(serviceId,
				 * transactionId, Double.parseDouble(amount),
				 * AuthenticationUtil.getCurrentUser().getId(),hashMap); }
				 */

				hashResponse = merchantPaymentService.merchantPayment(serviceId, transactionId,
						Double.parseDouble(amount)/100, AuthenticationUtil.getCurrentUser().getId(), hash);

				if (hashResponse.get("status").equals("success")) {
					responseDTO.setMessage("Service Accomplish");
					responseDTO.setStatus(ResponseStatus.SUCCESS.getKey());
					responseDTO.setDetail(hashResponse);
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				} else if (hashResponse.get("status").equals("unknown")) {
					transactionResponse.setStatus(ResponseStatus.AMBIGUOUS_TRANSACTION.getKey());
					transactionResponse.setDate(hashResponse.get("transactionDate"));
					transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
					transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
					transactionResponse.setMessage("Technical Error Please Contact Administrator");
					return new ResponseEntity<Object>(transactionResponse, HttpStatus.CONFLICT);
				}

				else {
					responseDTO.setStatus(ResponseStatus.BAD_REQUEST.getKey());
					responseDTO.setMessage(hashResponse.get("Result Message"));
					return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
				}
			} else {
				responseDTO.setStatus(ResponseStatus.VALIDATION_FAILED.getKey());
				responseDTO.setMessage("Request Not Valid");
				responseDTO.setDetail(hashes.getMyHash());
				return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatus(ResponseStatus.FAILURE.getKey());
			responseDTO.setMessage("Service Failed");
			return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getNeaPayment")
	public ResponseEntity<Object> getNeaPayment(HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO responseDTO = new RestResponseDTO();
		try {
			HashMap<String, String> hashMap = null;
			// HashMap<String, String> hash = new HashMap<>();
			String serviceId = request.getParameter("generalserviceUnique");
			String transactionId = request.getParameter("generaltransactionId");
			String scno = request.getParameter("scno");
			String officeCode = request.getParameter("officecode");
			// Hashes hashes =
			// merchantPaymentValidation.generalMerchantValidation(serviceId,
			// transactionId, amount);
			Hashes hashes = new Hashes();
//			hash.put("username", username);

			
			hashes.setValid(true);
			if (hashes.isValid()) {
				/*
				 * else if(hash!=null
				 * &serviceId.equals(StringConstants.WLINK_TOPUP )){
				 * hashResponse =
				 * merchantPaymentService.merchantPayment(serviceId,
				 * transactionId, Double.parseDouble(amount),
				 * AuthenticationUtil.getCurrentUser().getId(),hashMap); }
				 */
				

				NeaBillAmountResponse amountResponse = merchantPaymentService.getNeaPaymentAmount(serviceId, transactionId,
						 scno,officeCode);

				if (amountResponse.getHashResponse().get("status").equals("success")) {
					responseDTO.setMessage("Service Accomplish");
					responseDTO.setStatus(ResponseStatus.SUCCESS.getKey());
					responseDTO.setDetail(amountResponse);
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				} 

				else {
					responseDTO.setStatus(ResponseStatus.BAD_REQUEST.getKey());
					responseDTO.setMessage(amountResponse.getHashResponse().get("Result Message"));
					return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
				}
			} else {
				responseDTO.setStatus(ResponseStatus.VALIDATION_FAILED.getKey());
				responseDTO.setMessage("Request Not Valid");
				responseDTO.setDetail(hashes.getMyHash());
				return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatus(ResponseStatus.FAILURE.getKey());
			responseDTO.setMessage("Service Failed");
			return new ResponseEntity<Object>(responseDTO, HttpStatus.CONFLICT);
		}
	}


}
