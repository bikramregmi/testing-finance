package com.mobilebanking.model.error;

import com.mobilebanking.model.Status;

public class MenuError {

	private Long id;
	
	private String name;
		
	private Status status;	
	
	private String url;
	
	private boolean valid;
	
	private String superMenu;
	
	
	


	public String getSuperMenu() {
		return superMenu;
	}


	public void setSuperMenu(String superMenu) {
		this.superMenu = superMenu;
	}


	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
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
