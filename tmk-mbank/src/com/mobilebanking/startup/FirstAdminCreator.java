package com.mobilebanking.startup;


import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mobilebanking.api.IGeneralSettingsApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Country;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.AccountType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.CountryRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.GeneralSettingsRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.repositories.UserTemplateRepository;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;




public class FirstAdminCreator /*implements WebApplicationInitializer*/{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private MenuCreation menuCreation;
	
	@Autowired 
	private UserTemplateRepository userTemplateRepository;
	
	@Autowired
	private IGeneralSettingsApi generalSettingsApi;

	@Autowired
	private GeneralSettingsRepository settingsRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void create() {
		
		
		menuCreation.startupCreator();
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Kathmandu");
		logger.debug("tz==>" + timeZone);
		logger.debug("tz.getId()==>" + timeZone.getID());

		createUser(StringConstants.USER_ADMIN, UserType.Admin,
				Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED, "123456", Status.Active, timeZone, "AdminRole");
		createUser(StringConstants.USER_SYSADMIN, UserType.Admin,
				Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED, "123456", Status.Active, timeZone, "AdminRole");
		createDefaultCountry();
		createPoolAccount();
		createCommissionPoolAccount();
		createGeneralSettings();
		 
	}
	
	private void createGeneralSettings() {
		if (settingsRepository.findSettingsByKey(StringConstants.SPARROW_SMS_CREDIT) == null)
			generalSettingsApi.save(StringConstants.SPARROW_SMS_CREDIT, String.valueOf(0));
		if (settingsRepository.findSettingsByKey(StringConstants.SPARROW_SMS_CREDIT_CONSUMED) == null)
			generalSettingsApi.save(StringConstants.SPARROW_SMS_CREDIT_CONSUMED, String.valueOf(0));
	}


	private void createCommissionPoolAccount() {
		Account account = accountRepository.findAccountByAccountHeadAndUserType(
				StringConstants.POOL_COMMISSION_ACCOUNT, AccountType.COMMISSION_POOL);
		if (account == null) {
			account = new Account();
			account.setAccountNumber("COMMISSIONPOOL");
			account.setAccountHead(StringConstants.POOL_COMMISSION_ACCOUNT);
			account.setAccountType(AccountType.COMMISSION_POOL);
			account.setBalance(0.0);
			accountRepository.save(account);
		}
	}
	
	private void createPoolAccount() {
		Account account = accountRepository.findAccountByAccountHeadAndUserType(StringConstants.POOL_ACCOUNT_HEAD, AccountType.POOL);
		if (account == null) {
			account = new Account();
			account.setAccountNumber("MBANKPOOL");
			account.setAccountHead(StringConstants.POOL_ACCOUNT_HEAD);
			account.setAccountType(AccountType.POOL);
			account.setBalance(0.0);
			User user = userRepository.findByUsername(StringConstants.USER_ADMIN);
			account.setUser(user);
			accountRepository.save(account);
		}
		
	}

	private void createUser(String userName, UserType userType, String authorities, String password, Status status,
			TimeZone timeZone, String role) {
			
			User user = userRepository.findByUsername(userName);
			if (user == null) {
				user = new User();
				user.setUserName(userName);
				user.setUserType(userType);
				user.setAuthority(authorities);
				user.setPassword(passwordEncoder.encode(password));
				user.setCreated(new Date());
				user.setStatus(status);
				user.setUserTemplate(userTemplateRepository.findByUserTemplate(role));
				
				userRepository.save(user);

			}
	}
	
	private void createDefaultCountry() {
		List<Country> countryList = countryRepository.findCountry();
		if (countryList == null || countryList.isEmpty()) {
			Country country = new Country();
			country.setName("Nepal");
			country.setDialCode("00977");
			country.setIsoThree("NPL");
			country.setIsoTwo("NP");
			country.setStatus(Status.Active);
			country.setCurrencyCode("NRs");
			try {
				countryRepository.save(country);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	@PostConstruct
	public void createParkingAccount() {
		Account account = accountRepository.findAccountByAccountNumber("parkingAccount");

		if (account == null) {
			account = new Account();
			account.setAccountNumber("parkingAccount");
			account.setAccountType(AccountType.PARKING);
			account.setAccountHead("Parking Account");
			account.setBalance(0.00);
			accountRepository.save(account);
		}

	}
	
	@PostConstruct
	public void createCashAccount() {
		Account account = accountRepository.findAccountByAccountNumber("cashAccount");

		if (account == null) {
			account = new Account();
			account.setAccountNumber("cashAccount");
			account.setAccountType(AccountType.CASH);
			account.setAccountHead("Cash Account");
			account.setBalance(0.00);
			accountRepository.save(account);
		}

	}
/*
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		  AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
	        appContext.register(ApplicationContextConfig.class);
	 
	        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher",
	                new DispatcherServlet(appContext));
	        dispatcher.setLoadOnStartup(1);
	        dispatcher.addMapping("/");
	 
		
		
		FilterRegistration.Dynamic fr=servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		  
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
	}
*/
}
