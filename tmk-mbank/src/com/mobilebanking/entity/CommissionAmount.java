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
@Table(name="commissionAmount")
public class CommissionAmount extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=true)
	private Double commissionFlat;
	
	@Column(nullable=true)
	private Double commissionPercentage;
	
	@Column
	private Double fromAmount;
	
	@Column
	private Double toAmount;
	
	@Column
	private Status status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Commission commission;
	
	@Column(nullable=true)
	private Double feeFlat;
	
	@Column(nullable=true)
	private Double feePercentage;

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

	public Double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(Double fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Double getToAmount() {
		return toAmount;
	}

	public void setToAmount(Double toAmount) {
		this.toAmount = toAmount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Commission getCommission() {
		return commission;
	}

	public void setCommission(Commission commission) {
		this.commission = commission;
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
}
