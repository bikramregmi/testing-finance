package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.UserNotification;
import com.mobilebanking.model.UserNotificationDTO;

@Component
public class UserNotificationConverter implements IConverter<UserNotification, UserNotificationDTO>,IListConverter<UserNotification, UserNotificationDTO>{

	@Autowired
	private NotificationConverter notificationConverter;
	
	@Override
	public UserNotification convertToEntity(UserNotificationDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserNotificationDTO convertToDto(UserNotification entity) {
		UserNotificationDTO dto = new UserNotificationDTO();
		dto.setId(entity.getId());
		dto.setSeen(entity.isSeen());
		dto.setUser(entity.getUser().getId());
		dto.setNotification(notificationConverter.convertToDto(entity.getNotification()));
		return dto;
	}

	@Override
	public List<UserNotificationDTO> convertToDtoList(List<UserNotification> entityList) {
		List<UserNotificationDTO> dtoList = new ArrayList<>();
		for(UserNotification entity: entityList){
			dtoList.add(convertToDto(entity));
		}
		return dtoList;
	}

}
