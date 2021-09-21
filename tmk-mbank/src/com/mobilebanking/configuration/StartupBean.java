package com.mobilebanking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mobilebanking.startup.FirstAdminCreator;

@Configuration
public class StartupBean {

	@Bean(name="firstAdminCreator")
	public FirstAdminCreator firstAdminCreator(){
		return new FirstAdminCreator();
	}
}
