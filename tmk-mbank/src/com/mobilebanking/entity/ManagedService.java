package com.mobilebanking.entity;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class ManagedService {
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Merchant merchant;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private MerchantService merchantService;

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public MerchantService getMerchantService() {
		return merchantService;
	}

	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
}
