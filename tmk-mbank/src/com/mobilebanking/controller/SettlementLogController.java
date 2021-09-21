package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.ISettlementLogApi;
import com.mobilebanking.model.SettlementLogDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class SettlementLogController {

	@Autowired
	private ISettlementLogApi settlementLogApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/pendingSettlement")
	public String listPendingSettlement(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if(authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)){
			
				List <SettlementLogDTO> settlementLog = settlementLogApi.listPendingLog();
				modelMap.put("settlementList", settlementLog);
			
			}
			return "Settlement/settlement";
		}
		return "/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/autoUpdatePendingSettlement")
	public String autoUpdatePendingSettlement(@RequestParam("settlementLogId") long id,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if(authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)){
			
			Boolean settlementResponse = settlementLogApi.autoUpdateSettlementBySettlementLog(id);
			
			if(settlementResponse){
				redirectAttributes.addFlashAttribute("valid", "isValid");

			}else{
				redirectAttributes.addFlashAttribute("valid", "isNotValid");

			}
			return "redirect:/pendingSettlement";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/manualUpdatePendingSettlement")
	public String manualUpdatePendingSettlement(@RequestParam("settlementLogId") long id,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if(authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)){
			
			Boolean settlementResponse = settlementLogApi.manualUpdateSettlementBySettlementLog(id);
			
			if(settlementResponse){
				redirectAttributes.addFlashAttribute("valid", "isValid");

			}else{
				redirectAttributes.addFlashAttribute("valid", "isNotValid");

			}
			return "redirect:/pendingSettlement";
			}
		}
		return "redirect:/";
	}
	
}
