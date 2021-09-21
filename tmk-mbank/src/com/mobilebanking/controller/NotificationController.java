package com.mobilebanking.controller;

import java.util.List;

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
import com.mobilebanking.api.INotificationApi;
import com.mobilebanking.api.INotificationGroupApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.NotificationChannel;
import com.mobilebanking.model.NotificationDTO;
import com.mobilebanking.model.NotificationGroupDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.UserNotificationDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class NotificationController {
	@Autowired
	private INotificationApi notificationApi;

	@Autowired
	private INotificationGroupApi notificationGroupApi;
	
	@Autowired
	private IBankApi bankApi;
	
	@Autowired
	private IBankBranchApi bankBranchApi;

	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public String notification(@RequestParam(value = "pageNo", required = false) Integer currentPage,
			ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED) || authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				PagablePage notificationList = null;
				List<NotificationGroupDTO> notificationGroupDtoList = null;
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Admin)){
					notificationList = notificationApi.getNotification(currentPage);
					List<BankDTO> bankDtoList = bankApi.getAllBank();
					modelMap.put("bankList", bankDtoList);
					modelMap.put("userType", UserType.Admin);
					notificationGroupDtoList = notificationGroupApi.getAll();
					
				}
				BankDTO bankDto = null;
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)){
					 bankDto = bankApi.getBankDtoById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					notificationList = notificationApi.getBankNotification(currentPage,bankDto.getSwiftCode());
					modelMap.put("bankCode", bankDto.getSwiftCode());
				}else if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)){
					 bankDto = bankBranchApi.findBankOfBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
					notificationList = notificationApi.getBankNotification(currentPage,bankDto.getSwiftCode());
					modelMap.put("bankCode", bankDto.getSwiftCode());
				}
				if(!AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Admin)){
					notificationGroupDtoList = notificationGroupApi.getByBranchCode(bankDto.getSwiftCode()); 
				}
				modelMap.put("notificationList", notificationList);
				modelMap.put("notificationGroupList", notificationGroupDtoList);
				return "/Notification/notification";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/notification/send", method = RequestMethod.POST)
	public String sendNotification(NotificationDTO notificationDTO, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)){
					 BankDTO bankDto = bankApi.getBankDtoById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					 notificationDTO.setBankCode(bankDto.getSwiftCode());
				}else if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)){
					BankDTO bankDto = bankBranchApi.findBankOfBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
					notificationDTO.setBankCode(bankDto.getSwiftCode());
				}
				notificationApi.saveNotification(notificationDTO);
				redirectAttributes.addFlashAttribute("message", "Notification Sent Successfully");
				return "redirect:/notification";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/getNotification", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> setwebtoken(String token) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED))
					|| (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED))
					|| (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED))|| (authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED))
					|| (authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED))) {
				List<UserNotificationDTO> notificationList = notificationApi
						.getUserNotification(AuthenticationUtil.getCurrentUser().getId(), NotificationChannel.WEB);
				Long unseen = notificationApi.getUnseenNotification(AuthenticationUtil.getCurrentUser().getId());
				response = getResponseDto(ResponseStatus.SUCCESS, unseen.toString(), notificationList);
				return new ResponseEntity<RestResponseDTO>(response, HttpStatus.OK);
			}
		}
		response = getResponseDto(ResponseStatus.UNAUTHORIZED_USER, "Un-Authorized User", "");
		return new ResponseEntity<RestResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/seennotification", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> seennotification(@RequestParam("lastNotification") Long lastNotificationid) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED))
					|| (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED))
					|| (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED))|| (authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED))
					|| (authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED))) {
				notificationApi.changeNotificationStatus(lastNotificationid,
						AuthenticationUtil.getCurrentUser().getId());
				response = getResponseDto(ResponseStatus.SUCCESS, "Success", "");
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

}
