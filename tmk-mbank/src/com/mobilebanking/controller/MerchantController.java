package com.mobilebanking.controller;

import java.util.ArrayList;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.api.IMerchantManagerApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.error.MerchantError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.MerchantValidation;

@Controller
public class MerchantController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(MerchantController.class);

	@Autowired
	private IMerchantApi merchantApi;

	@Autowired
	private ICityApi cityApi;
	
	@Autowired
	private IStateApi stateApi;
	
	@Autowired
	private IBankApi bankApi;

	@Autowired
	private MerchantValidation merchantValidation;
	
	@Autowired
	private IMerchantServiceApi merchantServiceApi;
	
	@Autowired
	private IMerchantManagerApi merchantManagerApi;
	
	private MessageSource messageSource;
	

	@ModelAttribute("merchant")
	public MerchantDTO getUser() {
		return new MerchantDTO();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/merchant/edit")
	public String editMerchant(ModelMap modelMap,
	@RequestParam(value = "merchantId", required = true) String merchantId,
	HttpServletRequest request) {
		
		MerchantDTO merchantDTO = merchantApi.findMerchantById(Long.parseLong(merchantId));
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (merchantDTO == null) {
					logger.debug("customerDTO null==>" + merchantDTO);
					return "redirect:/merchant/add";
				} else {
					List<StateDTO> stateList = stateApi.getAllState();
					modelMap.put("stateList", stateList);
					List<CityDTO> cityList = cityApi.findCityByStateName(merchantDTO.getState());
					modelMap.put("cityList", cityList);
					modelMap.put("merchant", merchantDTO);
					return "Merchant/editMerchant";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/merchant/edit")
	public String editCustomer(@ModelAttribute("merchantDTO") MerchantDTO merchantDTO, ModelMap modelMap,
			HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		
		logger.debug("in post editMerchant==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (merchantDTO == null) {
					logger.debug("merchantDTO null==>" + merchantDTO);
					return "redirect:/merchant/edit";
				} else {
					try {
						MerchantError error = new MerchantError();
						error = merchantValidation.merchantEditValidation(merchantDTO);
						logger.debug("valid==>" + error.isValid());
						if (error.isValid()) {
							merchantApi.editMerchant(merchantDTO);;
							redirectAttributes.addFlashAttribute("message", "Merchant Edited Successfull");
							return "redirect:/merchant/list";
						} else {
							logger.debug("error occured while adding==>");
							modelMap.put("error", error);
							List<StateDTO> stateList = stateApi.getAllState();
							modelMap.put("stateList", stateList);
							List<CityDTO> cityList = cityApi.findCityByStateName(merchantDTO.getState());
							modelMap.put("cityList", cityList);
							modelMap.put("merchant",merchantDTO);
							return "Merchant/editMerchant";
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

	@RequestMapping(method = RequestMethod.GET, value = "/merchant/add")
	public String addMerchant(ModelMap modelMap, Model model, HttpServletRequest request) {
		logger.debug("in get addMerchant==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				String mapping = request.getServletPath();
				System.out.println(" request mapping value " + mapping);

				List<StateDTO> statesList = stateApi.getAllState();
				modelMap.put("statesList", statesList);
		        model.addAttribute("merchantDto",new MerchantDTO());
		        
				return "Merchant/" + "addMerchant";

			}
			return "redirect:/";
			
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/merchant/add",method = RequestMethod.POST)
	public String addMerchant(@ModelAttribute("merchant") MerchantDTO merchantDto, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		logger.debug("in post Merchant==>");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			System.out.println("testing for superagent " + authority);
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (merchantDto == null) {
					logger.debug("Merchant null==>" + merchantDto);
					return "redirect:/merchant/add";
				} else {
					try {
						MerchantError error = new MerchantError();
						error = merchantValidation.merchantValidation(merchantDto);
						logger.debug("valid==>" + error.isValid());
						if (error.isValid()) {
							merchantApi.saveMerchant(merchantDto);
							redirectAttributes.addFlashAttribute("message", "merchant.added.successfull");
							return "redirect:/merchant/list";

						} else {
							System.out.println(error+ " this is error");
							logger.debug("error occured while adding==>");
							modelMap.put("error", error);
							List<StateDTO> statesList = stateApi.getAllState();
							modelMap.put("statesList", statesList);
							modelMap.put("merchant", merchantDto);
							return "Merchant/addMerchant";
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.debug("e==>" + e);
						System.out.println("Exception:" + e.getMessage());
						return "redirect:/merchant/add";
					}
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/merchant/list")
	public String listMerchant(ModelMap modelMap, HttpServletRequest request, Model model,
			HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			List<MerchantDTO> merchantList = new ArrayList<MerchantDTO>();
			merchantList = merchantApi.getAllMerchant();
			modelMap.put("merchantList", merchantList);
			return "/Merchant/listMerchant";
		}
		
		return "redirect:/";
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/ajax/merchant/getByBank")
	@ResponseBody
	public Map<String, List<MerchantDTO>> getMerchantsByBank(
			HttpServletRequest request, HttpServletResponse response, @RequestParam("bank") long bankId) {
		Map<String, List<MerchantDTO>> bankMerchantMap = new HashMap<String, List<MerchantDTO>>();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<MerchantDTO> merchantList = new ArrayList<MerchantDTO>();
				BankDTO bankDto = bankApi.getBankDtoById(bankId);
				merchantList = bankDto.getMerchant();
				bankMerchantMap.put("merchantList", merchantList);
			}
			
		}
		return bankMerchantMap;
	}

	@RequestMapping(value = "/merchant/delete",method = RequestMethod.GET)
	public String deleteMerchant(ModelMap modelMap, Model model,
			@RequestParam(value = "merchantId", required = true) String merchantId, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				merchantApi.deleteMerchant(Long.parseLong(merchantId));
				redirectAttributes.addFlashAttribute("message", "merchant.delete.successfull");
				return "redirect:/listMerchant";
			}
			return "/";
		}
		return "/";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/merchant/payment")
	public String merchantPayment(ModelMap modelMap, Model model,
			@RequestParam(value = "serviceIdentifier", required = true) String serviceIdentifier,
			@RequestParam(value = "transactionIdentifier", required = true) String transactionIdentifier,
			@RequestParam(value = "paymentAmount", required = true) String paymentAmount, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		return "/";
	}
	
	@RequestMapping(value = "/merchant/manageService",method = RequestMethod.GET)
	public String manageService(ModelMap modelMap, Model model, HttpServletRequest request,
			@RequestParam(value = "merchantId", required = true) long merchantId,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<MerchantServiceDTO> merchantServiceList = merchantServiceApi.getAllMerchantServiceExceptIncluded(merchantId);
				MerchantDTO merchantDto = merchantApi.findMerchantById(merchantId);
				List<MerchantServiceDTO> merchantService = merchantServiceApi.getAllMerchantServicesByMerchant(merchantId);
				modelMap.put("merchantDto", merchantDto);
				modelMap.put("merchantServiceList", merchantServiceList);
				modelMap.put("merchantId",  merchantId);
				modelMap.put("merchantServiceDtoList", merchantService);
				return "Merchant/manageService";
			}
			return "/";
		}
		return "/";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/merchant/addservicetomerchant")
	public String addMerchantServiceToServiceProvider(@RequestParam("merchantServiceId") long merchantServiceId,  @RequestParam(value = "merchantId", required = true) long serviceProviderId) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					
				merchantApi.addMerchantServiceToMerchant(merchantServiceId, serviceProviderId);
				return "redirect:/merchant/manageService?merchantId="+serviceProviderId;
				
			}
		}
	
		return "redirect:/";
	}


	@RequestMapping(method = RequestMethod.GET, value = "/merchant/deleteservicefromserviceprovider")
	public String deleteService(ModelMap modelMap, @RequestParam(value = "merchantId", required = true) long serviceProviderId , @RequestParam(value = "serviceId", required = true) long serviceId, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				
				merchantManagerApi.deleteServiceFromMerchant(serviceId,serviceProviderId);
				
				return "redirect:/addservicetoserviceprovider?serviceproviderid="+serviceProviderId;
			}
		}
	
		return "redirect:/";
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/changestatus")
	public String changeStatus(@RequestParam(value = "serviceId", required = false) long serviceId,
			@RequestParam(value = "serviceProviderId", required = false) long serviceProviderId,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					
				//serviceProviderService.changeStatus(serviceProviderId);
				merchantManagerApi.changeStatus(serviceProviderId, serviceId);
				return "redirect:/merchant/service/manageprovider?serviceid="+serviceId;
				
			}
		}
	
		return "redirect:/";
	}

	
	

	/*
	 * 
	 * 
	 * @RequestMapping(method = RequestMethod.GET, value =
	 * "/Static/{staticPage}") public String getStatic(ModelMap
	 * map, @PathVariable("staticPage") String staticPage) { return "Static/" +
	 * staticPage; }
	 * 
	 * @RequestMapping(method = RequestMethod.POST, value =
	 * "/Static/{staticPage}") public String getStaticPost(ModelMap
	 * map, @PathVariable("staticPage") String staticPage) { return "Static/" +
	 * staticPage; }
	 */

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
