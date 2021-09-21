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
@Table(name="bankCommission")
public class BankCommission extends AbstractEntity<Long> {

	private static final long serialVersionUID = -5988101436441528929L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	@ManyToOne
	private CommissionAmount commissionAmount;
	
	@Column(nullable=true)
	private Double commissionFlat;
	
	@Column(nullable=true)
	private Double commissionPercentage;
	
	@Column(nullable=true)
	private Double feeFlat;
	
	@Column(nullable=true)
	private Double feePercentage;
	
	@Column(nullable=true)
	private Double channelPartnerCommissionFlat;
	
	@Column(nullable=true)
	private Double channelPartnerCommissionPercentage;

	@Column(nullable=true)
	private Double operatorCommissionFlat;
	
	@Column(nullable=true)
	private Double operatorCommissionPercentage;
	
	@Column(nullable=true)
	private Double cashBack;
	
	@Column(nullable=false)
	private Status status;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public CommissionAmount getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(CommissionAmount commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public Double getCommissionFlat() {
		return commissionFlat;
	}

	public void setCommissionFlat(Double commissionFlat) {
		this.commissionFlat = commissionFlat;
	}

	public Double getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(Double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	public Double getFeeFlat() {
		return feeFlat;
	}

	public void setFeeFlat(Double feeFlat) {
		this.feeFlat = feeFlat;
	}

	public Double getFeePercentage() {
		return feePercentage;
	}

	public void setFeePercentage(Double feePercentage) {
		this.feePercentage = feePercentage;
	}

	public Double getChannelPartnerCommissionFlat() {
		return channelPartnerCommissionFlat;
	}

	public void setChannelPartnerCommissionFlat(Double channelPartnerCommissionFlat) {
		this.channelPartnerCommissionFlat = channelPartnerCommissionFlat;
	}

	public Double getChannelPartnerCommissionPercentage() {
		return channelPartnerCommissionPercentage;
	}

	public void setChannelPartnerCommissionPercentage(Double channelPartnerCommissionPercentage) {
		this.channelPartnerCommissionPercentage = channelPartnerCommissionPercentage;
	}

	public Double getOperatorCommissionFlat() {
		return operatorCommissionFlat;
	}

	public void setOperatorCommissionFlat(Double operatorCommissionFlat) {
		this.operatorCommissionFlat = operatorCommissionFlat;
	}

	public Double getOperatorCommissionPercentage() {
		return operatorCommissionPercentage;
	}

	public void setOperatorCommissionPercentage(Double operatorCommissionPercentage) {
		this.operatorCommissionPercentage = operatorCommissionPercentage;
	}

	public Double getCashBack() {
		return cashBack;
	}

	public void setCashBack(Double cashBack) {
		this.cashBack = cashBack;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
