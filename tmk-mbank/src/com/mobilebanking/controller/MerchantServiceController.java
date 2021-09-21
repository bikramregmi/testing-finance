/**
 * 
 */
package com.mobilebanking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.api.IServiceCategoryApi;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.ServiceCategoryDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.error.MerchantServiceError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.MerchantServiceValidation;

/**
 * @author bibek
 *
 */
@Controller
public class MerchantServiceController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(MerchantController.class);

	@Autowired
	IMerchantApi merchantApi;

	@Autowired
	IMerchantServiceApi merchantServiceApi;

	@Autowired
	private IServiceCategoryApi serviceCategoryApi;

	@Autowired
	MerchantServiceValidation merchantServiceValidation;

	@RequestMapping(value = "/merchant/service/add", method = RequestMethod.GET)
	public String addMerchantService(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<MerchantDTO> merchantList = merchantApi.getMerchantByStatus(Status.Active);
				modelMap.put("merchantList", merchantList);
				List<ServiceCategoryDTO> servideProvidetList = serviceCategoryApi.getAllServiceCategory();
				modelMap.put("serviceCategory", servideProvidetList);
				return "Service/addService";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/merchant/service/add", method = RequestMethod.POST)
	public String addMerchantService(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("service") MerchantServiceDTO merchantServiceDto) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				MerchantServiceError merchantServiceError = new MerchantServiceError();
				merchantServiceError = merchantServiceValidation.merchantServiceValidation(merchantServiceDto);
				if (merchantServiceError.isValid()) {
					try {
						merchantServiceApi.saveMerchantService(merchantServiceDto);
					} catch (Exception e) {
						e.printStackTrace();
						logger.debug(e.getMessage());
						List<ServiceCategoryDTO> servideProvidetList = serviceCategoryApi.getAllServiceCategory();
						modelMap.put("serviceCategory", servideProvidetList);
						List<MerchantDTO> merchantList = merchantApi.getMerchantByStatus(Status.Active);
						modelMap.put("merchantList", merchantList);
						modelMap.put("message", "Could Not Add Service");
						return "Service/addService";
					}
					modelMap.put("message", "Service Added Successfully");
					return "redirect:/merchant/service/list";
				} else {
					modelMap.put("error", merchantServiceError);
					List<MerchantDTO> merchantList = merchantApi.getMerchantByStatus(Status.Active);
					modelMap.put("merchantList", merchantList);
					List<ServiceCategoryDTO> servideProvidetList = serviceCategoryApi.getAllServiceCategory();
					modelMap.put("serviceCategory", servideProvidetList);
					return "Service/addService";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/merchant/service/list", method = RequestMethod.GET)
	public String listMerchantService(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<MerchantServiceDTO> merchantServiceList = merchantServiceApi.findAllMerchantService();
				modelMap.put("services", merchantServiceList);
				return "Service/listService";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/ajax/merchant/service/update", method = RequestMethod.POST)
	public Map<String, String> updateMerchantService(ModelMap modelMap, MerchantServiceDTO merchantServiceDto,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> updateResponseMap = new HashMap<String, String>();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				updateResponseMap.put("status", "success");
			}
			updateResponseMap.put("status", "failure");
		}
		return updateResponseMap;
	}

	@RequestMapping(value = "/ajax/service/getServicesByMerchant")
	@ResponseBody
	public Map<String, List<MerchantServiceDTO>> getServicesByMerchant(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("merchant") long merchantId) {
		Map<String, List<MerchantServiceDTO>> serviceResponseMap = new HashMap<String, List<MerchantServiceDTO>>();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				List<MerchantServiceDTO> merchantServiceList = merchantServiceApi
						.getMerchantServicesWithoutCommissionByMerchant(merchantId);
				serviceResponseMap.put("serviceList", merchantServiceList);
			}
		}
		return serviceResponseMap;
	}

	@RequestMapping(value = "/merchant/service/edit", method = RequestMethod.GET)
	public String editMerchantService(@RequestParam("serviceId") long serviceId, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				MerchantServiceDTO service = merchantServiceApi.findMerchantServiceById(serviceId);
				modelMap.put("service", service);
				return "Service/editService";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/merchant/service/edit", method = RequestMethod.POST)
	public String editMerchantService(MerchantServiceDTO merchantServiceDTO, RedirectAttributes redirectAttributes,ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				MerchantServiceError merchantServiceError = new MerchantServiceError();
				merchantServiceError = merchantServiceValidation.merchantServiceEditValidation(merchantServiceDTO);
				if (merchantServiceError.isValid()) {
					MerchantServiceDTO service = merchantServiceApi.editMerchantService(merchantServiceDTO);
					redirectAttributes.addFlashAttribute("message", "Service Edited Successfully");
					return "redirect:/merchant/service/list";
				}else {
					modelMap.put("service", merchantServiceDTO);
					modelMap.put("error", merchantServiceError);
					return "Service/editService";
				}
			}
			return "redirect:/";
		}
		return "redirect:/";
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub

	}

}
