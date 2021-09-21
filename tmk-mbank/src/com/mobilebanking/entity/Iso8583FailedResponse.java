/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.IsoStatus;

/**
 * @author bibek
 *
 */

@Entity
@Table(name="iso8583FailedResponse")
public class Iso8583FailedResponse extends AbstractEntity<Long> {

	private static final long serialVersionUID = -6097587072122400203L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Transaction transaction;
	
	@Column
	private String remarks;
	
	@Column
	private IsoStatus status;

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public IsoStatus getStatus() {
		return status;
	}

	public void setStatus(IsoStatus status) {
		this.status = status;
	}
	
}
