/**
 * 
 */
package com.mobilebanking.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="userLog")
public class UserLog extends AbstractEntity<Long> {

	private static final long serialVersionUID = 6472532932007959454L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;
	
	@Column
	private String remarks;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoggedIn;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}
}
