package com.mobilebanking.fcm;

import com.mobilebanking.model.NotificationDTO;

public interface PushNotification {

	public String sendNotification(NotificationDTO notificationDTO);
	
	public String sendGroupNotification(NotificationDTO notificationDTO);

	public String sendNotification(String title, String body, String sendTo,String channel,String serverKey);

	void suscribeToTopic(String registrationToken, String topicName,String secretKey);

	void unsuscribeFromTopic(String registrationToken, String topicName,String secretKey);

	
}
