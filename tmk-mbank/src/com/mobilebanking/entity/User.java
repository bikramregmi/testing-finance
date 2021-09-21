package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserType;

@Entity
@Table(name = "user")
public class User extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(unique=true, nullable = false)
	private String userName;

	@Column(nullable=false)
	private String password;
	
	@Column(nullable=true)
	private String pin;
 
	@Column(nullable=false)
	private UserType userType;

	@Column(nullable = false)
	private String authority;

	@Column(nullable=false)
	private Status status;
	
	@Column(nullable=true)
	private String secretKey;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private WebService webService; 	
	
	@Column
	private long associatedId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private UserTemplate userTemplate;
	
	@Column
	private String email;
	
	@Column(nullable=true)
	private boolean isFirstLogin = true;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private City city;

	@Column
	private String address;
	
	@Column(nullable = true)
	private String token;
	
	@Column(nullable = true)
	private String verificationCode;
	
	@Column(nullable=true)
	private Integer loginCount = 0;
	
	@Column(nullable=true)
	private Integer webServiceLoginCount = 0;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	private String smsUserName;
	
	private Boolean maker;
	
	private Boolean checker;
	
	private String deviceToken;
	
	private String serverKey;
	
	private Long changedFromId;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public UserType getUserType() {
		return userType;
	}	

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public WebService getWebService() {
		return webService;
	}

	public void setWebService(WebService webService) {
		this.webService = webService;
	}

	public long getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedId(long associatedId) {
		this.associatedId = associatedId;
	}

	public UserTemplate getUserTemplate() {
		return userTemplate;
	}

	public void setUserTemplate(UserTemplate userTemplate) {
		this.userTemplate = userTemplate;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Integer getWebServiceLoginCount() {
		return webServiceLoginCount;
	}

	public void setWebServiceLoginCount(Integer webServiceLoginCount) {
		this.webServiceLoginCount = webServiceLoginCount;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getSmsUserName() {
		return smsUserName;
	}

	public void setSmsUserName(String smsUserName) {
		this.smsUserName = smsUserName;
	}

	public Boolean getMaker() {
		return maker;
	}

	public void setMaker(Boolean maker) {
		this.maker = maker;
	}

	public Boolean getChecker() {
		return checker;
	}

	public void setChecker(Boolean checker) {
		this.checker = checker;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public Long getChangedFromId() {
		return changedFromId;
	}

	public void setChangedFromId(Long changedFromId) {
		this.changedFromId = changedFromId;
	}
	
}

