/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.IsoRequestType;
import com.mobilebanking.model.IsoStatus;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="iso8583Log")
public class Iso8583Log extends AbstractEntity<Long> {

	private static final long serialVersionUID = -9051390185794584920L;
	
	@Lob
	@Column(length=3000)
	private String isoRequest;
	
	@Lob
	@Column(length=3000)
	private String isoResponse;
	
	@Column
	private String responseCode;
	
	@Column
	private IsoStatus status;
	
	@Column
	private IsoRequestType requestType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private BankBranch bankBranch;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Transaction transaction;

	public String getIsoRequest() {
		return isoRequest;
	}

	public void setIsoRequest(String isoRequest) {
		this.isoRequest = isoRequest;
	}

	public String getIsoResponse() {
		return isoResponse;
	}

	public void setIsoResponse(String isoResponse) {
		this.isoResponse = isoResponse;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public IsoStatus getStatus() {
		return status;
	}

	public void setStatus(IsoStatus status) {
		this.status = status;
	}

	public IsoRequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(IsoRequestType requestType) {
		this.requestType = requestType;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
