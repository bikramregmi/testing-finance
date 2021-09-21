package com.mobilebanking.model;

import java.util.List;

public class MenuTemplateDTO {
	
	private Long id;
	
	private String name;
	
	private List<MenuDTO> menu;
	
	private UserType userType;
	
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MenuDTO> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuDTO> menu) {
		this.menu = menu;
	}

	

	

	


}
