
package com.mobilebanking.controller;

import com.mobilebanking.api.*;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.*;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.DateUtil;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MainController implements MessageSourceAware {

	private MessageSource messageSource;

	private Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private IUserLogApi userLogApi;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IMerchantServiceApi serviceApi;

	@Autowired
	private ITransactionApi transactionApi;

	@Autowired
	private ISmsLogApi smsLogApi;

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private IAccountApi accountApi;

	@Autowired
	private INonFinancialTransactionApi nonFinancialTransactionApi;
	
	@Autowired
	private IServiceCategoryApi serviceCategoryApi;
	
	@Autowired
	private IChequeRequestApi chequeRequestApi;
	
	@Autowired
	private IChequeBlockRequestApi chequeBlockApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String getHome(ModelMap modelMap, Model model,
			@RequestParam(value = "errormessage", required = false) String errormessage,
			@RequestParam(value = "infomessage", required = false) String infomessage,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request, HttpSession session)
			throws ClientException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			System.out.println("User Type " + AuthenticationUtil.getCurrentUser().getUserType());
			session.setAttribute("currentUser", AuthenticationUtil.getCurrentUser());
			User currentUser = AuthenticationUtil.getCurrentUser();
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)) {
				try {
					userLogApi.save(AuthenticationUtil.getCurrentUser().getUserName(), "Logged In ", true);
					UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
					if (user.isFirstLogin()) {
						modelMap.put("firstLogin", true);
						modelMap.put("username", user.getUserName());
					}
					modelMap.put("bankList",bankApi.getAllBank());
					modelMap.put("totalTransaction", transactionApi.countTransactions()+nonFinancialTransactionApi.countTransaction());
					modelMap.put("totalCustomer", customerApi.countCustomer(currentUser.getId()));
					modelMap.put("inactiveCustomer", customerApi.countCustomerWithoutTransaction(currentUser.getId()));
					modelMap.put("totalCount", smsLogApi.countSmsLog());
					modelMap.put("totalIncoming", smsLogApi.countIncomingSms());
					modelMap.put("totalOutgoing",smsLogApi.countOutgoingSms());
					modelMap.put("totalMiniStatement", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType.MINISTATEMENT));
					modelMap.put("totalBalanceEnquiry", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType.BALANCEENQUIRY));
					List<MerchantServiceDTO> serviceList = serviceApi.findAllMerchantService();
