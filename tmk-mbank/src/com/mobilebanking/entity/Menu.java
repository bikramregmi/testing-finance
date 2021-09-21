package com.mobilebanking.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.mobilebanking.model.MenuType;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="menu")
public class Menu extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Status status;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private MenuType menuType;

	@Column(nullable = false)
	private long superId;

	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "menu")
	@Column(nullable = true)
	List<MenuTemplate> menuTemplate;

	public List<MenuTemplate> getMenuTemplate() {
		return menuTemplate;
	}

	public void setMenuTemplate(List<MenuTemplate> menuTemplate) {
		this.menuTemplate = menuTemplate;
	}

	public long getSuperId() {
		return superId;
	}

	public void setSuperId(long superId) {
		this.superId = superId;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

}
