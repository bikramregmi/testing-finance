/**
 * 
 */
package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.error.BankBranchError;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.BankBranchValidation;

/**
 * @author bibek
 *
 */
@Controller
public class BankBranchController implements MessageSourceAware {
	private Logger logger = LoggerFactory.getLogger(BankBranchController.class);
	
	@Autowired
	private IStateApi stateApi;
	
	@Autowired
	private ICityApi cityApi;
	
	@Autowired
	private IBankBranchApi bankBranchApi;
	
	@Autowired
	private IEmailApi emailApi;
	
	@Autowired
	private BankBranchValidation branchValidation;
	
	@Autowired
	private IUserApi userApi;
	
	
	@RequestMapping(method=RequestMethod.GET, value="/bank/branch/list")
	public String listBankBranch(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				long bankId = AuthenticationUtil.getCurrentUser().getAssociatedId();
				List<BankBranchDTO> bankBranchList = bankBranchApi.listBankBranchByBank(bankId);
				modelMap.put("bankBranchList", bankBranchList);
				return "Branch/listBranch";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/bank/branch/add")
	public String addBankBranch(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				List<StateDTO> statesList = stateApi.getAllState();
				modelMap.put("statesList", statesList);
				System.out.println("HERE TEST BRANCH");
				return "Branch/addBranch";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/bank/branch/add")
	public String addBankBranch(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("branch") BankBranchDTO branchDto, RedirectAttributes redirectAttributes) {
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				BankBranchError error = branchValidation.validateBranch(branchDto);
				if(error.isValid()){
					Long bankId = AuthenticationUtil.getCurrentUser().getAssociatedId();
					branchDto.setBank(String.valueOf(bankId));
					bankBranchApi.saveBranch(branchDto);
					redirectAttributes.addFlashAttribute("message", "Branch Added Successfully");
					return "redirect:/bank/branch/list";
				}else{
					List<StateDTO> statesList = stateApi.getAllState();
					modelMap.put("statesList", statesList);
					modelMap.put("branch", branchDto);
					modelMap.put("error", error);
					return "Branch/addBranch";
				}
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/bank/branch/edit")
	public String editBankBranch(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @RequestParam("branchId") String branchId) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				BankBranchDTO bankBranch = bankBranchApi.findBranchById(Long.parseLong(branchId));
				List<StateDTO> stateList = stateApi.getAllState();
				List<CityDTO> cityList = cityApi.findCityByStateName(bankBranch.getState());
				modelMap.put("branch", bankBranch);
				modelMap.put("stateList", stateList);
				modelMap.put("cityList", cityList);
				return "Branch/editBranch";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/bank/branch/edit")
	public String editBankBranch(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("branch") BankBranchDTO branchDto, RedirectAttributes redirectAttributes) {
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				bankBranchApi.editBranch(branchDto);
				redirectAttributes.addFlashAttribute("message", "Bank Edited Successfully");
				return "redirect:/bank/branch/list";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/getBranchByBank", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getBranch(@RequestParam("bank") Long bankId) {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
				try {
					restResponseDto.setDetail(bankBranchApi.listBankBranchByBank(bankId));
					restResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
					restResponseDto.setMessage(StringConstants.DATA_READ);
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					restResponseDto.setResponseStatus(ResponseStatus.FAILURE);
					restResponseDto.setMessage(StringConstants.DATA_SAVING_ERROR);
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub
		
	}

}
