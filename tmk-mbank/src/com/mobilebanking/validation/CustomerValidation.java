package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.AccountMode;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.error.CustomerError;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.CustomerRepository;
import com.mobilebanking.util.AuthenticationUtil;

@Component
public class CustomerValidation {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IBankBranchApi branchApi;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private BankBranchRepository branchRepository;

	@Autowired
	private ICustomerBankAccountApi customerBankAccountApi;

	@Autowired
	private CustomerRepository customerRepository;

	private boolean valid;

	public CustomerError customerValidation(CustomerDTO customerDTO) {
		CustomerError error = new CustomerError();
		valid = true;

		error.setFirstName(checkFirstName(customerDTO.getFirstName()));
		error.setLastName(checkLastName(customerDTO.getLastName()));
		error.setAddressOne(checkAddressOne(customerDTO.getAddressOne()));
		if (customerDTO.getEmail() != null && !(customerDTO.getEmail().trim().equals(""))) {
			error.setEmail(checkEmail(customerDTO.getEmail()));
		}
		error.setState(checkState(customerDTO.getState()));
		error.setCity(checkCity(customerDTO.getCity()));
		error.setBankBranch(checkBranch(customerDTO.getBankBranch()));
		error.setAccountNumber(
				checkAccountNumber(customerDTO.getAccountNumber(), Long.parseLong(customerDTO.getBankBranch())));
		error.setAccountType(checkAccountType(customerDTO.getAccountMode()));
		error.setCustomerProfile(checkCustomerProfile(customerDTO.getCustomerProfileId()));
		if (customerDTO.getLandline() != null && !(customerDTO.getLandline().trim().equals(""))) {
			error.setLandline(checkLandline(customerDTO.getLandline()));
		}
		error.setMobileNumber(checkMobileNumber(customerDTO.getMobileNumber(), customerDTO.getBankBranch()));
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType().equals(UserType.Bank)) {
			Bank bank = bankRepository.findOne(currentUser.getAssociatedId());
			error.setLicenseCount(checkLicenseCount(bank.getLicenseCount()));
		} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
			BankBranch branch = branchRepository.findOne(currentUser.getAssociatedId());
			error.setLicenseCount(checkLicenseCount(branch.getBank().getLicenseCount()));
		}
		error.setValid(valid);
		return error;

	}

	private String checkCustomerProfile(Long customerProfileId) {

		if (customerProfileId == null) {
			valid = false;
			return "Please select customer profile";
		}
		return null;
	}

	private String checkFirstName(String firstName) {
		if (firstName == null) {
			valid = false;
			return "Invalid First Name";
		} else if (firstName.trim().equals("")) {
			valid = false;
			return "Invalid First Name";
		}
		return null;
	}

	private String checkLastName(String lastName) {
		if (lastName == null) {
			valid = false;
			return "Invalid Last Name";
		} else if (lastName.trim().equals("")) {
			valid = false;
			return "Invalid Last Name";
		}
		return null;
	}

	private String checkAddressOne(String addressOne) {
		if (addressOne == null) {
			valid = false;
			return "Invalid Address";
		} else if (addressOne.trim().equals("")) {
			valid = false;
			return "Invalid Address";
		}
		return null;
	}

	private String checkEmail(String email) {
		if (!email.contains("@") || !email.contains(".com")) {
			valid = false;
			return "Invalid Email";
		}
		return null;
	}

	private String checkState(String state) {
		if (state == null) {
			valid = false;
			return "Invalid State";
		} else if (state.trim().equals("")) {
			valid = false;
			return "Invalid State";
		}
		return null;
	}

	private String checkCity(String city) {
		if (city == null) {
			valid = false;
			return "Invalid City";
		} else if (city.trim().equals("")) {
			valid = false;
			return "Invalid City";
		} else if (isNotANumber(city)) {
			valid = false;
			return "Invalid City";
		}
		return null;
	}

	private String checkBranch(String branch) {
		if (branch == null) {
			valid = false;
			return "Invalid Branch";
		} else if (branch.trim().equals("")) {
			valid = false;
			return "Invalid Branch";
		} else if (isNotANumber(branch)) {
			valid = false;
			return "Invalid Branch";
		}
		return null;
	}

	public String checkAccountNumber(String accountNo, long bankBranchId) {
		if (accountNo == null) {
			valid = false;
			return "Invalid Account Number";
		} else if (accountNo.trim().equals("")) {
			valid = false;
			return "Invalid Account Number";
		} else {
			BankBranchDTO bankBranch = branchApi.findBranchById(bankBranchId);
			CustomerBankAccountDTO customerAccount = customerBankAccountApi
					.findCustomerBankAccountByAccountNumberAndBank(bankBranch.getBranchCode() + accountNo, bankBranch.getBankId());
			if (customerAccount != null) {
				valid = false;
				return "Account Number Already Exists";
			}
		}
		return null;
	}

	private String checkAccountType(AccountMode accountMode) {
		if (accountMode == null) {
			valid = false;
			return "Invalid Account Type";
		}
		return null;
	}

	private String checkLandline(String landline) {
		if (isNotANumber(landline)) {
			valid = false;
			return "Invalid Landline";
		}
		return null;
	}

	private String checkMobileNumber(String mobileNo, String branchId) {
		if (mobileNo == null) {
			valid = false;
			return "Invalid Mobile Number";
		} else if (mobileNo.trim().equals("")) {
			valid = false;
			return "Invalid Mobile Number";
		} else if (isNotANumber(mobileNo)) {
			valid = false;
			return "Invalid Mobile Number";
		} else if (mobileNo.length() != 10) {
			valid = false;
			return "Mobile Number Must Be 10 digit long";
		} else {
			BankBranch bankBranch = branchRepository.findOne(Long.parseLong(branchId));
			Customer getcustomer = customerRepository.findByMobileNumberAndBank(mobileNo, bankBranch.getBank());
			if (getcustomer != null) {
				valid = false;
				return "Customer with this Mobile Number already exist";
			}
		}

		return null;
	}

	private String checkLicenseCount(int licenseCount) {
		if (!(licenseCount > 0)) {
			valid = false;
			return "Bank Has Ran Out Of LicenseCount. Please Contact The Admininstrator";
		}
		return null;
	}

	public String checkLicenseCountForBulkUpload(int customerCount) {
		int licenseCount = 0;
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser.getUserType().equals(UserType.Bank)) {
			Bank bank = bankRepository.findOne(currentUser.getAssociatedId());
			licenseCount = bank.getLicenseCount();
		} else if (currentUser.getUserType().equals(UserType.BankBranch)) {
			BankBranch branch = branchRepository.findOne(currentUser.getAssociatedId());
			licenseCount = branch.getBank().getLicenseCount();
		}
		if (licenseCount < customerCount) {
			return "Bank Has Ran Out Of LicenseCount. Please Contact The Admininstrator";
		}
		return null;
	}

	private boolean isNotANumber(String string) {
		try {
			Long.parseLong(string);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	public CustomerError customerEditValidation(CustomerDTO customerDTO) {
		CustomerError error = new CustomerError();
		valid = true;

		error.setFirstName(checkFirstName(customerDTO.getFirstName()));
		error.setLastName(checkLastName(customerDTO.getLastName()));
		error.setAddressOne(checkAddressOne(customerDTO.getAddressOne()));
		error.setState(checkState(customerDTO.getState()));
		error.setCity(checkCity(customerDTO.getCity()));
		error.setCustomerProfile(checkCustomerProfile(customerDTO.getCustomerProfileId()));
		error.setValid(valid);
		return error;

	}
}
