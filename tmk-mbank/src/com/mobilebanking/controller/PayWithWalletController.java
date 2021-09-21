package com.mobilebanking.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.util.AuthenticationUtil;

@Controller
public class PayWithWalletController {
	

	@RequestMapping(method = RequestMethod.GET, value = "/onlinepayment")
	public String peerToPeerTransfer(ModelMap modelMap, HttpServletRequest request) {
		String authority = AuthenticationUtil.getCurrentUser().getAuthority();
		return "PayWithWallet/showprice";
	}

}
