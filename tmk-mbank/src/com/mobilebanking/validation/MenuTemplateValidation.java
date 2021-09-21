package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IMenuTemplateApi;
import com.mobilebanking.entity.Menu;
import com.mobilebanking.entity.MenuTemplate;
import com.mobilebanking.model.MenuTemplateDTO;
import com.mobilebanking.model.MenuType;
import com.mobilebanking.model.error.MenuTemplateError;
import com.mobilebanking.repositories.MenuRepository;
import com.mobilebanking.repositories.MenuTemplateRepository;

@Service
public class MenuTemplateValidation {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMenuTemplateApi menuTemplateApi;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private MenuTemplateRepository menuTemplateRepository;

	/*public MenuTemplateValidation(IMenuTemplateApi menuTemplateApi) {
		this.menuTemplateApi = menuTemplateApi;
	}*/

	public MenuTemplateError validateMenuTemplate(MenuTemplateDTO menuTemplateDto) {
		MenuTemplateError error = new MenuTemplateError();
		boolean valid= true;
		if (menuTemplateDto.getName() == null) {
			logger.debug("Name Required==>");
			error.setName("Name Required");
			valid=false;
		}else{
			MenuTemplate menuTemplate =menuTemplateRepository.findByMenuTemplate(menuTemplateDto.getName());
			if(menuTemplate!=null){
				logger.debug("Name Required==>");
				error.setName("Template name already Exist");
				valid=false;
			}
		}
		error.setValid(valid);
		return error;
	}
	
	public MenuTemplateError validateMenuTemplateExist(long  id) {
		
		MenuTemplateError error = new MenuTemplateError();
		boolean valid=true;
		MenuTemplate menuTemplate=menuTemplateRepository.findOne(id);
		if(menuTemplate==null){
			logger.debug("Name Required==>");
			error.setName("Template Not valid");
			valid=false;
		}
		
		
		error.setValid(true);
		return error;
		
		
	}
	
	public MenuTemplateError validateMenuTemplateAndUrl(long  id, String url) {
		
		MenuTemplateError error = new MenuTemplateError();
		boolean valid=true;
		MenuTemplate menuTemplate=menuTemplateRepository.findOne(id);
		if(menuTemplate==null){
			logger.debug("Name Required==>");
			error.setName("Template Not valid");
			valid=false;
		}
		
		Menu menu= menuRepository.menuValidationforTemplate(id, MenuType.SubMenu, url);
		if(menu==null){
			logger.debug("Name Required==>");
			error.setName("Invalid Menu");
			valid=false;
		}
		
		error.setValid(true);
		return error;
		
		
	}
}
