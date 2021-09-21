package com.mobilebanking.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */

@Entity
@Table(name="state")
public class State extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Status status;

	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL, mappedBy="state")
	private Set<City> city = new HashSet<City>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<City> getCity() {
		return city;
	}

	public void setCity(Set<City> city) {
		this.city = city;
	}
	
	
	

}
