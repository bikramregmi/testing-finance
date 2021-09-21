package com.mobilebanking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class AgentTransactionController {
	
	
	@RequestMapping(value="/cashOutCustomer",method=RequestMethod.GET)
	public String cashOutCustomer(){
		if(AuthenticationUtil.getCurrentUser()!=null){
		   String authority = AuthenticationUtil.getCurrentUser().getAuthority();
		   System.out.println("Your Authorities : "+authority);
		   if(authority.contains(Authorities.AGENT) && authority.contains(Authorities.AUTHENTICATED)){
			   return "AgentTransaction/cashOutCustomer";
		   }
		}
		return "redirect:Status/500";
	}

	@RequestMapping(value="/cashOutCustomer",method=RequestMethod.POST)
	public String cashOutCustomerPost(@RequestParam(value="walletId",required=true)String walletId){
		
		if(AuthenticationUtil.getCurrentUser()!=null){
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if(authority.contains(Authorities.AGENT) && authority.contains(Authorities.AUTHENTICATED)){
				System.out.println("Wallet ID : "+walletId);
			}
		}
		
		
		return "redirect:Status/500";
	}
	
	
}