//					for(MerchantServiceDTO service : serviceList){
//						service.setCount(transactionApi.countTransactionByServiceAndStatus(service.getUniqueIdentifier(),TransactionStatus.Complete));
//					}
					modelMap.put("serviceList", serviceList);
					modelMap.put("overall", "active");
					modelMap.put("daily", "passive");
				} catch (Exception e) {
					modelMap.put("totalTransaction", 0);
					modelMap.put("totalCustomer", 0);
					modelMap.put("inactiveCustomer", 0);
					modelMap.put("totalCount", 0);
					modelMap.put("totalIncoming", 0);
					modelMap.put("totalOutgoing", 0);
					modelMap.put("totalMiniStatement", 0);
					modelMap.put("totalBalanceEnquiry", 0);
					modelMap.put("overall", "active");
					modelMap.put("daily", "passive");
					e.printStackTrace();
					logger.debug(e.getMessage());
				}

				return "/Main/adminLogin";
			} else if (authority.contains(Authorities.AGENT) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_ADMIN) || authority.contains(Authorities.BANK_BRANCH_ADMIN)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				logger.debug("userlogin==>");
				UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				if (user.isFirstLogin()) {
					modelMap.put("firstLogin", true);
					modelMap.put("username", user.getUserName());
				}
				if (currentUser.getUserType().equals(UserType.Bank)) {
					try {
						modelMap.put("bankList",bankApi.getAllBank());
						modelMap.put("totalTransaction", transactionApi.countTransactions()+nonFinancialTransactionApi.countTransaction());
						modelMap.put("totalCustomer",customerApi.countCustomer(currentUser.getId()));
						modelMap.put("lastWeekCustomer", customerApi.countCustomerWithInLastWeek(currentUser.getAssociatedId()));
						modelMap.put("lastMonthCustomer", customerApi.countCustomerWithInLastMonth(currentUser.getAssociatedId()));
						modelMap.put("inactiveCustomer", customerApi.getCustomerWithoutTransaction(currentUser.getAssociatedId()) != null ? customerApi.getCustomerWithoutTransaction(currentUser.getAssociatedId()).size() : 0);
						modelMap.put("totalCount", smsLogApi.countSmsLog());
						modelMap.put("totalMiniStatement", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType.MINISTATEMENT));
						modelMap.put("totalBalanceEnquiry", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType.BALANCEENQUIRY));
						modelMap.put("totalIncoming", smsLogApi.countIncomingSms());
						modelMap.put("totalOutgoing", smsLogApi.countOutgoingSms());
						System.out.println("id current user ko "+currentUser.getAssociatedId());
						BankDTO bank = bankApi.getBankDtoById(currentUser.getAssociatedId());
						HashMap<String, Double> balance = accountApi.getCreditBalanceAndBalanceByBank(bank.getId());
						modelMap.put("remainingBalance", balance.get("balance"));
						modelMap.put("creditBalance", balance.get("creditLimit"));
						modelMap.put("availableBalance", balance.get("balance") + balance.get("creditLimit"));
						modelMap.put("totalSmsCount", bank.getSmsCount());
						List<MerchantServiceDTO> serviceList = serviceApi.findAllMerchantService();
						for(MerchantServiceDTO service : serviceList){
							service.setCount(transactionApi.countTransactionByServiceAndStatus(service.getUniqueIdentifier(),TransactionStatus.Complete));
						}
						modelMap.put("serviceList", serviceList);
						modelMap.put("overall", "active");
						modelMap.put("daily", "passive");
						return "/Main/userLogin";
					} catch (Exception e) {
						modelMap.put("totalTransaction", 0);
						modelMap.put("totalCustomer", 0);
						modelMap.put("lastWeekCustomer", 0);
						modelMap.put("lastMonthCustomer", 0);
						modelMap.put("inactiveCustomer", 0);
						modelMap.put("totalCount", 0);
						modelMap.put("totalMiniStatement", 0);
						modelMap.put("totalBalanceEnquiry", 0);
						modelMap.put("totalIncoming", 0);
						modelMap.put("totalOutgoing", 0);
						modelMap.put("remainingBalance", 0);
						modelMap.put("creditBalance", 0);
						modelMap.put("totalSmsCount", 0);
						modelMap.put("availableBalance", 0);
						modelMap.put("overall", "active");
						modelMap.put("daily", "passive");
						e.printStackTrace();
						logger.debug(e.getMessage());
						return "/Main/userLogin";
					}
				} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
					try {
						modelMap.put("totalTransaction", transactionApi.countTransactions()+nonFinancialTransactionApi.countTransaction());
						modelMap.put("totalCustomer",customerApi.countCustomer(currentUser.getId()));
						modelMap.put("lastWeekCustomer", customerApi.countCustomerWithInLastWeek(currentUser.getAssociatedId()));
						modelMap.put("lastMonthCustomer", customerApi.countCustomerWithInLastMonth(currentUser.getAssociatedId()));
						modelMap.put("inactiveCustomer", customerApi.getCustomerWithoutTransaction(currentUser.getAssociatedId()) != null ? customerApi.getCustomerWithoutTransaction(currentUser.getAssociatedId()).size() : 0);
						modelMap.put("totalCount", smsLogApi.countSmsLog());
						modelMap.put("totalMiniStatement",nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType.MINISTATEMENT));
						modelMap.put("totalBalanceEnquiry",nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType.BALANCEENQUIRY));
						modelMap.put("totalIncoming", smsLogApi.countIncomingSms());
						modelMap.put("totalOutgoing", smsLogApi.countOutgoingSms());
						List<MerchantServiceDTO> serviceList = serviceApi.findAllMerchantService();
