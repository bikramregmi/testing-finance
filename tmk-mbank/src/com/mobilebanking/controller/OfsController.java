package com.mobilebanking.controller;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.api.ICardlessOtpApi;
import com.mobilebanking.api.impl.CardlessOtpApi;
import com.mobilebanking.api.impl.CustomerBankAccountApi;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.network.NodeServerRequestHandler;
import com.mobilebanking.repositories.OfsMessageRepository;
import com.wallet.ofs.service.OfsMessageService;

@Controller
public class OfsController {

	@Autowired
	private OfsMessageRepository ofsMessageRepository;
	
	@Autowired
	private OfsMessageService ofsMessageService;
	
	@Autowired
	private CardlessOtpApi cardlessApi ;
	
	@Autowired
	private CustomerBankAccountApi customerBankAccountApi;
	
	@Autowired
	private ICardlessOtpApi cardlessOtpApi;
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/ofsTest")
	public String test(ModelMap modelMap, HttpServletRequest request, @RequestParam ("acno") String accountNumber) {
		// TODO getBankId
		CustomerBankAccountDTO customerBankAccount = customerBankAccountApi.findCustomerBankAccountByAccountNumberAndBank(accountNumber, 1L);
		try {
			cardlessApi.saveAndShareOTP(customerBankAccount, "9849161691", 1000, 1l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String cardno = request.getParameter("cardNo");
		
		// merchantPaymentService.dishHome();;
		OfsMessage ofsMessage = new OfsMessage();
		ofsMessage.getMessageData().setAT_UNIQUE_ID(System.currentTimeMillis()+"");
		ofsMessage.getMessageData().setCARD_NO(cardno);
		ofsMessage.getMessageData().setATM_TIMESTAMP(System.currentTimeMillis()+"");
		com.wallet.ofs.OfsResponse response = ofsMessageService.composeOfsMessage(ofsMessage);
		OfsResponse ofs = new OfsResponse();
		try {
			BeanUtils.copyProperties(ofs, response);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ofsMessageRepository.save(ofs);*/
//		return "onlinepayment/bussewa/bussewa";
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/validateOtptest")
	public String test1(ModelMap modelMap, HttpServletRequest request, @RequestParam ("otp") String otp, @RequestParam ("id") Long id) {
		try {
			Boolean valid = cardlessApi.validateOtp(otp, id);
			System.out.println("otp validity "+ valid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String cardno = request.getParameter("cardNo");
		
		// merchantPaymentService.dishHome();;
		OfsMessage ofsMessage = new OfsMessage();
		ofsMessage.getMessageData().setAT_UNIQUE_ID(System.currentTimeMillis()+"");
		ofsMessage.getMessageData().setCARD_NO(cardno);
		ofsMessage.getMessageData().setATM_TIMESTAMP(System.currentTimeMillis()+"");
		com.wallet.ofs.OfsResponse response = ofsMessageService.composeOfsMessage(ofsMessage);
		OfsResponse ofs = new OfsResponse();
		try {
			BeanUtils.copyProperties(ofs, response);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ofsMessageRepository.save(ofs);*/
//		return "onlinepayment/bussewa/bussewa";
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listentoport")
	public String startSocket(@RequestParam ("port") int port , ModelMap modelMap, HttpServletRequest request)throws BindException {

		int cTosPortNumber = port;

		/*try {
			servSocket = new ServerSocket(cTosPortNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//	    System.out.println("Waiting for a connection on " + cTosPortNumber);
	 
	    	
			try( ServerSocket servSocket  = new ServerSocket(cTosPortNumber)) {
				while(true){
				new Thread( new NodeServerRequestHandler(servSocket.accept(),cardlessOtpApi)).start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    	}
		

@RequestMapping(method = RequestMethod.GET, value = "/sendecho")
public String sendEcho(@RequestParam ("port") int port , ModelMap modelMap, HttpServletRequest request)throws BindException {


	cardlessApi.sendEcho("10.0.7.131", port);
		
		return null;
    	}
	
}

