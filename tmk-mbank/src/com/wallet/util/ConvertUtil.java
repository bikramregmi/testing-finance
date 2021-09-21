/*package com.wallet.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wallet.entity.Account;
import com.wallet.entity.Agent;
import com.wallet.entity.Aml;
import com.wallet.entity.Bank;
import com.wallet.entity.BankAccount;
import com.wallet.entity.Branch;
import com.wallet.entity.City;
import com.wallet.entity.Commission;
import com.wallet.entity.Compliance;
import com.wallet.entity.ConfirmationCode;
import com.wallet.entity.Country;
import com.wallet.entity.Customer;
import com.wallet.entity.Discount;
import com.wallet.entity.DishHome;
import com.wallet.entity.FinancialInstitution;
import com.wallet.entity.Ledger;
import com.wallet.entity.Menu;
import com.wallet.entity.MenuTemplate;
import com.wallet.entity.Merchant;
import com.wallet.entity.PaymentCatagory;
import com.wallet.entity.Services;
import com.wallet.entity.State;
import com.wallet.entity.SuperAgent;
import com.wallet.entity.Transaction;
import com.wallet.entity.User;
import com.wallet.entity.UserSession;
import com.wallet.entity.UserTemplate;
import com.wallet.entity.WebService;
import com.wallet.model.AccountDTO;
import com.wallet.model.AgentDTO;
import com.wallet.model.AgentType;
import com.wallet.model.AmlDTO;
import com.wallet.model.BankAccountDTO;
import com.wallet.model.BankDTO;
import com.wallet.model.BranchDTO;
import com.wallet.model.CityDTO;
import com.wallet.model.CommissionDTO;
import com.wallet.model.ComplianceDTO;
import com.wallet.model.ConfirmationCodeDTO;
import com.wallet.model.CountryDTO;
import com.wallet.model.CustomerDTO;
import com.wallet.model.DiscountDTO;
import com.wallet.model.DishHomeDto;
import com.wallet.model.DocumentType;
import com.wallet.model.FinancialInstitutionDTO;
import com.wallet.model.FinancialInstitutionType;
import com.wallet.model.LedgerDTO;
import com.wallet.model.MenuDTO;
import com.wallet.model.MenuTemplateDTO;
import com.wallet.model.MenuType;
import com.wallet.model.MerchantDTO;
import com.wallet.model.PaymentCatagoryDTO;
import com.wallet.model.ReportDTO;
import com.wallet.model.ServicesDTO;
import com.wallet.model.StateDTO;
import com.wallet.model.Status;
import com.wallet.model.SuperAgentDTO;
import com.wallet.model.TransactionDTO;
import com.wallet.model.UserDTO;
import com.wallet.model.UserSessionDTO;
import com.wallet.model.UserTemplateDTO;
import com.wallet.model.WebServiceDTO;

public class ConvertUtil {

	private Logger logger = LoggerFactory.getLogger(ConvertUtil.class);

	private ConvertUtil() {

	}
	
	public static Services convertServiceDtoToServices(ServicesDTO serviceDto){
		
		Services service = new Services();
		service.setName(serviceDto.getName());
		return service;
	}
	public static ConfirmationCodeDTO convertConfirmationCodeToDTO(ConfirmationCode confirmationCode){
		
		ConfirmationCodeDTO dto = new ConfirmationCodeDTO();
		dto.setId(confirmationCode.getId());
		
		return dto;
		
	
	}
	
	public static Services convertServicesDtoToServices(ServicesDTO serviceDto,Services services){
		services.setName(serviceDto.getName());
		return services;
	}
	
	
	
	public static FinancialInstitutionDTO convertFinanceInstituteToFinanceIntitute(FinancialInstitution institution){
		FinancialInstitutionDTO instituteDTO = new FinancialInstitutionDTO();
		instituteDTO.setId(institution.getId());
		instituteDTO.setName(institution.getName());
		instituteDTO.setCity(institution.getCity().getName());
		instituteDTO.setAccount(institution.getAccountNo());
		instituteDTO.setAddress(institution.getAddress());
		instituteDTO.setSwiftCode(institution.getSwiftCode());
		instituteDTO.setInstituteType(institution.getFinancialInstitutionType().toString());
		
		return instituteDTO;
	}
	
	public static FinancialInstitution convertInstituteDtoToInstitute(FinancialInstitutionDTO instituteDTO,FinancialInstitution institution){
		
		institution.setAddress(instituteDTO.getAddress());
		institution.setFinancialInstitutionType(FinancialInstitutionType.valueOf(instituteDTO.getInstituteType()));
		institution.setName(instituteDTO.getName());
		institution.setSwiftCode(instituteDTO.getSwiftCode());
		institution.setAccountNo(instituteDTO.getAccount());
		return institution;
	}
	
	public static Merchant convertMerchantDtoToMerchant(Merchant merchant,MerchantDTO merchantDTO){
		
		merchant.setMerchantName(merchantDTO.getMerchantName());
		merchant.setRegistrationNumber(merchantDTO.getRegistrationNumber());
		merchant.setMobileNo(merchantDTO.getMobileNo());
		merchant.setOwnerName(merchantDTO.getOwnerName());
		merchant.setVatNumber(merchant.getVatNumber());
		merchant.setLandLine(merchant.getLandLine());
		merchant.setDescription(merchantDTO.getDescription());
		return merchant;
		
	}
	
	public static List<MerchantDTO> convertMerchantToDTO(List<Merchant> merchant) {
		List<MerchantDTO> merchantDTO = new ArrayList<MerchantDTO>();
		for (Merchant merchantList : merchant) {
			merchantDTO.add(convertMerchant(merchantList));
		}
		return merchantDTO;
	}

	public static MerchantDTO convertMerchant(Merchant merchant) {
		
		MerchantDTO merchantDTO = new MerchantDTO();
		try{
		if (merchant != null) {
			merchantDTO.setId(merchant.getId());
			if (merchant.getCity() != null) {
				merchantDTO.setCity(merchant.getCity().getName());
			}
			
			merchantDTO.setDescription(merchant.getDescription());
			merchantDTO.setLandLine(merchant.getLandLine());
			merchantDTO.setMerchantName(merchant.getMerchantName());
			merchantDTO.setMobileNo(merchant.getMobileNo());
			merchantDTO.setOwnerName(merchant.getOwnerName());
			merchantDTO.setRegistrationNumber(merchant.getRegistrationNumber());
			merchantDTO.setVatNumber(merchant.getVatNumber());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return merchantDTO;
	}
	
	public static List<ServicesDTO> convertServicesToDTO(List<Services> services) {
		List<ServicesDTO> servicesDTO = new ArrayList<ServicesDTO>();
		for (Services servicesList : services) {
			servicesDTO.add(convertServices(servicesList));
		}
		return servicesDTO;
	}
	
public static List<ServicesDTO> convertServicesToServicesDto(List<Services> services){
		
		List<ServicesDTO> servicesDto = new ArrayList<ServicesDTO>();
		for(Services service : services){
			
			servicesDto.add(convertServices(service));
			
		}
		return servicesDto;
	}

	public static ServicesDTO convertServices(Services services) {
		ServicesDTO servicesDTO = new ServicesDTO();
		try{
		if (services != null) {
			servicesDTO.setId(services.getId());
			servicesDTO.setName(services.getName());
			servicesDTO.setUniqueIdentifier(services.getUniqueIdentifier());
			servicesDTO.setMerchant(services.getMerchant().getMerchantName());
			servicesDTO.setUrl(services.getUrls());
			if (services.getMerchant() != null) {
				servicesDTO.setMerchant(services.getMerchant().getMerchantName());
				servicesDTO.setMerchantId(services.getMerchant().getId());
			}
			//servicesDTO.setMinimumvalue(services.getMinnimumvalue());
			

			//servicesDTO.setPriceinput(services.isPriceinput());
			//servicesDTO.setRealTimeSettlement(services.isRealTimeSettlement());
			//servicesDTO.setUniqueIdentifier(services.getUniqueIdentifier());
			//servicesDTO.setTokenKey(services.getTokenKey());
			//servicesDTO.setSuccessURL(services.getSuccessURL());
			//servicesDTO.setFailureURL(services.getFailureURL());

			servicesDTO.setPriceinput(services.isPriceinput());
			servicesDTO.setRealTimeSettlement(services.isRealTimeSettlement());
			servicesDTO.setTokenKey(services.getTokenKey());
			servicesDTO.setSuccessURL(services.getSuccessURL());
			servicesDTO.setFailureURL(services.getFailureURL());
			if(!services.isPriceinput()){
				servicesDTO.setPriceRange(services.getPriceRange());
			}

		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return servicesDTO;
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
			dto.setAccountNo(account.getAccountNo());
			dto.setBalance(account.getBalance());
			dto.setAccountId(account.getId());
//			dto.setUser(account.getUser());
			dto.setAssociatedId(account.getAssociatedId());
		}
		return dto;
	}

	public static List<LedgerDTO> convertLedgerListToDTO(List<Ledger> txnList) {
		List<LedgerDTO> dtoList = new ArrayList<LedgerDTO>();
		for (Ledger txn : txnList) {
			dtoList.add(convertLedgerToDTO(txn));
		}
		return dtoList;
	}

	public static LedgerDTO convertLedgerToDTO(Ledger txn) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LedgerDTO dto = new LedgerDTO();
		try {
			if (txn != null) {

				dto.setAmount(txn.getAmount());
				dto.setFromBalance(txn.getFromBalance());
				dto.setRemarks(txn.getRemarks());
				dto.setToBalance(txn.getToBalance());
				dto.setTransactionId(Long.toString(txn.getTransactionId()));
				dto.setTransactionStatus(txn.getStatus());
				dto.setTransactionType(txn.getType());
				if (txn.getExternalRefId() > 0) {
					dto.setExternalRefId(txn.getExternalRefId());
				}
				if (txn.getFromAccount() != null) {
					dto.setFromAccount(txn.getFromAccount().getAccountNo());
				}
				if (txn.getToAccount() != null) {
					dto.setToAccount(txn.getToAccount().getAccountNo());
				}
				dto.setDate(dateFormatter.format(txn.getCreated()));
				dto.setParentId(txn.getParentId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public static String CreateHash(String key, String password, String hash) throws ClientException {
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
		dto.setUsername(session.getUser().getUsername());
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

	public static Customer convertCustomerDTO(CustomerDTO customerDTO) {
		Customer customer = new Customer();

		customer.setAddress_one(customerDTO.getAddress_one());
		customer.setAddress_two(customerDTO.getAddress_two());
		
		customer.setTimeZone(customerDTO.getFirstName());
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLandline(customerDTO.getLandline());
		customer.setLastName(customerDTO.getLastName());
		customer.setMiddleName(customerDTO.getMiddleName());
		customer.setMobileNo(customerDTO.getMobileNo());
		return customer;
	}

	public static Customer convertCustomerDTO(Customer customer, CustomerDTO customerDto) {

		customer.setFirstName(customerDto.getFirstName());
		customer.setMiddleName(customerDto.getMiddleName());
		customer.setLastName(customerDto.getLastName());
		customer.setAddress_one(customerDto.getAddress_one());
		customer.setAddress_two(customerDto.getAddress_two());
		customer.setLandline(customerDto.getLandline());
		customer.setMobileNo(customerDto.getMobileNo());
		customer.setTimeZone(customerDto.getTimeZone());
		customer.setFullName(customerDto.getFirstName()+" "+customerDto.getMiddleName()+" "+customerDto.getLastName());

		return customer;
	}

	public static List<CustomerDTO> ConvertCustomerToDTO(List<Customer> customer) {
		List<CustomerDTO> customerDTO = new ArrayList<CustomerDTO>();
		for (Customer cust : customer) {
			customerDTO.add(convertCustomer(cust));
		}
		return customerDTO;
	}

	public static CustomerDTO convertCustomer(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setAddress_one(customer.getAddress_one());
		dto.setAddress_two(customer.getAddress_two());
		dto.setAccountNo(customer.getAccountNo());
		dto.setFirstName(customer.getFirstName());
		dto.setLandline(customer.getLandline());
		dto.setFullName(customer.getFullName());
		dto.setLastName(customer.getLastName());
		dto.setMiddleName(customer.getMiddleName());
		dto.setMobileNo(customer.getMobileNo());
		dto.setTimeZone(customer.getTimeZone());
		return dto;
	}

	public static List<UserDTO> ConvertUserToDTO(List<User> user) {
		List<UserDTO> userDTO = new ArrayList<UserDTO>();
		for (User u : user) {
			userDTO.add(convertUser(u));
		}
		return userDTO;
	}

	public static UserDTO convertUser(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setAuthority(user.getAuthority());
		dto.setUserType(user.getUserType());
		dto.setStatus(user.getStatus());
		dto.setTimeZone(user.getTimeZone());
		dto.setSecret_key(user.getSecret_key());
		if (user.getUserTemplate() != null) {
			dto.setUserTemplateDTO(ConvertUtil.convertUserTemplate(user.getUserTemplate()));
		}
		if (user.getWeb_service() != null) {
			dto.setWeb_service(convertWebService(user.getWeb_service()));
		}
		return dto;
	}

	public static AgentDTO convertAgent(Agent agent) {

		AgentDTO agentDTO = new AgentDTO();
		try {
			if (agent != null || agent.getId() != null) {
				agentDTO.setId(agent.getId());
				try {
					agentDTO.setParentId(agent.getParent().getId());
				} catch (Exception e) {
					agentDTO.setParentId(1l);
				}
				agentDTO.setAddress(agent.getAddress());
				agentDTO.setAgencyName(agent.getAgencyName());
				agentDTO.setAgentCode(agent.getAgentCode());
							agentDTO.setDestinationCountryList(
						ConvertUtil.convertCountrytoCountryDtoList(agent.getDestinationCountryList()));
				agentDTO.setCountry(agent.getCity().getState().getCountry().getName());	agentDTO.setAccountNo(agent.getAccountNo());
				agentDTO.setAgentType(agent.getAgentType().toString());
				agentDTO.setCity(agent.getCity().getName());

				agentDTO.setDocumentId(agent.getDocumentId());
				agentDTO.setDocumentType(agent.getDocType().toString());
				agentDTO.setLandline(agent.getLandline());
				agentDTO.setMobileNo(agent.getMobileNo());
				agentDTO.setOwnerAddress(agent.getOwnerAddress());
				agentDTO.setOwnerName(agent.getOwnerName());
				agentDTO.setState(agent.getCity().getState().getName());
				agentDTO.setTimeZone(agent.getTimeZone());
//				agentDTO.setPaying(agent.isPaying());
//				agentDTO.setReceiving(agent.isReceiving());
			}
		} catch (Exception e) {

		}
		return agentDTO;
	}
	
	public static PaymentCatagoryDTO convertPaymentCatagory(PaymentCatagory paymentCatagory) {

		PaymentCatagoryDTO paymentCatagoryDTO= new PaymentCatagoryDTO();
		paymentCatagoryDTO.setId(paymentCatagory.getId());
		paymentCatagoryDTO.setName(paymentCatagory.getName());
		paymentCatagoryDTO.setServiceDTO(convertServicesToServicesDto(paymentCatagory.getServices()));
		return paymentCatagoryDTO;
		
	}

	public static Agent convertAgentDTO(AgentDTO agentDTO) {

		Agent agent = new Agent();
		agent.setAddress(agentDTO.getAddress());
		agent.setAgencyName(agentDTO.getAgencyName());
		agent.setAgentCode(agentDTO.getAgentCode());
		agent.setAgentType(AgentType.valueOf(agentDTO.getAgentType()));
		if (agentDTO.getDocumentType() == DocumentType.CITIZENSHIP.toString()) {
			agent.setDocType(DocumentType.CITIZENSHIP);
		} else if (agentDTO.getDocumentType() == DocumentType.PASSPORT.toString()) {
			agent.setDocType(DocumentType.PASSPORT);
		} else {
			agent.setDocType(DocumentType.LICENSE);
		}
		agent.setDocumentId("test");
		agent.setLandline(agentDTO.getLandline());
		agent.setOwnerAddress(agentDTO.getOwnerAddress());
		agent.setOwnerName(agentDTO.getOwnerName());
		// agent.setParent(superAgent);
		agent.setMobileNo(agentDTO.getMobileNo());
		agent.setTimeZone(agentDTO.getTimeZone());
		// agent = agentRepository.save(agent);

		return agent;
	}

	public static Agent convertAgentDTO(AgentDTO agentDTO, City city) {

		Agent agent = new Agent();
		agent.setAddress(agentDTO.getAddress());
		agent.setAgencyName(agentDTO.getAgencyName());
		agent.setAgentCode(agentDTO.getAgentCode());
		if (agentDTO.getDocumentType() == DocumentType.CITIZENSHIP.toString()) {
			agent.setDocType(DocumentType.CITIZENSHIP);
		} else if (agentDTO.getDocumentType() == DocumentType.PASSPORT.toString()) {
			agent.setDocType(DocumentType.PASSPORT);
		} else {
			agent.setDocType(DocumentType.LICENSE);
		}
		agent.setAgentType(AgentType.valueOf(agentDTO.getAgentType()));
		agent.setDocumentId("test");
		agent.setLandline(agentDTO.getLandline());
		agent.setMobileNo(agentDTO.getMobileNo());
		agent.setOwnerAddress(agentDTO.getOwnerAddress());
		agent.setOwnerName(agentDTO.getOwnerName());
		agent.setTimeZone(agentDTO.getTimeZone());
		agent.setPaying(agentDTO.isPaying());
		agent.setReceiving(agentDTO.isReceiving());
		agent.setCity(city);

		return agent;
	}

	public static List<AgentDTO> convertAgenttoAgentDTO(List<Agent> agent) {
		List<AgentDTO> agentDTO = new ArrayList<AgentDTO>();
		for (Agent agentList : agent) {
			agentDTO.add(convertAgent(agentList));
		}
		return agentDTO;
	}
	
	public static Agent convertAgentDTO(AgentDTO agentDTO, SuperAgent parent, City city) {

		Agent agent = new Agent();
		agent.setAddress(agentDTO.getAddress());
		agent.setAgencyName(agentDTO.getAgencyName());
		agent.setAgentCode(agentDTO.getAgentCode());
		if (agentDTO.getDocumentType() == DocumentType.CITIZENSHIP.toString()) {
			agent.setDocType(DocumentType.CITIZENSHIP);
		} else if (agentDTO.getDocumentType() == DocumentType.PASSPORT.toString()) {
			agent.setDocType(DocumentType.PASSPORT);
		} else {
			agent.setDocType(DocumentType.LICENSE);
		}
		agent.setAgentType(AgentType.valueOf(agentDTO.getAgentType()));
		agent.setDocumentId("test");
		agent.setLandline(agentDTO.getLandline());
		agent.setMobileNo(agentDTO.getMobileNo());
		agent.setOwnerAddress(agentDTO.getOwnerAddress());
		agent.setOwnerName(agentDTO.getOwnerName());
		agent.setTimeZone(agentDTO.getTimeZone());
		agent.setParent(parent);
		agent.setPaying(agentDTO.isPaying());
		agent.setReceiving(agentDTO.isReceiving());
		agent.setCity(city);

		return agent;
	}
	
	public static SuperAgent convertSuperAgentDTO(SuperAgentDTO superAgentDTO, City city, User user, User createdBy) {
		SuperAgent superAgent = new SuperAgent();

		superAgent.setAccountNo("TMK" + System.currentTimeMillis());
		superAgent.setAgencyName(superAgentDTO.getAgencyName());
		superAgent.setAgentCode(superAgentDTO.getAgentCode());
		superAgent.setAddress(superAgentDTO.getAddress());
		superAgent.setLandline(superAgentDTO.getLandline());
		superAgent.setMobileNo(superAgentDTO.getMobileNo());
		superAgent.setTimeZone(superAgentDTO.getTimeZone());
		superAgent.setCity(city);
		superAgent.setCreatedBy(createdBy);
		superAgent.setUser(user);
		superAgent.setDocType(DocumentType.CITIZENSHIP);
		superAgent.setDocumentId("test");
		superAgent.setOwnerAddress(superAgentDTO.getOwnerAddress());
		superAgent.setOwnerName(superAgentDTO.getOwnerName());

		return superAgent;
	}

	public static List<SuperAgentDTO> convertSuperAgentToSuperAgentDTO(List<SuperAgent> superAgent) {
		List<SuperAgentDTO> superAgentDTO = new ArrayList<SuperAgentDTO>();
		for (SuperAgent superAgentList : superAgent) {
			superAgentDTO.add(convertSuperAgent(superAgentList));
		}
		return superAgentDTO;
	}
	
	public static SuperAgentDTO convertSuperAgent(SuperAgent superAgent) {
		SuperAgentDTO superAgentDTO = new SuperAgentDTO();

		try {
			superAgentDTO.setId(superAgent.getId());
			superAgentDTO.setAddress(superAgent.getAddress());
			superAgentDTO.setAgencyName(superAgent.getAgencyName());
			superAgentDTO.setAgentCode(superAgent.getAgentCode());
			superAgentDTO.setCity(superAgent.getCity().getName());
			superAgentDTO.setLandline(superAgent.getLandline());
			superAgentDTO.setMobileNo(superAgent.getMobileNo());
			superAgentDTO.setOwnerAddress(superAgent.getOwnerAddress());
			superAgentDTO.setOwnerName(superAgent.getOwnerName());
			superAgentDTO.setState(superAgent.getCity().getState().getName());
			superAgentDTO.setTimeZone(superAgent.getTimeZone());
			superAgentDTO.setAccountNo(superAgent.getAccountNo());
		} catch (Exception e) {

		}

		return superAgentDTO;

	}

	
	public static SuperAgent convertSuperAgentDTO(SuperAgentDTO superAgentDTO) {
		SuperAgent superAgent = new SuperAgent();
		superAgent.setAccountNo(superAgentDTO.getAccountNo());
		superAgent.setAgencyName(superAgentDTO.getAgencyName());
		superAgent.setAgentCode(superAgentDTO.getAgentCode());
		superAgent.setAddress(superAgentDTO.getAddress());
		superAgent.setLandline(superAgentDTO.getLandline());
		superAgent.setMobileNo(superAgentDTO.getMobileNo());
		superAgent.setTimeZone(superAgentDTO.getTimeZone());
		return superAgent;
	}
	
	public static List<PaymentCatagoryDTO> convertPaymentCatagoryToPaymentCatagoryDTO(List<PaymentCatagory> paymentCatagory) {
		List<PaymentCatagoryDTO> paymentMethodDTO = new ArrayList<PaymentCatagoryDTO>();
		for (PaymentCatagory pc : paymentCatagory) {
			paymentMethodDTO.add(convertPaymentCatagory(pc));
		}
		return paymentMethodDTO;
	}

	public static <E> List<E> convertIterableToList(Iterable<E> iter) {
		List<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

	public static Country convertCountryDTO(CountryDTO countryDTO) {
		Country country = new Country();

		country.setName(countryDTO.getName());
		country.setIsoTwo(countryDTO.getIsoTwo());
		country.setIsoThree(countryDTO.getIsoThree());
		country.setDialCode(countryDTO.getDialCode());
		country.setStatus(Status.Active);

		return country;
	}

	public static Country convertCountryDTO(CountryDTO countryDTO, Country country) {

		country.setName(countryDTO.getName());
		country.setIsoTwo(countryDTO.getIsoTwo());
		country.setIsoThree(countryDTO.getIsoThree());
		country.setDialCode(countryDTO.getDialCode());
		country.setStatus(Status.valueOf(countryDTO.getStatus()));

		return country;
	}

	public static List<Country> convertToCountryListFromDtoList(List<CountryDTO> countryDtoList) {
		List<Country> countryList = new ArrayList<Country>();
		for (CountryDTO countryDto : countryDtoList) {
			countryList.add(convertCountryDTO(countryDto));
		}
		return countryList;
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



	public static List<CommissionDTO> convertCommissionToDTO(List<Commission> commission) {
		List<CommissionDTO> commissionDTO = new ArrayList<CommissionDTO>();
		for (Commission commissionList : commission) {
			commissionDTO.add(convertCommission(commissionList));
		}
		// TODO Auto-generated method stub
		return commissionDTO;
	}

	public static CommissionDTO convertCommission(Commission commission) {

		CommissionDTO commissionDTO = new CommissionDTO();
		try {
			commissionDTO.setId(commission.getId());
			commissionDTO.setFromAgent(commission.getFromAgent().getAgencyName());
			commissionDTO.setToAgent(commission.getToAgent().getAgencyName());
			commissionDTO.setFromAmount(commission.getFromAmount());
			commissionDTO.setToAmount(commission.getToAmount());
			commissionDTO.setPayingCommission(commission.getPayingCommission());
			commissionDTO.setPayingCommissionFlat(commission.getPayingCommissionFlat());
			commissionDTO.setReceivingCommission(commission.getReceivingCommission());
			commissionDTO.setReceivingCommissionFlat(commission.getReceivingCommissionFlat());
			commissionDTO.setOverallCommission(commission.getOverallCommission());
			commissionDTO.setOverallCommissionFlat(commission.getOverallCommissionFlat());
			commissionDTO.setCommissionType(commission.getCommissionType());
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return commissionDTO;
	}

	public static Commission convertDtoToCommission(CommissionDTO commissionDto) {
		Commission commission = new Commission();

		commission.setFromAmount(commissionDto.getFromAmount());
		commission.setToAmount(commissionDto.getToAmount());
		commission.setPayingCommission(commissionDto.getPayingCommission());
		commission.setPayingCommissionFlat(commission.getPayingCommissionFlat());
		commission.setOverallCommission(commissionDto.getOverallCommission());
		commission.setOverallCommissionFlat(commissionDto.getOverallCommissionFlat());
		commission.setReceivingCommission(commissionDto.getReceivingCommission());
		commission.setReceivingCommissionFlat(commission.getReceivingCommissionFlat());
		commission.setCommissionType(commissionDto.getCommissionType());

		return commission;
	}

	public static Commission convertDtoToCommission(CommissionDTO commissionDto, Commission commission) {

		commission.setFromAmount(commissionDto.getFromAmount());
		commission.setToAmount(commissionDto.getToAmount());
		commission.setPayingCommission(commissionDto.getPayingCommission());
		commission.setPayingCommissionFlat(commission.getPayingCommissionFlat());
		commission.setOverallCommission(commissionDto.getOverallCommission());
		commission.setOverallCommissionFlat(commissionDto.getOverallCommissionFlat());
		commission.setReceivingCommission(commissionDto.getReceivingCommission());
		commission.setReceivingCommissionFlat(commission.getReceivingCommissionFlat());
		commission.setCommissionType(commissionDto.getCommissionType());

		return commission;
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
		stateDTO.setCountry(state.getCountry().getName());
		stateDTO.setStatus(state.getStatus());
		return stateDTO;
	}

	public static StateDTO convertStateDto(State state) {
		StateDTO stateDTO = new StateDTO();
		stateDTO.setId(state.getId());
		stateDTO.setName(state.getName());
		stateDTO.setCountry(state.getCountry().getName());
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

	public static List<AmlDTO> convertAmlListToAmlDtoList(List<Aml> aml) {
		List<AmlDTO> amlDTO = new ArrayList<AmlDTO>();
		for (Aml amlList : aml) {
			amlDTO.add(convertAml(amlList));
		}
		return amlDTO;
	}

	public static AmlDTO convertAml(Aml aml) {
		AmlDTO amlDTO = new AmlDTO();
		amlDTO.setId(aml.getId());
		amlDTO.setName(aml.getName());
		amlDTO.setSource(aml.getSource());
		amlDTO.setAddress(aml.getAddress());
		amlDTO.setAlias(aml.getAlias());
		amlDTO.setDocuments(aml.getDocuments());
		amlDTO.setUploadedBy(aml.getUploadedBy().getUsername());
		amlDTO.setStatus(aml.getStatus());
		return amlDTO;
	}

	public static List<DiscountDTO> convertDiscountToDTO(List<Discount> discount) {
		List<DiscountDTO> discountDTO = new ArrayList<DiscountDTO>();
		for (Discount discountList : discount) {
			discountDTO.add(convertDiscount(discountList));

		}
		// TODO Auto-generated method stub
		return discountDTO;
	}

	public static DiscountDTO convertDiscount(Discount discount) {
		DiscountDTO discountDTO = new DiscountDTO();
		discountDTO.setId(discount.getId());
		discountDTO.setOverall_discount(discount.getOverall_discount());
		discountDTO.setOverall_discountFlat(discount.getOverall_discountFlat());
		discountDTO.setFromAmount(discount.getFromAmount());
		discountDTO.setToAmount(discount.getToAmount());
		discountDTO.setDiscountType(discount.getDiscountType());
		return discountDTO;
	}

	public static List<BankDTO> ConvertBankDTOtoBank(List<Bank> bank) {
		List<BankDTO> bankDTO = new ArrayList<BankDTO>();
		for (Bank bankList : bank) {
			bankDTO.add(convertBank(bankList));
		}
		return bankDTO;
	}

	public static BankDTO convertBank(Bank bank) {
		BankDTO bankDTO = new BankDTO();
		bankDTO.setId(bank.getId());
		bankDTO.setName(bank.getName());
		bankDTO.setAddress(bank.getAddress());
		bankDTO.setCountry(bank.getCountry().getName());
		bankDTO.setSwiftCode(bank.getSwiftCode());
		return bankDTO;
	}

	public static List<BranchDTO> ConvertBranchDTOtoBranch(List<Branch> branch) {
		List<BranchDTO> branchDTO = new ArrayList<BranchDTO>();
		for (Branch branchList : branch) {
			branchDTO.add(convertBranch(branchList));
		}
		return branchDTO;
	}

	public static BranchDTO convertBranch(Branch branch) {
		BranchDTO branchDTO = new BranchDTO();
		branchDTO.setId(branch.getId());
		branchDTO.setName(branch.getName());
		branchDTO.setBank(branch.getBank().getName());
		branchDTO.setCity(branch.getCity().getName());
		branchDTO.setAddress(branch.getAddress());
		branchDTO.setBranchCode(branch.getBranchCode());
		return branchDTO;
	}

	public static List<BankAccountDTO> ConvertBankAccountDTOtoBankAccount(List<BankAccount> bankAccount) {
		List<BankAccountDTO> bankAccountDTO = new ArrayList<BankAccountDTO>();
		for (BankAccount bankAccountList : bankAccount) {
			bankAccountDTO.add(convertBankAccount(bankAccountList));
		}
		return bankAccountDTO;
	}

	public static BankAccountDTO convertBankAccount(BankAccount bankAccount) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		// bankAccountDTO.setId(bankAccount.getId());
		// bankAccountDTO.setName(bankAccount.getName());
		// bankAccountDTO.setAddress(bankAccount.getAddress());
		// bankAccountDTO.setCity(bankAccount.getCity().getName());
		// bankAccountDTO.setSwiftCode(bankAccount.getSwiftCode());
		return bankAccountDTO;
	}


	public static List<TransactionDTO> ConvertTransactiontoTransactionDto(List<Transaction> transaction) {
		List<TransactionDTO> transactionDTO = new ArrayList<TransactionDTO>();
		for (Transaction transactionList : transaction) {
			transactionDTO.add(convertTransaction(transactionList));
		}
		return transactionDTO;
	}

	public static TransactionDTO convertTransaction(Transaction transaction) {
		
		TransactionDTO transactionDTO = new TransactionDTO();
		
		return transactionDTO;
	}
	
	public static Transaction convertTransactionDtoToTransaction(TransactionDTO transactionDto) {
		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDto.getAmount());
		transaction.setSourceAccount(transactionDto.getSourceAccount());
		transaction.setDestinationAccount(transactionDto.getDestinationAccount());
		return transaction;
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

	public static List<ReportDTO> convertReportList(List<Transaction> txnList) {
		List<ReportDTO> dtoList = new ArrayList<ReportDTO>();
		for (Transaction txn : txnList) {
			dtoList.add(convertReport(txn));
		}
		return dtoList;
	}

	public static ReportDTO convertReport(Transaction txn) {
		SimpleDateFormat time = new SimpleDateFormat("YYYY-MM-dd");
		ReportDTO dto = new ReportDTO();
		
		return dto;
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
		user.setTimeZone(userDto.getTimeZone());
		

		return user;

	}

	public static DishHomeDto convertDishHome(DishHome dishHome) {
		DishHomeDto dishHomeDto = new DishHomeDto();
		dishHomeDto.setRequestDetail(dishHome.getRequestDetail());
		dishHomeDto.setResponseDetail(dishHome.getResponseDetail());
		dishHomeDto.setBalance(dishHome.getRequestDetail().get("Balance"));
		return dishHomeDto;
	}

}
*/