/**
 * 
 */
package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IAccountApi;
import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.ICardlessBankApi;
import com.mobilebanking.api.ILedgerApi;
import com.mobilebanking.model.AccountDTO;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.LedgerDTO;
import com.mobilebanking.model.LedgerType;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

/**
 * @author bibek
 *
 */
@Controller
public class LedgerController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(BankBranchController.class);

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private ICardlessBankApi cardlessBankApi;

	@Autowired
	private ILedgerApi ledgerApi;

	@RequestMapping(value = "/ledger/loadbalance", method = RequestMethod.GET)
	public String loadBalance(HttpServletRequest request, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				List<BankDTO> bankList = bankApi.geteBankByStatus(Status.Active);
				modelMap.put("bankList", bankList);
				return "LoadFund/loadfundform";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/ledger/loadbalance", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> loadbalance(ModelMap modelMap, HttpServletRequest request,
			@RequestParam("bank") String bank, @RequestParam("balance") double balance,
			@RequestParam("remarks") String remarks, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponse = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR)) {
				try {
					AccountDTO accountDto = new AccountDTO();
					accountDto.setBank(bank);
					accountDto.setBalance(balance);
					accountDto.setCredit(true);

					// accountApi.updateAccount(accountDto);
					accountApi.loadFund(accountDto, remarks,AuthenticationUtil.getCurrentUser().getId());
					restResponse.setMessage("Succcess");
					restResponse.setStatus("success");
				} catch (Exception e) {
					e.printStackTrace();
					restResponse.setStatus("failure");
				}
			}
			return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.OK);
		}
		return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/ledger/creditlimits", method = RequestMethod.GET)
	public String creditLimits(HttpServletRequest request, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				List<BankDTO> banks = bankApi.geteBankByStatus(Status.Active);
				modelMap.put("banks", banks);
				return "LoadFund/creditlimitform";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/ledger/creditlimits", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> creditLimits(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("amount") double amount, @RequestParam("bank") String bank,
			@RequestParam("remarks") String remarks) {
		RestResponseDTO restResponse = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				try {
					accountApi.updateCreditLimit(bank, amount, remarks,AuthenticationUtil.getCurrentUser().getId());
					restResponse.setMessage("Succcess");
					restResponse.setStatus("success");
				} catch (Exception e) {
					e.printStackTrace();
					restResponse.setStatus("failure");
				}
				return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.OK);
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/ledger/cardlessbankcreditlimits", method = RequestMethod.GET)
	public String cardlessBankCreditLimits(HttpServletRequest request, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				modelMap.put("banklist", bankApi.geteBankByStatus(Status.Active));
				modelMap.put("cardlessBankList", cardlessBankApi.getAllCardlessBank());
				modelMap.put("bankSelected", false);
				return "LoadFund/cardlessBankBalance";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/ledger/cardlessbankcreditlimits", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> cardlessBankCreditLimits(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("amount") Double amount, @RequestParam("bank") Long bank,
			@RequestParam("cardlessBank") Long cardlessBank) {
		RestResponseDTO restResponse = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				try {
					accountApi.updateCardlessBankBalance(bank, cardlessBank, amount);
					restResponse.setMessage("Balance Updated Successfully.");
					restResponse.setStatus("success");
				} catch (Exception e) {
					e.printStackTrace();
					restResponse.setStatus("failure");
				}
				return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.OK);
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/ledger/loadfundreport", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getLoadFUndReport(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		RestResponseDTO restResponse = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				try {
					HashMap<String, Object> loadFundReport = ledgerApi.getLoadFundReport(fromDate, toDate);
					restResponse.setMessage("success");
					restResponse.setDetail(loadFundReport);
					return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.OK);
				} catch (Exception e) {
					System.out.println("error :" + e.getMessage());
					e.printStackTrace();
					restResponse.setStatus("Internal Server Error");
					return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		restResponse.setStatus("Unauthorized User");
		return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.UNAUTHORIZED);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub

	}

	@RequestMapping(value = "/ledger/ledger_history", method = RequestMethod.GET)
	public String getLedgerHistory(RedirectAttributes redirectAttributes, ModelMap modelMap, Model model,
			@RequestParam(value = "pageNo", required = false) Integer page,
			@RequestParam(value = "swift-code", required = false) String bankCode,
			@RequestParam(value = "to-date", required = false) String toDate,
			@RequestParam(value = "from-date", required = false) String fromDate,
			@RequestParam(value = "ledger-type", required = false) String ledgerType) {

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				PagablePage pg = ledgerApi.getLedgerHistory(page, fromDate, toDate, bankCode, ledgerType);
				if (authority.contains(Authorities.ADMINISTRATOR)) {
					modelMap.put("bankList", bankApi.getAllBank());
					modelMap.put("isAdmin", true);
				}
				List<LedgerType> ledgerTypeList = new ArrayList<LedgerType>();
				ledgerTypeList.add(LedgerType.LOADFUND);
				ledgerTypeList.add(LedgerType.UPDATE_CREDIT_LIMIT);
				ledgerTypeList.add(LedgerType.DECREASE_BALANCE);
				modelMap.put("ledgerTypeList", ledgerTypeList);
				modelMap.put("ledgerList", pg);
				modelMap.put("fromDate", (fromDate == null) ? "" : fromDate);
				modelMap.put("toDate", (toDate == null) ? "" : toDate);
				modelMap.put("bankCode", bankCode);
				modelMap.put("ledgerType", ledgerType);

			}
			return "reports/ledgerHistory";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/ledger/view_bank_ledger", method = RequestMethod.GET)
	public String getBankWiseLedger(RedirectAttributes redirectAttributes, ModelMap modelMap, Model model,
			@RequestParam(value = "bank-code", required = false) String bankCode,
			@RequestParam(value = "from-date", required = false) String fromDate,
			@RequestParam(value = "to-date", required = false) String toDate,
			@RequestParam(value = "dr-cr", required = false) String debitCredit) {

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (bankCode == null || bankCode.trim().equals("")) {
					modelMap.put("bankList", bankApi.getAllBank());
				} else {
					BankDTO bank = bankApi.getBankDtoById(Long.parseLong(bankCode));
					List<LedgerDTO> ledgerList = ledgerApi.getBankLedger(bankCode, fromDate, toDate, debitCredit);
					modelMap.put("bankLedgerList", ledgerList);
					modelMap.put("bankList", bankApi.getAllBank());
					modelMap.put("fromDate", (fromDate == null) ? "" : fromDate);
					modelMap.put("toDate", (toDate == null) ? "" : toDate);
					modelMap.put("bankCode", bankCode);
					modelMap.put("drCr", debitCredit);
					modelMap.put("accountNumber", bank.getAccountNumber());
				}
				return "/reports/viewBankLedger";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/ledger/decrease_balance", method = RequestMethod.GET)
	public String decreaseBalance(HttpServletRequest request, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				List<BankDTO> bankList = bankApi.geteBankByStatus(Status.Active);
				modelMap.put("bankList", bankList);
				return "LoadFund/decreaseBalanceForm";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/ledger/decrease_balance", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> decreaseBalance(ModelMap modelMap, HttpServletRequest request,
			@RequestParam("bank") String bank, @RequestParam("balance") double balance,
			@RequestParam("remarks") String remarks, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponse = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR)) {
				try {
					AccountDTO accountDto = new AccountDTO();
					accountDto.setBank(bank);
					accountDto.setBalance(balance);
					accountDto.setCredit(false);

					// accountApi.updateAccount(accountDto);
					if (accountApi.decreaseFund(accountDto, remarks,AuthenticationUtil.getCurrentUser().getId())) {
						restResponse.setMessage("Succcess");
						restResponse.setStatus("success");
					} else {
						restResponse.setMessage("Balance Exceeds Available Balance");
						restResponse.setStatus("failure");
					}
				} catch (Exception e) {
					e.printStackTrace();
					restResponse.setStatus("failure");
				}
			}
			return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.OK);
		}
		return new ResponseEntity<RestResponseDTO>(restResponse, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "ledger/download_ledger", method = RequestMethod.GET)
	public String downloadLedgerView(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authorities = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authorities.contains(Authorities.ADMINISTRATOR) && authorities.contains(Authorities.AUTHENTICATED)) {

				List<LedgerType> ledgerTypeList = new ArrayList<LedgerType>();
				ledgerTypeList.add(LedgerType.LOADFUND);
				ledgerTypeList.add(LedgerType.UPDATE_CREDIT_LIMIT);
				ledgerTypeList.add(LedgerType.DECREASE_BALANCE);
				modelMap.put("ledgerTypeList", ledgerTypeList);
				modelMap.put("bankList", bankApi.getAllBank());

			}

			return "reports/downloadLedgerReportView";

		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "ledger/load_ledger", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> loadLedgerData(
			@RequestParam(value = "bank", required = false) String bankCode,
			@RequestParam(value = "from_date", required = false) String fromDate,
			@RequestParam(value = "to_date", required = false) String toDate,
			@RequestParam(value = "ledger_type", required = false) String ledgerType,
			@RequestParam(value = "page_number", required = false) Integer pageNumber) {
		ResponseEntity<RestResponseDTO> response = null;
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authorities = AuthenticationUtil.getCurrentUser().getAuthority();
			System.out.println("hi");
			if (authorities.contains(Authorities.ADMINISTRATOR) && authorities.contains(Authorities.AUTHENTICATED)) {
				RestResponseDTO restResponseDTO = new RestResponseDTO();
				try {
					Map<String, PagablePage> map = new HashMap<String, PagablePage>();
					System.out.println("hi");
					map.put("pageable",
							ledgerApi.getLedgerHistory1(pageNumber, fromDate, toDate, bankCode, ledgerType));

					restResponseDTO.setDetail(map);
					restResponseDTO.setMessage("Success");
					response = new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.OK);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("exception occur in ledger download" + e.getMessage());
					restResponseDTO.setMessage("Internal server error");
					response = new ResponseEntity<RestResponseDTO>(restResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

		}
		return response;

	}
}
