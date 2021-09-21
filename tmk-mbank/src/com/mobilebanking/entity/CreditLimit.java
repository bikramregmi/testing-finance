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
@Table(name="creditLimit")
public class CreditLimit extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = -6959531272702397001L;
	
	@OneToOne(fetch= FetchType.EAGER)
	private Bank bank;
	
	@Column	
	private Double amount;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
