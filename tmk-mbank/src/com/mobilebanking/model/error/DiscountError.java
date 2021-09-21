package com.mobilebanking.model.error;

public class DiscountError {
	
	private String fromAgent;
	
	private String toAgent;

	private String fromAmount;
	
	private String toAmount;
	
	private String paying_commission;
	
	private String receiving_commission;
	
	private String discountType;

	private boolean valid;
	
	
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

	public String getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(String fromAmount) {
		this.fromAmount = fromAmount;
	}

	public String getToAmount() {
		return toAmount;
	}

	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}

	public String getPaying_commission() {
		return paying_commission;
	}

	public void setPaying_commission(String paying_commission) {
		this.paying_commission = paying_commission;
	}

	public String getReceiving_commission() {
		return receiving_commission;
	}

	public void setReceiving_commission(String receiving_commission) {
		this.receiving_commission = receiving_commission;
	}

	

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		// TODO Auto-generated method stub
		return valid;
	}

}
