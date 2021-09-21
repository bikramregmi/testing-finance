package com.wallet.ofs;

public class UserInfo {
//	"SAGARP04/123123/NP0010002,
	
	private String userSign="SAGARP04";
	
	private String userPassword="123123";
	
	private String  companyCode="NP0010002";

	public UserInfo(){
	this.userSign="SAGARP04";
	
	this.userPassword="123123";
	
	this.companyCode="NP0010002";
}
	
	public String getUserSign() {
		return userSign;
	}

	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
}
