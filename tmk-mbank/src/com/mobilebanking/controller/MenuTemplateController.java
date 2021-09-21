package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.mobilebanking.api.IMenuApi;
import com.mobilebanking.api.IMenuTemplateApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.MenuDTO;
import com.mobilebanking.model.MenuTemplateDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.error.MenuTemplateError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.MenuTemplateValidation;

@Controller
public class MenuTemplateController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private IMenuTemplateApi menuTemplateApi;
	@Autowired
	private IMenuApi menuApi;
	@Autowired
	private IUserApi userApi;

	@Autowired
	private MenuTemplateValidation menuTemplateValidation;
	@Autowired
	private MessageSource messageSource;

	/*
	 * public MenuTemplateController(IMenuTemplateApi menuTemplateApi, IMenuApi
	 * menuApi, IUserApi userApi,MenuTemplateValidation menuTemplateValidation)
	 * { this.menuTemplateApi = menuTemplateApi; this.menuApi = menuApi;
	 * this.userApi = userApi;
	 * this.menuTemplateValidation=menuTemplateValidation;
	 * 
	 * }
	 */

	@ModelAttribute("menuTemplate")
	public MenuTemplateDTO getMenuTemplate() {
		return new MenuTemplateDTO();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addMenuTemplate")
	public String addMenuTemplate(ModelMap modelMap, HttpServletRequest request) {

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				List<UserType> userType = new ArrayList<UserType>();
				userType.add(UserType.Admin);
				userType.add(UserType.Bank);
				userType.add(UserType.BankBranch);
				// userType.add(UserType.ChannelPartner);
				modelMap.put("userTypeList", userType);
				return "MenuTemplate/" + "addMenuTemplate";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addMenutotemplate")
	public String addMenutotemplate(@RequestParam(value = "templateId", required = true) String templateId,
			ModelMap modelMap, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.debug("in get addMenuTemplate==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				MenuTemplateError error = menuTemplateValidation.validateMenuTemplateExist(Long.parseLong(templateId));
				if (error.isValid()) {
					MenuTemplateDTO menuTemplate = menuTemplateApi.getMenuTemplateWithId(Long.parseLong(templateId));
					List<MenuDTO> menu = menuApi.getAllSubMenuExcNot(Long.parseLong(templateId));
					List<MenuDTO> Addedmenu = menuApi.getAllSubMenuIncluded(Long.parseLong(templateId));
					modelMap.put("menu", menu);
					modelMap.put("menuTemplate", menuTemplate);
					modelMap.put("Addedmenu", Addedmenu);
				} else {
					redirectAttributes.addFlashAttribute("error", error);
				}
				return "MenuTemplate/" + "MenuTemplateConnector";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addMenutotemplate")
	public String addMenutotemplatepost(@RequestParam(value = "templateId", required = true) String templateId,
			@RequestParam(value = "menuUrl", required = true) String menuUrl, ModelMap modelMap,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.debug("in get addMenuTemplate==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				MenuTemplateError error = menuTemplateValidation.validateMenuTemplateAndUrl(Long.parseLong(templateId),
						menuUrl);
				if (error.isValid()) {
					menuTemplateApi.addMenuTotemp(Long.parseLong(templateId), menuUrl);
					redirectAttributes.addFlashAttribute("message", "Menu added successfully.");
				} else {
					redirectAttributes.addFlashAttribute("error", error);
				}
				return "redirect:/addMenutotemplate?templateId=" + templateId;
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addMenuTemplate")
	public String addMenuTemplate(@ModelAttribute("menuTemplate") MenuTemplateDTO menuTemplateDTO, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.debug("in post addMenuTemplate==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				logger.debug("menuTemplateDTO ==>" + menuTemplateDTO);
				if (menuTemplateDTO == null) {
					logger.debug("menuTemplateDTO null==>" + menuTemplateDTO);
					return "redirect:/Static/500";
				} else {
					logger.debug("menuTemplateDTO not null==>");
					try {
						MenuTemplateError error = menuTemplateValidation.validateMenuTemplate(menuTemplateDTO);
						if (error.isValid()) {
							menuTemplateApi.saveMenuTemplate(menuTemplateDTO);
							redirectAttributes.addFlashAttribute("message", "Menu Template Add Sucessfull");
							return "redirect:/listMenuTemplate";
						} else {
							logger.debug("error occured while adding==>");
							modelMap.put("error", error);
							modelMap.put("menuTemplate", menuTemplateDTO);
							return "MenuTemplate/addMenuTemplate";
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

	@RequestMapping(method = RequestMethod.GET, value = "/listMenuTemplate")
	public String listMenuTemplate(ModelMap modelMap, HttpServletRequest request, Model model,
			HttpServletResponse response) {

		List<MenuTemplateDTO> menuTemplateDtoList = menuTemplateApi.getAllMenuTemplateDto();

		modelMap.put("menuTemplateList", menuTemplateDtoList);
		return "/MenuTemplate/listMenuTemplate";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editMenuTemplate")
	public String editMenuTemplate(ModelMap modelMap,
			@RequestParam(value = "menuTemplateId", required = true) String menuTemplateId,
			HttpServletRequest request) {

		MenuTemplateDTO menuTemplate = menuTemplateApi.getMenuTemplateDtoById(Long.parseLong(menuTemplateId));
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (menuTemplate == null) {
					logger.debug("menuTemplateDTO null==>" + menuTemplate);
					return "redirect:/Static/500";
				} else {
					modelMap.put("menuTemplate", menuTemplate);

					return "MenuTemplate/editMenuTemplate";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editMenuTemplate")
	public String editCustomer(@ModelAttribute("menuTemplate") MenuTemplateDTO menuTemplateDTO, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.debug("in post editMenuTemplate==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (menuTemplateDTO == null) {
					logger.debug("menuTemplateDTO null==>" + menuTemplateDTO);
					return "redirect:/Static/500";
				} else {
					try {
						MenuTemplateError error = menuTemplateValidation.validateMenuTemplate(menuTemplateDTO);
						logger.debug("valid==>" + error.isValid());
						if (error.isValid()) {
							menuTemplateApi.editMenuTemplate(menuTemplateDTO);
							redirectAttributes.addFlashAttribute("message", "Menu Template Edit Sucessfull");
							return "redirect:/listMenuTemplate";

						} else {
							logger.debug("error occured while editing==>");
							modelMap.put("error", error);
							modelMap.put("menuTemplate", menuTemplateDTO);
							return "MenuTemplate/editMenuTemplate";
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

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(method = RequestMethod.GET, value = "templatemenu/remove")
	public String removeMenuFromTemplate(ModelMap modelMap,
			@RequestParam(value = "menu_id", required = true) String menuId,
			@RequestParam(value = "template_id", required = true) String templateId, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		logger.debug("in get addMenuTemplate==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				menuTemplateApi.removeMenufromtemp(Long.parseLong(templateId), Long.parseLong(menuId));
				redirectAttributes.addFlashAttribute("message", "Menu Removed successfully.");
				return "redirect:/addMenutotemplate?templateId=" + templateId;
			}
		}
		return "redirect:/";
	}

}
