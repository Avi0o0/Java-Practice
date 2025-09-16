package com.practice.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.app.service.DummyService;

@RestController
public class DummyController {
	
	private final DummyService dummyService;
	
	public DummyController(DummyService dummyService) {
		this.dummyService = dummyService;
	}
	
	@GetMapping("/getDummyData")
	public String getDummyData() {
		return dummyService.getDummyData();
	}
	
	@GetMapping("/getActiveProfile")
	public String getActiveProfile() {
		return dummyService.currentActiveProfile();
	}
}
