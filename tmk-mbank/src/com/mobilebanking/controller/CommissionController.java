package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.ICommissionApi;
import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CommissionAmountDTO;
import com.mobilebanking.model.CommissionAmountDtoList;
import com.mobilebanking.model.CommissionDTO;
import com.mobilebanking.model.CommissionType;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.TransactionType;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;

@Controller
public class CommissionController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CommissionController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private IMerchantApi merchantApi;
	
	@Autowired
	private IBankApi bankApi;
	
	@Autowired
	private ICommissionApi commissionApi;

	@RequestMapping(value="/commission/add", method=RequestMethod.GET)
	public String addCommisison(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<MerchantDTO> merchantList = merchantApi.getMerchantByStatus(Status.Active);
				//need to modify this we will get only active banks
				List<BankDTO> bankList = bankApi.getAllBank();
				TransactionType[] transactionType = TransactionType.values();
				CommissionType[] commissionType = CommissionType.values();
				modelMap.put("commissionType", commissionType);
				modelMap.put("bankList", bankList);
				modelMap.put("merchantList", merchantList);
				modelMap.put("transactionType", transactionType);
				return "Commission/addCommission";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/commission/add", method=RequestMethod.POST)
	public String addCommission(@Valid @ModelAttribute CommissionAmountDtoList commissionAmountList, 
			BindingResult bindingResult1,@Valid @ModelAttribute CommissionDTO commissionDto,BindingResult bindingResult2,ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) throws JSONException, ClientException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED)) {
				List<CommissionAmountDTO> commissionAmounts = commissionAmountList.getCommissionAmounts();
				try {
					commissionApi.saveCommission(commissionDto, commissionAmounts);
				} catch (Exception e) {
					List<MerchantDTO> merchantList = merchantApi.getMerchantByStatus(Status.Active);
					//need to modify this we will get only active banks
					List<BankDTO> bankList = bankApi.getAllBank();
					TransactionType[] transactionType = TransactionType.values();
					CommissionType[] commissionType = CommissionType.values();
					modelMap.put("commissionType", commissionType);
					modelMap.put("bankList", bankList);
					modelMap.put("merchantList", merchantList);
					modelMap.put("transactionType", transactionType);
					return "Commission/addCommission";
				}
				
				return "redirect:/commission/list";
			}
			return "redirect:/";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/commission/list", method=RequestMethod.GET)
	public String listCommission(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<CommissionDTO> commissionList = commissionApi.getAllCommission();
				System.out.println(commissionList + " THIS IS LIES");
				modelMap.put("commissionList", commissionList);
				return "Commission/listCommission";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/commission/details", method=RequestMethod.GET)
	public String viewDetails(ModelMap modelMap, @RequestParam("commission") long commissionId, 
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				CommissionDTO commission = commissionApi.getCommissionById(commissionId);
				List<CommissionAmountDTO> commissionAmounts = commissionApi.getCommissionAmountByCommission(commissionId);
				modelMap.put("commissionAmounts", commissionAmounts);
				modelMap.put("commission", commission);
				return "Commission/details";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/commission/edit", method=RequestMethod.GET)
	public String editCommission(ModelMap modelMap, @RequestParam("commission") long commissionId, 
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				CommissionDTO commission = commissionApi.getCommissionById(commissionId);
				List<CommissionAmountDTO> commissionAmounts = commissionApi.getCommissionAmountByCommission(commissionId);
				modelMap.put("commissionAmounts", commissionAmounts);
				modelMap.put("commission", commission);
				return "Commission/editCommission";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/commission/edit", method=RequestMethod.POST)
	public String editCommission(@Valid @ModelAttribute CommissionAmountDtoList commissionAmountList, 
			BindingResult bindingResult1,@Valid @ModelAttribute CommissionDTO commissionDto,BindingResult bindingResult2,ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) throws JSONException, ClientException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED)) {
				List<CommissionAmountDTO> commissionAmounts = commissionAmountList.getCommissionAmounts();
				try {
					commissionApi.editCommission(commissionDto, commissionAmounts);
				} catch (Exception e) {
					CommissionDTO commission = commissionApi.getCommissionById(commissionDto.getId());
					commissionAmounts = commissionApi.getCommissionAmountByCommission(commissionDto.getId());
					modelMap.put("commissionAmounts", commissionAmounts);
					modelMap.put("commission", commission);
					return "Commission/editCommission";
				}
				
				return "redirect:/commission/list";
			}
			return "redirect:/";
		}
		
		return "redirect:/";
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
}
