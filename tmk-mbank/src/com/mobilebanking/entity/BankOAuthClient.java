/**
 * 
 */
package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * @author bibek
	 *
 */
@Entity
@Table(name="bankOAuthClient")
public class BankOAuthClient extends AbstractEntity<Long> {

	private static final long serialVersionUID = -4100291033175592809L;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@Column
	private String oAuthClientId;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getoAuthClientId() {
		return oAuthClientId;
	}

	public void setoAuthClientId(String oAuthClientId) {
		this.oAuthClientId = oAuthClientId;
	}
}
