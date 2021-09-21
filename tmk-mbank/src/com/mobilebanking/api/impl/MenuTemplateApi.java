package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilebanking.api.IMenuTemplateApi;
import com.mobilebanking.api.IUserTemplateApi;
import com.mobilebanking.controller.StateController;
import com.mobilebanking.entity.Menu;
import com.mobilebanking.entity.MenuTemplate;
import com.mobilebanking.entity.UserTemplate;
import com.mobilebanking.model.MenuTemplateDTO;
import com.mobilebanking.model.MenuType;
import com.mobilebanking.model.UserTemplateDTO;
import com.mobilebanking.repositories.MenuRepository;
import com.mobilebanking.repositories.MenuTemplateRepository;
import com.mobilebanking.repositories.UserTemplateRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
@Transactional
public class MenuTemplateApi implements IMenuTemplateApi{
	
	private Logger logger=LoggerFactory.getLogger(StateController.class );
	
	@Autowired
	private MenuTemplateRepository menuTemplateRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private UserTemplateRepository userTemplateRepository;
	
	@Autowired
	private IUserTemplateApi userTemplateApi;	

	@Override
	public void saveMenuTemplate(MenuTemplateDTO menuTemplateDTO) {
		
		MenuTemplate menuTemplate=new MenuTemplate();
		menuTemplate.setName(menuTemplateDTO.getName());
		menuTemplate=menuTemplateRepository.save(menuTemplate);
		
		
		UserTemplateDTO userTemplateDto= new UserTemplateDTO();
		userTemplateDto.setMenuTemplate(menuTemplate.getName());
		userTemplateDto.setUserTemplateName(menuTemplate.getName());
		userTemplateDto.setUserType(menuTemplateDTO.getUserType());
		userTemplateApi.saveUserTemplate(userTemplateDto);
		
		logger.debug("Template Saved");
	
	}
	
	@Override
	public void editMenuTemplate(MenuTemplateDTO menuTemplateDTO) {
		
		MenuTemplate menuTemplate = menuTemplateRepository.findOne(menuTemplateDTO.getId());
		menuTemplate.setName(menuTemplateDTO.getName());
		menuTemplate = menuTemplateRepository.save(menuTemplate);
		
		UserTemplate userTemplate = userTemplateRepository.findByMenuTemplate(menuTemplate.getId());
		userTemplate.setUserTemplateName(menuTemplate.getName());
		userTemplateRepository.save(userTemplate);
		logger.debug("Template Saved");
	
	}

	@Override
	public MenuTemplateDTO getMenuTemplateWithId(long menuTemplateId) {
		return  ConvertUtil.convertMenuTemplate(menuTemplateRepository.findOne(menuTemplateId));
	}
	
	@Override
	public MenuTemplateDTO getMenuTemplateDtoById(long menuTemplateId) {
		return ConvertUtil.convertMenuTemplate(menuTemplateRepository.findOne(menuTemplateId));
	}

	@Override
	public List<MenuTemplate> getAllMenuTemplate() {
		List<MenuTemplate> copy = ConvertUtil.convertIterableToList(menuTemplateRepository.findAll());
		return copy;
	}
	
	@Override
	public List<MenuTemplateDTO> findMenuTemplate() {
		List<MenuTemplate> menuTemplateList = new ArrayList<MenuTemplate>();
		menuTemplateList = menuTemplateRepository.findMenuTemplate();
		return ConvertUtil.convertMenuTemplateDTOtoMenuTemplate(menuTemplateList);
	}

	@Override
	public List<MenuTemplateDTO> getAllMenuTemplateDto() {
		List<MenuTemplate> menuTemplateList = ConvertUtil.convertIterableToList(menuTemplateRepository.findAll());
		List<MenuTemplateDTO> menuTemplateDtoList = ConvertUtil.convertMenuTemplateListToDtoList(menuTemplateList);
		
		return menuTemplateDtoList;
	}

	@Override
	@Transactional
	public void addMenuTotemp(long id, String url) {
		MenuTemplate menuTemplate=menuTemplateRepository.findOne(id);
		Menu menu= menuRepository.findByMenuByUrl(url);
		menuTemplate.getMenu().add(menu);
		menuTemplateRepository.save(menuTemplate);
		this.parentResolver(id, url);
		
	}
	
	private void parentResolver(long id, String url){
		Menu menu=menuRepository.getParent(id, MenuType.SuperMenu, url);
		if(menu!=null){
			MenuTemplate menuTemplate=menuTemplateRepository.findOne(id);
			menuTemplate.getMenu().add(menu);
			menuTemplateRepository.save(menuTemplate);
		}
		
	}

	

	@Override
	public void removeMenufromtemp(long templateId, Long menuId) {
		MenuTemplate menuTemplate=menuTemplateRepository.findOne(templateId);
		Menu menu= menuRepository.findOne(menuId);
		if(menuTemplate.getMenu().contains(menu)){
			menuTemplate.getMenu().remove(menu);
			menuTemplateRepository.save(menuTemplate);
		}
		boolean valid = false;
		List<Menu> menuList = menuRepository.getSubMenus(menu.getSuperId());
		for (Menu m : menuList) {
			if (menuTemplate.getMenu().contains(m)) {
				valid = true;
			}
		}
		if (!valid) {
			menuTemplate.getMenu().remove(menuRepository.findOne(menu.getSuperId()));
			menuTemplate = menuTemplateRepository.save(menuTemplate);
		}

	}
	
	
}
