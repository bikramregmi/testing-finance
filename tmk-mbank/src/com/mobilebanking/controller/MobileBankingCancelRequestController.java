package com.mobilebanking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mobilebanking.api.IMobileBankingCancelRequestApi;
import com.mobilebanking.model.MobileBankingCancelRequestDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class MobileBankingCancelRequestController {

	@Autowired
	private IMobileBankingCancelRequestApi mobileBankingCancelRequestApi;

	@RequestMapping(value = "/cancelrequets")
	public String getCancelRequest(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_ADMIN) || authority.contains(Authorities.BANK_BRANCH)
					|| authority.contains(Authorities.BANK_BRANCH_ADMIN))
					&& authority.contains(Authorities.AUTHENTICATED)) {
				List<MobileBankingCancelRequestDTO> cancelRequestList = mobileBankingCancelRequestApi
						.findRequest(AuthenticationUtil.getCurrentUser().getId());
				modelMap.put("cancelRequestlist", cancelRequestList);
				return "CancelRequest/list";
			}
		}
		return "redirect:/";
	}

}
