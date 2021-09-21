package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilebanking.model.NotificationChannel;

@Entity
@Table(name = "userNotification")
public class UserNotification extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Notification notification;

	@ManyToOne
	private User user;
	
	private boolean seen;

	private NotificationChannel notificationChannel;

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public NotificationChannel getNotificationChannel() {
		return notificationChannel;
	}

	public void setNotificationChannel(NotificationChannel notificationChannel) {
		this.notificationChannel = notificationChannel;
	}

}
