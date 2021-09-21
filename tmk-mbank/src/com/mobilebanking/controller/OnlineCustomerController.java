/**
 * 
 */
package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

/**
 * @author bibek
 *
 */
@Controller
public class OnlineCustomerController implements MessageSourceAware {
	
	@Autowired
	ICustomerApi customerApi;
	
	@Autowired
	IStateApi stateApi;
	
	@Autowired
	ICityApi cityApi;
	
	@Autowired
	ICustomerBankAccountApi customerBankAccountApi;
	
	@RequestMapping(value="/online/balance", method=RequestMethod.GET)
	public String balanceInquiry(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.CUSTOMER)) {
				return "Online/Customer/Balance/balanceInquiry";
			}
			return "redirect:/";
		}
		return "redirect:/";	
	}
	
	@RequestMapping(value="/online/profile", method=RequestMethod.GET)
	public String viewProfile(ModelMap modelMap, @RequestParam("user") String uniqueId, 
			HttpServletResponse response, HttpServletRequest request) {
		if (uniqueId == null) {
			return "redirect:/";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.CUSTOMER)) {
				CustomerDTO customer = customerApi.findCustomerByUniqueId(uniqueId);
				if (customer == null) {
					return "redirect:/";
				}
				try {
					CustomerDTO sessionCustomer = customerApi.getCustomerById(
							AuthenticationUtil.getCurrentUser().getAssociatedId());
					if (sessionCustomer == null || (sessionCustomer.getId() != customer.getId())) {
						return "redirect:/";
					}
				} catch (Exception e) {
					return "redirect:/";
				}
				//List<CustomerBankAccount>
				modelMap.put("customer", customer);
				modelMap.put("uuid", customer.getUniqueId());
				return "Online/Customer/Profile/profile";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/online/profile/edit", method=RequestMethod.GET)
	public String editProfile(ModelMap modelMap, @RequestParam("user") String uniqueId, 
			HttpServletRequest request, HttpServletResponse response) {
		if (uniqueId == null) {
			return "redirect:/";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.CUSTOMER)) {
				
				CustomerDTO customer = customerApi.findCustomerByUniqueId(uniqueId);
				if (customer == null) {
					return "redirect:/";
				}
				try {
					CustomerDTO sessionCustomer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					if (sessionCustomer == null || (sessionCustomer.getId() != customer.getId())) {
						return "redirect:/";
					}
					List<StateDTO> statesList = stateApi.getAllState();
					List<CityDTO> cityList = cityApi.findCityByStateName(customer.getState());
					modelMap.put("cityList", cityList);
					modelMap.put("statesList", statesList);
					modelMap.put("customer", customer);
					return "Online/Customer/Profile/edit";
				} catch (Exception e) {
					return "redirect:/";
				}
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/online/profile/edit", method=RequestMethod.POST)
	public String editProfile(ModelMap modelMap, @ModelAttribute("customer") CustomerDTO customer, 
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (customer == null) {
			return "redirect:/";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.CUSTOMER)) {
				try {
					CustomerDTO sessionCustomer = customerApi.getCustomerById(
							AuthenticationUtil.getCurrentUser().getAssociatedId());
					if (sessionCustomer == null) {
						return "redirect:/";
					}
					customer.setUniqueId(sessionCustomer.getUniqueId());
					CustomerDTO editedCustomer = customerApi.editCustomer(customer);
					modelMap.put("customer", customer);
					modelMap.put("message", "Profile Edited Successfully");
					return "redirect:/online/profile?user="+editedCustomer.getUniqueId();
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/";
				}
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub

	}

}
