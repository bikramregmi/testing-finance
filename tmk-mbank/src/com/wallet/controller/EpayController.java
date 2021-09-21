/*package com.wallet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wallet.api.IEpayApi;
import com.wallet.model.Hashes;
import com.wallet.model.mobile.ResponseDTO;
import com.wallet.model.mobile.ResponseStatus;
import com.wallet.util.AuthenticationUtil;

@Controller
public class EpayController {
	
	@Autowired
	private IEpayApi epayApi;

	@RequestMapping(method = RequestMethod.POST, value = "/ePayOnlinePayment")
	public ResponseEntity<Object> rechargeDishHome(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {


			String serviceId = request.getParameter("generalserviceUnique");
			String amount = request.getParameter("generalamount");

			Hashes hashes = new Hashes();
			hashes.setValid(true);
			//Hashes hashes = merchantPaymentValidation.generalMerchantValidation(serviceId, transactionId, amount);
			if (hashes.isValid()) {
				boolean valid = epayApi.onlinePayment(serviceId, Double.parseDouble(amount), AuthenticationUtil.getCurrentUser().getId());

				if (valid == true) {
					responseDTO.setStatus(ResponseStatus.SUCCESS);
					responseDTO.setMessage("Service Accomplish");
					//responseDTO.setDetails(dishHomeDto);
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				} else {
					responseDTO.setStatus(ResponseStatus.BAD_REQUEST);
					responseDTO.setMessage("Service failed");
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				}
			} else {
				responseDTO.setStatus(ResponseStatus.VALIDATION_FAILED);
				responseDTO.setMessage("Request Not Valid");
				responseDTO.setDetails(hashes.getMyHash());
				return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatus(ResponseStatus.FAILURE);
			responseDTO.setMessage("Service Failed");
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		}
	}
}
*/