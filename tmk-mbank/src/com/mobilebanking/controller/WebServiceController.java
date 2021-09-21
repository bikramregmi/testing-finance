package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.api.IWebServiceApi;
import com.mobilebanking.model.WebServiceDTO;
import com.mobilebanking.model.error.WebServiceError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.WebServiceValidation;

@Controller
public class WebServiceController {

	@Autowired
	private IWebServiceApi webServiceApi;
	
	@Autowired
	private WebServiceValidation webServiceValidation;

	/*public WebServiceController(IWebServiceApi webServiceApi,
			WebServiceValidation webServiceValidation) {
		this.webServiceApi = webServiceApi;
		this.webServiceValidation = webServiceValidation;
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "/addApiUser")
	public String addUser(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "msg", required = false) String msg) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser()
					.getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				if (msg != null) {
					modelMap.put("msg", msg);
				}
				return "WebService/" + "addApiUser";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addApiUser")
	public String addUserPost(WebServiceDTO dto, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser()
					.getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				try {
					WebServiceError error = new WebServiceError();
					error = webServiceValidation.validateUser(dto);
					if (error.isValid()) {
						webServiceApi.createUser(dto);
						return "WebService/" + "viewApiUser";
					} else {
						modelMap.put("user", dto);
						modelMap.put("error", error);
						return "WebService/" + "addApiUser";
					}
				} catch (Exception e) {
					modelMap.put("msg", "Service Down !!!");
					return "redirect:/WebService/addApiUser";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/viewApiUser")
	public String getUser(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "msg", required = false) String msg) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser()
					.getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				try {
					if (msg != null) {
						modelMap.put("msg", msg);
					}
					List<WebServiceDTO> dtoList = new ArrayList<WebServiceDTO>();
					dtoList = webServiceApi.getAlluser();
					modelMap.put("serviceList", dtoList);
					return "WebService/" + "viewApiUser";
				} catch (Exception e) {
					System.out.println("Exception e:" + e.getMessage());
				}
			}
		}
		return "redirect:/";
	}

}
