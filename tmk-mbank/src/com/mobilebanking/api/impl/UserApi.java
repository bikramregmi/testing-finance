package com.mobilebanking.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.IChannelPartnerApi;
import com.mobilebanking.api.ICustomerLogApi;
import com.mobilebanking.api.IEmailApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.City;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.FcmServerSetting;
import com.mobilebanking.entity.Merchant;
import com.mobilebanking.entity.NotificationGroup;
import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.entity.User;
import com.mobilebanking.entity.UserLog;
import com.mobilebanking.entity.UserTemplate;
import com.mobilebanking.entity.WebService;
import com.mobilebanking.fcm.PushNotification;
import com.mobilebanking.model.AccountType;
import com.mobilebanking.model.ChannelPartnerDTO;
import com.mobilebanking.model.SmsStatus;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CityRepository;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.FcmServerSettingRepository;
import com.mobilebanking.repositories.MerchantRepository;
import com.mobilebanking.repositories.NotificationGroupRepository;
import com.mobilebanking.repositories.SmsLogRepository;
import com.mobilebanking.repositories.UserLogRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.repositories.UserTemplateRepository;
import com.mobilebanking.repositories.WebServiceRepository;
import com.mobilebanking.util.AccountUtil;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.StringConstants;

@Service
public class UserApi implements IUserApi, Runnable {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WebServiceRepository webServiceRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private IBankBranchApi bankBranchApi;

	@Autowired
	private UserTemplateRepository userTemplateRepository;

	@Autowired
	private UserLogRepository userLogRepository;

	@Autowired
	private BankBranchRepository bankBranchRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ICustomerLogApi customerLogApi;

	@Autowired
	private SmsLogRepository smsLogRepository;

	@Autowired
	private BankOAuthClientRepository bankOAuthClientRepository;

	@Autowired
	private CustomerBankAccountRepository customerBankAccountRepository;

	@Autowired
	private IEmailApi emailApi;

	@Autowired
	private AccountUtil accountUtil;

	@Autowired
	private PushNotification pushNotification;

	private String deviceToken;

	@Autowired
	private NotificationGroupRepository notificationGroupRepository;

	@Autowired
	private FcmServerSettingRepository fcmServerSettingRepository;

	@Autowired
	private IChannelPartnerApi channelPartnerApi;

