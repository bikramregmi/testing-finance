package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IMenuTemplateApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.api.IUserTemplateApi;
import com.mobilebanking.model.MenuTemplateDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserTemplateDTO;
import com.mobilebanking.model.error.UserTemplateError;
import com.mobilebanking.repositories.UserTemplateRepository;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.UserTemplateValidation;

@Controller
public class UserTemplateController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private IUserTemplateApi userTemplateApi;

	@Autowired
	private UserTemplateRepository userTemplateRepository;

	@Autowired
	private UserTemplateValidation userTemplateValidation;

	@Autowired
	private IMenuTemplateApi menuTemplateApi;

	@Autowired
	private IUserApi userApi;

	private MessageSource messageSource;

	/*
	 * public UserTemplateController(IUserTemplateApi userTemplateApi,
	 * UserTemplateRepository userTemplateRepository,IMenuTemplateApi
	 * menuTemplateApi,IUserApi userApi) { this.userTemplateApi =
	 * userTemplateApi; this.userTemplateRepository = userTemplateRepository;
	 * this.menuTemplateApi = menuTemplateApi; this.userApi =userApi;
	 * 
	 * }
	 */

	@ModelAttribute("userTemplate")
	public UserTemplateDTO getUserTemplate() {
		return new UserTemplateDTO();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addUserTemplate")
	public String addUserTemplate(ModelMap modelMap, HttpServletRequest request) {
		logger.debug("in get addUserTemplate==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<MenuTemplateDTO> menuTemplateList = new ArrayList<MenuTemplateDTO>();
				List<UserDTO> userList = new ArrayList<UserDTO>();
				menuTemplateList = menuTemplateApi.findMenuTemplate();
				userList = userApi.findUser();
				modelMap.put("menuTemplateList", menuTemplateList);
				modelMap.put("userList", userList);

				return "UserTemplate/" + "addUserTemplate";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addUserTemplate")
	public String addUserTemplate(@ModelAttribute("userTemplate") UserTemplateDTO userTemplateDTO, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.debug("in post addUserTemplate==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				logger.debug("userTemplateDTO ==>" + userTemplateDTO);
				if (userTemplateDTO == null) {
					logger.debug("userTemplateDTO null==>" + userTemplateDTO);
					return "redirect:/Static/500";
				} else {
					logger.debug("userTemplateDTO not null==>");
					try {
						userTemplateDTO.setOperation("add");
						UserTemplateError error = new UserTemplateError();
						error = userTemplateValidation.validateUserTemplate(userTemplateDTO);
						
						if (error.isValid()) {
							userTemplateApi.saveUserTemplate(userTemplateDTO);
							redirectAttributes.addFlashAttribute("message", "userTemplate.add.sucessfull");

							return "redirect:/listUserTemplate";

						} else {
							logger.debug("error occured while adding==>");
							modelMap.put("error", error);
							modelMap.put("userTemplate", userTemplateDTO);
							return "UserTemplate/addUserTemplate";
						}

					} catch (Exception e) {
						logger.debug("e==>" + e);
						System.out.println("Exception:" + e.getMessage());
						return "redirect:/Static/500";
					}
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listUserTemplate")
	public String listUserTemplate(ModelMap modelMap, HttpServletRequest request, Model model,
			HttpServletResponse response) {

		 List<UserTemplateDTO> dtoList= userTemplateApi.getAllUserTemplate();
		 

		// modelMap.put("userTemplateList", copy);
		 
		 modelMap.put("userTemplate", dtoList);
		String param = (String) model.asMap().get("message");
		logger.debug("param:" + param);

		if (param != null && !param.trim().equals("")) {
			modelMap.put("message", param == null ? "" : messageSource.getMessage(param, null, Locale.ROOT));
		}
		return "/UserTemplate/listUserTemplate";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editUserTemplate")
	public String editUserTemplate(ModelMap modelMap,
			@RequestParam(value = "userTemplateId", required = true) Long userTemplateId,
			HttpServletRequest request) {

		UserTemplateDTO userTemplate = userTemplateApi.getUserTemplateWithId(userTemplateId);
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (userTemplate == null) {
					logger.debug("userTemplateDTO null==>" + userTemplate);
					return "redirect:/Static/500";
				} else {
					
					List<MenuTemplateDTO> menuTemplate  = menuTemplateApi.findMenuTemplate();
					modelMap.put("userTemplate", userTemplate);
					modelMap.put("menuTemplate", menuTemplate);
					return "UserTemplate/editUserTemplate";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editUserTemplate")
	public String editCustomer(@ModelAttribute("userTemplate") UserTemplateDTO userTemplateDTO, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.debug("in post editUserTemplate==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (userTemplateDTO == null) {
					logger.debug("userTemplateDTO null==>" + userTemplateDTO);
					return "redirect:/Static/500";
				} else {
					try {
						userTemplateDTO.setOperation("edit");
						UserTemplateError error = new UserTemplateError();
						error = userTemplateValidation.validateUserTemplate(userTemplateDTO);
						error.setValid(true);
						logger.debug("valid==>" + error.isValid());
						if (error.isValid()) {
							userTemplateApi.editUserTemplate(userTemplateDTO);
							redirectAttributes.addFlashAttribute("message", "userTemplate.edit.sucessfull");
							return "redirect:/listUserTemplate";

						} else {
							logger.debug("error occured while editing==>");
							modelMap.put("error", error);
							modelMap.put("userTemplate", userTemplateDTO);
							return "UserTemplate/editUserTemplate";
						}
					} catch (Exception e) {
						logger.debug("e==>" + e);
						e.printStackTrace();
						System.out.println("Exception:" + e.getMessage());
						return "redirect:/Static/500";
					}
				}
			}
		}
		return "redirect:/";
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
