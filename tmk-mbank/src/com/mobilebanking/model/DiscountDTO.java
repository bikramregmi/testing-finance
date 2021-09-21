package com.mobilebanking.model;

public class DiscountDTO {
	
	private long id;

	private String fromAgent;
	
	private String toAgent;

	private double fromAmount;
	
	private double toAmount;

	private double overall_discount;
	
	private double overall_discountFlat;
	
	private DiscountType discountType;
	
	private double payingDiscount;
	
	private double receivingDiscount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFromAgent() {
		return fromAgent;
	}

	public void setFromAgent(String fromAgent) {
		this.fromAgent = fromAgent;
	}


	public String getToAgent() {
		return toAgent;
	}


	public void setToAgent(String toAgent) {
		this.toAgent = toAgent;
	}

	public double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(double fromAmount) {
		this.fromAmount = fromAmount;
	}

	public double getToAmount() {
		return toAmount;
	}

	public void setToAmount(double toAmount) {
		this.toAmount = toAmount;
	}

	public double getOverall_discount() {
		return overall_discount;
	}


	public void setOverall_discount(double overall_discount) {
		this.overall_discount = overall_discount;
	}


	public double getOverall_discountFlat() {
		return overall_discountFlat;
	}


	public void setOverall_discountFlat(double overall_discountFlat) {
		this.overall_discountFlat = overall_discountFlat;
	}


	public DiscountType getDiscountType() {
		return discountType;
	}


	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public double getPayingDiscount() {
		return payingDiscount;
	}

	public void setPayingDiscount(double payingDiscount) {
		this.payingDiscount = payingDiscount;
	}

	public double getReceivingDiscount() {
		return receivingDiscount;
	}

	public void setReceivingDiscount(double receivingDiscount) {
		this.receivingDiscount = receivingDiscount;
	}
}
