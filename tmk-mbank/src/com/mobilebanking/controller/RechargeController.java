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
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.TransactionResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.validation.MerchantPaymentValidation;

@Controller
public class RechargeController {

	@Autowired
	private IMerchantServiceApi merchantServiceApi;
	
	@Autowired
	private IMerchantPaymentRefactorApi merchantPaymentService;
	
	@Autowired
	private MerchantPaymentValidation merchantPaymentValidation;

	@RequestMapping(method = RequestMethod.POST, value = "/rechargePin")
	public ResponseEntity<Object> getService(HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO responseDTO = new RestResponseDTO();
		try {
			HashMap<String,String> hashMap = null;
//			HashMap<String, String> hash = new HashMap<>();
			String serviceId = request.getParameter("generalserviceUnique");
//			String transactionId = request.getParameter("generaltransactionId");
//			String username = request.getParameter("merchantUsername");
			String amount = request.getParameter("generalamount");
			

			//Hashes hashes = merchantPaymentValidation.generalMerchantValidation(serviceId, transactionId, amount);
			Hashes hashes = new Hashes();
			HashMap<String,String> hashResponse = new HashMap<>();
			HashMap<String, String> hash = new HashMap<>();
//			hash.put("subisuUsername", username);
			TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
HashMap<String, String> validationResponse = merchantPaymentValidation.merchantPaymentValidation(null, serviceId, Double.parseDouble(amount), AuthenticationUtil.getCurrentUser().getId());
			
			if(validationResponse.get("message").equals("valid")){
				hashes.setValid(true);
			}else{
				transactionResponse.setMessage(validationResponse.get("message"));
				transactionResponse.setStatus(ResponseStatus.VALIDATION_FAILED.getKey());
				return new ResponseEntity<Object>(transactionResponse, HttpStatus.FORBIDDEN);
			}
			hashes.setValid(true);
			if (hashes.isValid()) {
				/*else if(hash!=null &serviceId.equals(StringConstants.WLINK_TOPUP )){
					hashResponse = merchantPaymentService.merchantPayment(serviceId, transactionId, Double.parseDouble(amount),
							AuthenticationUtil.getCurrentUser().getId(),hashMap);
				}*/
				
				hashResponse = merchantPaymentService.merchantPayment(serviceId, null, Double.parseDouble(amount),
						AuthenticationUtil.getCurrentUser().getId(),hash);
				
				if (hashResponse.get("status").equals("success")) {
					transactionResponse.setDate(hashResponse.get("transactionDate"));
					transactionResponse.setPinNo(hashResponse.get("pinNo"));
					transactionResponse.setSerialNo(hashResponse.get("serialNo"));
					transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
					transactionResponse.setMessage("Service Accomplish");
					transactionResponse.setStatus(ResponseStatus.SUCCESS.getKey());
					return new ResponseEntity<Object>(transactionResponse, HttpStatus.OK);
				}
				else if(hashResponse.get("status").equals("unknown")){
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
}
