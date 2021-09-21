package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="smsPackages")
public class SmsPackages extends AbstractEntity<Long>{
	
	@OneToOne(fetch = FetchType.EAGER)
	private Bank bank;
	
	@OneToOne(fetch = FetchType.EAGER)
	private BankBranch bankBranch;
	
	@OneToOne(fetch = FetchType.EAGER)
	private GeneralSettings generalSetting;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public GeneralSettings getGeneralSetting() {
		return generalSetting;
	}

	public void setGeneralSetting(GeneralSettings generalSetting) {
		this.generalSetting = generalSetting;
	}
	
}
