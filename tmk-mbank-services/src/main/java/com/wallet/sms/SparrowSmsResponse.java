/**
 * 
 */
package com.wallet.sms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author bibek
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SparrowSmsResponse {
	
	private int count;
	
	private String response_code;
	
	private String response;
	
	private String credit_consumed;
	
	private String message_id;
	
	private String username;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getCredit_consumed() {
		return credit_consumed;
	}

	public void setCredit_consumed(String credit_consumed) {
		this.credit_consumed = credit_consumed;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
