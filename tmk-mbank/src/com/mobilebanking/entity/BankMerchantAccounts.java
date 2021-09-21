/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="bankMerchantAccounts")
public class BankMerchantAccounts extends AbstractEntity<Long> {
	private static final long serialVersionUID = -4093370332354752450L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Merchant merchant;
	
	@Column
	private Status status;
	
	@Column
	private String accountNumber;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
