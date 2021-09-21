package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IMenuApi;
import com.mobilebanking.model.MenuDTO;
import com.mobilebanking.model.error.MenuError;
import com.mobilebanking.repositories.MenuRepository;

@Service
public class MenuValidation {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMenuApi menuApi;
	
	@Autowired
	private MenuRepository menuRepository;

	/*public MenuValidation(IMenuApi menuApi) {
		this.menuApi = menuApi;
	}*/

	public MenuError validateMenu(MenuDTO menuDto) {
		MenuError error = new MenuError();

		boolean isValid =true;
		if (menuDto.getName() == null) {
			logger.debug("Name Required==>");
			error.setName("Name Required");
			isValid = false;
		}

		if (menuDto.getUrl() == null) {
			
			logger.debug("URL Required==>");
			error.setUrl("URL Required");
			isValid=false;
		}else if (menuDto.getUrl().trim().equals("")){
			logger.debug("URL Required==>");
			error.setUrl("URL Required");
			isValid=false;
		}else{
			if(menuRepository.findByMenuByUrl(menuDto.getUrl())!=null){
				logger.debug("URL Required==>");
				error.setUrl("URL already exist");
				isValid=false;
			}
		}
		
		error.setValid(isValid);

		return error;
	}
}
