package com.mobilebanking.model;

public class SmsPackageDTO {

	private Long id;
	
	private Long bankId;
	
	private Long bankBranchId;
	
	private Long generalSettingId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(Long bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public Long getGeneralSettingId() {
		return generalSettingId;
	}

	public void setGeneralSettingId(Long generalSettingId) {
		this.generalSettingId = generalSettingId;
	}
	
}
