package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.ICardlessBankApi;
import com.mobilebanking.api.IChannelPartnerApi;
import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.ICommissionApi;
import com.mobilebanking.api.IMerchantApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.api.IUserTemplateApi;
import com.mobilebanking.model.BankAccountSettingsDto;
import com.mobilebanking.model.BankCommissionDTO;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.BankMerchantAccountsDto;
import com.mobilebanking.model.ChannelPartnerDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.CommissionAmountDTO;
import com.mobilebanking.model.CommissionDTO;
import com.mobilebanking.model.CommissionDTOforBankCommission;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.ProfileAccountSettingDto;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.error.BankError;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.BankValidation;

@Controller
public class BankController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(BankController.class);

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private BankValidation bankValidation;

	@Autowired
	private IStateApi stateApi;

	@Autowired
	private ICityApi cityApi;

	@Autowired
	private IChannelPartnerApi channelPartnerApi;

	@Autowired
	private IMerchantApi merchantApi;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ICommissionApi commissionApi;

	@Autowired
	private IMerchantServiceApi merchantServiceApi;

	@Autowired
	private IUserTemplateApi userTemplateApi;

	@Autowired
	private ICardlessBankApi cardlessBankApi;

	@RequestMapping(method = RequestMethod.GET, value = "/addBank")
	public String addBank(ModelMap modelMap, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<StateDTO> statesList = stateApi.getAllState();
				List<ChannelPartnerDTO> channelPartnersList = channelPartnerApi.findAllChannelPartner();
				List<MerchantDTO> merchantList = merchantApi.getAllMerchant();
				modelMap.put("merchantList", merchantList);
				modelMap.put("channelPartners", channelPartnersList);
				modelMap.put("statesList", statesList);
				modelMap.put("userTemplateList", userTemplateApi.findUserTemplateByUserType(UserType.Bank));
				return "Bank/" + "addBank";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addBank")
	public String addBank(ModelMap modelMap, @ModelAttribute("bank") BankDTO bankDto, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (bankDto == null) {
					logger.debug("bank null");
					System.out.println("EMPTY ");
					return "redirect:/";
				} else {
					try {
						BankError bankError = new BankError();
						bankError = bankValidation.bankValidation(bankDto);
						logger.debug("valid" + bankError.isValid());
						if (bankError.isValid()) {
							bankDto.setCreatedById(AuthenticationUtil.getCurrentUser().getId());
							bankApi.saveBank(bankDto);

							redirectAttributes.addFlashAttribute("message", "bank.Added.Successfully");
							return "redirect:/listBank";
						} else {
							logger.debug("error while adding");
							modelMap.put("merchantList", merchantApi.getAllMerchant());
							modelMap.put("channelPartners", channelPartnerApi.findAllChannelPartner());
							modelMap.put("statesList", stateApi.getAllState());
							modelMap.put("userTemplateList", userTemplateApi.findUserTemplateByUserType(UserType.Bank));
							modelMap.put("error", bankError);
							modelMap.put("bank", bankDto);
							return "Bank/addBank";
						}

					} catch (Exception e) {
						logger.debug("exception" + e);
						e.printStackTrace();
						return "redirect:/";
					}
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listBank")
	public String listBank(ModelMap modelMap, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "swiftCode", required = false) String swiftCode,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.CHANNELPARTNER))
					&& authority.contains(Authorities.AUTHENTICATED)) {
				if (page == null || page == 0) {
					page = 1;
				}
				if (authority.contains(Authorities.ADMINISTRATOR)) {
					modelMap.put("pageData", bankApi.geteBanks(page, name, swiftCode, fromDate, toDate));

					modelMap.put("fromDate", fromDate);
					modelMap.put("toDate", toDate);
					modelMap.put("name", name);
					modelMap.put("swiftCode", swiftCode);
					return "/Bank/listBank";
				} else {
					List<BankDTO> bankDTOList = bankApi
							.getAllBankOfChannelPartner(AuthenticationUtil.getCurrentUser().getAssociatedId());
					modelMap.put("bankList", bankDTOList);
					return "/channelPartnerView/bank/listBank";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editBank")
	public String editBank(ModelMap modelMap, @RequestParam(value = "bankId", required = true) String bankId,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				BankDTO bank = bankApi.getBankDtoById(Long.parseLong(bankId));
				if (bank == null) {
					logger.debug("bank null");
					return "redirect:listBank";
				} else {
					modelMap.put("bank", bank);
					List<StateDTO> stateList = stateApi.getAllState();
					modelMap.put("stateList", stateList);
					List<CityDTO> cityList = cityApi.findCityByStateName(bank.getState());
					modelMap.put("cityList", cityList);
					List<MerchantDTO> merchantList = merchantApi.getAllMerchant();
					List<MerchantDTO> merchantListNotListedBank = new ArrayList<>();
					for (MerchantDTO merchant : merchantList) {
						boolean valid = true;
						for (MerchantDTO merchantBank : bank.getMerchant()) {
							if (merchant.getId() == merchantBank.getId()) {
								valid = false;
							}
						}
						if (valid) {
							merchantListNotListedBank.add(merchant);
						}
					}
					modelMap.put("merchantList", merchantListNotListedBank);

					return "Bank/editBank";

				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listcommission")
	public String listCommission(ModelMap modelMap, @RequestParam(value = "bank", required = true) String bankId,
			HttpServletRequest request, HttpServletResponse response) {

		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				BankDTO bank = bankApi.getBankDtoById(Long.parseLong(bankId));
				modelMap.put("bank", bank);
				if (bank == null) {
					logger.debug("bank null");
					return "redirect:listBank";
				} else {
					modelMap.put("bank", bank);
					List<CommissionDTO> cimmissionDtoList = commissionApi.getAllCommission();
					modelMap.put("commissionList", cimmissionDtoList);
					modelMap.put("bank", bankId);
					return "Commission/listBankCommission";

				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/commission/assign")
	public String assignCommission(ModelMap modelMap,
			@RequestParam(value = "commissionId", required = true) Long commissionId,
			@RequestParam(value = "bankId", required = true) Long bankId, HttpServletRequest request,
			HttpServletResponse response) {

		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				List<CommissionAmountDTO> commissionAmountList = commissionApi
						.getCommissionAmountByCommission(commissionId);
				modelMap.put("commissionAmountList", commissionAmountList);
				List<BankCommissionDTO> bankCommissionList = new ArrayList<>();
				for (CommissionAmountDTO commissionAmountDTO : commissionAmountList) {
					BankCommissionDTO bankCommission = commissionApi
							.getBankCommissionByCommissionAmountIdAndBank(commissionAmountDTO.getId(), bankId);
					if (bankCommission != null) {
						bankCommissionList.add(bankCommission);
					}
				}
				if (bankCommissionList.size() == 0) {
					modelMap.put("bankCommisssionEmpty", true);
				}
				modelMap.put("bankCommissionList", bankCommissionList);
				CommissionDTOforBankCommission commission = new CommissionDTOforBankCommission();
				commission.setMerchantId(commissionAmountList.get(0).getMerchantId());
				commission.setMerchantServiceId(commissionAmountList.get(0).getMerchantServiceId());
				MerchantDTO merchant = merchantApi.findMerchantById(commission.getMerchantId());
				commission.setMerchantName(merchant.getName());
				MerchantServiceDTO merchantService = merchantServiceApi
						.findMerchantServiceById(commission.getMerchantServiceId());
				commission.setServiceName(merchantService.getService());
				commission.setBankId(bankId);
				modelMap.put("commission", commission);
				return "Commission/addBankCommission";

			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/manageaccounts", method = RequestMethod.GET)
	public String manageBankAccounts(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("bank") String bankCode) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				modelMap.put("account", bankApi.getBankAccountsSettingsByBank(bankCode));
				modelMap.put("merchantAccountsList", bankApi.getbankMerchantAccountsByBank(bankCode));
				modelMap.put("bank", bankApi.getBankByCode(bankCode));
				return "Bank/accounts/list";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/accounts", method = RequestMethod.GET)
	public String account(HttpServletRequest request, ModelMap modelMap, @RequestParam("bank") String bank) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				modelMap.put("bankCode", bank);
				return "Bank/accounts/add";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/addaccounts", method = RequestMethod.POST)
	public String account(HttpServletRequest request, ModelMap modelMap,
			@ModelAttribute("bankAccounts") BankAccountSettingsDto bankAccounts) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				if (bankApi.getBankAccountsSettingsByBank(bankAccounts.getBank()) == null) {
					bankApi.saveBankAccountSetting(bankAccounts);
					return "redirect:/bank/manageaccounts?bank=" + bankAccounts.getBank();
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/editaccount", method = RequestMethod.GET)
	public String editAccount(HttpServletRequest request, ModelMap modelMap, @RequestParam("bank") String bankCode) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				BankDTO bank = bankApi.getBankByCode(bankCode);
				BankAccountSettingsDto accounts = bankApi.getBankAccountsSettingsByBank(bankCode);
				modelMap.put("accounts", accounts);
				modelMap.put("bank", bank);
				return "Bank/accounts/edit";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/editaccount", method = RequestMethod.POST)
	public String editAccount(HttpServletRequest request, ModelMap modelMap,
			@ModelAttribute("bankAccounts") BankAccountSettingsDto bankAccounts) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				bankApi.editBankAccountSetting(bankAccounts);
				return "redirect:/bank/manageaccounts?bank=" + bankAccounts.getBank();
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/merchantaccounts", method = RequestMethod.GET)
	public String merchantAccounts(HttpServletRequest request, ModelMap modelMap,
			@RequestParam("bank") String bankCode) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				BankDTO bank = bankApi.getBankByCode(bankCode);
				List<MerchantDTO> merchantList = bankApi.getMerchantWithoutMerchantAccountByBank(bankCode);
				modelMap.put("merchantList", merchantList);
				modelMap.put("bank", bank);
				return "Bank/accounts/addMerchantAccount";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/addmerchantaccounts", method = RequestMethod.POST)
	public String merchantAccounts(BankMerchantAccountsDto merchantAccount, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				bankApi.saveBankMerchantAccount(merchantAccount);
				return "redirect:/bank/manageaccounts?bank=" + merchantAccount.getBank();
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/editmerchantaccount", method = RequestMethod.GET)
	public String editMerchantAccount(HttpServletRequest request, ModelMap modelMap, @RequestParam("id") Long id) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				BankMerchantAccountsDto accounts = bankApi.getBankMerchantAccountsById(id);
				modelMap.put("accounts", accounts);
				return "Bank/accounts/editMerchantAccount";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/editmerchantaccounts", method = RequestMethod.POST)
	public String editMerchantAccount(HttpServletRequest request, ModelMap modelMap,
			BankMerchantAccountsDto merchantAccounts) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
				merchantAccounts = bankApi.editBankMerchantAccount(merchantAccounts);
				return "redirect:/bank/manageaccounts?bank=" + merchantAccounts.getBank();
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/profileaccounts", method = RequestMethod.GET)
	public String profileAccounts(HttpServletRequest request, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				modelMap.put("account", bankApi
						.getProfileAccountsSettingsByBank(AuthenticationUtil.getCurrentUser().getAssociatedId()));
				modelMap.put("bank", bankApi.getBankById(AuthenticationUtil.getCurrentUser().getAssociatedId()));
				return "Bank/accounts/profileAccounts";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/addprofileaccounts", method = RequestMethod.POST)
	public String profileAccount(HttpServletRequest request, ModelMap modelMap,
			@ModelAttribute("bankAccounts") ProfileAccountSettingDto profileAccounts) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				if (bankApi.getProfileAccountsSettingsByBank(
						AuthenticationUtil.getCurrentUser().getAssociatedId()) == null) {
					bankApi.saveBankProfileAccountSetting(profileAccounts,
							AuthenticationUtil.getCurrentUser().getAssociatedId());
					return "redirect:/bank/profileaccounts";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/addprofileaccounts", method = RequestMethod.GET)
	public String addProfileAccounts(HttpServletRequest request, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				return "Bank/accounts/addProfileAccounts";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/editprofileaccounts", method = RequestMethod.GET)
	public String editProfileAccounts(HttpServletRequest request, ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				ProfileAccountSettingDto accounts = bankApi
						.getProfileAccountsSettingsByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
				modelMap.put("accounts", accounts);
				// modelMap.put("bank", bank);
				return "Bank/accounts/editProfileAccount";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/bank/editprofileaccounts", method = RequestMethod.POST)
	public String editProfileAccount(HttpServletRequest request, ModelMap modelMap,
			@ModelAttribute("bankAccounts") ProfileAccountSettingDto bankAccounts) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
				bankApi.editProfileAccountSetting(bankAccounts, AuthenticationUtil.getCurrentUser().getAssociatedId());
				return "redirect:/bank/profileaccounts";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/payment")
	public String makePayment(HttpServletRequest request, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)) {

			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editBank")
	public String editBank(ModelMap modelMap, @ModelAttribute("bank") BankDTO bankDTO, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.debug("at edit bank");
		if (AuthenticationUtil.getCurrentUser() != null) {
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (bankDTO == null) {
					logger.debug("null at bank");
					return "redirect:/Static/500";
				} else {
					try {
						BankError bankError = new BankError();
						bankError = bankValidation.editValidation(bankDTO);
						if (bankError.isValid()) {
							logger.debug("error valid=" + bankError.isValid());
							bankApi.editBank(bankDTO);

							redirectAttributes.addFlashAttribute("message", "bank.edited.successfully");
							return "redirect:/listBank";
						} else {
							logger.debug("error during edit");
							modelMap.put("error", bankError);
							modelMap.put("bank", bankDTO);
							return "Bank/editBank";
						}
					} catch (Exception e) {
						redirectAttributes.addFlashAttribute("message", "Something went wrong. Please try again later");
						BankDTO bank = bankApi.getBankDtoById(bankDTO.getId());
						modelMap.put("bank", bank);
						List<StateDTO> stateList = stateApi.getAllState();
						modelMap.put("stateList", stateList);
						List<CityDTO> cityList = cityApi.findCityByStateName(bank.getState());
						modelMap.put("cityList", cityList);
						List<MerchantDTO> merchantList = merchantApi.getAllMerchant();
						List<MerchantDTO> merchantListNotListedBank = new ArrayList<>();
						for (MerchantDTO merchant : merchantList) {
							boolean valid = true;
							for (MerchantDTO merchantBank : bank.getMerchant()) {
								if (merchant.getId() == merchantBank.getId()) {
									valid = false;
								}
							}
							if (valid) {
								merchantListNotListedBank.add(merchant);
							}
						}
						modelMap.put("merchantList", merchantListNotListedBank);

						return "Bank/editBank";
					}
				}
			}
		}
		return "redirect:/";
	}

//	@RequestMapping(value = "/addSmsCount", method = RequestMethod.POST)
//	public ResponseEntity<RestResponseDTO> updateUser(HttpServletRequest request, @ModelAttribute("bankId") Long bankId,
//			@ModelAttribute("smsCount") Integer smsCount, ModelMap modelMap, RedirectAttributes redirectAttributes) {
//
//		RestResponseDTO restResponseDto = new RestResponseDTO();
//
//		if (AuthenticationUtil.getCurrentUser() != null) {
//			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
//
//			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
//					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
//
//				try {
//
//					boolean updateSmsCount = bankApi.updateSmsCount(bankId, smsCount);
//					if (updateSmsCount) {
//						restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED);
//					} else {
//						restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR);
//						return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR);
//					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
//				}
//			}
//		}
//		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
//	}

//	@RequestMapping(value = "/addLicenseCount", method = RequestMethod.POST)
//	public ResponseEntity<RestResponseDTO> addLicenseCount(HttpServletRequest request,
//			@ModelAttribute("bankId") Long bankId, @ModelAttribute("licenseCount") Integer licenseCount,@ModelAttribute("remarks") String remarks,
//			ModelMap modelMap, RedirectAttributes redirectAttributes) {
//
//		RestResponseDTO restResponseDto = new RestResponseDTO();
//
//		if (AuthenticationUtil.getCurrentUser() != null) {
//			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
//
//			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
//					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
//
//				try {
//
//					boolean updateSmsCount = bankApi.addLicenseCount(bankId, licenseCount);
//					boolean addRemarks = bankApi.addRemarks(bankId, remarks);
//
//					if (updateSmsCount || addRemarks) {
//						restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED);
//					} else {
//						restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR);
//						return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR);
//					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
//				}
//			}
//		}
//		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
//	}

	@RequestMapping(value = "/getAllBank", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getBank() {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
				try {
					restResponseDto.setDetail(bankApi.getAllBank());
					restResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
					restResponseDto.setMessage(StringConstants.DATA_READ);
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR);
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/bank/checkcbsstatus", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> checkBankStatus(@RequestParam("bank") Long bankId) {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
				if (bankApi.checkBankCbsStatus(bankId)) {
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
				} else {
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.REQUEST_TIMEOUT);
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/bank/inactivecbs", method = RequestMethod.GET)
	public ResponseEntity<List<BankDTO>> getInactiveBank() {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
				List<BankDTO> bankList = bankApi.getBankByCbsStatus(Status.Inactive);
				return new ResponseEntity<List<BankDTO>>(bankList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	private RestResponseDTO getResponseDto(ResponseStatus status, String message) {
		RestResponseDTO restResponseDto = new RestResponseDTO();

		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);

		return restResponseDto;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;

	}
}
