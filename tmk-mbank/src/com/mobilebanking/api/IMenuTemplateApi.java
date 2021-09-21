package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.MenuTemplate;
import com.mobilebanking.model.MenuTemplateDTO;

public interface IMenuTemplateApi {
	
	void saveMenuTemplate(MenuTemplateDTO menuTemplateDTO);

	List<MenuTemplate> getAllMenuTemplate();
	
	List<MenuTemplateDTO> findMenuTemplate();

	MenuTemplateDTO getMenuTemplateWithId(long menuTemplateId);
	
	List<MenuTemplateDTO> getAllMenuTemplateDto();
	
	void addMenuTotemp(long id, String url);
	
	
	MenuTemplateDTO getMenuTemplateDtoById(long menuTemplateId);

	void editMenuTemplate(MenuTemplateDTO menuTemplateDTO);

	void removeMenufromtemp(long templateId, Long menuId);

}
