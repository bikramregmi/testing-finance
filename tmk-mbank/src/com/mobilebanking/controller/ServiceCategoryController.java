package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IServiceCategoryApi;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.ServiceCategoryDTO;
import com.mobilebanking.model.error.ServiceCategoryError;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.ServiceCatergoryValidation;


@Controller
public class ServiceCategoryController {

	
private Logger logger = LoggerFactory.getLogger(ServiceCategoryController.class);
	
	
	@Autowired
	private IServiceCategoryApi serviceCategoryApi;
	
	@Autowired
	private ServiceCatergoryValidation serviceCategoryValidation;
	
	@RequestMapping(method = RequestMethod.GET, value = "/servicecategory")
	public String listServiceCategory(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if(authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)){
//				List<ServiceCategoryDTO> servideProvidetList = serviceCategoryApi.getAllServiceCategory();
//				modelMap.put("serviceCategory", servideProvidetList);
			}
			return "/servicecategory/servicecategory";
		}
		return "/";
	}
	
	

	@RequestMapping(method = RequestMethod.POST, value = "/addservicecategory")
	public String addServiceCategory(ModelMap modelMap, @ModelAttribute("serviceCategory") ServiceCategoryDTO serviceCategoryDto, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if(authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)){
				
					try {
						ServiceCategoryError serviceCategoryError = new ServiceCategoryError();
						serviceCategoryError.setValid(true);
						serviceCategoryError = serviceCategoryValidation.serviceCategoryValidation(serviceCategoryDto);
						if(serviceCategoryError.isValid()) {
							serviceCategoryDto = serviceCategoryApi.saveServiceCategory(serviceCategoryDto);
							return "redirect:/listcategory";
						} else {
							logger.debug("error while adding Service Category");
							modelMap.put("serviceCategory",serviceCategoryDto);
							return "servicecategory/servicecategory";
						}

					} catch (Exception e) {
						logger.debug("exception" + e);
//						restResponseDto = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR,StringConstants.DATA_SAVING_EXCEPTION, new Object());
						e.printStackTrace();
						return "redirect:/Static/500";
					}

			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listcategory")
	public String listMerchant(ModelMap modelMap, HttpServletRequest request, Model model,
			HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			List<ServiceCategoryDTO> servideProvidetList = serviceCategoryApi.getAllServiceCategory();
			modelMap.put("serviceCategory", servideProvidetList);
			
			return "servicecategory/listservicecategory";
		}
		
		return "redirect:/";
		
	}
	
	@RequestMapping(value="/editServiceCategory", method=RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> updateUser(HttpServletRequest request, 
			@ModelAttribute("serviceCategoryId") Long serviceCategoryId, 
			@ModelAttribute("name") String name, 
			ModelMap modelMap, RedirectAttributes redirectAttributes) {
		
		RestResponseDTO restResponseDto = new RestResponseDTO();
		
		if (AuthenticationUtil.getCurrentUser()!= null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			
			
			
			if ((authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.AUTHENTICATED+","+Authorities.ADMINISTRATOR))) {
				ServiceCategoryDTO serviceCategory = new ServiceCategoryDTO();
				serviceCategory.setId(serviceCategoryId);
				serviceCategory.setName(name);
				try {
					ServiceCategoryError serviceCategoryError = new ServiceCategoryError();
					serviceCategoryError.setValid(true);
					serviceCategoryError = serviceCategoryValidation.serviceCategoryValidation(serviceCategory);
					if(serviceCategoryError.isValid()) {
						serviceCategory  = serviceCategoryApi.editServiceCategory(serviceCategory);
					
					
					restResponseDto = getResponseDto(ResponseStatus.SUCCESS,StringConstants.DATA_SAVED, serviceCategory);
					}else{
						restResponseDto = getResponseDto(ResponseStatus.FAILURE,StringConstants.DATA_SAVING_ERROR, serviceCategoryError);
					}
					} catch (Exception e) {
					e.printStackTrace();
					restResponseDto = getResponseDto(ResponseStatus.FAILURE,StringConstants.DATA_SAVING_EXCEPTION, null);
				}
			} 
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto,HttpStatus.OK);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.MessageSourceAware#setMessageSource(org.springframework.context.MessageSource)
	 */
	
	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail){
		RestResponseDTO restResponseDto = new RestResponseDTO();
		
		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);
		
		return restResponseDto;
	}

	
}
