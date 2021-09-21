package com.mobilebanking.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="notice")
public class Notice extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String title ;
	
	private String notice;

	@ManyToOne(fetch=FetchType.EAGER)
	private Bank bank;
	
	private Boolean isViewed;
	
	public String getNotice() {
		return notice;
	}

	
	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}


	public Boolean getIsViewed() {
		return isViewed;
	}


	public void setIsViewed(Boolean isViewed) {
		this.isViewed = isViewed;
	}
	
	
}
