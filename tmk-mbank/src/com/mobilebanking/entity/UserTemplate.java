package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mobilebanking.model.UserType;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="userTemplate")
public class UserTemplate extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String userTemplateName;
	
	@OneToOne(fetch = FetchType.EAGER)
	private MenuTemplate menuTemplate;
	
	@Column(nullable = true)
	private UserType usertype;
	
	

	public UserType getUsertype() {
		return usertype;
	}

	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}

	public String getUserTemplateName() {
		return userTemplateName;
	}

	public void setUserTemplateName(String userTemplateName) {
		this.userTemplateName = userTemplateName;
	}

	public MenuTemplate getMenuTemplate() {
		return menuTemplate;
	}

	public void setMenuTemplate(MenuTemplate menuTemplate) {
		this.menuTemplate = menuTemplate;
	}
	
	

	
	
}
