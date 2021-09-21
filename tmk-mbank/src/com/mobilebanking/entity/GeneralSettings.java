package com.mobilebanking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "generalSettings")
public class GeneralSettings extends AbstractEntity<Long>{
	
	private static final long serialVersionUID = 7841239421217148363L;

	@Column
	private String settingsKey;
	
	@Column
	private String settingsValue;

	public String getSettingsKey() {
		return settingsKey;
	}

	public void setSettingsKey(String settingsKey) {
		this.settingsKey = settingsKey;
	}

	public String getSettingsValue() {
		return settingsValue;
	}

	public void setSettingsValue(String settingsValue) {
		this.settingsValue = settingsValue;
	}
	
	
}