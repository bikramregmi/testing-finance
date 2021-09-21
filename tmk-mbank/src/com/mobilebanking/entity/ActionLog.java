/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="actionLog")
public class ActionLog extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	@Column
	String remarks;
	
	@Column
	UserType forUserType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	User forUser;
	
	@ManyToOne(fetch=FetchType.EAGER)
	User updatedBy;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public UserType getForUserType() {
		return forUserType;
	}

	public void setForUserType(UserType forUserType) {
		this.forUserType = forUserType;
	}

	public User getForUser() {
		return forUser;
	}

	public void setForUser(User forUser) {
		this.forUser = forUser;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
}
