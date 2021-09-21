/**
 * 
 */
package com.mobilebanking.model;

/**
 * @author bibek
 *
 */
public class CommissionAmountDTO {
	
	private long id;
	
	private Double flat;
	
	private Double percentage;
	
	private Double fromAmount;
	
	private Double toAmount;
	
	private Status status;
	
	private Long commissionId;
	
	private Long merchantServiceId;
	
	private Long merchantId;
	
	private Double feeFlat;
	
	private Double feePercentage;
	
	private Double commissionFlat;
	
	private Double commissionPercentage;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getFlat() {
		return flat;
	}

	public void setFlat(Double flat) {
		this.flat = flat;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
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

	public Long getCommissionId() {
		return commissionId;
	}

	public void setCommissionId(Long commissionId) {
		this.commissionId = commissionId;
	}

	public Long getMerchantServiceId() {
		return merchantServiceId;
	}

	public void setMerchantServiceId(Long merchantServiceId) {
		this.merchantServiceId = merchantServiceId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
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
}
