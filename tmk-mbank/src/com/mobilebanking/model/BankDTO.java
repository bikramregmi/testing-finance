package com.mobilebanking.model;

import java.util.ArrayList;
import java.util.List;

public class BankDTO {
	
	private Long id;
	
	private String	name;
	
	private String address;
	
	private String city;
	
	private String swiftCode;
	
	private String state;
	
	private Long channelPartner;
	
	private String channelPartnerName;
	
	private String[] merchants;
	
	private List<MerchantDTO> merchant = new ArrayList<MerchantDTO>();
	
	private Integer licenseCount;
	
	private String licenseExpiryDate;
	
	private Long createdById;
	
	private String createdBy;
	
	private String email;
	
	private String mobileNumber;
	
	private Double balance;
	
	private Integer smsCount;
	
	private String operatorAccountNumber;
	
	private String isoUrl;
	
	private String portNumber;
	
	private String channelPartnerAccountNumber;
	
	private String cardAcceptorTerminalIdentification;
	
	private String acquiringInstitutionIdentificationCode;
	
	private String merchantType;
	
	private String bankTranferAccount;
	
	private String userTemplate;
	
	private Double remainingBalance;
	
	private Double creditLimit;
	
	private Boolean isDedicated;
	
	private String desc1;
	
	private String accountNumber;
	
	private Status cbsStatus;

	private String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getChannelPartner() {
		return channelPartner;
	}

	public void setChannelPartner(Long channelPartner) {
		this.channelPartner = channelPartner;
	}

	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}

	public String[] getMerchants() {
		return merchants;
	}

	public void setMerchants(String[] merchants) {
		this.merchants = merchants;
	}

	public List<MerchantDTO> getMerchant() {
		return merchant;
	}

	public void setMerchant(List<MerchantDTO> merchant) {
		this.merchant = merchant;
	}

	public Integer getLicenseCount() {
		return licenseCount;
	}

	public void setLicenseCount(Integer licenseCount) {
		this.licenseCount = licenseCount;
	}

	public String getLicenseExpiryDate() {
		return licenseExpiryDate;
	}

	public void setLicenseExpiryDate(String licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(Integer smsCount) {
		this.smsCount = smsCount;
	}

	public String getOperatorAccountNumber() {
		return operatorAccountNumber;
	}

	public void setOperatorAccountNumber(String operatorAccountNumber) {
		this.operatorAccountNumber = operatorAccountNumber;
	}

	public String getIsoUrl() {
		return isoUrl;
	}

	public void setIsoUrl(String isoUrl) {
		this.isoUrl = isoUrl;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getChannelPartnerAccountNumber() {
		return channelPartnerAccountNumber;
	}

	public void setChannelPartnerAccountNumber(String channelPartnerAccountNumber) {
		this.channelPartnerAccountNumber = channelPartnerAccountNumber;
	}

	public String getCardAcceptorTerminalIdentification() {
		return cardAcceptorTerminalIdentification;
	}

	public void setCardAcceptorTerminalIdentification(String cardAcceptorTerminalIdentification) {
		this.cardAcceptorTerminalIdentification = cardAcceptorTerminalIdentification;
	}

	public String getAcquiringInstitutionIdentificationCode() {
		return acquiringInstitutionIdentificationCode;
	}

	public void setAcquiringInstitutionIdentificationCode(String acquiringInstitutionIdentificationCode) {
		this.acquiringInstitutionIdentificationCode = acquiringInstitutionIdentificationCode;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getBankTranferAccount() {
		return bankTranferAccount;
	}

	public void setBankTranferAccount(String bankTranferAccount) {
		this.bankTranferAccount = bankTranferAccount;
	}

	public String getUserTemplate() {
		return userTemplate;
	}

	public void setUserTemplate(String userTemplate) {
		this.userTemplate = userTemplate;
	}

	public Double getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(Double remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Boolean getIsDedicated() {
		return isDedicated;
	}

	public void setIsDedicated(Boolean isDedicated) {
		this.isDedicated = isDedicated;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Status getCbsStatus() {
		return cbsStatus;
	}

	public void setCbsStatus(Status cbsStatus) {
		this.cbsStatus = cbsStatus;
	}

}
