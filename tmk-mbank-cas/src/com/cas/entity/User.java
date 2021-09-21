package com.cas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.cas.model.UserStatus;

@Entity
public class User extends AbstractEntity<Long> {
	private static final long serialVersionUID = -8691675356402414797L;
	
	@Column(unique = true, nullable = false)
	private String userName;

	private String password;
	
	private String ipAddress;

	private String authority;

	private UserStatus status;
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

}
