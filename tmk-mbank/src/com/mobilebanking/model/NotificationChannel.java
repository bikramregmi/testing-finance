package com.mobilebanking.model;

public enum NotificationChannel {
	WEB("Web"), MOBILE("Mobile"), BOTH("Both"),CBSALERT("CBS Alert");

	private String channel;

	private NotificationChannel(String channel) {
		this.channel = channel;
	}

	public String getChannel() {
		return channel;
	}

}
