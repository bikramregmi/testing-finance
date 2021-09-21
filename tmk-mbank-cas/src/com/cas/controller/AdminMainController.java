package com.cas.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Admin") 
public class AdminMainController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	private MessageSource messageSource;


	public AdminMainController() {
		
	}
	

}
