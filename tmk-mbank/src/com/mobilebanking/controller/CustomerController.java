package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IActionLogApi;
import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.ICustomerKycApi;
import com.mobilebanking.api.ICustomerLogApi;
import com.mobilebanking.api.ICustomerProfileApi;
import com.mobilebanking.api.IDocumentIdsApi;
import com.mobilebanking.api.ISmsModeApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.AccountMode;
import com.mobilebanking.model.ActionLogDTO;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerKycDTO;
import com.mobilebanking.model.CustomerLogDto;
import com.mobilebanking.model.CustomerProfileDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.DocumentIdsDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.SmsModeDto;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.error.CustomerError;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.CustomerValidation;

@Controller
public class CustomerController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private ICustomerBankAccountApi customerBankAccountApi;

	@Autowired
	private IStateApi stateApi;

	@Autowired
	private ICityApi cityApi;

	@Autowired
	private CustomerValidation customerValidation;

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private IBankBranchApi bankBranchApi;

	@Autowired
	private IActionLogApi actionLogApi;

	@Autowired
	private ISmsModeApi smsModeApi;

	@Autowired
	private ICustomerKycApi customerKycApi;

	@Autowired
	private IDocumentIdsApi documentIdsApi;

	@Autowired
	private ICustomerProfileApi customerProfileApi;

	@Autowired
	private ICustomerLogApi customerLogApi;

	private MessageSource messageSource;

	/*
	 * public CustomerController(ICustomerApi customerApi, CustomerValidation
	 * customerValidation, ICountryApi countryApi) { this.customerApi =
	 * customerApi; this.customerValidation = customerValidation;
	 * this.countryApi = countryApi; }
	 */

	@ModelAttribute("customer")
	public CustomerDTO getUser() {
		return new CustomerDTO();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/messages")
	public String getRegistrationSuccess(ModelMap modelMap,
			@RequestParam(value = "message", required = true) String message,

			HttpServletRequest request) {
		logger.debug("in messages==>" + messageSource.getMessage(message, null, Locale.ROOT));
		modelMap.put("message", message == null ? "" : messageSource.getMessage(message, null, Locale.ROOT));
		return "Message/" + "messages";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editCustomer")
	public String editCustomer(ModelMap modelMap,
			@RequestParam(value = "customerId", required = true) String customerId, HttpServletRequest request) {
		try {
			CustomerDTO customer = customerApi.getCustomerById(Long.parseLong(customerId));
			modelMap.put("customer", customer);
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
						|| (authority.contains(Authorities.SUPER_AGENT)
								&& authority.contains(Authorities.AUTHENTICATED))) {
					if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
						return "redirect:/";
					}
					if (customer == null) {
						logger.debug("customerDTO null==>" + customer);
						return "redirect:/Static/500";
					} else {
						String[] ids = TimeZone.getAvailableIDs();
						List<String> timeZoneList = new ArrayList<>();
						for (String id : ids) {
							timeZoneList.add(id);
						}
						List<CustomerProfileDTO> customerProfileList = customerProfileApi
								.listCustomerProfile(AuthenticationUtil.getCurrentUser().getAssociatedId());
						modelMap.put("customerProfileList", customerProfileList);
						modelMap.put("timeZoneList", timeZoneList);
						modelMap.put("customer", customer);

						return "Customer/editCustomer";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/customer/add")
	public String addCustomer(ModelMap modelMap) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
						|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
					if (AuthenticationUtil.getCurrentUser().getMaker() == null
							|| !AuthenticationUtil.getCurrentUser().getMaker()) {
						return "redirect:/";
					}
					List<StateDTO> statesList = stateApi.getAllState();
					List<BankBranchDTO> branchList = bankBranchApi
							.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
					BankDTO bankDto = bankApi.getBankDtoById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					List<SmsModeDto> smsModeList = smsModeApi.findSmsModeByBank(bankDto.getName());
					AccountMode[] accountMode = AccountMode.values();
					List<CustomerProfileDTO> customerProfileList = customerProfileApi
							.listCustomerProfile(bankDto.getId());
					modelMap.put("customerProfileList", customerProfileList);
					modelMap.put("smsModeList", smsModeList);
					modelMap.put("accountMode", accountMode);
					modelMap.put("bankName", bankDto.getName());
					modelMap.put("branchList", branchList);
					modelMap.put("statesList", statesList);
					modelMap.put("userType", "bank");
					return "Customer/addCustomer";
				} else if (authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
						|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)) {

					List<StateDTO> statesList = stateApi.getAllState();
					BankBranchDTO bankBranchDto = bankBranchApi
							.findBranchById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					if (AuthenticationUtil.getCurrentUser().getMaker() == null
							|| !AuthenticationUtil.getCurrentUser().getMaker()) {
						return "redirect:/";
					}
					BankDTO bankDto = bankApi.getBankDtoById(bankBranchDto.getBankId());
					List<CustomerProfileDTO> customerProfileList = customerProfileApi
							.listCustomerProfile(bankDto.getId());
					modelMap.put("customerProfileList", customerProfileList);
					List<SmsModeDto> smsModeList = smsModeApi.findSmsModeByBank(bankDto.getName());
					AccountMode[] accountMode = AccountMode.values();
					modelMap.put("smsModeList", smsModeList);
					modelMap.put("accountMode", accountMode);
					modelMap.put("bankBranchName", bankBranchDto.getName() + " - " + bankBranchDto.getBank());
					modelMap.put("statesList", statesList);
					modelMap.put("userType", "branch");
					return "Customer/addCustomer";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/add")
	public String addCustomer(CustomerDTO customerDto, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			User currentUser = AuthenticationUtil.getCurrentUser();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
					|| (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))) {
				try {
					if (currentUser.getUserType().equals(UserType.BankBranch)) {
						customerDto.setBankBranch(String.valueOf(currentUser.getAssociatedId()));
					}
					CustomerError error = customerValidation.customerValidation(customerDto);
					if (error.isValid()) {
						customerDto.setStatus(CustomerStatus.Approved);
						if (currentUser.getUserType().equals(UserType.BankBranch)) {
							if (!currentUser.getChecker()) {
								customerDto.setStatus(CustomerStatus.Hold);
							}
						} else if (currentUser.getUserType().equals(UserType.Bank)) {
							if (!currentUser.getChecker()) {
								customerDto.setStatus(CustomerStatus.Hold);
							}
						}
						if (customerDto.getBankBranch() != null) {
							BankBranchDTO bankBranch = bankBranchApi
									.findBranchById(Long.parseLong(customerDto.getBankBranch()));
							String branchCode = bankBranch.getBranchCode()
									.substring(bankBranch.getBranchCode().length() - 3);
							customerDto.setAccountNumber(branchCode + customerDto.getAccountNumber());
						}
						customerDto.setCreatedBy(AuthenticationUtil.getCurrentUser().getId().toString());
						customerApi.saveCustomer(customerDto);
						redirectAttributes.addFlashAttribute("message", "Customer Registration Sucessfull");

						return "redirect:/customer/list";

					} else {
						redirectAttributes.addFlashAttribute("error", error);
						redirectAttributes.addFlashAttribute("customer", customerDto);
						return "redirect:/customer/add";
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/customer/add";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/customer/list")
	public String listCustomer(ModelMap modelMap, HttpServletRequest request, Model model, HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer page,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "accountNo", required = false) String accountNo,
			@RequestParam(value = "branch", required = false) String branch,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "from_date", required = false) String fromDate,
			@RequestParam(value = "to_date", required = false) String toDate) {
		PagablePage customerList = new PagablePage();
		boolean canApprove = true;
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				User currentUser = AuthenticationUtil.getCurrentUser();
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
						|| (authority.contains(Authorities.BANK_BRANCH)
								&& authority.contains(Authorities.AUTHENTICATED))) {
					if (currentUser.getUserType().equals(UserType.BankBranch)) {
						if (currentUser.getChecker() == null || !currentUser.getChecker()) {
							canApprove = false;
						}
					} else if (authority.contains(Authorities.BANK)) {
						modelMap.put("branchList", bankBranchApi
								.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId()));
						modelMap.put("isBank", true);
						if (currentUser.getChecker() == null || !currentUser.getChecker()) {
							canApprove = false;
						}
					} else if (authority.contains(Authorities.ADMINISTRATOR)) {
						modelMap.put("bankList", bankApi.getAllBank());
						modelMap.put("userAdmin", true);
						canApprove = false;
					}

					modelMap.put("canApprove", canApprove);
					customerList = customerApi.getCustomer(page, name, mobileNo, city, status, accountNo, branch, bank,
							true, fromDate, toDate, false);
					modelMap.put("cityList", cityApi.getAllCity());
					modelMap.put("stateList", stateApi.getAllState());
					modelMap.put("canApprove", canApprove);
					modelMap.put("customerList", customerList);
					modelMap.put("customerStatusList", CustomerStatus.values());
					modelMap.put("name", name);
					modelMap.put("accountNo", accountNo);
					modelMap.put("mobileNo", mobileNo);
					modelMap.put("city", city);
					modelMap.put("status", status);
					modelMap.put("branch", branch);
					modelMap.put("bank", bank);
					modelMap.put("from_date", fromDate);
					modelMap.put("to_date", toDate);
					return "/Customer/listCustomer";
				}
				return "redidrect:/";

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/customer/list/pendingRegistration")
	public String listPendingRegistration(ModelMap modelMap, HttpServletRequest request, Model model,
			HttpServletResponse response, @RequestParam(value = "pageNo", required = false) Integer page,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "accountNo", required = false) String accountNo,
			@RequestParam(value = "branch", required = false) String branch,
			@RequestParam(value = "bank", required = false) String bank) {
		PagablePage customerList = new PagablePage();
		boolean canApprove = true;
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				User currentUser = AuthenticationUtil.getCurrentUser();
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
						|| (authority.contains(Authorities.BANK_BRANCH)
								&& authority.contains(Authorities.AUTHENTICATED))) {
					if (currentUser.getUserType().equals(UserType.BankBranch)) {
						if (currentUser.getChecker() == null || !currentUser.getChecker()) {
							canApprove = false;
						}
					} else if (authority.contains(Authorities.BANK)) {
						modelMap.put("branchList", bankBranchApi
								.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId()));
						modelMap.put("isBank", true);
						if (currentUser.getChecker() == null || !currentUser.getChecker()) {
							canApprove = false;
						}
					} else if (authority.contains(Authorities.ADMINISTRATOR)) {
						modelMap.put("bankList", bankApi.getAllBank());
						modelMap.put("userAdmin", true);
						canApprove = false;
					}

					modelMap.put("canApprove", canApprove);
					customerList = customerApi.getCustomer(page, name, mobileNo, city, "Approved", accountNo, branch,
							bank, false, null, null, false);
					modelMap.put("cityList", cityApi.getAllCity());
					modelMap.put("stateList", stateApi.getAllState());
					modelMap.put("canApprove", canApprove);
					modelMap.put("customerList", customerList);
					modelMap.put("customerStatusList", CustomerStatus.values());
					modelMap.put("name", name);
					modelMap.put("accountNo", accountNo);
					modelMap.put("mobileNo", mobileNo);
					modelMap.put("city", city);
					modelMap.put("status", status);
					modelMap.put("branch", branch);
					modelMap.put("bank", bank);
					return "/Customer/pendingRegistrationList";
				}
				return "redidrect:/";

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/customer/list/expired")
	public String listExpiredCustomer(ModelMap modelMap, HttpServletRequest request, Model model,
			HttpServletResponse response, @RequestParam(value = "pageNo", required = false) Integer page,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "accountNo", required = false) String accountNo,
			@RequestParam(value = "branch", required = false) String branch,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "from_date", required = false) String fromDate,
			@RequestParam(value = "to_date", required = false) String toDate) {
		PagablePage customerList = new PagablePage();
		boolean canApprove = true;
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				User currentUser = AuthenticationUtil.getCurrentUser();
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
						|| (authority.contains(Authorities.BANK_BRANCH)
								&& authority.contains(Authorities.AUTHENTICATED))) {
					if (currentUser.getUserType().equals(UserType.BankBranch)) {
						if (currentUser.getChecker() == null || !currentUser.getChecker()) {
							canApprove = false;
						}
					} else if (authority.contains(Authorities.BANK)) {
						modelMap.put("branchList", bankBranchApi
								.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId()));
						modelMap.put("isBank", true);
						if (currentUser.getChecker() == null || !currentUser.getChecker()) {
							canApprove = false;
						}
					} else if (authority.contains(Authorities.ADMINISTRATOR)) {
						modelMap.put("bankList", bankApi.getAllBank());
						modelMap.put("userAdmin", true);
						canApprove = false;
					}

					modelMap.put("canApprove", canApprove);
					customerList = customerApi.getCustomer(page, name, mobileNo, null, null, accountNo, branch, bank,
							true, fromDate, toDate, true);
					modelMap.put("canApprove", canApprove);
					modelMap.put("customerList", customerList);
					modelMap.put("customerStatusList", CustomerStatus.values());
					modelMap.put("name", name);
					modelMap.put("accountNo", accountNo);
					modelMap.put("mobileNo", mobileNo);
					modelMap.put("branch", branch);
					modelMap.put("bank", bank);
					modelMap.put("from_date", fromDate);
					modelMap.put("to_date", toDate);
					return "/Customer/expiredCustomer";
				}
				return "redidrect:/";

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/customer/renewed/get")
	public ResponseEntity<RestResponseDTO> getRenewedCustomer(@RequestParam("days") int days) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
					|| (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))) {
				try{
					response.setResponseStatus(ResponseStatus.SUCCESS);
					response.setStatus("Success");
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetail(customerApi.getRenewedCustomer(days,AuthenticationUtil.getCurrentUser().getId()));
					return new ResponseEntity<>(response,HttpStatus.OK);
				}catch(Exception e){
					e.printStackTrace();
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/changestatus")
	public String changeStatus(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if ((authority.contains(Authorities.BANK) || authority.contains(Authorities.BANK_BRANCH))
						&& authority.contains(Authorities.AUTHENTICATED)) {
					if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)) {
						if (AuthenticationUtil.getCurrentUser().getChecker() == null
								|| !AuthenticationUtil.getCurrentUser().getChecker()) {
							return "redirect:/";
						}
					}
					String uniqueId = request.getParameter("customer");
					CustomerStatus status = CustomerStatus.valueOf(request.getParameter("status"));
					String remarks = request.getParameter("remarks");
					boolean valid = customerApi.changeStatus(uniqueId, status, remarks,
							AuthenticationUtil.getCurrentUser().getUserName());

					if (valid) {
						redirectAttributes.addFlashAttribute("message", "Status Updated Sucessfull");
					} else {
						redirectAttributes.addFlashAttribute("message",
								"Ubable to update status, Please try again later");
					}

					return "redirect:/customer/list";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/customer/retryRegistration")
	public String retryRegistration(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if ((authority.contains(Authorities.BANK) || authority.contains(Authorities.BANK_BRANCH))
						&& authority.contains(Authorities.AUTHENTICATED)) {
					if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)) {
						if (AuthenticationUtil.getCurrentUser().getChecker() == null
								|| !AuthenticationUtil.getCurrentUser().getChecker()) {
							return "redirect:/";
						}
					}
					String uniqueId = request.getParameter("customer");
					CustomerStatus status = CustomerStatus.Approved;
					String remarks = "Retry Customer Registration";
					boolean valid = customerApi.changeStatus(uniqueId, status, remarks,
							AuthenticationUtil.getCurrentUser().getUserName());

					if (valid) {
						redirectAttributes.addFlashAttribute("message", "Customer Registered Sucessfull");
					} else {
						redirectAttributes.addFlashAttribute("message",
								"Ubable to update Customer Registration, Please try again later");
					}

					return "redirect:/customer/list/pendingRegistration";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/customer/inactive/list")
	public String listCustomer(ModelMap modelMap, HttpServletRequest request, Model model,
			HttpServletResponse response) {
		boolean canApprove = false;
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
						|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
						|| (authority.contains(Authorities.BANK_BRANCH)
								&& authority.contains(Authorities.AUTHENTICATED))) {
					User currentUser = AuthenticationUtil.getCurrentUser();
					if (currentUser.getUserType().equals(UserType.BankBranch)) {
						long bankBranchId = AuthenticationUtil.getCurrentUser().getAssociatedId();
						BankBranchDTO bankBranch = bankBranchApi.findBranchById(bankBranchId);
						canApprove = bankBranch.isChecker() ? true : false;
					} else if (currentUser.getUserType().equals(UserType.Admin)) {
						modelMap.put("userAdmin", true);
					}
					List<CustomerDTO> customerList = customerApi
							.getCustomerWithoutTransaction(currentUser.getAssociatedId());
					modelMap.put("canApprove", canApprove);
					modelMap.put("customerList", customerList);
					return "/Customer/inactiveCustomer";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/ajax/customer/update")
	@ResponseBody
	public Map<String, String> updateCustomer(ActionLogDTO actionLogDto, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> responseMap = new HashMap<String, String>();
		if (AuthenticationUtil.getCurrentUser() != null) {

			customerApi.updateCustomer(Long.parseLong(actionLogDto.getForUser()), actionLogDto.getAction());
			actionLogApi.saveActionLog(actionLogDto.getRemarks(), Long.parseLong(actionLogDto.getForUser()),
					actionLogDto.getForUserType(), AuthenticationUtil.getCurrentUser().getId());
			responseMap.put("status", "success");
			return responseMap;
		}
		responseMap.put("status", "failure");

		return responseMap;
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "/deleteCustomer")
	 * public String deleteCustomer(ModelMap modelMap, Model model,
	 * 
	 * @RequestParam(value = "customerId", required = true) String customerId,
	 * HttpServletRequest request, HttpServletResponse response,
	 * RedirectAttributes redirectAttributes) { if
	 * (AuthenticationUtil.getCurrentUser() != null) { String authority =
	 * AuthenticationUtil.getCurrentUser().getAuthority(); if
	 * (authority.contains(Authorities.ADMINISTRATOR) &&
	 * authority.contains(Authorities.AUTHENTICATED) ||
	 * (authority.contains(Authorities.SUPER_AGENT) &&
	 * authority.contains(Authorities.AUTHENTICATED))) {
	 * 
	 * customerApi.deleteCustomer(Long.parseLong(customerId));
	 * redirectAttributes.addFlashAttribute("message",
	 * "customer.delete.successfull"); return "redirect:/listCustomer"; } return
	 * "/"; } return "/"; }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "customer/details")
	public String viewDetails(@RequestParam("customer") String uniqueId, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws ClientException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			boolean canApprove = true;
			if ((authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED))
					|| (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))
					|| (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED))) {
				CustomerDTO customerDto = customerApi.findCustomerByUniqueId(uniqueId);
				List<CustomerBankAccountDTO> customerBankAccountList = new ArrayList<>();
				if (authority.contains(Authorities.ADMINISTRATOR)) {
					customerBankAccountList = customerBankAccountApi.findCustomerBankAccountByCustomer(uniqueId);
					modelMap.put("canApprove", false);
				}
				if (authority.contains(Authorities.BANK_BRANCH)) {
					customerBankAccountList = customerBankAccountApi.findCustomerAllAccountsByBranchAndCustomer(
							AuthenticationUtil.getCurrentUser().getAssociatedId(), uniqueId);
					modelMap.put("userTypeBank", false);
					if (!AuthenticationUtil.getCurrentUser().getChecker()) {
						modelMap.put("canApprove", false);
					}
				} else if (authority.contains(Authorities.BANK)) {
					customerBankAccountList = customerBankAccountApi.findCustomerAllAccountsByBankAndCustomer(
							AuthenticationUtil.getCurrentUser().getAssociatedId(), uniqueId);
					List<BankBranchDTO> branchList = bankBranchApi
							.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
					modelMap.put("branchList", branchList);
					modelMap.put("userTypeBank", true);
				}
				modelMap.put("canApprove", canApprove);
				modelMap.put("customerBankAccountList", customerBankAccountList);
				modelMap.put("customer", customerDto);
				modelMap.put("accountMode", AccountMode.values());
			}
			return "/Customer/details";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/customer/document/view", method = RequestMethod.GET)
	public String viewDocuments(ModelMap modelMap, @RequestParam("customerId") String customerId,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println(customerId + " THIS IS CUSTOMER ID");
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.BANK)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.BANK_BRANCH)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)) {
				try {
					// CustomerDTO customerDto =
					// customerApi.getCustomerById(customerId);
					List<CustomerKycDTO> customerKyc = customerKycApi.getCustomerKycByCustomer(customerId);
					modelMap.put("uuid", customerId);
					modelMap.put("customerKyc", customerKyc);
					return "Customer/Document/list";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/customer/document/add", method = RequestMethod.GET)
	public String addCustomerDocuments(ModelMap modelMap, @RequestParam("customerId") String customerId,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.BANK)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.BANK_BRANCH)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)) {
				List<StateDTO> statesList = stateApi.getAllState();
				List<DocumentIdsDTO> documentIds = documentIdsApi.getDocumentIdsByStatus(Status.Active);
				// CustomerDto =
				modelMap.put("uuid", customerId);
				modelMap.put("documentIds", documentIds);
				modelMap.put("statesList", statesList);
				return "Customer/Document/add";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/customer/document/add", method = RequestMethod.POST)
	public String addCustomerDocuments(ModelMap modelMap,
			@ModelAttribute("customerDocument") CustomerKycDTO customerKycDto, HttpServletRequest request,
			HttpServletResponse response) {
		if (customerKycDto == null) {
			return "redirect:/customer/document/add";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.BANK)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.BANK_BRANCH)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)) {
				String uuid = customerKycDto.getCustomer();
				try {
					customerKycApi.save(customerKycDto);
					return "redirect:/customer/document/view?customerId=" + uuid;
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/customer/document/add?customerId=" + uuid;
				}

			}
		}
		return "redirect:/";
	}

	/*
	 * @RequestMapping(value="/customer/documents/add",
	 * method=RequestMethod.GET) public String addDocuments(ModelMap
	 * modelMap, @RequestParam("customer") String uniqueId, HttpServletRequest
	 * request, HttpServletResponse response) { if
	 * (AuthenticationUtil.getCurrentUser() != null) { String authority =
	 * AuthenticationUtil.getCurrentUser().getAuthority(); if
	 * (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) ||
	 * authority.equals(Authorities.BANK_BRANCH+","+Authorities.AUTHENTICATED)
	 * || authority.equals(Authorities.AUTHENTICATED+","+Authorities.BANK) ||
	 * authority.equals(Authorities.AUTHENTICATED+","+Authorities.BANK_BRANCH))
	 * { if (uniqueId == null) { return "redirect:/customer/list"; } CustomerDTO
	 * customerDto = customerApi.findCustomerByUniqueId(uniqueId);
	 * List<DocumentIdsDTO> documents =
	 * documentIdsApi.getDocumentIdsByStatus(Status.Active);
	 * modelMap.put("documets", documents); modelMap.put("uniqueId",
	 * customerDto.getUniqueId()); return "Customer/Document/add"; } }
	 * 
	 * return "redirect:/"; }
	 * 
	 * @RequestMapping(value="/customer/documents/add",
	 * method=RequestMethod.POST) public String addDocuments(ModelMap
	 * modelMap, @ModelAttribute("customerKyc") CustomerKycDTO customerKycDto,
	 * HttpServletRequest request, HttpServletResponse response,
	 * RedirectAttributes redirectAttributes) { if
	 * (AuthenticationUtil.getCurrentUser() != null) { String authority =
	 * AuthenticationUtil.getCurrentUser().getAuthority(); if
	 * (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) ||
	 * authority.equals(Authorities.BANK_BRANCH+","+Authorities.AUTHENTICATED)
	 * || authority.equals(Authorities.AUTHENTICATED+","+Authorities.BANK) ||
	 * authority.equals(Authorities.AUTHENTICATED+","+Authorities.BANK_BRANCH))
	 * { if (customerKycDto == null) { return "redirect:/customer/list"; }
	 * 
	 * 
	 * return "Customer/Document/list"; } } return "return redirect:/"; }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "customer/resetpin")
	public String resetPassword(RedirectAttributes redirectAttributes, @RequestParam("customer") String uniqueId) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if ((authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))
					|| (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED))) {
				try {
					Boolean pinReset = customerApi.resetPin(uniqueId);
					if (pinReset) {
						redirectAttributes.addFlashAttribute("message", "Pin Changed SuccessFully");
					} else {
						redirectAttributes.addFlashAttribute("message", "Unable to reset pin.Please try again later.");
					}
					return "redirect:/customer/list";
				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("message", "Unable to reset pin.Please try again later.");
					return "redirect:/customer/list";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/renew")
	public ResponseEntity<RestResponseDTO> renewCustomer(RedirectAttributes redirectAttributes,
			@RequestParam("customer") String uniqueId) {
		RestResponseDTO responseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED))
					|| (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))
					|| (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED))) {
				try {
					HashMap<String, String> response = customerApi.renewCustomer(uniqueId,
							AuthenticationUtil.getCurrentUser().getUserName());
					if (response.get("status").equals("success")) {
						responseDto.setStatus("Success");
						responseDto.setResponseStatus(ResponseStatus.SUCCESS);
						responseDto.setMessage("Customer renewed successfully.");
					} else {
						responseDto.setStatus("Failure");
						responseDto.setResponseStatus(ResponseStatus.FAILURE);
						responseDto.setMessage(response.get("Result Message"));
					}
					return new ResponseEntity<>(responseDto, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					responseDto.setStatus("Failure");
					responseDto.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
					responseDto.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		responseDto.setStatus("Failure");
		responseDto.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
		responseDto.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "customer/edit")
	public String editCustomer(ModelMap modelMap, @RequestParam("customer") String uniqueId) throws Exception {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if ((authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))
					|| (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED))) {
				CustomerDTO customer = customerApi.findCustomerByUniqueId(uniqueId);
				modelMap.put("customer", customer);
				List<StateDTO> stateList = stateApi.getAllState();
				List<CustomerStatus> customerStatuslist = Arrays.asList(CustomerStatus.values());
				modelMap.put("customerStatus", customerStatuslist);
				modelMap.put("stateList", stateList);
				List<CityDTO> cityList = cityApi.findCityByStateName(customer.getState());
				modelMap.put("cityList", cityList);
				long bankId = 0;
				if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)) {
					bankId = AuthenticationUtil.getCurrentUser().getAssociatedId();
				} else if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)) {
					BankDTO bankDto = bankBranchApi
							.findBankOfBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
					bankId = bankDto.getId();
				}
				List<CustomerProfileDTO> customerProfileList = customerProfileApi.listActiveCustomerProfile(bankId);
				CustomerProfileDTO customerProfileDTO = customerProfileApi.getCustomerProfile(uniqueId);
				modelMap.put("customerProfile", customerProfileDTO);
				modelMap.put("customerProfileList", customerProfileList);
				return "/Customer/editCustomer";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/edit")
	public String editCustomer(ModelMap modelMap, CustomerDTO customerDto, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))
					|| (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED))) {
				try {
					CustomerError error = new CustomerError();
					error = customerValidation.customerEditValidation(customerDto);
					if (error.isValid()) {
						customerApi.editCustomer(customerDto);
						redirectAttributes.addFlashAttribute("message", "Customer Edited Successfully");
						return "redirect:/customer/list";
					} else {
						modelMap.put("customer", customerDto);
						modelMap.put("error", error);
						List<StateDTO> stateList = stateApi.getAllState();
						modelMap.put("stateList", stateList);
						List<CityDTO> cityList = cityApi.findCityByStateName(customerDto.getState());
						modelMap.put("cityList", cityList);
						return "/Customer/editCustomer";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/changeAccountNumber")
	public String changeAccountNumber(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ClientException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String uniqueId = null;
			try {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if ((authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))
						|| (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED))) {
					uniqueId = request.getParameter("uniqueId");
					String currentAccountNumber = request.getParameter("currentAccountNumber");
					String newAccountNumber = request.getParameter("accountNumber");
					String accountMode = request.getParameter("accountMode");
					String comment = request.getParameter("comment");
					CustomerDTO customer = customerApi.findCustomerByUniqueId(uniqueId);
					CustomerBankAccountDTO account = customerBankAccountApi
							.findCustomerBankAccountByAccountNo(currentAccountNumber, customer.getId());
					BankBranchDTO bankBranchDTO = bankBranchApi.findBranchById(account.getBranchId());
					if (newAccountNumber != currentAccountNumber) {
						String error = customerValidation.checkAccountNumber(newAccountNumber, account.getBranchId());
						if (error == null) {
							if (!(newAccountNumber
									.startsWith(bankBranchDTO.getBranchCode() + bankBranchDTO.getBranchCode()))) {
								newAccountNumber = bankBranchDTO.getBranchCode() + newAccountNumber;
							}
							customerApi.changeAccountNumber(uniqueId, currentAccountNumber, newAccountNumber,
									AccountMode.valueOf(accountMode), comment);
							redirectAttributes.addFlashAttribute("message", "Account Edited Successfully");
							return "redirect:/customer/details?customer=" + uniqueId;
						} else {
							redirectAttributes.addFlashAttribute("editAccountValid", false);
							redirectAttributes.addFlashAttribute("error", error);
							redirectAttributes.addFlashAttribute("uniqueId", uniqueId);
							redirectAttributes.addFlashAttribute("currentAccountNumber", currentAccountNumber);
							redirectAttributes.addFlashAttribute("accountNumber", newAccountNumber);
							redirectAttributes.addFlashAttribute("selectedAccountMode", accountMode);
							return "redirect:/customer/details?customer=" + uniqueId;
						}

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/customer/details?customer=" + uniqueId;
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/mobileNumber")
	public String changeMobileNumber(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ClientException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String uniqueId = null;
			try {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if ((authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))
						|| (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED))) {
					User currentUser = AuthenticationUtil.getCurrentUser();
					uniqueId = request.getParameter("uniqueId");
					String currentMobileNumber = request.getParameter("currentMobileNumber");
					String newMobileNumber = request.getParameter("mobileNumber");
					String comment = request.getParameter("comment");
					boolean customer = customerApi.getCustomerByMobileNo(newMobileNumber, currentUser.getAssociatedId(),
							currentUser.getUserType());
					if (customer) {
						redirectAttributes.addFlashAttribute("mobileNumberValidation", false);
						redirectAttributes.addFlashAttribute("error", "Customer with this Mobile number Already Exist");
						redirectAttributes.addFlashAttribute("uniqueId", uniqueId);
						redirectAttributes.addFlashAttribute("currentMobileNumber", currentMobileNumber);
						redirectAttributes.addFlashAttribute("mobileNumber", newMobileNumber);
						return "redirect:/customer/details?customer=" + uniqueId;
					}
					if (newMobileNumber != currentMobileNumber) {
						String error = null;
						if (newMobileNumber.length() != 10) {
							error = "Mobile Number Invalid";
						}
						if (error == null) {
							customerApi.changeMobileNumber(uniqueId, currentMobileNumber, newMobileNumber, comment);
							redirectAttributes.addFlashAttribute("message", "Mobile Number Edited Successfully");
							return "redirect:/customer/details?customer=" + uniqueId;
						} else {
							redirectAttributes.addFlashAttribute("mobileNumberValidation", false);
							redirectAttributes.addFlashAttribute("error", error);
							redirectAttributes.addFlashAttribute("uniqueId", uniqueId);
							redirectAttributes.addFlashAttribute("currentMobileNumber", currentMobileNumber);
							redirectAttributes.addFlashAttribute("mobileNumber", newMobileNumber);
							return "redirect:/customer/details?customer=" + uniqueId;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("errorMesssage", "Something went wrong, Please try again later");
				return "redirect:/customer/details?customer=" + uniqueId;
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/addAccount")
	public String addAccountNumber(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ClientException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String uniqueId = request.getParameter("uniqueId");
			String accountNumber = request.getParameter("accountNumber");
			String accountMode = request.getParameter("accountMode");
			String comment = request.getParameter("comment");
			long branchId = 0L;
			if (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)) {
				branchId = AuthenticationUtil.getCurrentUser().getAssociatedId();
			} else if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)) {
				branchId = Long.parseLong(request.getParameter("branch"));
			}
			BankBranchDTO bankBranch = bankBranchApi.findBranchById(branchId);
			String error = customerValidation.checkAccountNumber(accountNumber, branchId);
			accountNumber = bankBranch.getBranchCode() + accountNumber;
			if (error == null) {
				customerApi.addCustomerBankAccount(uniqueId, accountNumber, branchId, AccountMode.valueOf(accountMode),
						comment);
				redirectAttributes.addFlashAttribute("message", "Account Added Successfully");
				return "redirect:/customer/details?customer=" + uniqueId;
			} else {
				redirectAttributes.addFlashAttribute("addAccountValid", false);
				redirectAttributes.addFlashAttribute("error", error);
				redirectAttributes.addFlashAttribute("uniqueId", uniqueId);
				return "redirect:/customer/details?customer=" + uniqueId;
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/account/delete")
	public String deleteAccountNumber(HttpServletRequest request, RedirectAttributes redirectAttributes)
			throws ClientException {
		String uniqueId = null;
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {
				String authority = AuthenticationUtil.getCurrentUser().getAuthority();
				if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
					return "redirect:/";
				}
				if ((authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))
						|| (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED))) {
					uniqueId = request.getParameter("customer");
					String customerBankAccountId = request.getParameter("account");
					customerApi.deleteCustomerBankAccount(Long.parseLong(customerBankAccountId));
					redirectAttributes.addFlashAttribute("message", "Account Deleted Successfully");
					return "redirect:/customer/details?customer=" + uniqueId;
				}
			}
		} catch (UnsupportedOperationException e) {
			redirectAttributes.addFlashAttribute("errorMesssage", e.getMessage());
			return "redirect:/customer/details?customer=" + uniqueId;
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMesssage", "Something went wrong. Please try again later");
			return "redirect:/customer/details?customer=" + uniqueId;
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/Static/{staticPage}")
	public String getStatic(ModelMap map, @PathVariable("staticPage") String staticPage) {
		return "Static/" + staticPage;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/Static/{staticPage}")
	public String getStaticPost(ModelMap map, @PathVariable("staticPage") String staticPage) {
		return "Static/" + staticPage;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/customer/getCustomerLog", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getCustomerLog(HttpServletRequest request,
			@RequestParam("uuid") String customerUid, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED))
					|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)) {
				try {
					List<CustomerLogDto> customerDtoList = customerLogApi.getCustomerLogByCustomer(customerUid);
					if (customerDtoList.size() > 15) {
						restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED,
								customerDtoList.subList(0, 15));
					} else {
						restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED,
								customerDtoList);
					}
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED, null);
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/findCustomer", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getCustomer(@RequestParam("data") String data,
			@RequestParam(value = "bankCode", required = false) String bankCode) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {

				if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)) {
					BankDTO bankDto = bankApi.getBankDtoById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					bankCode = bankDto.getSwiftCode();
				} else if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.BankBranch)) {
					BankDTO bankDto = bankBranchApi
							.findBankOfBranch(AuthenticationUtil.getCurrentUser().getAssociatedId());
					bankCode = bankDto.getSwiftCode();
				}

				List<CustomerDTO> customerList = customerApi.getUserByMobileNumberConCat(data, bankCode);
				response = getResponseDto(ResponseStatus.SUCCESS, "User Retrived Successfully", customerList);
				return new ResponseEntity<RestResponseDTO>(response, HttpStatus.OK);
			}
		}
		response = getResponseDto(ResponseStatus.UNAUTHORIZED_USER, "Un-Authorized User", "");
		return new ResponseEntity<RestResponseDTO>(response, HttpStatus.UNAUTHORIZED);
	}

	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail) {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);
		return restResponseDto;
	}

	@RequestMapping(value = "/customer/resetToken", method = RequestMethod.GET)
	public String resetToken(@RequestParam("uniqueId") String uniqueId, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
							| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
				try {
					Boolean valid = customerApi.resetToken(uniqueId);
					if (valid) {
						redirectAttributes.addFlashAttribute("message", "Token flushed Successfully");
						return "redirect:/customer/details?customer=" + uniqueId;
					} else {
						redirectAttributes.addFlashAttribute("message", "Unable to flush token, please try again.");
						return "redirect:/customer/details?customer=" + uniqueId;
					}

				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("message", "Something went wrong, please try again.");
					return "redirect:/customer/details?customer=" + uniqueId;
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/customer/regestrationreport", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getCustomerRegestrationReport(
			@RequestParam(value = "bank", required = false) Long bank,
			@RequestParam(value = "branch", required = false) Long branch,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
					|| (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED))) {
				// try {
				User currentUser = AuthenticationUtil.getCurrentUser();
				Long currentUserId = currentUser.getId();
				if (currentUser.getUserType().equals(UserType.Bank)) {
					bank = bankApi.getBankById(currentUser.getAssociatedId()).getId();
				} /*
					 * else if (currentUser.getUserType().equals(UserType.Bank))
					 * { BankDTO bank =
					 * bankBranchApi.findBankOfBranch(currentUser.
					 * getAssociatedId()); bankCode = bank.getSwiftCode();
					 * currentUserId = bank.getId(); }
					 */
				// customerApi.getCustomerRegestrationReport(bankCode, fromDate,
				// toDate, currentUserId));
				restResponseDto.setDetail(
						customerApi.getCustomerRegestrationReport(bank,branch, fromDate, toDate, currentUserId));
				restResponseDto.setMessage("success");
				return new ResponseEntity<>(restResponseDto, HttpStatus.OK);
				/*
				 * } catch (Exception e) {
				 * System.out.println("Exception occurs !!"+e.getMessage());
				 * restResponseDto.setMessage("Internal Server Error"); return
				 * new ResponseEntity<>(restResponseDto,
				 * HttpStatus.INTERNAL_SERVER_ERROR); }
				 */
			}
		}
		restResponseDto.setMessage("Unauthorized User");
		return new ResponseEntity<>(restResponseDto, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "reports/c_r_report", method = RequestMethod.GET)
	public String crReportView(ModelMap map) {

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AUTHENTICATED)
					&& (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK))) {
				if (authority.contains(Authorities.ADMINISTRATOR)) {
					map.put("userAdmin", true);
				}
				return "reports/cusRegReportView";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
}
