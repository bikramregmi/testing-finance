package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.ICardlessBankApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.model.AccountDTO;
import com.mobilebanking.model.CardlessBankAccountDTO;
import com.mobilebanking.model.CardlessBankDTO;
import com.mobilebanking.model.error.CardlessBankError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.CardlessBankValidation;

@Controller
public class CardlessBankController {

	@Autowired
	private ICardlessBankApi cardlessBankApi;

	@Autowired
	private CardlessBankValidation cardlessBankValidation;

	@Autowired
	private IStateApi stateApi;

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private IBankApi bankApi;

	@RequestMapping(method = RequestMethod.GET, value = "/cardlessbank")
	public String getCardlessBank(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				modelMap.put("cardlessBankList", cardlessBankApi.getAllCardlessBank());
				return "CardlessBank/cardlessBank";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cardlessbank/add")
	public String addCardlessBank(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				modelMap.put("statesList", stateApi.getAllState());
				return "CardlessBank/addCardlessBank";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/cardlessbank/save")
	public String saveCardlessBank(CardlessBankDTO cardlessBankDTO, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				CardlessBankError error = cardlessBankValidation.validateCardlessBank(cardlessBankDTO);
				if (error.isValid()) {
					cardlessBankApi.saveCardlessBank(cardlessBankDTO);
					redirectAttributes.addFlashAttribute("message", "Cardless Bank Added Successfully");
					return "redirect:/cardlessbank";
				} else {
					modelMap.put("error", error);
					modelMap.put("cardlessBank", cardlessBankDTO);
					modelMap.put("statesList", stateApi.getAllState());
					return "CardlessBank/addCardlessBank";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cardlessbank/edit")
	public String editCardlessBank(@RequestParam("cardlessbank") Long id, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				modelMap.put("cardlessBank", cardlessBankApi.getById(id));
				return "CardlessBank/editCardlessBank";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/cardlessbank/edit/save")
	public String editCardlessBank(CardlessBankDTO cardlessBankDTO, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				CardlessBankError error = cardlessBankValidation.validateCardlessBankEdit(cardlessBankDTO);
				if (error.isValid()) {
					cardlessBankApi.updateCardlessBank(cardlessBankDTO);
					redirectAttributes.addFlashAttribute("message", "Cardless Bank Updated Successfully");
					return "redirect:/cardlessbank";
				} else {
					modelMap.put("error", error);
					modelMap.put("cardlessBank", cardlessBankDTO);
					return "CardlessBank/editCardlessBank";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cardlessbank/getbalance")
	public ResponseEntity<String> getCurrentBalance(@RequestParam("cardlessbank") Long cardlessBankId,
			@RequestParam("bank") Long bankId, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				double currentBalance = 0.0;
				AccountDTO account = accountApi.getAccountByBankAndCardlessBank(bankId, cardlessBankId);
				if (account != null) {
					currentBalance = account.getBalance();
				}
				return new ResponseEntity<String>(String.valueOf(currentBalance), HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("", HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "cardlessbank/manage")
	public String manageCardlessBank(@RequestParam("bank") String bankCode, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				modelMap.put("cardlessBankAccountList", cardlessBankApi.getCardlessBankAccountByBank(bankCode));
				modelMap.put("bank", bankApi.getBankByCode(bankCode));
				return "/CardlessBank/cardlessBankAccountList";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/cardless/account/add", method = RequestMethod.GET)
	public String cardlessbankaccounts(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("bank") String bankCode) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				modelMap.put("bank", bankApi.getBankByCode(bankCode));
				return "/CardlessBank/addCardlessBankAccount";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/cardless/account/add/getcardlessbank", method = RequestMethod.GET)
	public ResponseEntity<List<CardlessBankDTO>> getCardlessBankToAdd(@RequestParam("bank") String bankCode) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				return new ResponseEntity<>(cardlessBankApi.getCardlessBankNotInBank(bankCode),HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "cardless/account/add/save", method = RequestMethod.POST)
	public String addCardlessAccounts(CardlessBankAccountDTO cardlessBankAccount, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				cardlessBankApi.saveCardlessBankAccount(cardlessBankAccount);
				return "redirect:/cardlessbank/manage?bank=" + cardlessBankAccount.getBank();
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "cardless/account/edit", method = RequestMethod.GET)
	public String editCardlessBankAccount(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("cardlessbank") Long id) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				CardlessBankAccountDTO account = cardlessBankApi.getCardlessBankAccountById(id);
				modelMap.put("cardlessBankAccount", account);
				return "/CardlessBank/editCardlessbankAccount";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "cardless/account/edit/save", method = RequestMethod.POST)
	public String editCardlessBankAccount(HttpServletRequest request, ModelMap modelMap,
			CardlessBankAccountDTO cardlessBankAccountsDTO) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				cardlessBankAccountsDTO = cardlessBankApi.editCardlessBankAccount(cardlessBankAccountsDTO);
				return "redirect:/cardlessbank/manage?bank=" + cardlessBankAccountsDTO.getBank();
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/cardless/credit", method = RequestMethod.GET)
	public String editCardlessBankAccount(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("bank") String bankCode,@RequestParam("cardless") Long cardlessId) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				modelMap.put("cardless",  cardlessBankApi.getById(cardlessId));
				modelMap.put("bank", bankApi.getBankByCode(bankCode));
				modelMap.put("bankSelected", true);
				return "LoadFund/cardlessBankBalance";
			}
		}
		return "redirect:/";
	}
}
