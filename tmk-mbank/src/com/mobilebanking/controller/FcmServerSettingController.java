package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IFcmServerSettingApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.FcmServerSettingDTO;
import com.mobilebanking.model.error.FcmServerSettingError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.FcmServerSettingValidation;

@Controller
public class FcmServerSettingController {

	
	@Autowired
	private IFcmServerSettingApi fcmServerSettingApi;
	
	@Autowired
	private FcmServerSettingValidation serverSettingValidation;
	
	@Autowired
	private IBankApi bankApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/fcmServerSetting/list")
	public String listserverSetting(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR))
					&& authority.contains(Authorities.AUTHENTICATED)) {
				
				List<FcmServerSettingDTO> fcmServerSettingList = fcmServerSettingApi.listFcmServerSetting();
				modelMap.put("fcmServerSettingList", fcmServerSettingList);
				return "Notification/FcmSetting/list";
			}
			
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/fcmServerSetting/add")
	public String addServerSetting(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) )
					&& authority.contains(Authorities.AUTHENTICATED)) {
				
				List<BankDTO> bankDtoList = bankApi.getAllBank();
				modelMap.put("bankList", bankDtoList);
				
				return "Notification/FcmSetting/add";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/fcmServerSetting/bank/add")
	public String manageNotificationGroupCustomer(@RequestParam("key") String serverKey,HttpServletRequest request,
			ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String bankCode;
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR))
					&& authority.contains(Authorities.AUTHENTICATED)) {
			
				modelMap.put("fcmServerSetting", fcmServerSettingApi.getByServerKey(serverKey));
				modelMap.put("addedBank",fcmServerSettingApi.getAddedBank(serverKey));
				modelMap.put("notAddedBank",
						fcmServerSettingApi.getNotAddedBank(serverKey));
				return "Notification/FcmSetting/manageBank";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/fcmServerSetting/bank/add")
	public String addBankToServerSetting(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR))
					&& authority.contains(Authorities.AUTHENTICATED)) {

				String serverKey = request.getParameter("serverKey");
				String[] bankSwiftCodeList = request.getParameterValues("bank");
				if (bankSwiftCodeList != null && bankSwiftCodeList.length != 0) {
					fcmServerSettingApi.addBankToFcmServer(serverKey, bankSwiftCodeList);
					redirectAttributes.addFlashAttribute("message", "Bank added to Fcm server");
					return "redirect:/fcmServerSetting/bank/add?key=" + serverKey;
				} else {
					redirectAttributes.addFlashAttribute("error", "Please select a Bank");
					return "redirect:/fcmServerSetting/bank/add?key=" + serverKey;
				}
			}
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/fcmServerSetting/bank/remove")
	public String removeCustomerFromNotificationGroup(@RequestParam("serverKey") String serverKey,
			@RequestParam("bankCode") String bankCode, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR))
					&& authority.contains(Authorities.AUTHENTICATED)) {
			
				fcmServerSettingApi.removeBankFromFcmServer(bankCode, serverKey);
				
				redirectAttributes.addFlashAttribute("message", "Bank removed from FCM Server");
				return "redirect:/fcmServerSetting/bank/add?key=" + serverKey;
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/fcmServerSetting/add")
	public String addserverSetting(FcmServerSettingDTO serverSettingDto, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR)
					&& authority.contains(Authorities.AUTHENTICATED))) {
				FcmServerSettingError error = serverSettingValidation.validateServerSetting(serverSettingDto);
				if (error.isValid()) {
					fcmServerSettingApi.saveServerSetting(serverSettingDto);
					redirectAttributes.addFlashAttribute("message", "Server Setting Added Succesfully");
					return "redirect:/fcmServerSetting/list";
				}
				modelMap.put("error", error);
				modelMap.put("serverSettingDto", serverSettingDto);
				return "Notification/FcmSetting/add";
			}
		}
		return "redirect:/";
	}

	
}
