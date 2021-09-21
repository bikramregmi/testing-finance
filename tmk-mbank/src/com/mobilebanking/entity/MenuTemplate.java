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

/**
 * @author bibek
 *
 */
@Entity
@Table(name="menuTemplate")
public class MenuTemplate extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String name;

	@ManyToMany(cascade = CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "menuTemplate_menu", joinColumns = @JoinColumn(name = "menuTemplate_id") , inverseJoinColumns = @JoinColumn(name = "menu_id") )
	@Column(nullable = true)
	private List<Menu> menu;

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
