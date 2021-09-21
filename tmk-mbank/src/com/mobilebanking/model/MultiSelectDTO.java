package com.mobilebanking.model;

import java.util.List;

public class MultiSelectDTO {
	
	private String countryId;
	
	private String agentId;
	
	private List<String> pickData;
	
	private List<String> ids;
	
	private List<String> pickListResult;
	
	

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public List<String> getPickListResult() {
		return pickListResult;
	}

	public void setPickListResult(List<String> pickListResult) {
		this.pickListResult = pickListResult;
	}

	public List<String> getPickData() {
		return pickData;
	}

	public void setPickData(List<String> pickData) {
		this.pickData = pickData;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
	



}
