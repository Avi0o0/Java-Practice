package com.practice.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class DummyService {
	
	@Value("${app.checkprop}")
	String profile;

	public String getDummyData() {
		return "Its a dummy data";
	}
	
//	@Scheduled(cron = "*/10 * * * * *")
//	public void runEvery10Sec() {
//	    System.out.println("Running every 10 seconds: " + LocalDateTime.now());
//	}

	public String currentActiveProfile() {
		return profile;
	}
}