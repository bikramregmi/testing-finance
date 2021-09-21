/*package com.wallet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wallet.api.IUnitedSolutionsApi;
import com.wallet.model.Hashes;
import com.wallet.model.mobile.ResponseDTO;
import com.wallet.model.mobile.ResponseStatus;

@Controller
public class UnitedSoluionsController {
	
	@Autowired
	private IUnitedSolutionsApi unitedSolutionsApi;
	
	@RequestMapping(method = RequestMethod.POST, value = "/unitedsolutionbalanceenquiry")
	public ResponseEntity<Object> checkBalance(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {

			String serviceId = request.getParameter("generalserviceUnique");
			Hashes hashes = new Hashes();
			hashes.setValid(true);
			//Hashes hashes = merchantPaymentValidation.generalMerchantValidation(serviceId, transactionId, amount);
			if (hashes.isValid()) {
				boolean valid = unitedSolutionsApi.checkBalance(serviceId);

				if (valid==true) {
					responseDTO.setStatus(ResponseStatus.SUCCESS);
					responseDTO.setMessage("Service Accomplish");
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/unitedSolutions")
	public String eprabhuPayment(ModelMap modelMap, HttpServletRequest request) {

		unitedSolutionsApi.flightAvailability();
		return "unitedsolution/unitedsolutions";
	}
}
*/