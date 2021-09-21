/**
 * 
 */
package com.mobilebanking.model;

import java.util.List;

/**
 * @author bibek
 *
 */
public class CommissionAmountDtoList {
	
	private List<CommissionAmountDTO> commissionAmounts;
	
	private Long commissionId;
	
	private Long merchantServiceId;
	
	private Long merchantId;
	
	public List<CommissionAmountDTO> getCommissionAmounts() {
		return commissionAmounts;
	}

	public void setCommissionAmounts(List<CommissionAmountDTO> commissionAmounts) {
		this.commissionAmounts = commissionAmounts;
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
}
