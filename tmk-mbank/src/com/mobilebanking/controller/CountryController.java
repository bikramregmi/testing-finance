package com.mobilebanking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.ICountryApi;
import com.mobilebanking.model.CountryDTO;
import com.mobilebanking.model.MultiSelectDTO;
import com.mobilebanking.model.error.CountryError;
import com.mobilebanking.validation.CountryValidation;

@Controller
public class CountryController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	private ICountryApi countryApi;
	@Autowired
	private CountryValidation countryValidation;

	@Autowired
	private MessageSource messageSource;

	/*public CountryController(ICountryApi countryApi, CountryValidation countryValidation, ICurrencyApi currencyApi) {
		this.countryApi = countryApi;
		this.countryValidation = countryValidation;
		this.currencyApi = currencyApi;
	}
*/
	@ModelAttribute("multiSelectDTO")
	public MultiSelectDTO getMultiSelectDTO() {
		return new MultiSelectDTO();
	}



	@RequestMapping(method = RequestMethod.GET, value = "/addCountry")
	public String addCountry(ModelMap modelmap, HttpServletRequest request) {

		return "redirect:/";
//		if (AuthenticationUtil.getCurrentUser() != null) {
//			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
//			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
//				return "Country/" + "addCountry";
//
//			}
//		}
//		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addCountry")

	public String addCountry(ModelMap modelMap, @ModelAttribute("country") CountryDTO countryDTO,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (countryDTO == null) {
			logger.debug("country null");
			return "redirect:/Static/500";
		} else {
			try {
				CountryError countryError = new CountryError();
				countryError = countryValidation.countryValidation(countryDTO);
				logger.debug("valid" + countryError.isValid());
				if (countryError.isValid()) {
					countryApi.saveCountry(countryDTO);
					String message = messageSource.getMessage("country.save.success",
							new Object[] { countryDTO.getName() }, "Country has been successfully saved.", null);
					redirectAttributes.addFlashAttribute("message", message);
					return "redirect:/listCountry";
				} else {
					logger.debug("error while adding");
					modelMap.put("error", countryError);
					modelMap.put("country", countryDTO);
					return "Country/addCountry";
				}

			} catch (Exception e) {
				logger.debug("exception" + e);
				return "redirect:/Static/500";
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listCountry")
	public String listCountry(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/";
//
//		List<CountryDTO> countryDTOList = new ArrayList<CountryDTO>();
//		countryDTOList = countryApi.getAllCountry();
//		modelMap.put("countryList", countryDTOList);
//		logger.debug("agentList" + countryDTOList);
//		return "/Country/listCountry";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editCountry")
	public String editCountry(ModelMap modelMap, @RequestParam(value = "countryId", required = true) String countryId,
			HttpServletRequest request, HttpServletResponse response) {
//		if (AuthenticationUtil.getCurrentUser() != null) {
//			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
//			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
//				CountryDTO countryDTO = countryApi.findCountryById(Long.parseLong(countryId));
//				if (countryDTO == null) {
//					logger.debug("country null");
//					return "redirect:Static/500";
//				} else {
//					modelMap.put("country", countryDTO);
//
//					return "Country/editCountry";
//
//				}
//			}
//		}
		return "redirect:/";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/editCountry")
	public String editCountry(ModelMap modelMap, @ModelAttribute("country") CountryDTO countryDTO,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.debug("at edit country");
		if (countryDTO == null) {
			logger.debug("null at country");
			return "redirect:/Static/500";
		} else {
			try {
				CountryError countryError = new CountryError();
				countryError = countryValidation.countryValidation(countryDTO);
				if (countryError.isValid()) {
					logger.debug("error valid" + countryError.isValid());
					countryApi.editCountry(countryDTO);;
					redirectAttributes.addFlashAttribute("message", "country.edited.succesfully");
					return "redirect:/listCountry";
				} else {
					logger.debug("error during edit");
					modelMap.put("countryError", countryError);
					modelMap.put("country", countryDTO);
					return "Country/editCountry";
				}
			} catch (Exception e) {
				logger.debug("exception occured=>" + e);
				return "redirect:/Static/500";
			}
		}
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/deleteCountry")
	public String deleteCountry(@RequestParam(value = "countryId", required = true) String countryId,  RedirectAttributes redirectAttributes) {
		/*if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.SUPER_AGENT) && authority.contains(Authorities.AUTHENTICATED)) {

				countryApi.deleteCountry(Long.parseLong(countryId));
				redirectAttributes.addFlashAttribute("message", "country.delete.successfull");
				return "redirect:/listAgent";
			}
			return "/";
		}*/
		return "redirect:/";
	}
	

}
