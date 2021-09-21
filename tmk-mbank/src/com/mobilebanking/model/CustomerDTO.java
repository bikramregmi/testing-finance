package com.mobilebanking.model;

import java.util.HashMap;
import java.util.List;

public class CustomerDTO {
	
	private Long id;
	
	private String fullName;
		
	private String addressOne;
	
	private String landline;
	
	private String mobileNumber;
	
//	private String customerType ;
	
	private String firstName;
	
	private String addressTwo;
	
	private String middleName;
	
	private String lastName;
	
	private String city;
	
	private String state;
	
	private String email;
	
	private String createdBy;
	
	private String addressThree;
	
	private CustomerStatus status;
	
	private String bank;
	
	private String bankBranch;
	
	private String uniqueId;
	
	private boolean gprsSubscribed;
	
	private boolean smsSubscribed;
	
	private boolean alertType;
	
	private boolean mobileBanking;
	
	private List<SmsType> smsType;
	
	private boolean appService ;
	
	private boolean smsService ;
	
	private String bankBranchCode;
	
	private List<String> accountNumbers;
	
	private List<AccountMode> accountModes;
	
	private List<String> accountBranch;
	
	private String accountNumber;
	
	private AccountMode accountMode;
	
	private boolean appVerification = false ;
	
	private boolean isRegistered;
	
	private boolean beneficiaryFlag;
	
	private String mPin;
	
	private String uuid;
	
	private String deviceToken;
	
	private String created;
	
	private String lastModified;
	
	private Long customerProfileId;
	
	private String comment;
	
	private String commentedBy;

	private String approvalMessage;
	
	private String bankCode;
	
	private List<HashMap<String, String>> accountDetail;
	
	private long oauthTokenCount;
	
	private boolean firebaseToken;
	
	private Status userStatus;
	
	private Integer daysAfterRenew;
	
	private String lastRenewDate;
	
	private String expiredDate;
	
	public String getmPin() {
		return mPin;
	}

	public void setmPin(String mPin) {
		this.mPin = mPin;
	}

	public boolean isBeneficiaryFlag() {
		return beneficiaryFlag;
	}

	public void setBeneficiaryFlag(boolean beneficiaryFlag) {
		this.beneficiaryFlag = beneficiaryFlag;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getMiddleName() {
		return middleName;
	}
	

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getAddressThree() {
		return addressThree;
	}

	public void setAddressThree(String addressThree) {
		this.addressThree = addressThree;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public AccountMode getAccountMode() {
		return accountMode;
	}

	public void setAccountMode(AccountMode accountMode) {
		this.accountMode = accountMode;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public boolean isGprsSubscribed() {
		return gprsSubscribed;
	}

	public void setGprsSubscribed(boolean gprsSubscribed) {
		this.gprsSubscribed = gprsSubscribed;
	}

	public boolean isSmsSubscribed() {
		return smsSubscribed;
	}

	public void setSmsSubscribed(boolean smsSubscribed) {
		this.smsSubscribed = smsSubscribed;
	}

	public boolean isAlertType() {
		return alertType;
	}

	public void setAlertType(boolean alertType) {
		this.alertType = alertType;
	}

	public boolean isMobileBanking() {
		return mobileBanking;
	}

	public void setMobileBanking(boolean mobileBanking) {
		this.mobileBanking = mobileBanking;
	}

	public List<SmsType> getSmsType() {
		return smsType;
	}

	public void setSmsType(List<SmsType> smsType) {
		this.smsType = smsType;
	}

	public boolean isAppService() {
		return appService;
	}

	public void setAppService(boolean appService) {
		this.appService = appService;
	}

	public boolean isSmsService() {
		return smsService;
	}

	public void setSmsService(boolean smsService) {
		this.smsService = smsService;
	}

	public List<String> getAccountNumbers() {
		return accountNumbers;
	}

	public void setAccountNumbers(List<String> accountNumbers) {
		this.accountNumbers = accountNumbers;
	}

	public List<AccountMode> getAccountModes() {
		return accountModes;
	}

	public void setAccountModes(List<AccountMode> accountModes) {
		this.accountModes = accountModes;
	}

	public List<String> getAccountBranch() {
		return accountBranch;
	}

	public void setAccountBranch(List<String> accountBranch) {
		this.accountBranch = accountBranch;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public boolean isAppVerification() {
		return appVerification;
	}

	public void setAppVerification(boolean appVerification) {
		this.appVerification = appVerification;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public Long getCustomerProfileId() {
		return customerProfileId;
	}

	public void setCustomerProfileId(Long customerProfileId) {
		this.customerProfileId = customerProfileId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
	}

	public String getApprovalMessage() {
		return approvalMessage;
	}

	public void setApprovalMessage(String approvalMessage) {
		this.approvalMessage = approvalMessage;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public List<HashMap<String, String>> getAccountDetail() {
		return accountDetail;
	}

	public void setAccountDetail(List<HashMap<String, String>> accountDetail) {
		this.accountDetail = accountDetail;
	}

	public long getOauthTokenCount() {
		return oauthTokenCount;
	}

	public void setOauthTokenCount(long oauthTokenCount) {
		this.oauthTokenCount = oauthTokenCount;
	}

	public boolean getFirebaseToken() {
		return firebaseToken;
	}

	public void setFirebaseToken(boolean firebaseToken) {
		this.firebaseToken = firebaseToken;
	}

	public Status getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Status userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getDaysAfterRenew() {
		return daysAfterRenew;
	}

	public void setDaysAfterRenew(Integer daysAfterRenew) {
		this.daysAfterRenew = daysAfterRenew;
	}

	public String getLastRenewDate() {
		return lastRenewDate;
	}

	public void setLastRenewDate(String lastRenewDate) {
		this.lastRenewDate = lastRenewDate;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}


}
