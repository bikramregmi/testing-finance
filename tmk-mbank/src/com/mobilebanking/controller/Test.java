package com.mobilebanking.controller;

import java.util.Date;

public class Test {
	
	private Date created;
	
	private String username;
	
	private String authority;
	
	private Integer status;
	
	public Test(){}
	
	public Test(Date created,String userName,String authority,int status){
		this.created=created;
		this.username=userName;
		this.authority=authority;
		this.status=status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
