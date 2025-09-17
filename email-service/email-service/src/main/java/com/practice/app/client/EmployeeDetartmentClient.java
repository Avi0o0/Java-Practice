package com.practice.app.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.practice.app.request.UserRequest;
import com.practice.app.response.EmployeeResponse;

import jakarta.validation.Valid;


@FeignClient("springboot-practice")
public interface EmployeeDetartmentClient {

	@GetMapping("/employee/{id}")
	public EmployeeResponse getEmployee(@RequestHeader("Authorization") String token, @PathVariable Integer id);
	
	@GetMapping("/employees")
	public List<EmployeeResponse> getAllEmployee();
	
	@GetMapping("/employees/by-department/{deptId}")
	public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable Integer deptId);
	
	@PostMapping("/login")
	public String login(@Valid @RequestBody UserRequest request);
}
