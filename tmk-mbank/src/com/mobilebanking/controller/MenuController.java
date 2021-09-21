package com.mobilebanking.controller;

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

import com.mobilebanking.api.IMenuApi;
import com.mobilebanking.model.MenuDTO;
import com.mobilebanking.model.error.MenuError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.MenuValidation;

@Controller
public class MenuController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private IMenuApi menuApi;
	
	@Autowired
	private MenuValidation menuValidation;
	
	private MessageSource messageSource;

	/*public MenuController(IMenuApi menuApi,MenuValidation menuValidation) {
		this.menuApi = menuApi;
		this.menuValidation=menuValidation;
	}*/

	@ModelAttribute("menu")
	public MenuDTO getMenu() {
		return new MenuDTO();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listMenu")
	public String listMenu(ModelMap modelMap, HttpServletRequest request, Model model, HttpServletResponse response) {

		List<MenuDTO> menuDtoList = menuApi.findAllMenu();
		modelMap.put("menuList", menuDtoList);
		String param = (String) model.asMap().get("message");
		logger.debug("param:" + param);

		if (param != null && !param.trim().equals("")) {
			modelMap.put("message", param == null ? "" : messageSource.getMessage(param, null, Locale.ROOT));
		}
		return "/Menu/listMenu";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addMenu")
	public String addMenu(ModelMap modelMap, HttpServletRequest request) {
		logger.debug("in get addMenu==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<MenuDTO> menuDTO= menuApi.findAllSuperMenu();
				modelMap.put("superMenu", menuDTO);
				return "Menu/" + "addMenu";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addMenu")
	public String addMenu(@ModelAttribute("menu") MenuDTO menuDTO, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.debug("in post addMenu==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				logger.debug("menuDTO ==>" + menuDTO);
				if (menuDTO == null) {
					logger.debug("menuDTO null==>" + menuDTO);
					return "redirect:/Static/500";
				} else {
					logger.debug("menuDTO not null==>");
					try {
						MenuError error = menuValidation.validateMenu(menuDTO);
						if (error.isValid()) {
							menuApi.saveMenu(menuDTO);
							redirectAttributes.addFlashAttribute("message", "menu.add.sucessfull");
							return "redirect:/listMenu";

						} else {
							logger.debug("error occured while adding==>");
							modelMap.put("error", error);
							modelMap.put("menu", menuDTO);
							return "Menu/addMenu";
						}

					} catch (Exception e) {
						logger.debug("e==>" + e);
						return "redirect:/Static/500";
					}
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editMenu")
	public String editMenu(ModelMap modelMap, @RequestParam(value = "menuId", required = true) String menuId,
			HttpServletRequest request) {

		MenuDTO menu = menuApi.getMenuDtoById(Long.parseLong(menuId));
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (menu == null) {
					logger.debug("menuDTO null==>" + menu);
					return "redirect:/Static/500";
				} else {
					modelMap.put("menu", menu);

					return "Menu/editMenu";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editMenu")
	public String editCustomer(@ModelAttribute("menu") MenuDTO menuDTO, ModelMap modelMap, HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.debug("in post editMenu==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (menuDTO == null) {
					logger.debug("menuDTO null==>" + menuDTO);
					return "redirect:/Static/500";
				} else {
					try {
						MenuError error = menuValidation.validateMenu(menuDTO);
						logger.debug("valid==>" + error.isValid());
						if (error.isValid()) {
							menuApi.editMenu(menuDTO);
							redirectAttributes.addFlashAttribute("message", "menu.edit.sucessfull");

							return "redirect:/listMenu";

						} else {
							logger.debug("error occured while editing==>");
							modelMap.put("error", error);
							modelMap.put("menu", menuDTO);
							return "Menu/editMenu";
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

}
