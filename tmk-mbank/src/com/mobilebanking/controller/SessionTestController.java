package com.mobilebanking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.model.SessionTest;

@Controller
@Scope("session")
public class SessionTestController  {

	private Logger logger = LoggerFactory.getLogger(SessionTestController.class);
	
	@Autowired
	private SessionTest sessionTest;

	@RequestMapping(method = RequestMethod.GET, value = "/SessionTest")
	public String viewAgent(ModelMap modelMap, @RequestParam("setsession") String setsession, HttpServletRequest request, HttpServletResponse response) {
		sessionTest.setMysession(setsession);
		modelMap.put("mytest", sessionTest);
				return "SessionTest";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/SessionTestsec")
	public String SessionTestsec(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		
		modelMap.put("mytest", sessionTest);
				return "SessionTest";
		
	}

}
