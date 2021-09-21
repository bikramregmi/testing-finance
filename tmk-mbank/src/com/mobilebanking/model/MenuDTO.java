package com.mobilebanking.model;

public class MenuDTO {
	
	private Long id;
	
	private String name;
	
	private Status status;
	
	private MenuType menuType;
	
	private String url;
	
	private String superMenu;
	
	
	private long superId;
	
	
	

	public long getSuperId() {
		return superId;
	}


	public void setSuperId(long superId) {
		this.superId = superId;
	}


	public String getSuperMenu() {
		return superMenu;
	}


	public void setSuperMenu(String superMenu) {
		this.superMenu = superMenu;
	}


	public MenuType getMenuType() {
		return menuType;
	}


	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
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


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
