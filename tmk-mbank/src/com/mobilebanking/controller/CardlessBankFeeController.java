package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.ICardlessBankApi;
import com.mobilebanking.api.ICardlessBankFeeApi;
import com.mobilebanking.model.CardlessBankFeeDTO;
import com.mobilebanking.model.CardlessBankFeeDTOList;
import com.mobilebanking.model.error.CardlessBankFeeError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.CardlessBankFeeValidation;

@Controller
public class CardlessBankFeeController {

	@Autowired
	private ICardlessBankFeeApi cardlessBankFeeApi;

	@Autowired
	private ICardlessBankApi cardlessBankApi;
	
	@Autowired
	private CardlessBankFeeValidation cardlessBankFeeValidation;

	@RequestMapping(method = RequestMethod.GET, value = "/cardlessbank/fee")
	public String getCardlessBankFee(@RequestParam("cardlessbank") Long id, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				modelMap.put("feeList", cardlessBankFeeApi.getFeeByCardlessBankId(id));
				modelMap.put("cardlessBank", cardlessBankApi.getById(id));
				return "CardlessBank/cardlessBankFee";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/cardlessbank/fee/save")
	public String saveCardlessBankFee(CardlessBankFeeDTOList cardlessBankFeeDTOList, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				CardlessBankFeeError error = cardlessBankFeeValidation.validateCardlessBankFee(cardlessBankFeeDTOList);
				if(error.isValid()){
					cardlessBankFeeApi.updateFee(cardlessBankFeeDTOList);
					redirectAttributes.addFlashAttribute("message", "Fee Updated Successfully");
				}else{
					modelMap.put("error", error);
					if(cardlessBankFeeDTOList.isSameforall()){
						CardlessBankFeeDTO cardlessBankFeeDTO = new CardlessBankFeeDTO();
						cardlessBankFeeDTO.setFee(cardlessBankFeeDTOList.getFee());
						cardlessBankFeeDTO.setSameForAll(true);
						List<CardlessBankFeeDTO> feeList = new ArrayList<>();
						feeList.add(cardlessBankFeeDTO);
						modelMap.put("feeList", feeList);
					}else{
						cardlessBankFeeDTOList.getFeeList().get(0).setSameForAll(false);
						modelMap.put("feeList", cardlessBankFeeDTOList.getFeeList());
					}
					modelMap.put("cardlessBank", cardlessBankApi.getById(cardlessBankFeeDTOList.getCardlessBankId()));
					return "CardlessBank/cardlessBankFee";
				}
				return "redirect:/cardlessbank";
			}
		}
		return "redirect:/";
	}

}
