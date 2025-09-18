package com.akshat.practice.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.akshat.practice.app.client.AppClient;

@Service
public class DummyService {
	private final AppClient appClient;
	private final RestTemplate restTemplate;
	
	private static final String URL = "http://localhost:8000/getDummyData";
	
	public DummyService(AppClient appClient, RestTemplate restTemplate) {
		this.appClient = appClient;
		this.restTemplate = restTemplate;
	}
	
	//@CircuitBreaker("")
	public String getDummyData() {
		return appClient.getDummyData();
	}
	
	public String getDummyDataRest() {
        String url = URL;
        return restTemplate.getForObject(url, String.class);
    }
}
