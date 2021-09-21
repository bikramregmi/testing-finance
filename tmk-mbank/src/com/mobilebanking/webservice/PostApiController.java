package com.mobilebanking.webservice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.ICustomerApi;

@Controller
public class PostApiController {
	
	@Autowired
	private ICustomerApi customerApi;

	@RequestMapping(value = "/delete/oauthtoken", method = RequestMethod.POST)
	public ResponseEntity<String> deleteOauthToken(HttpServletRequest request){
		String username = request.getParameter("username");
		if(username != null){
			if(customerApi.deleteOauthToken(username)){
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
