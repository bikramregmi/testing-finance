package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.Menu;
import com.mobilebanking.model.MenuDTO;

public interface IMenuApi {
	
	Menu saveMenu(Menu menu);

	List<Menu> getAllMenu();

	List<MenuDTO> getAllSubMenuExcNot(long templateId);
	
	List<MenuDTO> getAllSubMenuIncluded(long templateId);
	
	List<MenuDTO> findMenu();
	
	Menu getMenuWithId(long menuId);
	
	MenuDTO getMenuDtoById(long menuId);
	
	void saveMenu(MenuDTO menuDto);
	
	List<MenuDTO> findAllMenu();
	
	List<MenuDTO> findAllSuperMenu();
	
	void editMenu(MenuDTO menuDto);

	
}
