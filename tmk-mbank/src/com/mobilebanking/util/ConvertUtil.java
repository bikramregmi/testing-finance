package com.mobilebanking.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IUserApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.BankOAuthClient;
import com.mobilebanking.entity.ChannelPartner;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.Commission;
import com.mobilebanking.entity.CommissionAmount;
import com.mobilebanking.entity.Compliance;
import com.mobilebanking.entity.Country;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.CustomerLog;
import com.mobilebanking.entity.CustomerProfile;
import com.mobilebanking.entity.Ledger;
import com.mobilebanking.entity.Menu;
import com.mobilebanking.entity.MenuTemplate;
import com.mobilebanking.entity.Merchant;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.entity.NonFinancialTransaction;
import com.mobilebanking.entity.SettlementLog;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.SmsMode;
import com.mobilebanking.entity.State;
import com.mobilebanking.entity.TransactionAlert;
import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserSession;
import com.mobilebanking.entity.UserTemplate;
import com.mobilebanking.entity.WebService;
import com.mobilebanking.model.AccountDTO;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.ChannelPartnerDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.CommissionAmountDTO;
import com.mobilebanking.model.CommissionDTO;
import com.mobilebanking.model.ComplianceDTO;
import com.mobilebanking.model.CountryDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerLogDto;
import com.mobilebanking.model.CustomerProfileDTO;
import com.mobilebanking.model.LedgerDTO;
import com.mobilebanking.model.LedgerType;
import com.mobilebanking.model.MenuDTO;
import com.mobilebanking.model.MenuTemplateDTO;
import com.mobilebanking.model.MenuType;
import com.mobilebanking.model.MerchantDTO;
import com.mobilebanking.model.MerchantManagerDTO;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.NonFinancialTransactionDTO;
import com.mobilebanking.model.SettlementLogDTO;
import com.mobilebanking.model.SmsLogDTO;
import com.mobilebanking.model.SmsModeDto;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.TransactionAlertDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserSessionDTO;
import com.mobilebanking.model.UserTemplateDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.WebServiceDTO;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.OAuthAccessTokenRepository;

@Component
public class ConvertUtil {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BankOAuthClientRepository bankAuthRepository;

	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private OAuthAccessTokenRepository oAuthAccessTokenRepository;

	@Autowired
	private BankRepository bankRepository;

	private ConvertUtil() {

	}

