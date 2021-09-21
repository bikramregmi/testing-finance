package com.mobilebanking.webservice;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.ITransactionAlertApi;
import com.mobilebanking.model.TransactionAlertDTO;
import com.mobilebanking.model.TransactionAlertResponseCode;
import com.mobilebanking.model.mobile.TransactionAlertResponseDTO;
import com.mobilebanking.validation.TransactionAlertValidation;

@Controller
@RequestMapping("/alert")
public class TransDailyAlertController {

	@Autowired
	private TransactionAlertValidation transactionAlertValidation;
	
	@Autowired
	private ITransactionAlertApi transactionAlertApi;
	
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public ResponseEntity<TransactionAlertResponseDTO> alertTransaction(HttpServletRequest request, TransactionAlertDTO transactionAlert ) {
		TransactionAlertResponseDTO response = new TransactionAlertResponseDTO();
		try {
			
			HashMap<String,String> validationResponse = transactionAlertValidation.validateTransaction(transactionAlert);
			
			if(validationResponse.get("status").equals("invalid")){
				response.setCode(validationResponse.get("code"));
				response.setMessage(validationResponse.get("message"));
				response.setStatus("FAILURE");
				return new ResponseEntity<TransactionAlertResponseDTO>(response, HttpStatus.BAD_REQUEST);
			}else{
				
				Boolean valid = transactionAlertApi.sentTransactionAlert(transactionAlert);
				if(valid){
					response.setCode(validationResponse.get("code"));
					response.setMessage("The customer was successfully alerted");
					response.setStatus("SUCCESS");
					return new ResponseEntity<TransactionAlertResponseDTO>(response, HttpStatus.OK);

				}else{
					response.setCode(TransactionAlertResponseCode.FAILURE.getKey());
					response.setMessage("Unable to alert the customer.");
					response.setStatus("FAILURE");
					return new ResponseEntity<TransactionAlertResponseDTO>(response, HttpStatus.METHOD_FAILURE);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(TransactionAlertResponseCode.INTERNAL_SERVER_ERROR.getKey());
			response.setMessage(TransactionAlertResponseCode.INTERNAL_SERVER_ERROR.getValue());
			response.setStatus("FAILURE");
			return new ResponseEntity<TransactionAlertResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	
}
