package com.mobilebanking.model.error;

public class UserTemplateError {
	
	
	private String userTemplateName;
	
	
	private String menuTemplate;
	
	private boolean valid;
	
	

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getUserTemplateName() {
		return userTemplateName;
	}

	public void setUserTemplateName(String userTemplateName) {
		this.userTemplateName = userTemplateName;
	}

	public String getMenuTemplate() {
		return menuTemplate;
	}

	public void setMenuTemplate(String menuTemplate) {
		this.menuTemplate = menuTemplate;
	}
	
	
	

	
}
