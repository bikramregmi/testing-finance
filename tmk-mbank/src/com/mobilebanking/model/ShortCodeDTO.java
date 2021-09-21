package com.mobilebanking.model;

public class ShortCodeDTO {

	private String from;
	
	private String to;
	
	private String keyword;
	
	private String text;

	/*public ShortCodeDTO(String from2, String to2, String keyword2, String text2) {
		this.from=from2;
		this.to=to2;
		this.keyword=SmsShortCodes.valueOf(keyword2);
		this.text=text2;
	}*/

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
