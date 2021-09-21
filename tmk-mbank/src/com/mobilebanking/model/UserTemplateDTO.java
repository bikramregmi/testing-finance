package com.mobilebanking.model;

public class UserTemplateDTO {

	private Long id;

	private String userTemplateName;

	private String menuTemplate;
	
	private MenuTemplateDTO menuTemplateDTO;
	
	private String operation;
	
	
	private UserType userType;
	
	
	
	
	
	
	

	

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public MenuTemplateDTO getMenuTemplateDTO() {
		return menuTemplateDTO;
	}

	public void setMenuTemplateDTO(MenuTemplateDTO menuTemplateDTO) {
		this.menuTemplateDTO = menuTemplateDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
