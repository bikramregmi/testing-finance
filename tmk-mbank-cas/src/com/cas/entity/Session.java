package com.cas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session {

	@Column(nullable = false, name = "session_id")
	@Id
	private String sessionId;

	@Column(name = "valid_session")
	private char valid;

	@Column(name = "session_data")
	@Lob
	private byte[] attributes;

	@Column(name = "last_access")
	private Long lastAccessed;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public char getValid() {
		return valid;
	}

	public void setValid(char valid) {
		this.valid = valid;
	}

	public Long getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public byte[] getAttributes() {
		return attributes;
	}

	public void setAttributes(byte[] attributes) {
		this.attributes = attributes;
	}

}
