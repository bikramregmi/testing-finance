package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */

@Entity
@Table(name="webService")
public class WebService extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private String api_user;
	
	@Column(unique = true, nullable = false)
	private String api_password;

	@Column(unique = true, nullable = false)
	private String access_key;
	
	@Column(nullable = false)
	private Status status;

	public String getApi_user() {
		return api_user;
	}

	public void setApi_user(String api_user) {
		this.api_user = api_user;
	}

	public String getApi_password() {
		return api_password;
	}

	public void setApi_password(String api_password) {
		this.api_password = api_password;
	}

	public String getAccess_key() {
		return access_key;
	}

	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
