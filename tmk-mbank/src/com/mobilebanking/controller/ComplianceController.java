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

import com.mobilebanking.api.IComplianceApi;
import com.mobilebanking.api.ICountryApi;
import com.mobilebanking.entity.Compliance;
import com.mobilebanking.model.ComplianceDTO;
import com.mobilebanking.model.CountryDTO;
import com.mobilebanking.model.error.ComplianceError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.ComplianceValidation;

@Controller
public class ComplianceController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(ComplianceController.class);

	@Autowired
	private IComplianceApi complianceApi;
	@Autowired
	private ComplianceValidation complianceValidation;
	@Autowired
	private ICountryApi countryApi;
	
	private MessageSource messageSource;

	/*public ComplianceController(IComplianceApi complianceApi,
			ComplianceValidation complianceValidation, ICountryApi countryApi) {
		this.complianceApi = complianceApi;
		this.complianceValidation = complianceValidation;
		this.countryApi = countryApi;

	}
*/
	@RequestMapping(method = RequestMethod.GET, value = "/addCompliance")
	public String addCompliance(ModelMap modelMap, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser()
					.getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				List<CountryDTO> countryList = new ArrayList<CountryDTO>();
				countryList = countryApi.findCountry();
				modelMap.put("countryList", countryList);
				return "Compliance/" + "addCompliance";
			}
		}
		return "redirect:/";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/addCompliance")
	public String addCompliance(ModelMap modelMap,
			@ModelAttribute("compliance") ComplianceDTO complianceDTO,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser()
					.getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				if (complianceDTO == null) {
					logger.debug("compliance null");
					return "redirect:/Static/500";
				} else {
					try {
						ComplianceError complianceError = new ComplianceError();
						complianceError = complianceValidation
								.complianceValidation(complianceDTO);
						logger.debug("valid" + complianceError.isValid());
						if (complianceError.isValid()) {
							complianceApi.saveCompliance(complianceDTO);

							redirectAttributes.addFlashAttribute("message",
									"compliance.Added.Successfully");

							return "redirect:/listCompliance";
						} else {
							logger.debug("error while adding");
							modelMap.put("error", complianceError);
							modelMap.put("compliance", complianceDTO);
							return "Compliance/listCompliance";
						}

					} catch (Exception e) {
						logger.debug("exception" + e);
						return "redirect:/Static/500";
					}
				}
			}
		}
		return "redirect:/";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listCompliance")
	public String listCompliance(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		List<ComplianceDTO> complianceDTOList = new ArrayList<ComplianceDTO>();
		complianceDTOList = complianceApi.getAllCompliance();
		modelMap.put("complianceList", complianceDTOList);
		logger.debug("Compliance List" + complianceDTOList);
		String param = (String) model.asMap().get("message");
		logger.debug("param:" + param);

		if (param != null && !param.trim().equals("")) {
			modelMap.put(
					"message",
					param == null ? "" : messageSource.getMessage(param, null,
							Locale.ROOT));
		}
		return "/Compliance/listCompliance";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editCompliance")
	public String editCompliance(
			ModelMap modelMap,
			@RequestParam(value = "complianceId", required = true) String complianceId,
			HttpServletRequest request, HttpServletResponse response) {
		Compliance compliance = complianceApi.getComplianceById(Long
				.parseLong(complianceId));
		modelMap.put("compliance", compliance);
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser()
					.getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				if (compliance == null) {
					logger.debug("compliance null");
					return "redirect:Static/500";
				} else {
					List<CountryDTO> countryList = new ArrayList<CountryDTO>();
					countryList = countryApi.findCountry();
					modelMap.put("countryList", countryList);
					modelMap.put("compliance", compliance);

					return "Compliance/editCompliance";

				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editCompliance")
	public String editCompliance(ModelMap modelMap,
			@ModelAttribute("compliance") ComplianceDTO complianceDTO,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		logger.debug("at edit compliance");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser()
					.getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				if (complianceDTO == null) {
					logger.debug("null at compliance");
					return "redirect:/Static/500";
				} else {
					try {
						ComplianceError complianceError = new ComplianceError();
						complianceError = complianceValidation
								.complianceValidation(complianceDTO);
						if (complianceError.isValid()) {
							logger.debug("error valid="
									+ complianceError.isValid());
							complianceApi.editCompliance(complianceDTO);

							redirectAttributes.addFlashAttribute("message",
									"compliance.edited.successfully");

							return "redirect:/listCompliance";

						} else {
							logger.debug("error during edit");
							modelMap.put("complianceError", complianceError);
							modelMap.put("compliance", complianceDTO);
							return "Compliance/editCompliance";
						}
					} catch (Exception e) {
						logger.debug("exception occured=>" + e);
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
