package com.mobilebanking.model;

import java.util.List;

public class FcmServerSettingDTO {

	private long id;
	
	private String serverKey;
	
	private String serverId;
	
	private long bankId;
	
	private List<String> bankName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public List<String> getBankName() {
		return bankName;
	}

	public void setBankName(List<String> bankName) {
		this.bankName = bankName;
	}

}