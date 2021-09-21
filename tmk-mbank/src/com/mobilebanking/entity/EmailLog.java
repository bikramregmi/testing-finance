/**
 * 
 */
package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mobilebanking.model.EmailStatus;
import com.mobilebanking.model.EmailType;
import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="emailLog")
public class EmailLog extends AbstractEntity<Long> {

	private static final long serialVersionUID = -3307698821617506018L;
	
	@Column(nullable=false)
	public String email;
	
	@Column
	public EmailStatus status;
	
	@Column(nullable=true)
	private Date delivered;
	
	@Column
	private UserType forUserType;
	
	@Column
	private String forUser;
	
	@Column
	private EmailType emailType;
	
	@Column
	private Long bankId;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EmailStatus getStatus() {
		return status;
	}

	public void setStatus(EmailStatus status) {
		this.status = status;
	}

	public Date getDelivered() {
		return delivered;
	}

	public void setDelivered(Date delivered) {
		this.delivered = delivered;
	}

	public UserType getForUserType() {
		return forUserType;
	}

	public void setForUserType(UserType forUserType) {
		this.forUserType = forUserType;
	}

	public String getForUser() {
		return forUser;
	}

	public void setForUser(String forUser) {
		this.forUser = forUser;
	}

	public EmailType getEmailType() {
		return emailType;
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

}
