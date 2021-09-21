package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sparrowSettings")
public class SparrowSettings extends AbstractEntity<Long>{
	
	private static final long serialVersionUID = -7735217728350724227L;

	private String url;
	
	private String identity;
	
	private String token;
	
	private String shortCode;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
}
