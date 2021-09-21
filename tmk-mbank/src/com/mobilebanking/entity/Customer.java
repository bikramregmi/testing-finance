package com.mobilebanking.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.mobilebanking.model.CustomerStatus;

/**
 * @author bibek
 *
 */
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@Column
	private String fullName;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String middleName;

	@Column
	private String address;

	@Column
	private String addressTwo;

	@Column
	private String addressThree;

	@Column
	private String phoneNumber;

	@Column
	private String mobileNumber;

	@ManyToOne
	private City city;

	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	@Column
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@Column
	private CustomerStatus status;

	@Column(unique = true)
	private String uniqueId;

	@Column(nullable = true)
	private boolean gprsSubscribed = true;

	@Column(nullable = true)
	private boolean smsSubscribed = true;

	@Column
	private boolean alertType = true;

	@Column
	private boolean mobileBanking = true;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SmsMode> smsMode;

	@Column(nullable = true)
	private Long creratedTimeMilliSeconds;

	@Column(nullable = true)
	private boolean appService = false;

	@Column(nullable = true)
	private boolean smsService = false;

	@Column(nullable = true)
	private boolean appVerification = false;

	private Boolean isRegstered;

	@ManyToOne(fetch = FetchType.EAGER)
	private BankBranch bankBranch;

	@ManyToOne(fetch = FetchType.EAGER)
	private Bank bank;

	private String comment;

	private String commentedby;

	private Boolean firstApprove;
	
	private String approvalMessage;
	
	private Boolean hasTransaction;
	
	private Date lastRenewed;
	
	private Date expiredDate;
	
	public String getApprovalMessage() {
		return approvalMessage;
	}

	public void setApprovalMessage(String approvalMessage) {
		this.approvalMessage = approvalMessage;
	}

	public Boolean getIsRegstered() {
		return isRegstered;
	}

	public void setIsRegstered(Boolean isRegstered) {
		this.isRegstered = isRegstered;
	}

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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getAddressThree() {
		return addressThree;
	}

	public void setAddressThree(String addressThree) {
		this.addressThree = addressThree;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
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

	public List<SmsMode> getSmsMode() {
		return smsMode;
	}

	public void setSmsMode(List<SmsMode> smsMode) {
		this.smsMode = smsMode;
	}

	public Long getCreratedTimeMilliSeconds() {
		return creratedTimeMilliSeconds;
	}

	public void setCreratedTimeMilliSeconds(Long creratedTimeMilliSeconds) {
		this.creratedTimeMilliSeconds = creratedTimeMilliSeconds;
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

	public boolean isAppVerification() {
		return appVerification;
	}

	public void setAppVerification(boolean appVerification) {
		this.appVerification = appVerification;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentedby() {
		return commentedby;
	}

	public void setCommentedby(String commentedby) {
		this.commentedby = commentedby;
	}

	public Boolean getFirstApprove() {
		return firstApprove;
	}

	public void setFirstApprove(Boolean firstApprove) {
		this.firstApprove = firstApprove;
	}

	public Boolean getHasTransaction() {
		return hasTransaction;
	}

	public void setHasTransaction(Boolean hasTransaction) {
		this.hasTransaction = hasTransaction;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getLastRenewed() {
		return lastRenewed;
	}

	public void setLastRenewed(Date lastRenewed) {
		this.lastRenewed = lastRenewed;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

}
