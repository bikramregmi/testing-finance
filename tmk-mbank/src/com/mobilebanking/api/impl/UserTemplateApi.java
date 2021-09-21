package com.mobilebanking.api.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IUserTemplateApi;
import com.mobilebanking.controller.StateController;
import com.mobilebanking.entity.MenuTemplate;
import com.mobilebanking.entity.UserTemplate;
import com.mobilebanking.model.UserTemplateDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.repositories.MenuTemplateRepository;
import com.mobilebanking.repositories.UserTemplateRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
public class UserTemplateApi implements IUserTemplateApi {
	
	private Logger logger=LoggerFactory.getLogger(StateController.class );
	
	@Autowired
	private UserTemplateRepository userTemplateRepository;
	
	@Autowired
	private  MenuTemplateRepository menuTemplateRepository;
	
	/*public UserTemplateApi(UserTemplateRepository userTemplateRepository,MenuTemplateRepository menuTemplateRepository,UserRepository userRepository) {
		this.userTemplateRepository = userTemplateRepository;
		this.menuTemplateRepository=menuTemplateRepository;
		this.userRepository=userRepository;
	}*/

	@Override
	public void saveUserTemplate(UserTemplateDTO userTemplateDTO) {
		MenuTemplate menuTemplate=menuTemplateRepository.findByMenuTemplate(userTemplateDTO.getMenuTemplate());
		UserTemplate userTemplate=new UserTemplate();
		userTemplate.setUserTemplateName(userTemplateDTO.getUserTemplateName());
		userTemplate.setMenuTemplate(menuTemplate);
		if(userTemplateDTO.getUserType()!=null){
			userTemplate.setUsertype(userTemplateDTO.getUserType());
		}
		userTemplateRepository.save(userTemplate);
		
	
	}
	
	
	@Override
	public void editUserTemplate(UserTemplateDTO userTemplateDTO) {
		
		MenuTemplate menuTemplate=menuTemplateRepository.findByMenuTemplate(userTemplateDTO.getMenuTemplate());
		UserTemplate userTemplate= userTemplateRepository.findOne(userTemplateDTO.getId());
		userTemplate.setMenuTemplate(menuTemplate);
		userTemplateRepository.save(userTemplate);
		
	}

	@Override
	public UserTemplateDTO getUserTemplateWithId(long userTemplateId) {
		return ConvertUtil.convertUserTemplate(userTemplateRepository.findByid(userTemplateId));
	}

	@Override
	public List<UserTemplateDTO> getAllUserTemplate() {
		List<UserTemplate> copy = ConvertUtil.convertIterableToList(userTemplateRepository.findAll());
		return ConvertUtil.convertUserTemplateToDto(copy);
	}


	@Override
	public List<UserTemplateDTO> findUserTemplateByUserType(UserType userType) {
		List<UserTemplate> userTemplateList = userTemplateRepository.findByUsertype(userType);
		if(userTemplateList!=null){
			return ConvertUtil.convertUserTemplateToDto(userTemplateList);
		}
		return null;
	}


}
