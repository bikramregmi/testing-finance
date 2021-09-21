package com.mobilebanking.model;


import java.sql.Date;

/**
 * @author bibek
 *
 */
public class ActionLogDTO {
	
	public long id;
	
	public String remarks;
	
	public String forUser;
	
	public UserType forUserType;
	
	public String updatedBy;
	
	public Date updatedOn;
	
	public CustomerStatus action;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getForUser() {
		return forUser;
	}

	public void setForUser(String forUser) {
		this.forUser = forUser;
	}

	public UserType getForUserType() {
		return forUserType;
	}

	public void setForUserType(UserType forUserType) {
		this.forUserType = forUserType;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public CustomerStatus getAction() {
		return action;
	}

	public void setAction(CustomerStatus action) {
		this.action = action;
	}
}
