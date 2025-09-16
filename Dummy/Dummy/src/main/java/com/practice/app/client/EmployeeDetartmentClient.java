package com.practice.app.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.akshat.practice.app.beans.response.EmployeeResponse;

@FeignClient(value = "springboot-practice", url = "http://localhost:8080/")
public interface EmployeeDetartmentClient {

	@GetMapping("/employee/{id}")
	public EmployeeResponse getEmployee(@PathVariable Integer id);
	
	@GetMapping("/employees")
	public List<EmployeeResponse> getAllEmployee();
	
	@GetMapping("/employees/by-department/{deptId}")
	public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable Integer deptId);
	
	
}
