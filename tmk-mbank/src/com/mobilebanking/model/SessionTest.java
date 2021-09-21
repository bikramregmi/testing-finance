package com.mobilebanking.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class SessionTest {

	String mysession;

	public String getMysession() {
		return mysession;
	}

	public void setMysession(String mysession) {
		this.mysession = mysession;
	}
	
	
	
	
}
