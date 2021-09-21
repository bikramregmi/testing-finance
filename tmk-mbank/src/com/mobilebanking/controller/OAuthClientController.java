package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IOAuthClientApi;
import com.mobilebanking.entity.OAuthClient;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.error.BankOAuthClientError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.BankOAuthClientValidation;

@Controller
@RequestMapping(value = "/oauth")
public class OAuthClientController {

	private Logger logger = LoggerFactory.getLogger(OAuthClientController.class);

	@Autowired
	private IOAuthClientApi oAuthClientApi;

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private BankOAuthClientValidation validation;

	@RequestMapping(value = "/registerclient", method = RequestMethod.GET)
	public String registerClient(HttpServletRequest request, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				List<BankDTO> bankList = bankApi.getBankWithoutOauthClient();
				modelMap.put("bankList", bankList);
				return "OAuth/register";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/registerclient", method = RequestMethod.POST)
	public String registerClient(HttpServletRequest request, HttpServletResponse resposne, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				BankOAuthClientError error = validation.bankOAuthValidation(request.getParameter("redirect_uri"),
						request.getParameter("bank"));
				if (error.isValid()) {
					try {
						OAuthClient oAuthClient = oAuthClientApi.registerClient(request.getParameter("redirect_uri"),
								Long.parseLong(request.getParameter("bank")));
						modelMap.addAttribute("oAuthClient", oAuthClient);
					} catch (Exception e) {
						e.printStackTrace();
						List<BankDTO> bankList = bankApi.getAllBank();
						modelMap.put("bankList", bankList);
						return "redirect:/oauth/registerclient";
					}
				} else {
					System.out.println(error.getBank() + " BANK ");
					System.out.println(error.getRedirectUrl() + " redirect");
					modelMap.put("error", error);
					List<BankDTO> bankList = bankApi.getAllBank();
					modelMap.put("bankList", bankList);
					return "OAuth/register";
				}
				modelMap.addAttribute("message", "Client Registerd Successfully");
				logger.debug("Client Registered");
				return "redirect:/oauth/viewclient";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/viewclient", method = RequestMethod.GET)
	public String viewClient(HttpServletRequest request, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				List<OAuthClient> oAuthClients = oAuthClientApi.listAllOAuthClients();
				modelMap.put("oAuthClients", oAuthClients);
				return "OAuth/list";
			}
		}
		return "redirect:/";
	}
}
