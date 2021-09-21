package com.mobilebanking.controller;

import java.util.ArrayList;
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

import com.mobilebanking.api.IChannelPartnerApi;
import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.model.ChannelPartnerDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.error.ChannelPartnerError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.ChannelPartnerValidation;

@Controller
public class ChannelPartnerController implements MessageSourceAware {
	
	@Autowired
	private IStateApi stateApi;
	
	@Autowired
	private ICityApi cityApi;
	
	@Autowired
	private IChannelPartnerApi channelPartnerApi;
	
	@Autowired
	private ChannelPartnerValidation channelPartnerValidation;

	@RequestMapping(method=RequestMethod.GET, value="/listchannelpartner")
	public String listChannelPartner(
			ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.ADMINISTRATOR)) {
				List<ChannelPartnerDTO> channelPartnersList = new ArrayList<ChannelPartnerDTO>();
				channelPartnersList = channelPartnerApi.findAllChannelPartner();
				modelMap.put("channelPartners", channelPartnersList);
				return "ChannelPartner/listChannelPartner";
			}
			return "redirect:/";
		}
		return "redirect:/";
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/addchannelpartner")
	public String addChannelPartner(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.ADMINISTRATOR)) {
				List<StateDTO> stateList = new ArrayList<StateDTO>();
				stateList = stateApi.getAllState();
				modelMap.put("statesList", stateList);
				return "ChannelPartner/addChannelPartner";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/addchannelpartner")
	public String addChannelPartner(RedirectAttributes redirectAttributes, ChannelPartnerDTO channelPartnerDto) {
		if (channelPartnerDto == null) {
			return "redirect:/listChannelPartner";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.ADMINISTRATOR)) {
				ChannelPartnerError error = channelPartnerValidation.validateChannelPartner(channelPartnerDto);
				if(error.isValid()){
					channelPartnerApi.saveChannelPartner(channelPartnerDto);
					return "redirect:/listchannelpartner";
				}else{
					redirectAttributes.addFlashAttribute("error",error);
					redirectAttributes.addFlashAttribute("channelPartner",channelPartnerDto);
					return "redirect:/addchannelpartner";
				}
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/editchannelpartner")
	public String editChannelPartner(ModelMap modelMap, @RequestParam("channelpartner") Long id) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.ADMINISTRATOR)) {
				ChannelPartnerDTO channelPartnerDto = channelPartnerApi.findChannelPartnerById(id);
				modelMap.put("channelPartner", channelPartnerDto);
				List<StateDTO> stateList = stateApi.getAllState();
				modelMap.put("stateList", stateList);
				List<CityDTO> cityList = cityApi.findCityByStateName(channelPartnerDto.getState());
				modelMap.put("cityList", cityList);
				return "ChannelPartner/editChannelPartner";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/editchannelpartner")
	public String editChannelPartner(RedirectAttributes redirectAttributes, @ModelAttribute("channelPartner") ChannelPartnerDTO channelPartnerDto) {
		if (channelPartnerDto == null) {
			return "redirect:/listChannelPartner";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED) && authority.contains(Authorities.ADMINISTRATOR)) {
				channelPartnerApi.editChannelPartner(channelPartnerDto);
				redirectAttributes.addFlashAttribute("message", "Channel Partner Edited Successfully");
				return "redirect:/listchannelpartner";
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
