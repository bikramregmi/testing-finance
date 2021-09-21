package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.CustomerBankAccount;
import com.mobilebanking.entity.CustomerProfile;
import com.mobilebanking.entity.Transaction;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.AccountMode;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.CustomerBankAccountRepository;
import com.mobilebanking.repositories.CustomerProfileRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.repositories.TransactionRepository;
import com.mobilebanking.repositories.UserRepository;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.PasswordGenerator;

@Controller
public class TestController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerProfileRepository customerProfileRepo;

	@Autowired
	private CustomerBankAccountRepository bankAccountRepo;

	@Autowired
	private PasswordGenerator passwordGenerator;

	@RequestMapping(method = RequestMethod.GET, value = "profile/update/uniqueid")
	public ResponseEntity<HashMap<String, String>> updateUniqueId() {
		HashMap<String, String> response = new HashMap<>();
		if (AuthenticationUtil.getCurrentUser() != null) {
			for (CustomerProfile profile : customerProfileRepo.findAll()) {
				if (profile.getProfileUniqueId() == null) {
					CustomerProfile customerProfile = customerProfileRepo.findById(profile.getId());
					if(customerProfile == null)
						customerProfile = customerProfileRepo.findOne(profile.getId());
					String uniqueId = passwordGenerator.generateProfileUniqueId();
					customerProfile.setProfileUniqueId(uniqueId);
					customerProfileRepo.save(customerProfile);
					response.put(uniqueId, customerProfile.getId() + " " + customerProfile.getName());
				}
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/updateApproval")
	public ResponseEntity<String> updateMakerChecker() {

		int i = 0, j = 0;

		List<Customer> customerList = customerRepository.findAll();
		for (Customer customer : customerList) {
			if (customer.getStatus().equals(CustomerStatus.Approved)) {
				i++;
				customer.setFirstApprove(true);
				customer = customerRepository.save(customer);
				if (customer.getUser().getStatus().equals(Status.Inactive)) {
					j++;
					User user = customer.getUser();
					user.setStatus(Status.Active);
					user = userRepository.save(user);
				}
			}

		}

		return new ResponseEntity<>("Customer update " + i + " user update " + j, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/updateRefStan")
	public ResponseEntity<String> updateRefStan() {
		List<Transaction> tranasctionList = transactionRepository.findAll();
		Integer success = 0;
		Integer failure = 0;
		Integer responseEmpty = 0;
		Integer refstanNull = 0;
		for (Transaction transaction : tranasctionList) {
			if (transaction.getResponseDetail() != null && !transaction.getResponseDetail().isEmpty()) {
				try {
					String refstan = transaction.getResponseDetail().get("RefStan");
					if (refstan != null) {
						transaction.setRefstan(refstan);
						transactionRepository.save(transaction);
						success++;
					}
					refstanNull++;
				} catch (Exception e) {
					e.printStackTrace();
					failure++;
				}
			} else {
				responseEmpty++;
			}
		}
		return new ResponseEntity<>("success: " + success + "\nfailure: " + failure + "\nResponse Null/Empty: "
				+ responseEmpty + "\nRefStan Null: " + refstanNull, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET, value =
	 * "/customer/profile/create/default") public ResponseEntity<String>
	 * defaultProfile() { CustomerProfile profile = new CustomerProfile();
	 * profile.setName("DEFAULT"); profile.setDailyTransactionLimit(10L);
	 * profile.setDailyTransactionAmount(5000.00);
	 * profile.setWeeklyTransactionLimit(50000.00);
	 * profile.setMonthlyTransactionLimit(500000.00)
	 * customerProfileRepo.save(profile); return new
	 * ResponseEntity<>("success: " + success + "\nfailure: " + failure +
	 * "\nResponse Null/Empty: " + responseEmpty + "\nRefStan Null: " +
	 * refstanNull, HttpStatus.OK); }
	 */

	/*
	 * @RequestMapping(value="/sendMail", method=RequestMethod.GET) public
	 * ResponseEntity<String> sendMail(@RequestParam("m") String mailAddress){
	 * try { emailApi.sendEmail("test Mail", mailAddress); return new
	 * ResponseEntity<>("Success",HttpStatus.OK); } catch (MessagingException e)
	 * { e.printStackTrace(); return new
	 * ResponseEntity<>(e.getMessage(),HttpStatus.OK); } }
	 */

	@RequestMapping(value = "/customer/set/profile/default", method = RequestMethod.GET)
	public ResponseEntity<String> addDefaultProfile() {
		int total = 0;
		int hasProfile = 0;
		int success = 0;
		int error = 0;
		CustomerProfile defaultProfile = customerProfileRepo.findById(1L);
		List<Customer> customerList = customerRepository.findAll();
		for (Customer customer : customerList) {
			total++;
			try {
				if (customerProfileRepo.findByCustomer(customer) == null) {
					defaultProfile.getCustomer().add(customer);
					success++;
				} else {
					hasProfile++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				error++;
			}
		}
		defaultProfile = customerProfileRepo.save(defaultProfile);
		String response = "total: " + total + "<br/>success: " + success + "<br/>Already in profile: " + hasProfile
				+ "<br/>Error: " + error;
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/customer/set/profile", method = RequestMethod.GET)
	public ResponseEntity<String> addkisanProfile(@RequestParam("bank") Long bankId,
			@RequestParam("profile") long profileId) {
		int total = 0;
		int hasProfile = 0;
		int success = 0;
		int error = 0;
		int notApprove = 0;
		CustomerProfile kisanProfile = customerProfileRepo.findById(profileId);
		List<Customer> customerList = customerRepository.listCustomerByBank(bankId);
		for (Customer customer : customerList) {
			total++;
			try {
				if (!kisanProfile.getCustomer().contains(customer)) {
					kisanProfile.getCustomer().add(customer);
					success++;
				} else {
					hasProfile++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				error++;
			}
		}
		kisanProfile = customerProfileRepo.save(kisanProfile);
		String response = "total: " + total + "<br/>success: " + success + "<br/>Already in profile: " + hasProfile
				+ "<br/>Error: " + error + "</br>not approved: " + notApprove;
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/customer/set/fullname", method = RequestMethod.GET)
	public ResponseEntity<String> setFullName() {
		int total = 0;
		int hasmidName = 0;
		int success = 0;
		int error = 0;
		List<Customer> customerList = customerRepository.findAll();
		for (Customer customer : customerList) {
			total++;
			try {
				if (customer.getMiddleName() != null && !(customer.getMiddleName().trim().equals(""))) {
					customer.setFullName(
							customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getLastName());
					hasmidName++;
				} else {
					customer.setFullName(customer.getFirstName() + " " + customer.getLastName());
				}
				customerRepository.save(customer);
				success++;
			} catch (Exception e) {
				e.printStackTrace();
				error++;
			}
		}
		String response = "total: " + total + "<br/>success: " + success + "<br/>Has Mid Name: " + hasmidName
				+ "<br/>Error: " + error;
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/customer/set/primary/account", method = RequestMethod.GET)
	public ResponseEntity<String> setPrimaryAccount() {
		List<Customer> customerList = customerRepository.findAll();
		int total = 0;
		int hasPrimaryAccount = 0;
		int noPrimaryAccount = 0;
		int error = 0;
		int success = 0;
		int noAccount = 0;
		int moreThanOneSavingAccount = 0;
		List<String> errorList = new ArrayList<>();
		for (Customer customer : customerList) {
			try {
				total++;
				if (bankAccountRepo.findPrimaryAccount(customer, true) == null) {
					noPrimaryAccount++;
					List<CustomerBankAccount> accountList = bankAccountRepo
							.findCustomerBankAccountOfCustomerAndAccountMode(customer.getId(), AccountMode.SAVING);
					if (accountList != null && !accountList.isEmpty() && !(accountList.size() > 1)) {
						CustomerBankAccount account = bankAccountRepo.findOne(accountList.get(0).getId());
						account.setPrimary(true);
						bankAccountRepo.save(account);
						success++;
					} else {
						if (accountList == null || accountList.isEmpty()) {
							noAccount++;
						} else if (accountList.size() > 1) {
							moreThanOneSavingAccount++;
						}
					}
				} else {
					hasPrimaryAccount++;
				}
			} catch (Exception e) {
				errorList.add(e.getMessage());
				error++;
			}
		}
		String response = "total = " + total + "</br>Has Primary Account = " + hasPrimaryAccount
				+ "</br>noPrimaryAccount = " + noPrimaryAccount + "</br>No Account = " + noAccount
				+ "</br>More Than One Saving Account = " + moreThanOneSavingAccount + "</br>success = " + success
				+ "</br>error = " + error + "</br>error list =" + errorList;
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
