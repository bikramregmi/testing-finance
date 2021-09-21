package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Notification;
import com.mobilebanking.model.NotificationDTO;

@Component
public class NotificationConverter
		implements IConverter<Notification, NotificationDTO>, IListConverter<Notification, NotificationDTO> {

	@Override
	public List<NotificationDTO> convertToDtoList(List<Notification> entityList) {
		List<NotificationDTO> notificationList = new ArrayList<>();

		for (Notification notification : entityList) {
			notificationList.add(convertToDto(notification));
		}
		return notificationList;
	}

	@Override
	public Notification convertToEntity(NotificationDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotificationDTO convertToDto(Notification entity) {

		NotificationDTO notificationDto = new NotificationDTO();

		notificationDto.setBody(entity.getMessage());
		notificationDto.setId(entity.getId());
		notificationDto.setSendTo(entity.getSendTo());
		notificationDto.setTopic(entity.isTopic());
		notificationDto.setDate(entity.getCreated().toString().substring(0, 16));
		notificationDto.setTitle(entity.getTitle());
		if(entity.getResponse() != null && !entity.getResponse().contains("Failed")){

			notificationDto.setResponse("Notification successfully sent");
		}else{
		notificationDto.setResponse(entity.getResponse());
		}
		return notificationDto;

	}

}
