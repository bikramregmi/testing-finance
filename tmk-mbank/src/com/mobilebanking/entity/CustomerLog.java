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
@Table(name="customerLog")
public class CustomerLog extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 99537392215154203L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Customer customer;
	
	@Column
	private String remarks;
	
	@Column
	private String changedBy;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoggedIn;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	
}
