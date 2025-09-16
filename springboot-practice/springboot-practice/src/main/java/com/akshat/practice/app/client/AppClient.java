package com.akshat.practice.app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "Dummy", url = "http://localhost:8000/")
public interface AppClient {

	@GetMapping("/getDummyData")
	public String getDummyData();

}
