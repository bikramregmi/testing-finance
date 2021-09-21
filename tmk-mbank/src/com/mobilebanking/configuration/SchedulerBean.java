package com.mobilebanking.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerBean {
	
	@Bean(destroyMethod = "shutdown")
	public Executor configureTaskScheduler(){
		return Executors.newScheduledThreadPool(3);
	}

}
