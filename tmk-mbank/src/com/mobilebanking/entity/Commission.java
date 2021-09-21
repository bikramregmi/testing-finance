/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.CommissionType;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.TransactionType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="commission")
public class Commission extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Merchant merchant;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private MerchantService service;
		
	@Column
	private Status status;
	
	@Column
	private boolean commissoinForCustomer = false;
	
	@Column
	private boolean sameForAll;
	
	@Column
	private boolean feeCharge;
	
	@Column
	private TransactionType transactionType;
	
	@Column
	private CommissionType commissionType;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public MerchantService getService() {
		return service;
	}

	public void setService(MerchantService service) {
		this.service = service;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public boolean isCommissoinForCustomer() {
		return commissoinForCustomer;
	}

	public void setCommissoinForCustomer(boolean commissoinForCustomer) {
		this.commissoinForCustomer = commissoinForCustomer;
	}

	public boolean isSameForAll() {
		return sameForAll;
	}

	public void setSameForAll(boolean sameForAll) {
		this.sameForAll = sameForAll;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public CommissionType getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(CommissionType commissionType) {
		this.commissionType = commissionType;
	}

}
