package com.mobilebanking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.api.IMerchantManagerApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.model.MerchantManagerDTO;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class MerchantManagerController {

	private Logger logger = LoggerFactory.getLogger(MerchantManagerController.class);
	
	@Autowired
	private IMerchantServiceApi merchantServiceApi;
	
	@Autowired
	private IMerchantManagerApi merchantManagerApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/merchant/service/manageprovider")
	public String manageService(ModelMap modelMap, HttpServletRequest request,
			@RequestParam(value = "serviceid", required = true) long serviceId) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					MerchantServiceDTO merchantSolution = merchantServiceApi.findMerchantServiceById(serviceId);
//					List<MerchantDto> merchantList = merchantService.findServiceProviderByService(merchantSolution.getId());
//					modelMap.put("merchantList", merchantList);
					modelMap.put("merchantService", merchantSolution);
					modelMap.put("merchantManagerList", merchantManagerApi.getAllMerchantManagerByService(serviceId));
					return "multiservice/managemerchant";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/merchant/service/merchantpriority")
	public ResponseEntity<RestResponseDTO> merchantPriority(
			@RequestParam(value = "serviceId", required = false) long serviceId,
			@RequestParam(value = "merchantId", required = false) long merchantId,
			HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO restResponseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					MerchantManagerDTO merchantManagerDto=merchantManagerApi.merchantPriority(serviceId, merchantId);
					restResponseDTO = getResponseDto(ResponseStatus.SUCCESS, "Successfully selected", merchantManagerDto);
				} catch (Exception e) {
					logger.debug("exception" + e);
					restResponseDTO = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, "Request Operator",
							new Object());
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
	}
	
	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail) {
		RestResponseDTO RestResponseDTO = new RestResponseDTO();

		RestResponseDTO.setResponseStatus(status);
		RestResponseDTO.setMessage(message);
		RestResponseDTO.setDetail(detail);

		return RestResponseDTO;
	}

	
}
