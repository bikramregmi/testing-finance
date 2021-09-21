package com.mobilebanking.model.error;

public class CustomerError {
	
	private String firstName;
	
	private String lastName;
	
	private String addressOne;
	
	private String email;
	
	private String state;
	
	private String city;
	
	private String bankBranch;
	
	private String accountNumber;
	
	private String accountType;
	
	private String landline;
	
	private String mobileNumber;
	
	private boolean valid;
	
	private String licenseCount;
	
	private String customerProfile;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getLicenseCount() {
		return licenseCount;
	}

	public void setLicenseCount(String licenseCount) {
		this.licenseCount = licenseCount;
	}

	public String getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(String customerProfile) {
		this.customerProfile = customerProfile;
	}
}
