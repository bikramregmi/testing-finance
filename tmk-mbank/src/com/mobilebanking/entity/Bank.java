package com.mobilebanking.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="bank")
public class Bank extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private String	name;
	
	@Column(nullable = false)
    private String address;
	
    @Column(nullable = false)
    private String swiftCode;
	
    @ManyToOne(fetch=FetchType.EAGER)
    private City city;

    @OneToMany(orphanRemoval=true,cascade=CascadeType.ALL, mappedBy="bank")
	private Set<BankBranch> bankBranch = new HashSet<BankBranch>();
    
    @ManyToOne(fetch=FetchType.EAGER)
    private ChannelPartner channelPartner;
    
    @Column(nullable=true)
    private Status status;
    
    @ManyToMany(fetch=FetchType.EAGER)
    List<Merchant> merchant;
    
    @Column(nullable=true)
    private int licenseCount;
    
	@Column
	private Date licenseExpiryDate;
	
	@Column(nullable=true)
	private int customerCount = 0;

	@ManyToOne
	private User createdBy;
	@Column
	private String email;
	@Column
	private Double balance;
	@Column
	private String mobileNumber;
	@Column
	private String accountNumber;
	@Column
	private Integer smsCount;
	
	@Column(nullable=true)
	private String operatorAccountNumber;
	
	@Column(nullable=true)
	private String isoUrl;
	
	@Column(nullable=true)
	private String portNumber;
	
	@Column(nullable=true)
	private String channelPartnerAccountNumber;
	
	@Column
	private String cardAcceptorTerminalIdentification;
	
	@Column
	private String acquiringInstitutionIdentificationCode;
	
	@Column
	private String merchantType;
	
	@Column
	private String bankTranferAccount;
	
	@Column
	private Boolean isAlertSent;
	
	private String desc1;

	@Column
	private String remarks;
	
	private Status cbsStatus;
	
//	@Column
//	private Boolean isDedicated;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBankTranferAccount() {
		return bankTranferAccount;
	}

	public void setBankTranferAccount(String bankTranferAccount) {
		this.bankTranferAccount = bankTranferAccount;
	}
	
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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

	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Set<BankBranch> getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(Set<BankBranch> bankBranch) {
		this.bankBranch = bankBranch;
	}
	public ChannelPartner getChannelPartner() {
		return channelPartner;
	}
	public void setChannelPartner(ChannelPartner channelPartner) {
		this.channelPartner = channelPartner;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<Merchant> getMerchant() {
		return merchant;
	}
	public void setMerchant(List<Merchant> merchant) {
		this.merchant = merchant;
	}
	public int getLicenseCount() {
		return licenseCount;
	}
	public void setLicenseCount(int licenseCount) {
		this.licenseCount = licenseCount;
	}
	public Date getLicenseExpiryDate() {
		return licenseExpiryDate;
	}
	public void setLicenseExpiryDate(Date licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}
	public int getCustomerCount() {
		return customerCount;
	}
	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public Boolean getIsAlertSent() {
		return isAlertSent;
	}

	public void setIsAlertSent(Boolean isAlertSent) {
		this.isAlertSent = isAlertSent;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public Status getCbsStatus() {
		return cbsStatus;
	}

	public void setCbsStatus(Status cbsStatus) {
		this.cbsStatus = cbsStatus;
	}
	
//	public Boolean getIsDedicated() {
//		return isDedicated;
//	}
//
//	public void setIsDedicated(Boolean isDedicated) {
//		this.isDedicated = isDedicated;
//	}

	
	
}
