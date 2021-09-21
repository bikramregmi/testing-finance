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

import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.IChequeRequestApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.ChequeRequestDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class ChequeRequestController {

	@Autowired
	private IChequeRequestApi chequeRequestApi;
	@Autowired
	private IBankBranchApi bankBranchApi;
	@Autowired
	private IUserApi userApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/chequerequest")
	public String chequeRequest(ModelMap modelMap, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				List<ChequeRequestDTO> chequeRequestList = chequeRequestApi.getAllChequeRequestByBankBranch(user.getAssociatedId());
				modelMap.put("chequeRequestList", chequeRequestList);
				return "ChequeRequest/" + "chequerequest";
			}else if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				List<BankBranchDTO> bankBranchList = bankBranchApi.listBankBranchByBank(user.getAssociatedId());
				List<ChequeRequestDTO> chequeRequestList = chequeRequestApi.getAllChequeRequestByBankBranch(bankBranchList);
				modelMap.put("chequeRequestList", chequeRequestList);
				modelMap.put("bankBranchList", bankBranchList);
				modelMap.put("bank", true);
				return "ChequeRequest/" + "chequerequest";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/editchequeRequest")
	public String editBank(ModelMap modelMap, @RequestParam(value = "chequeRequestId", required = true) long chequeRequestId,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				
				chequeRequestApi.editchequeRequest(chequeRequestId);
				
					return "redirect:/chequerequest";

				}
			}
		
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchAccount")
	public String searchAccount(ModelMap modelMap, @RequestParam(value = "accountNumber", required = true) String accountNumber,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				
				List <ChequeRequestDTO> chequeRequestList = chequeRequestApi.getcheckRequestByAccountNumber(accountNumber);
				
				modelMap.put("chequeRequestList", chequeRequestList);
				return "ChequeRequest/" + "chequerequest";

				}
			}
		
		return "redirect:/";
	}
}
