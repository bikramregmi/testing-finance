package com.mobilebanking.model;

import java.util.Date;


public class UserDTO  {
	private Long id;
	
	private String userName;
	
	private String password;
	
	private String repassword;

	private UserType userType;

	private Status status;
	
	private String authority;
	
	private String operation;

	private String secret_key;
	
	private String customer;
	
	private String superAgent;
	
	private String subAgent;
	
	private WebServiceDTO web_service;
	
	private String userTemplate; 
	
	private UserTemplateDTO UserTemplateDTO;
	
	private String userObject;
	
	private long objectUserId;
	
	private boolean isFirstLogin;
	
	private String state;
	
	private String city;
	
	private int licenseCount;
	
	private Date expiryDate;
	
	private String address;
	
	private String oldPassword;
	
	private Integer loginCount = 0;
	
	private Integer webServiceLoginCount = 0;
	
	private long associatedId ;
	
	private String email;
	
	private boolean maker;
	
	private boolean checker;
	
	public long getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedId(long associatedId) {
		this.associatedId = associatedId;
	}

	public String getUserObject() {
		return userObject;
	}

	public void setUserObject(String userObject) {
		this.userObject = userObject;
	}

	public long getObjectUserId() {
		return objectUserId;
	}

	public void setObjectUserId(long objectUserId) {
		this.objectUserId = objectUserId;
	}

	public String getUserTemplate() {
		return userTemplate;
	}

	public void setUserTemplate(String userTemplate) {
		this.userTemplate = userTemplate;
	}

	

	public UserTemplateDTO getUserTemplateDTO() {
		return UserTemplateDTO;
	}

	public void setUserTemplateDTO(UserTemplateDTO userTemplateDTO) {
		UserTemplateDTO = userTemplateDTO;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSuperAgent() {
		return superAgent;
	}

	public void setSuperAgent(String superAgent) {
		this.superAgent = superAgent;
	}

	public String getSubAgent() {
		return subAgent;
	}

	public void setSubAgent(String subAgent) {
		this.subAgent = subAgent;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

	public WebServiceDTO getWeb_service() {
		return web_service;
	}

	public void setWeb_service(WebServiceDTO web_service) {
		this.web_service = web_service;
	}

	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
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

	public int getLicenseCount() {
		return licenseCount;
	}

	public void setLicneseCount(int licenseCount) {
		this.licenseCount = licenseCount;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLicenseCount(int licenseCount) {
		this.licenseCount = licenseCount;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getMaker() {
		return maker;
	}

	public void setMaker(boolean maker) {
		this.maker = maker;
	}

	public boolean getChecker() {
		return checker;
	}

	public void setChecker(boolean checker) {
		this.checker = checker;
	}

}
