package com.mobilebanking.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.api.ICardlessOtpApi;

@Controller
public class AtmRequestController {

	@Autowired
	private ICardlessOtpApi cardlessOtpApi;
	
	
	public void validateOtp(String isoRequest){
		
		cardlessOtpApi.unpackIsoOtpData(isoRequest);
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/validateOtp")
	public ResponseEntity<String> validateOtp(@RequestParam("isoRequest") String isoRequest, HttpServletRequest request){
		
		
		return null;
		
	}
	
}