//						for(MerchantServiceDTO service : serviceList){
//							service.setCount(transactionApi.countTransactionByServiceAndStatus(service.getUniqueIdentifier(),TransactionStatus.Complete));
//						}
						modelMap.put("serviceList", serviceList);
						modelMap.put("overall", "active");
						modelMap.put("daily", "passive");
						return "/Main/branchLogin";
					} catch (Exception e) {
						modelMap.put("totalTransaction", 0);
						modelMap.put("totalCustomer", 0);
						modelMap.put("lastWeekCustomer", 0);
						modelMap.put("lastMonthCustomer", 0);
						modelMap.put("inactiveCustomer", 0);
						modelMap.put("totalCount", 0);
						modelMap.put("totalMiniStatement", 0);
						modelMap.put("totalBalanceEnquiry", 0);
						modelMap.put("totalIncoming", 0);
						modelMap.put("totalOutgoing", 0);
						modelMap.put("overall", "active");
						modelMap.put("daily", "passive");
						e.printStackTrace();
						logger.debug(e.getMessage());
						return "/Main/branchLogin";
					}
				} else if (authority.contains(Authorities.SUPER_AGENT)) {
					logger.debug("userlogin==>");
					return "/Main/userLogin";
				} else if (authority.contains(Authorities.CUSTOMER)) {
					logger.debug("userlogin==>");
					CustomerDTO customer = customerApi.getCustomerById(currentUser.getAssociatedId());
					modelMap.put("uuid", customer.getUniqueId());
					user = userApi.getUserWithId(currentUser.getId());
					if (user.isFirstLogin()) {
						modelMap.put("firstLogin", true);
						modelMap.put("username", user.getUserName());
					}
					return "/Main/userLogin";
				} else if (authority.contains(Authorities.AUTHENTICATED)) {
					logger.debug("userlogin==>");
					return "/Main/userLogin";
				} else if (authority.contains(Authorities.SENDER_CUSTOMER)) {
					logger.debug("userlogin==>");
					modelMap.put("isCustomer", true);
					return "/Main/userLogin";
				} else if (authority.contains(Authorities.BENEFICIARY_CUSTOMER)) {
					logger.debug("userlogin==>");
					modelMap.put("isCustomer", true);
					return "/Main/userLogin";
				}

			}else if(authority.contains(Authorities.CHANNELPARTNER)) {
				UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				if (user.isFirstLogin()) {
					modelMap.put("firstLogin", true);
					modelMap.put("username", user.getUserName());
				}
				try {
					modelMap.put("totalBanks", bankApi.countByChannelPartner(user.getAssociatedId()));
					modelMap.put("totaltransactionAlert", 0);
					modelMap.put("totalCommission", 0);
					return "/Main/channelPartnerLogin";
				} catch (Exception e) {
					modelMap.put("totalBanks", 0);
					modelMap.put("totaltransactionAlert", 0);
					modelMap.put("totalCommission", 0);
					e.printStackTrace();
					logger.debug(e.getMessage());
					return "/Main/channelPartnerLogin";
				}
			
			}
		}
		return "redirect:/main";
	}

	
	//added for the new tab in the dashboard by a.s
	@RequestMapping(method = RequestMethod.GET, value = "/today_dashboard")
	public String getDashbardOfToday(ModelMap modelMap, Model model,
			@RequestParam(value = "errormessage", required = false) String errormessage,
			@RequestParam(value = "infomessage", required = false) String infomessage,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request, HttpSession session)
			throws ClientException {
		if (AuthenticationUtil.getCurrentUser() != null) {
			System.out.println("User Type " + AuthenticationUtil.getCurrentUser().getUserType());
			session.setAttribute("currentUser", AuthenticationUtil.getCurrentUser());
			User currentUser = AuthenticationUtil.getCurrentUser();
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			//Date date=DateUtil.getDate(DateUtil.convertDateToString(DateUtil.getCurrentDate()));
			Date date=DateUtil.getDateWith0Time();
			//System.out.println("today dashboard :"+date);
			if (authority.contains(Authorities.ADMINISTRATOR)) {
				try {
					userLogApi.save(AuthenticationUtil.getCurrentUser().getUserName(), "Logged In ", true);
					UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
					if (user.isFirstLogin()) {
						modelMap.put("firstLogin", true);
						modelMap.put("username", user.getUserName());
					}
					modelMap.put("totalTransaction", transactionApi.countTransactionsByDate(date)+nonFinancialTransactionApi.countTransactionsByDate(date));
					modelMap.put("totalCustomer", customerApi.countCustomerByDate(date));
					modelMap.put("inactiveCustomer", customerApi.countCustomerWithoutTransactionByDate(date));
					modelMap.put("totalCount", smsLogApi.countSmsLogByDate(date));
					modelMap.put("totalIncoming", smsLogApi.countIncomingSmsByDate(date));
					modelMap.put("totalOutgoing",smsLogApi.countOutgoingSmsByDate(date));
					modelMap.put("totalMiniStatement", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.MINISTATEMENT,date));
					modelMap.put("totalBalanceEnquiry", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.BALANCEENQUIRY,date));
					List<MerchantServiceDTO> serviceList = serviceApi.findAllMerchantService();
					for(MerchantServiceDTO service : serviceList){
						service.setCount(transactionApi.countTransactionByServiceAndStatusAndDate(service.getUniqueIdentifier(),TransactionStatus.Complete,date));
					}
					modelMap.put("serviceList", serviceList);
					modelMap.put("overall", "passive");
					modelMap.put("daily", "active");
				} catch (Exception e) {
					modelMap.put("totalTransaction", 0);
					modelMap.put("totalCustomer", 0);
					modelMap.put("inactiveCustomer", 0);
					modelMap.put("totalCount", 0);
					modelMap.put("totalIncoming", 0);
					modelMap.put("totalOutgoing", 0);
					modelMap.put("totalMiniStatement", 0);
					modelMap.put("totalBalanceEnquiry", 0);
					modelMap.put("overall", "passive");
					modelMap.put("daily", "active");
					e.printStackTrace();
					logger.debug(e.getMessage());
				}

				return "/Main/adminLogin";
			} else if (authority.contains(Authorities.AGENT) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_ADMIN) || authority.contains(Authorities.BANK_BRANCH_ADMIN)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				logger.debug("userlogin==>");
				UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				if (user.isFirstLogin()) {
					modelMap.put("firstLogin", true);
					modelMap.put("username", user.getUserName());
				}
				if (currentUser.getUserType().equals(UserType.Bank)) {
					try {
						modelMap.put("totalTransaction", transactionApi.countTransactionsByDate(date)+nonFinancialTransactionApi.countTransactionsByDate(date));
						modelMap.put("totalCustomer",customerApi.countCustomerByDate(date));
						modelMap.put("lastWeekCustomer", customerApi.countCustomerWithInLastWeek(currentUser.getAssociatedId()));
						modelMap.put("lastMonthCustomer", customerApi.countCustomerWithInLastMonth(currentUser.getAssociatedId()));
						modelMap.put("inactiveCustomer", customerApi.getCustomerWithoutTransactionByDate(currentUser.getAssociatedId(),date) != null ? customerApi.getCustomerWithoutTransactionByDate(currentUser.getAssociatedId(),date).size() : 0);
						modelMap.put("totalCount", smsLogApi.countSmsLogByDate(date));
						modelMap.put("totalMiniStatement", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.MINISTATEMENT,date));
						modelMap.put("totalBalanceEnquiry", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.BALANCEENQUIRY,date));
						modelMap.put("totalIncoming", smsLogApi.countIncomingSmsByDate(date));
						modelMap.put("totalOutgoing", smsLogApi.countOutgoingSmsByDate(date));
						BankDTO bank = bankApi.getBankDtoById(currentUser.getAssociatedId());
						HashMap<String, Double> balance = accountApi.getCreditBalanceAndBalanceByBank(bank.getId());
						modelMap.put("remainingBalance", balance.get("balance"));
						modelMap.put("creditBalance", balance.get("creditLimit"));
						modelMap.put("availableBalance", balance.get("balance") + balance.get("creditLimit"));
						modelMap.put("totalSmsCount", bank.getSmsCount());
						List<MerchantServiceDTO> serviceList = serviceApi.findAllMerchantService();
						for(MerchantServiceDTO service : serviceList){
							service.setCount(transactionApi.countTransactionByServiceAndStatusAndDate(service.getUniqueIdentifier(),TransactionStatus.Complete,date));
						}
						modelMap.put("serviceList", serviceList);
						modelMap.put("overall", "passive");
						modelMap.put("daily", "active");
						return "/Main/userLogin";
					} catch (Exception e) {
						modelMap.put("totalTransaction", 0);
						modelMap.put("totalCustomer", 0);
						modelMap.put("lastWeekCustomer", 0);
						modelMap.put("lastMonthCustomer", 0);
						modelMap.put("inactiveCustomer", 0);
						modelMap.put("totalCount", 0);
						modelMap.put("totalMiniStatement", 0);
						modelMap.put("totalBalanceEnquiry", 0);
						modelMap.put("totalIncoming", 0);
						modelMap.put("totalOutgoing", 0);
						modelMap.put("remainingBalance", 0);
						modelMap.put("creditBalance", 0);
						modelMap.put("totalSmsCount", 0);
						modelMap.put("availableBalance", 0);
						modelMap.put("overall", "passive");
						modelMap.put("daily", "active");
						e.printStackTrace();
						logger.debug(e.getMessage());
						return "/Main/userLogin";
					}
				} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
					try {
						modelMap.put("totalTransaction", transactionApi.countTransactionsByDate(date)+nonFinancialTransactionApi.countTransactionsByDate(date));
						modelMap.put("totalCustomer",customerApi.countCustomerByDate(date));
						modelMap.put("lastWeekCustomer", customerApi.countCustomerWithInLastWeek(currentUser.getAssociatedId()));
						modelMap.put("lastMonthCustomer", customerApi.countCustomerWithInLastMonth(currentUser.getAssociatedId()));
						modelMap.put("inactiveCustomer", customerApi.getCustomerWithoutTransactionByDate(currentUser.getAssociatedId(),date) != null ? customerApi.getCustomerWithoutTransactionByDate(currentUser.getAssociatedId(),date).size() : 0);
						modelMap.put("totalCount", smsLogApi.countSmsLog());
						modelMap.put("totalMiniStatement",nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.MINISTATEMENT,date));
						modelMap.put("totalBalanceEnquiry",nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.BALANCEENQUIRY,date));
						modelMap.put("totalIncoming", smsLogApi.countIncomingSmsByDate(date));
						modelMap.put("totalOutgoing", smsLogApi.countOutgoingSmsByDate(date));
						List<MerchantServiceDTO> serviceList = serviceApi.findAllMerchantService();
						for(MerchantServiceDTO service : serviceList){
							service.setCount(transactionApi.countTransactionByServiceAndStatusAndDate(service.getUniqueIdentifier(),TransactionStatus.Complete,date));
						}
						modelMap.put("serviceList", serviceList);
						modelMap.put("overall", "passive");
						modelMap.put("daily", "active");
						return "/Main/branchLogin";
					} catch (Exception e) {
						modelMap.put("totalTransaction", 0);
						modelMap.put("totalCustomer", 0);
						modelMap.put("lastWeekCustomer", 0);
						modelMap.put("lastMonthCustomer", 0);
						modelMap.put("inactiveCustomer", 0);
						modelMap.put("totalCount", 0);
						modelMap.put("totalMiniStatement", 0);
						modelMap.put("totalBalanceEnquiry", 0);
						modelMap.put("totalIncoming", 0);
						modelMap.put("totalOutgoing", 0);
						modelMap.put("overall", "passive");
						modelMap.put("daily", "active");
						e.printStackTrace();
						logger.debug(e.getMessage());
						return "/Main/branchLogin";
					}
				} else if (authority.contains(Authorities.SUPER_AGENT)) {
					logger.debug("userlogin==>");
					return "/Main/userLogin";
				} else if (authority.contains(Authorities.CUSTOMER)) {
					logger.debug("userlogin==>");
					CustomerDTO customer = customerApi.getCustomerById(currentUser.getAssociatedId());
					modelMap.put("uuid", customer.getUniqueId());
					user = userApi.getUserWithId(currentUser.getId());
					if (user.isFirstLogin()) {
						modelMap.put("firstLogin", true);
						modelMap.put("username", user.getUserName());
					}
					return "/Main/userLogin";
				} else if (authority.contains(Authorities.AUTHENTICATED)) {
					logger.debug("userlogin==>");
					return "/Main/userLogin";
				} else if (authority.contains(Authorities.SENDER_CUSTOMER)) {
					logger.debug("userlogin==>");
					modelMap.put("isCustomer", true);
					return "/Main/userLogin";
				} else if (authority.contains(Authorities.BENEFICIARY_CUSTOMER)) {
					logger.debug("userlogin==>");
					modelMap.put("isCustomer", true);
					return "/Main/userLogin";
				}

			}
			else if (authority.contains(Authorities.CHANNELPARTNER)) {
				UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				if (user.isFirstLogin()) {
					modelMap.put("firstLogin", true);
					modelMap.put("username", user.getUserName());
				}
				try {
					modelMap.put("totalTransaction", transactionApi.countTransactionsByDate(date)+nonFinancialTransactionApi.countTransactionsByDate(date));
					modelMap.put("totalCustomer",customerApi.countCustomerByDate(date));
					modelMap.put("lastWeekCustomer", customerApi.countCustomerWithInLastWeek(currentUser.getAssociatedId()));
					modelMap.put("lastMonthCustomer", customerApi.countCustomerWithInLastMonth(currentUser.getAssociatedId()));
					modelMap.put("inactiveCustomer", customerApi.getCustomerWithoutTransactionByDate(currentUser.getAssociatedId(),date) != null ? customerApi.getCustomerWithoutTransactionByDate(currentUser.getAssociatedId(),date).size() : 0);
					modelMap.put("totalCount", smsLogApi.countSmsLogByDate(date));
					modelMap.put("totalMiniStatement", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.MINISTATEMENT,date));
					modelMap.put("totalBalanceEnquiry", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.BALANCEENQUIRY,date));
					List<MerchantServiceDTO> serviceList = serviceApi.findAllMerchantService();
					for(MerchantServiceDTO service : serviceList){
						service.setCount(transactionApi.countTransactionByServiceAndStatusAndDate(service.getUniqueIdentifier(),TransactionStatus.Complete,date));
					}
					modelMap.put("serviceList", serviceList);
					modelMap.put("overall", "passive");
					modelMap.put("daily", "active");
					return "/Main/channel_partner_login";
				} catch (Exception e) {
					modelMap.put("totalTransaction", 0);
					modelMap.put("totalCustomer", 0);
					modelMap.put("lastWeekCustomer", 0);
					modelMap.put("lastMonthCustomer", 0);
					modelMap.put("inactiveCustomer", 0);
					modelMap.put("totalCount", 0);
					modelMap.put("totalMiniStatement", 0);
					modelMap.put("totalBalanceEnquiry", 0);
					modelMap.put("overall", "passive");
					modelMap.put("daily", "active");
					e.printStackTrace();
					logger.debug(e.getMessage());
					return "/Main/channel_partner_login";
				}
			
			}
		}
		return "redirect:/main";
	}
	//end of the added part 
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/main")
	public String getMain(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "errormessage", required = false) String errormessage, HttpServletResponse response) {

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)) {
				return "redirect:/admin";
			}
		}
		modelMap.put("errormessage",
				errormessage == null ? "" : messageSource.getMessage(errormessage, null, Locale.ROOT));
		return "Main/mbank-web/index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String login(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "errormessage", required = false) String errormessage, HttpServletResponse response) {

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR)) {
				return "redirect:/admin";
			}
		}
		modelMap.put("errormessage",
				errormessage == null ? "" : messageSource.getMessage(errormessage, null, Locale.ROOT));
		return "Main/mbank-web/login";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/home")
	public String getLogin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam(value = "errormessage", required = false) String errormessage,
			@RequestParam(value = "infomessage", required = false) String infomessage,
			@RequestParam(value = "rsmsg", required = false) String rsmsg) {
		modelMap.put("showlogin", true);
		Cookie cookie = new Cookie("REM", "1234567");
		cookie.setMaxAge(2592000);
		response.addCookie(cookie);
		if (AuthenticationUtil.getCurrentUser() != null) {
			return "redirect:/";
		}
		return "home";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/businessSummaryReport")
	public ResponseEntity<RestResponseDTO> getBusinessSummaryReport(){
		RestResponseDTO response = new RestResponseDTO();
		User currentUser = AuthenticationUtil.getCurrentUser();
		HashMap<String, Object> businessSummary = new HashMap<>();
		HashMap<String, Object> activeRegistration = new HashMap<>();
		activeRegistration.put("tillDate", customerApi.countCustomer(currentUser.getId()));
		activeRegistration.put("thirtyDays", customerApi.countCustomerWithInLastMonth(0));
		activeRegistration.put("sevenDays", customerApi.countCustomerWithInLastWeek(0));
		activeRegistration.put("oneDay", customerApi.countCustomerByDate(DateUtil.getDateWith0Time()));
		HashMap<String, Object> transaction = new HashMap<>();
		HashMap<String, Object> transactionVolume = new HashMap<>();
		transactionVolume.put("tillDate", transactionApi.countTransactions());
		transactionVolume.put("thirtyDays", transactionApi.countTransactionsByDate(DateUtil.getDateWith0Time()));
		transactionVolume.put("sevenDays", transactionApi.countTransactionsByDate(DateUtil.getDateWith0Time()));
		transactionVolume.put("oneDay", transactionApi.countTransactionsByDate(DateUtil.getDateWith0Time()));
		HashMap<String, Object> transactionValue = new HashMap<>();
		HashMap<String, Object> revenue = new HashMap<>();
		transaction.put("transactionVolume", transactionVolume);
		double transactionAmount = 0.0;
		double commissionAmount = 0.0;
		List<TransactionDTO> transactionList = transactionApi.getAllTransactions();
		if(transactionList != null){
			for(TransactionDTO t : transactionList){
				transactionAmount+=t.getAmount();
				commissionAmount+=t.getCommissionAmount();
			}
		}
		revenue.put("tillDate", commissionAmount);
		transactionValue.put("tillDate",transactionAmount);
		transactionAmount = 0.0;
		commissionAmount = 0.0;
		transactionList = transactionApi.getTransactionBetweenDate(DateUtil.getWeekBackDate(), new Date());
		if(transactionList != null){
			for(TransactionDTO t : transactionList){
				transactionAmount+=t.getAmount();
				commissionAmount+=t.getCommissionAmount();
			}
		}
		revenue.put("sevenDays", commissionAmount);
		transactionValue.put("sevenDays",transactionAmount);
		transactionAmount = 0.0;
		commissionAmount = 0.0;
		transactionList = transactionApi.getTransactionBetweenDate(DateUtil.getMonthBackDate(), new Date());
		if(transactionList != null){
			for(TransactionDTO t : transactionList){
				transactionAmount+=t.getAmount();
				commissionAmount+=t.getCommissionAmount();
			}
		}
		revenue.put("thirtyDays", commissionAmount);
		transactionValue.put("thirtyDays",transactionAmount);
		transactionAmount = 0.0;
		commissionAmount = 0.0;
		transactionList =  transactionApi.getTransactionBetweenDate(DateUtil.getDateWith0Time(), new Date());
		if(transactionList != null){
			for(TransactionDTO t : transactionList){
				transactionAmount+=t.getAmount();
				commissionAmount+=t.getCommissionAmount();
			}
		}
		revenue.put("oneDay", commissionAmount);
		transactionValue.put("oneDay",transactionAmount);
		transaction.put("transactionValue", transactionValue);
		
		HashMap<String, Object> balanceCheck = new HashMap<>();
		balanceCheck.put("tillDate", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType.BALANCEENQUIRY));
		balanceCheck.put("thirtyDays", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.BALANCEENQUIRY, DateUtil.getMonthBackDate()));
		balanceCheck.put("sevenDays", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.BALANCEENQUIRY, DateUtil.getWeekBackDate()));
		balanceCheck.put("oneDay", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.BALANCEENQUIRY, DateUtil.getDateWith0Time()));
		HashMap<String, Object> miniStatement = new HashMap<>();
		miniStatement.put("tillDate", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionType(NonFinancialTransactionType.MINISTATEMENT));
		miniStatement.put("thirtyDays", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.MINISTATEMENT, DateUtil.getMonthBackDate()));
		miniStatement.put("sevenDays", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.MINISTATEMENT, DateUtil.getWeekBackDate()));
		miniStatement.put("oneDay", nonFinancialTransactionApi.countNonFinancialTransactionCountByTransactionTypeAndDate(NonFinancialTransactionType.MINISTATEMENT, DateUtil.getDateWith0Time()));
		HashMap<String, Object> fundTransfer = new HashMap<>();
		fundTransfer.put("tillDate", transactionApi.countByTransactionTypeAndDate(TransactionType.Transfer, null));
		fundTransfer.put("thirtyDays", transactionApi.countByTransactionTypeAndDate(TransactionType.Transfer, DateUtil.getMonthBackDate()));
		fundTransfer.put("sevenDays", transactionApi.countByTransactionTypeAndDate(TransactionType.Transfer, DateUtil.getWeekBackDate()));
		fundTransfer.put("oneDay", transactionApi.countByTransactionTypeAndDate(TransactionType.Transfer, DateUtil.getDateWith0Time()));
		HashMap<String, Object> chequeBook = new HashMap<>();
		chequeBook.put("tillDate", chequeRequestApi.countChequeBookRequest(null));
		chequeBook.put("thirtyDays", chequeRequestApi.countChequeBookRequest(DateUtil.getMonthBackDate()));
		chequeBook.put("sevenDays", chequeRequestApi.countChequeBookRequest(DateUtil.getWeekBackDate()));
		chequeBook.put("oneDay", chequeRequestApi.countChequeBookRequest(DateUtil.getDateWith0Time()));
		HashMap<String, Object> chequeBlock = new HashMap<>();
		chequeBlock.put("tillDate", chequeBlockApi.countChequeBlockRequest(null));
		chequeBlock.put("thirtyDays", chequeBlockApi.countChequeBlockRequest(DateUtil.getMonthBackDate()));
		chequeBlock.put("sevenDays", chequeBlockApi.countChequeBlockRequest(DateUtil.getWeekBackDate()));
		chequeBlock.put("oneDay", chequeBlockApi.countChequeBlockRequest(DateUtil.getDateWith0Time()));
		List<HashMap<String,Object>> serviceCategoryList = new ArrayList<>();
		for(ServiceCategoryDTO serviceCategory : serviceCategoryApi.getAllServiceCategory()){
			HashMap<String,Object> category = new HashMap<>();
			category.put("name", serviceCategory.getName());
			category.put("tillDate", transactionApi.getByServiceCategoryAndDate(serviceCategory.getId(),null).size());
			category.put("thirtyDays", transactionApi.getByServiceCategoryAndDate(serviceCategory.getId(),DateUtil.getMonthBackDate()).size());
			category.put("sevenDays", transactionApi.getByServiceCategoryAndDate(serviceCategory.getId(),DateUtil.getWeekBackDate()).size());
			category.put("oneDay", transactionApi.getByServiceCategoryAndDate(serviceCategory.getId(),DateUtil.getDateWith0Time()).size());
			serviceCategoryList.add(category);
		}
		
		businessSummary.put("date", new Date().toLocaleString());
		businessSummary.put("activeRegistration", activeRegistration);
		businessSummary.put("blockedRegistration", customerApi.countCustomerByStatus(currentUser.getId(), CustomerStatus.Blocked));
		businessSummary.put("transaction", transaction);
		businessSummary.put("balanceCheck", balanceCheck);
		businessSummary.put("miniStatement", miniStatement);
		businessSummary.put("fundTransfer", fundTransfer);
		businessSummary.put("chequeBook", chequeBook);
		businessSummary.put("chequeBlock", chequeBlock);
		businessSummary.put("serviceCategoryList", serviceCategoryList);
		businessSummary.put("revenue", revenue);
		
		response.setMessage("Success");
		response.setDetail(businessSummary);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
