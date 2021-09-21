package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.INotificationGroupApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.NotificationGroupDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.error.NotificationGroupError;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.NotificationGroupValidation;


@Controller
public class NotificationGroupController {

	@Autowired
	private INotificationGroupApi notificationGroupApi;

	@Autowired
	private NotificationGroupValidation notificationGroupValidation;

	@Autowired
	private IBankApi bankApi;
	
	@Autowired
	private IBankBranchApi bankBranchApi;

	@RequestMapping(method = RequestMethod.GET, value = "/notificationGroup/list")
	public String listNotificationGroup(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR))
					&& authority.contains(Authorities.AUTHENTICATED)) {
				modelMap.put("notificationGroupList", notificationGroupApi.getAll());
					modelMap.put("userType", UserType.Admin);
				return "Notification/notificationGroup";
			}
			
			if ((authority.contains(Authorities.BANK))
					&& authority.contains(Authorities.AUTHENTICATED)|| authority.contains(Authorities.BANK_ADMIN)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)){
				modelMap.put("notificationGroupList", notificationGroupApi.getByBank(AuthenticationUtil.getCurrentUser().getAssociatedId()));
				return "Notification/notificationGroup";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/notificationGroup/add")
	public String addNotificationGroup(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK))
					&& authority.contains(Authorities.AUTHENTICATED)|| authority.contains(Authorities.BANK_ADMIN)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Admin)){
					List<BankDTO> bankDtoList = bankApi.getAllBank();
					modelMap.put("bankList", bankDtoList);
					modelMap.put("userType", UserType.Admin);
				}
				
				
				return "Notification/addNotificationGroup";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/notificationGroup/add")
	public String addNotificationGroup(NotificationGroupDTO notificationGroup, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK))
					&& authority.contains(Authorities.AUTHENTICATED)|| authority.contains(Authorities.BANK_ADMIN)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)){
					notificationGroup.setBankId(AuthenticationUtil.getCurrentUser().getAssociatedId());
				}else if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)){
					BankDTO bankDto = bankBranchApi.findBankOfBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
					notificationGroup.setBankId(bankDto.getId());
				}
				NotificationGroupError error = notificationGroupValidation.validateNotificationGroup(notificationGroup);
				if (error.isValid()) {
					notificationGroupApi.addNotificationGroup(notificationGroup);
					redirectAttributes.addFlashAttribute("message", "Notification Group Added Succesfully");
					return "redirect:/notificationGroup/list";
				}
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Admin)){
				List<BankDTO> bankDtoList = bankApi.getAllBank();
				modelMap.put("bankList", bankDtoList);
				}
				modelMap.put("error", error);
				modelMap.put("notificationGroup", notificationGroup);
				return "Notification/addNotificationGroup";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/notificationGroup/customer")
	public String manageNotificationGroupCustomer(@RequestParam("notificationGroup") String notificationGroupName,HttpServletRequest request,
			ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String bankCode;
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK))
					&& authority.contains(Authorities.AUTHENTICATED)|| authority.contains(Authorities.BANK_ADMIN)
					&& authority.contains(Authorities.AUTHENTICATED)) {
				bankCode =request.getParameter("bankCode");
				if(!validateBank(bankCode)){
					return "redirect:/";
				}
			
				modelMap.put("notificationGroup", notificationGroupApi.getByName(notificationGroupName,bankCode));
				modelMap.put("subscribedCustomerList",
						notificationGroupApi.getSubscribedCustomerList(notificationGroupName,bankCode));
				modelMap.put("customerList", notificationGroupApi.getNotSubscribedCustomerList(notificationGroupName,bankCode));
				return "Notification/manageCustomer";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/notificationGroup/customer/add")
	public String addCustomerToNotificationGroup(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK))
					&& authority.contains(Authorities.AUTHENTICATED)) {
				String bankCode = request.getParameter("bankCode");
				if(!validateBank(bankCode)){
					return "redirect:/";
				}
			
				String notificationGroupName = request.getParameter("notificationGroup");
				String[] customerUniqueIdList = request.getParameterValues("customer");
				if (customerUniqueIdList != null && customerUniqueIdList.length != 0) {
					notificationGroupApi.addCustomerToNotificationGroup(notificationGroupName, customerUniqueIdList,bankCode);
					redirectAttributes.addFlashAttribute("message", "Customer subscribed to Notification Group");
					return "redirect:/notificationGroup/customer?notificationGroup=" + notificationGroupName+"&bankCode="+bankCode;
				} else {
					redirectAttributes.addFlashAttribute("error", "Please select a customer");
					return "redirect:/notificationGroup/customer?notificationGroup=" + notificationGroupName+"&bankCode="+bankCode;
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/notificationGroup/customer/remove")
	public String removeCustomerFromNotificationGroup(@RequestParam("group") String notificationGroupName,
			@RequestParam("customer") String customerUniqueId, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK))
					&& authority.contains(Authorities.AUTHENTICATED)) {
				String bankCode = request.getParameter("bankCode");
			
				if(!validateBank(bankCode)){
					return "redirect:/";
				}
			
				notificationGroupApi.removeCustomerFromNotificationGroup(notificationGroupName, customerUniqueId,bankCode);
				redirectAttributes.addFlashAttribute("message", "Customer removed from Notification Group");
				return "redirect:/notificationGroup/customer?notificationGroup=" + notificationGroupName+"&bankCode="+bankCode;
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/getgroup", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getGroup(@RequestParam("bankCode")String bankCode) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED))) {
				List<NotificationGroupDTO> notificationGroupList = notificationGroupApi.getByBranchCode(bankCode);
				response = getResponseDto(ResponseStatus.SUCCESS,"Success" , notificationGroupList);
				return new ResponseEntity<RestResponseDTO>(response, HttpStatus.OK);
			}
		}
		response = getResponseDto(ResponseStatus.UNAUTHORIZED_USER, "Un-Authorized User", "");
		return new ResponseEntity<RestResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}

	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail) {
		RestResponseDTO restResponseDto = new RestResponseDTO();

		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);

		return restResponseDto;
	}
	
	private Boolean validateBank(String bankCode){
		
		BankDTO bank = bankApi.getBankByCode(bankCode);
		if(bank==null){
			return false;
		}else 
			return true;
		
	}
}
