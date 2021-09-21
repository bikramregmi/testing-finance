package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.IChequeBlockRequestApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.ChequeBlockRequestDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;

@Controller
public class ChequeBlockRequestController {

	@Autowired
	private IChequeBlockRequestApi chequeBlockRequestApi;
	
	@Autowired
	private IBankBranchApi bankBranchApi;
	@Autowired
	private IUserApi userApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/chequeblockrequest")
	public String chequeBlockRequest(ModelMap modelMap, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				List<ChequeBlockRequestDTO> chequeRequestList = chequeBlockRequestApi.getChequeListByBankBranch(user.getAssociatedId());
				modelMap.put("chequeBlockRequestList"
						+ "", chequeRequestList);
				return "ChequeRequest/" + "chequeblockrequest";
			}else if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				List<BankBranchDTO> bankBranchList = bankBranchApi.listBankBranchByBank(user.getAssociatedId());
				List<ChequeBlockRequestDTO> chequeRequestList = chequeBlockRequestApi.getChequeListByBankBranch(bankBranchList);
				modelMap.put("chequeBlockRequestList"
						+ "", chequeRequestList);
				modelMap.put("bankBranchList", bankBranchList);
				modelMap.put("bank", true);
				return "ChequeRequest/" + "chequeblockrequest";
			}

				
			
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/editchequeBlockRequest")
	public String editBank(ModelMap modelMap, @RequestParam(value = "chequeBlockRequestId", required = true) long chequeBlockRequestId,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				
				chequeBlockRequestApi.editchequeBlockRequest(chequeBlockRequestId);
				
					return "redirect:/chequeblockrequest";

				}
			}
		
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getCheckBlockRequest")
	public String getCheckBlockRequest(ModelMap modelMap, @RequestParam(value = "chequeBlockRequestId", required = true) long chequeBlockRequestId,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				
				chequeBlockRequestApi.editchequeBlockRequest(chequeBlockRequestId);
				
					return "redirect:/chequeblockrequest";

				}
			}
		
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getBlockChequeRequest")
	public ResponseEntity<RestResponseDTO> getBlockCheque(ModelMap modelMap, @RequestParam(value = "branchId", required = true) long branchId,
			HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				List<ChequeBlockRequestDTO> chequeRequestList = chequeBlockRequestApi.getChequeListByBankBranch(branchId);
				restResponseDto = getResponseDto(ResponseStatus.SUCCESS,StringConstants.DATA_READ, chequeRequestList);
//					return "redirect:/chequeblockrequest";

				}
			}
		
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchAccountOfBlockedCheque")
	public String searchAccount(ModelMap modelMap, @RequestParam(value = "accountNumber", required = true) String accountNumber,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				
				List <ChequeBlockRequestDTO> chequeBlockRequestList = chequeBlockRequestApi.getcheckBlockRequestByAccountNumber(accountNumber);
				
				modelMap.put("chequeBlockRequestList", chequeBlockRequestList);
				return "ChequeRequest/" + "chequeblockrequest";

				}
			}
		
		return "redirect:/";
	}
	
	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail){
		RestResponseDTO restResponseDto = new RestResponseDTO();
		
		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);
		
		return restResponseDto;
	}
}
