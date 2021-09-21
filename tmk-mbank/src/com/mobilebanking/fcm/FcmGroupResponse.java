package com.mobilebanking.fcm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FcmGroupResponse {

	@JsonProperty("message_id")
	private Integer messageId;
	
	@JsonProperty("message_id")
	public Integer getMessageId() {
	return messageId;
	}

	@JsonProperty("message_id")
	public void setMessageId(Integer messageId) {
	this.messageId = messageId;
	}

	
}