	public static HashMap<String, String> mapToHashMapString(Map<String, String> mymap) {
		HashMap<String, String> hashRequest = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : mymap.entrySet()) {
			hashRequest.put(entry.getKey(), entry.getValue());
		}
		return hashRequest;
	}

	public static List<SmsModeDto> convertSmsModeToDtoList(List<SmsMode> smsModeList) {
		List<SmsModeDto> smsModeDtoList = new ArrayList<SmsModeDto>();
		for (SmsMode sm : smsModeList) {
			smsModeDtoList.add(convertSmsModeToDto(sm));
		}
		return smsModeDtoList;
	}

	public static SmsModeDto convertSmsModeToDto(SmsMode smsMode) {
		SmsModeDto smsModeDto = new SmsModeDto();
		smsModeDto.setId(smsMode.getId());
		smsModeDto.setMessage(smsMode.getMessage());

		smsModeDto.setBank(smsMode.getBank().getName());
		smsModeDto.setCreatedBy(smsMode.getCreatedBy().getUserName());
		smsModeDto.setSmsType(smsMode.getSmsType());
		smsModeDto.setStatus(smsMode.getStatus());
		return smsModeDto;
	}

	public static List<MerchantServiceDTO> convertMerchantServiceToDto(List<MerchantService> merchantServiceList) {
		List<MerchantServiceDTO> merchantServiceDtoList = new ArrayList<MerchantServiceDTO>();
		for (MerchantService mService : merchantServiceList) {
			merchantServiceDtoList.add(convertMerchantService(mService));
		}
		return merchantServiceDtoList;
	}

	public static MerchantServiceDTO convertMerchantService(MerchantService merchantService) {
		MerchantServiceDTO merchantServiceDto = new MerchantServiceDTO();
		merchantServiceDto.setId(merchantService.getId());
		/*
		 * merchantServiceDto.setMerchant(merchantService.getMerchant().getName(
		 * ));
		 */
		merchantServiceDto.setService(merchantService.getService());
		merchantServiceDto.setUniqueIdentifier(merchantService.getUniqueIdentifier());
		merchantServiceDto.setUrl(merchantService.getUrl());
		merchantServiceDto.setStatus(merchantService.getStatus());
		merchantServiceDto.setLabelName(merchantService.getLabelName());
		merchantServiceDto.setLabelMaxLength(merchantService.getLabelMaxLength());
		merchantServiceDto.setLabelMinLength(merchantService.getLabelMinLength());
		merchantServiceDto.setLabelPrefix(merchantService.getLabelPrefix());
		merchantServiceDto.setLabelSample(merchantService.getLabelSample());
		merchantServiceDto.setInstructions(merchantService.getInstructions());
		merchantServiceDto.setPriceInput(merchantService.isPriceInput());
		merchantServiceDto.setNotificationUrl(merchantService.getNotificationUrl());
		merchantServiceDto.setMinValue(merchantService.getMinValue());
		merchantServiceDto.setMaxValue(merchantService.getMaximumValue());
		merchantServiceDto.setServiceCategoryName(merchantService.getServiceCatagory().getName());
		merchantServiceDto.setCategoryId(merchantService.getServiceCatagory().getId());
		if (merchantService.getWebView() != null) {
			merchantServiceDto.setWebView(merchantService.getWebView());
		} else {
			merchantServiceDto.setWebView(false);
		}
		if (merchantService.isPriceInput()) {
			merchantServiceDto.setPriceRange(merchantService.getPriceRange());
		}
		merchantServiceDto.setFixedlabelSize(merchantService.isFixedlabelSize());
		if (merchantService.isFixedlabelSize()) {
			merchantServiceDto.setLabelSize(merchantService.getLabelFizedSize());

		} else {
			merchantServiceDto.setLabelMaxLength(merchantService.getLabelMaxLength());
			merchantServiceDto.setLabelMinLength(merchantService.getLabelMinLength());
		}
		if (merchantService.getDocuments() != null)
			merchantServiceDto.setIcon(merchantService.getDocuments().getIdentifier() + ".png");

		return merchantServiceDto;
	}

	public static List<CommissionDTO> convertCommissionToDto(List<Commission> commissionList) {
		List<CommissionDTO> commissionDtoList = new ArrayList<CommissionDTO>();
		for (Commission c : commissionList) {
			commissionDtoList.add(convertCommission(c));
		}
		return commissionDtoList;
	}

	public static CommissionDTO convertCommission(Commission commission) {
		CommissionDTO commissionDto = new CommissionDTO();
		// commissionDto.setId(commission.getId());
		// commissionDto.setBank(commission.getBank().getName());
		// commissionDto.setMerchant(commission.getMerchant().getName());
		// commissionDto.setMerchantService(commission.getService().getService());
		// commissionDto.setStatus(commission.getStatus());
		// //commissionDto.setCommissionAmount(convertCommissionAmountToDtoList(commission.getCommissionAmount()));
		// commissionDto.setSameForAll(commission.isSameForAll());
		return commissionDto;
	}

	public static List<CommissionAmountDTO> convertCommissionAmountToDtoList(
			List<CommissionAmount> commissionAmountList) {
		List<CommissionAmountDTO> commissionAmountDto = new ArrayList<CommissionAmountDTO>();
		for (CommissionAmount ca : commissionAmountList) {
			commissionAmountDto.add(convertCommissionAmountDto(ca));
		}
		return commissionAmountDto;
	}

	public static CommissionAmountDTO convertCommissionAmountDto(CommissionAmount commissionAmount) {
		CommissionAmountDTO commissionAmountDto = new CommissionAmountDTO();
		commissionAmountDto.setId(commissionAmount.getId());
		commissionAmountDto.setFromAmount(commissionAmount.getFromAmount());
		commissionAmountDto.setToAmount(commissionAmount.getToAmount());
		commissionAmountDto.setCommissionFlat(commissionAmount.getCommissionFlat());
		commissionAmountDto.setCommissionPercentage(commissionAmount.getCommissionPercentage());
		commissionAmountDto.setStatus(commissionAmount.getStatus());
		return commissionAmountDto;
	}

	public static List<MerchantDTO> convertMerchantToDTO(List<Merchant> merchant) {
		List<MerchantDTO> merchantDto = new ArrayList<MerchantDTO>();
		for (Merchant merchantList : merchant) {
			merchantDto.add(convertMerchant(merchantList));
		}
		return merchantDto;
	}

	public static MerchantDTO convertMerchant(Merchant merchant) {

		MerchantDTO merchantDto = new MerchantDTO();

		if (merchant != null) {
			merchantDto.setId(merchant.getId());
			if (merchant.getCity() != null) {
				merchantDto.setCity(merchant.getCity().getName());
				merchantDto.setState(merchant.getCity().getState().getName());
			}
			merchantDto.setDescription(merchant.getDescription());
			merchantDto.setLandLine(merchant.getLandLine());
			merchantDto.setName(merchant.getName());
			merchantDto.setMobileNumber(merchant.getMobileNumber());
			;
			merchantDto.setOwnerName(merchant.getOwnerName());
			merchantDto.setRegistrationNumber(merchant.getRegistrationNumber());
			merchantDto.setVatNumber(merchant.getVatNumber());
			merchantDto.setStatus(merchant.getStatus());
			merchantDto.setAddress(merchant.getAddress());
			merchantDto.setApiUrl(merchant.getMerchantApiUrl());
			merchantDto.setApiUsername(merchant.getApiUsername());
			merchantDto.setApiPassword(merchant.getApiPassWord());
			merchantDto.setExtraField1(merchant.getExtraFieldOne());
			merchantDto.setExtraField2(merchant.getExtraFieldTwo());
			merchantDto.setExtraField3(merchant.getExtraFieldThree());
		}
		return merchantDto;
	}

	public static List<CustomerBankAccountDTO> convertCustomerBankAccountToDto(
			List<CustomerBankAccount> customerBankAccounts) {
		List<CustomerBankAccountDTO> customerBankAccountDto = new ArrayList<CustomerBankAccountDTO>();
		for (CustomerBankAccount ca : customerBankAccounts) {
			customerBankAccountDto.add(convertCustomerBankAccount(ca));
		}

		return customerBankAccountDto;
	}

	public static CustomerBankAccountDTO convertCustomerBankAccount(CustomerBankAccount customerBankAccount) {
		CustomerBankAccountDTO customerBankAccountDto = new CustomerBankAccountDTO();
		customerBankAccountDto.setId(customerBankAccount.getId());
		customerBankAccountDto.setBranch(customerBankAccount.getBranch().getName());
		customerBankAccountDto.setBranchCode(customerBankAccount.getBranch().getBranchCode());
		customerBankAccountDto.setBank(customerBankAccount.getBranch().getBank().getName());
		customerBankAccountDto.setAccountNumber(customerBankAccount.getAccountNumber());
		customerBankAccountDto.setCustomer(customerBankAccount.getCustomer().getFirstName() + " "
				+ customerBankAccount.getCustomer().getLastName());
		customerBankAccountDto.setAccountMode(customerBankAccount.getAccountMode());
		customerBankAccountDto.setStatus(customerBankAccount.getStatus());
		customerBankAccountDto.setBranchId(customerBankAccount.getBranch().getId());
		customerBankAccountDto.setBankId(customerBankAccount.getBranch().getBank().getId());

		return customerBankAccountDto;
	}

	public static List<ChannelPartnerDTO> convertChannelPartnerListToDTO(List<ChannelPartner> channelPartners) {
		List<ChannelPartnerDTO> channelPartnerDtoList = new ArrayList<ChannelPartnerDTO>();
		if (!channelPartners.isEmpty()) {
			for (ChannelPartner chPartner : channelPartners) {
				channelPartnerDtoList.add(convertChannelPartner(chPartner));
			}
		}
		return channelPartnerDtoList;
	}

	public static ChannelPartnerDTO convertChannelPartner(ChannelPartner channelPartner) {
		ChannelPartnerDTO channelPartnerDto = new ChannelPartnerDTO();

		channelPartnerDto.setId(channelPartner.getId());
		channelPartnerDto.setName(channelPartner.getName());
		channelPartnerDto.setOwner(channelPartner.getOwner());
		channelPartnerDto.setAddress(channelPartner.getAddress());
		channelPartnerDto.setStatus(channelPartner.getStatus());
		channelPartnerDto.setCity(channelPartner.getCity().getName());
		channelPartnerDto.setState(channelPartner.getCity().getState().getName());
		channelPartnerDto.setUniqueCode(channelPartner.getUniqueCode());
		return channelPartnerDto;
	}

	public static List<AccountDTO> convertAccountList(List<Account> account) {
		List<AccountDTO> dtoList = new ArrayList<AccountDTO>();
		if (account != null) {
			for (Account acc : account) {
				dtoList.add(convertAccount(acc));
			}
		}
		return dtoList;
	}

	public static AccountDTO convertAccount(Account account) {
		AccountDTO dto = new AccountDTO();
		if (account != null) {
			dto.setAccountNumber(account.getAccountNumber());
			dto.setBalance(account.getBalance());
			dto.setAccountId(account.getId());
			// dto.setUser(account.getUser());
			/* dto.setAssociatedId(account.getAssociatedId()); */
		}
		return dto;
	}

	public List<LedgerDTO> convertLedgerListToDTO(List<Ledger> txnList) {
		List<LedgerDTO> dtoList = new ArrayList<LedgerDTO>();
		for (Ledger txn : txnList) {
			dtoList.add(convertLedgerToDTO(txn));
		}
		return dtoList;
	}

	public LedgerDTO convertLedgerToDTO(Ledger txn) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LedgerDTO dto = new LedgerDTO();
		try {
			if (txn != null) {

				dto.setAmount(txn.getAmount());
				dto.setFromBalance(txn.getFromBalance());
				dto.setRemarks(txn.getRemarks());
				dto.setToBalance(txn.getToBalance());
				dto.setTransactionStatus(txn.getStatus());
				dto.setTransactionType(txn.getType());
				dto.setUploadedBy(txn.getUploadedBy() != null ? txn.getUploadedBy().getUserName() : "");
				if (txn.getFromAccount() != null) {
					dto.setFromAccount(txn.getFromAccount().getAccountNumber());
				}
				if (txn.getToAccount() != null) {
					dto.setToAccount(txn.getToAccount().getAccountNumber());
				}
				dto.setDate(dateFormatter.format(txn.getCreated()));
				dto.setParentId(txn.getParentId());
				if (txn.getType() == LedgerType.LOADFUND || txn.getType() == LedgerType.DECREASE_BALANCE
						|| txn.getType() == LedgerType.UPDATE_CREDIT_LIMIT) {
					Bank bank = bankRepository.findByAccountNumber(txn.getToAccount().getAccountNumber());
					if (bank != null)
						dto.setBank(bank.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public static String createHash(String key, String password, String hash) throws ClientException {
		try {
			byte[] decodedKey = (key).getBytes();
			SecretKeySpec keySpec = new SecretKeySpec(decodedKey, hash);
			Mac mac = Mac.getInstance(hash);
			mac.init(keySpec);
			byte[] dataBytes = password.getBytes("UTF-8");
			byte[] signatureBytes = mac.doFinal(dataBytes);
			String encoded = new String(Base64.encodeBase64(signatureBytes), "UTF-8");
			System.out.println("Prepared Encoded Signature :" + encoded);
			return encoded;
		} catch (Exception e) {
			throw new ClientException("Service Down !!!");
		}
	}

	public static List<UserSessionDTO> convertSessionList(List<UserSession> userSession) {
		List<UserSessionDTO> dtoList = new ArrayList<UserSessionDTO>();
		for (UserSession session : userSession) {
			dtoList.add(convertSession(session));
		}
		return dtoList;
	}

	public static List<UserTemplateDTO> convertUserTemplateToDto(List<UserTemplate> userTemplate) {
		List<UserTemplateDTO> dtoList = new ArrayList<UserTemplateDTO>();
		for (UserTemplate ut : userTemplate) {
			dtoList.add(convertUserTemplate(ut));
		}
		return dtoList;
	}

	public static UserSessionDTO convertSession(UserSession session) {
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
		UserSessionDTO dto = new UserSessionDTO();
		dto.setUsername(session.getUser().getUserName());
		dto.setId(session.getId());
		dto.setSessionId(session.getSessionId());
		dto.setUserId(session.getUser().getId());
		dto.setUserType(session.getUser().getUserType());
		dto.setLastRequest(time.format(session.getLastRequest()));
		return dto;
	}

	public static UserTemplateDTO convertUserTemplate(UserTemplate template) {

		UserTemplateDTO dto = new UserTemplateDTO();
		dto.setId(template.getId());
		dto.setUserTemplateName(template.getUserTemplateName());
		if (template.getMenuTemplate() != null) {
			dto.setMenuTemplateDTO(ConvertUtil.convertMenuTemplate(template.getMenuTemplate()));
		}
		return dto;
	}

	public List<CustomerDTO> convertCustomerToDto(List<Customer> customerList) {
		List<CustomerDTO> customerDTO = new ArrayList<CustomerDTO>();
		if (customerList != null) {
			for (Customer customer : customerList) {
				customerDTO.add(convertCustomer(customer));
			}
		}
		return customerDTO;
	}

	public CustomerDTO convertCustomer(Customer customer) {

		CustomerDTO customerDto = new CustomerDTO();
		BankOAuthClient bankAouthClient = bankAuthRepository.findByBank(customer.getBank());
		String clientID;
		if (bankAouthClient != null) {
			clientID = bankAouthClient.getoAuthClientId();
			if (customer.getUser() != null) {
				String token = userApi.getUserToken(customer.getUser().getUserName(), clientID);
				if (token != null) {
					byte[] encodedBytes = Base64.encodeBase64(token.getBytes());
					customerDto.setDeviceToken(new String(encodedBytes));
				}
			}
		}
		customerDto.setId(customer.getId());
		customerDto.setFirstName(customer.getFirstName());
		customerDto.setMiddleName(customer.getMiddleName());
		customerDto.setLastName(customer.getLastName());
		customerDto.setAddressOne(customer.getAddress());
		customerDto.setAddressTwo(customer.getAddressTwo());
		customerDto.setAddressThree(customer.getAddressThree());
		if (customer.getBank() != null) {
			customerDto.setBank(customer.getBank().getName());
			customerDto.setBankCode(customer.getBank().getSwiftCode());
		}
		if (customer.getBankBranch() != null) {
			customerDto.setBankBranch(customer.getBankBranch().getName());
			customerDto.setBankBranchCode(customer.getBankBranch().getBranchCode());
		}

		if (customer.getCity() != null) {
			customerDto.setCity(customer.getCity().getName());
			customerDto.setState(customer.getCity().getState().getName());
		} else {
			customerDto.setCity("N/A");
			customerDto.setState("N/A");
		}

		customerDto.setEmail(customer.getEmail());

		customerDto.setMobileNumber(customer.getMobileNumber());
		customerDto.setStatus(customer.getStatus());
		customerDto.setUniqueId(customer.getUniqueId());
		if (customer.getStatus() != null) {
			customerDto.setStatus(customer.getStatus());
		}
		if (customer.getMiddleName() == null || customer.getMiddleName().isEmpty()) {
			customerDto.setFullName(customer.getFirstName() + " " + customer.getLastName());
		} else {
			customerDto.setFullName(
					customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getLastName());
		}

		customerDto.setAppVerification(customer.isAppVerification());
		customerDto.setAppService(customer.isAppService());
		customerDto.setSmsService(customer.isSmsService());
		customerDto.setCreated(DateUtil.convertDateToString(customer.getCreated()));
		if (customer.getApprovalMessage() != null) {
			customerDto.setApprovalMessage(customer.getApprovalMessage());
		}

		if (customer.getComment() != null) {
			customerDto.setComment(customer.getComment());
		} else {
			customerDto.setComment("N/A");
		}

		if (customer.getCommentedby() != null) {
			customerDto.setCommentedBy(customer.getCommentedby());
		} else {
			customerDto.setCommentedBy("N/A");
		}
		if (customer.getLastModified() != null) {
			customerDto.setLastModified(DateUtil.convertDateToString(customer.getLastModified()));
		} else {
			customerDto.setLastModified("N/A");
		}
		List<HashMap<String, String>> accountDetail = new ArrayList<>();
		for (CustomerBankAccount bankAccount : customerBankAccountRepository
				.findCustomerBankAccountOfCustomer(customer.getUniqueId())) {
			HashMap<String, String> account = new HashMap<>();
			account.put("accountNumber", bankAccount.getAccountNumber());
			account.put("accountType", bankAccount.getAccountMode().toString());
			accountDetail.add(account);
		}
		customerDto.setAccountDetail(accountDetail);
		customerDto.setOauthTokenCount(oAuthAccessTokenRepository.countByUsername(customer.getUser().getUserName()));
		if (customer.getUser().getDeviceToken() != null && !(customer.getUser().getDeviceToken().trim().equals("")))
			customerDto.setFirebaseToken(true);
		else
			customerDto.setFirebaseToken(false);
		customerDto.setUserStatus(customer.getUser().getStatus());
		if (customer.getLastRenewed() != null) {
			customerDto.setDaysAfterRenew(DateUtil.countDays(customer.getLastRenewed(), new Date()));
			customerDto.setLastRenewDate(DateUtil.convertDateToString(customer.getLastRenewed()));
		} else {
			customerDto.setLastRenewDate("N/A");
		}
		if (customer.getExpiredDate() != null) {
			customerDto.setExpiredDate(DateUtil.convertDateToString(customer.getExpiredDate()));
		} else {
			customerDto.setExpiredDate("N/A");
		}
		return customerDto;
	}

	public static List<UserDTO> ConvertUserToDTO(List<User> user) {
		List<UserDTO> userDTO = new ArrayList<UserDTO>();
		for (User u : user) {
			if (!(u.getUserName().equals("admin")) && !(u.getUserName().equals("sysadmin"))) {
				userDTO.add(convertUser(u));
			}
		}
		return userDTO;
	}

	public static UserDTO convertUser(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setAddress(user.getAddress());
		dto.setEmail(user.getEmail());
		dto.setUserName(user.getUserName());
		dto.setAuthority(user.getAuthority());
		dto.setUserType(user.getUserType());
		dto.setStatus(user.getStatus());
		dto.setSecret_key(user.getSecretKey());
		dto.setAssociatedId(user.getAssociatedId());
		if (user.getCity() != null) {
			dto.setCity(user.getCity().getName());
			dto.setState(user.getCity().getState().getName());
		}
		dto.setFirstLogin(user.isFirstLogin());
		if (user.getUserTemplate() != null) {
			dto.setUserTemplateDTO(ConvertUtil.convertUserTemplate(user.getUserTemplate()));
		}
		if (user.getWebService() != null) {
			dto.setWeb_service(convertWebService(user.getWebService()));
		}

		if (user.getUserType().equals(UserType.BankBranch) || user.getUserType().equals(UserType.Bank)) {
			if (user.getMaker() != null)
				dto.setMaker(user.getMaker());
			if (user.getChecker() != null)
				dto.setChecker(user.getChecker());
		}

		return dto;
	}

	public static <E> List<E> convertIterableToList(Iterable<E> iter) {
		List<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

	public static List<CountryDTO> convertCountrytoCountryDtoList(List<Country> country) {
		List<CountryDTO> countryDTO = new ArrayList<CountryDTO>();
		for (Country countryList : country) {
			countryDTO.add(convertToCountryDto(countryList));
		}
		return countryDTO;
	}

	public static CountryDTO convertToCountryDto(Country country) {
		CountryDTO countryDTO = new CountryDTO();

		countryDTO.setId(country.getId());
		countryDTO.setName(country.getName());
		countryDTO.setIsoTwo(country.getIsoTwo());
		countryDTO.setIsoThree(country.getIsoThree());
		countryDTO.setDialCode(country.getDialCode());
		countryDTO.setStatus(country.getStatus().getValue());

		return countryDTO;
	}

	public static City convertDtoToCity(CityDTO cityDto) {
		City city = new City();
		city.setName(cityDto.getName());

		return city;

	}

	public static List<StateDTO> convertStateDtoToState(List<State> state) {
		List<StateDTO> stateDTO = new ArrayList<StateDTO>();
		for (State stateList : state) {
			stateDTO.add(convertState(stateList));
		}
		return stateDTO;
	}

	public static StateDTO convertState(State state) {
		StateDTO stateDTO = new StateDTO();
		stateDTO.setId(state.getId());
		stateDTO.setName(state.getName());
		stateDTO.setStatus(state.getStatus());
		return stateDTO;
	}

	public static StateDTO convertStateDto(State state) {
		StateDTO stateDTO = new StateDTO();
		stateDTO.setId(state.getId());
		stateDTO.setName(state.getName());
		stateDTO.setStatus(Status.Active);
		return stateDTO;
	}

	public static State convertDtoToState(StateDTO stateDto, State state) {
		state.setName(stateDto.getName());
		state.setStatus(stateDto.getStatus());

		return state;
	}

	public static State convertDtoToState(StateDTO stateDto) {
		State state = new State();
		state.setName(stateDto.getName());
		state.setStatus(Status.Active);
		return state;
	}

	public static List<CityDTO> convertCityDtoToCity(List<City> city) {
		List<CityDTO> cityDTO = new ArrayList<CityDTO>();
		for (City cityList : city) {
			cityDTO.add(convertCity(cityList));
		}
		return cityDTO;
	}

	public static CityDTO convertCity(City city) {
		CityDTO cityDTO = new CityDTO();
		cityDTO.setId(city.getId());
		cityDTO.setName(city.getName());
		cityDTO.setState(city.getState().getName());
		cityDTO.setStatus(city.getStatus());
		return cityDTO;
	}

	public static List<ComplianceDTO> convertComplianceDtoToCompliance(List<Compliance> compliance) {
		List<ComplianceDTO> complianceDTO = new ArrayList<ComplianceDTO>();
		for (Compliance complianceList : compliance) {
			complianceDTO.add(convertCompliance(complianceList));
		}
		return complianceDTO;
	}

	public static ComplianceDTO convertCompliance(Compliance compliance) {
		ComplianceDTO complianceDTO = new ComplianceDTO();
		complianceDTO.setId(compliance.getId());
		complianceDTO.setCountry(compliance.getCountry().getName());
		complianceDTO.setAmount(compliance.getAmount());
		complianceDTO.setDays(compliance.getDays());
		complianceDTO.setRequirements(compliance.getRequirements());
		return complianceDTO;
	}

	public static List<BankBranchDTO> convertBankBranchToDto(List<BankBranch> bankBranchList) {
		List<BankBranchDTO> bankBranchDto = new ArrayList<BankBranchDTO>();
		for (BankBranch bBranch : bankBranchList) {
			bankBranchDto.add(convertBankBranch(bBranch));
		}
		return bankBranchDto;
	}

	public static BankBranchDTO convertBankBranch(BankBranch bankBranch) {
		BankBranchDTO bankBranchDto = new BankBranchDTO();
		bankBranchDto.setId(bankBranch.getId());
		bankBranchDto.setName(bankBranch.getName());
		bankBranchDto.setAddress(bankBranch.getAddress());
		bankBranchDto.setBranchCode(bankBranch.getBranchCode());
		if (bankBranch.getCity() != null) {
			bankBranchDto.setCity(bankBranch.getCity().getName());
			bankBranchDto.setState(bankBranch.getCity().getState().getName());
		}
		bankBranchDto.setBank(bankBranch.getBank().getName());
		bankBranchDto.setChecker(bankBranch.isChecker());
		bankBranchDto.setMaker(bankBranch.isMaker());
		bankBranchDto.setBankId(bankBranch.getBank().getId());
		bankBranchDto.setBranchId(bankBranch.getBranchId());
		bankBranchDto.setEmail(bankBranch.getEmail());
		bankBranchDto.setLatitude(bankBranch.getLatitude());
		bankBranchDto.setLongitude(bankBranch.getLongitude());
		return bankBranchDto;
	}

	public static List<BankDTO> ConvertBankDTOtoBank(List<Bank> bank) {
		List<BankDTO> bankDTO = new ArrayList<BankDTO>();
		for (Bank bankList : bank) {
			bankDTO.add(convertBank(bankList));
		}
		return bankDTO;
	}

	public static List<BankDTO> ConvertBankListtoBankDtoList(List<Bank> bank) {
		List<BankDTO> bankDTO = new ArrayList<BankDTO>();
		for (Bank bankList : bank) {
			bankDTO.add(convertBank(bankList));
		}
		return bankDTO;
	}

	public static BankDTO convertBank(Bank bank) {
		BankDTO bankDto = new BankDTO();
		bankDto.setId(bank.getId());
		bankDto.setName(bank.getName());
		bankDto.setAddress(bank.getAddress());
		bankDto.setSwiftCode(bank.getSwiftCode());
		bankDto.setCity(bank.getCity().getName());
		bankDto.setSmsCount(bank.getSmsCount());
		bankDto.setLicenseCount(bank.getLicenseCount());
		bankDto.setLicenseExpiryDate(DateUtil.convertDateToString(bank.getLicenseExpiryDate()));
		if (bank.getChannelPartner() != null) {
			bankDto.setChannelPartnerName(bank.getChannelPartner().getName());
		}

		bankDto.setState(bank.getCity().getState().getName());
		bankDto.setMerchant(convertMerchantForBank(bank.getMerchant()));
		bankDto.setAcquiringInstitutionIdentificationCode(bank.getAcquiringInstitutionIdentificationCode());
		bankDto.setCardAcceptorTerminalIdentification(bank.getCardAcceptorTerminalIdentification());
		bankDto.setMerchantType(bank.getMerchantType());
		bankDto.setIsoUrl(bank.getIsoUrl());
		bankDto.setPortNumber(bank.getPortNumber());
		bankDto.setBankTranferAccount(bank.getBankTranferAccount());
		bankDto.setEmail(bank.getEmail());
		bankDto.setAccountNumber(bank.getAccountNumber());
		bankDto.setCbsStatus(bank.getCbsStatus());
		// bankDto.setIsDedicated(bank.getIsDedicated());
		return bankDto;
	}

	public static List<MerchantDTO> convertMerchantForBank(List<Merchant> merchantList) {
		List<MerchantDTO> merchantDtoList = new ArrayList<MerchantDTO>();
		if (!merchantList.isEmpty()) {
			for (Merchant m : merchantList) {
				merchantDtoList.add(convertMerchantForBank(m));
			}
		}
		return merchantDtoList;
	}

	public static MerchantDTO convertMerchantForBank(Merchant merchant) {
		MerchantDTO merchantDto = new MerchantDTO();
		merchantDto.setId(merchant.getId());
		merchantDto.setName(merchant.getName());
		return merchantDto;
	}

	public static List<MenuDTO> ConvertMenuDTOtoMenu(List<Menu> menu) {
		List<MenuDTO> menuDTO = new ArrayList<MenuDTO>();
		for (Menu menuList : menu) {
			menuDTO.add(convertMenu(menuList));
		}
		return menuDTO;
	}

	public static MenuDTO convertMenu(Menu menu) {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(menu.getId());
		menuDTO.setName(menu.getName());
		menuDTO.setUrl(menu.getUrl());
		menuDTO.setStatus(menu.getStatus());
		menuDTO.setMenuType(menu.getMenuType());
		menuDTO.setMenuType(menu.getMenuType());
		if (menu.getMenuType() == MenuType.SubMenu) {
			menuDTO.setSuperId(menu.getSuperId());
		}
		return menuDTO;
	}

	public static Menu convertDtoToMenu(MenuDTO menuDto) {
		Menu menu = new Menu();
		menu.setName(menuDto.getName());
		menu.setUrl(menuDto.getUrl());
		return menu;
	}

	public static Menu convertDtoToMenu(MenuDTO menuDto, Menu menu) {

		menu.setName(menuDto.getName());
		menu.setUrl(menuDto.getUrl());
		menu.setStatus(menuDto.getStatus());
		return menu;
	}

	public static List<MenuTemplateDTO> convertMenuTemplateDTOtoMenuTemplate(List<MenuTemplate> menuTemplate) {
		List<MenuTemplateDTO> menuTemplateDTO = new ArrayList<MenuTemplateDTO>();
		for (MenuTemplate menuTemplateList : menuTemplate) {
			menuTemplateDTO.add(convertMenuTemplate(menuTemplateList));
		}
		return menuTemplateDTO;
	}

	public static MenuTemplateDTO convertMenuTemplate(MenuTemplate menuTemplate) {
		MenuTemplateDTO menuTemplateDTO = new MenuTemplateDTO();
		menuTemplateDTO.setId(menuTemplate.getId());
		menuTemplateDTO.setName(menuTemplate.getName());
		if (menuTemplate.getMenu() != null) {
			menuTemplateDTO.setMenu(ConvertMenuDTOtoMenu(menuTemplate.getMenu()));
		}
		return menuTemplateDTO;
	}

	public static List<MenuTemplateDTO> convertMenuTemplateListToDtoList(List<MenuTemplate> menuTemplateList) {
		List<MenuTemplateDTO> menuTemplateDtoList = new ArrayList<MenuTemplateDTO>();
		for (MenuTemplate menuTemplate : menuTemplateList) {
			menuTemplateDtoList.add(convertMenuTemplate(menuTemplate));
		}

		return menuTemplateDtoList;
	}

	public static List<WebServiceDTO> ConvertWebServiceList(List<WebService> serviceList) {
		List<WebServiceDTO> dtoList = new ArrayList<WebServiceDTO>();
		for (WebService service : serviceList) {
			dtoList.add(convertWebService(service));
		}
		return dtoList;
	}

	public static WebServiceDTO convertWebService(WebService service) {
		WebServiceDTO dto = new WebServiceDTO();
		dto.setAccess_key(service.getAccess_key());
		dto.setApi_password(service.getApi_password());
		dto.setApi_user(service.getApi_user());
		dto.setStatus(service.getStatus());
		return dto;
	}

	public static ArrayList<String> convertDtoToCountryNameArray(List<CountryDTO> countryDtoList) {
		ArrayList<String> countryArrayList = new ArrayList<String>();
		for (CountryDTO countryDto : countryDtoList) {
			countryArrayList.add(countryDto.getName());
		}

		return countryArrayList;
	}

	public static User convertDtoToUser(UserDTO userDto, User user) {
		user.setUserType(userDto.getUserType());
		user.setStatus(userDto.getStatus());

		return user;

	}

	public static MerchantManagerDTO convertMerchantManager(MerchantManager merchantManager) {

		MerchantManagerDTO merchantManagerDTO = new MerchantManagerDTO();
		merchantManagerDTO.setId(merchantManager.getId());
		merchantManagerDTO.setMerchantId(merchantManager.getMerchantsAndServices().getMerchant().getId());
		merchantManagerDTO.setMerchantServiceId(merchantManager.getMerchantsAndServices().getMerchantService().getId());
		merchantManagerDTO.setSelected(merchantManager.isSelected());
		merchantManagerDTO.setStatus(merchantManager.getStatus());
		merchantManagerDTO.setMerchantName(merchantManager.getMerchantsAndServices().getMerchant().getName());
		return merchantManagerDTO;
	}

	public static List<MerchantManagerDTO> convertMerchantManagerList(List<MerchantManager> merchantManagerList) {
		List<MerchantManagerDTO> merchantManagerDTOList = new ArrayList<>();
		for (MerchantManager merchantManager : merchantManagerList) {
			merchantManagerDTOList.add(convertMerchantManager(merchantManager));
		}
		return merchantManagerDTOList;
	}

	public List<SmsLogDTO> converSmsLogList(List<SmsLog> smslogList) {
		List<SmsLogDTO> smslogDtoList = new ArrayList<>();
		if (smslogList != null) {
			for (SmsLog smsLog : smslogList) {
				smslogDtoList.add(convertSmsLog(smsLog));
			}
		}
		return smslogDtoList;

	}

	private SmsLogDTO convertSmsLog(SmsLog smsLog) {

		SmsLogDTO smsLogDto = new SmsLogDTO();

		if (smsLog.getSmsForUser() == UserType.Customer) {
			try {
				if (!smsLog.getSmsType().equals(SmsType.CBSALERT)) {
					Customer customer = customerRepository.findCustomerByUniqueId(smsLog.getSmsFor());
					if (customer.getMiddleName() != null && !(customer.getMiddleName().trim().equals(""))) {
						smsLogDto.setSmsForUser("Customer:- " + customer.getFirstName() + " " + customer.getMiddleName()
								+ " " + customer.getLastName());
					} else {
						smsLogDto.setSmsForUser("Customer:- " + customer.getFirstName() + " " + customer.getLastName());
					}
					smsLogDto.setSmsFor(customer.getMobileNumber());
				} else {
					smsLogDto.setSmsFor(smsLog.getForMobileNo());
					smsLogDto.setSmsForUser("CBS User.");
				}
			} catch (NullPointerException e) {
				smsLogDto.setSmsForUser("Customer");
				if (smsLog.getForMobileNo() != null) {
					smsLogDto.setSmsFor(smsLog.getForMobileNo());
				}
			}
			if (smsLog.getBank() != null) {
				if (smsLog.getSmsFromUser() == UserType.Bank) {
					smsLogDto.setSmsFromUser(smsLog.getBank().getName());
				}
			}
			if (smsLog.getBankBranch() != null) {
				if (smsLog.getSmsFromUser() == UserType.BankBranch) {
					smsLogDto.setSmsFromUser(smsLog.getBankBranch().getName());
				}
			}
		} else if (smsLog.getSmsForUser() == UserType.BankBranch) {
			if (smsLog.getBankBranch() != null) {
				BankBranch branch = smsLog.getBankBranch();
				smsLogDto.setSmsFor(branch.getMobileNumber());
				smsLogDto.setSmsForUser("Bank Branch:- " + branch.getName());
			}
			if (smsLog.getBank() != null) {
				if (smsLog.getSmsFromUser() == UserType.Bank) {
					smsLogDto.setSmsFromUser(smsLog.getBank().getName());
				}
			}
		} else if (smsLog.getSmsForUser() == UserType.Bank) {
			if (smsLog.getBank() != null) {
				Bank bank = smsLog.getBank();
				smsLogDto.setSmsFor(bank.getMobileNumber());
				smsLogDto.setSmsForUser("Bank :- " + bank.getName());
				smsLogDto.setSmsForUser(smsLog.getSmsForUser().toString());
			}

		}
		smsLogDto.setDelivered(smsLog.getDelivered());
		smsLogDto.setStatus(smsLog.getStatus());
		smsLogDto.setStatusString(smsLog.getStatus().toString());
		smsLogDto.setIsIncommingSms(smsLog.isSmsIn());
		smsLogDto.setMessage(smsLog.getMessage());
		smsLogDto.setDeliveredDate(smsLog.getCreated().toString().substring(0, 19));
		return smsLogDto;

	}

	public static List<NonFinancialTransactionDTO> convertNonFinancialTransactionList(
			List<NonFinancialTransaction> transactionList) {
		List<NonFinancialTransactionDTO> logDtoList = new ArrayList<>();
		for (NonFinancialTransaction log : transactionList) {
			logDtoList.add(convertNonFinancialTransaction(log));
		}
		return logDtoList;
	}

	private static NonFinancialTransactionDTO convertNonFinancialTransaction(NonFinancialTransaction entity) {
		NonFinancialTransactionDTO nonFinancialTransactionDTO = new NonFinancialTransactionDTO();
		nonFinancialTransactionDTO.setBankCode(entity.getBank().getSwiftCode());
		nonFinancialTransactionDTO.setBranchCode(entity.getBankBranch().getBranchCode());
		nonFinancialTransactionDTO.setAccountNumber(entity.getAccountNumber());
		if (entity.getCustomer().getMiddleName() == null || entity.getCustomer().getMiddleName().trim().equals("")) {
			nonFinancialTransactionDTO
					.setCustomerName(entity.getCustomer().getFirstName() + " " + entity.getCustomer().getLastName());
		} else {
			nonFinancialTransactionDTO.setCustomerName(entity.getCustomer().getFirstName() + " "
					+ entity.getCustomer().getMiddleName() + " " + entity.getCustomer().getLastName());
		}
		nonFinancialTransactionDTO.setDate(entity.getCreated().toString().substring(0, 19));
		nonFinancialTransactionDTO.setTransactionType(entity.getTransactionType());
		nonFinancialTransactionDTO.setMobileNumber(entity.getCustomer().getMobileNumber());
		nonFinancialTransactionDTO.setTransactionIdentifier(entity.getTransactionId());
		nonFinancialTransactionDTO.setStatus(entity.getStatus());
		return nonFinancialTransactionDTO;
	}

	public static List<SettlementLogDTO> convertSettlementLogList(List<SettlementLog> settlementLogList) {
		List<SettlementLogDTO> logDtoList = new ArrayList<>();
		for (SettlementLog log : settlementLogList) {
			logDtoList.add(convertSettlementLog(log));
		}
		return logDtoList;

	}

	private static SettlementLogDTO convertSettlementLog(SettlementLog log) {

		SettlementLogDTO settlementLog = new SettlementLogDTO();
		settlementLog.setSettlementDate(log.getCreated().toString());
		settlementLog.setTransactionIdentifier(log.getTransaction().getTransactionIdentifier());
		settlementLog.setSettlementStatus(log.getSettlementStatus());
		settlementLog.setId(log.getId());
		settlementLog.setSettlementType(log.getSettlementType());
		settlementLog.setBankName(log.getBank().getName());
		if (log.getResponseCode() != null)
			settlementLog.setResponseCode(log.getResponseCode());
		return settlementLog;
	}

	public CustomerProfileDTO convertCustomerProfile(CustomerProfile customerProfile) {
		CustomerProfileDTO customerProfileDto = new CustomerProfileDTO();
		customerProfileDto.setId(customerProfile.getId());
		customerProfileDto.setName(customerProfile.getName());
		customerProfileDto.setPerTxnLimit(customerProfile.getPerTransactionLimit());
		customerProfileDto.setDailyTxnLimit(customerProfile.getDailyTransactionLimit());
		customerProfileDto.setWeeklyTxnLimit(customerProfile.getWeeklyTransactionLimit());
		customerProfileDto.setMonthlyTxnLimit(customerProfile.getMonthlyTransactionLimit());
		if (customerProfile.getDailyTransactionAmount() != null) {
			customerProfileDto.setDailyTransactionAmount(customerProfile.getDailyTransactionAmount());
		}
		if (customerProfile.getRegistrationCharge() != null) {
			customerProfileDto.setRegistrationAmount(customerProfile.getRegistrationCharge());
		}
		if (customerProfile.getRenewCharge() != null) {
			customerProfileDto.setRenewAmount(customerProfile.getRenewCharge());
		}
		if (customerProfile.getPinResetCharge() != null) {
			customerProfileDto.setPinResetCharge(customerProfile.getPinResetCharge());
		}
		if (customerProfile.getStatus() != null) {
			customerProfileDto.setStatus(customerProfile.getStatus());
		}
		if (customerProfile.getBank() == null) {
			customerProfileDto.setIsDefault(true);
		}
		customerProfileDto.setProfileUniqueId(customerProfile.getProfileUniqueId());
		return customerProfileDto;
	}

	public List<CustomerProfileDTO> convertCustomerProfileList(List<CustomerProfile> entityList) {
		List<CustomerProfileDTO> dtoList = new ArrayList<>();
		for (CustomerProfile entity : entityList) {
			dtoList.add(convertCustomerProfile(entity));
		}
		return dtoList;
	}

	public List<CustomerLogDto> convetCustomerLogList(List<CustomerLog> customerLogs) {
		List<CustomerLogDto> dtoList = new ArrayList<>();
		for (CustomerLog entity : customerLogs) {
			dtoList.add(convertCustomerLog(entity));
		}
		return dtoList;
	}

	private CustomerLogDto convertCustomerLog(CustomerLog entity) {

		CustomerLogDto customerLog = new CustomerLogDto();
		if (entity.getChangedBy() != null) {
			customerLog.setChangedBy(entity.getChangedBy());
		} else {
			customerLog.setChangedBy("SYSTEM");
		}
		customerLog.setRemarks(entity.getRemarks());
		customerLog.setCustomerName(entity.getCustomer().getFirstName() + " " + entity.getCustomer().getLastName());
		customerLog.setDate(entity.getCreated().toString());
		return customerLog;
	}

	public List<TransactionAlertDTO> convertToTransactionAlertDTO(List<TransactionAlert> transactionAlertList) {
		List<TransactionAlertDTO> transactionAlertDtoList = new ArrayList<>();
		if (transactionAlertList != null) {
			for (TransactionAlert transactionAlert : transactionAlertList) {
				transactionAlertDtoList.add(convertTransactionAlertToDTO(transactionAlert));
			}
		}
		return transactionAlertDtoList;

	}

	private TransactionAlertDTO convertTransactionAlertToDTO(TransactionAlert transactionAlert) {
		TransactionAlertDTO transactionAlertDto = new TransactionAlertDTO();
		transactionAlertDto.setChannelPartner(transactionAlert.getChannelPartner());
		// transactionAlertDto.setDateTime(DateUtil.convertDateToString(transactionAlert.getDate()));(this
		// date can be null that through exception)
		transactionAlertDto.setDateTime(DateUtil.convertDateToString(transactionAlert.getCreated()));
		transactionAlertDto.setMessage(transactionAlert.getMessage());
		transactionAlertDto.setMobileNumber(transactionAlert.getMobileNumber());
		transactionAlertDto.setSwiftCode(transactionAlert.getSwiftCode());
		transactionAlertDto.setSmsStatus(transactionAlert.getStatus());
		transactionAlertDto.setNotificationStatus(transactionAlert.getNotificationStatus());
		return transactionAlertDto;
	}

}
