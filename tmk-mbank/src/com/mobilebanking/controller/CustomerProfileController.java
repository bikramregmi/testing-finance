package com.mobilebanking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.ICustomerProfileApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CustomerProfileDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
@RequestMapping(value = "/customerProfile")
public class CustomerProfileController {
	
	@Autowired
	private ICustomerProfileApi customerProfileApi;
	
	@Autowired
	private IBankBranchApi bankBranchApi;
	
	@Autowired
	private IBankApi bankApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/add")
	public String getCustomerProfile(){
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				
				return "Customer/addCustomerProfile";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public String addCustomerProfile(CustomerProfileDTO customerProfileDto, RedirectAttributes redirectAttributes){
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)){
					customerProfileDto.setBankId(AuthenticationUtil.getCurrentUser().getAssociatedId());
				}else if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)){
					BankDTO bankDto = bankBranchApi.findBankOfBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
					customerProfileDto.setBankId(bankDto.getId());
				}
				
				customerProfileApi.saveProfile(customerProfileDto);
				redirectAttributes.addFlashAttribute("message", "Customer Profile added");
				return "redirect:/customerProfile/list";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String listCustomerProfile(ModelMap modelMap){
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			long bankId = 0;
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED) || authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)){
					bankId = AuthenticationUtil.getCurrentUser().getAssociatedId();
				}else if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)){
					BankDTO bankDto = bankBranchApi.findBankOfBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
					bankId = bankDto.getId();
				}
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Admin)){
					modelMap.put("isAdmin", true);
				}
				List<CustomerProfileDTO> customerProfileList = customerProfileApi.listCustomerProfile(bankId);
				
				modelMap.put("customerProfileList", customerProfileList);
				return "Customer/listCustomerProfile";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/edit")
	public String editCustomerProfile(@RequestParam("profileId")long profileId, ModelMap modelMap){
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				CustomerProfileDTO customerProfile= customerProfileApi.getProfileById(profileId);
				modelMap.put("profile", customerProfile);
				if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Admin)){
				modelMap.put("userType", UserType.Admin);
				modelMap.put("bankList", bankApi.getAllBank());
				}
				return "Customer/editCustomerProfile";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/changeStatus")
	public String changeStatus(@RequestParam("profileId")long profileId, ModelMap modelMap,RedirectAttributes redirectAttributes){
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				boolean toggled = customerProfileApi.changeStatus( profileId);
				if(toggled)
				redirectAttributes.addFlashAttribute("message", "Customer Profile Status changed");
				else
					redirectAttributes.addFlashAttribute("message", "Unable to change status, please try again.");
				return "redirect:/customerProfile/list";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/edit")
	public String editCustomerProfile(CustomerProfileDTO customerProfileDto, RedirectAttributes redirectAttributes){
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED) || authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				/*if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)){
					customerProfileDto.setBankId(AuthenticationUtil.getCurrentUser().getAssociatedId());
				}else if(AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)){
					BankDTO bankDto = bankBranchApi.findBankOfBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
					customerProfileDto.setBankId(bankDto.getId());
				}*/
				customerProfileApi.editProfile(customerProfileDto);
				redirectAttributes.addFlashAttribute("message", "Customer Profile edited");
				return "redirect:/customerProfile/list";
			
			}
		}
		return "redirect:/";
	}

}
