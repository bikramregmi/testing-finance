package com.mobilebanking.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IMenuTemplateApi;
import com.mobilebanking.model.UserTemplateDTO;
import com.mobilebanking.model.error.UserTemplateError;
import com.mobilebanking.repositories.MenuRepository;
import com.mobilebanking.repositories.MenuTemplateRepository;
import com.mobilebanking.repositories.UserTemplateRepository;

@Service
public class UserTemplateValidation {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMenuTemplateApi menuTemplateApi;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private MenuTemplateRepository menuTempalteRepository;

	@Autowired
	private UserTemplateRepository userTemplateRepository;

	/*
	 * public MenuTemplateValidation(IMenuTemplateApi menuTemplateApi) {
	 * this.menuTemplateApi = menuTemplateApi; }
	 */

	public UserTemplateError validateUserTemplate(UserTemplateDTO userTemplateDTO) {
		UserTemplateError error = new UserTemplateError();
		boolean valid = true;

		if (userTemplateDTO.getOperation().equals("add")) {
			if (userTemplateDTO.getUserTemplateName() == null) {
				logger.debug("Name Required==>");
				error.setUserTemplateName("Name Required");
				valid = false;
			} else {
				if (userTemplateRepository.findByUserTemplate(userTemplateDTO.getUserTemplateName()) != null) {
					logger.debug("Name Required==>");
					error.setUserTemplateName("Template name already Exist");
					valid = false;
				}
			}
		}
		if (userTemplateDTO.getMenuTemplate() == null) {
			logger.debug("Name Required==>");
			error.setMenuTemplate("menu Template  Required");
			valid = false;
		} else {
			if (menuTempalteRepository.findByMenuTemplate(userTemplateDTO.getUserTemplateName()) != null) {
				logger.debug("Name Required==>");
				error.setMenuTemplate("Template name already Exist");
				valid = false;
			}
		}

		error.setValid(valid);
		return error;
	}

}
