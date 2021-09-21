package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="poolAccount")
public class PoolAccount extends AbstractEntity<Long> {

	private static final long serialVersionUID = 188L;

	@Column
	private double totalCommission;

	@Column
	private double sendingCommission;

	@Column
	private double receivingCommission;

	@Column
	private double remainingCommission;
	public double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public double getSendingCommission() {
		return sendingCommission;
	}

	public void setSendingCommission(double sendingCommission) {
		this.sendingCommission = sendingCommission;
	}

	public double getReceivingCommission() {
		return receivingCommission;
	}

	public void setReceivingCommission(double receivingCommission) {
		this.receivingCommission = receivingCommission;
	}

	public double getRemainingCommission() {
		return remainingCommission;
	}

	public void setRemainingCommission(double remainingCommission) {
		this.remainingCommission = remainingCommission;
	}

}
