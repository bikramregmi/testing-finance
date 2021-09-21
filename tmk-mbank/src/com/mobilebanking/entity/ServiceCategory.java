package com.mobilebanking.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "serviceCategory")
public class ServiceCategory  extends AbstractEntity<Long>{

	private static final long serialVersionUID = -4814657310665905098L;

	@ManyToMany(cascade = CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "serviceCategory_merchantService", joinColumns = @JoinColumn(name = "serviceCategory_id") , inverseJoinColumns = @JoinColumn(name = "merchantServices_id") )
	@Column(nullable = true)
	List<MerchantService> merchantServices;

	
	private String name;

	public List<MerchantService> getMerchantServices() {
		return merchantServices;
	}


	public void setMerchantServices(List<MerchantService> merchantServices) {
		this.merchantServices = merchantServices;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
