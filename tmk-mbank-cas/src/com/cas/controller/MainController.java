package com.cas.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cas.api.IUserApi;
import com.cas.auth.SessionLoggingStrategy;
import com.cas.repositories.CountryRepository;

@Controller
public class MainController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(MainController.class);
	private final IUserApi userApi;
	private final SessionLoggingStrategy sessionLoggingStrategy;
	private final CountryRepository countryRepository;
	private MessageSource messageSource;


	public MainController(IUserApi userApi,
			SessionLoggingStrategy sessionLoggingStrategy,
			CountryRepository countryRepository) {
		this.userApi = userApi;
		this.sessionLoggingStrategy = sessionLoggingStrategy;
		this.countryRepository=countryRepository;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String getMainPage(HttpServletRequest request) {
		logger.debug("aaaaa");
		return "Index";
	}
	
	

	@Override
	public void setMessageSource(MessageSource messageSource) {
		
	}

	

}
