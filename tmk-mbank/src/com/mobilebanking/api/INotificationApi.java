package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.NotificationChannel;
import com.mobilebanking.model.NotificationDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.UserNotificationDTO;

public interface INotificationApi {

	
public NotificationDTO saveNotification(NotificationDTO notificationDTO);
	
	public PagablePage getNotification(Integer currentPage);

	public List<UserNotificationDTO> getUserNotification(Long id, NotificationChannel channel);

	public Long getUnseenNotification(Long id);

	public void changeNotificationStatus(Long lastNotificationid, Long id);

	public PagablePage getBankNotification(Integer currentPage,String bankCode);

	
}
