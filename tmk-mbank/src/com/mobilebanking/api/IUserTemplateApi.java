package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.UserTemplateDTO;
import com.mobilebanking.model.UserType;

public interface IUserTemplateApi {
	
	void saveUserTemplate(UserTemplateDTO userTemplateDTO);

	List<UserTemplateDTO> getAllUserTemplate();

	UserTemplateDTO getUserTemplateWithId(long userTemplateId);

	void editUserTemplate(UserTemplateDTO userTemplateDTO);

	List<UserTemplateDTO> findUserTemplateByUserType(UserType userType);

}
