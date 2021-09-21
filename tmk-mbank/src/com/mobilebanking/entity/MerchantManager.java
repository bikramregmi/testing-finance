package com.mobilebanking.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

@Entity
@Table(name = "merchantmanager")
public class MerchantManager extends AbstractEntity<Long>{
	
	private static final long serialVersionUID = -3287115922571242818L;

	@Embedded
	private ManagedService merchantsAndServices;
	
	private boolean selected;

	private Status status;

	public ManagedService getMerchantsAndServices() {
		return merchantsAndServices;
	}

	public void setMerchantsAndServices(ManagedService merchantsAndServices) {
		this.merchantsAndServices = merchantsAndServices;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