	@Override
	public User saveUser(UserDTO userDto) {
		City city = cityRepository.findCityByIdAndState(userDto.getState(), Long.parseLong(userDto.getCity()));
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setAddress(userDto.getAddress());
		user.setCity(city);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setUserType(userDto.getUserType());
		user.setStatus(Status.Active);
		if (userDto.getUserType().equals(UserType.Admin)) {
			user.setAuthority(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED);
		} else if (userDto.getUserType().equals(UserType.Bank)) {
			if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Admin)) {
				user.setAssociatedId(userDto.getObjectUserId());
			} else if (AuthenticationUtil.getCurrentUser().getUserType().equals(UserType.Bank)) {
				user.setAssociatedId(AuthenticationUtil.getCurrentUser().getAssociatedId());
			}
			user.setMaker(userDto.getMaker());
			user.setChecker(userDto.getChecker());
			user.setAuthority(Authorities.BANK + "," + Authorities.AUTHENTICATED);
		} else if (userDto.getUserType().equals(UserType.BankBranch)) {
			user.setAuthority(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED);
			user.setAssociatedId(userDto.getObjectUserId());
			user.setMaker(userDto.getMaker());
			user.setChecker(userDto.getChecker());
		} else if (user.getUserType().equals(UserType.CardLessBank)) {
			user.setAuthority(Authorities.CARDLESS_BANK + "," + Authorities.AUTHENTICATED);
			user.setAssociatedId(userDto.getAssociatedId());
		} else if (userDto.getUserType().equals(UserType.ChannelPartner)) {
			user.setAuthority(Authorities.CHANNELPARTNER + "," + Authorities.AUTHENTICATED);
			user.setAssociatedId(userDto.getObjectUserId());
		}

		if (userDto.getUserTemplate() != null) {
			UserTemplate u = userTemplateRepository.findByUserTemplate(userDto.getUserTemplate());
			if (u != null) {
				user.setUserTemplate(u);
			}
		}
		try {
			user = userRepository.save(user);
			UserLog userLog = new UserLog();
			userLog.setUser(user);
			userLog.setRemarks("User Created ");
			userLogRepository.save(userLog);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (user.getUserType().equals(UserType.BankBranch)) {
			BankBranch bankBranch = bankBranchRepository.findOne(user.getAssociatedId());
			emailApi.sendEmail(UserType.BankBranch, user.getUserName(), bankBranch.getEmail(), userDto.getPassword(),
					bankBranch.getBank().getName());
		} else if (user.getUserType().equals(UserType.Bank)) {
			Bank bank = bankRepository.findOne(user.getAssociatedId());
			emailApi.sendEmail(UserType.Bank, user.getUserName(), bank.getEmail(), userDto.getPassword(),
					bank.getName());
		} else if (user.getUserType().equals(UserType.ChannelPartner)) {
			ChannelPartnerDTO channelPartner = channelPartnerApi.findChannelPartnerById(user.getAssociatedId());

			// emailApi.sendEmail(UserType.ChannelPartner,user.getUserName(),channelPartner.getEmail(),userDto.getPassword(),channelPartner.getName());;
		}
		return user;
	}

	public void createSmsLogBank(Bank bank, SmsType smsType, User user) {
		SmsLog smsLog = new SmsLog();
		smsLog.setBank(bank);
		smsLog.setSmsForUser(UserType.Bank);
		smsLog.setSmsFor(String.valueOf(bank.getId()));
		smsLog.setSmsFromUser(UserType.Admin);
		smsLog.setSmsFrom(AuthenticationUtil.getCurrentUser().getId());
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLog.setSmsType(smsType);
		if (smsType.equals(SmsType.REGISTRATION)) {
			String message = StringConstants.REGISTRATION_SMS;
			message = message.replace("{username}", user.getUserName());
			message = message.replace("{password}", user.getSecretKey());
			smsLog.setMessage(message);
		}

		smsLogRepository.save(smsLog);
	}

	public void createSmsLogBankBranch(BankBranch bankBranch, SmsType smsType, User user) {

		SmsLog smsLog = new SmsLog();
		smsLog.setBankBranch(bankBranch);
		smsLog.setSmsForUser(UserType.BankBranch);
		smsLog.setSmsFor(String.valueOf(bankBranch.getId()));
		smsLog.setSmsFromUser(UserType.Admin);
		smsLog.setSmsFrom(AuthenticationUtil.getCurrentUser().getId());
		smsLog.setStatus(SmsStatus.INITIATED);
		smsLog.setSmsType(smsType);
		if (smsType.equals(SmsType.REGISTRATION)) {
			String message = StringConstants.REGISTRATION_SMS;
			message = message.replace("{username}", user.getUserName());
			message = message.replace("{password}", user.getSecretKey());
			smsLog.setMessage(StringConstants.REGISTRATION_SMS);
		} else if (smsType.equals(SmsType.NOTIFICATION)) {
			String message = StringConstants.REGISTRATION_USERNAME.replace("{username}", user.getUserName());
			message = message.replace("{password}", user.getSecretKey());
			smsLog.setMessage(message);
		}

		smsLogRepository.save(smsLog);
	}

	@Override
	public void editUser(UserDTO userDto) throws IOException, JSONException {
		User user = userRepository.findOne(userDto.getId());

		user = ConvertUtil.convertDtoToUser(userDto, user);
		user.setUserTemplate(userTemplateRepository.findByUserTemplate(userDto.getUserTemplate()));
		userRepository.save(user);

	}

	public void changePassword(long userId, String newPassword) throws IOException, JSONException {
		User user = userRepository.findOne(userId);

		user.setPassword(passwordEncoder.encode(newPassword));

		user = userRepository.save(user);
		UserLog userLog = new UserLog();
		userLog.setUser(user);
		userLog.setRemarks("Password Changed");
		userLogRepository.save(userLog);
	}

	public void createAccountForUser(User user, double balance) throws Exception {
		Account account = new Account();
		if (user.getUserType().equals(UserType.Bank) || user.getUserType().equals(UserType.BankBranch)) {
			account.setAccountType(AccountType.BANK);
			if (user.getUserType().equals(UserType.BankBranch)) {
				BankBranch bBranch = bankBranchRepository.findOne(user.getAssociatedId());
				account.setBank(bBranch.getBank());
				account.setAccountHead(bBranch.getBank().getName());
			} else {
				Bank bank = bankRepository.findOne(user.getAssociatedId());
				account.setAccountHead(bank.getName());
			}
		} else if (user.getUserType().equals(UserType.Merchant)) {
			account.setAccountType(AccountType.MERCHANT);
			Merchant merchant = merchantRepository.findOne(user.getAssociatedId());
			account.setMerchant(merchant);
			account.setAccountHead(merchant.getName());
		}
		account.setAccountNumber(accountUtil.generateAccountNumber());
		account.setUser(user);
		account.setBalance(balance);
		account.setUser(user);

		accountRepository.save(account);
	}

	@Override
	public List<UserDTO> getAllUser() {
		List<User> copy = ConvertUtil.convertIterableToList(userRepository.findAll());
		return ConvertUtil.ConvertUserToDTO(copy);
	}

	@Override
	public UserDTO getUserWithId(long userId) {
		User user = userRepository.findOne(userId);
		return ConvertUtil.convertUser(user);

	}

	@Override
	public List<UserDTO> findAllUserExceptAdmin() {
		List<User> copy = ConvertUtil.convertIterableToList(userRepository.findAllUserExceptAdmin());
		return ConvertUtil.ConvertUserToDTO(copy);

	}

	public List<UserDTO> findUser() {
		List<User> userList = new ArrayList<User>();
		userList = userRepository.findUser();
		return ConvertUtil.ConvertUserToDTO(userList);
	}

	@Override
	public List<UserDTO> findAllUserExceptDefaultAdmin() {
		List<String> str = new ArrayList<>();
		str.add("admin");
		str.add("sysadmin");
		str.add(AuthenticationUtil.getCurrentUser().getUserName());
		List<User> copy = ConvertUtil.convertIterableToList(userRepository.findAllUserExceptDefaultAdmin(str));
		return ConvertUtil.ConvertUserToDTO(copy);
	}

	@Override
	public Boolean deleteUser(Long userId) {
		User user = userRepository.findOne(userId);
		/*
		 * List<UserLog> userlogList = userLogRepository.findByUser(user); if
		 * (userlogList != null && !userlogList.isEmpty()) {
		 * userLogRepository.delete(userlogList); }
		 */
		user.setUserName("DELETED_USER_" + System.currentTimeMillis() + "_" + user.getUserName());
		user.setLastModified(new Date());
		user.setStatus(Status.Deleted);
		userRepository.save(user);
		return true;
	}

	@Override
	public String generateSecretKey(String clientId, String accessKey) throws ClientException {
		try {
			String secretKey = ConvertUtil.createHash(clientId, accessKey, "HmacSHA256");
			User user = userRepository.findByUsername(clientId);
			if (user != null) {
				WebService webService = webServiceRepository.findByAccessKey(accessKey);
				if (webService != null) {
					user.setSecretKey(secretKey);
					user.setWebService(webService);
					userRepository.save(user);
					return secretKey;
				} else {
					throw new ClientException("Service Down !!!");
				}
			} else {
				throw new ClientException("Service Down !!!");
			}
		} catch (Exception e) {
			throw new ClientException("Service Down !!!");
		}
	}

	@Override
	public User findByAccessAndSecretKey(String accessKey, String secretKey) {
		User u = userRepository.findByAccessAndSecretKey(accessKey, secretKey);
		return u;
	}

	@Override
	public UserDTO getUserByUserName(String userName) {
		User user = userRepository.findByUsername(userName);
		if (user != null)
			return ConvertUtil.convertUser(user);

		return null;
	}

	@Override
	public void createUser(String userName, UserType userType, String authorities, String password, Status status,
			TimeZone timeZone, long associatedId) {
		User user = userRepository.findByUsername(userName);

		if (user == null) {
			user = new User();
			user.setUserName(userName);
			user.setUserType(userType);
			user.setAuthority(authorities);
			user.setPassword(passwordEncoder.encode(password));
			user.setCreated(new Date());
			user.setStatus(status);
			user.setAssociatedId(associatedId);
			;
			userRepository.save(user);
		}
	}

	@Override
	public Object userDetector(UserType userType, String name, long associatedId) {
		Object object = new Object();
		if (userType == UserType.Bank) {
			object = bankApi.getBankByNameLike(name);
		} else if (userType == UserType.Admin) {

		} else if (userType == UserType.BankBranch) {
			System.out.println("GETTING LISTS + ");
			// get the list of all bank branch of that particular bank
			object = bankBranchApi.listBankBranchByNameLikeAndBank(name, associatedId);
		} else if (userType == UserType.ChannelPartner) {
			object = channelPartnerApi.getChannelPartnerByNameLike(name);
		}
		return object;
	}

	@Override
	public List<UserDTO> findUserByRole(String role) {
		List<User> userList = ConvertUtil.convertIterableToList(userRepository.findUserByRole(role));
		if (userList != null && !userList.isEmpty()) {
			return ConvertUtil.ConvertUserToDTO(userList);
		}
		return null;
	}

	@Override
	public UserDTO changePassword(UserDTO userDto) throws Exception {
		User user = userRepository.findByUsername(userDto.getUserName());
		if (user.isFirstLogin() == true) {
			user.setFirstLogin(false);
		}
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user = userRepository.save(user);
		if (user.getAuthority().contains(Authorities.CUSTOMER)) {
			Customer customer = customerRepository.findOne(user.getAssociatedId());
			customerLogApi.save(customer.getUniqueId(), "Password Changed", customer.getFirstName());
		} else if (user.getUserType().equals(UserType.Bank)) {
			Bank bank = bankRepository.findOne(user.getAssociatedId());
			emailApi.sendEmail(UserType.Bank, user.getUserName(), bank.getEmail(), userDto.getPassword(),
					bank.getName());
		} else if (user.getUserType().equals(UserType.BankBranch)) {
			BankBranch bankBranch = bankBranchRepository.findOne(user.getAssociatedId());
			emailApi.sendEmail(UserType.BankBranch, user.getUserName(), bankBranch.getEmail(), userDto.getPassword(),
					bankBranch.getBank().getName());
		}
		UserLog userLog = new UserLog();
		userLog.setUser(user);
		userLog.setRemarks("Password Changed");
		userLogRepository.save(userLog);
		return ConvertUtil.convertUser(user);
	}

	@Override
	public UserDTO changePasswordByAdmin(UserDTO userDto) {
		User user = userRepository.findByUsername(userDto.getUserName());
		if (user.isFirstLogin() == true) {
			user.setFirstLogin(false);
		}
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user = userRepository.save(user);
		if (user.getUserType().equals(UserType.Bank)) {
			Bank bank = bankRepository.findOne(user.getAssociatedId());
			emailApi.sendEmail(UserType.Bank, user.getUserName(), bank.getEmail(), userDto.getPassword(),
					bank.getName());

		} else if (user.getUserType().equals(UserType.BankBranch)) {
			BankBranch bankBranch = bankBranchRepository.findOne(user.getAssociatedId());
			emailApi.sendEmail(UserType.BankBranch, user.getUserName(), bankBranch.getEmail(), userDto.getPassword(),
					bankBranch.getBank().getName());
		}

		UserLog userLog = new UserLog();
		userLog.setUser(user);
		userLog.setRemarks("Password Changed");
		userLogRepository.save(userLog);
		return ConvertUtil.convertUser(user);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto) {

		User user = userRepository.findOne(userDto.getId());
		user.setAddress(userDto.getAddress());
		City city = cityRepository.findCityByIdAndState(userDto.getState(), Long.parseLong(userDto.getCity()));
		user.setCity(city);
		user.setChecker(userDto.getChecker());
		user.setMaker(userDto.getMaker());
		user = userRepository.save(user);
		return ConvertUtil.convertUser(user);
	}

	@Override
	public boolean checkPassword(UserDTO userDto) {

		User user = userRepository.findByUsername(userDto.getUserName());
		if (passwordEncoder.matches(userDto.getOldPassword(), user.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public UserDTO findUserByAssociatedIdAndUserType(Long id, UserType userType) {
		User user = userRepository.findByUserTypeAndAssociatedId(userType, id);
		return ConvertUtil.convertUser(user);
	}

	@Override
	public String verifyCode(String verificationCode, String userName) {
		User user = userRepository.findByUsername(userName);
		if (user != null) {
			if (verificationCode.equals(user.getVerificationCode())) {
				Customer customer = customerRepository.findOne(user.getAssociatedId());
				if (customer == null) {
					return null;
				}
				customer.setAppVerification(true);
				try {
					customerRepository.save(customer);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return user.getToken();
			}
		}
		return null;
	}

	@Override
	public String getUserToken(String username, String clientId) {
		return userRepository.getUserToken(username);
	}

	@Override
	public boolean verifyUserAsPerbank(String userName, String clientId) {
		Customer customer = customerRepository.getCustomerByUserName(userName);
		List<CustomerBankAccount> customerAccounts = customerBankAccountRepository
				.findCustomerBankAccountOfCustomer(customer.getUniqueId());
		if (customerAccounts.isEmpty()) {
			return false;
		}
		for (int i = 0; i < customerAccounts.size(); i++) {
			Bank bank = customerAccounts.get(i).getBranch().getBank();
			String bankOAuthClient = bankOAuthClientRepository.findOAuthClientForBank(bank.getId());
			if (clientId.equals(bankOAuthClient)) {
				return true;
			}
		}
		// User user = customer.getCreatedBy();
		// Bank bank = null;
		// if (user != null && (user.getUserType() == UserType.BankBranch ||
		// user.getUserType().equals(UserType.BankBranch))) {
		// List<BankBranch> branchList =
		// bankBranchRepository.findBankBranchesByAssociateId(user.getAssociatedId());
		// for (int i=0;i<branchList.size();i++) {
		// bank = branchList.get(i).getBank();
		// break;
		// }
		// } else {
		// List<Bank> bankList =
		// bankRepository.findBankByAssociatedId(user.getAssociatedId());
		// for (int i=0;i<bankList.size();i++) {
		// bank = bankList.get(i);
		// break;
		// }
		// }
		// //now verify if the client code is of the same bank or not
		// String bankOAuthClient =
		// bankOAuthClientRepository.findOAuthClientForBank(bank.getId());
		// if (clientId.equals(bankOAuthClient)) {
		// return true;
		// }
		return false;
	}

	@Override
	public List<UserDTO> findBranchUserByBank(long bankId) {
		List<User> userList = userRepository.findBranchUserByBank(bankId, UserType.BankBranch);
		if (userList != null) {
			return ConvertUtil.ConvertUserToDTO(userList);
		}
		return null;
	}

	@Override
	public List<UserDTO> findUserListByAssociatedIdAndUserType(long associatedId, UserType userType) {
		List<User> userList = userRepository.findUserListByUserTypeAndAssociatedId(userType, associatedId);
		if (userList != null) {
			return ConvertUtil.ConvertUserToDTO(userList);
		}
		return null;
	}

	@Override
	public Boolean setDeviceToken(String token, Long id, String serverKey) {
		User user = userRepository.findOne(id);
		FcmServerSetting previousfcmServerSetting = null;
		FcmServerSetting fcmServerSetting = fcmServerSettingRepository.findByBankAndFcmServerKey(user.getBank().getId(),
				serverKey);
		String previousToken = null;
		Boolean differenetDeviceToken;
		if (user.getDeviceToken() == null) {
			user.setDeviceToken(token);
			user.setServerKey(serverKey);
			user = userRepository.save(user);
			differenetDeviceToken = false;
		} else if (user.getDeviceToken().equals(token)) {
			differenetDeviceToken = false;
		} else {
			differenetDeviceToken = true;
			previousToken = user.getDeviceToken();
			if (user.getServerKey() != null) {
				previousfcmServerSetting = fcmServerSettingRepository.findByBankAndFcmServerKey(user.getBank().getId(),
						user.getServerKey());
			}
			try {
				pushNotification.unsuscribeFromTopic(previousToken, user.getBank().getSwiftCode() + "customer",
						previousfcmServerSetting.getFcmServerID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (differenetDeviceToken) {
			user = userRepository.findOne(id);
			user.setDeviceToken(token);
			user.setServerKey(serverKey);
			user = userRepository.save(user);
			try {
				// FcmServerSetting fcmServerSetting =
				// fcmServerSettingRepository.findByBankAndFcmServerKey(user.getBank(),
				// user.getSecretKey());

				pushNotification.sendNotification("New device detected", "New token detected,initiate logout.",
						previousToken, "Multiple Device", previousfcmServerSetting.getFcmServerID());

				pushNotification.suscribeToTopic(token, user.getBank().getSwiftCode() + StringConstants.CUSTOMER_TOPIC,
						fcmServerSetting.getFcmServerID());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				pushNotification.unsuscribeFromTopic(previousToken,
						user.getBank().getSwiftCode() + StringConstants.CUSTOMER_TOPIC,
						previousfcmServerSetting.getFcmServerID());
			} catch (Exception e) {
				e.printStackTrace();
			}

			Customer customer = customerRepository.findByMobileNumberAndBank(user.getSmsUserName(), user.getBank());
			List<NotificationGroup> groupList = notificationGroupRepository.findByCustomerAndBankCode(customer.getId(),
					user.getBank().getSwiftCode());
			for (NotificationGroup notificationGroup : groupList) {

				try {
					pushNotification.unsuscribeFromTopic(previousToken,
							user.getBank().getSwiftCode() + notificationGroup.getName(),
							previousfcmServerSetting.getFcmServerID());
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					pushNotification.suscribeToTopic(token, user.getBank().getSwiftCode() + notificationGroup.getName(),
							fcmServerSetting.getFcmServerID());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	@Override
	public List<UserDTO> getUserByUserNameConCat(String data) {
		List<User> userList = userRepository.findUserByUserNameCONCAT(data);
		if (userList != null) {
			return ConvertUtil.ConvertUserToDTO(userList);
		}
		return null;
	}

	@Override
	public void run() {

		deviceToken = null;

	}

	@Override
	public boolean checkDeviceToken(Long userId) {
		User user = userRepository.findOne(userId);
		return (user.getDeviceToken() != null && !(user.getDeviceToken().trim().equals(""))
				&& user.getServerKey() != null && !(user.getServerKey().trim().equals("")));
	}

	@Override
	public boolean changeBranch(Long userId, Long branchId) {
		User user = userRepository.findOne(userId);
		User newUser = new User();
		try {
			BeanUtils.copyProperties(newUser, user);
			Date date = new Date();
			user.setLastModified(date);
			user.setUserName("BRANCH_CHANGED_" + date.getTime() + "_" + user.getUserName());
			user.setStatus(Status.Deleted);
			userRepository.save(user);
			newUser.setAssociatedId(branchId);
			newUser.setChangedFromId(user.getId());
			userRepository.save(newUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
